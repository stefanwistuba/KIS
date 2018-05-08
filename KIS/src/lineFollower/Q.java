package lineFollower;

public class Q {
    public double[] state;
    public int action;
    public double reward;

    public Q(final double[] state, final int action, final double reward) {
        this.state = state;
        this.action = action;
        this.reward = reward;
    }
    
//    public int[] getState() {
//    	return this.state;
//    }
//    
//    public int getAction() {
//    	return this.action;
//    }
//    
//    public float getReward() {
//    	return this.reward;
//    }
	
}
