public class Graphics
{
    /* ========== VISUAL ================ */
    // STRUCTURES 
    static double yOffset = 0;
    static boolean focusOnElevator = true;
    static short viewDirection = 0; // comme elevator.direction : -1 vers le bas, 0 no move, 1 vers le haut

    static class Rectangle
    {
	double width = Defines.FLOOR_WIDTH ; // Usual floor size
	double height = Defines.FLOOR_HEIGHT ; // Usual floor size
	double x = -1 ;
	double y = -1 ;
    }

    static class Color
    {
	int r = 0;
	int g = 0;
	int b = 0;
    }

    static Color createNewColor(double r, double g, double b)
    {
	Color color = new Color();
	color.r = (int)(r);
	color.g = (int)(g);
	color.b = (int)(b);
	return color;
    }

    static void drawFloors(ElevatorProject.Building building, EcranGraphique window)
    {
	for(short i = 0 ; i < building.floors.length ; i++)
	    {
		int lastLevel = building.floors.length ;
		ElevatorProject.Floor currentFloor = building.floors[lastLevel-1-i];

		Rectangle left = new Rectangle();
		left.x = Defines.BORDER_GAP ;
		left.y = yOffset + (i+1) * Defines.FLOOR_HEIGHT ; // i+1 in order to see the sky

		Rectangle right = new Rectangle();
		right.x = Defines.WINDOW_SIZE - Defines.BORDER_GAP - Defines.FLOOR_WIDTH ;
		right.y = yOffset + (i+1) * Defines.FLOOR_HEIGHT ; // i+1 in order to see the sky
		
		if(left.y >= -Defines.FLOOR_HEIGHT && left.y <=  Defines.WINDOW_SIZE ) 
	    // If the floor is in the window, then we draw it. 
	    {
		//			Ecran.afficherln("Dedans ." , i);
		drawRectangle(left, window,createNewColor(4, 139, 154)); // Here is the floor color
		drawRectangle(right, window,createNewColor(4, 139, 154));
		drawPassengersInFloor(left, right,currentFloor.passengers, window);
		
		// Drawing the number of passengers
		String nbPassenger = "Waiting :"+Integer.toString((int)(currentFloor.passengers));
		drawPanel(left.x+left.width-nbPassenger.length()*Defines.CHAR_WIDTH,left.y + 1/6.0*left.height,nbPassenger,window, createNewColor(150,150,150));

		// Drawing the number of the floor
		String index = "Floor number:" + Integer.toString((int)(i));
		drawPanel(right.x+right.width/2-nbPassenger.length()*Defines.CHAR_WIDTH/2,right.y + 1/6.0*right.height,index,window, createNewColor(4,139,154));
	    }

    }


}

static void drawPanel(double x, double y , String writting, EcranGraphique window, Color color)
{
    window.setColor(color.r,color.g,color.b);
    int size = writting.length();
    window.drawText((int)(x),(int)(y), window.COLABA8x13,writting);
    window.drawRect((int)(x-5),(int)(y-Defines.CHAR_HEIGHT),(int)(size*Defines.CHAR_WIDTH+5),Defines.CHAR_HEIGHT+2);
}
    
static void drawElevator(ElevatorProject.Elevator elevator, EcranGraphique window, double dt)
{
    Rectangle rectangle = new Rectangle();
    int lastLevel = elevator.waitingList.length;

    // ElevatorProject.Elevator
    rectangle.x = Defines.BORDER_GAP + Defines.FLOOR_WIDTH + 1;
    double position = lastLevel - (float)(elevator.positionByHeight) / (float)(Defines.FLOOR_HEIGHT_METERS) ;
    rectangle.y = yOffset + Defines.FLOOR_HEIGHT * (position);


    double x = dt / 1000.0;

    if(System.currentTimeMillis() > elevator.timeAtArrive + elevator.timeToWait)
	{
	    rectangle.y -= (float)(elevator.direction) * x / (float)Defines.FLOOR_HEIGHT_METERS * Defines.FLOOR_HEIGHT;		
	}





    rectangle.width = Defines.ELEVATOR_WIDTH;
    rectangle.height = Defines.ELEVATOR_HEIGHT;
    drawRectangle(rectangle, window, createNewColor(128,0,0)); // Here is the elevator color 

    // Cables
    window.drawLine((int)(rectangle.x +0.3*rectangle.width) ,(int)(yOffset+Defines.FLOOR_HEIGHT),(int)(rectangle.x + 0.3*rectangle.width), (int)(rectangle.y));
    window.drawLine((int)(rectangle.x +0.6*rectangle.width) ,(int)(yOffset+Defines.FLOOR_HEIGHT),(int)(rectangle.x + 0.6*rectangle.width), (int)(rectangle.y));

    //Passengers 
    for(short i = 0; i < elevator.passengers; i++)
	{
	    double xOffset = 0;
	    double numberMaxInWidth = (rectangle.width / Defines.PASSENGER_WIDTH);
	    xOffset = i / numberMaxInWidth;
	    drawPassenger(rectangle.x + (i % numberMaxInWidth) * Defines.PASSENGER_WIDTH + xOffset, rectangle.y + rectangle.height / 3.0, window, createNewColor(0, 128, 255));
	}
    // Drawing panel 
    String nbPass = Integer.toString((int)(elevator.passengers));
    double ratio = (double)(elevator.passengers) / (double)(elevator.CAPACITY);
    drawPanel(rectangle.x + 1/3.0 * rectangle.width, rectangle.y + 2, nbPass,window, createNewColor(255 ,255.0 * (1.0 - ratio), 255.0 * (1.0 - ratio)));


    // updating focusing elevator
    if(focusOnElevator)
	{
	    yOffset = (yOffset > -rectangle.y) 
		? yOffset - Defines.BASIC_OFFSET 
		: yOffset;
	    yOffset = (yOffset < -rectangle.y) 
		? yOffset + Defines.BASIC_OFFSET 
		: yOffset;

	}
	
}

static void drawBuilding(ElevatorProject.Building building, EcranGraphique window)
{
    Rectangle rectangle = new Rectangle();
    int nFloors = building.floors.length;

    rectangle.x = Defines.BORDER_GAP -1 ; // -1 to do not draw twice on the same pixel
    rectangle.y = yOffset + Defines.FLOOR_HEIGHT -1  ; // same
    rectangle.width = Defines.WINDOW_SIZE - 2 * Defines.BORDER_GAP + 2; // +2 same
    rectangle.height = Defines.FLOOR_HEIGHT * nFloors;
    drawRectangle(rectangle, window, createNewColor(255,255,255)); // Here is the building color 
}

static void drawPassengersInFloor(Rectangle left, Rectangle right, int passengers, EcranGraphique window)
{
    for(short i=0;i<passengers;i++)
	{
	    Color passengerColor = createNewColor(Math.min(i,255),Math.max(0,255-i),0);
	    if(i%2==0) // Draw once right, once left
		{
		    double passengerX = left.x+left.width-i*Defines.PASSENGER_WIDTH;
		    while(passengerX <= left.x ) 
			{
			    passengerX = passengerX - 1 + left.width  ; 
			}
		    double passengerY = left.y+left.height*1/3;
		    drawPassenger(passengerX,passengerY,window, passengerColor);
		}

	    else // Draw once right, once left
		{
		    double passengerX = right.x+i*Defines.PASSENGER_WIDTH;
		    while(passengerX >= right.x + right.width )
			{
			    passengerX = passengerX + 1 - right.width ; 
			}
		    double passengerY = right.y+right.height*1/3;
		    drawPassenger(passengerX,passengerY,window,passengerColor);
		}
		
		
	}
}

static void drawPassenger(double x, double y, EcranGraphique window, Color color)
{
    window.setColor(color.r,color.g,color.b);
    window.drawLine((int)x,(int)y,(int)x,(int)(y+Defines.PASSENGER_HEIGHT));
}

static void drawRectangle(Rectangle rectangle, EcranGraphique window , Color color)
{
    window.setColor(color.r,color.g,color.b);
    window.drawRect((int)rectangle.x,(int)rectangle.y,(int)rectangle.width,(int)rectangle.height);
}


// INITIALIZATION
static EcranGraphique createNewEcranGraphique()
// Return a new EcranGraphic already initialized (with constants before).
{
    EcranGraphique window = new EcranGraphique();
    window.init(Defines.WINDOW_X, Defines.WINDOW_Y,Defines.WINDOW_SIZE,Defines.WINDOW_SIZE,Defines.RESOLUTION,Defines.RESOLUTION,Defines.WINDOW_TITLE);
    window.clear();
    return window;
}

static void draw(ElevatorProject.Building building, EcranGraphique window, double dt)
{
    window.clear();

    // Control
    char letter = window.getKey();
    switch(letter)
	{
	default :  viewDirection = 0; Ecran.afficherln("letter : " , (int)letter); break;
	case 0 :  break;
	case 'o' : {viewDirection = -1;} break;
	case 'l' : {viewDirection = 1;} break;
	case 'c' : {focusOnElevator = !focusOnElevator;} break;
	}
    //

    // UPDATE
    if(!focusOnElevator)
	{
	    yOffset = yOffset + (float)(-viewDirection) * Defines.BASIC_OFFSET;
	}
    else{ /* update on the elevator drawing in order to calculate only once */ }
    //

    // Draw
    drawBuilding(building, window); // draw the main square (white)
    drawFloors(building, window); // draw all the floors (TO DO : adapter la position des floors après 10)
    drawElevator(building.elevator, window, dt); // draw the elevator regarding the current time
    //

    window.flush(); // print the drawings 
}

}
