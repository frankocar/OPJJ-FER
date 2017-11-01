package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * A demo for SmartScript execution
 * 
 * @author Franko Car
 *
 */
public class ExecDemo {

	/**
	 * Main class
	 * 
	 * @param args none
	 */
	public static void main(String[] args) {		
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get("./webroot/scripts/osnovni.smscr")), StandardCharsets.UTF_8);    //osnovni
//			docBody = new String(Files.readAllBytes(Paths.get("./webroot/scripts/zbrajanje.smscr")), StandardCharsets.UTF_8);  //zbrajanje
//			docBody = new String(Files.readAllBytes(Paths.get("./webroot/scripts/brojPoziva.smscr")), StandardCharsets.UTF_8); //broj poziva
//			docBody = new String(Files.readAllBytes(Paths.get("./webroot/scripts/fibonacci.smscr")), StandardCharsets.UTF_8);  //fibonacci
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		
		
		//odkomentirati za zbrajanje.smscr
//		parameters.put("a", "4");
//		parameters.put("b", "2");
		
		//odkomentirati za brojPoziva.smscr
//		persistentParameters.put("brojPoziva", "3");
//		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
		
		new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

		//odkomentirati za brojPoziva.smscr
//		System.out.println();
//		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}
	
}
