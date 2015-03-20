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
    final static double ELEVATOR_WIDTH = WINDOW_SIZE - 2 * FLOOR_WIDTH - 2 * BORDER_GAP  ;
    final static double ELEVATOR_HEIGHT = FLOOR_HEIGHT  ;
    final static double PASSENGER_WIDTH = 2;
    final static double PASSENGER_HEIGHT = 2 * FLOOR_HEIGHT / 3;
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
	boolean[] waitingList;
	boolean[] directionList;
    };

    static class Floor
    {
	double probability=Math.random(); // TO DO 
        int passengers=0;
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
	elevator.waitingList=new boolean[nFloor];
	elevator.directionList=new boolean[nFloor];
		
	for(short i=0;i<nFloor;i++)//function ?
	    {
		elevator.waitingList[i]=false;
	    }
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
	Ecran.afficherln("Updated");
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
			currentFloor.passengers = currentFloor.passengers + 1;
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
		if(currentFloor.passengers>0)
		    {
			building.elevator.waitingList[i]=true;
		    }
		else
		    {
			building.elevator.waitingList[i]=false;
		    }
	    }
    }

    static void updateElevator(Building building) 
    // Move & unstack
    {
	moveElevator(building.elevator); // Comme l'élevator sait tout ce qu'il faut pour bouger, on n'a besoin que d'un élévator (et pas d'un building)
        
    }

    static void moveElevator(Elevator elevator)
    {
	
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

    static Color createNewColor(int r, int g, int b)
    {
	Color color = new Color();
	color.r = r;
	color.g = g;
	color.b = b;
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
	    }
    }
    
    static void drawElevator(Elevator elevator, EcranGraphique window, int dt)
    {
	Rectangle rectangle = new Rectangle();
	int lastLevel = elevator.waitingList.length;

	rectangle.x = BORDER_GAP + FLOOR_WIDTH;
	rectangle.y = (lastLevel)*FLOOR_HEIGHT - (1/3.0 * elevator.positionBy3) * FLOOR_HEIGHT - elevator.direction*dt/1000.0 * FLOOR_HEIGHT/3.0;
	Ecran.afficherln("y: " , rectangle.y);
	rectangle.width = ELEVATOR_WIDTH;
	rectangle.height = ELEVATOR_HEIGHT;
	drawRectangle(rectangle, window, createNewColor(128,0,0)); // Here is the elevator color 
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
