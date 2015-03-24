
package com.TRFS.scenarios.json;

public class JSONProperties{
   	private int UID;
   	private int hierarchy;
   	private int inFlow;
   	private int lanes;
   	private int maxspeed;
   	private int oneway;
   	private int z;

 	public int getUID(){
		return this.UID;
	}
	public void setUID(int uID){
		this.UID = uID;
	}
 	public int getHierarchy(){
		return this.hierarchy;
	}
	public void setHierarchy(int hierarchy){
		this.hierarchy = hierarchy;
	}
 	public int getInFlow(){
		return this.inFlow;
	}
	public void setInFlow(int inFlow){
		this.inFlow = inFlow;
	}
 	public int getLanes(){
		return this.lanes;
	}
	public void setLanes(int lanes){
		this.lanes = lanes;
	}
 	public int getMaxspeed(){
		return this.maxspeed;
	}
	public void setMaxspeed(int maxspeed){
		this.maxspeed = maxspeed;
	}
 	public int getOneway(){
		return this.oneway;
	}
	public void setOneway(int oneway){
		this.oneway = oneway;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
}
