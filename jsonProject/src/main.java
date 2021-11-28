
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class main {

	
	public static void main(String[] args) throws IOException {
		String str = "{\"name\": \"Sam Smith\", \"technology\": {\"Same\": [90]}}";  
		String str2="{\"name\": {\"tech\": \"Sam Smith\"}, \"tech\": {\"Same\": [90, {\"Game\": 80},{\"Game\": 80},{\"Game\": 80},[{\"Bow\": 0}] , [{\"Game\": 90}]]}}";
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
		
		Path fileName=Path.of("example.json");
		String str3=Files.readString(fileName);
		Map map3=new Map(str3);
		JSONType json3=map3.createSchema();
		//System.out.println(json3.printJSON());

		
		Map map=new Map(str);
		Map map2=new Map(str2);
		JSONType json=map.createSchema();
		//System.out.println(json.getMap().get("name").getType());
		JSONType json2=map2.createSchema();
		ArrayList<JSONType> arr=new ArrayList<>();
		//arr.add(json);
		arr.add(json2);
		//arr.add(json3);
		KMerge2 merge=new KMerge2(arr);
		ArrayList<MergeType> result=merge.merge(arr);
		PrintData(result);
		System.out.println(getSize(result));
		
		
    	
		
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



//static void printData(ArrayList<Object> arr) {
//	
//	for (int i=0; i<arr.size(); i++) {
//		if (arr.get(i) instanceof JSONType) {
//			System.out.println(((JSONType)(arr.get(i))).getType());
//		}else if (arr.get(i) instanceof ArrayList) {
//			System.out.println("Array: ");
//			printData((ArrayList<Object>)arr.get(i));
//		}else if (arr.get(i) instanceof HashMap) {
//			System.out.println("{");
//			HashMap<String, ArrayList<Object>> map=((HashMap)arr.get(i));
//			Iterator keys=map.keySet().iterator();
//			while(keys.hasNext()) {
//				String key=(String) keys.next();
//				System.out.print(key+": ");
//				printData(map.get(key));
//			}
//			System.out.println("}");
//		}
//	}
//	
//}
	
	static void PrintData(ArrayList<MergeType> list) {
		for (int i=0; i<list.size(); i++) {
			MergeType merge=list.get(i);
			if (merge.getType().equals("primitive")) {
				System.out.println(list.get(i).getType());
				
			}else if (merge.getType().equals("array")) {
				System.out.println("array: [");
				PrintData(merge.getArray());
				System.out.println("]");
			}else if (merge.getType().equals("object")) {
				System.out.println("{");
				HashMap<String, ArrayList<MergeType>> map=list.get(i).getMap();
				Iterator<String> iterate=map.keySet().iterator();
				while(iterate.hasNext()) {
					
					String key=iterate.next();
					System.out.print(key+": ");
					PrintData(map.get(key));
				
					
				}
				ArrayList<String> optional=merge.getOptional();
				System.out.println("Optional: ");
				for (int j=0; j<optional.size(); j++) {
					System.out.print(optional.get(j)+", ");
				}
				System.out.println("");
				System.out.println("}");
			}
		}
	}
	
	static int getSize(ArrayList<MergeType> arr) {
		int returnNum=0;
		for (int i=0; i<arr.size(); i++) {
			if (arr.get(i).getType().equals("object")) {
				HashMap<String, ArrayList<MergeType>> map=arr.get(i).getMap();
				Iterator<String> key=map.keySet().iterator();
				while(key.hasNext()) {
					returnNum=returnNum+1+getSize(map.get(key.next()));
				}
			}else if (arr.get(i).getType().equals("array")) {
				returnNum=returnNum+1+getSize(arr.get(i).getArray());
			}else {
				return returnNum+1;
			}
			
		}
		return returnNum;
	}
}
