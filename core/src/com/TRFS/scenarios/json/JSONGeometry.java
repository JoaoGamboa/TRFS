
package com.TRFS.scenarios.json;

import java.util.List;

public class JSONGeometry{
   	private List<?> coordinates;
   	private String type;

 	public List<?> getCoordinates(){
		return this.coordinates;
	}
	public void setCoordinates(List<?> coordinates){
		this.coordinates = coordinates;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
}
