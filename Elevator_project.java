// SILVERT Ervan & MICHAUD Paul-Armand

public class Elevator_project
{
    /* ======== CONSTANTS ======= */
    final static short MAX_LIMIT_FLOORS = 100; // Max number of floors in a building
    final static byte WINDOW_X=0;
    final static byte WINDOW_Y=0;
    final static int WINDOW_SIZE=1000;
    final static int RESOLUTION=WINDOW_SIZE;
    final static String WINDOW_TITLE="Elevator_simulation";
    final static char QUIT_CHARACTER='q';
    // VISUAL
    final static double BORDER_GAP = 0.1 * WINDOW_SIZE ;
    final static double FLOOR_WIDTH = WINDOW_SIZE * 0.35 ;
    final static double FLOOR_HEIGHT = WINDOW_SIZE * 0.1 ;
    final static double ELEVATOR_WIDTH = WINDOW_SIZE - 2 * FLOOR_WIDTH - 2 * BORDER_GAP - 2  ;
    final static double ELEVATOR_HEIGHT = FLOOR_HEIGHT  ;
    final static double PASSENGER_WIDTH = 2;
    final static double PASSENGER_HEIGHT = 2 * FLOOR_HEIGHT / 3;
    final static int CHAR_WIDTH = 10;
    final static int CHAR_HEIGHT = 15;
    /* ========================= */


    /* ========= CLASS DECLARATION ============== */ 
    static class Building
    {
	Elevator elevator;
	Floor[] floors;
    };
	
    static class Elevator
    {
	short CAPACITY=-1; // Can't be constant due to hand initialization 
	short direction=0; //-1,0,1 /!\ 
        short positionBy3=0;
	short passengers=0;
	short[] waitingList;
	short[] destinationList;
    };

    static class Floor
    {
	double probability=Math.random(); // TO DO 
        short passengers=0;
    };
	
	
    /* =========== INITIALIZATION FUNCTIONS ============ */	
    static Building createNewBuilding(short nFloor)
    // Return a new building with all members initialized.
    {
	Building building = new Building();
	building.floors=new Floor[nFloor];
	building.elevator=createNewElevator(nFloor);	
		
	for(short i=0;i<nFloor;i++) // function ?
	    {
		building.floors[i]=new Floor();
			
	    }
	return building;
    }
	
    static Elevator createNewElevator(short nFloor)
    // Return a new elevator with all members initialized.
    {
	Elevator elevator = new Elevator();
	elevator.waitingList=new short[nFloor];
	elevator.destinationList=new short[nFloor];
		
	for(short i=0;i<nFloor;i++)//function ?
	    {
		elevator.waitingList[i]=0;
		elevator.destinationList[i]=0;
	    }

	Ecran.afficherln("Veuillez saisir la capacite max de l'ascenceur.");
	elevator.CAPACITY = Clavier.saisirShort();

	return elevator;
    }
    
    static short askForNumberFloors()
    // Return a valid number of floors from the user.
    {
	Ecran.afficherln("Veuillez saisir le nombre d'étages que votre building doit posseder. (entre 0 et ",MAX_LIMIT_FLOORS, " ).");
	short nFloors=Clavier.saisirShort();
	verifyInBetween(nFloors,0,MAX_LIMIT_FLOORS);
	return nFloors;

    }

    static void verifyInBetween(double number, int min, int max)
    // Ask for a number until the condition is true.
    {
	while(number < min || number > max)
	    {
		Ecran.afficherln("Saisie incorrecte. Veuillez réiterer un nombre entre " , min , " et ", max);
		number=Clavier.saisirDouble();
	    }
    }

    static void update(Building building)
    {
	/*
	  Ajoute les nouvelles personnes à chaque étages
	  Gestion des appels (building dit a elevator qui attend et ou)
	  Elevator bouge
	  Elevator et se charge/décharge
	*/
        manageApparition(building);
	manageCalls(building);
	updateElevator(building) ; // Va appeler move et décharge
    }

    static void manageApparition(Building building)
    {
	for(short i=0;i<building.floors.length;i++)
	    {
		Floor currentFloor = building.floors[i];
		if(tossProbility(currentFloor.probability) == true)
		    {
			currentFloor.passengers += 1;
		    }
	    }
    }

    static boolean tossProbility(double probability)
    {
	return Math.random()<=probability;
    }

    static void manageCalls(Building building)
    {
	for(short i=0;i<building.floors.length;i++)
	    {
		Floor currentFloor = building.floors[i];
		building.elevator.waitingList[i]=currentFloor.passengers;
	    }
    }

    static void updateElevator(Building building) 
    // Move & unstack
    {
	moveElevator(building.elevator); 
	if(building.elevator.positionBy3%3 == 0)
	    {
		unstackElevator(building.elevator);
		stackInElevator(building);
		updateDirection(building.elevator);
	    }
	Ecran.afficherln("Direction : ", building.elevator.direction);

	
        
    }

    static void moveElevator(Elevator elevator)
    {
	if((elevator.positionBy3+elevator.direction)/3 <=elevator.waitingList.length-1 && (elevator.positionBy3+elevator.direction)/3 >=0) 
	    {
		elevator.positionBy3+=elevator.direction;
	    }
	
    }
    static void unstackElevator(Elevator elevator)
    {
	Ecran.afficherln(" Passengers : " , elevator.passengers , ". At floor " , elevator.positionBy3/3, " how many people down : " , elevator.destinationList[elevator.positionBy3/3]);
	short nbQuit=elevator.destinationList[elevator.positionBy3/3];
	elevator.passengers-=nbQuit;
	elevator.destinationList[elevator.positionBy3/3]=0;
    }

    static void stackInElevator(Building building)
    {
	Elevator elevator = building.elevator;
	Floor currentFloor = building.floors[elevator.positionBy3/3];

	while(elevator.passengers<elevator.CAPACITY && currentFloor.passengers>0)
	    {
		currentFloor.passengers-=1;
		elevator.passengers++;
		addRandomLocation(elevator);
	    }

    }

    static void addRandomLocation(Elevator elevator)
    {
	short currentPosition = (short)(elevator.positionBy3/3);
	short randomLocation = -1;
	do
	    {
		randomLocation = (short)(Math.random()*elevator.waitingList.length);
	    }while(randomLocation==currentPosition);
	elevator.destinationList[randomLocation]++;
    }

    static void updateDirection(Elevator elevator)
    {
	short newDirection = 0;
	short position = (short)(elevator.positionBy3/3);
	
	if(elevator.direction!=0)
	    {
		// First we see in the same direction if there is someone calling the elevator
		for(short i=(short)(position+signOf(elevator.direction));i<elevator.waitingList.length && i>=0;i+=elevator.direction)
		    {
			if(elevator.waitingList[i]>0 || elevator.destinationList[i]>0)
			    {
				newDirection = elevator.direction;
			    }
		    }
	
		// Then if necessary we see in the other direction if there is someone calling the elevator
		if(newDirection == 0)
		    {
			for(short i=(short)(position+signOf((short)(-elevator.direction)));i<elevator.waitingList.length && i>=0;i+= - elevator.direction)
			    {
				if(elevator.waitingList[i]>0 || elevator.destinationList[i]>0)
				    {
					newDirection = (short)(- elevator.direction);
				    }
			    }
	
		    }
		// We change the direction 
		elevator.direction = newDirection ; 
	    }
	else
	    {
		for(short i=0;i<elevator.waitingList.length;i++)
		    {
			if(elevator.waitingList[i]>0)
			    {
				elevator.direction = signOf((short)(i-elevator.positionBy3/3));
			    }
		    }
	    }

    }

    static short signOf(short i)
    {
	if(i<0){return -1;}
	if(i>0){return 1;}
	if(i==0){return 0;}
	return 0;
    }


    /* ========== VISUAL ================ */
    // STRUCTURES 
    static class Rectangle
    {
	double width = FLOOR_WIDTH ; // Usual floor size
	double height = FLOOR_HEIGHT ; // Usual floor size
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

    static void drawFloors(Building building, EcranGraphique window)
    {
	for(short i = 0 ; i < building.floors.length ; i++)
	    {
		int lastLevel = building.floors.length ;
		Floor currentFloor = building.floors[lastLevel-1-i];

		Rectangle left = new Rectangle();
		left.x = BORDER_GAP ;
		left.y = (i+1) * FLOOR_HEIGHT ; // i+1 in order to see the sky

		Rectangle right = new Rectangle();
		right.x = WINDOW_SIZE - BORDER_GAP - FLOOR_WIDTH ;
		right.y = (i+1) * FLOOR_HEIGHT ; // i+1 in order to see the sky

		drawRectangle(left,window,createNewColor(4,139,154)); // Here is the floor color
		drawRectangle(right,window,createNewColor(4,139,154));
		drawPassengersInFloor(left,right,currentFloor.passengers,window);
		
		// Drawing the number of passengers
		String writting = "Waiting :"+Integer.toString((int)(currentFloor.passengers));
		drawPanel(left.x+left.width-writting.length()*CHAR_WIDTH,left.y + 1/6.0*left.height,writting,window, createNewColor(150,150,150));

		// Drawing the number of the floor
		String index = "Floor number:" + Integer.toString((int)(i));
		drawPanel(right.x+right.width/2-writting.length()*CHAR_WIDTH/2,right.y + 1/6.0*right.height,index,window, createNewColor(4,139,154));

	    }


    }

    static void drawPanel(double x, double y , String writting, EcranGraphique window, Color color)
    {
	window.setColor(color.r,color.g,color.b);
	int size = writting.length();
	window.drawText((int)(x),(int)(y), window.COLABA8x13,writting);
	window.drawRect((int)(x-5),(int)(y-CHAR_HEIGHT),(int)(size*CHAR_WIDTH+5),CHAR_HEIGHT+2);
    }
    
    static void drawElevator(Elevator elevator, EcranGraphique window, int dt)
    {
	Rectangle rectangle = new Rectangle();
	int lastLevel = elevator.waitingList.length;

	// Elevator
	rectangle.x = BORDER_GAP + FLOOR_WIDTH+1;
	rectangle.y = (lastLevel)*FLOOR_HEIGHT - (1/3.0 * elevator.positionBy3) * FLOOR_HEIGHT - elevator.direction*dt/1000.0 * FLOOR_HEIGHT/3.0;
	rectangle.width = ELEVATOR_WIDTH;
	rectangle.height = ELEVATOR_HEIGHT;
	drawRectangle(rectangle, window, createNewColor(128,0,0)); // Here is the elevator color 

	// Cables
	window.drawLine((int)(rectangle.x +0.3*rectangle.width) ,(int)(FLOOR_HEIGHT),(int)(rectangle.x + 0.3*rectangle.width), (int)(rectangle.y));
	window.drawLine((int)(rectangle.x +0.6*rectangle.width) ,(int)(FLOOR_HEIGHT),(int)(rectangle.x + 0.6*rectangle.width), (int)(rectangle.y));

	//Passengers 
	for(short i=0;i<elevator.passengers;i++)
	    {
	        double xOffset=0;
		double numberMaxInWidth = (rectangle.width / PASSENGER_WIDTH);
		xOffset = i / numberMaxInWidth;
		    drawPassenger(rectangle.x+(i%numberMaxInWidth)*PASSENGER_WIDTH+xOffset,rectangle.y+rectangle.height/3.0,window,createNewColor(0,128,255));
	    }
	// Drawing panel 
	String nbPass = Integer.toString((int)(elevator.passengers));
	double ratio = (double)(elevator.passengers) / (double)(elevator.CAPACITY);
	Ecran.afficherln("Color : " , 255.0*(1.0-ratio) );
	drawPanel(rectangle.x + 1/3.0 * rectangle.width, rectangle.y+2,nbPass,window,createNewColor(255,255.0*(1.0-ratio),255.0*(1.0-ratio)));

	
    }

    static void drawBuilding(Building building, EcranGraphique window)
    {
	Rectangle rectangle = new Rectangle();
	int nFloors = building.floors.length;

	rectangle.x = BORDER_GAP -1 ; // -1 to do not draw twice on the same pixel
	rectangle.y = FLOOR_HEIGHT -1  ; // same
 	rectangle.width = WINDOW_SIZE - 2 * BORDER_GAP + 2; // +2 same
	rectangle.height = FLOOR_HEIGHT * nFloors;
	drawRectangle(rectangle, window, createNewColor(255,255,255)); // Here is the building color 
    }

    static void drawPassengersInFloor(Rectangle left, Rectangle right, int passengers, EcranGraphique window)
    {
	for(short i=0;i<passengers;i++)
	    {
		Color passengerColor = createNewColor(Math.min(i,255),Math.max(0,255-i),0);
		if(i%2==0) // Draw once right, once left
		    {
	        	double passengerX = left.x+left.width-i*PASSENGER_WIDTH;
		        while(passengerX <= left.x ) 
			    {
	        		passengerX = passengerX - 1 + left.width  ; 
	        	    }
			double passengerY = left.y+left.height*1/3;
			drawPassenger(passengerX,passengerY,window, passengerColor);
		    }

		else // Draw once right, once left
		    {
			double passengerX = right.x+i*PASSENGER_WIDTH;
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
	window.drawLine((int)x,(int)y,(int)x,(int)(y+PASSENGER_HEIGHT));
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
	window.init(WINDOW_X, WINDOW_Y,WINDOW_SIZE,WINDOW_SIZE,RESOLUTION,RESOLUTION,WINDOW_TITLE);
	window.clear();
	return window;
    }

    static void draw(Building building, EcranGraphique window, int dt)
    {
	window.clear();
	
	drawBuilding(building,window); // draw the main square (white)
	drawFloors(building,window); // draw all the floors (TO DO : adapter la position des floors après 10)
	drawElevator(building.elevator,window,dt); // draw the elevator regarding the current time
	
	window.flush(); // print the drawings 
    }

    public static void main(String args[])
    {
	/*=============INITIAL STATE===============*/
	Building building = createNewBuilding(askForNumberFloors());
	EcranGraphique window = createNewEcranGraphique();

	long loopTime = 0; // record the time passed during a loop
	int toSecond = 0; // record the wolhe time passed to know when a second passed.
	while(window.getKey()!=QUIT_CHARACTER) 
	    {
		loopTime =  System.currentTimeMillis(); // Get the start time
		
		if(toSecond >= 1000) // We update the data (and the appearing ratio) every second
		    {
			update(building); 
			toSecond = 0; // Reinit to 0 the time passed
		    } 

		draw(building,window,toSecond); // But we draw all the time

		toSecond = (int)(System.currentTimeMillis() - loopTime) + toSecond; // counting the passed time.
		
	    }
	window.exit();
	
	/*=============UPDATE==================*/
		
    }
};
