package competition.cig.dungpham;

//import competition.cig.robinbaumgarten.astar.AStarSimulator;

import ch.idsia.ai.agents.Agent;
//import ch.idsia.ai.agents.Agent.AGENT_TYPE;
import ch.idsia.mario.engine.sprites.Mario;
import ch.idsia.mario.environments.Environment;

public class AstarAgent implements Agent 
{
	private boolean[] action = new boolean[Environment.numberOfButtons];
	private Simulator sim;
	private String name = "MARIO_A*";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void reset()
	{		
		action = new boolean[Environment.numberOfButtons];
		//action[Mario.KEY_JUMP] = true;
		//action[Mario.KEY_RIGHT] = true;
        sim = new Simulator();
	}

	@Override
	public boolean[] getAction(Environment observation) 
	{
		//long startTime = System.currentTimeMillis();
		// advance the simulator
		
		/*System.out.println("x = " + sim.world.mario.x);
        System.out.println("y = " + sim.world.mario.y);
        System.out.println("damage: " + sim.world.mario.damage);*/
        //System.out.println("sim.world.mario.x BEFORE: " + sim.world.mario.x);
		/*System.out.println("action IS: " + action[0]);
		System.out.println("action IS: " + action[1]);
		System.out.println("action IS: " + action[2]);
		System.out.println("action IS: " + action[3]);
		System.out.println("action IS: " + action[4]);
		System.out.println("--------------------------------");*/
		//System.out.println("currentWorld.mario.x BEFORE: " + sim.currentWorld.mario.x);
		sim.runSimulator(action);
		//System.out.println("currentWorld.mario.x AFTER: " + sim.currentWorld.mario.x);
		//System.out.println("sim.world.mario.x AFTER: " + sim.world.mario.x);
		
		// get info from the environment
		byte[][] scene = observation.getLevelSceneObservation(0);
    	float[] enemies = observation.getEnemiesFloatPos();
		// update the simulator
    	sim.update(enemies, scene);
    	// return the optimal action
    	action = sim.optimalPath();
    	//System.out.println("openList.size() IS: " + sim.openList.size());
    	/*System.out.println("x = " + sim.world.mario.x);
        System.out.println("y = " + sim.world.mario.y);
        System.out.println("damage: " + sim.world.mario.damage);*/
        //System.out.println("---------------------------------------------------------------------------");
    	return action;
	}

	public AGENT_TYPE getType()
    {
        return Agent.AGENT_TYPE.AI;
    }

    public String getName() 
    {        
    	return name;    
    }

    public void setName(String Name) 
    { 
    	this.name = Name;    
    }

}
