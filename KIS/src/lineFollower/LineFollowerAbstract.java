package lineFollower;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

public abstract class LineFollowerAbstract {
	private final static double alpha = 1.0;
	private static double epsilon = 0.1;
	private final static double gamma = 0.2;
	
	private final static int amountOfActions = 3;
	private final static int amountOfStates = 8;
	
	protected final static int LEFT_CURVE = 0;
	protected final static int LINE_FOLLOW = 1;
	protected final static int RIGHT_CURVE = 2;
	
	private final static int amountOfQ = amountOfActions * amountOfStates;
	
	protected double[][] states = {{0, 0, 0, -1}, {0, 0, 1, -0.4}, {0, 1, 0, 1}, {0, 1, 1, 0.4}, {1, 0, 0, -0.4}, {1, 0, 1, -1}, {1, 1, 0, 0.4}, {1, 1, 1, 0.5}};
	
	protected static Q[] Qs;
	
	public LineFollowerAbstract() {
        Qs = this.initialize();
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
		int count = 0;
		while(true) {
			//get state first
			double [] firstState = this.getState(-1);
//			for(double a : firstState) {
//				System.out.print(a + "; ");
//			}
			
			//get max reward for state
			Q maxReward = this.getMaxRewardQ(firstState);
			
			//execute action and save the executed action
			int actionToBeExec = maxReward.action;
			
			// Epsilon chance to execute another random action
			if(Math.random() < epsilon) {
				actionToBeExec = (int)(Math.random()*3);
				maxReward = this.findValueByStateAndAction(firstState, actionToBeExec);
			}
			
			
			// Execute Action
			this.executeAction(actionToBeExec);
			
			//get second state
			double [] secondState = this.getState(0);
			/*for(double a : secondState) {
				System.out.print(a + "; ");
			}*/
			
			double r = this.getReward(secondState);
			Q maxReward2 = this.getMaxRewardQ(secondState);
			
			// Calculation of new Q-Value
			double newQ = maxReward.reward + alpha * (r + (gamma * maxReward2.reward) - maxReward.reward);
			
			//System.out.println("OldReward: " + maxReward.reward + " newReward: " + newQ + "\n");
			
			maxReward.reward = newQ;		
			
			//System.out.println("executed");
			if (count % 1000 == 0 && epsilon > 0) {
				epsilon -= 0.01;
			}
			count++;
			
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected Q findValueByStateAndAction(double[] state, int action) {
		for(Q val : Qs) {
			if(Arrays.equals(val.state, state) && val.action == action) {
				return val;
			}
		}
		return null;
	}
	protected abstract int executeAction(int action);
	
	private Q[] initialize() {
		int counter = 0;
		Q[] q = new Q[amountOfQ];
		for(double[] state : this.states) {
			for(int i = 0; i < amountOfActions; i++) {
				q[counter] = new Q(new double[] {(int) state[0], (int) state[1], (int) state[2]}, i, 0);
				counter = counter + 1;
			}
		}
		
		List<Q> solution = new ArrayList<>();
		for (int i = 0; i < q.length; i++) {
		    solution.add(q[i]);
		}
		
		Collections.shuffle(solution);
		
		return solution.toArray(new Q[solution.size()]);
	}
	
	private Q getMaxRewardQ(double [] state) {
		Q[] filtered = new Q[3];
		int counter = 0;
		for(Q item : Qs) {
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