package com.TRFS.simulator;

import java.util.ArrayList;

import com.TRFS.scenarios.map.MapPreview;
import com.TRFS.ui.general.parameters.DynamicSimParam;

/**
 * @author jgamboa
 * 
 * General simulation parameters, not related to behavior model parameters.
 * Every {@link DynamicSimParam} inside simParamListBase will show in the simulation parameters window during simulation run.
 *
 */
public class SimulationParameters {
	
//--GENERAL --------------------------------	
	
	//Static
	public static MapPreview currentMap;
	public static String currentCarFolModel;
	public static String currentLaneChangeModel;
	
	public static boolean allowHOV;
	public static boolean simplifyGeometry;
	public static boolean smoothGeometry;
	public static boolean drawDebug;
	
	//Dynamic															NAME				MIN			MAX		DEFAULT	INCREMENT	DecimalFormat
	public static DynamicSimParam simSpeed 		= new DynamicSimParam("Simulation Speed"	, 0, 		5, 		1, 		0.01f,	 	"0.00", "Times");
	public static DynamicSimParam iterations 	= new DynamicSimParam("Resolution"			, 1, 		5, 		1, 	    1, 			"0", 	"Iter/frame");
	public static DynamicSimParam desVelocity 	= new DynamicSimParam("Desired Velocity"	, 0, 		200, 	90, 	1,  		"###", "Km/h"		);
	public static DynamicSimParam maxVelocity 	= new DynamicSimParam("Max. Velocity"		, 0, 		200, 	120, 	1, 			"###", "Km/h"		);
	
	public static DynamicSimParam truckPercent 	= new DynamicSimParam("Truck Percentage"	, 0, 		100, 	10, 	1, 			"0.0", "Percent");
	public static DynamicSimParam desFlow 		= new DynamicSimParam("Desired Flow"		, 0, 		4000, 	2000, 	1, 			"####", "Veic/h");
	
	//Array for window
	public static DynamicSimParam[] simParamsListBase = {simSpeed, iterations, desVelocity, maxVelocity, truckPercent};
	public static ArrayList<DynamicSimParam> simParamsListFlows = new ArrayList<DynamicSimParam>();
	
	
}
