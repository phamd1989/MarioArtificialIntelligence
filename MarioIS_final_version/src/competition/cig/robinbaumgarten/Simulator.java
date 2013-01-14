package competition.cig.robinbaumgarten;

import java.util.ArrayList;
import competition.cig.robinbaumgarten.astar.LevelScene;
import competition.cig.robinbaumgarten.astar.level.Level;

public class Simulator 
{
	// private data
	public static LevelScene world = null;
	//private SearchNode realityNode;
	public static ArrayList<SearchNode> openList; 
	private ArrayList<SearchNode> closeList;
	
	
	/* try sample here	 */	
	public class SearchNode 
	{
		// class data
		SearchNode parent = null; // useful for tracing back 
	    LevelScene worldScene = null; // current world state at this node

		float costSoFar; // the cost so far to reach this node	
		float heuristicCost; // the estimated cost of this node to reach goal
	    float totalCost; // the total A* cost of this node

		boolean[] action; // a series of action to be taken by Mario
		//boolean isVisited = false; // whether the node has been visited or not
		//boolean isReality = false; // whether this node is in reality or not
	    //int ticks; // introduced since child's currentCost is not the same as parent's currentCost 
		
		float maxSpeed = 10.09f;// constant maximum speed 
		float damageCost = 100000.09f;
		//float isInVisited = 1000.09f;
		
		// helper functions
		public SearchNode(SearchNode parent, boolean[] action)
		{
			// constructor
			this.parent = parent;
			this.action = action;
			
			if (parent!=null)
			{
			    this.costSoFar = parent.costSoFar + simulateAction(); // 
			    this.heuristicCost = computeHeuristicCost(worldScene.mario.x, /*worldScene*/worldScene.mario.xa); // from here, what is the heuristic cost to reach goal
			    //Simulator
			}
			else
			{
			    this.costSoFar = 0;
			    //System.out.println("worldScene is: " + worldScene);
			    worldScene = new LevelScene();
				worldScene.init();
				worldScene.level = new Level(1500,15);
			    this.heuristicCost = computeHeuristicCost(/*worldScene*/worldScene.mario.x, 0);
			}
			this.totalCost = this.costSoFar + this.heuristicCost;
			//System.out.println("this.totalCost IS: " + this.totalCost);
		}
		
		public float computeHeuristicCost(float position, float speed)
		{
			// compute the heuristic time from this position to a distant target
			// basically evaluating which move is better than another in terms of time		
			float largeDistance = 10000.0f;
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
	    	
	    	/*
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
	    	*/    	    	    
	    	
	    	/*System.out.println("Simulator.world INITIAL: " + world);
	    	System.out.println("Simulator.world.mario.x INITIAL: " + world.mario.x);
	    	System.out.println("this.parent.worldScene INITIAL: " + this.parent.worldScene);
	    	System.out.println("this.parent.worldScene.mario.x INITIAL: " + this.parent.worldScene.mario.x);*/
	    	
	    	/*world = this.parent.worldScene;
	    	this.parent.worldScene = backupState();*/
	    	
	    	/*System.out.println("Simulator.world BEFORE: " + world);
	    	System.out.println("this.parent.worldScene BEFORE: " + this.parent.worldScene);
	    	System.out.println("this.worldScene BEFORE: " + this.worldScene);*/
	    	
	    	/*System.out.println("Simulator.world.mario.x BEFORE: " + world.mario.x);
	    	for (int i=0;i<this.action.length; i++)
	    	{
	    		System.out.println("this.action[i]: " + this.action[i]);
	    	}*/
	    	
	    	LevelScene newLevel = new LevelScene();
	    	newLevel.init();
	    	newLevel.level = new Level(1500,15);
	    	
	    	/*System.out.println("world.mario.x BEFORE: " + world.mario.x);
	    	System.out.println("newLevel.mario.x BEFORE: " + newLevel.mario.x);
	    	System.out.println("this.parent.worldScene.mario.x BEFORE: " + this.parent.worldScene.mario.x);*/
	    	
	    	//int initialDamage = this.parent.worldScene.mario.damage; // damage at the parent
	    	ArrayList<boolean[]> path = new ArrayList<boolean[]>();
	    	SearchNode runner = this;
	    	while(runner.action != null)
	    	{	
	    		path.add(runner.action);
	    		runner = runner.parent;
	    	}
	    	
	    	for (int i=path.size()-2;i>=0;i--)
	    	{
	    		//System.out.println("path.size() = " + path.size());
	    		newLevel.mario.setKeys(path.get(i));
	    		newLevel.tick();
	    		//world.mario.setKeys(path.get(i));
	    		//world.tick();
	    	}
	    	
	    	/*System.out.println("world.mario.x AFTER: " + world.mario.x);
	    	System.out.println("newLevel.mario.x AFTER: " + newLevel.mario.x);
	    	System.out.println("this.parent.worldScene.mario.x AFTER: " + this.parent.worldScene.mario.x);*/
	    	
	    	
	    	//runSimulator(this.action);
	    	/*System.out.println("this.parent.worldScene.mario.x BEFORE: " + this.parent.worldScene.mario.x);
	    	System.out.println("world.mario.x BEFORE: " + world.mario.x);
	    	world.mario.setKeys(action);
	    	world.tick();
	    	System.out.println("world.mario.x AFTER: " + world.mario.x);*/
	    	//System.out.println("Simulator.world AFTER: " + world);
	    	
	    	// evaluate the destination, add penalty if being hurt or being visited before
	    	//float timePassed = Math.abs(simulationNode.worldScene.mario.x - realityNode.worldScene.mario.x)/maxSpeed;
	    	
	    	/*System.out.println("Simulator.world.mario.x AFTER: " + world.mario.x);
	    	System.out.println("this.parent.worldScene.mario.x: " + this.parent.worldScene.mario.x);*/
	    	
	    	
	    	float timePassed = Math.abs(newLevel.mario.x - this.parent.worldScene.mario.x)/maxSpeed;
	    	//System.out.println("timePassed IS: " + timePassed);
	    	float penalty = 0;
	    	if (newLevel.mario.damage != 0)
	    	{
	    		penalty = damageCost;	    		
	    	}
	    	//System.out.println("penalty IS: " + penalty);
	    	float cost = timePassed + penalty;
	    	/*if (isVisited)
	    		cost = cost + isInVisited;*/
	    	
	    	//newLevel = backupState();
	    	
	    	try 
	    	{
				worldScene = (LevelScene) newLevel.clone();
				//world = (LevelScene) newLevel.clone();
			} 
	    	catch (CloneNotSupportedException e) 
	    	{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	//worldScene = backupState();
	    	
	    	/*System.out.println("this.parent.worldScene AFTER: " + this.parent.worldScene);
	    	System.out.println("this.worldScene AFTER: " + this.worldScene);
	    	
	    	System.out.println("cost is: " + cost);*/
	    	
	    	//System.out.println("cost IS: " + cost);
	    	//System.out.println("---------------------------------------------------------------------------");
	       	return cost;
	    }
			
	}

	/* END TRYING HERE*/
	
	
	// initialize simulator to a default configuration
	public Simulator()
	{
		world = new LevelScene();
		world.init();
		world.level = new Level(500,15);
	}
	
	// update the internal simulator
	public void update(float[] enemies, byte[][] data)
	{
		world.setEnemies(enemies);
		world.setLevelScene(data);
	}
	
	public static void runSimulator(boolean[] action)
	{
		world.mario.setKeys(action);
		world.tick();
	}
	
	// initialize the very first searchnode 
	public void startSearch()
	{
		// initialize the firstNode with null parent and null action
		SearchNode firstNode = new SearchNode(null,null);
		// set the world state of this node to the current world state
		firstNode.worldScene = backupState();
		//System.out.println("firstNode.worldScene.mario.x IS: " + firstNode.worldScene.mario.x);
		// put all children of firstNode to the openList
		openList = new ArrayList<SearchNode>();
		openList.addAll(/*firstNode*/firstNode.generateChildren());
		
		// initialize closeList and put firstNode to the closeList
		closeList = new ArrayList<SearchNode>();
		closeList.clear();
		closeList.add(firstNode);		
	}
	
	// pick the lowest-cost node from the open list
	public SearchNode pickBestNode()
	{
		SearchNode bestNode = openList.get(0);
		SearchNode node;
		//int index = 0;
		int lenOpenList = openList.size();
		for (int i=0; i<lenOpenList; i++)
		{
			node = openList.get(i);
			if (node.totalCost < bestNode.totalCost)
			{
				bestNode = node;
				//index = i;
			}
		}
		openList.remove(bestNode); // remove the bestNode from the open list
		return bestNode;
	}
	
	// main A* search algorithm goes here
	public void search(long startTime)
	{
		/*// initialization
		// start with a root node
		// generate all children nodes
		// initialize the firstNode with null parent and null action
		SearchNode firstNode = new SearchNode(null,null);
		// set the world state of this node to the current world state
		firstNode.worldScene = this.world;
		openList = new ArrayList<SearchNode>();
		closeList = new ArrayList<SearchNode>();
		openList.add(firstNode);*/
		
		
		
		// while openList is not empty or time limit has not run out or search is still inside the screen
		// pick the best node from the open list 
		// generate all children nodes from this node (this step requires running the simulator)
		// 
		while ((!openList.isEmpty()) && (System.currentTimeMillis() - startTime < 40))
		{
			SearchNode next = pickBestNode();//.remove(pickBestNode());
			//next.isReality = true;
			
			ArrayList<SearchNode> children = next.generateChildren();
			//openList.remove(next); // remove next from the open list
			closeList.add(next);
			for (SearchNode child:children)
			{
				if (!openList.contains(child) && !closeList.contains(child)) // if child is not in open or close list, then add to the open list
				{
					openList.add(child);
				}
				else if (openList.contains(child)) // if the child is already in the 
				{
					if (openList.get(openList.indexOf(child)).totalCost > child.totalCost)
					{
						openList.remove(openList.indexOf(child));
						openList.add(child);
					}
				}
			}
			// running the simulator 
		}
		// put all children of firstNode to the openList
	}
	
	// return the optimal path after searching
	public boolean[] optimalPath()
	{
		// run pickBestNode()
		// trace back from that bestNode to the one right after where the simulator is staying
		// make the move 
		long startTime = System.currentTimeMillis();
		//System.out.println("levelScene STATE 1: " + world);
		startSearch();
		search(startTime);
		
		// finding the lowest-cost node available in the open list
		float minCost = 10000000.0f;
		SearchNode bestInOpenList = null;
		for(SearchNode node:openList)
		{
			//System.out.println("node.totalCost OUT: " + node.totalCost);
			if (node.totalCost < minCost)
			{
				//System.out.println("node.totalCost IN: " + node.totalCost);
				bestInOpenList = node;
				minCost = node.totalCost;
			}
		}
		
		// tracing back to where reality is
		SearchNode previous = null;
		while (bestInOpenList.parent != null)
		{
			previous = bestInOpenList;
			bestInOpenList = bestInOpenList.parent;
		}
		
		return previous.action;
	}
	
	// make a clone of the current world state (copying mario's state, all enemies, and some level information)
	public static LevelScene backupState()
		{
			LevelScene sceneCopy = null;
			try
			{
				sceneCopy = (LevelScene) world.clone();
			} catch (CloneNotSupportedException e)
			{
				e.printStackTrace();
			}
			
			return sceneCopy;
		}

	
}
