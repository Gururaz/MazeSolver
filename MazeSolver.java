package mazeSolver;
import lejos.hardware.lcd.LCD;

// Main Entry and end point of the program
public class MazeSolver {

	public static void main(String[] args) {	

		RightWallFollower r = new RightWallFollower();
		r.rightToRightLogic();
		LCD.clearDisplay();
		LCD.drawString("Accomplished!!", 2, 2);
	}

}



