package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 500;//800;
	private static int CIRCLE_SIZE = 5;

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

		showStatistics();
		
		int speedscale;
		try { speedscale = Math.max(1, Integer.parseInt(JOptionPane.showInputDialog("Tidsskala (1/x): "))); } catch (NumberFormatException e) { speedscale = Integer.MAX_VALUE; }
		showRouteMap(MARGIN + MAPYSIZE, speedscale);
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
	public void showRouteMap(int ybase, int speedscale) {

		if (gpspoints.length == 0) return;
		
		double xstep = xstep();
		double ystep = ystep();
		
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
//		int index = 0;
//		GPSPoint prev = gpspoints[index];
//		GPSPoint head = gpspoints[index];
//		index++;
//		
//		while (head != null) {
//			
//			//bug vi kom borti, vis i mårra
//			//int prevX = (int)(xstep * prev.getLatitude() % 1000);
//			//int prevY = (int)(ystep * prev.getLongitude() % 1000);
//			//int headX = (int)(xstep * head.getLatitude() % 1000);
//			//int headY = (int)(ystep * head.getLongitude() % 1000);
//			
//			int prevX = (int)(xstep * (prev.getLongitude() - minlon));
//			int prevY = (int)(ystep * (prev.getLatitude() - minlat));
//			int headX = (int)(xstep * (head.getLongitude() - minlon));
//			int headY = (int)(ystep * (head.getLatitude() - minlat));
//			
//			drawLine(prevX, HEIGHT - prevY + ybase, headX, HEIGHT - headY + ybase);
//			drawCircle(headX, HEIGHT - headY + ybase, 5);
//			
//			prev = head;
//			if (index >= gpspoints.length) head = null; else head = gpspoints[index++]; 
//			
//		}
		
		//6b)
		double[] speeds = GPSUtils.arrayExtend(gpscomputer.speeds());
		
		setColor(0, 0, 255);
		
		int prevX = (int)(xstep * (gpspoints[0].getLongitude() - minlon));
		int prevY = (int)(ystep * (gpspoints[0].getLatitude() - minlat));
		int prevT = gpspoints[0].getTime();
		int circleID = fillCircle(prevX, HEIGHT - prevY + ybase, CIRCLE_SIZE);
		
		setColor(0, 255, 0);
		
		for (int i = 0; i < gpspoints.length; ++i) {
			
			GPSPoint current = gpspoints[i];
			
			pause((current.getTime() - prevT) * 1000 / speedscale);
			setSpeed(Math.min(10, Math.max(1, (int)speeds[i] / 10)));	//1 - 10
			
			int currentX = (int)(xstep * (current.getLongitude() - minlon));
			int currentY = (int)(ystep * (current.getLatitude() - minlat));
			
			drawLine(prevX, HEIGHT - prevY + ybase, currentX, HEIGHT - currentY + ybase);
			drawCircle(currentX, HEIGHT - currentY + ybase, CIRCLE_SIZE);
			moveCircle(circleID, currentX, HEIGHT - currentY + ybase);

			prevX = currentX;
			prevY = currentY;
			prevT = current.getTime();
			
		};
		
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
