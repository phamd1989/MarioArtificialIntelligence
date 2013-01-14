package competition.cig.dungpham;

import java.util.ArrayList;

import competition.cig.dungpham.Simulator.SearchNode;

import ch.idsia.mario.engine.sprites.Mario;

public class Utils 
{

	// Create a list of (almost) all valid actions possible in our node
    public static ArrayList<boolean[]> createPossibleActions(SearchNode searchNode)
    {
    	ArrayList<boolean[]> possibleActions = new ArrayList<boolean[]>();

    	// jump
    	possibleActions.add(createAction(false, false, false, true, false)); // jump only
    	possibleActions.add(createAction(false, false, false, true, true)); // jump and speed     	
    	     	
    	
    	// run right
    	possibleActions.add(createAction(false, true, false, false, true)); // move right and speed
    	possibleActions.add(createAction(false, true, false, true, true)); // move right, jump, and speed
    	possibleActions.add(createAction(false, true, false, false, false)); // move right only
    	possibleActions.add(createAction(false, true, false, true, false)); // move right and jump
 	
    	// run left
    	possibleActions.add(createAction(true, false, false, false, false)); // move left only
    	possibleActions.add(createAction(true, false, false, true, false)); // move left and jump
    	possibleActions.add(createAction(true, false, false, false, true)); // move left and speed
    	possibleActions.add(createAction(true, false, false, true, true)); // move left, jump, and speed 	
    	     	
    	return possibleActions;
    }
    
    public static boolean[] createAction(boolean left, boolean right, boolean down, boolean jump, boolean speed)
    {
    	boolean[] action = new boolean[5];
    	action[Mario.KEY_DOWN] = down;
    	action[Mario.KEY_JUMP] = jump;
    	action[Mario.KEY_LEFT] = left;
    	action[Mario.KEY_RIGHT] = right;
    	action[Mario.KEY_SPEED] = speed;
    	return action;
    }

}
