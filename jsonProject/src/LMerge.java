import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import org.json.simple.JSONObject;
import java.util.ArrayList; // import the ArrayList class
import java.util.*;
public class LMerge {
	static String myMethod(String data ) {
//		System.out.println(data);
		String schema ="";

		data = handledata(data);

		String[]test = data.split(",");
//		System.out.println(data);
		for (int i = 0; i < test.length; i++) {
			  String[] sample = test[i].split(":");
			  if (sample.length>1 && getType(sample[1])=="obj"){
				  String [] tmp = Arrays.copyOfRange(test, i, test.length);
				  String obj = getObj(tmp);
				  i = i+findskip(obj);
				  schema = schema+sample[0]+":{"+myMethod(obj)+"},";
				  myMethod(obj);
			  }else {
			  schema = schema + sample[0]+":"+getType(sample[1])+",";
			  }
			}
        return schema.substring(0,schema.length()-1);
	}
	static int findskip(String data) {
		int count = 0;
		for (int i = 0; i < data.length(); i++) {
		    if (data.charAt(i) == 44) {
		        count++;
		    }
		}
		return count;
	}
	static String handledata(String data){
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i)==58) {
				if (data.charAt(i+1)==34) {
					for (int j = i+2; j < data.length(); j++) {
						if (data.charAt(j)==34 && data.charAt(j-1)!='\\') {
							data = data.substring(0, i+2) + data.substring(j);
							break;
						}
					}
				}
					
			}
			
		}
		if (data.charAt(0)=='{' && data.charAt(data.length()-1)=='}') {
			data=data.substring(1,data.length()-1);
		}
		return data;
	}
	static String getType(String data ) {
		String type = "";
		if (data.charAt(0)==34 || data.charAt(0)==39) {
			type = "str";
		}else if(data.charAt(0)=='[') {
			type="arr";
		}
		else if(data.charAt(0)==123) {
			type = "obj";
		}else if(data==" True" || data==" False" ) {
			type="boolen";
		}else if(data.equals("null") || data.equals(" null") ) {
			type="Null";
		}else {
			type="num";
		}
		return type;
	}
	static String getObj(String [] data) {
//		System.out.println(data[0]);
		String obj=data[0].split(":",2)[1];
		int count = 1;	
		int closeindex = 0;
		for (int i = 1; i < data.length; i++) {
			obj=obj+","+data[i];		
		}
		for (int i = 1; i < obj.length(); i++) {
	        char c = obj.charAt(i);        
	        if (c == '{') {
	            count++;
	        }
	        else if (c == '}') {
	            count--;
	            if (count == 0) {
	            	closeindex = i;
	            	break;
	            }
	        }		
		}
//		System.out.println("result is "+obj);
		return obj.substring(1,closeindex);		
	}
	static String getObj2(String [] data) {
//		System.out.println(data[0]);
		String obj=data[0].split(":",2)[1];
		int count = 1;	
		int closeindex = 0;
		for (int i = 1; i < data.length; i++) {
			obj=obj+","+data[i];		
		}
		for (int i = 1; i < obj.length(); i++) {
	        char c = obj.charAt(i);        
	        if (c == '{') {
	            count++;
	        }
	        else if (c == '}') {
	            count--;
	            if (count == 0) {
	            	closeindex = i;
	            	break;
	            }
	        }		
		}
//		System.out.println(obj.substring(1,closeindex));
		String addtional = obj.substring(closeindex);
//		System.out.println("add: "+addtional);
		int count2=0;
		if (addtional.length()!=1 && addtional.charAt(1)=='+')
			for (int i = 0; i < addtional.length(); i++) {
				closeindex++;
				char c = addtional.charAt(i);
//				System.out.println(c);
		        if (c==',') {
		        	return obj.substring(1,closeindex-1);
		        }
			
		}

		return obj.substring(1,closeindex);		
	}
//	static String getArr(String [] data) {
//		
//	}
	static String[] schemadiv(String a){
		String [] schemalist=a.split(" \\+ ");
		int count = 0;
		ArrayList<String> result = new ArrayList<String>();
		result.add(schemalist[0]);
		for(int i =0; i<schemalist.length-1;i++) {			
			String current=schemalist[i];
			for (int j =0; j<current.length();j++) {
				if(current.charAt(j)=='}') {
					count--;
				}else if(current.charAt(j)=='{') {
					count++;
				}
			}
			if (count == 0) {
				result.add(schemalist[i+1]);
			}else {
				String tmp=result.get(result.size() - 1);
				result.set(result.size()-1,tmp+" + "+schemalist[i+1]);
			}
		}
		String[] x = new String[result.size()];
		for(int i =0; i<result.size();i++) {
			x[i]=result.get(i);
		}
		return x;
	}
	static String merge(String a,String b) {
//		System.out.println("a is "+a);
//		System.out.println("b is "+b);
		String result = "";
		String [] schemalist=schemadiv(a);
		boolean matchonce = false;
		for(int i =0; i<schemalist.length;i++) {
			if (match(schemalist[i],b)) {
				String [] current = schemalist[i].split(",");
				String [] update = b.split(",");
				String afterupdate ="";
				for (int j=0; j<current.length;j++) {
					String newtype="";
					String key =current[j].split(":")[0];
					int k = 0;
					while (!update[k].split(":")[0].equals(key)){
//						System.out.println(update[k].split(":")[0].equals(key));
						k++;
					}
					if (current[j].split(":")[1].charAt(0)=='{') {
						String [] tmp1 = Arrays.copyOfRange(current, j, current.length);
						String first = getObj2(tmp1);
//						System.out.println("first is"+first);
						String add = "";
						String second = "";
						if (first.split("}+").length!=1) {
							
							add ="+"+first.split("}\\+",2)[1];
//							System.out.println("then is"+add);

						}else {
							first= getObj(tmp1);
						}
						if(update[k].split(":")[1].charAt(0)=='{') {
							String [] tmp2 = Arrays.copyOfRange(update, k, update.length);
							second = getObj(tmp2);
							
							afterupdate=afterupdate+","+current[j].split(":")[0]+":{"+merge(getObj(tmp1),second)+"}"+add;
//							System.out.println("is"+afterupdate);
						}else if (add.contains(update[k].split(":")[1])){
							afterupdate=afterupdate+","+current[j].split(":")[0]+":{"+getObj(tmp1)+"}"+add;
//							System.out.println("is"+afterupdate);
						}else {
							afterupdate=afterupdate+","+current[j].split(":")[0]+":{"+getObj(tmp1)+"}"+"+"+update[k].split(":")[1];
//							System.out.println("is"+afterupdate);
						}
//						System.out.println("t1"+getObj(tmp1));
//						System.out.println("t2"+getObj(tmp2));
						int count = 0;
						loop:
						while(true){
							for (int x=0; x<current[j].length();x++) {
								if(current[j].charAt(x)=='}') {
									count--;
								}else if(current[j].charAt(x)=='{') {
									count++;
								}
						    }
							j++;
							if(count==0) {
								j--;
								break loop;
							};
						}
					
					}else if(update[k].split(":")[1].charAt(0)=='{') {
						String [] tmp2 = Arrays.copyOfRange(update, k, update.length);
						afterupdate=afterupdate+","+current[j].split(":")[0]+":{"+getObj(tmp2)+"}"+"+"+current[j].split(":")[1];
//						System.out.println("is"+afterupdate);
					}
					else if (!current[j].split(":")[1].contains(update[k].split(":")[1])) {
						newtype = update[k].split(":")[1]+"+"+current[j].split(":")[1];
						afterupdate=afterupdate+","+current[j].split(":")[0]+":"+newtype;	
//						System.out.println("is"+afterupdate);
					}else {
						afterupdate=afterupdate+","+current[j];
//						System.out.println("is"+afterupdate);
					}
					
				}
				schemalist[i] =  afterupdate.substring(1);
				matchonce=true;
				break;
			}

		}
		if (matchonce) {
			result=reunionplus(schemalist);
		}else {
			result=a+" + "+b;
		}
        return result;
	}
	static String reunion(String[] data) {
		String result = "";
		for (int j=0; j<data.length;j++) {
			result=result+","+data[j];
		}
		return result.substring(1);
	}
	static String reunionplus(String[] data) {
		String result = "";
		for (int j=0; j<data.length;j++) {
			result=result+" + "+data[j];

		}
		return result.substring(3);
	}
	static ArrayList<String> findkey(String data){
//		System.out.println("data is: "+data);
		String [] current = data.split(",");
		ArrayList<String> currentlist = new ArrayList<String>();
		for (int j=0; j<current.length;j++) {
			currentlist.add(current[j].split(":")[0]);
//			System.out.println(current[j].split(":")[1]);
			if (current[j].split(":")[1].charAt(0)=='{') {
				int count=0;
				while(true){
					for (int k=0; k<current[j].length();k++) {
						if(current[j].charAt(k)=='}') {
							count--;
						}else if(current[j].charAt(k)=='{') {
							count++;
						}
				    }
					j++;
					if(count==0) {
						j--;
						break;
					};
//					System.out.println(current[j]);
				}
			}
		}
//		System.out.println(currentlist);
		return currentlist; 
	}
	static boolean match(String a,String b) {
//		System.out.println("a is"+a);
//		System.out.println("b is"+b);
		boolean result = false;
		ArrayList<String> currentlist = new ArrayList<String>();
		ArrayList<String> updatelist = new ArrayList<String>();
		currentlist = findkey(a);
		updatelist = findkey(b);
		Collections.sort(currentlist);
		Collections.sort(updatelist);
		result=currentlist.equals(updatelist);
		
		
		
		return result;
	}
	public static void main(String[] args) {
//		String str = "{ \"name\": \"Alice\", \"age\": 20 }";
//		JSONObject obj = new JSONObject();
//		String n = obj.toJSONString("name", n, null);
//		int a = obj.get("age");
//		System.out.println(n + " " + a);
		String schema = "";
	    try {
	        File myObj = new File("yelp_academic_dataset_tip.json");
	        Scanner myReader = new Scanner(myObj);
	        int x= 0;
	        String data = myReader.nextLine();
	        schema = myMethod(data);
//	        System.out.println(myMethod(data));
	        while (x<100) {
	          x++;
	          data = myReader.nextLine();
//	          System.out.println(x);
	          String test=merge(schema, myMethod(data));
	          
//	          System.out.println("test:"+test);
	          schema=test;
	        }
//	        System.out.println(schema);
	        int count = 0;
	        
	        for (int i = 0; i < schema.length(); i++) {
	            if (schema.charAt(i) == ':') {
	                count++;
	            }
	        }
	        System.out.println("schema number: "+count);
	        myReader.close();
	        String [] format = schema.split(" \\+ ");
	        for (int i = 0; i < format.length-1; i++) {
	        	System.out.println(format[i]+" + ");
	        }
	        System.out.println(format[format.length-1]);
	      } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	      }
	   
	}
}
