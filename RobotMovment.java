package mazeSolver;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

//All the basic robot functionality such as turn, forward motion, motor angle calculations are covered in this class
public class RobotMovment extends RobotAttributes {

	private final float WHEEL_DIAMETER = 5.5f, WHEEL_RADIUS = 5.5f/2, RADIUS_OF_TURNING = 61.5f, Pi = 3.14f; 
	private final int MAX_ERROR = 15, MIN_ERROR = 10, GEAR_RATIO = 3, REVOLUTION = 360;                      

	SensorReading sensor = new SensorReading();

	// Method which moves the robot in the forward direction
	public void moveForward(){ 

		LCD.clearDisplay();
		LCD.drawString("Moving Forward",1,2);

		setSpeed(MAX_SPEED);

		double angle = angleCal(DISTANCE_TO_MOVE); 

		backwards(angle);
		Delay.msDelay(MID_DELAY);	
	}	

	public void backwards(double angle){
		rightMotor.rotate((int) -angle,true);
		leftMotor.rotate((int) -angle);
	}

	public double angleCal(int Distance){
		double motorAngle  = GEAR_RATIO*(Distance/(Pi*WHEEL_DIAMETER))*REVOLUTION;
		return motorAngle;
	}

	// Method to turn the robot to required degrees
	public void turn(int degrees){
		
		LCD.clearDisplay();
		LCD.drawString("Turning", 1, 1);

		while(true) {
			float error = calcError(degrees,sensor.fetchGyroAngle());
			float delta = ComputeDelta(error);
			Delay.msDelay(20);
			LCD.drawString("delta= "+ delta,1,2);				
			setSpeed(SLOW_SPEED*delta);

			float gyroAngle = sensor.fetchGyroAngle();
			LCD.drawString("Angle= "+ gyroAngle,1,3);

			error = calcError(degrees,gyroAngle);
			LCD.drawString("error= "+ error,1,4);

			float motorAngle = CalcMotorAngle(error);
			rotate(motorAngle);

			if(sensor.fetchGyroAngle() == degrees){ 
				stopMotors();
				break;
			}
		}
		Delay.msDelay(MIN_DELAY);	
	}

	// Method to set speed based on the error interms of turn degrees or the wall distance
	public float ComputeDelta(float error) {
		float Kp = 0;
		
		if(error > MAX_ERROR || error < -MAX_ERROR) {
			Kp= 4;	
		}
		else if((error <= MAX_ERROR && error > MIN_ERROR) || (error > -MAX_ERROR && error < -MIN_ERROR)){
			Kp = 0.9f ;
		}
		else if((error <= MIN_ERROR) || error > -MIN_ERROR) {
			Kp = 0.5f;
		} 
		return Kp;
	}

	public void rotate(float motorAngle) {
		startSynchronization();
		rightMotor.rotate((int)-motorAngle,true);
		leftMotor.rotate((int) motorAngle,true);
		endSynchronization();
	}

	//Method to calculate difference in desired angle and current position of bot. 

	public float calcError(int degrees,float gyroAngle){
		float error = (degrees - gyroAngle);
		return error;
	}

	//Method to calculate motor angle to turn bot to required angle using radius of turning.

	public float CalcMotorAngle(float error) {
		float motorAngle = GEAR_RATIO * (error * RADIUS_OF_TURNING) / WHEEL_RADIUS;
		return motorAngle;
	}

	public void setSpeed(double speed) {
		LCD.clear(6);
		LCD.drawString("speed= " + speed, 1, 6);
		rightMotor.setSpeed((int)speed);
		leftMotor.setSpeed((int)speed);	
	}
}
