import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;


public class MergeType {
	private String type;
	private HashMap<String, ArrayList<JSONType>> map;
	private ArrayList<String> optional;
	
	public MergeType(String type) {
		this.type=type;
		map=new HashMap<String, ArrayList<JSONType>>();
		optional=new ArrayList<>();
		
	}
	public String getType() {
		return type;
	}
	public ArrayList<JSONType> getJSONType(String string) {
		return map.get(string);
	}
	public void addJSONType(String string, ArrayList<JSONType> type) {
		this.map.put(string, type);
	}
	public void addOptional(String string) {
		this.optional.add(string);
	}
	public HashMap<String, ArrayList<JSONType>> getMap(){
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