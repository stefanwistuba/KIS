package legoUebung;

import lejos.hardware.motor.Motor;

public class LeftCurve implements Action {

	@Override
	public void performe() {
		Motor.A.setSpeed(100);
		Motor.D.setSpeed(100);
		Motor.A.forward();
		Motor.D.backward();
	}

}
