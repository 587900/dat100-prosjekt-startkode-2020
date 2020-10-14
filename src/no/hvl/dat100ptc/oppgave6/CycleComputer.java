package no.hvl.dat100ptc.oppgave6;

import java.util.Arrays;
import static no.hvl.dat100ptc.oppgave6.CycleComputerSettings.*;

import easygraphics.*;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class CycleComputer {
	
	private static final int TEXT_WIDTH = 220;

	private GPSComputer gpscomp;
	private GPSPoint[] gpspoints;
	
	private int N = 0;

	private double minlon, minlat, maxlon, maxlat;

	private double xstep, ystep;
	
	//for tick function
	private double[] times, distances, elevations, speeds, climbs, kcals;
	private int prevX, prevY, prevT, circleID;
	double xHeightProfile, xHeightProfilePadding, xHeightProfileScale;
	private int index;
	
	private int xOff, yOff;
	private int width, height;
	private int xShift;
	
	private EasyGraphics drawer;

	
	public CycleComputer(EasyGraphics drawer, GPSComputer gpscomp) { this(drawer, gpscomp, WINDOW_WIDTH, WINDOW_HEIGHT, 0, 0); }
	public CycleComputer(EasyGraphics drawer, GPSComputer gpscomp, int width, int height, int xOff, int yOff) {
		System.out.println("Constructing new CycleComputer... width/height/xOff/yOff: " + width + " " + height + " " + xOff + " " + yOff);
		this.drawer = drawer;
		this.gpscomp = gpscomp;
		gpspoints = gpscomp.getGPSPoints();
		
		this.width = width;
		this.height = height;
		
		this.xOff = xOff;
		this.yOff = yOff;
		
		N = gpspoints.length; // number of gps points

		minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));

		xstep = xstep();
		ystep = ystep();

	}

	//6c)
	public void bikeRoute() {
		prepeareBikeRoute();
		while (tick());
	}
	
	public void prepeareBikeRoute() {
		if (gpspoints.length == 0) return;
		
		index = 0;
		
		times = gpscomp.times();
		distances = GPSUtils.arrayExtend(gpscomp.distances());
		elevations = gpscomp.getElevations();
		speeds = GPSUtils.arrayExtend(gpscomp.speeds());
		climbs = GPSUtils.arrayExtend(gpscomp.climbs());
		kcals = GPSUtils.arrayExtend(gpscomp.kcals(80));
		xHeightProfile = MARGIN;
		xHeightProfileScale = Math.min(1, (height * 2 / 3 - MARGIN * 2) / GPSUtils.findMax(elevations));
		xHeightProfilePadding = (width - MARGIN * 2) / (double)elevations.length;
		prevX = (int)(xstep * (gpspoints[0].getLongitude() - minlon)) + MARGIN;
		prevY = (int)(ystep * (gpspoints[0].getLatitude() - minlat)) + MARGIN;
		prevT = gpspoints[0].getTime();
		
		//draw graphs to the right of the text
		xShift = 0;
		if (width > TEXT_WIDTH * 2) {
			xHeightProfilePadding = (width - TEXT_WIDTH - MARGIN * 2) / (double)elevations.length;
			xShift = TEXT_WIDTH;
		}
		
		drawer.setColor(0, 0, 255); 
		circleID = drawer.fillCircle(xShift + xOff + prevX, height - prevY + yOff, CIRCLE_SIZE);
	}
	
	public int secondsUntilNextTick(int offset) {
		if (index == 0 || index >= gpspoints.length) return 0;
		return gpspoints[index].getTime() - offset - gpspoints[0].getTime();
	}
	
	public boolean tick() {
		if (index >= gpspoints.length) return false;
		tick(index++);
		return true;
	}
	public void tick(int index) {
		int timePassed = (int)GPSUtils.sum(Arrays.copyOfRange(times, 0, index+1));
		double distance = GPSUtils.sum(Arrays.copyOfRange(distances, 0, index+1));
		double maxspeed = GPSUtils.findMax(Arrays.copyOfRange(speeds, 0, index+1));
		double avgspeed = distance / timePassed * 3.6; //GPSUtils.average(Arrays.copyOfRange(speeds, 0, index+1));
		double totkcal  = GPSUtils.sum(Arrays.copyOfRange(kcals, 0, index+1));
		double maxclimb = GPSUtils.findMax(Arrays.copyOfRange(climbs, 0, index+1));
		
		
		GPSPoint current = gpspoints[index];
		
		//timings
		//drawer.pause((current.getTime() - prevT) * 1000 / speedscale);
		drawer.setSpeed(Math.min(10, Math.max(1, (int)(speeds[index] * speedfactor))));	//1 - 10
		
		//height profile			
		drawer.setColor(0, 0, 255);
		drawer.drawLine(xShift + xOff + (int)xHeightProfile, height - MARGIN + yOff, xShift + xOff + (int)xHeightProfile, height - MARGIN + yOff - (int)Math.max(0, elevations[index]*xHeightProfileScale));
		xHeightProfile += xHeightProfilePadding;

		//route
		if (climbs[index] > 0) drawer.setColor(255, 0, 0); else if (climbs[index] < 0) drawer.setColor(0, 255, 0); else drawer.setColor(0, 0, 255);
		
		int currentX = (int)(xstep * (current.getLongitude() - minlon)) + MARGIN;
		int currentY = (int)(ystep * (current.getLatitude() - minlat)) + MARGIN;
		
		drawer.drawLine(xShift + xOff + prevX, height - prevY + yOff, xShift + xOff + currentX, height - currentY + yOff);
		drawer.drawCircle(xShift + xOff + currentX, height - currentY + yOff, CIRCLE_SIZE);
		drawer.moveCircle(circleID, xShift + xOff + currentX, height - currentY + yOff);
		
		//statistics
		drawer.setColor(255, 255, 255);
		drawer.fillRectangle(xOff + TEXT_START_X + 110, yOff + TEXT_START_Y, 110, TEXT_Y_INCREMENT * 10);
		drawer.setColor(0, 0, 0);
		int textY = yOff + TEXT_START_Y + TEXT_Y_INCREMENT;
		int textX = xOff + TEXT_START_X;
		drawer.drawString("Time passed   :" + GPSUtils.formatTime(timePassed), 						textX, textY); textY += TEXT_Y_INCREMENT;	//yOff applied to textY universally
		drawer.drawString("Distance      :" + GPSUtils.formatDouble(distance)			+ " m",		textX, textY); textY += TEXT_Y_INCREMENT;
		drawer.drawString("Elevation     :" + GPSUtils.formatDouble(elevations[index]) 	+ " m", 	textX, textY); textY += TEXT_Y_INCREMENT;
		drawer.drawString("Speed         :" + GPSUtils.formatDouble(speeds[index])		+ " km/h",	textX, textY); textY += TEXT_Y_INCREMENT;
		drawer.drawString("Max speed     :" + GPSUtils.formatDouble(maxspeed) 			+ " km/h", 	textX, textY); textY += TEXT_Y_INCREMENT;
		drawer.drawString("Avg speed     :" + GPSUtils.formatDouble(avgspeed) 			+ " km/h", 	textX, textY); textY += TEXT_Y_INCREMENT;
		drawer.drawString("Kcal now      :" + GPSUtils.formatDouble(kcals[index])		+ " kcal",	textX, textY); textY += TEXT_Y_INCREMENT;
		drawer.drawString("Kcal spent    :" + GPSUtils.formatDouble(totkcal) 			+ " kcal", 	textX, textY); textY += TEXT_Y_INCREMENT;
		drawer.drawString("Climb         :" + GPSUtils.formatDouble(climbs[index])	 	+ " %", 	textX, textY); textY += TEXT_Y_INCREMENT;
		drawer.drawString("Max climb     :" + GPSUtils.formatDouble(maxclimb) 			+ " %", 	textX, textY); textY += TEXT_Y_INCREMENT;

		//prep next tick
		prevX = currentX;
		prevY = currentY;
		prevT = current.getTime();
	}

	public double xstep() {
		if (width > TEXT_WIDTH * 2) return (width - TEXT_WIDTH - 2*MARGIN) / Math.abs(maxlon - minlon);		//draw graphs to the right of the text
		return (width - 2*MARGIN) / Math.abs(maxlon - minlon);
	}

	public double ystep() {
		return (height - 2*MARGIN) / Math.abs(maxlat - minlat);
	}

}
