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
	Elevator mainElevator;
	Floor[] floors;
    };
	
    static class Elevator
    {
	short CAPACITY=-1; // Can't be constant due to hand initialization 
	short direction=0; //-1,0,1 /!\ 
	short positionFloor=0;
	short passengers=0;
	boolean[] waitingList;
    };

    static class Floor
    {
	double probability=0.0;
	short passengers=0;
    };
	
	
    /* =========== INITIALIZATION FUNCTIONS ============ */
    static Floor createNewFloor(double appearingProbability)
    // Return a new floor with all members initialized.
    {
	Floor floor = new Floor();
	floor.probability=appearingProbability;
	return floor;
		
    }
	
    static Building createNewBuilding(short Nfloor, double appearingProba)
    // Return a new building with all members initialized.
    {
	Building building = new Building();
	building.floors=new Floor[Nfloor];
	building.mainElevator=createNewElevator(Nfloor);	
		
	for(short i=0;i<Nfloor;i++) // function ?
	    {
		building.floors[i]=createNewFloor(appearingProba);
			
	    }
	return building;
    }
	
    static Elevator createNewElevator(short Nfloor)
    // Return a new elevator with all members initialized.
    {
	Elevator elevator = new Elevator();
	elevator.waitingList=new boolean[Nfloor];
		
	for(short i=0;i<Nfloor;i++)//function ?
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
    static double askForProbabilty()
    {
	Ecran.afficherln("Veuillez saisir la probabilité d'apparition d'un nouvel individu à un étage. (entre 0 et 1 ).");
	double probability=Clavier.saisirDouble();
	verifyInBetween(probability,0,1);
	return probability;
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
	Ecran.afficherln("I updated.");
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
	Ecran.afficherln("I drew.");
    }

    public static void main(String args[])
    {
	/*=============INITIAL STATE===============*/
	Building building = createNewBuilding(askForNumberFloors(),askForProbabilty());
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
