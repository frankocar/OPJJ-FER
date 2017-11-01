package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A class that represents a context of a single request
 * 
 * @author Franko Car
 *
 */
public class RequestContext {

	/**
	 * Output stream to write to
	 */
	private OutputStream outputStream;
	/**
	 * Character set to use
	 */
	private Charset charset;
	
	/**
	 * Encoding to use, UTF-8 by default
	 */
	private String encoding = "UTF-8";
	/**
	 * Status code, 200 by default
	 */
	private int statusCode = 200;
	/**
	 * Status text, "OK" by default
	 */
	private String statusText = "OK";
	/**
	 * Mime type, "text/html" by default
	 */
	private String mimeType = "text/html";
	
	/**
	 * A map of request parameters
	 */
	private Map<String, String> parameters;
	/**
	 * A map o temporary parameters for each request
	 */
	private Map<String, String> temporaryParameters;
	/**
	 * A map of parameters that remain persistent
	 */
	private Map<String, String> persistentParameters;
	/**
	 * A list of cookies that should be listed
	 */
	private List<RCCookie> outputCookies;
	/**
	 * A flag marking if a header has been generated already, to make
	 * sure it's not repeated twice in a singlr request
	 */
	private boolean headerGenerated = false;
	
	/**
	 * Dispatcher stored for use by other classes 
	 */
	private IDispatcher dispatcher;
	
	/**
	 * A map to store any additional header data that might be added
	 */
	private Map<String, String> additonalHeaderData;
	
	
	/**
	 * A constructor
	 * 
	 * @param outputStream OutputStream to use
	 * @param parameters Map of parameters
	 * @param persistentParameters Map of persistent parameters
	 * @param outputCookies List of output cookies
	 * @param temporaryParameters Map of temporary parameters
	 * @param dispatcher Associated dispatcher
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {
		if (outputStream == null) {
			throw new IllegalArgumentException("Output stream can't be null");
		}
		this.outputStream = outputStream;
		
		this.parameters = Collections.unmodifiableMap(parameters == null ? new HashMap<>() : parameters);
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.temporaryParameters = temporaryParameters == null ? new HashMap<>() : temporaryParameters;

		this.dispatcher = dispatcher;
		this.outputCookies = outputCookies == null ? new LinkedList<>() : outputCookies;
		
	}
	
	/**
	 * A constructor
	 * 
	 * @param outputStream OutputStream to use
	 * @param parameters Map of parameters
	 * @param persistentParameters Map of persistent parameters
	 * @param outputCookies List of output cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		this(outputStream, parameters, persistentParameters, outputCookies, null, null);
	}
	
	/**
	 * A getter for a single parameter of a requested name
	 * 
	 * @param name Parameter name
	 * @return Parameter value
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * A getter for a set of parameter names
	 * 
	 * @return Unmodifiable set of parameter names
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}
	
	/**
	 * A getter for a single persistent parameter of a requested name
	 * 
	 * @param name Parameter name
	 * @return Parameter value
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * A getter for a set of persistent parameter names
	 * 
	 * @return Unmodifiable set of persistent parameter names
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * A setter for a persistent parameter
	 * 
	 * @param name Parameter name
	 * @param value Parameter value
	 */
	public void setPersistentParameter(String name, String value) {
		if (name == null) {
			throw new IllegalArgumentException("null parameter can't be added");
		}
		persistentParameters.put(name, value);
	}
	
	/**
	 * Removes a persistent parameter of a given name
	 * 
	 * @param name Parameter name
	 */
	public void removePersistentParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException("null parameter can't be removed");
		}
		persistentParameters.remove(name);
	}
	
	/**
	 * A getter for a single temporary parameter of a requested name
	 * 
	 * @param name Parameter name
	 * @return Parameter value
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * A getter for a set of temporary parameter names
	 * 
	 * @return Unmodifiable set of temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * A setter for a temporary parameter
	 * 
	 * @param name Parameter name
	 * @param value Parameter value
	 */
	public void setTemporaryParameter(String name, String value) {
		if (name == null) {
			throw new IllegalArgumentException("null parameter can't be added");
		}
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Removes a temporary parameter of a given name
	 * 
	 * @param name Parameter name
	 */
	public void removeTemporaryParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException("null parameter can't be removed");
		}
		temporaryParameters.remove(name);
	}
	
	/**
	 * Adds a new RCCookie object to the list of cookies to output
	 * 
	 * @param rcCookie RCCookie to add
	 */
	public void addRCCookie(RCCookie rcCookie) {
		isHeaderGenerated();
		if (rcCookie == null) {
			throw new IllegalArgumentException("null cookie can't be added");
		}
		outputCookies.add(rcCookie);		
	}
	
	/**
	 * Checks if a header has been generated and throws an exception if it has.
	 */
	private void isHeaderGenerated() {
		if (headerGenerated == true) {
			throw new RuntimeException("Header properties can't be changed after header has been generated");
		}
	}
	
	/**
	 * Writes given byte array to the stream
	 * 
	 * @param data Data to write
	 * @return {@link RequestContext} object that has been used
	 * @throws IOException if an I/O error occurs.
	 */
	public RequestContext write(byte[] data) throws IOException {
		writeHeader();		
		outputStream.write(data);
		return this;
	}

	/**
	 * Writes given String array to the stream
	 * 
	 * @param data String to write
	 * @return {@link RequestContext} object that has been used
	 * @throws IOException if an I/O error occurs.
	 */
	public RequestContext write(String data) throws IOException {
		writeHeader();
		return write(data.getBytes(charset));
	}
	
	
	/**
	 * A method that generates and writes a header to the given output stream
	 * 
	 * @throws IOException if an I/O error occurs
	 */
	private void writeHeader() throws IOException {
		charset = Charset.forName(encoding);
		
		if (headerGenerated) {
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		
		sb.append("Content-Type: " + mimeType);
		if (mimeType.startsWith("text/")) {
			sb.append("; charset=" + encoding);
		}
		sb.append("\r\n");
		
		if (!outputCookies.isEmpty()) {
			for (RCCookie cookie : outputCookies) {
				sb.append("Set-Cookie: " + cookie.getName() + "=\"" + cookie.getValue() + '"');
				if (cookie.getDomain() != null) {
					sb.append("; Domain=" + cookie.getDomain());
				}
				if (cookie.getPath() != null) {
					sb.append("; Path=" + cookie.getPath());
				}
				if (cookie.getMaxAge() != null) {
					sb.append("; Max-Age=" + cookie.getMaxAge());
				}
				sb.append("; HttpOnly");
				sb.append("\r\n");
			}
		}
		
		if (additonalHeaderData != null && !additonalHeaderData.isEmpty()) {
			additonalHeaderData.entrySet().forEach(e -> sb.append(e.getKey() + ": " + e.getValue() + "\r\n"));
		}
		
		sb.append("\r\n");
		
		byte[] bytes = sb.toString().getBytes(StandardCharsets.ISO_8859_1);
		
		outputStream.write(bytes);
		outputStream.flush();
		headerGenerated = true;
		
	}
	
	/**
	 * Adds additional data to the header 
	 * 
	 * @param key Data key
	 * @param value Data value
	 */
	public void addHeaderData(String key, String value) {
		if (key == null || value == null) {
			throw new IllegalArgumentException("Header data can't be null");
		}
		
		if (additonalHeaderData == null) {
			additonalHeaderData = new HashMap<>();
		}
		
		additonalHeaderData.put(key, value);
	}

	/**
	 * Returns an associated dispatcher
	 * 
	 * @return Associated dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * A setter for encoding
	 * 
	 * @param encoding Encoding to use
	 */
	public void setEncoding(String encoding) {
		isHeaderGenerated();
		if (!Charset.isSupported(encoding)) {
			throw new IllegalArgumentException("Unsupported character set");
		}
		this.encoding = encoding;
	}

	/**
	 * A getter for current status code
	 * 
	 * @param statusCode Current status code
	 */
	public void setStatusCode(int statusCode) {
		isHeaderGenerated();
		this.statusCode = statusCode;
	}

	/**
	 * Status code setter
	 * 
	 * @param statusText New status code
	 */
	public void setStatusText(String statusText) {
		isHeaderGenerated();
		this.statusText = statusText;
	}

	/**
	 * Mime type setter
	 * 
	 * @param mimeType New mime type
	 */
	public void setMimeType(String mimeType) {
		isHeaderGenerated();
		this.mimeType = mimeType;
	}


	/**
	 * A class to store individual cookies
	 * 
	 * @author Franko Car
	 *
	 */
	public static class RCCookie {
		/**
		 * Cookie name
		 */
		private String name;
		/**
		 * Cookie value
		 */
		private String value;
		/**
		 * Cookie domain
		 */
		private String domain;
		/**
		 * Cookie path
		 */
		private String path;
		/**
		 * Cookie max age
		 */
		private Integer maxAge;
		
		/**
		 * A constructor
		 * 
		 * @param name Name
		 * @param value Value
		 * @param maxAge Maximum age
		 * @param domain Domain
		 * @param path Path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}
		
		/**
		 * A getter for name
		 * 
		 * @return Name
		 */
		public String getName() {
			return name;
		}
		/**
		 * A getter for value
		 * 
		 * @return Value
		 */
		public String getValue() {
			return value;
		}
		/**
		 * A getter for domain
		 * 
		 * @return Domain
		 */
		public String getDomain() {
			return domain;
		}
		/**
		 * A getter for path
		 * 
		 * @return Path
		 */
		public String getPath() {
			return path;
		}
		/**
		 * A getter for maximum age
		 * 
		 * @return Maximum age
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
	}


	
	
	
}
