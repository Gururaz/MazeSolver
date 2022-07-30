package mazeSolver;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

// Menu to select the target wall color
public class Menu {				
	private final String COLOR_ARRAY[] = {"NONE", "BLACK","BLUE", "GREEN","YELLOW","RED", "WHITE","BROWN"}; 
	public static int colorSelectID; 
	private final int DELAY = 2000;

	public void colorSelect() {
		menu(0);
		colorSelectID = cursorMove(); 
		LCD.clearDisplay();
		LCD.drawString("Target Colour" , 1, 2);
		LCD.drawString("" + COLOR_ARRAY[colorSelectID], 1, 3);
		Delay.msDelay(DELAY);
		LCD.clearDisplay();
	}

	private void menu(int count) {
		LCD.clear();
		for(int i = 0; i < COLOR_ARRAY.length; i++) {
			LCD.drawString(COLOR_ARRAY[i], 3, i);	
		}	
		LCD.drawString(">", 1, count);
	}

	private int cursorMove() {
		int count = 0;
		do{
			int button = 0;
			button = Button.waitForAnyEvent();
			if(button == Button.ID_ESCAPE){	
				LCD.clear();
				LCD.drawString("Mission Abort", 1,2);
				Delay.msDelay(DELAY);
				menu(0);
			}

			if(button == Button.ID_DOWN || button == Button.ID_RIGHT){
				count++;
				count = count % 8;
				menu(count);
			}

			if (button == Button.ID_UP || button == Button.ID_LEFT){
				count--;
				if (count < 0) {count = 7;}
				count =count % 8;
				menu(count);
			}			
		}while(Button.ENTER.isUp());	
		return count;
	}
}

