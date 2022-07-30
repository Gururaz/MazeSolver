package mazeSolver;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

// To allign the robot to the center of the tile
public class RobotAllign extends RobotAttributes{

	SensorReading sensor = new SensorReading();
	RobotMovment robotMovment = new RobotMovment();

	private float angle = sensor.fetchGyroAngle(); 
	private final float ALLIGN_MIN_DISTANCE = 12.5f, ALLIGN_MAX_DISTANCE = 13.2f;
	
	public void intialPosition() {
		robotMovment.turn((int)angle);
		LCD.clearDisplay();
		LCD.drawString("Ready", 1, 2);
	}

	public void position(){
		float distance = sensor.fetchDistance();
		LCD.drawString("dist" + distance , 1, 2);
		if(distance > DISTANCE_TO_MOVE) {
			LCD.drawString("Straight", 1, 3);
			Delay.msDelay(MAX_DELAY);
		}
		rightAllign(LOOKRIGHT);
		backAllign(LOOKBACK);
		intialPosition();

		LCD.clearDisplay();
		LCD.drawString("Ready!!", 1, 2);
	}

	private void rightAllign(int angle) {
		LCD.clearDisplay();
		robotMovment.turn(angle);
		alignLogic();

	}
	
	private void backAllign(int angle) {
		LCD.clearDisplay();
		robotMovment.turn(angle);
		alignLogic();
	}
	
	public void alignLogic() {
		LCD.clearDisplay();
		float distance;
		robotMovment.setSpeed(SLOW_SPEED);

		while(true) {
			Delay.msDelay(MID_DELAY);
			distance = sensor.fetchDistance();
			LCD.drawString("dist =" + distance, 1, 1);
			if(distance > ALLIGN_MAX_DISTANCE) {
				runBackward();	
			}
			else if(distance < ALLIGN_MIN_DISTANCE){
				runForward();  
			}
			else if(distance >= ALLIGN_MIN_DISTANCE && distance <= ALLIGN_MAX_DISTANCE) {
				LCD.drawString("Alligned", 1, 2);
				stopMotors();
				break;

			}
		}
	}
}
