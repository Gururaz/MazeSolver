package mazeSolver;
import MazebotSim.MazebotSimulation;
import MazebotSim.Visualization.GuiMazeVisualization;
import lejos.utility.Delay;

public class mainSimulatedMaze {

	public static void main(String[] args) {
		MazebotSimulation sim = new MazebotSimulation("Mazes/maze_1_3by4.png", 1.4, 1.05);
		GuiMazeVisualization gui = new GuiMazeVisualization(1.4, sim.getStateAccessor());
		
		sim.scaleSpeed(1);

		sim.setRobotPosition(0.18, 0.20, 90);
		
		
		sim.startSimulation();
		gui.startVisualization();
		
		MazeSolver.main(new String[0]);

		Delay.msDelay(100);
		sim.stopSimulation();
	}
}
