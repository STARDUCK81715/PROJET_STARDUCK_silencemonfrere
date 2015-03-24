public class Defines
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

}
