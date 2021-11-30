
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class main {

	
	public static void main(String[] args) throws IOException, ParseException {
		//String str = "{\"name\": \"Sam Smith\", \"technology\": {\"Same\": null}, \"tech\": {\"Same\": [9]}}";  
		//String str2="{\"name\": {\"tech\": \"Sam Smith\"}, \"tech\": {\"Same\": [90, {\"Game\": 80}, {\"Game\": 90},[{\"Bow\": 0}] , [{\"Game\": 90}]]}}";
		//String str5 = " {\"Same\": 9}";  
		//String str6="{\"Same\": [90, {\"Game\": 80}, {\"Game\": 90},[{\"Bow\": 0}] , [{\"Game\": 90}]]}";
    	JSONParser parser=new JSONParser();
    	//JSONObject json = null;
    	JSONObject json2=null;
		//	json = (JSONObject) parser.parse(str);
		//} catch (ParseException e) {
			// TODO Auto-generated catch block/			e.printStackTrace();
		//}
		//System.out.println((((JSONArray)((JSONObject)(json.get("technology"))).get("Same"))).get(0) instanceof Boolean);
		
		//try {
			//json2 = (JSONObject) parser.parse(str);
		//} catch (ParseException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		//Map map1=new Map(str);
		//Map map2=new Map(str2);
		ArrayList<JSONType> arrlist=new ArrayList<>();
		//arrlist.add(map1.createSchema());
		//arrlist.add(map2.createSchema());
		KMerge2 merge2=new KMerge2(arrlist);
		//PrintData(merge2.merge(arrlist));
		
		
		Path fileName=Path.of("content-tweets.json");
		String str3=Files.readString(fileName);

		JSONParser parser1=new JSONParser();
		JSONArray array=(JSONArray) parser1.parse(str3);
	;
		ArrayList<JSONType> arr=new ArrayList<>();
		for (int i=0; i<array.size();i++) {
			Map map=new Map(array.get(i).toString());
			arr.add(map.createSchema());
			
		}
		System.out.println("Number of records in sample: "+arr.size());
		
		KMerge2 merge=new KMerge2(arr);
		ArrayList<MergeType> result=merge.merge(arr);
		System.out.println("Results:");
		
		
		System.out.println("");
		System.out.println("Result schema: "+Display.getData(result));
		double num=0.0;
		for (int i=0; i<arr.size();i++) {
			ArrayList<JSONType> arr2=new ArrayList<>();
			arr2.add(arr.get(i));
			KMerge2 m=new KMerge2(arr2);
			ArrayList<MergeType> r=m.merge(arr2);
			num=num+(Display.getSize(r));
		}
		System.out.println("Map average type size: "+num/arr.size());
		System.out.println("Reduction final size:"+Display.getSize(result));
		
		
		
		
    	
		
	}
	
//	static JSONObject merge(JSONObject jsonInput[]) {
//		Set<String> keys=new HashSet<String>();
//		for (int i=0; i<jsonInput.length; i++) {
//			Object keyArray[]=jsonInput[i].keySet().toArray();
//			for (int j=0; j<keyArray.length; j++) {
//				keys.add((String)keyArray[j]);
//			}
//		}
//		Object keyArray[]=keys.toArray();
//		for (int i=0; i<keyArray.length; i++) {
//			System.out.println(keyArray[i]);
//		}
//		
//		return null;
//	}
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
	
//	static void PrintData(ArrayList<MergeType> list) {
//		for (int i=0; i<list.size(); i++) {
//			MergeType merge=list.get(i);
//			
//			if (merge.getType().equals("array")) {
//				System.out.print("[");
//				PrintData(merge.getArray());
//				System.out.print("]");
//			}else if (merge.getType().equals("object")) {
//				System.out.print("{");
//				HashMap<String, ArrayList<MergeType>> map=list.get(i).getMap();
//				Iterator<String> iterate=map.keySet().iterator();
//				while(iterate.hasNext()) {
//					
//					String key=iterate.next();
//					if (merge.getOptional().contains(key)) {
//						System.out.print(key+"?: ");
//					}else {
//					System.out.print(key+": ");
//					
//					}
//					PrintData(map.get(key));
//					System.out.print(", ");
//				
//					
//				}
//				System.out.print("}");
//				
//				ArrayList<String> optional=merge.getOptional();
////				System.out.print("Optional: ");
////				for (int j=0; j<optional.size(); j++) {
////					System.out.print(optional.get(j)+", ");
////				}
//				//System.out.println("");
//				//System.out.println("}");
//			}else {
//				System.out.print(list.get(i).getType()+" ");
//				
//			}
//		}
//	}
//	
//	static String getData(ArrayList<MergeType> list) {
//		String str="";
//		for (int i=0; i<list.size(); i++) {
//			MergeType merge=list.get(i);
//			
//			if (merge.getType().equals("array")) {
//				str=str+'[';
//				str=str+getData(merge.getArray());
//				str=str+']';
//			}else if (merge.getType().equals("object")) {
//				str=str+'{';
//				HashMap<String, ArrayList<MergeType>> map=list.get(i).getMap();
//				Iterator<String> iterate=map.keySet().iterator();
//				while(iterate.hasNext()) {
//					
//					String key=iterate.next();
//					if (merge.getOptional().contains(key)) {
//						str=str+key+"?: ";
//					}else {
//					str=str+key+": ";
//					
//					}
//					str=str+getData(map.get(key));
//					str=str+", ";
//				
//					
//				}
//				str=str+'}';
//				
//				ArrayList<String> optional=merge.getOptional();
////				System.out.print("Optional: ");
////				for (int j=0; j<optional.size(); j++) {
////					System.out.print(optional.get(j)+", ");
////				}
//				//System.out.println("");
//				//System.out.println("}");
//			}else {
//				return list.get(i).getType()+" ";
//				
//			}
//		}
//		return str;
//	}
//	
//	static int getSize(ArrayList<MergeType> arr) {
//		int returnNum=0;
//		for (int i=0; i<arr.size(); i++) {
//			if (arr.get(i).getType().equals("object")) {
//				HashMap<String, ArrayList<MergeType>> map=arr.get(i).getMap();
//				Iterator<String> key=map.keySet().iterator();
//				while(key.hasNext()) {
//					returnNum=returnNum+1+getSize(map.get(key.next()));
//				}
//			}else if (arr.get(i).getType().equals("array")) {
//				
//				returnNum=returnNum+1+getSize(arr.get(i).getArray());
//			}else {
//				returnNum= returnNum+1;
//			}
//			
//		}
//		return returnNum;
//	}
//}
