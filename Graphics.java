public class Graphics
{

    /* ==================================================================================================== */ 
    /*                                               STATIC VARIABLES                                       */
    /* ==================================================================================================== */

    /*
      Nous ne pouvons pas nous permettre de faire passer ces variables en paramètres.
      Ces variables nécessitent de garder leur valeur indépendemment des tours de boucles.
      De plus, ils sont supposés être accessibles à tout moment et dépendent de leur précédente valeur.
      Le passage par référence n'existant pas en Java, la seul solution consisterait à construit un type agrégé afin de stocker ces trois variables. Cette solution a été rejetée car cele necessite un changement de toute l'architecture des fonctions et une structure spécifique à un type de donnée parfaitement caractérisées par le mot "static". 
      Rappelons que nous restons éloignés de la POO et que l'ensemble de notre programme justifie notre savoir de passer des arguments aux fonctions. 

      Merci.
    */ 
    static double yOffset = 0;
    static boolean focusOnElevator = true;
    static short viewDirection = 0; // comme elevator.direction : -1 vers le bas, 0 no move, 1 vers le haut


    /* ==================================================================================================== */ 
    /*                                                  STRUCTURES                                          */
    /* ==================================================================================================== */

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
    
    static class Point
    // A point in this project only need the coordinates and an offset to know how to be place regarding the scrolling
    {
	// We decided to put stars randomly on the whole background width and not only in a bloc
	double x = Math.random() * Defines.WINDOW_SIZE; 

	// We decided to put the stars only behind a strict number of floors, to keep some consistency about the style.
	// (3 is the number of floors which might have stars behind it. Defining a constant could be too much for this) 
	double y = Math.random() * Defines.FLOOR_HEIGHT * 3;

	// This attribute allows a point to be "constant" like the stars over the first three floors
	double offset = -1;

    }

    /* ==================================================================================================== */ 
    /*                                                  METHODS                                             */
    /* ==================================================================================================== */


    static Color createNewColor(double r, double g, double b)
    // Construct a new color regard the RGB code
    {
	Color color = new Color();
	color.r = (int)(r);
	color.g = (int)(g);
	color.b = (int)(b);
	return color;
    }
    
    
    static void drawFloors(ElevatorProject.Building building, EcranGraphique window)
    // This method call all the methods regarding the floors aspect.
    {
	for(short i = 0 ; i < building.floors.length ; i++)
	    {
		// shortcuts
		int lastLevel = building.floors.length ;
		ElevatorProject.Floor currentFloor = building.floors[lastLevel-1-i];

		// Each floor is cut in two, left and right side
		Rectangle left = new Rectangle();
		left.x = Defines.BORDER_GAP ;
		left.y = yOffset + (i+1) * Defines.FLOOR_HEIGHT ; // i+1 in order to see the sky

		Rectangle right = new Rectangle();
		right.x = Defines.WINDOW_SIZE - Defines.BORDER_GAP - Defines.FLOOR_WIDTH ;
		right.y = yOffset + (i+1) * Defines.FLOOR_HEIGHT ; // i+1 in order to see the sky
		
		// If the floor is in the screen, then we draw it. 
		if(left.y >= -Defines.FLOOR_HEIGHT && left.y <=  Defines.WINDOW_SIZE ) 
		    {
			// FLOOR COLOR HERE 
			drawRectangle(left, window,createNewColor(216,156,96)); 
			drawRectangle(right, window,createNewColor(216,156,96));
			
			    //balcon
			//window.setColor(216,156,96);
			
			if(i >0)
			{
				//left side
			window.drawLine((int)left.x,(int)left.y,(int)left.x-50,(int)left.y);
			window.drawLine((int)left.x,(int)(left.y-0.5*Defines.FLOOR_HEIGHT),(int)left.x-50,(int)(left.y-0.5*Defines.FLOOR_HEIGHT));
				
				//right side 
			window.drawLine((int)(right.x+Defines.FLOOR_WIDTH),(int)right.y,(int)(right.x+Defines.FLOOR_WIDTH+50),(int)right.y);
			window.drawLine((int)(right.x+Defines.FLOOR_WIDTH),(int)(right.y-0.5*Defines.FLOOR_HEIGHT),(int)(right.x+Defines.FLOOR_WIDTH+50),(int)(right.y-0.5*Defines.FLOOR_HEIGHT));
				
			    for(int j=0;j<=50;j+=10)
			    {
				window.drawLine((int)left.x-j,(int)left.y,(int)left.x-j,(int)left.y+(int)(-0.5*Defines.FLOOR_HEIGHT)); //drawing left pillars
				window.drawLine((int)(right.x+Defines.FLOOR_WIDTH+j),(int)right.y,(int)(right.x+Defines.FLOOR_WIDTH+j),(int)right.y+(int)(-0.5*Defines.FLOOR_HEIGHT));//drawing right pillars
			    }
			}
			drawPassengersInFloor(left, right,currentFloor.passengers, window);
		
			// Drawing the number of passengers (textual)
			String nbPassenger = "Waiting :"+Integer.toString((int)(currentFloor.passengers));
			drawPanel(left.x + left.width - nbPassenger.length() * Defines.CHAR_WIDTH, // x
				  left.y + 1.0 / 6.0 * left.height, // y
				  nbPassenger, // what
				  window, // where
				  createNewColor(150,150,150) // which color
				  );
		
			//Drawing the time to wait 
			drawPanel(left.x + left.width - nbPassenger.length() * Defines.CHAR_WIDTH, // x
				  left.y + 1.0 / 6.0 * left.height, // y
				  Long.toString(building.elevator.timeToWait), // what
				  window, // where 
				  createNewColor(150,150,150) // which color
				  );

			// Drawing the number of the floor
			String index = "Floor number:" + Integer.toString((int)(lastLevel - i));
			drawPanel(right.x + right.width / 2 - nbPassenger.length() * Defines.CHAR_WIDTH / 2, // x
				  right.y + 1.0 / 6.0 * right.height, // y
				  index, // what
				  window, // where
				  createNewColor(4,139,154) // which color
				  );
		    
			//Drawing floor probability
			drawPanel(right.x + right.width / 2 - nbPassenger.length() * Defines.CHAR_WIDTH / 2, // x
				  right.y + 1.0 / 3.0 * right.height, // y
				  ("Probability: " + Double.toString( (int)(1000 * currentFloor.probability) / 100.0)), // what
				  window, // where
				  createNewColor(150,150,150)); // which color
		    }

	    }


    }

    static void drawPanel(double x, double y , String writting, EcranGraphique window, Color color)
    // Draws a text in a rectangle. A panel somehow.
    {
	window.setColor(color.r,color.g,color.b);
	int size = writting.length();
	window.drawText((int)(x),(int)(y), window.COLABA8x13,writting);
	window.drawRect((int)(x-5),(int)(y-Defines.CHAR_HEIGHT),(int)(size*Defines.CHAR_WIDTH+5),Defines.CHAR_HEIGHT+2);
    }
    
    static void drawElevator(ElevatorProject.Elevator elevator, EcranGraphique window, double dt)
    // Draws everything regarding the elevator.
    {
	// shortcuts
	Rectangle rectangle = new Rectangle();
	int lastLevel = elevator.waitingList.length;

	// Positionning && Sizing
	rectangle.width = Defines.ELEVATOR_WIDTH;
	rectangle.height = Defines.ELEVATOR_HEIGHT;
	rectangle.x = Defines.BORDER_GAP + Defines.FLOOR_WIDTH + 1; // GAP + FLOOR + 1 

	double position = lastLevel - (float)(elevator.positionByHeight) / (float)(Defines.FLOOR_HEIGHT_METERS) ; // FLOOR NUMBER
	rectangle.y = yOffset + Defines.FLOOR_HEIGHT * (position); // POSITION * FLOORS_HEIGH

	// INTERPOLATING
	double x = dt / 1000.0; // x as f(x) = interpolate(x)
	double interpolation = 1 * x;

	// if the elevator is moving // TODO add a bool in elevator
	if(System.currentTimeMillis() > elevator.timeAtArrive + elevator.timeToWait)
	    {
		rectangle.y -= (float)(elevator.direction) * interpolation / (float)Defines.FLOOR_HEIGHT_METERS * Defines.FLOOR_HEIGHT;		
	    }

	drawRectangle(rectangle, window, createNewColor(128,0,0)); 

	// Drawing the cables
	window.drawLine((int)(rectangle.x +0.3*rectangle.width), (int)(yOffset+Defines.FLOOR_HEIGHT),(int)(rectangle.x + 0.3*rectangle.width), (int)(rectangle.y));
	window.drawLine((int)(rectangle.x +0.6*rectangle.width), (int)(yOffset+Defines.FLOOR_HEIGHT),(int)(rectangle.x + 0.6*rectangle.width), (int)(rectangle.y));

	//Passengers 
	// TODO : exploiter tout l'espace pour chaque passager 
	for(short i = 0; i < elevator.passengers; i++)
	    {
		double xOffset = 0;
		double numberMaxInWidth = (rectangle.width / Defines.PASSENGER_WIDTH);
		xOffset = i / numberMaxInWidth;
		drawPassenger(rectangle.x + (i % numberMaxInWidth) * Defines.PASSENGER_WIDTH + xOffset, rectangle.y + rectangle.height / 3.0, window, createNewColor(0, 128, 255));
	    }

	// Drawing number of passenger (its colors tends to the red with the number of passengers)
	String nbPass = Integer.toString((int)(elevator.passengers));
	double ratio = (double)(elevator.passengers) / (double)(elevator.CAPACITY); // here is the tend-tion
	drawPanel(rectangle.x + 1/3.0 * rectangle.width, rectangle.y + 2, nbPass,window, createNewColor(255 ,255.0 * (1.0 - ratio), 255.0 * (1.0 - ratio)));


	// updating focusing elevator
	// TO DO : sortir ca de la, aucun rapport quoi :p 
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
    // Calls all the methods to draw a building and draw its skull.
    {
	Rectangle rectangle = new Rectangle();
	int nFloors = building.floors.length;

	rectangle.x = Defines.BORDER_GAP - Defines.MARGIN+2; // MARGIN to do not draw twice on the same pixel
	rectangle.y = yOffset + Defines.FLOOR_HEIGHT - Defines.MARGIN +2 ; // same
	rectangle.width = Defines.WINDOW_SIZE - 2 * Defines.BORDER_GAP + 2 * Defines.MARGIN-4; // + 2 same
	rectangle.height = Defines.FLOOR_HEIGHT * nFloors;
	fillRectangle(rectangle, window, createNewColor(232, 214, 163)); // Here is the building color
    }

    static void drawPassengersInFloor(Rectangle left, Rectangle right, int passengers, EcranGraphique window)
    {
	for(short i = 0; i < passengers; i++)
	    {
		// The passenger color tends to the red from the green 
		Color passengerColor = createNewColor(Math.min(i, 255),Math.max(0, 255-i),0);
		// We draw the passenger once on each side of the elevator.
		if(i % 2 == 0) 
		    {
			double passengerX = left.x + left.width - i * Defines.PASSENGER_WIDTH-10;
			while(passengerX <= left.x ) 
			    {
				// Here we draw the potential passagers out of the building between the others shifting them one by one.
				passengerX = passengerX - 1 + left.width  ; 
			    }
			double passengerY = left.y + left.height - Defines.PASSENGER_HEIGHT;
			drawPassenger(passengerX, passengerY, window, passengerColor);
		    }

		// We draw the passenger once on each side of the elevator.
		else 
		    {
			double passengerX = right.x + i * Defines.PASSENGER_WIDTH+10;
			while(passengerX >= right.x + right.width )
			    {
				// The same
				passengerX = passengerX + 1 - right.width ; 
			    }
			double passengerY = right.y + right.height - Defines.PASSENGER_HEIGHT;
			drawPassenger(passengerX, passengerY, window, passengerColor);
		    }
		
		
	    }
    }

    static void drawPassenger(double x, double y, EcranGraphique window, Color color)
    // Basic method to draw a passenger. All of them are drawn by this method.
    {
	window.setColor((int)(color.r), (int)(color.g), (int)(color.b));
	window.drawLine((int)x, (int)y, (int)x, (int)(y + Defines.PASSENGER_HEIGHT)); // body 
	window.fillCircle((int)x, (int)y, (int)(0.03 * Defines.FLOOR_HEIGHT)); // head 
    }

    static void drawRectangle(Rectangle rectangle, EcranGraphique window , Color color)
    // Personal method to draw our own rectangle.
    {
	window.setColor(color.r, color.g, color.b);
	window.drawRect( (int)rectangle.x, (int)rectangle.y, (int)rectangle.width, (int)rectangle.height);
    }

    static void fillRectangle(Rectangle rectangle, EcranGraphique window , Color color)
    // Personal method to draw our own filled rectangle.
    {
	window.setColor(color.r, color.g, color.b);
	window.fillRect( (int)rectangle.x, (int)rectangle.y, (int)rectangle.width, (int)rectangle.height);
    }
    

    static EcranGraphique createNewEcranGraphique()
    // Return a new EcranGraphic already initialized (with constants before).
    {
	EcranGraphique window = new EcranGraphique();
    
	window.init(Defines.WINDOW_X,
		    Defines.WINDOW_Y,
		    Defines.WINDOW_SIZE,
		    Defines.WINDOW_SIZE,
		    Defines.RESOLUTION,
		    Defines.RESOLUTION,
		    Defines.WINDOW_TITLE);

	window.clear();
	return window;
    }


    
    /* ==================================================================================================== */ 
    /*                              METHODS TO DRAW THE DETAILS                                             */
    /* ==================================================================================================== */

    static void initStars(Point stars[])
    {
	for(int i = 0; i < stars.length; i++)
	    {
		stars[i] = new Point();
	    }
    }

    static void drawSky(EcranGraphique window, Point[] stars, double yOffset)
    // Actually, draws the stars
    {
	for(int i = 0; i < stars.length; i++)
	    {
		stars[i].offset = yOffset ;

		// if the stars is in the screen
		if(
		   stars[i].y + stars[i].offset > yOffset  // if under the top of the screen
		   && stars[i].y + stars[i].offset < yOffset + Defines.RESOLUTION // and over the bottom of the screen 
		   )
		    {
			drawStar(window, stars[i]);
		    }
	    }
    }

    static void drawStar(EcranGraphique window, Point star)
    // Basic method to draw a star.
    {
	// Moving on X
	final int DIVISOR = 1000; // IMPLIES THE SPEED OF STARS

	int x = (int)star.x + (int)System.currentTimeMillis() / DIVISOR ;
	x  = x % Defines.RESOLUTION;

	// Draws
	int starRadius = 1;
	window.fillCircle(x, (int)star.y + (int)star.offset, starRadius);	
    }

    static void drawGround(EcranGraphique window, ElevatorProject.Building building, double yOffset)
    {
	    //Draws the grass on the floor
	    window.setColor(0,107,33);
	    window.fillRect(0, (int)(((building.floors.length*Defines.FLOOR_HEIGHT+Defines.BORDER_GAP)-2*Defines.FLOOR_HEIGHT)+yOffset),Defines.WINDOW_SIZE, (int)(5*Defines.FLOOR_HEIGHT));
	    
	    //draws the lamp
	    /*
	    Color colorLamp= new Color;
	    colorLamp.r=0;
	    colorLamp.g=0;
	    colorLamp.b=0;
	    */
	    
    }


    /* ==================================================================================================== */ 
    /*                                                  MAIN METHOD                                         */
    /* ==================================================================================================== */

    static void draw(ElevatorProject.Building building, EcranGraphique window, double dt, Point[] stars)
    {
	window.clear();
	Color colorTime=createNewColor(253, 63, 146);

	// CONTROL 
	char letter = window.getKey();
	switch(letter)
	    {
	    default :  viewDirection = 0; Ecran.afficherln("letter : " , (int)letter); break;
	    case 0 :  break;
	    case 'o' : {viewDirection = -1;} break; // Vers le haut
	    case 'l' : {viewDirection = 1;} break; // Vers le bas
	    case 'c' : 
		// un - centering the elevator
		{
		    focusOnElevator = !focusOnElevator;
		    yOffset = - building.elevator.positionByHeight / Defines.FLOOR_HEIGHT_METERS * Defines.FLOOR_HEIGHT; // Put the screen on the elevator on 'c' - type.
		} break;
	    }
	//

	// UPDATE THE OFFSET
	if(!focusOnElevator)
	    {
		yOffset = yOffset + (float)(-viewDirection) * Defines.BASIC_OFFSET;
	    }
	else{ /* TO DO */ /* update on the elevator drawing in order to calculate only once */ }
	//

	// DRAW
	/*
	  It is important to draw at first the farthest elements 
	  so the natural super-position about drawing method feign the z-index
	*/
	drawSky(window, stars, yOffset);
	drawGround(window,building, yOffset);
	drawBuilding(building, window); // draw the main square (white)
	drawFloors(building, window); // draw all the floors (TO DO : adapter la position des floors après 10)
	drawElevator(building.elevator, window, dt); // draw the elevator regarding the current time
	//Graphics.drawPanel(0.0,yOffset,Integer.toString(ElevatorProject.time.timePassed),window,colorTime);
	//
    }

}
