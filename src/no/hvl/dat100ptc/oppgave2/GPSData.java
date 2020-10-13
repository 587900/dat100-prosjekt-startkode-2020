package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	//2b)
	public GPSData(int n) {
		gpspoints = new GPSPoint[n];
		antall = 0;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	//2b)
	protected boolean insertGPS(GPSPoint gpspoint) {

		//viss full return false
		if (antall >= gpspoints.length) return false; //full tabell
		
		//viss ikkje, sett inn punkt i tabell på posisjon antall
		gpspoints[antall] = gpspoint;
		
		//ikrementer antall
		antall++;
		
		return true;
	}

	//2b)
	public boolean insert(String time, String latitude, String longitude, String elevation) {

		/*
		GPSPoint gpspoint = GPSDataConverter.convert(time, latitude, longitude, elevation);
		return insertGPS(gpspoint);
		*/
		return insertGPS(GPSDataConverter.convert(time, latitude, longitude, elevation));
		
	}

	//2b)
	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");

		//for (int i = 0; i < gpspoints.length; ++i) {
		for (int i = 0; i < antall; ++i) {
			
			/*
			GPSPoint gpspoint = gpspoints[i];
			System.out.print(gpspoint.toString());
			*/
			System.out.print(gpspoints[i].toString());
		}
		
		System.out.println("====== Konvertert GPS Data - SLUTT ======");

	}
}
