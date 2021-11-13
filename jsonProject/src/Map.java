import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Map {
	private JSONParser parser;
	private JSONObject json;
	public Map(String jsonString) {
		parser=new JSONParser();
		try {
			json = (JSONObject) parser.parse(jsonString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public JSONType createSchema() {
		return makeSchema(this.json);
	}
	
	
	public JSONType makeSchema(Object obj) {
		
		if (obj instanceof JSONObject) {
			//System.out.println("Object");
			JSONType returnType=new JSONType("object");
			Iterator<String> keys=((JSONObject)obj).keySet().iterator();
			while(keys.hasNext()) {
				String key=keys.next();
				returnType.addJSONType(key, makeSchema(((JSONObject)obj).get(key)));
			}
			return returnType;
		}else if (obj instanceof JSONArray) {
			//System.out.println("Array");
			JSONType returnType=new JSONType("array");
			Iterator<Object> it=((JSONArray)obj).iterator();
			int i=0;
			while (it.hasNext()) {
				returnType.addJSONType(String.valueOf(i), makeSchema(it.next()));
				i++;
			}
			return returnType;
			
		}else {
			
			return new JSONType("primitive");
		}
	}
}