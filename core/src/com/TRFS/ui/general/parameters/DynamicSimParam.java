package com.TRFS.ui.general.parameters;

/**
 * @author jgamboa
 *
 * A {@link DynamicSimParam} prepares a given variable to be manipulated by the user during simulation run. 
 * One can provide boundaries, a default value and the intended increment to be used by the slider.
 *
 */
public class DynamicSimParam {
	
	private String name;
	
	private float minimumVal,
				  maximumVal,
			   	  defaultVal,
			   	  incrementVal,
				  currentVal;
	
	private String decimalFormat;
	
	private String units;
	
	public DynamicSimParam (String name, float minimumVal,	float maximumVal, float defaultVal, float incrementVal, String decimalFormat, String units) {
		super();
		this.name = name;
		this.minimumVal = minimumVal;
		this.maximumVal = maximumVal;
		this.defaultVal = defaultVal;
		this.incrementVal = incrementVal;
		this.currentVal = defaultVal;
		this.decimalFormat = decimalFormat;
		this.units = units;
	}
	
	public void resetCurrent() {
		this.currentVal = defaultVal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(currentVal);
		result = prime * result + Float.floatToIntBits(defaultVal);
		result = prime * result + Float.floatToIntBits(incrementVal);
		result = prime * result + Float.floatToIntBits(maximumVal);
		result = prime * result + Float.floatToIntBits(minimumVal);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DynamicSimParam other = (DynamicSimParam) obj;
		if (Float.floatToIntBits(currentVal) != Float
				.floatToIntBits(other.currentVal))
			return false;
		if (Float.floatToIntBits(defaultVal) != Float
				.floatToIntBits(other.defaultVal))
			return false;
		if (Float.floatToIntBits(incrementVal) != Float
				.floatToIntBits(other.incrementVal))
			return false;
		if (Float.floatToIntBits(maximumVal) != Float
				.floatToIntBits(other.maximumVal))
			return false;
		if (Float.floatToIntBits(minimumVal) != Float
				.floatToIntBits(other.minimumVal))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DynamicSimParamBase [name=" + name + ", minimumVal="
				+ minimumVal + ", maximumVal=" + maximumVal + ", defaultVal="
				+ defaultVal + ", incrementVal=" + incrementVal
				+ ", currentVal=" + currentVal + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getMinimumVal() {
		return minimumVal;
	}

	public void setMinimumVal(float minimumVal) {
		this.minimumVal = minimumVal;
	}

	public float getMaximumVal() {
		return maximumVal;
	}

	public void setMaximumVal(float maximumVal) {
		this.maximumVal = maximumVal;
	}

	public float getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(float defaultVal) {
		this.defaultVal = defaultVal;
	}

	public float getIncrementVal() {
		return incrementVal;
	}

	public void setIncrementVal(float incrementVal) {
		this.incrementVal = incrementVal;
	}

	public float getCurrentVal() {
		return currentVal;
	}

	public void setCurrentVal(float currentVal) {
		this.currentVal = currentVal;
	}

	public String getDecimalFormat() {
		return decimalFormat;
	}

	public void setDecimalFormat(String decimalFormat) {
		this.decimalFormat = decimalFormat;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}
	
}
