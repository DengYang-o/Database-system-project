import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;


public class MergeType {
	private String type;
	private HashMap<String, ArrayList<MergeType>> map;
	private ArrayList<MergeType> arr;
	private ArrayList<String> optional;
	
	public MergeType(String type) {
		this.type=type;
		this.map=new HashMap<String, ArrayList<MergeType>>();
		this.arr= new ArrayList<>();
		
		optional=new ArrayList<>();
		
	}
	
	public ArrayList<String> getOptional(){
		return this.optional;
	}
	
	public void SetArray(ArrayList<MergeType> arr) {
		this.arr=arr;
	}
	public void setMap(HashMap<String, ArrayList<MergeType>> map) {
		this.map=map;
	}
	public String getType() {
		return type;
	}
	
	public ArrayList<MergeType> getArray(){
		return this.arr;
	}
	
	public ArrayList<MergeType> getJSONType(String string) {
		return map.get(string);
	}
	public void addJSONType(String string, ArrayList<MergeType> type) {
		this.map.put(string, type);
	}
	public void addOptional(String string) {
		this.optional.add(string);
	}
	public HashMap<String, ArrayList<MergeType>> getMap(){
		return this.map;
	}
	
	public String printJSON() {
		String returnType="{\"type\": "+this.type;
		String returnProperties="\"properties\": {\n";
		Iterator<String> it=this.map.keySet().iterator();
		while(it.hasNext()) {
			String key=it.next();
			returnProperties=returnProperties+"\""+key+"\""+": {";
			for (int i=0; i<this.map.get(key).size(); i++) {
				returnProperties=returnProperties+this.map.get(key).get(i).printJSON()+", ";
			}
		}
		
		return returnType+'\n'+returnProperties+"}"+'\n'+"}";
	}
}