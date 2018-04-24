package legoUebung;

public class Test123 {

	public static void main(String[] args) {
		int colorOfLeftColorSensor = 1;
		int colorOfMiddleColorSensor = 0;
		int colorOfRightColorSensor = 1;
		int a = (0 | colorOfLeftColorSensor << 2 | colorOfMiddleColorSensor << 1 | colorOfRightColorSensor);
		System.out.println(a);
	}

}
