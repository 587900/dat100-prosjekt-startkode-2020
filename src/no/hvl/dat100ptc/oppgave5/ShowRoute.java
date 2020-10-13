package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 500;//800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 

		return xstep;
	}

	//5c
	// antall y-pixels per breddegrad
	public double ystep() {
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
	
		return MAPYSIZE / (Math.abs(maxlat - minlat));
	}

	//5c)
	public void showRouteMap(int ybase) {

		if (gpspoints.length == 0) return;
		
		setColor(0, 255, 0);
		
		double xstep = xstep();
		double ystep = ystep();
		
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		int index = 0;
		GPSPoint prev = gpspoints[index];
		GPSPoint head = gpspoints[index];
		index++;
		
		while (head != null) {
			
			//bug vi kom borti, vis i mårra
			//int prevX = (int)(xstep * prev.getLatitude() % 1000);
			//int prevY = (int)(ystep * prev.getLongitude() % 1000);
			//int headX = (int)(xstep * head.getLatitude() % 1000);
			//int headY = (int)(ystep * head.getLongitude() % 1000);
			
			int prevX = (int)(xstep * (prev.getLongitude() - minlon));
			int prevY = (int)(ystep * (prev.getLatitude() - minlat));
			int headX = (int)(xstep * (head.getLongitude() - minlon));
			int headY = (int)(ystep * (head.getLatitude() - minlat));
			
			drawLine(prevX, HEIGHT - prevY + ybase, headX, HEIGHT - headY + ybase);
			drawCircle(headX, HEIGHT - headY + ybase, 5);
			
			prev = head;
			if (index >= gpspoints.length) head = null; else head = gpspoints[index++]; 
			
		}
		
	}

	//5c
	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);
		
		int y = 0;
		drawString("Total Time     :" + GPSUtils.formatTime(gpscomputer.totalTime()), 0, y); 
		y += TEXTDISTANCE;
		drawString("Total distance :" + GPSUtils.formatDouble(gpscomputer.totalDistance() / 1000) + " km", 0, y);
		y += TEXTDISTANCE;
		drawString("Total elevation:" + GPSUtils.formatDouble(gpscomputer.totalElevation()) + " m", 0, y);
		y += TEXTDISTANCE;
		drawString("Max speed      :" + GPSUtils.formatDouble(gpscomputer.maxSpeed()) + " km/t", 0, y);
		y += TEXTDISTANCE;
		drawString("Average speed  :" + GPSUtils.formatDouble(gpscomputer.averageSpeed()) + " km/t", 0, y);
		y += TEXTDISTANCE;
		drawString("Energy         :" + GPSUtils.formatDouble(gpscomputer.totalKcal(80)) + " kcal", 0, y);
	}

}
