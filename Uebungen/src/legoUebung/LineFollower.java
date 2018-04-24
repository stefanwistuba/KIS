package legoUebung;

import java.util.ArrayList;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;

public class LineFollower {
	
	private ArrayList<Double> q = new ArrayList<>();
	
	private final static double alpha = 0.9;
	private final static double epsilon = 0.1;
	private final static double gamma = 0.2;
	
	private final static int amountOfActions = 3;
	private final static int amountOfStates = 8;
	
	private final static int LEFT_CURVE = 0;
	private final static int LINE_FOLLOW = 1;
	private final static int RIGHT_CURVE = 2;
	
	private EV3ColorSensor leftColorSensor;
	private EV3ColorSensor middleColorSensor;
	private EV3ColorSensor rightColorSensor;
	
	private int colorSensorSampleSize;
	
	private final static int amountOfQ = amountOfActions * amountOfStates;
	
	public void follow() {
		while (true) {
			int state = this.simGetState();
			int action = 1;
			
			double max = Double.MIN_VALUE;
			
			if (Math.random() >= this.epsilon) {
				for (int i = 0; i < this.amountOfActions; i++) {
					for (int j = 0; j < this.amountOfStates; j++) {
						double qValue = this.q.get(i * this.amountOfStates + j);
						
						if (qValue > max) {
							max = qValue;
							action = i;
						}
					}
				}
			} else { // Random Action
				action = (int) (Math.random() * 3);
			}
			
			//this.execute(action);
			this.simAction(action);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}     
			
			//int nextState = this.getState();
			int nextState = this.simGetState();
			
			//LCD.drawString("stat: " + nextState, 1, 1);
			
			int reward = this.getReward(nextState);
			//LCD.drawString("rew: " + reward, 1, 2);
			
			max = 0.0;
			
			// Search for new max from states
			for (int i = 0; i < this.amountOfActions; i++) {
				if (this.q.get(i * this.amountOfStates + nextState) > max) {
					max = this.q.get(i * this.amountOfStates + nextState);
				}
			}
			
			double oldQValue = this.q.get(action * this.amountOfStates + state);
			
			double qValue = oldQValue + this.alpha * (reward + this.gamma * max - oldQValue);
			
			this.q.set(action * 8 + state, qValue);
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Automatisch generierter Erfassungsblock
				e.printStackTrace();
			}
		}
	}
	
	public void initialize() {
		for (int i = 0; i < this.amountOfQ; i++) {
			q.add(i, Math.random());
		}
		
		/*this.leftColorSensor = new EV3ColorSensor(SensorPort.S3);
		this.middleColorSensor = new EV3ColorSensor(SensorPort.S2);
		this.rightColorSensor = new EV3ColorSensor(SensorPort.S1);
		
		this.colorSensorSampleSize = this.leftColorSensor.sampleSize();
		
		Motor.A.setSpeed(100);
		Motor.D.setSpeed(100);*/
	}
	
	private int simGetState() {
		int res = (int) (Math.random() * 8);
		System.out.println("Result: " + res);
		return res;
	}
	
	private void simAction(int action) {
		switch (action) {
		case LEFT_CURVE:
			System.out.println("Linkskurve".toUpperCase());
			break;
		case LINE_FOLLOW:
			System.out.println("GERADEAUS");
			break;
		case RIGHT_CURVE:
			System.out.println("RECHSTSKURVE");
			break;
		default:
			break;
		}
	}
	
	private int getState() {		
		float[] leftSample = new float[this.colorSensorSampleSize];
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
	
	private int getReward(int state) {
		int reward;
		switch (state) {
		case 0:
			reward = -50;
			break;
		case 1:
			reward = -15;
			break;
		case 2:
			reward = 50;
			break;
		case 3:
			reward = 15;
			break;
		case 4:
			reward = -15;
			break;
		case 5:
			reward = 0;
			break;
		case 6:
			reward = 15;
			break;
		case 7:
			reward = 15;
			break;
		default:
			reward = 0;
			break;
		}
		
		return reward;
	}
	
	private void execute(int action) {
		switch (action) {
		case LEFT_CURVE:
			Motor.A.forward();
			Motor.D.backward();
			break;
		case LINE_FOLLOW:
			Motor.A.forward();
			Motor.D.forward();
			break;
		case RIGHT_CURVE:
			Motor.A.backward();
			Motor.D.forward();
			break;
		default:
			break;
		}
	}
	
	public static void main(String[] args) {
		LineFollower lineFollower = new LineFollower();
		
		lineFollower.initialize();
		lineFollower.follow();
	}

}
