/**
* \file Interface.java
* \brief Partie de controle de la simulation
* \authors Ervan Silvert
* \authors Paul-Armand Michaud
*/


/**
* \class Interface
* \brief Etude numerique de la situation actuelle et a venir.
* \authors Ervan Silvert
* \authors Paul-Armand Michaud
*/

public class Interface
{

    /**
     * \brief Dessine le contenu et met a jour les donnees
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    public static void update(ElevatorProject.Building building, EcranGraphique window)
    {
	drawMenu(building, window);
	drawArrows(building, window);

	switch(window.getKey())
	    {
	    default : break;
	    case 'p' : ElevatorProject.LAG_TIME /= 2.0; break;
	    case 'm' : ElevatorProject.LAG_TIME *= 2.0; break;
	    }


	switch(window.getMouseState())
	    {
	    default : break;
	    case 1: // mouse is pressed
		{
		    click(building, window);
		}break;
	    }
    }


    /**
     * \brief Dessine l'interface
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
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
	int position =(int)( (float)elevator.positionByHeight / (float)(Defines.FLOOR_HEIGHT_METERS)  ); 
	int nextPosition = ( elevator.direction > 0 && elevator.direction + position >= 0 ) 	    ? position + elevator.direction : position;
			    
	nextPosition = (elevator.direction == 0) ? 0 : nextPosition; 	
	
	// LEAVERS
	int nQuit = elevator.destinationList[nextPosition];	
	window.drawText((int)(0.1 * menuWidth), (int)(menuY + menuHeight * 0.1), window.COLABA8x13, "Sortent au prochain etage : " + Integer.toString(nQuit) );

	// ENTERS
	int nEnter = elevator.waitingList[nextPosition];
	window.drawText((int)(0.1 * menuWidth), (int)(menuY + menuHeight * 0.2), window.COLABA8x13, "Veulent monter au prochain etage : " + Integer.toString(nEnter) );

	// TIME TO WAIT
	window.setColor(64, 64, 0);
	
	// TIME MULTIPLICATOR
	window.drawText(
			(int)(0.6 * menuWidth), //x
			(int)(menuY + menuHeight * 0.3), //y
			window.COLABA8x13, // format
			"x : " + Integer.toString((int)(1000.0 / ElevatorProject.LAG_TIME)) // text
			);

	// ELAPSED TIME 
	window.drawText(
			(int)(0.6 * menuWidth), //x
			(int)(menuY + menuHeight * 0.1), //y
			window.COLABA8x13, // format
			"Time : " + Integer.toString(ElevatorProject.timePassed) // text
			);
	//
    }


    /**
     * \brief Dessine les fleches
     * \authors Ervan Silvert
     */
    public static void drawArrows(ElevatorProject.Building building, EcranGraphique window)
    {
	final int arrowSize = 30 ;
	
	// UPPER ARROW
	Graphics.Rectangle up = new Graphics.Rectangle();
	up.x = Defines.WINDOW_SIZE - 2 * arrowSize;
	up.width = arrowSize;
	up. y = 2.0 / 3.0 * Defines.WINDOW_SIZE +  arrowSize ;
	up.height = arrowSize ;
	if(building.elevator.direction == 1)
	    {Graphics.fillRectangle(up, window, Graphics.createNewColor(255,255,255));}
	else
	    {Graphics.drawRectangle(up, window, Graphics.createNewColor(255,255,255));}
	

	// LOWER ARROW
	Graphics.Rectangle low = new Graphics.Rectangle();
	low.x = Defines.WINDOW_SIZE - 2 * arrowSize;
	low.width = arrowSize;
	low. y = 2.0 / 3.0 * Defines.WINDOW_SIZE + 3 * arrowSize ;
	low.height = arrowSize ;
	if(building.elevator.direction == -1)
	    {Graphics.fillRectangle(low, window, Graphics.createNewColor(255,255,255));}
	else
	    {Graphics.drawRectangle(low, window, Graphics.createNewColor(255,255,255));}
	

	// MIDDLE BUTTON
	Graphics.Rectangle middle = new Graphics.Rectangle();
	middle.x = Defines.WINDOW_SIZE - 2 * arrowSize;
	middle.width = arrowSize;
	middle. y = 2.0 / 3.0 * Defines.WINDOW_SIZE + 2 * arrowSize ;
	middle.height = arrowSize ;
	if(Graphics.focusOnElevator)
	    {Graphics.fillRectangle(middle, window, Graphics.createNewColor(0, 0, 128));}
	else
	    {Graphics.drawRectangle(up, window, Graphics.createNewColor(255,255,255));}
    }


    /**
     * \brief Inactif la
     * \authors Ervan Silvert
     */
    public static void click(ElevatorProject.Building building, EcranGraphique window)
    {
	// Update the mouse Position
	int mX = -1, mY = -1 ;
	updateMousePosition(mX, mY, window);	
	// 
    }


    /**
     * \brief Inactif la
     * \authors Ervan Silvert
     */
    public static void updateMousePosition(int x, int y, EcranGraphique window)
    {
	x = window.getMouseX();
	y = window.getMouseY();
    }


}
