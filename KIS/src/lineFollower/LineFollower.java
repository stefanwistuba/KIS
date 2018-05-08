package lineFollower;

public class LineFollower extends LineFollowerAbstract{

	@Override
	protected double[] getState(int action) {
		double[] leftSample = new double[this.colorSensorSampleSize];
		float[] middleSample = new float[this.colorSensorSampleSize];
		float[] rightSample = new float[this.colorSensorSampleSize];
		
		this.leftColorSensor.fetchSample(leftSample, 0);
		this.middleColorSensor.fetchSample(middleSample, 0);
		this.rightColorSensor.fetchSample(rightSample, 0);
		
		int colorOfLeftColorSensor = (int) leftSample[0];
		int colorOfMiddleColorSensor = (int) middleSample[0];
		int colorOfRightColorSensor = (int) rightSample[0];
		
		if (colorOfLeftColorSensor == 7) {
			colorOfLeftColorSensor = 1;
		} else {
			colorOfLeftColorSensor = 0;
		}
		
		if (colorOfMiddleColorSensor == 7) {
			colorOfMiddleColorSensor = 1;
		} else {
			colorOfMiddleColorSensor = 0;
		}
		
		if (colorOfRightColorSensor == 7) {
			colorOfRightColorSensor = 1;
		} else {
			colorOfRightColorSensor = 0;
		}
		
		return (0 | colorOfLeftColorSensor << 2 | colorOfMiddleColorSensor << 1 | colorOfRightColorSensor);
	}

	@Override
	protected void executeAction(int action) {
		// TODO Auto-generated method stub
		
	}

}
