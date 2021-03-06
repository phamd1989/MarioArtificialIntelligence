package ch.idsia.mario.simulation;

import ch.idsia.ai.agents.Agent;
import ch.idsia.utils.ParameterContainer;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Karakovskiy
 * Date: Apr 12, 2009
 * Time: 9:55:56 PM
 * Package: .Simulation
 */


public class SimulationOptions extends ParameterContainer
{
//    protected Agent agent;
//    protected MarioComponent marioComponent = null;

    public static int currentAttempt = -42;

    protected SimulationOptions()
    {
        super();
        currentAttempt = 1;
    }

    public SimulationOptions getSimulationOptionsCopy()
    {
        SimulationOptions ret = new SimulationOptions();
        ret.setAgent(getAgent());
        ret.setLevelDifficulty(getLevelDifficulty());
        ret.setLevelLength(getLevelLength());
        ret.setLevelRandSeed(getLevelRandSeed());
        ret.setLevelType(getLevelType());
        //ret.setLevelType(2);
//        ret.setMarioComponent(marioComponent);
        ret.setVisualization(isVisualization());
        ret.setPauseWorld(isPauseWorld());
        ret.setPowerRestoration(isPowerRestoration());
        ret.setMaxAttempts(getMaxAttempts());
        //ret.setMaxAttempts(3);
        ret.setMarioMode(getMarioMode());
        //ret.setMarioMode();
        ret.setTimeLimit(getTimeLimit());
        ret.setZLevelEnemies(getZLevelEnemies());
        ret.setZLevelMap(getZLevelMap());
        return ret;
    }

    // Agent
    public Agent getAgent() {
        return a(getParameterValue("-ag"));      }

    public void setAgent(Agent agent) {
        setParameterValue("-ag", s(agent));
    }

    // TODO? LEVEL_TYPE enum?
    // LevelType
    public int getLevelType() {
        return i(getParameterValue("-lt"));      }

    public void setLevelType(int levelType) {
        setParameterValue("-lt", s(levelType));    }


    // LevelDifficulty
    public int getLevelDifficulty() {
        return i(getParameterValue("-ld"));                           }

    public void setLevelDifficulty(int levelDifficulty) {
        setParameterValue("-ld", s(levelDifficulty));    }

    //LevelLength
    public int getLevelLength() {
        return i(getParameterValue("-ll"));      }

    public void setLevelLength(int levelLength) {
        setParameterValue("-ll", s(levelLength));    }

    //LevelRandSeed
    public int getLevelRandSeed() {
        return i(getParameterValue("-ls"));     }

    public void setLevelRandSeed(int levelRandSeed) {
        setParameterValue("-ls", s(levelRandSeed));    }

    //Visualization
    public boolean isVisualization() {
        return b(getParameterValue("-vis"));     }

    public void setVisualization(boolean visualization) {
        setParameterValue("-vis", s(visualization));    }

    //PauseWorld
    public void setPauseWorld(boolean pauseWorld) {
        setParameterValue("-pw", s(pauseWorld));    }

    public Boolean isPauseWorld() {
        return b(getParameterValue("-pw"));     }

    //PowerRestoration
    public Boolean isPowerRestoration() {
        return b(getParameterValue("-pr"));     }

    public void setPowerRestoration(boolean powerRestoration) {
        setParameterValue("-pr", s(powerRestoration));    }

    //StopSimulationIfWin
    public Boolean isStopSimulationIfWin() {
        return b(getParameterValue("-ssiw"));     }

    public void setStopSimulationIfWin(boolean stopSimulationIfWin) {
        setParameterValue("-ssiw", s(stopSimulationIfWin));    }

    //MaxAttempts
    public int getMaxAttempts() {
        return i(getParameterValue("-an"));     }

    public void setMaxAttempts(int maxAttempts) {
        setParameterValue("-an", s(maxAttempts));    }

    //MarioMode
    public int getMarioMode() {
        return i(getParameterValue("-mm"));
    }

    private void setMarioMode(int marioMode) {
        setParameterValue("-mm", s(marioMode));
    }

    //ZLevelMap
    public int getZLevelMap() {
        return i(getParameterValue("-zm"));
    }

    public void setZLevelMap(int zLevelMap)
    {
        setParameterValue("-zm", s(zLevelMap));
    }

    //ZLevelEnemies
    public int getZLevelEnemies() {
        return i(getParameterValue("-ze"));
    }

    public void setZLevelEnemies(int zLevelEnemies)
    {
        setParameterValue("-ze", s(zLevelEnemies));
    }

    // TimeLimit
    public int getTimeLimit() {
        return i(getParameterValue("-tl"));
    }

    public void setTimeLimit(int timeLimit) {
        setParameterValue("-tl", s(timeLimit));
    }

}
