package mazeSolver;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

// Class to check for the target wall in the three sided region
public class ThreeSided extends RobotAttributes {
	
	SensorReading sensor = new SensorReading();
	RobotMovment robotMovment = new RobotMovment();

	public static boolean ColorDetected = false;
	private final int THREE_WALLS = 3, MIN_COLOR_DISTANCE = 5;
	private final float WALL_MAX_DISTANCE = 10.5f,  DETECTION_SPEED = 0.4f;
	private float distanceToWall = 0;

	// Method which calls sideCheck (written below) and turns the robot towards the next wall to check on all the three sides
	public void threeSidedWall(){
		AngleSensor.reset();
		int count = 1;
		LCD.clearDisplay();
		LCD.drawString("Three Sided Wall" , 1, 2);
		Delay.msDelay(MID_DELAY);
		
		while(count <= THREE_WALLS){
			sideCheck();
			if(ColorDetected == true) {
				break;
			}
			robotMovment.turn(LOOKRIGHT);
			AngleSensor.reset();
			count += STEP_CHANGE;
		}
	}	
	
	// Method to move the robot to the wall for the color check. 
	private void sideCheck(){
		movePreciseBackward();
		colorCheck();	
		movePreciseForward();
	}

	
	private void colorCheck(){
		int colorDetectID = sensor.colorDetector();
		if(Menu.colorSelectID == colorDetectID){
			makeNoise();
			ColorDetected = true;
		}

		else {
			LCD.clearDisplay();
			LCD.drawString("False Wall", 1, 5);
			Delay.msDelay(MID_DELAY);
		}	
	}

	private void movePreciseBackward(){
		LCD.clearDisplay();
		while(true){
			distanceToWall = (sensor.fetchDistance());
			Delay.msDelay(MID_DELAY);
			LCD.drawString("Dist = " + distanceToWall, 1, 2);
			float kp = robotMovment.ComputeDelta(distanceToWall);
			robotMovment.setSpeed(MID_SPEED * kp * DETECTION_SPEED); // Used DETECTION SPEED variable for more control over the speed
			runBackward();

			if(distanceToWall <= MIN_COLOR_DISTANCE){
				LCD.clearDisplay();
				LCD.drawString("stop!!", 1, 6);
				stopMotors(); 
				break;
			}
		}
	}

	private void movePreciseForward(){
		LCD.clearDisplay();
		while(true){
			distanceToWall = (sensor.fetchDistance());
			Delay.msDelay(MID_DELAY);
			LCD.drawString("Dist = " + distanceToWall, 1, 2);
			float kp = robotMovment.ComputeDelta(distanceToWall);
			robotMovment.setSpeed(MID_SPEED * kp * DETECTION_SPEED);
			runForward();

			if(distanceToWall >= WALL_MAX_DISTANCE) {
				LCD.clearDisplay();
				LCD.drawString("stop!!", 1, 6);
				stopMotors();
				break;
			}
		}
	}
}

