import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONObject;

public class KMerge3 {
	ArrayList<MergeType> array;
	
	public KMerge3(ArrayList<MergeType> array) {
		this.array=array;
	}
	
	public ArrayList<MergeType> kMerge() {
		return merge(this.array);
	}
	
	private ArrayList<MergeType> merge(ArrayList<MergeType> list){
		ArrayList<MergeType> objList=new ArrayList<>();
		ArrayList<MergeType> arrList=new ArrayList<>();
		ArrayList<MergeType> primList=new ArrayList<>();
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
		boolean containNum=false;
		boolean containString=false;
		boolean containBool=false;
		boolean containNull=false;
		for (int i=0; i<primList.size(); i++) {
			if (primList.get(i).getType().equals("num") && containNum==false) {
				returnList.add(new MergeType("num"));
				containNum=true;
			}
			else if (primList.get(i).getType().equals("bool") && containBool==false) {
				returnList.add(new MergeType("bool"));
				containBool=true;
			}else if (primList.get(i).getType().equals("string") && containString==false) {
				returnList.add(new MergeType("string"));
				containString=true;
			}else if (primList.get(i).getType().equals("null") && containNull==false) {
				returnList.add(new MergeType("null"));
				containNull=true;
			}
			
		}
		if (arrList.isEmpty()==false) {
		returnList.add(arrayMerge(arrList));
		}
		if (objList.isEmpty()==false) {
		returnList.add(ObjectMerge(objList));
		}
		return returnList;
	}
	
	
	public MergeType arrayMerge(ArrayList<MergeType> list){
		if (list.isEmpty()) {
			return null;
		}
		ArrayList<MergeType> returnList=new ArrayList<>();
		for (int i=0; i<list.size(); i++) {
			for (int j=0; j<list.get(i).getArray().size(); j++) {
				returnList.add(list.get(i).getArray().get(j));
			}
		}
		
		MergeType returnType=new MergeType("array");
		returnType.SetArray(merge(returnList));
		return returnType;
	}

	
	public MergeType ObjectMerge(ArrayList<MergeType> objList) {
		if (objList.isEmpty()) {
			return null;
		}
		ArrayList<String> keys=new ArrayList<>();
		for (int i=0; i<objList.size(); i++) {
			HashMap<String, ArrayList<MergeType>> map=objList.get(i).getMap();
			Iterator<String> keyList=map.keySet().iterator();
			while(keyList.hasNext()) {
				String key=keyList.next();
				if (keys.contains(key)==false) {
					keys.add(key);
				}
			}
			
		}
		HashMap<String, ArrayList<MergeType>> arrayMap=new HashMap<>();
		for (int i=0; i<keys.size(); i++) {
			arrayMap.put(keys.get(i), new ArrayList<MergeType>());
			for (int j=0; j<objList.size(); j++) {
				HashMap<String, ArrayList<MergeType>> map=objList.get(j).getMap();
				if (map.containsKey(keys.get(i))){
					arrayMap.get(keys.get(i)).addAll(map.get(keys.get(i)));
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
			for (int i=0; i<objList.size(); i++) {
				if (objList.get(i).getMap().containsKey(key)==false || objList.get(i).getOptional().contains(key)) {
					returnType.addOptional(key);
					break;
				}
			}
		}
		//System.out.println("Printed");
		
		return returnType;
	}
}