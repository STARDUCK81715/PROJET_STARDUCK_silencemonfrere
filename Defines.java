public class Defines
{
    /* ======== CONSTANTS ======= */
    final static short MAX_LIMIT_FLOORS = 100; // Max number of floors in a building
    final static byte WINDOW_X = 0;
    final static byte WINDOW_Y = 0;
    final static int WINDOW_SIZE = 1000; 
    final static int RESOLUTION = WINDOW_SIZE; // Drawing area size
    final static String WINDOW_TITLE = "Elevator_simulation"; // Title of the window
    final static char QUIT_CHARACTER = 'q'; // Char to type in order to quit
    final static short FLOOR_HEIGHT_METERS = 5; // The height (in meters) of the floor (matter about the elevator travelling time)
    final static short WAITING_TIME_PER_PASSENGER = 1000; // The time to wait each time a passenger move into / from the elevator
    final static short LAG_TIME = 1000 ; // The time between two updates
    
    // VISUAL
    final static double BORDER_GAP = 0.1 * WINDOW_SIZE ; // Visual gap on both sides of the building.
    final static double FLOOR_WIDTH = WINDOW_SIZE * 0.35 ; // Visual width of a half - floor
    final static double FLOOR_HEIGHT = WINDOW_SIZE * 0.1 ; // Visual height of a half - floor
    final static double ELEVATOR_WIDTH = WINDOW_SIZE - 2 * FLOOR_WIDTH - 2 * BORDER_GAP - 2  ; // The rest of the space
    final static double ELEVATOR_HEIGHT = FLOOR_HEIGHT  ; 
    final static double PASSENGER_WIDTH = 2; // pixels
    final static double PASSENGER_HEIGHT = 2 * FLOOR_HEIGHT / 3; // following the floor height
    final static int CHAR_WIDTH = 10; // width of the visual char (found by experimentation)
    final static int CHAR_HEIGHT = 15; // height of the visual char (found by experimentation)
    final static double BASIC_OFFSET = 1; // While scrolling, the BASIC_OFFSET is the movement it implies
    final static int N_STARS = 500; // Number of stars filling the background
    final static short MARGIN = 2 ;
    
    /* ========================= */

}
