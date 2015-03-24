
package com.TRFS.scenarios.json;

public class JSONFeatures{
   	private JSONGeometry geometry;
   	private JSONProperties properties;
   	private String type;

 	public JSONGeometry getGeometry(){
		return this.geometry;
	}
	public void setGeometry(JSONGeometry geometry){
		this.geometry = geometry;
	}
 	public JSONProperties getProperties(){
		return this.properties;
	}
	public void setProperties(JSONProperties properties){
		this.properties = properties;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
}
