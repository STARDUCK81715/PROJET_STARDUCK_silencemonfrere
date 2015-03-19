// SILVERT Ervan & MICHAUD Paul-Armand

public class Elevator_project
{
	/*static class Passenger
	{
		
		
	};*/
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
		boolean[] waitingList;//=new boolean[N_FLOOR];
	};
	
	/*static class List
	{
		short nElements=0;
		short[] elements ;
	};*/
	
	static class Floor
	{
		double probability=0.0;
		short passengers=-1;
	};
	
	
	/* =========== INITIALIZATION FUNCTIONS ============ */
	static Floor CreateNewFloor()
	{
		Floor floor = new Floor();
		floor.probability=Math.random();
		return floor;
		
	}
	
	static Building CreateNewBuilding(short Nfloor)
	{
		Building building = new Building();
		building.floors=new Floor[Nfloor];
		building.mainElevator=CreateNewElevator(Nfloor);	
		
		for(short i=0;i<Nfloor;i++) // function ?
		{
			building.floors[i]=CreateNewFloor();
			
		}
		return building;
	}
	
	static Elevator CreateNewElevator(short Nfloor)
	{
		Elevator elevator = new Elevator();
		elevator.waitingList=new boolean[Nfloor];
		
		for(short i=0;i<Nfloor;i++)//function ?
		{
			elevator.waitingList[i]=false;
			
		}
		return elevator;
	}
	
	static short TestFloors(short nFloor)
	{
		while(nFloor<1||nFloor>100)
		{
			Ecran.afficher("Please enter a number between 1-100 !: ");
			nFloor=Clavier.saisirShort();
		}
		return nFloor;
	}
	
	public static void main(String args[])
        {
     /*=============INITIAL STATE===============*/
		
		short nFloor;
		Ecran.afficherln("How big is your Building ? (Answer by a number of floors between 1-100): ");
		nFloor=Clavier.saisirShort();
		TestFloors(nFloor);
		Building mainBuilding=CreateNewBuilding(nFloor);
		/*test*/
		for(short i=0;i<mainBuilding.floors.length;i++)
		{
			Ecran.afficherln(mainBuilding.floors[i].probability);
		}
		/*fintest*/
	/*=============UPDATE==================*/
		
    }
};
