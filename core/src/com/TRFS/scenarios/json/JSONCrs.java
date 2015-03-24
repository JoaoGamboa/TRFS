package com.TRFS.scenarios.json;

public class JSONCrs {
   	private JSONCrsProperties properties;
   	private String type;

 	public JSONCrsProperties getProperties(){
		return this.properties;
	}
	public void setProperties(JSONCrsProperties properties){
		this.properties = properties;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}

}
