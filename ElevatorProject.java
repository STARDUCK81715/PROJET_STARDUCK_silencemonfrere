// SILVERT Ervan & MICHAUD Paul-Armand

public class ElevatorProject
{
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
			if(elevator.waitingList[i]>0 || elevator.destinationList[i]>0)
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

    public static void main(String args[])
    {
	/*=============INITIAL STATE===============*/
	Building building = createNewBuilding(askForNumberFloors());
	EcranGraphique window = Graphics.createNewEcranGraphique();

	long loopTime = 0; // record the time passed during a loop
	int toSecond = 0; // record the wolhe time passed to know when a second passed.
	while(window.getKey()!=Defines.QUIT_CHARACTER) 
	    {
		loopTime =  System.currentTimeMillis(); // Get the start time
		
		if(toSecond >= 1000) // We update the data (and the appearing ratio) every second
		    {
			update(building); 
			toSecond = 0; // Reinit to 0 the time passed
		    } 

		Graphics.draw(building,window,toSecond); // But we draw all the time

		toSecond = (int)(System.currentTimeMillis() - loopTime) + toSecond; // counting the passed time.
		
	    }
	window.exit();
	
	/*=============UPDATE==================*/
		
    }
};
