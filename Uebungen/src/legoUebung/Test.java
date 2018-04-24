package legoUebung;

import java.util.ArrayList;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import lejos.hardware.sensor.*;
import lejos.hardware.port.SensorPort;
import lejos.robotics.*;
import lejos.hardware.motor.Motor;

public class Test {

	public static void maien(String[] args) {
		EV3ColorSensor colorSensor1 = new EV3ColorSensor(SensorPort.S1);
		EV3ColorSensor colorSensor2 = new EV3ColorSensor(SensorPort.S4);
		EV3ColorSensor colorSensor3 = new EV3ColorSensor(SensorPort.S2);
		
		
		
		SampleProvider col1 = colorSensor1.getMode("ColorID");
		SampleProvider col2 = colorSensor2.getMode("ColorID");
		SampleProvider col3 = colorSensor3.getMode("ColorID");
		
		int sampleSize1 = colorSensor1.sampleSize();
		int sampleSize2 = colorSensor2.sampleSize();
		int sampleSize3 = colorSensor3.sampleSize();
		LCD.drawString(sampleSize1 + " - " + sampleSize2, 0, 0);
		
		float[] sample1 = new float[sampleSize1];
		float[] sample2 = new float[sampleSize2];
		float[] sample3 = new float[sampleSize3];
		
		int farbe1, farbe2, farbe3;
		
		int red1, red2, blue1, blue2, green1, green2;
		
		Motor.A.setSpeed(100);
		Motor.D.setSpeed(100);
		Motor.A.forward();
		Motor.D.forward();
		
		while(true) {
			
			
			col1.fetchSample(sample1, 0);
			col2.fetchSample(sample2, 0);
			col3.fetchSample(sample3, 0);
			farbe1 = (int) sample1[0];
			farbe2 = (int) sample2[0];
			farbe3 = (int) sample3[0];
			
			LCD.drawString("Farbe1: " + farbe1, 0, 1);
			LCD.drawString("Farbe2: " + farbe2, 0, 4);
			LCD.drawString("Farbe3: " + farbe3, 0, 6);
			
			
			if(farbe1 != 6) {
				Motor.A.backward();
				Motor.D.forward();
				continue;
			}
			
			if(farbe2 != 6) {
				Motor.D.backward();
				Motor.A.forward();
				continue;
			}
			
			Motor.A.forward();
			Motor.D.forward();
			
		
		}
	}
}
