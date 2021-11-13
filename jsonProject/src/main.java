
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.*;

public class main {

	
	public static void main(String[] args) {
		String str = "{\"name\": \"Sam Smith\", \"technology\": {\"Same\": [90]}}";  
//		String str2="{\"nam\": \"Sam Smith\", \"tech\": {\"Same\": [90]}}";
//    	JSONParser parser=new JSONParser();
//    	JSONObject json = null;
//    	JSONObject json2=null;
//		try {
//			json = (JSONObject) parser.parse(str);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			json2 = (JSONObject) parser.parse(str2);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	System.out.println(((JSONObject)json.get("technology")).get("Same") instanceof JSONArray);
//    	Object keys[]=json.keySet().toArray();
//    	System.out.println((String)keys[0]);
//    	JSONObject jsonArray[]= {json, json2};
//    	merge(jsonArray);
		
		Map map=new Map(str);
		JSONType json=map.createSchema();
		System.out.println(json.printJSON());
    	
		
	}
	
	static JSONObject merge(JSONObject jsonInput[]) {
		Set<String> keys=new HashSet<String>();
		for (int i=0; i<jsonInput.length; i++) {
			Object keyArray[]=jsonInput[i].keySet().toArray();
			for (int j=0; j<keyArray.length; j++) {
				keys.add((String)keyArray[j]);
			}
		}
		Object keyArray[]=keys.toArray();
		for (int i=0; i<keyArray.length; i++) {
			System.out.println(keyArray[i]);
		}
		
		return null;
	}
}
