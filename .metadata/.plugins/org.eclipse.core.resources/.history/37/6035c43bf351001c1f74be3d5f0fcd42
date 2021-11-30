

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Display {



static void PrintData(ArrayList<MergeType> list) {
	for (int i=0; i<list.size(); i++) {
		MergeType merge=list.get(i);
		
		if (merge.getType().equals("array")) {
			System.out.print("[");
			PrintData(merge.getArray());
			System.out.print("]");
		}else if (merge.getType().equals("object")) {
			System.out.print("{");
			HashMap<String, ArrayList<MergeType>> map=list.get(i).getMap();
			Iterator<String> iterate=map.keySet().iterator();
			while(iterate.hasNext()) {
				
				String key=iterate.next();
				if (merge.getOptional().contains(key)) {
					System.out.print(key+"?: ");
				}else {
				System.out.print(key+": ");
				
				}
				PrintData(map.get(key));
				System.out.print(", ");
			
				
			}
			System.out.print("}");
			
			ArrayList<String> optional=merge.getOptional();
//			System.out.print("Optional: ");
//			for (int j=0; j<optional.size(); j++) {
//				System.out.print(optional.get(j)+", ");
//			}
			//System.out.println("");
			//System.out.println("}");
		}else {
			System.out.print(list.get(i).getType()+" ");
			
		}
	}
}

static String getData(ArrayList<MergeType> list) {
	String str="";
	for (int i=0; i<list.size(); i++) {
		MergeType merge=list.get(i);
		
		if (merge.getType().equals("array")) {
			str=str+'[';
			str=str+getData(merge.getArray());
			str=str+']';
		}else if (merge.getType().equals("object")) {
			str=str+'{';
			HashMap<String, ArrayList<MergeType>> map=list.get(i).getMap();
			Iterator<String> iterate=map.keySet().iterator();
			while(iterate.hasNext()) {
				
				String key=iterate.next();
				if (merge.getOptional().contains(key)) {
					str=str+key+"?: ";
				}else {
				str=str+key+": ";
				
				}
				str=str+getData(map.get(key));
				str=str+", ";
			
				
			}
			str=str+'}';
			
			ArrayList<String> optional=merge.getOptional();
//			System.out.print("Optional: ");
//			for (int j=0; j<optional.size(); j++) {
//				System.out.print(optional.get(j)+", ");
//			}
			//System.out.println("");
			//System.out.println("}");
		}else {
			str=str+ list.get(i).getType()+" ";
			
		}
	}
	return str;
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
			returnNum= returnNum+1;
		}
		
	}
	return returnNum;
}
}
