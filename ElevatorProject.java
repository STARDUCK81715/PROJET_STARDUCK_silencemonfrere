/**
* \file ElevatorProject.java
* \brief Moteur physique - Simulation de l'ascenceur
* \authors Ervan Silvert
* \authors Paul-Armand Michaud
*/


/**
* \class ElevatorProject
* \brief Moteur physique
* \authors Ervan Silvert
* \authors Paul-Armand Michaud
*/

public class ElevatorProject
{

    /**
     * \brief Le temps entre deux update
     */
    static short LAG_TIME = 200 ; 

    /**    
     * \brief Compte le temps ecoule depuis le debut de la simulation en accord avec le sujet
     */
     static int timePassed = 0; //counting time according to the subject
	
    /* ========= CLASS DECLARATION ============== */ 

    /**
     * \class Building
     * \brief Structure servant a transporter toutes les donnees. Contient les etages et l'ascenceur.
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static class Building
    {
	Elevator elevator;
	Floor[] floors; // the whole floors
    };

    /**
     * \class Elevator
     * \brief Structure represantant l'ascenceur contenant les passagers, sa position, sa direction et son timing.
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */	
    static class Elevator
    {
	short CAPACITY = -1; // Can't be constant due to hand initialization 
	short direction = 0; // is -1 when going top, 1 going bot and 0 not moving
        short positionByHeight = 0;
	short passengers = 0;
	short[] waitingList; // The list of the floor with the number of passengers waiting
	short[] destinationList; // The list of the floor with the number of passengers inside the elevator who will quit
	boolean canGo = true;

    };

    /**
     * \class Floor
     * \brief Structure representant un etage, contenant les passagers et une probability d'apparition d'un nouveau passager
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static class Floor
    {
	double probability = Math.random() / 10.0; // Probability that someone appears
        short passengers = 0; // Number of passenger waiting
    };
      
	
	
    /* =========== INITIALIZATION FUNCTIONS ============ */	
    /**
     * \brief Constructeur de Building
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static Building createNewBuilding(short nFloor)
    // Return a new building with all members initialized.
    {
	Building building = new Building();
	building.floors = new Floor[nFloor];
	building.elevator = createNewElevator(nFloor);	
		
	for(short i = 0; i < nFloor; i++) // function ?
	    {
		building.floors[i] = new Floor();
			
	    }
	return building;
    }
    
    /**
     * \brief Constructeur d'Elevator
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */	
    static Elevator createNewElevator(short nFloor)
    // Return a new elevator with all members initialized.
    {
	Elevator elevator = new Elevator();
	elevator.waitingList = new short[nFloor];
	elevator.destinationList = new short[nFloor];
		
	for(short i = 0; i < nFloor; i++)
	    {
		elevator.waitingList[i] = 0;
		elevator.destinationList[i] = 0;
	    }

	Ecran.afficherln("Veuillez saisir la capacite max de l'ascenceur.");
	elevator.CAPACITY = Clavier.saisirShort();

	return elevator;
    }
    
    /**
     * \brief Rentre manuellement le nombre d'etage
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static short askForNumberFloors()
    // Return a valid number of floors from the user.
    {
	Ecran.afficherln("Veuillez saisir le nombre d'etages que votre building doit posseder. (entre 0 et ", Defines.MAX_LIMIT_FLOORS, " ).");
	short nFloors = Clavier.saisirShort();
	verifyInBetween(nFloors, 0, Defines.MAX_LIMIT_FLOORS);
	return nFloors;
    }

    /**
     * \brief Verifie la validite d'un nombre entre deux bornes
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static void verifyInBetween(double number, int min, int max)
    // Ask for a number until the condition is true.
    {
	while(number < min || number > max)
	    {
		Ecran.afficherln("Saisie incorrecte. Veuillez reiterer un nombre entre " , min , " et ", max);
		number = Clavier.saisirDouble();
	    }
    }


    /**
     * \brief Fait apparaitre les passagers et evoluer l'ascenceur
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static void update(Building building)
    {
        manageApparition(building); // Toss a coin to know if someone appears for each floor
	updateElevator(building) ; // Va appeler move et decharge
    }


    /**
     * \brief Gere l'apparition des passagers
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static void manageApparition(Building building)
    {
	// for each floor
	for(short i = 0; i < building.floors.length; i++)
	    {
		Floor currentFloor = building.floors[i];

		// if we have a good drop
		if(tossProbility(currentFloor.probability) == true)
		    {
			// we had a passenger
			currentFloor.passengers += 1;
		    }
	    }
    }


    /**
     * \brief Renvoie aleatoirement vrai ou faux selon un facteur de probabilite
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static boolean tossProbility(double probability)
    // toss a coin 
    {
	return Math.random() <= probability;
    }


    /**
     * \brief Met a jour la liste d'attente de l'asecenceur
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static void manageCalls(Building building)
    // each update floor the elevator's waiting list
    {
	for(short i = 0; i < building.floors.length; i++)
	    {
		Floor currentFloor = building.floors[i];
		building.elevator.waitingList[i] = currentFloor.passengers;
	    }
    }


    /**
     * \brief Met a jour l'ascenceur et son mouvement
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static void updateElevator(Building building) 
    // Move & unstack passengers
    {
	manageCalls(building); // synchronize the waiting list

	if( building.elevator.canGo ) 	
	    {moveElevator(building.elevator);} // move the elevator

	if(building.elevator.positionByHeight % Defines.FLOOR_HEIGHT_METERS == 0)
	    // Nous sommes à un étage
	    {
		building.elevator.canGo = !unstackElevator(building.elevator);
		if( building.elevator.canGo  ) building.elevator.canGo = !stackInElevator(building); // let passengers moving
		updateDirection(building.elevator); // update the direction
	    }     
    }


    /**
     * \brief Permet a l'ascenceur de bouger
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static void moveElevator(Elevator elevator)
    {
	if(
	   (elevator.positionByHeight + elevator.direction) / Defines.FLOOR_HEIGHT_METERS <= elevator.waitingList.length - 1  // security
	   && (elevator.positionByHeight + elevator.direction) / Defines.FLOOR_HEIGHT_METERS >= 0 // security // the waiting time passed
	   ) 
	    {
		elevator.positionByHeight += elevator.direction;
	    }
    }


    /**
     * \brief Decharge l'ascenceur
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static boolean unstackElevator(Elevator elevator)
    // Let the passengers going out
    {
	// Number of passengers that wanted to go out here
	short nbQuit = elevator.destinationList[ elevator.positionByHeight / Defines.FLOOR_HEIGHT_METERS ];

	if(nbQuit > 0)
	    // If someone want to exit then someone exits... :p
	    {
		elevator.passengers -= 1;
		elevator.destinationList[ elevator.positionByHeight / Defines.FLOOR_HEIGHT_METERS ] -= 1;
		return true;
	    }
	return false;
    }


    /**
     * \brief Charge l'ascenceur
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static boolean stackInElevator(Building building)
    // Let the passengers going in
    {
	Elevator elevator = building.elevator;
	Floor currentFloor = building.floors[ elevator.positionByHeight / Defines.FLOOR_HEIGHT_METERS ];
	short nbIn = (short)Math.min( elevator.CAPACITY - elevator.passengers, currentFloor.passengers); // if their is still a litle place
	
	if(nbIn > 0)
	    // If someone want to enter then someone enters... :p
	    {
		elevator.passengers += 1;
		currentFloor.passengers -= 1;
		addRandomLocation(elevator);
		return true;
	    }
	return false;
    }


    /**
     * \brief Ajoute aleatoirement un appel a la liste de l'ascenceur
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static void addRandomLocation(Elevator elevator)
    // Add randomly a location to the elevator(s destination list
    {
	short currentPosition = (short)( elevator.positionByHeight / Defines.FLOOR_HEIGHT_METERS );
	short randomLocation = -1;
	do
	    // The new destination shouldn't be the same as the actual floor
	    {
		randomLocation = (short)(Math.random() * elevator.waitingList.length);
		
	    } while( randomLocation == currentPosition || elevator.waitingList.length <= 1);
	
	elevator.destinationList[randomLocation]++;
    }

    /**
     * \brief Met a jour la direction de l'ascenceur conformement au cahier des charges.
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static void updateDirection(Elevator elevator)
    // The main algorithm, it decides where the elevator goes
    {
	short newDirection = 0 ;
	short position = (short)(elevator.positionByHeight / Defines.FLOOR_HEIGHT_METERS);
	
	if(elevator.direction != 0)
	    // we were moving, then we look forward if someone is waiting 
	    { 
		for(short i = (short)(position + elevator.direction); i < elevator.waitingList.length && i >= 0; i += elevator.direction)
		    {
			if(elevator.waitingList[i] > 0 || elevator.destinationList[i] > 0)
			    {
				newDirection = elevator.direction;
			    }
		    }
	
		// Then if necessary we see in the other direction if there is someone calling the elevator
		if(newDirection == 0) // (no one is waiting in the past direction)
		    {
			for(short i = (short)(position + -elevator.direction ); i < elevator.waitingList.length && i >= 0; i -= elevator.direction)
			    {
				if(elevator.waitingList[i] > 0 || elevator.destinationList[i] > 0)
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
		for(short i = 0; i < elevator.waitingList.length; i++)
		    {
			if(elevator.waitingList[i] > 0 || elevator.destinationList[i] > 0)
			    {
				elevator.direction = signOf((short)(i - elevator.positionByHeight / Defines.FLOOR_HEIGHT_METERS));
			    }
		    }
	    }
    }


    /**
     * \brief Retourne le signe d'une expression
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    static short signOf(short i)
    {
	if(i < 0){return -1;}
	if(i > 0){return 1;}
	if(i == 0){return 0;}
	return 0; // Oh, java ...
    }

    /**
     * \brief Initialistation et boucle de la simulation
     * \authors Ervan Silvert
     * \authors Paul-Armand Michaud
     */
    public static void main(String args[])
    {
	/*=============INITIAL STATE===============*/
	Building building = createNewBuilding(askForNumberFloors());
	EcranGraphique window = Graphics.createNewEcranGraphique();

	long  loopTime = 0; // record the time passed during a loop
	double toSecond = 0; // record the wolhe time passed to know when a second passed.
	    
	Graphics.Point stars[] = new Graphics.Point[Defines.N_STARS];
	Graphics.initStars(stars);

	while(window.getKey() != Defines.QUIT_CHARACTER) 
	    {  
		 // Get the start time
		loopTime =  System.currentTimeMillis();
		
		// We update the data (and the appearing ratio) every second
		if(toSecond >= LAG_TIME) 
		    {
			update(building); 
			toSecond = 0; // Reinit to 0 the time passed
			timePassed += 1; // TODO EN ARGUMENT 
			
		    } 

		Graphics.draw(building, window, toSecond, stars); // But we draw all the time
		Interface.update(building, window); // update the textuals drawings

		window.flush(); // print the drawings 

		toSecond += System.currentTimeMillis() - (double)loopTime; // counting the passed time.
	    }

	window.exit();
	
	/*=============UPDATE==================*/
		
    }
};
