import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;


public class JSONType {
	private String type;
	private HashMap<String, JSONType> map;
	private ArrayList<String> optional;
	
	public JSONType(String type) {
		this.type=type;
		map=new HashMap<String, JSONType>();
		optional=new ArrayList<>();
		
	}
	public String getType() {
		return type;
	}
	public JSONType getJSONType(String string) {
		return map.get(string);
	}
	public void addJSONType(String string, JSONType type) {
		this.map.put(string, type);
	}
	public void addOptional(String string) {
		this.optional.add(string);
	}
	
	public String printJSON() {
		String returnType="{\"type\": "+this.type;
		String returnProperties="\"properties\": {\n";
		Iterator<String> it=this.map.keySet().iterator();
		while(it.hasNext()) {
			String key=it.next();
			returnProperties=returnProperties+"\""+key+"\""+": "+this.map.get(key).printJSON();
		}
		
		return returnType+'\n'+returnProperties+"}"+'\n'+"}";
	}
}
