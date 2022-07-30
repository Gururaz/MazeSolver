package mazeSolver;
import lejos.utility.Delay;

public class RightWallFollower extends RobotAttributes{
	
	SensorReading sensor = new SensorReading();
	RobotMovment robotMovment = new RobotMovment();
	RobotAllign aligner = new RobotAllign();

	private final int TWO_WALLS = 2, THREE_WALLS = 3, LOOKLEFT = 90, LOOKSTRAIGHT = 0, COUNT_RESET = 0;

	private int count = 0; 

	public void rightToRightLogic()  {
		Menu menu = new Menu();					// Starting the program by choosing the target wall through a menu option
		menu.colorSelect();

		makeNoise();							// Method from the parent class to make a beep to indicate the bot has started its operation
		
		//Allign obj = new Allign();   // Needed if the bot is not placed at the center of the tile
		//obj.position();

		//The condition below follows three operations throughtout the traverse of the maze
		while(true) {
			Delay.msDelay(MID_DELAY);		
			firstMove();
			secondMove();

			if(count == TWO_WALLS) {
				thirdMove();	
			} 

			if(count == THREE_WALLS) {
				ThreeSided threeSidedCheck = new ThreeSided();
				threeSidedCheck.threeSidedWall();					// If the wall count reaches 3 then the three sided class is called 
				if(ThreeSided.ColorDetected == true) {
					break;
				}
			}
			count = COUNT_RESET;		
			AngleSensor.reset();
		}
	}

	// Method to move the robot in forward direction from tile to tile, also checks for wall infront
	private void firstMove() {
		if(sensor.fetchDistance() > DISTANCE_TO_MOVE) {
			robotMovment.moveForward();
		}
		stopMotors();

		if(sensor.wallCheck() == true) {
			count = count + STEP_CHANGE;
		}
	}

	// Robot turns right once the first move is complete, if there is no wall the robot continues to move in the current direction 
	private void secondMove() {
		robotMovment.turn(LOOKRIGHT);
		
		if(sensor.wallCheck() == true) {
			aligner.alignLogic();
			Delay.msDelay(MID_DELAY);
			count += STEP_CHANGE;	
			robotMovment.turn(LOOKSTRAIGHT);  // if there is a wall on the right, robot turns back to its intial position
		}
	}

	// Method is activated only when the total wall count is 2, robot turn to the left and checks for wall
	private void thirdMove() {
		robotMovment.turn(LOOKLEFT);
		if(sensor.wallCheck() == true) {  
			count += STEP_CHANGE;
		}
	}
}
