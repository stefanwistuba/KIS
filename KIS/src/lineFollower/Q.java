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
    
    public String toString() {
    	String state = "";
    	for(int i = 0; i<this.state.length; i++) {
    		state+= this.state[i] + " ";
    	}
    	state += " " + "Act: " + this.action + " ";
    	return state;
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