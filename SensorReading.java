package mazeSolver;
import lejos.hardware.lcd.LCD;
import lejos.robotics.Color;
import lejos.utility.Delay;

// Class to read data from Color, Ultrasonic & Gyro sensor
public class SensorReading extends RobotAttributes{
	
	public int colorID;
	private final int UNITCONVERTER = 100; // To display in cm
	
	public int colorDetector() {	
		LCD.clearDisplay();
		
		while(true) {				
			Delay.msDelay(MAX_DELAY);
			colorID = colorSensor.getColorID();		 // .getColorID returns a integer number ID of the color with respect  
			final int x = 1,  y = 5;
			LCD.drawString("ColorDetected", x, 4);
			
			switch(colorID) {
			case Color.RED:
				LCD.drawString("RED", x, y);
				Delay.msDelay(MAX_DELAY);
				return 5;

			case Color.GREEN:
				LCD.drawString("GREEN", x, y);
				Delay.msDelay(MAX_DELAY);
				return 3;


			case Color.BLUE:
				LCD.drawString("BLUE", x, y);
				Delay.msDelay(MAX_DELAY);
				return 2;


			case Color.BLACK:	
				LCD.drawString("BLACK", x, y);
				Delay.msDelay(MAX_DELAY);
				return 1;


			case Color.BROWN:
				LCD.drawString("BROWN", x, y);
				Delay.msDelay(MAX_DELAY);
				return 7;


			case Color.YELLOW:
				LCD.drawString("YELLOW", x, y);
				Delay.msDelay(MAX_DELAY);
				return 4;


			case Color.NONE:
				LCD.drawString("NONE", x, y);
				Delay.msDelay(MAX_DELAY);
				return 0;
			}		
		return 0; 
	}
}

	public float fetchDistance(){
		float [] sample = new float [distance.sampleSize()];		
		distance.fetchSample(sample, 0);
		return sample[0] * UNITCONVERTER;						
	}
	
	public float fetchGyroAngle(){
		float [] sample1 = new float [angleMeasure.sampleSize()];
		angleMeasure.fetchSample(sample1,0);
		return sample1[0];
	}
	
	public boolean wallCheck(){
		if(fetchDistance() < DISTANCE_TO_MOVE) {
			return true;
		}
		return false;
	}
}
