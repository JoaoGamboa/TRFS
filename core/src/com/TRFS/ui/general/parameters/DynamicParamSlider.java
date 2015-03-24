package com.TRFS.ui.general.parameters;

import java.text.DecimalFormat;

import com.TRFS.simulator.AssetsMan;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class DynamicParamSlider extends Slider{
	
	private DynamicSimParam parameter;

	private Label label;
	
	private String decimalFormat;
	
	private String units;
	
	private static Skin skin = AssetsMan.uiSkin;
	
	private DecimalFormat df;
	
	public DynamicParamSlider(DynamicSimParam parameter) {
		super(parameter.getMinimumVal(), parameter.getMaximumVal(), parameter.getIncrementVal(), false, skin);
		
		this.parameter = parameter;
		this.decimalFormat = parameter.getDecimalFormat();
		this.units = parameter.getUnits();
		
		this.setValue(parameter.getCurrentVal());
		
		df = new DecimalFormat(decimalFormat);
		String parameterFormated = df.format(parameter.getCurrentVal());
		
		this.label = new Label(String.format(parameter.getName()+": " + parameterFormated + " " + units, parameter.getCurrentVal()), skin, "smallLabel");
		
		addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				getParameter().setCurrentVal(getValue());
				String param = df.format(getValue());
				getLabel().setText(String.format(getParameter().getName() +": " + param + " " + getUnits(),getValue()));
			}
		});

		addListener( new InputListener() {
		   public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		      event.stop();
		      return false;
		   }
		});
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((parameter == null) ? 0 : parameter.hashCode());
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
		DynamicParamSlider other = (DynamicParamSlider) obj;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		return true;
	}

	public DynamicSimParam getParameter() {
		return parameter;
	}

	public void setParameter(DynamicSimParam parameter) {
		this.parameter = parameter;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}
}
