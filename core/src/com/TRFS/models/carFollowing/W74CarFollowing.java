package com.TRFS.models.carFollowing;

import java.util.Random;

import com.TRFS.ui.general.parameters.DynamicSimParam;
import com.TRFS.vehicles.Vehicle;
import com.TRFS.vehicles.Vehicle.VehicleConfig;

/**
 * @author jgamboa
 *
 */
public class W74CarFollowing extends CarFollowingModel {
	
	//COSNTANT OR GLOBAL PARAMETERS RELATED TO WIEDEMANN '74 CAR FOLLOWING MODEL. 
	//Calibration Constants												NAME		MIN		MAX		DEFAULT		INCREMENT	DecimalFormat
	public static DynamicSimParam	AXadd		= new DynamicSimParam("AXmult"		, 0f, 	10f, 	1.25f	,    0.05f, 	"0.00", ""  	),
									AXmult		= new DynamicSimParam("AXmult"		, 0f, 	10f, 	2f		,    0.05f, 	"0.00", ""  	),
								 	BXadd 		= new DynamicSimParam("BXadd"		, 0f, 	10f, 	2f		,    0.05f, 	"0.00", ""      ),
								 	BXmult 		= new DynamicSimParam("BXmult"		, 0f, 	10f, 	1f		,    0.05f, 	"0.00", ""      ),
								 	CXconst 	= new DynamicSimParam("CXconst"		, 0f, 	10f, 	5f		,    0.05f, 	"0.00", ""      ),
								 	CXadd 		= new DynamicSimParam("CXadd"		, 0f, 	10f, 	4f		,    0.05f, 	"0.00", ""      ),
								 	CXmult 		= new DynamicSimParam("CXmult"		, 0f, 	10f, 	3.6f	,    0.05f, 	"0.00", ""      ),
								 	EXadd 		= new DynamicSimParam("EXadd"		, 0f, 	10f, 	1.5f	,    0.05f, 	"0.00", ""      ),
								 	EXmult 		= new DynamicSimParam("EXmult"		, 0f, 	10f, 	0.55f	,    0.05f, 	"0.00", ""      ),
								 	OPDVadd 	= new DynamicSimParam("OPDVadd"		, 0f, 	10f, 	1.5f	,    0.05f, 	"0.00", ""      ),
								 	OPDVmult 	= new DynamicSimParam("OPDVmult"	, 0f, 	10f, 	1.5f	,    0.05f, 	"0.00", ""      ),
								 	BNULLmult 	= new DynamicSimParam("BNULLmult"	, 0f, 	10f, 	0.1f	,    0.05f, 	"0.00", ""      ),
								 	FAKTORVmult	= new DynamicSimParam("FAKTORVmult"	, 0f, 	10f, 	1f		,    0.05f, 	"0.00", ""      ),	
								 	BMAXmult 	= new DynamicSimParam("BMAXmult"	, 0f, 	10f, 	0.1f	,    0.05f, 	"0.00", ""      );
		
	public static DynamicSimParam[] calibrationParameters = {AXmult, BXadd, BXmult , CXconst, CXadd, CXmult, EXadd, EXmult, 
																	   	OPDVadd, OPDVmult, BNULLmult, FAKTORVmult, BMAXmult };
	
	// Wiedemann '74 Variables
	// Variable Parameters
	public float AX, BX, CX, EX, ABX, SDV, SDX, OPDV, FaktorV;
	private float NRND, RND1, RND2, RND3, desiredSpeedFactor, engineForceRatio, brakeForceRatio;

	private int state; // 1-Braking; 2-Approaching; 3-Following; 4-Free Flow
	
	public W74CarFollowing(Vehicle vehicle) {
		super(vehicle);
		
		this.NRND = (float) (new Random().nextGaussian() * 0.15f + 0.5f);
		this.RND1 = (float) (new Random().nextGaussian() * 0.15f + 0.5f);
		this.RND2 = (float) (new Random().nextGaussian() * 0.15f + 0.5f);
		this.RND3 = (float) (new Random().nextGaussian() * 0.15f + 0.5f);
		
		this.desiredSpeedFactor = (new Random().nextInt((150 - 70) + 1) + 70)/100f;
		this.engineForceRatio = vehicle.config.mass/VehicleConfig.engineForce;
		this.brakeForceRatio = vehicle.config.mass/VehicleConfig.brakeForce;
	}

	@Override
	public float update() {
		float w74Acceleration = 0;
		
		float Vn = vehicle.physics.speed;
		float maxSpeed = vehicle.behavior.pathFollowing.state.currentLink.maxspeed;
		float desiredSpeed = vehicle.behavior.pathFollowing.state.currentLink.maxspeed * desiredSpeedFactor;
		
		if (leader != null) {
			dX = leader.physics.position.dst(vehicle.physics.position);
			dV = leader.physics.speed - vehicle.physics.speed;
			float length1 = leader.config.length;
			float Vn1 = leader.physics.speed;
			float an1 = leader.physics.acceleration;
			
			AX = length1 + AXadd.getCurrentVal() + RND1 * AXmult.getCurrentVal();
			BX = (float) ((BXadd.getCurrentVal() + BXmult.getCurrentVal() * RND1) * Math
					.sqrt(dV > 0 ? Vn : Vn1));
			ABX = AX + BX;
	
			if (dX <= ABX) {
				state = 1;
			} else {
				EX = EXadd.getCurrentVal() + EXmult.getCurrentVal() * (NRND - RND2);
				SDX = AX + EX * BX;
	
				CX = CXconst.getCurrentVal() * (CXadd.getCurrentVal() + CXmult.getCurrentVal() + (RND1 + RND2));
				SDV = (float) Math.pow(((dX - length1 - AX) / CX), 2);
	
				if (dX < SDX) {
					OPDV = SDV * (-OPDVadd.getCurrentVal() - OPDVmult.getCurrentVal() * NRND);
	
					if (dV > SDV) {
						state = 2;
					} else if (dV > OPDV) {
						state = 3;
					} else {
						state = 4;
					}
				} else if (dX < SDV && dX < 50 * Math.sqrt(dV)) {
					state = 2;
				} else {
					state = 4;
				}
			}
						
			switch (state) {
			case 1:
				w74Acceleration = (float) ((0.5 * (Math.pow(dV, 2) / (ABX - dX - length1)))
						+ an1 + (-20 + 1.5 * Vn / 60) * ((ABX - dX - length1) / BX));
			case 2:
				w74Acceleration = (float) Math.min(0.5	* (Math.pow(dV, 2) / (ABX - dX - length1)) + an1, (-20 + 1.5 * Vn / 60));
			case 3:
				w74Acceleration = dV > OPDV ? 1 : -1 * BNULLmult.getCurrentVal() * (RND3 + NRND);
			case 4:
				FaktorV = maxSpeed / ((desiredSpeed) + FAKTORVmult.getCurrentVal() * (maxSpeed - desiredSpeed));
				w74Acceleration = BMAXmult.getCurrentVal() * (maxSpeed - Vn * FaktorV);
			}
		} else {			
			FaktorV = maxSpeed / ((desiredSpeed) + FAKTORVmult.getCurrentVal() * (maxSpeed - desiredSpeed));
			w74Acceleration = BMAXmult.getCurrentVal() * (maxSpeed - Vn * FaktorV);
			dX = dV = 0;
		}
		
		//W74 returns an acceleration value but our Vehicle's take a throttle percentage as input, so a conversion must be made. (F ratio = mass * acceleration / maxF)		
		return w74Acceleration > 0 ? w74Acceleration*engineForceRatio : w74Acceleration * brakeForceRatio;
	}

}


