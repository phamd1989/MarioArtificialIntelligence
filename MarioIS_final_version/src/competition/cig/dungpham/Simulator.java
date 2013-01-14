package competition.cig.dungpham;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ch.idsia.mario.engine.sprites.Mario;
import competition.cig.robinbaumgarten.astar.LevelScene;
import competition.cig.robinbaumgarten.astar.level.Level;

public class Simulator 
{
	// private data
	public static LevelScene currentWorld = null;
	public static float currentPosition = 0;
	//private SearchNode realityNode;
	public ArrayList<SearchNode> openList; 
	private ArrayList<SearchNode> closeList;// = new ArrayList<SearchNode>();
	public SearchNode bestPosition;
	private ArrayList<boolean[]> tracing;
	private ArrayList<Float> trackBestPos = new ArrayList<Float>();
	
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
		boolean goodNode = false;
		//boolean isVisited = false; // whether the node has been visited or not
		//boolean isReality = false; // whether this node is in reality or not
	    //int ticks; // introduced since child's currentCost is not the same as parent's currentCost 
		
		float maxSpeed = 10.09f;// constant maximum speed 
		float damageCost = 100000.09f;
		float tooCloseCost = 1500;
		//float isInVisited = 1000.09f;
		
		// helper functions
		public SearchNode(SearchNode parent, boolean[] action)
		{
			// constructor
			this.parent = parent;
			this.action = action;
			
			if (parent!=null)
			{
				//System.out.println("GO IN HERE!!!");
			    this.costSoFar = parent.costSoFar + simulateAction(); //
			    //System.out.println("worldScene.mario.x IS: " + worldScene.mario.x);
			    this.heuristicCost = computeHeuristicCost(worldScene.mario.x, worldScene.mario.xa); 
			    // from here, what is the heuristic cost to reach goal			    
			}
			else
			{
			    this.costSoFar = 0;			    
			    this.heuristicCost = computeHeuristicCost(currentWorld.mario.x, 0);
			}
			
			/*if (action != null)
			{
				if ((action[1] == true && action[3] == true && action[4] == true))
					this.totalCost = this.costSoFar + this.heuristicCost -100;
				else if (action[1] == true && action[3] == true)
					this.totalCost = this.costSoFar + this.heuristicCost -90;
				else if (action[1] == true && action[4] == true)
					this.totalCost = this.costSoFar + this.heuristicCost -80;
				else if (action[1] == true)
					this.totalCost = this.costSoFar + this.heuristicCost -70;
				else if (action[3] == true && action[4] == true)
					this.totalCost = this.costSoFar + this.heuristicCost -60;
				else if (action[3] == true)
					this.totalCost = this.costSoFar + this.heuristicCost -50;
				else
					this.totalCost = this.costSoFar + this.heuristicCost +10;
			}*/
			
			this.totalCost = (this.costSoFar)*1.2f  + (this.heuristicCost) * 0.89f;
			/*if (action != null)
				System.out.println("action IS: " + printAction(action));
			else
				System.out.println("NULL action ");*/
			//System.out.println("this.totalCost IS: " + this.totalCost);
			
		}
		
		public float computeHeuristicCost(float position, float speed)
		{
			// compute the heuristic time from this position to a distant target
			// basically evaluating which move is better than another in terms of time		
			float largeDistance = 16000.0f;
			
			// need to debug here
			// when only speed is given, it leads to the cost node of infinity.
			return 2*(largeDistance - position) / (maxSpeed +  speed);
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
	    	// set the runner to where the state of this node's parent is
	    	currentWorld = parent.worldScene;
	    	parent.worldScene = backupState();
	    	// find the initial damage
	    	int initialDamage = currentWorld.mario.damage;
	    	//System.out.println("currentWorld.mario.damage BEFORE IS: " + currentWorld.mario.damage);
	    	// now run the simulator, taking the action at this node
	    	
	    	runSimulator(action);
	    	runSimulator(action);
	    	runSimulator(action);
	    	// now find out the true cost
	    	float cost = 0;
	    	cost = 2 * Math.abs(currentWorld.mario.x - parent.worldScene.mario.x) / (maxSpeed + currentWorld.mario.xa + parent.worldScene.mario.xa);
	    	//System.out.println("currentWorld.mario.damage AFTER IS: " + currentWorld.mario.damage);
	    	//if  (currentWorld.mario.damage != 0)
	    	cost += (getMarioDamage() - initialDamage) * damageCost;
	    	
	    	// trying out if two nodes are too close to each other, add penalty
	    	/*if ((Math.abs(currentWorld.mario.x - parent.worldScene.mario.x) < 1)
	    			&& Math.abs(currentWorld.mario.y - parent.worldScene.mario.y) < 1)
	    		cost += tooCloseCost;*/
	    	
	    	for (SearchNode node:closeList)
	    	{
	    		if (Math.abs(node.worldScene.mario.x - currentWorld.mario.x) < 3 
	    			&& Math.abs(node.worldScene.mario.y - currentWorld.mario.y) < 3)
	    		{
	    			cost += tooCloseCost;
	    			break;
	    		}
	    	}
	    	
	    	if ((getMarioDamage() - initialDamage) == 0)
	    		this.goodNode = true;
	    	// set the state at this node to the state of the simulator
	    	worldScene = backupState();
	       	return cost;
	    }
			
	}
	
	public String printAction(boolean[] action)
    {
    	String s = "";
    	if (action[Mario.KEY_RIGHT]) s+= "Forward ";
    	if (action[Mario.KEY_LEFT]) s+= "Backward ";
    	if (action[Mario.KEY_SPEED]) s+= "Speed ";
    	if (action[Mario.KEY_JUMP]) s+= "Jump ";
    	if (action[Mario.KEY_DOWN]) s+= "Duck";
    	return s;
    }
	
	private int getMarioDamage()
    {
    	/*// early damage at gaps: Don't even fall 1 px into them.
		//currentWorld.level.isGap
    	if (currentWorld.level.isGap[(int) (currentWorld.mario.x/16)] 
    		&& currentWorld.mario.y > currentWorld.level.gapHeight[(int) (currentWorld.mario.x/16)]*16)
    	{
    		currentWorld.mario.damage+=5;
    	}*/
    	return currentWorld.mario.damage;
    }
	
	// initialize simulator to a default configuration
	public Simulator()
	{
		currentWorld = new LevelScene();
		currentWorld.init();
		currentWorld.level = new Level(1500,15);
	}
	
	// update the internal simulator
	public void update(float[] enemies, byte[][] data)
	{
		currentWorld.setEnemies(enemies);
		currentWorld.setLevelScene(data);
	}
	
	public static void runSimulator(boolean[] action)
	{
		currentWorld.mario.setKeys(action);
		currentWorld.tick();
	}
	
	// initialization for every search 
	public void startSearch()
	{
		// initialize a brand-new firstNode for each search 
		//System.out.println("STARTSEARCH");
		SearchNode firstNode = new SearchNode(null,null);
		// set the world state of this node to the current state or the reality state
		firstNode.worldScene = backupState();
		firstNode.goodNode = true;
		System.out.println("Starting position IS: " + firstNode.worldScene.mario.x);
		// initialize a new open list,  then put all children of firstNode to the openList
		closeList = new ArrayList<SearchNode>();
		closeList.clear();
		closeList.add(0,firstNode);
		openList = new ArrayList<SearchNode>();
		openList.addAll(firstNode.generateChildren());
		
		currentPosition = currentWorld.mario.x;
		bestPosition = firstNode;
		// initialize closeList and put firstNode to the closeList
		// don't need to worry about it for now, using the coordinates for the node
		// because the action and parent are always changed, 
				
		//System.out.println("END OF STARTSEARCH");
	}
	
	// pick the lowest-cost node from the open list
	public SearchNode pickBestNode()
	{
		SearchNode bestNode = null;//openList.get(0);
		SearchNode node;
		float minValue = 10000000.0f;
		//int index = 0;
		int lenOpenList = openList.size();
		//System.out.println("lenOpenList IS: " + lenOpenList);
		for (int i=0; i<lenOpenList; i++)
		{
			node = openList.get(i);
			//System.out.println("node.totalCost: " + node.totalCost);
			if (node.totalCost < minValue)// && node.goodNode == true)
			{
				//System.out.println("GOES IN HERE");
				bestNode = node;
				minValue = node.totalCost;
			}
		}
		openList.remove(bestNode); // remove the bestNode from the open list
								   // always the best node, no matter what!
		//System.out.println("bestNode.totalCost IS: " + bestNode.totalCost);
		closeList.add(0, bestNode);
		return bestNode;
	}
	
	
	// pick the lowest-cost node from the close list
	public SearchNode pickBestNodeFromCloseList()
		{
			SearchNode bestNodeInCloseList = null;//openList.get(0);
			SearchNode node;
			float minValue = 10000000.0f;
			//int index = 0;
			int lenOpenList = closeList.size();
			for (int i=0; i<lenOpenList; i++)
			{
				node = closeList.get(i);
				if (node.totalCost < minValue && node.goodNode == true)
				{
					bestNodeInCloseList = node;
					minValue = node.totalCost;
				}
			}
			closeList.remove(bestNodeInCloseList); // remove the bestNode from the open list
									   // always the best node, no matter what!
			//System.out.println("bestNode.totalCost IS: " + bestNode.totalCost);
			return bestNodeInCloseList;
		}
	
	// main A* search algorithm goes here
	public void search(long startTime)
	{	
		// maximum distance from Mario to the right end of the screen
		int maxRight = 176;
		// while openList is not empty or time limit has not run out or search is still inside the screen
		// pick the best node from the open list 
		// generate all children nodes from this node (this step requires running the simulator)
		// 
		while ((!openList.isEmpty()) 
				&& (System.currentTimeMillis() - startTime < 40) 
				&& (currentWorld.mario.x - currentPosition < maxRight))
		{
			SearchNode next = pickBestNode();//.remove(pickBestNode());
			//next.isReality = true;	
			//System.out.println(next);
			openList.addAll(next.generateChildren());
			//closeList.add(next);
			
			/*for (SearchNode temp:openList)
			{
				if (temp.goodNode == true && temp.worldScene.mario.x > bestPosition.worldScene.mario.x)
					bestPosition = temp; 
			}*/
			if (next.goodNode == true && next.worldScene.mario.x > bestPosition.worldScene.mario.x)
				bestPosition = next;
		}
		// put all children of firstNode to the openList
	}
	
	// extracting the optimal action after searching  
	public boolean[] extractPlan()
	{
		// optimal action
		tracing = new ArrayList<boolean[]>();
		boolean[] action = null;
		// picking the best node from the openList
		SearchNode runner = bestPosition;
		
		//SearchNode runner = pickBestNode();////////
		/*if (runner == null)
		{
			return Utils.createAction(false, false, false, false, false);
		}*/
		
		//System.out.println("runner IS: " + runner);
		/*while  (!runner.goodNode)
			runner = pickBestNode();*/
		/*if (temp1.worldScene.mario.x == runner.worldScene.mario.x)
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");*/
		
		/*SearchNode runner1 = pickBestNode();
		//SearchNode chosenNode = null;
		
		if (!runner1.goodNode)
			runner1 = pickBestNode();*/
		
		System.out.println("closeList.size() IS: " + closeList.size());
		
		/*SearchNode runner1 = pickBestNodeFromCloseList();
		SearchNode runner2 = pickBestNodeFromCloseList();
		
		System.out.println("runner1.worldScene.mario.x IS: " + runner1.worldScene.mario.x);
		System.out.println("runner1.totalCost IS: (current plan)" + runner1.totalCost);
		
		System.out.println("runner2.worldScene.mario.x IS: " + runner2.worldScene.mario.x);
		System.out.println("runner2.totalCost IS: (current plan)" + runner2.totalCost);*/
		
		/*while (Math.abs(runner1.worldScene.mario.x - runner2.worldScene.mario.x ) < 1)
			runner2 = pickBestNodeFromCloseList();*/
		
		System.out.println("runner.worldScene.mario.x IS: " + runner.worldScene.mario.x);
		System.out.println("runner.totalCost IS: (current plan)" + runner.totalCost);
		
		/*System.out.println("closeList.get(0).worldScene.mario.x: " + closeList.get(0).worldScene.mario.x);
		System.out.println("closeList.get(1).worldScene.mario.x: " + closeList.get(1).worldScene.mario.x);*/
		
		/*float maxi = 0.f;
		SearchNode secondBest = null;
		for (SearchNode node:closeList)
		{
			if (node.worldScene.mario.x > maxi && node.goodNode == true && Math.abs(node.worldScene.mario.x - runner.worldScene.mario.x) > 1)
			{
				secondBest = node;
				maxi = node.worldScene.mario.x;
			}
				
		}
		
		//System.out.println("secondBest.totalCost IS: " + secondBest.totalCost);
		System.out.println("node with second maximum distance in closeList IS: " + maxi);
		
		if (runner.totalCost > secondBest.totalCost)
			runner = secondBest;*/
		
		/*if (runner.totalCost > runner1.totalCost)
		{
			System.out.println("THIS HAPPEN???");
			runner = runner1;
		}*/
		// now tracing back to the root node before planning
		//runner = runner2;
		while (runner.parent != null)
		{
			action = runner.action;
			tracing.add(0,action);
			//chosenNode = runner;
			runner = runner.parent;
		}
		/*if (action == null)
		{
			System.out.println("THIS HAPPEN!!!!!!");
			
		}*/
		String result = "";
		for(int i = 0; i<tracing.size(); i++)
		{
			result = result + printAction(tracing.get(i)) + ", ";
			
		}
		System.out.println(result.toString());									
		
		
		//System.out.println("runner1.worldScene.mario.x IS: " + runner1.worldScene.mario.x);
		//System.out.println("chosenNode's cost IS: " + chosenNode.totalCost);
		//System.out.println("runner's cost IS: " + runner.totalCost);
		/*if (chosenNode.totalCost > runner.totalCost)
			return Utils.createAction(false, false, false, false, false);*/
		
		if (action == null)
		{
			System.out.println("THIS HAPPEN!!!!!!");
			return Utils.createAction(false, false, false, false, false);
		}
		
		trackBestPos.add(bestPosition.worldScene.mario.x);
		
		//tracing.remove(0);
		return action;
	}
	
	// return the optimal path after searching
	public boolean[] optimalPath()
	{	
		// startTime for search
		long startTime = System.currentTimeMillis();
		// the action to be returned
		boolean[] action = null;
		// reserve the state of the currentWorld 
		LevelScene realityState = backupState();
		
		
		//System.out.println("levelScene STATE 1: " + world);
		startSearch();
		//System.out.println("openList.size() BEFORE IS: " + openList.size());
		//System.out.println("closeList.size() BEFORE IS: " + closeList.size());
		search(startTime);
		//System.out.println("GOES HERE");
		//if (tracing == null || tracing.size() <= 5)
			action = extractPlan();			
		/*else
		{
			action = tracing.get(0);
			tracing.remove(0);
		}*/
			//closeList.get(1).action;
		restoreState(realityState);
		
		
		// OUTPUT
		BufferedWriter writer = null;
		try 
		{
			String str = "";
			writer = new BufferedWriter(new FileWriter("output_Mario.txt"));
			//writer.write(this.trackBestPos.t);
			for (int i = 0; i<this.trackBestPos.size(); i++)
			{
				float temp = this.trackBestPos.get(i);	        	
				//writer.write(Math.round(temp));
				//str = str + /*Math.round(temp)*/ + "hi\n";
				//str = str.concat("hi");
				str = str + temp;
				writer.write(str);//Math.round(temp));
				writer.newLine();
				str = "";
			}
			//writer.write(str);
		} 
		catch (IOException e) {
			System.err.println(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		}
		// END OUTPUT
		
		//System.out.println("openList.size() AFTER IS: " + openList.size());
		//System.out.println("closeList.size() AFTER IS: " + closeList.size());
		//System.out.println("closeList.get(0).action IS: " + closeList.get(0).action);
		System.out.println("tracing.size() IS: " + tracing.size());
		System.out.println("openList.size() IS: " + openList.size());
		//System.out.println("bestPosition.worldScene.mario.x IS: " + bestPosition.worldScene.mario.x);
		System.out.println("action IS: " + printAction(action));
		System.out.println("---------------------------------------------------------------------------");
		return action;
	}
	
	// make a clone of the current world state (copying mario's state, all enemies, and some level information)
	public static LevelScene backupState()
		{
			LevelScene sceneCopy = null;
			try
			{
				sceneCopy = (LevelScene) currentWorld.clone();
			} catch (CloneNotSupportedException e)
			{
				e.printStackTrace();
			}
			
			return sceneCopy;
		}
	
	public void restoreState(LevelScene l)
	{
		currentWorld = l;
	}
	
}
