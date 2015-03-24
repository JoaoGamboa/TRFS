
package com.TRFS.scenarios.json;

import java.util.List;

public class JSONFeatureCollection{
   	private List<JSONFeatures> features;
   	private String type;
   	private JSONCrs crs;

 	public List<JSONFeatures> getFeatures(){
		return this.features;
	}
	public void setFeatures(List<JSONFeatures> features){
		this.features = features;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
 	public JSONCrs getCrs(){
		return this.crs;
	}
	public void setJSONCrs(JSONCrs crs){
		this.crs = crs;
	}
}