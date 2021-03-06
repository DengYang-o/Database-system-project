
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class main {

	
	public static void main(String[] args) throws IOException, ParseException {
		System.out.println("K Merge results");
		CallK3Merge("nyt.json");
		System.out.println("L Merge results");
		CallL3Merge("nyt.json");
		
	}
	
	
	static void CallL3Merge(String filename) throws IOException, ParseException {
		
			String str3=null;
			Path fileName=Paths.get(filename);
			try{
				str3=new String(Files.readAllBytes(fileName));
			}catch (IOException e){
				System.out.println("Can't find file to open");
			}

			JSONParser parser1=new JSONParser();
			JSONArray array=(JSONArray) parser1.parse(str3);
			System.out.println("Number of records in sample: "+array.size());
			//ArrayList<MergeType> mergeArr=new ArrayList<>();

			
			ArrayList<MergeType> mergeArr=new ArrayList<>();
			int divide=1;
			if (array.size()>500) {
				divide=500;
			}
			for (int i=0; i<array.size()/divide; i++) {
				ArrayList<JSONType> tempArray=new ArrayList<>();
				for (int j=0; j<divide; j++) {
					//System.out.println(i*divide+j);
					if ((i*divide+j)>=array.size()) {
						break;
					}
					JSONMap map=new JSONMap(array.get(i*divide+j).toString());
					
					tempArray.add(map.createSchema());
				}
				mergeArr.addAll(new LMerge2(tempArray).kMerge());
			}
			//System.out.println(mergeArr.size());
			
			
//			System.out.println("Number of records in sample: "+arr.size());
//			
			LMerge3 merge=new LMerge3(mergeArr);
			ArrayList<MergeType> result=merge.kMerge();
			System.out.println("Results:");
			
//			
			System.out.println("");
			System.out.println("Result schema: "+Display.getData(result));
			double num=0.0;
			for (int i=0; i<array.size();i++) {
				ArrayList<JSONType> arr2=new ArrayList<>();
				arr2.add(new JSONMap(array.get(i).toString()).createSchema());
				LMerge2 m=new LMerge2(arr2);
				ArrayList<MergeType> r=m.kMerge();
				num=num+(Display.getSize(r));
			}
			System.out.println("Map average type size: "+num/array.size());
			System.out.println("Reduction final size:"+Display.getSize(result));
			
		}
	
	static void CallK2Merge(String filename) throws ParseException {
		ArrayList<JSONType> arrlist=new ArrayList<>();
		
		KMerge2 merge2=new KMerge2(arrlist);
		System.out.println(Display.getData(merge2.kMerge()));
		
		
		String str3=null;
		Path fileName=Paths.get(filename);
		try{
			str3=new String(Files.readAllBytes(fileName));
		}catch (IOException e){
			System.out.println("Can't find file to open");
		}

		JSONParser parser1=new JSONParser();
		JSONArray array=(JSONArray) parser1.parse(str3);
	;
		ArrayList<JSONType> arr=new ArrayList<>();
		for (int i=0; i<array.size();i++) {
			System.out.println(i);
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
	}
	
	static void CallK3Merge(String filename) throws IOException, ParseException {
	ArrayList<JSONType> arrlist=new ArrayList<>();
		
		KMerge2 merge2=new KMerge2(arrlist);
		System.out.println(Display.getData(merge2.kMerge()));
		
		
		String str3=null;
		Path fileName=Paths.get(filename);
		try{
			str3=new String(Files.readAllBytes(fileName));
		}catch (IOException e){
			System.out.println("Can't find file to open");
		}

		JSONParser parser1=new JSONParser();
		JSONArray array=(JSONArray) parser1.parse(str3);
		System.out.println("Number of records in sample: "+array.size());
		ArrayList<JSONType> arr=new ArrayList<>();
		//ArrayList<MergeType> mergeArr=new ArrayList<>();

		
		ArrayList<MergeType> mergeArr=new ArrayList<>();
		int divide=1;
		if (array.size()>500) {
			divide=500;
		}
		for (int i=0; i<array.size()/divide; i++) {
			ArrayList<JSONType> tempArray=new ArrayList<>();
			for (int j=0; j<divide; j++) {
				//System.out.println(i*divide+j);
				if ((i*divide+j)>=array.size()) {
					break;
				}
				JSONMap map=new JSONMap(array.get(i*divide+j).toString());
				
				tempArray.add(map.createSchema());
			}
			mergeArr.addAll(new KMerge2(tempArray).kMerge());
		}
		//System.out.println(mergeArr.size());
		
		
//		System.out.println("Number of records in sample: "+arr.size());
//		
		KMerge3 merge=new KMerge3(mergeArr);
		ArrayList<MergeType> result=merge.kMerge();
		System.out.println("Results:");
		
//		
		System.out.println("");
		System.out.println("Result schema: "+Display.getData(result));
		double num=0.0;
		for (int i=0; i<array.size();i++) {
			ArrayList<JSONType> arr2=new ArrayList<>();
			arr2.add(new JSONMap(array.get(i).toString()).createSchema());
			KMerge2 m=new KMerge2(arr2);
			ArrayList<MergeType> r=m.kMerge();
			num=num+(Display.getSize(r));
		}
		System.out.println("Map average type size: "+num/array.size());
		System.out.println("Reduction final size:"+Display.getSize(result));
		
	}
	
}
