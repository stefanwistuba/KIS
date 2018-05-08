package lineFollower;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public abstract class LineFollowerAbstract {
	private final static double alpha = 0.9;
	private final static double epsilon = 0.1;
	private final static double gamma = 0.2;
	
	private final static int amountOfActions = 3;
	private final static int amountOfStates = 8;
	
	protected final static int LEFT_CURVE = 0;
	protected final static int LINE_FOLLOW = 1;
	protected final static int RIGHT_CURVE = 2;
	
	private final static int amountOfQ = amountOfActions * amountOfStates;
	
	protected double[][] states = {{0, 0, 0, -0.3}, {0, 0, 1, 0.1}, {0, 1, 0, 1}, {0, 1, 1, 0.3}, {1, 0, 0, 0.1}, {1, 0, 1, -1}, {1, 1, 0, 0.3}, {1, 1, 1, -0.9}};
	
	protected static Q[] Qs;
	
	public LineFollowerAbstract() {
        this.Qs = this.initialize();
    }
	
	protected double getReward(double[] secondState) {
		for(double[] array : this.states) {
			if(array[0] == secondState[0] && array[1] == secondState[1] && array[2] == secondState[2]) {
				return array[3];
			}
		}
		return 0;
	}
	protected abstract double[] getState(int action);
	
	protected void execute() {
		int counter = 0;
		while(counter < 5000) {
			//get state first
			double [] firstState = this.getState(-1);
			for(double a : firstState) {
				System.out.print(a + "; ");
			}
			
			//get max reward for state
			Q maxReward = this.getMaxRewardQ(firstState);
			
			//execute action
			this.executeAction(maxReward.action);
			
			//get second state
			double [] secondState = this.getState(maxReward.action);
			for(double a : secondState) {
				System.out.print(a + "; ");
			}
			
			
			double r = this.getReward(secondState);
			Q maxReward2 = this.getMaxRewardQ(secondState);
			
			double newQ = maxReward.reward + this.alpha * (r + this.gamma * maxReward2.reward - maxReward.reward);
			
			System.out.println("OldReward: " + maxReward.reward + " newReward: " + newQ + "\n");
			
			maxReward.reward = newQ;		
			
			System.out.println("executed");
			
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			counter++;
		}
		System.out.println(this.getMaxRewardQ(new double[] {0,1,0}).reward);
		System.out.println(this.getMaxRewardQ(new double[] {1,0,0}).reward);
		System.out.println(this.getMaxRewardQ(new double[] {0,0,1}).reward);
	}
	
	protected abstract void executeAction(int action);
	
	
	private Q[] initialize() {
		int counter = 0;
		Q[] q = new Q[this.amountOfQ];
		for(double[] state : this.states) {
			for(int i = 0; i < this.amountOfActions; i++) {
				q[counter] = new Q(new double[] {(int) state[0], (int) state[1], (int) state[2]}, i, 0);
				counter = counter + 1;
			}
		}
		return q;
	}
	
	private Q getMaxRewardQ(double [] state) {
		Q[] filtered = new Q[3];
		int counter = 0;
		for(Q item : this.Qs) {
			if(Arrays.equals(state, item.state)) {
				filtered[counter] = item;
				counter = counter + 1;
			}
		}
		
		Q max = filtered[0];
		
		for(Q current : filtered) {
			if(current.reward >= max.reward) {
				max = current;
			}
		}
		
		return max;
	}
}
