package no.hvl.dat100ptc.oppgave6;

public class CycleComputerSettings {
	
	public static int SPACE = 10;
	public static int MARGIN = 20;
	
	// FIXME: take into account number of measurements / gps points
	@Deprecated public static int ROUTEMAPXSIZE = 800; 	//should be unused
	@Deprecated public static int ROUTEMAPYSIZE = 400;	//should be unused
	public static int HEIGHTSIZE = 200;
	public static int TEXTWIDTH = 200;
	
	//kvifor var ikkje dette med til å begynne med?	HEIGHT fungerte ikkje (HEIGHT var 2)
	public static int WINDOW_WIDTH = 2 * MARGIN + ROUTEMAPXSIZE;
	public static int WINDOW_HEIGHT = 2 * MARGIN + ROUTEMAPYSIZE + HEIGHTSIZE + SPACE;
	
	public static int CIRCLE_SIZE = 5;
	public static int TEXT_START_X = 0;
	public static int TEXT_START_Y = 0;
	public static int TEXT_Y_INCREMENT = 20;
	
	public static int speedscale = 100;		//default, adjusted in CycleMultiComputer
	public static double speedfactor = 1;	//default, adjusted in CycleMultiComputer
	public static void setSpeedscale(int x) { speedscale = x; }
	public static void setSpeedfactor(double d) { speedfactor = d; }

}
