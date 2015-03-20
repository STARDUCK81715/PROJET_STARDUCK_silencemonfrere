// SILVERT Ervan & MICHAUD Paul-Armand

public class Elevator_project
{
    /* ======== CONSTANTS ======= */
    final static short MAX_LIMIT_FLOORS = 100; // Max number of floors in a building
    final static byte WINDOW_X=0;
    final static byte WINDOW_Y=0;
    final static int WINDOW_SIZE=600;
    final static int RESOLUTION=WINDOW_SIZE;
    final static String WINDOW_TITLE="Elevator_simulation";
    final static char QUIT_CHARACTER='q';
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
    };

    static class Floor
    {
	double probability=Math.random();
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
	if(elevator.direction == 0)
	    elevator.positionBy3 +=elevator.direction;
    }



    /* ========== VISUAL ================ */
    // STRUCTURES 
    static class VisualFloor
    {

    };

    static class VisualBuilding
    {

    };

    static class VisualPassenger
    {

    };

    static class VisualElevator
    {

    };

    // INITIALIZATION
    static EcranGraphique createNewEcranGraphique()
    // Return a new EcranGraphic already initialized (with constants before).
    {
	EcranGraphique window = new EcranGraphique();
	window.init(WINDOW_X, WINDOW_Y,WINDOW_SIZE,WINDOW_SIZE,RESOLUTION,RESOLUTION,WINDOW_TITLE);
	window.clear();
	return window;
    }

    static void draw(Building building, EcranGraphique window)
    {

    }

    public static void main(String args[])
    {
	/*=============INITIAL STATE===============*/
	Building building = createNewBuilding(askForNumberFloors());
	EcranGraphique window = createNewEcranGraphique();
	while(window.getKey()!=QUIT_CHARACTER) // To quit: just type 'Q' !
	    {
		update(building);
		draw(building,window);
	    }
	window.exit();
	
	/*=============UPDATE==================*/
		
    }
};
