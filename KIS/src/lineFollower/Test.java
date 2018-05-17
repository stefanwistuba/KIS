package lineFollower;

import java.util.Arrays;

public class Test extends LineFollowerAbstract{

	public static void main(String[] args) {
		LineFollowerAbstract follower = new Test();
		System.out.println(follower.Qs.length);
		
		follower.execute();

	}

	@Override
	protected int executeAction(int action) {
		System.out.println("Execute action: " + action);
		return 0;
	}

	@Override
	protected double[] getState(int action) {
		System.out.println("GetState-Action: " + action);
		if(action == -1 || action == 1) {
			return Arrays.copyOfRange(this.states[(int) (Math.random() * 8)], 0, 3);
		}
		else if(action == 0) {
			return Math.random() > 0.5 ? Arrays.copyOfRange(this.states[2], 0, 3) : Arrays.copyOfRange(this.states[6], 0, 3);
		}
		else if(action == 2) {
			return Math.random() > 0.5 ? Arrays.copyOfRange(this.states[2], 0, 3) : Arrays.copyOfRange(this.states[3], 0, 3);
		}
		return new double[] {0,0,0};
	}

}