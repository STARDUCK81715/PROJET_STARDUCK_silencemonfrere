/**
* \file Defines.java
* \brief Fichier java contenant toutes les constantes 
* \authors Ervan Silvert
* \authors Paul-Armand Michaud
*/


/**
* \class Defines
* \brief Contient toutes les constantes en publique static constant
* \authors Ervan Silvert
* \authors Paul-Armand Michaud
*/


public class Defines
{
    /**
     * \brief Nombre max d'etage dans le batiment
     */
    final static short MAX_LIMIT_FLOORS = 100; 

    final static byte WINDOW_X = 0;
    final static byte WINDOW_Y = 0;
    final static int WINDOW_SIZE = 1000; 

    /**
     * \brief Taille de la zone de dessin
     */
    final static int RESOLUTION = WINDOW_SIZE; 

    /**
     * \brief Titre de la fenetre
     */
    final static String WINDOW_TITLE = "Elevator_simulation"; 

    /**
     * \brief Caractere a taper pour quitter
     */
    final static char QUIT_CHARACTER = 'q'; 

    /**
     * \brief La hauteur en metre de l'etage, influant sur la vitesse de l'ascenceur
     */
    final static short FLOOR_HEIGHT_METERS = 3; 

    /**
     * \brief Temps a attendre pour chaque mouvement de passager
     */
    final static short WAITING_TIME_PER_PASSENGER = 1000; 

    /**
     * \brief Le temps entre deux update
     */
    // TO TEST
    final static short LAG_TIME = 500 ; 
    
    /* VISUAL */ 
    
    /**
     * \brief Petit bord vide sur les bords de l'ecran pour le visuel
     */
    final static double BORDER_GAP = 0.1 * WINDOW_SIZE ; 

    /**
     * \brief Largeur d'un etage
     */
    final static double FLOOR_WIDTH = WINDOW_SIZE * 0.35 ; 

    /**
     * \brief Hauteur VISUELLE de l'etage
     */
    final static double FLOOR_HEIGHT = WINDOW_SIZE * 0.1 ; 

    /**
     * \brief Largeur de l'ascenceur
     */
    final static double ELEVATOR_WIDTH = WINDOW_SIZE - 2 * FLOOR_WIDTH - 2 * BORDER_GAP - 2  ; 

    /**
     * \brief Hauteur de l'ascenceur
     */
    final static double ELEVATOR_HEIGHT = FLOOR_HEIGHT  ; 

    /**
     * \brief Largeur d'un passager (pixel)
     */
    final static double PASSENGER_WIDTH = 2; 

    /**
     * \brief Hauteur d'un passager
     */
    final static double PASSENGER_HEIGHT =  FLOOR_HEIGHT / 3; 

    /**
     * \brief Largeur d'un caractere (trouve empiriquement) utilise pour l'encadrement
     */
    final static int CHAR_WIDTH = 10; 

    /**
     * \brief Hauteur d'un caractere (trouve empiriquement) utilise pour l'encadrement
     */
    final static int CHAR_HEIGHT = 15; 

    /**
     * \brief Deplacement induit par une demande de scrolling
     */
    final static double BASIC_OFFSET =  1000 / LAG_TIME; 

    /**
     * \brief Nombre d'etoile dans le ciel
     */
    final static int N_STARS = 500; 

    /**
     * \brief Decalage utilise un peu partout pour eviter les chevauchements
     */
    final static short MARGIN = 2 ;

}
