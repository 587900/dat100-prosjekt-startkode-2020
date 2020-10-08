package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSDataConverter {

	// konverter tidsinformasjon i gps data punkt til antall sekunder fra midnatt
	// dvs. ignorer information om dato og omregn tidspunkt til sekunder
	// Eksempel - tidsinformasjon (som String): 2017-08-13T08:52:26.000Z
    // skal omregnes til sekunder (som int): 8 * 60 * 60 + 52 * 60 + 26 
	
	private static int TIME_STARTINDEX = 11; // startindex for tidspunkt i timestr

	public static void main(String[] args) {
		
		String test = "2017-08-13T08:52:26.000Z";
		System.out.println(test.substring(11,13));
		System.out.println(test.substring(14,16));
		System.out.println(test.substring(17,19));
	}
	
	//expects input formatted as: 2017-08-13T08:52:26.000Z -> hr = 08, min = 52, sec = 26
	public static int toSeconds(String timestr) {
		
		int hr = Integer.parseInt(timestr.substring(11, 13));
		int min = Integer.parseInt(timestr.substring(14, 16));
		int sec = Integer.parseInt(timestr.substring(17, 19));
		
		return (hr * 3600 + min * 60 + sec);
		
	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {

		/*
		int seconds = toSeconds(timeStr);
		double latitude = Double.parseDouble(latitudeStr);
		double longitude = Double.parseDouble(longitudeStr);
		double elevation = Double.parseDouble(elevationStr);
		
		GPSPoint gpspoint = new GPSPoint(seconds, latitude, longitude, elevation);

		return gpspoint;
		*/
		
		return new GPSPoint(toSeconds(timeStr), Double.parseDouble(latitudeStr), 
				Double.parseDouble(longitudeStr), Double.parseDouble(elevationStr));
		
	}
	
}
