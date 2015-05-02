// SILVERT Ervan & MICHAUD Paul-Armand

public class ElevatorProject
{
     static int timePassed=0; //counting time according to the subject (5 seconds to up 1 floor if nobody enters or exits the elevator for exemple)
	
    /* ========= CLASS DECLARATION ============== */ 
    static class Building
    {
	Elevator elevator;
	Floor[] floors;
    };
	
    static class Elevator
    {
	short CAPACITY = -1; // Can't be constant due to hand initialization 
	short direction = 0; //-1,0,1 /!\ 
        short positionByHeight = 0;
	short passengers = 0;
	short[] waitingList;
	short[] destinationList;

	long timeAtArrive = 0;
	long timeToWait = 0;
    };

    static class Floor
    {
	double probability = Math.random();
        short passengers = 0;
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
		
	for(short i=0;i<nFloor;i++)
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
	Ecran.afficherln("Veuillez saisir le nombre d'etages que votre building doit posseder. (entre 0 et ",Defines.MAX_LIMIT_FLOORS, " ).");
	short nFloors=Clavier.saisirShort();
	verifyInBetween(nFloors,0,Defines.MAX_LIMIT_FLOORS);
	return nFloors;

    }

    static void verifyInBetween(double number, int min, int max)
    // Ask for a number until the condition is true.
    {
	while(number < min || number > max)
	    {
		Ecran.afficherln("Saisie incorrecte. Veuillez reiterer un nombre entre " , min , " et ", max);
		number=Clavier.saisirDouble();
	    }
    }

    static void update(Building building)
    {
	/*
	  Ajoute les nouvelles personnes à chaque etages
	  Gestion des appels (building dit a elevator qui attend et ou)
	  Elevator bouge
	  Elevator et se charge/decharge
	*/
        manageApparition(building);
	manageCalls(building);
	updateElevator(building) ; // Va appeler move et decharge
    }

    static void manageApparition(Building building)
    {
	for(short i=0; i < building.floors.length; i++)
	    {
		Floor currentFloor = building.floors[i];
		if(tossProbility(currentFloor.probability) == true)
		    {
			currentFloor.passengers += 1;
			
			// Our model has problem if someone appears during his waiting; so we directly add the delay if it happens and if it isn't full.
			if(
			   building.elevator.positionByHeight/Defines.FLOOR_HEIGHT_METERS == i 
			   &&  building.elevator.passengers < building.elevator.CAPACITY)
			    {
				building.elevator.timeToWait += Defines.WAITING_TIME_PER_PASSENGER;
			    }
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
	if(building.elevator.positionByHeight%Defines.FLOOR_HEIGHT_METERS == 0)
	    {
		// Nous sommes à un étage
		updateElevatorTiming(building);
		
		unstackElevator(building.elevator);
		stackInElevator(building);
		updateDirection(building.elevator);
	    }     
    }

    static void updateElevatorTiming(Building building)
    {
	if(System.currentTimeMillis() > building.elevator.timeAtArrive + building.elevator.timeToWait)
	    {
		// Actualiza the last step
		building.elevator.timeAtArrive = System.currentTimeMillis();
	
		// Update the time to wait
		int nQuit = building.elevator.destinationList[building.elevator.positionByHeight/Defines.FLOOR_HEIGHT_METERS];
		int nPossible  = building.elevator.CAPACITY - building.elevator.passengers ;
		int nEnter = Math.min(nPossible,building.floors[building.elevator.positionByHeight/Defines.FLOOR_HEIGHT_METERS].passengers);
		building.elevator.timeToWait = Defines.WAITING_TIME_PER_PASSENGER * (nQuit + nEnter);

		// Print it regarding people
		Ecran.afficherln("nquite : ", nQuit , " nEnter : " , nEnter , " Time total : " , building.elevator.timeToWait ) ; // TO DO : afficher à l'écran.
	    }
    }

    static void moveElevator(Elevator elevator)
    {
	if(
	   (elevator.positionByHeight+elevator.direction)/Defines.FLOOR_HEIGHT_METERS <= elevator.waitingList.length-1  // security
	   && (elevator.positionByHeight+elevator.direction)/Defines.FLOOR_HEIGHT_METERS >=0 // security
	   && System.currentTimeMillis() > elevator.timeAtArrive + elevator.timeToWait // nobody is moving
	   ) 
	    {
		//Ecran.afficherln("Current time : ",   -(elevator.timeAtArrive + elevator.timeToWait) + System.currentTimeMillis());
		elevator.positionByHeight+=elevator.direction;
	    }
	
    }
    static void unstackElevator(Elevator elevator)
    {
	//	Ecran.afficherln(" Passengers : " , elevator.passengers , ". At floor " , elevator.positionByHeight/Defines.FLOOR_HEIGHT_METERS, " how many people down : " , elevator.destinationList[elevator.positionByHeight/Defines.FLOOR_HEIGHT_METERS]);
	short nbQuit=elevator.destinationList[elevator.positionByHeight/Defines.FLOOR_HEIGHT_METERS];
	elevator.passengers-=nbQuit;
	elevator.destinationList[elevator.positionByHeight/Defines.FLOOR_HEIGHT_METERS]=0;
    }

    static void stackInElevator(Building building)
    {
	Elevator elevator = building.elevator;
	Floor currentFloor = building.floors[elevator.positionByHeight/Defines.FLOOR_HEIGHT_METERS];

	while(elevator.passengers<elevator.CAPACITY && currentFloor.passengers>0)
	    {
		currentFloor.passengers-=1;
		elevator.passengers++;
		addRandomLocation(elevator);
	    }

    }

    static void addRandomLocation(Elevator elevator)
    {
	short currentPosition = (short)(elevator.positionByHeight/Defines.FLOOR_HEIGHT_METERS);
	short randomLocation = -1;
	do
	    {
		randomLocation = (short)(Math.random()*elevator.waitingList.length);
	    }while(randomLocation==currentPosition);
	elevator.destinationList[randomLocation]++;
    }

    static void updateDirection(Elevator elevator)
    {
	short newDirection = 0; // We keep the initial direciton
	short position = (short)(elevator.positionByHeight / Defines.FLOOR_HEIGHT_METERS);
	
	if(elevator.direction != 0)
	    {
		// First we see in the same direction if there is someone calling the elevator
		for(short i = (short)(position + signOf(elevator.direction)); i < elevator.waitingList.length && i >= 0; i += elevator.direction)
		    {
			if(elevator.waitingList[i] > 0 || elevator.destinationList[i] > 0)
			    {
				newDirection = elevator.direction;
			    }
		    }
	
		// Then if necessary we see in the other direction if there is someone calling the elevator
		if(newDirection == 0)
		    {
			for(short i = (short)(position+signOf((short)(-elevator.direction) )); i < elevator.waitingList.length && i >= 0; i -= elevator.direction)
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

	// If waiting direction = 0 
	if(System.currentTimeMillis() < elevator.timeToWait + elevator.timeAtArrive)
	    {
		elevator.direction = 0;
	    }

    }

    static short signOf(short i)
    {
	if(i < 0){return -1;}
	if(i > 0){return 1;}
	if(i == 0){return 0;}
	return 0;
    }

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
		if(toSecond >= Defines.LAG_TIME) 
		    {
			update(building); 
			toSecond = 0; // Reinit to 0 the time passed
			timePassed+=1;
			
		    } 

		Graphics.draw(building, window, toSecond, stars); // But we draw all the time
		Interface.update(building, window); // update the textuals drawings
		window.flush(); // print the drawings 

		toSecond = System.currentTimeMillis() - (double)loopTime + toSecond; // counting the passed time.
		
	    }
	window.exit();
	
	/*=============UPDATE==================*/
		
    }
};
