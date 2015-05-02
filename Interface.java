public class Interface
{

    public static void update(ElevatorProject.Building building, EcranGraphique window)
    {

	drawMenu(building, window);

	drawArrows(window);

	switch(window.getMouseState())
	    {
	    default : break;
	    case 1: // mouse is pressed
		{
		    click(building, window);
		}break;
	    }
    }

    public static void drawMenu(ElevatorProject.Building building, EcranGraphique window)
    {
	/// BACKGROUND
        int menuY = (int)(0.6 * Defines.WINDOW_SIZE),
	    menuHeight = (int)(0.2 *  Defines.WINDOW_SIZE),
	    menuWidth = Defines.WINDOW_SIZE;
	window.setColor(4, 139, 154);
	window.fillRect(0, menuY, menuWidth, menuHeight);
	window.setColor(128, 0, 20);
	window.drawRect(0, menuY, menuWidth- 3, menuHeight);
	window.drawRect(1, menuY + 1, menuWidth - 4, menuHeight - 1);

	/// TEXT
	// quick access
        ElevatorProject.Elevator elevator = building.elevator;
	int position =(int)( (float)elevator.positionByHeight / (float)(Defines.FLOOR_HEIGHT_METERS));
	int nextPosition = (
			    elevator.direction < 0 && elevator.direction + position >= 0
			    || elevator.direction > 0 && elevator.direction + position <= building.floors.length -1
			    ) 
	    ? position + elevator.direction 
	    : position;
	
	
	// LEAVERS
	int nQuit = elevator.destinationList[nextPosition];	
	window.drawText((int)(0.1 * menuWidth), (int)(menuY + menuHeight * 0.1), window.COLABA8x13, "Sortent au prochain etage : " + Integer.toString(nQuit) );

	// ENTERS
	int nEnter = elevator.waitingList[nextPosition];
	window.drawText((int)(0.1 * menuWidth), (int)(menuY + menuHeight * 0.2), window.COLABA8x13, "Veulent monter au prochain etage : " + Integer.toString(nEnter) );

	// TIME TO WAIT
	window.setColor(64,64,0);
	int seconds =  (int)(System.currentTimeMillis() - elevator.timeAtArrive) / 1000 ;
	int secondsToWait = (int)(elevator.timeToWait / 1000);
	
	seconds = (elevator.direction == 0) ? seconds : 0;
	window.drawText(
			(int)(0.1 * menuWidth), // x
			(int)(menuY + menuHeight * 0.3), //y
			window.COLABA8x13, // format
			"Secondes attendues : " + Integer.toString(seconds) + " / " + Integer.toString(secondsToWait)  // text
			);
	window.drawText(
			(int)(0.6 * menuWidth), //x
			(int)(menuY + menuHeight * 0.1), //y
			window.COLABA8x13, // format
			"Time : " + Integer.toString(ElevatorProject.timePassed) // text
			);
	//
    }

    public static void drawArrows(EcranGraphique window)
    {
	final int arrowSize = 30 ;
	
	// UPPER ARROW
	Graphics.Rectangle up = new Graphics.Rectangle();
	up.x = Defines.WINDOW_SIZE - 2 * arrowSize;
	up.width = arrowSize;
	up. y = 2 * arrowSize ;
	up.height = arrowSize ;
	Graphics.drawRectangle(up, window, Graphics.createNewColor(255,255,255));

	// LOWER ARROW
	Graphics.Rectangle low = new Graphics.Rectangle();
	low.x = Defines.WINDOW_SIZE - 2 * arrowSize;
	low.width = arrowSize;
	low. y = 4 * arrowSize ;
	low.height = arrowSize ;
	Graphics.drawRectangle(low, window, Graphics.createNewColor(255,255,255));


	// MIDDLE BUTTON
	Graphics.Rectangle middle = new Graphics.Rectangle();
	middle.x = Defines.WINDOW_SIZE - 2 * arrowSize;
	middle.width = arrowSize;
	middle. y = 3 * arrowSize ;
	middle.height = arrowSize ;
	Graphics.drawRectangle(middle, window, Graphics.createNewColor(255,255,255));
	
    }

    public static void click(ElevatorProject.Building building, EcranGraphique window)
    {
	// Update the mouse Position
	int mX = -1, mY = -1 ;
	updateMousePosition(mX, mY, window);	
	// 
    }

    public static void updateMousePosition(int x, int y, EcranGraphique window)
    {
	x = window.getMouseX();
	y = window.getMouseY();
    }


}
