import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONObject;

public class KMerge2 {
	ArrayList<JSONType> array;
	
	public KMerge2(ArrayList<JSONType> array) {
		this.array=array;
	}
	
	public ArrayList<MergeType> merge(ArrayList<JSONType> list){
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
		ArrayList<MergeType> returnList=new ArrayList<>();
		if (primList.isEmpty()==false) {
			returnList.add(new MergeType("primitive"));
		}
		if (arrList.isEmpty()==false) {
		returnList.add(arrayMerge(arrList));
		}
		if (objList.isEmpty()==false) {
		returnList.add(ObjectMerge(objList));
		}
		return returnList;
	}
	
	
	public MergeType arrayMerge(ArrayList<JSONType> list){
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
		
		MergeType returnType=new MergeType("array");
		returnType.SetArray(merge(returnList));
		return returnType;
	}

	
	public MergeType ObjectMerge(ArrayList<JSONType> objList) {
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
		
		HashMap<String, ArrayList<MergeType>> returnMap=new HashMap<>();
		for (int i=0; i<keys.size(); i++) {
			//System.out.println(arrayMap.get(keys.get(i)).size());
			
			//System.out.println(keys.get(i));
			ArrayList<MergeType> list=merge(arrayMap.get(keys.get(i)));
			returnMap.put(keys.get(i), list);
			
		}
		MergeType returnType=new MergeType("object");
		returnType.setMap(returnMap);
		Iterator<String> iterate=returnType.getMap().keySet().iterator();
		while(iterate.hasNext()) {
			String key=iterate.next();
			if (returnType.getMap().get(key).size()<objList.size()) {
				returnType.addOptional(key);
			}
		}
		//System.out.println("Printed");
		
		return returnType;
	}
}