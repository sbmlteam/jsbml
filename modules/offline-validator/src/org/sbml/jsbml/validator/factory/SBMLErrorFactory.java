package org.sbml.jsbml.validator.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URISyntaxException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.util.Message;

public class SBMLErrorFactory {
    
    /**
     * Keys to access the JSON file
     */
    private static final String JSON_KEY_AVAILABLE = "Available";
    private static final String JSON_KEY_MESSAGE = "Message";
    private static final String JSON_KEY_SHORT_MESSAGE = "Available";
    private static final String JSON_KEY_PACKAGE = "Package";
    private static final String JSON_KEY_CATEGORY = "Category";
    private static final String JSON_KEY_DEFAULT_SEVERITY = "DefaultSeverity";
    private static final String JSON_KEY_UNFORMATED_SEVERITY = "SeverityL%dV%d";
    
    private static SoftReference<JSONObject> cachedJson;
    
    
    
    public static SBMLError createError(int id, int level, int version) {
	JSONObject errors = null;

	if (SBMLErrorFactory.cachedJson != null) {
	    errors = cachedJson.get();
	}

	if (errors == null) {

	    try {
		String fileName = "/org/sbml/jsbml/resources/SBMLErrors.json";

		File file = new File(SBMLError.class.getResource(fileName).toURI());

		JSONParser parser = new JSONParser();
		errors = (JSONObject) (parser.parse(new FileReader(file)));
	    }catch (URISyntaxException e)
	    {
		
	    }
	    catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	    SBMLErrorFactory.cachedJson = new SoftReference<JSONObject>(errors);
	}
	
	if(errors != null && errors.containsKey("" + id))
	{
	    JSONObject errorEntry = (JSONObject) errors.get("" + id);
	    
	    
	    if(errorEntry != null && isAvailable(errorEntry, level, version))
	    {
		SBMLError e = new SBMLError();
		
		e.setCode(id);
		e.setCategory((String)errorEntry.get(JSON_KEY_CATEGORY));
		
		Message m = new Message();
		m.setMessage((String)errorEntry.get(JSON_KEY_MESSAGE));
		e.setMessage(m);
		
		Message sm = new Message();
		sm.setMessage((String) errorEntry.get(JSON_KEY_SHORT_MESSAGE));
		
		e.setShortMessage(sm);
		
		e.setPackage((String)errorEntry.get(JSON_KEY_PACKAGE));
		
		
		Object sev = errorEntry.get(SBMLErrorFactory.getSeverityKey(level, version));
		
		if (sev == null)
		{
		    sev = errorEntry.get(SBMLErrorFactory.JSON_KEY_DEFAULT_SEVERITY);
		}
		
		e.setSeverity((String)sev);
		
//		System.out.println("Out: " + e + "   " + e.getShortMessage());
		return e;
	    }
	    
	}
	
	return null;
    }
    
    private static boolean isAvailable(JSONObject error, int level, int version)
    {
	String minLv = (String)(error.get(JSON_KEY_AVAILABLE));
	
	if(minLv != null)
	{
	    String[] blocks = minLv.substring(1).split("V");
	    
	    if(blocks.length == 2)
	    {
		int l = Integer.parseInt(blocks[0]);
		int v = Integer.parseInt(blocks[1]);
		
		// Return true if level is greater as the minimal level
		// Or if the level is equal, but the version greater equal as the min verison.
		return (level > l) || (level == l && version >= v);
	    }
	}
	
	return true;
    }
    
    private static String getSeverityKey(int level, int version)
    {
	return String.format(JSON_KEY_UNFORMATED_SEVERITY, level, version);
    }

}
