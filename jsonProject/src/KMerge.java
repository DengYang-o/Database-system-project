import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONObject;

public class KMerge {
	ArrayList<JSONType> array;
	
	public KMerge(ArrayList<JSONType> array) {
		this.array=array;
	}
	
	public ArrayList<Object> merge(ArrayList<JSONType> list){
		ArrayList<JSONType> objList=new ArrayList<>();
		ArrayList<JSONType> arrList=new ArrayList<>();
		ArrayList< JSONType> primList=new ArrayList<>();
		for (int i=0; i<list.size(); i++) {
			if (list.get(i).getType().equals("object")) {
				objList.add(list.get(i));
			}else if (list.get(i).getType().equals("array")) {
				arrList.add(list.get(i));
			}else {
				primList.add(list.get(i));
			}
			
		}
		ArrayList<Object> returnList=new ArrayList<>();
		if (primList.isEmpty()==false) {
			returnList.add(new JSONType("primitive"));
		}
		if (arrList.isEmpty()==false) {
		returnList.add(arrayMerge(arrList));
		}
		if (objList.isEmpty()==false) {
		returnList.add(ObjectMerge(objList));
		}
		return returnList;
	}
	
	
	public ArrayList<Object> arrayMerge(ArrayList<JSONType> list){
		if (list.isEmpty()) {
			return null;
		}
		ArrayList<JSONType> returnList=new ArrayList<>();
		for (int i=0; i<list.size(); i++) {
			Iterator<String> keyList=list.get(i).getMap().keySet().iterator();
			while(keyList.hasNext()) {
				returnList.add(list.get(i).getMap().get(keyList.next()));
			}
		}
		
		return merge(returnList);
	}

	
	public HashMap<String, ArrayList<Object>> ObjectMerge(ArrayList<JSONType> objList) {
		if (objList.isEmpty()) {
			return null;
		}
		ArrayList<String> keys=new ArrayList<>();
		for (int i=0; i<objList.size(); i++) {
			HashMap<String, JSONType> map=objList.get(i).getMap();
			Iterator<String> keyList=map.keySet().iterator();
			while(keyList.hasNext()) {
				String key=keyList.next();
				if (keys.contains(key)==false) {
					keys.add(key);
				}
			}
			
		}
		HashMap<String, ArrayList<JSONType>> arrayMap=new HashMap<>();
		for (int i=0; i<keys.size(); i++) {
			arrayMap.put(keys.get(i), new ArrayList<JSONType>());
			for (int j=0; j<objList.size(); j++) {
				HashMap<String, JSONType> map=objList.get(j).getMap();
				if (map.containsKey(keys.get(i))){
					arrayMap.get(keys.get(i)).add(map.get(keys.get(i)));
				}
			}
		}
		
		HashMap<String, ArrayList<Object>> returnMap=new HashMap<>();
		for (int i=0; i<keys.size(); i++) {
			//System.out.println(arrayMap.get(keys.get(i)).size());
			
			//System.out.println(keys.get(i));
			ArrayList<Object> list=merge(arrayMap.get(keys.get(i)));
			returnMap.put(keys.get(i), list);
		}
		//System.out.println("Printed");
		
		return returnMap;
	}
}
