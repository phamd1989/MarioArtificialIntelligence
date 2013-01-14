package ch.idsia.mario.engine;

import ch.idsia.tools.GameViewer;
import ch.idsia.tools.LOGGER;

import java.awt.Point;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import com.reddit.programming.mario.DebugPolyLineList;

public class GlobalOptions {
    public static boolean Labels = false;
    public static boolean MarioAlwaysInCenter = false;
    public static Integer FPS = 24;
    public static int InfiniteFPS = 100;
    public static boolean pauseWorld = false;

    public static boolean VisualizationOn = true;
    public static boolean GameVeiwerOn = true;


    private static MarioComponent marioComponent = null;
    private static GameViewer gameViewer = null;
    public static boolean TimerOn = true;

    //    public static Defaults defaults = new Defaults();
    public static boolean GameVeiwerContinuousUpdatesOn = false;
    public static boolean PowerRestoration;
    
    private static int seed = 0;
    private static int difficulty = 0;
    
    public static int[][] Pos = new int[1000][2];
    
    public static DebugPolyLineList MarioLines = new DebugPolyLineList();
    public static int[][] MarioPos = new int[400][3];
	public static int MarioPosSize = 0;

    public static boolean StopSimulationIfWin;
	public static boolean writeFrames = false;
	public static String currentController = "";
	public static boolean drawText = true;
	public static boolean dontResetWindowPosition = false;
	public static long totalFrames = 0;
	
    public static void registerMarioComponent(MarioComponent mc)
    {
        marioComponent = mc;
    }

    public static MarioComponent getMarioComponent() {
        return marioComponent;
    }
    

    public static void registerGameViewer(GameViewer gv)
    {
        gameViewer = gv;
    }

    public static void AdjustMarioComponentFPS() { marioComponent.adjustFPS(); }

    public static void gameViewerTick()
    {
        if (gameViewer != null)
            gameViewer.tick();
        else
            LOGGER.println("GameViewer is not available. Request for dump ignored.", LOGGER.VERBOSE_MODE.ERROR);
    }

    public static String getDateTime(Long d)
    {
        DateFormat dateFormat = (d == null) ? new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:ms") :
                new SimpleDateFormat("HH:mm:ss:ms") ;
        if (d != null)
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = (d == null) ? new Date() : new Date(d);
        return dateFormat.format(date);
    }

    public static void setSeed(int s)
    {
    	seed = s;
    }
    
    public static void setDifficulty(int d)
    {
    	difficulty = d;
    }

    public static int getSeed()
    {
    	return seed;
    }
    
    public static int getDifficulty()
    {
    	return difficulty;
    }
    
//    public static class Defaults extends SimulationOptions
//    {
//        private static boolean gui;
//        private static boolean toolsConfigurator;
//        private static boolean gameViewer;
//        private static boolean gameViewerContinuousUpdates;
//        private static boolean timer;
//        private static int attemptsNumber;
//        private static boolean echo;
//        private static boolean maxFPS;
//        private static String agentName;
//        private static Integer serverAgentPort;
//        private static boolean exitProgramWhenFinished;
//
//        public Defaults()
//        {
//            setLevelLength(320);
//            setLevelDifficulty(0);
//            setLevelRandSeed(1);
//            setVisualization(true);
//            setLevelType(LevelGenerator.TYPE_OVERGROUND);
//            setGui(false);
//            setAttemptsNumber(1);
//            setEcho(false);
//            setMaxFPS(false);
//            setPauseWorld(false);
//            setPowerRestoration(false);
//            setStopSimulationIfWin(false);
//            setAgentName("ForwardAgent");
//            setServerAgentPort(4242);
//            setExitProgramWhenFinished(false);
//        }
//
//        public static boolean isGui() {  return gui; }
//
//        public static void setGui(boolean gui) { Defaults.gui = gui;  }
//
//        public static boolean isToolsConfigurator() {return toolsConfigurator; }
//
//        public static void setToolsConfigurator(boolean toolsConfigurator) { Defaults.toolsConfigurator = toolsConfigurator; }
//
//        public static boolean isGameViewer() {
//            return gameViewer;
//        }
//
//        public static void setGameViewer(boolean gameViewer) {
//            Defaults.gameViewer = gameViewer;
//        }
//
//        public static boolean isGameViewerContinuousUpdates() {
//            return gameViewerContinuousUpdates;
//        }
//
//        public static void setGameViewerContinuousUpdates(boolean gameViewerContinuousUpdates) {
//            Defaults.gameViewerContinuousUpdates = gameViewerContinuousUpdates;
//        }
//
//        public static boolean isTimer() {
//            return timer;
//        }
//
//        public static void setTimer(boolean timer) {
//            Defaults.timer = timer;
//        }
//
//        public static int getAttemptsNumber() {
//            return attemptsNumber;
//        }
//
//        public static void setAttemptsNumber(int attemptsNumber) {
//            Defaults.attemptsNumber = attemptsNumber;
//        }
//
//        public static boolean isEcho() {
//            return echo;
//        }
//
//        public static void setEcho(boolean echo) {
//            Defaults.echo = echo;
//        }
//
//        public static boolean isMaxFPS() {
//            return maxFPS;
//        }
//
//        public static void setMaxFPS(boolean maxFPS) {
//            Defaults.maxFPS = maxFPS;
//        }
//
//        public static String getAgentName() {
//            return agentName;
//        }
//
//        public static void setAgentName(String agentName) {
//            Defaults.agentName = agentName;
//        }
//
//        public static Integer getServerAgentPort()
//        {
//            return serverAgentPort;
//        }
//
//        public static void setServerAgentPort(Integer serverAgentPort) {
//            Defaults.serverAgentPort = serverAgentPort;
//        }
//
//        public static boolean isExitProgramWhenFinished() {
//            return exitProgramWhenFinished;
//        }
//
//        public static void setExitProgramWhenFinished(boolean exitProgramWhenFinished) {
//            Defaults.exitProgramWhenFinished = exitProgramWhenFinished;
//        }
//    }
}
