
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
		Boolean useParametric=false;
		if (!useParametric) {
		ArrayList<JSONType> arrlist=new ArrayList<>();
		
		KMerge2 merge2=new KMerge2(arrlist);
		System.out.println(Display.getData(merge2.kMerge()));
		
		
		Path fileName=Path.of("nyt.json");
		String str3=Files.readString(fileName);

		JSONParser parser1=new JSONParser();
		JSONArray array=(JSONArray) parser1.parse(str3);
	;
		ArrayList<JSONType> arr=new ArrayList<>();
		for (int i=0; i<array.size();i++) {
			JSONMap map=new JSONMap(array.get(i).toString());
			arr.add(map.createSchema());
			
		}
		System.out.println("Number of records in sample: "+arr.size());
		
		KMerge2 merge=new KMerge2(arr);
		ArrayList<MergeType> result=merge.kMerge();
		System.out.println("Results:");
		
		
		System.out.println("");
		System.out.println("Result schema: "+Display.getData(result));
		double num=0.0;
		for (int i=0; i<arr.size();i++) {
			ArrayList<JSONType> arr2=new ArrayList<>();
			arr2.add(arr.get(i));
			KMerge2 m=new KMerge2(arr2);
			ArrayList<MergeType> r=m.kMerge();
			num=num+(Display.getSize(r));
		}
		System.out.println("Map average type size: "+num/arr.size());
		System.out.println("Reduction final size:"+Display.getSize(result));
		
		
		}else {
		
	ArrayList<JSONType> arrlist=new ArrayList<>();
		
		KMerge2 merge2=new KMerge2(arrlist);
		System.out.println(Display.getData(merge2.kMerge()));
		
		
		Path fileName=Path.of("nyt.json");
		String str3=Files.readString(fileName);

		JSONParser parser1=new JSONParser();
		JSONArray array=(JSONArray) parser1.parse(str3);
	;
		ArrayList<JSONType> arr=new ArrayList<>();
		//ArrayList<MergeType> mergeArr=new ArrayList<>();
		for (int i=0; i<array.size();i++) {
			JSONMap map=new JSONMap(array.get(i).toString());
			arr.add(map.createSchema());
			ArrayList<JSONType> jsonArr=new ArrayList<>();
			jsonArr.add(arr.get(i));
			
			ArrayList<MergeType> m=new KMerge2(jsonArr).kMerge();
		//	mergeArr.addAll(m);
			
			
		}
		
		ArrayList<MergeType> mergeArr=new ArrayList<>();
		for (int i=0; i<array.size()/500; i++) {
			ArrayList<JSONType> tempArray=new ArrayList<>();
			for (int j=0; j<500; j++) {
				JSONMap map=new JSONMap(array.get(i*500+j).toString());
				tempArray.add(map.createSchema());
			}
			mergeArr.addAll(new KMerge2(tempArray).kMerge());
		}
		
		
//		System.out.println("Number of records in sample: "+arr.size());
//		
		KMerge3 merge=new KMerge3(mergeArr);
		ArrayList<MergeType> result=merge.kMerge();
		System.out.println("Results:");
		
//		
		System.out.println("");
		System.out.println("Result schema: "+Display.getData(result));
		double num=0.0;
		for (int i=0; i<arr.size();i++) {
			ArrayList<JSONType> arr2=new ArrayList<>();
			arr2.add(arr.get(i));
			KMerge2 m=new KMerge2(arr2);
			ArrayList<MergeType> r=m.kMerge();
			num=num+(Display.getSize(r));
		}
		System.out.println("Map average type size: "+num/arr.size());
		System.out.println("Reduction final size:"+Display.getSize(result));
		
		
    	
		}
	}
	
}
