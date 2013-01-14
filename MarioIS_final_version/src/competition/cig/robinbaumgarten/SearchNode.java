package competition.cig.robinbaumgarten;
/*package competition.cig.dungpham;

import java.util.ArrayList;

import competition.cig.robinbaumgarten.astar.LevelScene;

//import competition.cig.robinbaumgarten.astar.AStarSimulator.SearchNode;

//import ch.idsia.mario.engine.LevelScene;
//import ch.idsia.mario.engine.sprites.Mario;


public class SearchNode 
{
	// class data
	SearchNode parent; // useful for tracing back 
    LevelScene worldScene; // current world state at this node

	float costSoFar; // the cost so far to reach this node	
	float heuristicCost; // the estimated cost of this node to reach goal
    float totalCost; // the total A* cost of this node

	boolean[] action; // a series of action to be taken by Mario
	boolean isVisited = false; // whether the node has been visited or not
	boolean isReality = false; // whether this node is in reality or not
    //int ticks; // introduced since child's currentCost is not the same as parent's currentCost 
	
	float maxSpeed = 10.09f;// constant maximum speed 
	float damageCost = 10000.09f;
	float isInVisited = 1000.09f;
	
	// helper functions
	public SearchNode(SearchNode parent, boolean[] action)
	{
		// constructor
		this.parent = parent;
		this.action = action;
		
		if (parent!=null)
		{
		    this.costSoFar = parent.costSoFar + simulateAction(); // 
		    this.heuristicCost = computeHeuristicCost(Simulator.world.mario.x, worldSceneSimulator.world.mario.xa); // from here, what is the heuristic cost to reach goal
		    //Simulator
		}
		else
		{
		    this.costSoFar = 0;
		    //System.out.println("worldScene is: " + worldScene);
		    this.heuristicCost = computeHeuristicCost(worldSceneSimulator.world.mario.x, 0);
		}
		this.totalCost = this.costSoFar + this.heuristicCost;
	}
	
	public float computeHeuristicCost(float position, float speed)
	{
		// compute the heuristic time from this position to a distant target
		// basically evaluating which move is better than another in terms of time		
		float largeDistance = 20000.0f;
		return (largeDistance-position)/maxSpeed;
	}
		
    public ArrayList<SearchNode> generateChildren()
	{
		// generate all children nodes from this node
	    ArrayList<SearchNode> childNodes = new ArrayList<SearchNode>();
	    ArrayList<boolean[]> allActions = Utils.createPossibleActions(this);
	    
	    for (boolean[] action:allActions)
	    {
	    	childNodes.add(new SearchNode(this, action));
	    }
		return childNodes;
	}
    
    // simulate the action to reach this node and evaluate the destination
    public float simulateAction()
    {	
    	
    	
    	 // holding all actions from a node to where reality is
    	ArrayList<boolean[]> allActions = new ArrayList<boolean[]>();
    	// this node is where the reality is, initialized to the current node
    	SearchNode realityNode = this;
    	System.out.println("realityNode.action: " + realityNode.action[4]);
    	//System.out.println("realityNode is: " + realityNode.parent.isReality);
    	// create node for simulation
    	SearchNode simulationNode = new SearchNode(null, null);
    	while (!realityNode.isReality)
    	{
    		allActions.add(realityNode.action);
    		realityNode = realityNode.parent;    	
    		//System.out.println("realityNode: " + realityNode);
    	} // after the while loop, allActions holds all action to move from reality to a to-be-evaluated node
    	
    	System.out.println("allActions has length: " + allActions.size());
    	
    	// get the initial damage from the node where reality is
    	System.out.println("realityNode: " + realityNode);
    	System.out.println("realityNode.worldScene: " + realityNode.worldScene);
    	int initialDamage = realityNode.worldScene.mario.damage;
    	
    	// now running the simulator from where it is residing
    	simulationNode = realityNode;
    	// run the simulator that many time (equal to the size of allActions[])
    	
    	System.out.println("realityNode BEFORE: " + realityNode);
    	System.out.println("simulationNode BEFORE: " + simulationNode);
    	System.out.println("realityNode.worldScene.mario.x BEFORE: " + realityNode.worldScene.mario.x);
    	System.out.println("simulationNode.worldScene.mario.x BEFORE: " + simulationNode.worldScene.mario.x);
    	
    	System.out.println("realityNode.worldScene BEFORE: " + realityNode.worldScene);
    	System.out.println("simulationNode.worldScene BEFORE: " + simulationNode.worldScene);
    	
    	for(int i = allActions.size()-1;i>=0;i--)
    	{
    		simulationNode.worldScene.mario.setKeys(allActions.get(i));
    		simulationNode.worldScene.tick();
    	}    	    	
    	
    	System.out.println("realityNode.worldScene AFTER: " + realityNode.worldScene);
    	System.out.println("simulationNode.worldScene AFTER: " + simulationNode.worldScene);
    	
    	System.out.println("realityNode AFTER: " + realityNode);
    	System.out.println("simulationNode AFTER: " + simulationNode);
    	System.out.println("realityNode.worldScene.mario.x AFTER: " + realityNode.worldScene.mario.x);
    	System.out.println("simulationNode.worldScene.mario.x AFTER: " + simulationNode.worldScene.mario.x);
    	    	    	    
    	
    	System.out.println("Simulator.world INITIAL: " + Simulator.world);
    	System.out.println("this.parent.worldScene INITIAL: " + this.parent.worldScene);
    	
    	Simulator.world = this.parent.worldScene;
    	this.parent.worldScene = Simulator.backupState();
    	
    	System.out.println("Simulator.world BEFORE: " + Simulator.world);
    	System.out.println("this.parent.worldScene BEFORE: " + this.parent.worldScene);
    	System.out.println("this.worldScene BEFORE: " + this.worldScene);
    	
    	System.out.println("Simulator.world.mario.x BEFORE: " + Simulator.world.mario.x);
    	int initialDamage = Simulator.world.mario.damage; // before running the simulator
    	Simulator.runSimulator(this.action);
    	System.out.println("Simulator.world AFTER: " + Simulator.world);
    	
    	// evaluate the destination, add penalty if being hurt or being visited before
    	//float timePassed = Math.abs(simulationNode.worldScene.mario.x - realityNode.worldScene.mario.x)/maxSpeed;
    	
    	System.out.println("Simulator.world.mario.x AFTER: " + Simulator.world.mario.x);
    	System.out.println("this.parent.worldScene.mario.x: " + this.parent.worldScene.mario.x);
    	
    	float timePassed = Math.abs(Simulator.world.mario.x - this.parent.worldScene.mario.x)/maxSpeed;
    	float penalty = (Simulator.world.mario.damage - initialDamage) * damageCost;
    	float cost = this.parent.costSoFar + timePassed + penalty;
    	if (isVisited)
    		cost = cost + isInVisited;
    	
    	worldScene = Simulator.backupState();
    	System.out.println("this.parent.worldScene AFTER: " + this.parent.worldScene);
    	System.out.println("this.worldScene AFTER: " + this.worldScene);
    	
    	System.out.println("cost is: " + cost);
    	System.out.println("---------------------------------------------------------------------------");
       	return cost;
    }
		
}
*/