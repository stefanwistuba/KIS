package lineFollower;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;

public class LineFollower extends LineFollowerAbstract {
	
	protected EV3ColorSensor leftColorSensor;
	protected EV3ColorSensor middleColorSensor;
	protected EV3ColorSensor rightColorSensor;

	protected int colorSensorSampleSize;
	
	protected void initializeEngines() {
		this.leftColorSensor = new EV3ColorSensor(SensorPort.S1);
		this.middleColorSensor = new EV3ColorSensor(SensorPort.S2);
		this.rightColorSensor = new EV3ColorSensor(SensorPort.S3);
		
		this.colorSensorSampleSize = this.leftColorSensor.sampleSize();
		
		Motor.A.setSpeed(150);
		Motor.D.setSpeed(150);
	}

	@Override
	protected double[] getState(int action) {
		float[] leftSample = new float[this.colorSensorSampleSize];
		float[] middleSample = new float[this.colorSensorSampleSize];
		float[] rightSample = new float[this.colorSensorSampleSize];
		
		this.leftColorSensor.fetchSample(leftSample, 0);
		this.middleColorSensor.fetchSample(middleSample, 0);
		this.rightColorSensor.fetchSample(rightSample, 0);
		
		int colorOfLeftColorSensor = (int) leftSample[0];
		int colorOfMiddleColorSensor = (int) middleSample[0];
		int colorOfRightColorSensor = (int) rightSample[0];
		
		double[] colorValues = new double[3];
		
		if (colorOfLeftColorSensor == 7) {
			colorValues[0] = 1;
		} else {
			colorValues[0] = 0;
		}
		
		if (colorOfMiddleColorSensor == 7) {
			colorValues[1] = 1;
		} else {
			colorValues[1] = 0;
		}
		
		if (colorOfRightColorSensor == 7) {
			colorValues[2] = 1;
		} else {
			colorValues[2] = 0;
		}
		
		return colorValues;
		
		
	}

	@Override
	protected int executeAction(int action) {
		switch (action) {
		case LEFT_CURVE:
			Motor.A.forward();
			Motor.D.backward();
			return 0;
		case LINE_FOLLOW:
			Motor.A.forward();
			Motor.D.forward();
			return 1;
		case RIGHT_CURVE:
			Motor.A.backward();
			Motor.D.forward();
			return 2;
		default:
			return -1;
		}
	}
	
	public static void main(String[] args) {
		LineFollower lf = new LineFollower();
		
		lf.initializeEngines();
		lf.execute();
		
	}

}