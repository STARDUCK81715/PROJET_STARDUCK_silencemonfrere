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
		//short N_FLOORS; // Can't be constant due to hand initialization 
		Floor[] floors/*=new Floor[N_FLOORS]*/;
	};
	
	static class Elevator
	{
		short CAPACITY=-1; // Can't be constant due to hand initialization 
		short direction=0; //-1,0,1 /!\ 
		short positionFloor=0;
		short passengers=0;
		short N_FLOOR=0;
		boolean[] waitingList=new boolean[N_FLOOR];
	};
	
	/*static class List
	{
		short nElements=0;
		short[] elements ;
	};*/
	
	static class Floor
	{
		short passengers=-1;
	};
	
	
	/* =========== INITIALIZATION FUNCTIONS ============ */
	static Floor CreateNewFloor()
	{
		Floor floor = new Floor();
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
		//elevator.N_FLOOR=Nfloor;
		elevator.waitingList=new boolean[Nfloor];
		
		for(short i=0;i<Nfloor;i++)//function ?
		{
			elevator.waitingList[i]=false;
			
		}
		return elevator;
	}
		
	public static void main(String args[])
        {
     /*=============INITIAL STATE===============*/
		/*test*/short n=35;
		Building buuilding=CreateNewBuilding(n);
		
		Ecran.afficher("   ",buuilding.floors[2].passengers,"    ",buuilding.mainElevator.CAPACITY);/*end of test*/
    }
};
