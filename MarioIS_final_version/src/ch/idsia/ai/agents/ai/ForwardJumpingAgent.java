package ch.idsia.ai.agents.ai;

//import competition.cig.robinbaumgarten.astar.LevelScene;

import competition.cig.robinbaumgarten.astar.LevelScene;
import competition.cig.robinbaumgarten.astar.level.Level;

import ch.idsia.ai.agents.Agent;
import ch.idsia.ai.agents.RegisterableAgent;
import ch.idsia.mario.engine.sprites.Mario;
import ch.idsia.mario.environments.Environment;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Karakovskiy
 * Date: Apr 25, 2009
 * Time: 12:27:07 AM
 * Package: ch.idsia.ai.agents.ai;
 */

public class ForwardJumpingAgent extends RegisterableAgent implements Agent {

    static final boolean superslow = false;
    private LevelScene levelScene;
    public ForwardJumpingAgent()
    {
        super("ForwardJumpingAgent");
        reset();
    }

    public void reset()
    {
        action = new boolean[Environment.numberOfButtons];
        action[Mario.KEY_RIGHT] = true;
        action[Mario.KEY_SPEED] = true;
        
        levelScene = new LevelScene();
        levelScene.init();
        levelScene.level = new Level(500, 16);
        
    }

    public boolean[] getAction(Environment observation)
    {
        try {Thread.sleep (39);}
        catch (Exception e){}
        action[Mario.KEY_SPEED] = action[Mario.KEY_JUMP] =  observation.mayMarioJump() || !observation.isMarioOnGround();
        
        // getting the info from the environment and pass it into the engine
        byte[][] scene = observation.getLevelSceneObservation(0);
    	float[] enemies = observation.getEnemiesFloatPos();
    	
    	levelScene.setLevelScene(scene);
    	levelScene.setEnemies(enemies);
    	
    	System.out.println("levelScene.mario.x BEFORE: " + levelScene.mario.x);
    	System.out.println("levelScene.mario.y BEFORE: " + levelScene.mario.y);
    	
    	//while (levelScene.mario.damage == 0)
        {
    		// set the action to be carried out by the engine, and then advance the world state
            levelScene.mario.setKeys(action);
            levelScene.tick();
            /*if (levelScene.mario.damage > 0)
            {
            	action[0] = false;
            	action[1] = false;
            	action[2] = false;
            	action[3] = false;
            	action[4] = false;
            	//return action;
            }*/
        }
    	
    	
    	
    	System.out.println("levelScene.mario.x AFTER: " + levelScene.mario.x);
    	System.out.println("levelScene.mario.y AFTER: " + levelScene.mario.y);
    	
    	
        
        /*System.out.println("x: " + levelScene.mario.x);
        System.out.println("y: " + levelScene.mario.y);*/
        
        System.out.println("damage: " + levelScene.mario.damage);
        
        
    	return action;
    }
}
