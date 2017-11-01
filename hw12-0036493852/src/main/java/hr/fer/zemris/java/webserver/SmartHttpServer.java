package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Simple HTTP server. It uses a configuration read from .properties file given as a command line
 * argument. It supports multiple sessions using cookies. It's able to run SmartScript scripts.
 * It's functionality can be extended by using workers to do a calculation, either by using 
 * {@code /ext/[worker name]} for workers located in {@code hr.fer.zemris.java.webserver.workers}
 * folder or by using names defined in {@code worker.properties} file. It's multithreaded to support 
 * multiple users at the same time. Uses HTTP/1.0 and HTTP/1.1 protocols and GET method.
 * 
 * @author Franko Car
 *
 */
public class SmartHttpServer {
	/**
	 * Address on which server listens for requests
	 */
	private String address;
	/**
	 * A port for server to use
	 */
	private int port;
	/**
	 * Maximum number of threads to use
	 */
	private int workerThreads;
	/**
	 * Length of a user session in seconds
	 */
	private int sessionTimeout;
	/**
	 * Map of mime types for different file extensions
	 */
	private Map<String, String> mimeTypes;
	/**
	 * Thread to run
	 */
	private ServerThread serverThread;
	/**
	 * ExecutorService to manage multiple threads
	 */
	private ExecutorService threadPool;
	/**
	 * Path to the root folder
	 */
	private Path documentRoot;
	
	/**
	 * Map of all workers mapped in workers.properties file
	 */
	private Map<String, IWebWorker> workersMap;
	
	/**
	 * A boolean to control servers ability to listen to all addresses.
	 * It will accept requests from any address when this is set to true
	 * and only from the address specified in it's configuration file
	 * when set to false.
	 */
	private static final boolean ALL_ADDRESSES = true;
	
	/**
	 * A map of all user sessions. Expired sessions are perionically removed
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * Random number generator
	 */
	private Random sessionRandom = new Random();
	
	/**
	 * Main method
	 * 
	 * @param args Configuration file
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected a config file path");
			return;
		}
		
		new SmartHttpServer(args[0]);
	}

	/**
	 * Server constructor.
	 * 
	 * @param configFileName Path to the configuration file-
	 */
	public SmartHttpServer(String configFileName) {
		Properties serverProperties = loadProp(configFileName);
		
		address = serverProperties.getProperty("server.adress");
		port = Integer.parseInt(serverProperties.getProperty("server.port"));
		workerThreads = Integer.parseInt(serverProperties.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(serverProperties.getProperty("session.timeout"));
		documentRoot = Paths.get(serverProperties.getProperty("server.documentRoot"));
		
		Properties mimeProperties = loadProp(serverProperties.getProperty("server.mimeConfig"));	
		mimeTypes = new HashMap<>();
		mimeProperties.entrySet().forEach(e -> mimeTypes.put(e.getKey().toString(), e.getValue().toString()));
		
		Properties workersConfig = loadProp(serverProperties.getProperty("server.workers"));
		workersMap = new HashMap<>();
		workersConfig.entrySet().forEach(e -> {
			String path = e.getKey().toString();
			String fqcn = e.getValue().toString();
			
			IWebWorker iww = null;
			try {
				iww = workerFromFqcn(fqcn);
				workersMap.put(path, iww);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
				System.out.println("Unable to initialize a requested worker");
			}
			
		});
		
		serverThread = new ServerThread();
		start();
		
		setupMemoryManager(5);
	}

	/**
	 * Runs a thread that will periodically remove expired sessions
	 * from sessions map to help with the memory consumption. Basically
	 * a garbage collector.
	 * 
	 * @param minutes Interval given in minutes to set how often it's run
	 */
	private void setupMemoryManager(int minutes) {
		Thread memoryManager = new Thread(() -> {
				while (true) {
					synchronized (SmartHttpServer.this) {
						Iterator<Map.Entry<String, SessionMapEntry>> it = sessions.entrySet().iterator();
						while (it.hasNext()) {
							long expiry = it.next().getValue().validUntil;
							if (expiry < (System.currentTimeMillis() / 1000)) {
								it.remove();
							}
						}	
					}
					
					try {
						Thread.sleep(minutes * 60_000);
					} catch (InterruptedException ignorable) {
					}				
			}
		});
		memoryManager.setDaemon(true);
		memoryManager.start();
		
	}

	/**
	 * A method that will instantiate a new worker from a given fully qualified class name
	 * 
	 * @param fqcn fully qualified class name
	 * @return requested IWebWorker
	 * @throws InstantiationException if this Class represents an abstract class, an interface, 
	 * 									an array class, a primitive type, or void; or if the class has no 
	 * 									nullary constructor; or if the instantiation fails for some other reason.
	 * @throws IllegalAccessException if the class or its nullary constructor is not accessible.
	 * @throws ClassNotFoundException if the class was not found
	 */
	private IWebWorker workerFromFqcn(String fqcn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
		Object newObject = referenceToClass.newInstance();
		return (IWebWorker) newObject;
	}
	
	/**
	 * Starts the server thread
	 */
	protected synchronized void start() {
		if (threadPool == null || threadPool.isShutdown()) {
			threadPool = Executors.newFixedThreadPool(workerThreads);
		}
		
		if (!serverThread.isAlive()) {
			serverThread.start();
		}
	}

	/**
	 * Stops the server thread
	 */
	protected synchronized void stop() {
		serverThread.kill();
		threadPool.shutdown();
	}
	
	/**
	 * Loads {@link Properties} from a given file
	 * 
	 * @param configName File of type .properties
	 * @return Properties object with loaded data
	 */
	private Properties loadProp(String configName) {
		Properties prop = new Properties();
		
		try (InputStream is = new FileInputStream(configName)) {
			prop.load(is);
		} catch (FileNotFoundException ex) {
			System.out.println("File not found: " + ex.getMessage());
			System.exit(1);
		} catch (IOException ex) {
			System.out.println("File unreadable: " + ex.getMessage());
			System.exit(1);
		}
		
		return prop;
	}

	/**
	 * A class to store entries to session map
	 * 
	 * @author Franko Car
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * SID to be used as a key
		 */
		String sid;
		/**
		 * Time when the cookie expires
		 */
		long validUntil;
		/**
		 * Map to store users parameters
		 */
		Map<String, String> map;	
	}

	/**
	 * Represents a thread run by the server
	 * 
	 * @author Franko Car
	 *
	 */
	protected class ServerThread extends Thread {
		
		/**
		 * A flag to control servers status, stops when this is true
		 */
		private boolean stop;
		/**
		 * Loading timeout in milliseconds
		 */
		private final static int TIMEOUT = 4000;
		
		@Override
		@SuppressWarnings("resource")
		public void run() {
			ServerSocket socket = null;
			try {
				socket = new ServerSocket();
				if (ALL_ADDRESSES) { 
					socket.bind(new InetSocketAddress((InetAddress)null, port));
				} else {
					socket.bind(new InetSocketAddress(InetAddress.getByName(address), port));					
				}

				socket.setSoTimeout(TIMEOUT);
			} catch (IOException e) {
				throw new RuntimeException("Socket error: " + e.getMessage(), e);
			} 
			
			while (!stop) {
				Socket client;
				try {
					client = socket.accept();
				} catch (IOException e) {
					continue;
				} 
				
				System.out.println("Request recieved");
				ClientWorker cw = new ClientWorker(client);
				threadPool.submit(cw);
			}
		}
		

		/**
		 * Stops the thread
		 */
		public void kill() {
			stop = true;
		}
	}

	/**
	 * Represent a thread run for each client
	 * 
	 * @author Franko Car
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * Client's socket
		 */
		private Socket csocket;
		/**
		 * Data input stream
		 */
		private PushbackInputStream istream;
		/**
		 * Data output stream
		 */
		private OutputStream ostream;
		/**
		 * Protocol version
		 */
		private String version;
		/**
		 * Requested method
		 */
		private String method;
		/**
		 * Parameters stored for each client
		 */
		private Map<String, String> params;
		/**
		 * Temporary parameters
		 */
		private Map<String, String> tempParams;
		/**
		 * Permanent parameters
		 */
		private Map<String, String> permPrams;
		/**
		 * A list of cookies to send 
		 */
		private List<RCCookie> outputCookies;
		
		/**
		 * Request context
		 */
		private RequestContext context;

		/**
		 * A constructor
		 * 
		 * @param csocket Socket used by a client who requested the data
		 */
		public ClientWorker(Socket csocket) {
			super();
			params = new HashMap<String, String>();
			tempParams = new HashMap<String, String>();
			permPrams = new HashMap<String, String>();
			outputCookies = new ArrayList<RequestContext.RCCookie>();
			context = null;
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				
				List<String> request = readRequest();
				if (request.size() < 1) {
					sendError(400, "Invalid header");
					return;
				}
				
				String[] firstLine = request.get(0).split(" ");
				if (firstLine == null || firstLine.length != 3) {
					sendError(400, "Bad request");
				}
				
				method = firstLine[0].toUpperCase();
				String requestedPath = firstLine[1];
				version = firstLine[2];
				
				if (!(version.equals("HTTP/1.1") || version.equals("HTTP/1.0"))) {
					sendError(400, "Version unsupported");
					return;
				}
				
				if (!method.equals("GET")) {
					sendError(400, "Method unsupported");
					return;
				}
				
				checkSession(request);
				
				String path;
				String paramString;
				
				if (requestedPath.contains("?")) {
					String[] split = requestedPath.split("[?]");
					path = split[0];
					paramString = split[1];
					parseParameters(paramString);
				} else {
					path = requestedPath;
					paramString = null;
				}
				
				if (checkWorker(path)) {
					return;
				}
				
				internalDispatchRequest(path, true);
			} catch (IOException e) {
				throw new RuntimeException("Socket communcation error", e);
			} finally {
				try {
					csocket.close();
				} catch (IOException ignorable) {
					ignorable.printStackTrace();
				}
			}
		}

		/**
		 * Checks whether a session with a client already exists
		 * 
		 * @param request Client's request in a form of a list
		 */
		private void checkSession(List<String> request) {
			synchronized (SmartHttpServer.this) { 
				String sidCandidate = null;
				String domain = null;
				
				for (String line : request) {
					if (line.startsWith("Cookie:")) {
						line = line.substring(7).trim();
						String[] cookies = line.split(";");
						
						for (String cookie : cookies) {
							cookie = cookie.trim();
							if (!cookie.startsWith("sid")) {
								continue;
							}
							
							sidCandidate = cookie.split("=")[1];
							sidCandidate = sidCandidate.replaceAll("\"", "");
						}
					} else if (line.startsWith("Host:")) {
						line = line.substring(5).trim();
						line = line.substring(0, line.indexOf(":"));
						domain = line;
					}
					
				}
				
				if (sidCandidate == null) {
					sidCandidate = createSID(domain);
				} else {
					SessionMapEntry session = sessions.get(sidCandidate);
					if (session != null) {
						if (session.validUntil > System.currentTimeMillis() / 1000) {
							session.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
						} else {
							sessions.remove(sidCandidate);
							sidCandidate = createSID(domain);
						}
					} else {
						sidCandidate = createSID(domain);
					}
				}
				permPrams = sessions.get(sidCandidate).map;				
			}
			
		}

		/**
		 * A method to generate a random SID and add it to the output list
		 * 
		 * @param domain Domain form which a request was made
		 * @return New SID
		 */
		private String createSID(String domain) {
			StringBuilder sb = new StringBuilder();
			
			for (int i = 0; i < 20; i++) {
				sb.append((char)(sessionRandom.nextInt(26) + 65)); //A == 65
			}
			
			SessionMapEntry entry = new SessionMapEntry();
			
			entry.sid = sb.toString();
			entry.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
			entry.map = new ConcurrentHashMap<>();
			
			domain = domain == null ? address : domain;
			
			outputCookies.add(new RCCookie("sid", entry.sid, null, domain, "/"));
			sessions.put(entry.sid, entry);
			return sb.toString();
		}

		/**
		 * Checks if a path should point to the worker and invokes it if true
		 * 
		 * @param path Given path
		 * @return Boolean true if it's a worker, false otherwise
		 * @throws IOException
		 */
		private boolean checkWorker(String path) throws IOException {
			if (!path.startsWith("/ext/")) {
				return false;
			}
			
			String fqcn = "hr.fer.zemris.java.webserver.workers." + path.substring(5);
			
			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);			
				context.setStatusCode(200);
			}
			
			try {
				workerFromFqcn(fqcn).processRequest(context);
			} catch (Exception e) {
				sendError(404, "Can't start worker");
				System.out.println("Unable to initialize a requested worker");
			}	
			return true;
		}

		/**
		 * A method to handle internal dispatch requests
		 * 
		 * @param urlPath Requested URL
		 * @param directCall Flag to mark a direct call
		 * @throws IOException If an error occurs while reading the stream
		 */
		private void internalDispatchRequest(String urlPath, boolean directCall) throws IOException {
			Path requestPath = Paths.get(documentRoot.toAbsolutePath() + urlPath);
			if (!requestPath.toAbsolutePath().toString().startsWith(documentRoot.toAbsolutePath().toString())) {
				sendError(403, "Forbidden");
				System.out.println(requestPath.toAbsolutePath().toString() + " is not a subdirectory of " + documentRoot.toAbsolutePath().toString());
				System.out.println("Forbidden");
				return;
			}
			
			if ((urlPath.equals("/private") || urlPath.startsWith("/private/")) && directCall) {
				sendError(404, "Not found");
				return;
			}
			
			if (!(Files.exists(requestPath) && Files.isRegularFile(requestPath) && Files.isReadable(requestPath) || workersMap.containsKey(urlPath))) {
				sendError(404, "Not found");
				return;
			}
			
			String fileName = requestPath.toAbsolutePath().toString();
			
			if (fileName.endsWith("/favicon.ico")) {
				context.setMimeType("image/x-icon");
				byte[] file = Files.readAllBytes(requestPath);
				context.addHeaderData("Content-Length", Integer.toString(file.length));
				context.write(file);	
				return;
			}
			
			String extension = "";
			int i = fileName.lastIndexOf('.');
			int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\')); //take care of stuff like /folder/folder.text/file
			if (i > p) {
				extension = fileName.substring(i + 1);
			}
			
			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
			}
			
			String mimeType = mimeTypes.getOrDefault(extension, "application/octet-stream");
			context.setMimeType(mimeType);			
			context.setStatusCode(200);
			
			if (workersMap.containsKey(urlPath)) {
				try {
					workersMap.get(urlPath).processRequest(context);
				} catch (Exception e) {
					sendError(405, "Unable to process request");
				}
				return;
			}
			
			if (extension.equalsIgnoreCase("smscr")) {
				String docBody = new String(Files.readAllBytes(Paths.get(documentRoot.toAbsolutePath() + urlPath)), StandardCharsets.UTF_8);
				context.setMimeType("text/plain");
				new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(), context).execute();
				return;
			}
			
			byte[] file = Files.readAllBytes(requestPath);
			context.addHeaderData("Content-Length", Integer.toString(file.length));
			context.write(file);
			
		}

		/**
		 * A method to parse parameters from a string
		 * 
		 * @param paramString Sting of parameters
		 */
		private void parseParameters(String paramString) {
			if (paramString == null) {
				return;
			}
			
			paramString = paramString.trim();
			String[] pairs = paramString.split("[&]");
			
			for (String pair : pairs) {
				String[] array = pair.trim().split("[=]");
				if (array.length != 2 || array[0].isEmpty() || array[1].isEmpty()) {
					throw new IllegalArgumentException("Illegal parameter");
				}
				params.put(array[0], array[1]);
			}
			
		}

		/**
		 * A method to read a request and split it into a list
		 * of separate lines
		 * 
		 * @return A request in a form of a list of strings
		 * @throws IOException If an error occurs while reading the stream
		 */
		private List<String> readRequest() throws IOException {
			List<String> headers = new ArrayList<>();
			
			String requestHeader = readStreamRequest();
//			System.out.println(requestHeader);
			
			String currentLine = null;
			for(String s : requestHeader.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * A state machine that reads a request from a stream and returns it as a
		 * single string
		 * 
		 * @return A request as a single string
		 * @throws IOException If an error occurs while reading the stream
		 */
		private String readStreamRequest() throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
		l:	while (true) {
				int b = istream.read();
				
				if(b == -1) return null;
				
				if(b != 13) {
					bos.write(b);
				}
				
				switch(state) {
				case 0: 
					if(b == 13) state = 1;
					else if(b == 10) state = 4;
					break;
				case 1: 
					if(b == 10) state = 2;
					else state = 0;
					break;
				case 2: 
					if(b == 13) state = 3;
					else state = 0;
					break;
				case 3: 
					if(b == 10) break l;
					else state = 0;
					break;
				case 4: 
					if(b == 10)  break l;
					else state = 0;
					break;
				}
			}
			return new String(bos.toByteArray(), StandardCharsets.US_ASCII);
		}
		
		/**
		 * Sends an error with given parameters
		 * 
		 * @param statusCode Error code
		 * @param statusText Error text
		 * @throws IOException If an error occurs while reading the stream
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" 
					+ "Server: SmartHTTPServer\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" 
					+ "Content-Length: 0\r\n" 
					+ "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			ostream.flush();

		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);			
		}
		
	}
}
