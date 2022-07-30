package mazeSolver;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

// This class covers all the common attributes which are used in multiple classes, also all the motors are instantiated in this class
public class RobotAttributes{

	public final int MAX_SPEED = 1000, MID_SPEED = 400, SLOW_SPEED = 100, STEP_CHANGE = 1, MAX_DELAY = 1000, MIN_DELAY = 200, MID_DELAY = 500, DISTANCE_TO_MOVE = 35,
			LOOKRIGHT = -90, LOOKBACK = -180, VOLUME = 50, FREQUENCY = 400, DURATION = 500;


	static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);

	static	EV3UltrasonicSensor  distanceSensor = new EV3UltrasonicSensor(SensorPort.S4);
	static  SampleProvider distance = distanceSensor.getDistanceMode();

	static EV3GyroSensor AngleSensor = new EV3GyroSensor(SensorPort.S3);
	static SampleProvider angleMeasure = AngleSensor.getAngleMode();

	static EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
	static EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);

	public void startSynchronization() {
		rightMotor.startSynchronization();
		leftMotor.startSynchronization();
	}

	public void endSynchronization() {
		rightMotor.endSynchronization();
		leftMotor.endSynchronization();
	}

	public void stopMotors() {
		rightMotor.stop(true);
		leftMotor.stop();
	}

	void runBackward() {	
		startSynchronization();
		rightMotor.backward();
		leftMotor.backward();
		endSynchronization(); 
	}

	void runForward() {
		startSynchronization();
		rightMotor.forward();
		leftMotor.forward();
		endSynchronization(); 

	}
	// Method to activate the sound 
	public void makeNoise() {
		Sound.setVolume(VOLUME);
		Sound.playTone(FREQUENCY,DURATION);	
		LCD.clearDisplay();
	}
}
