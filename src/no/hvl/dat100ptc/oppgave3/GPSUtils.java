package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min = da[0]; 
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		
		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] ret = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; ++i) {
			ret[i] = gpspoints[i].getLatitude();
		}
		
		return ret;
		
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] ret = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; ++i) {
			ret[i] = gpspoints[i].getLongitude();
		}
		
		return ret;
	}

	private static int R = 6371000; // jordens radius

	//returns distance in meters
	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double lat1 = Math.toRadians(gpspoint1.getLatitude());
		double long1 = Math.toRadians(gpspoint1.getLongitude());
		
		double lat2 = Math.toRadians(gpspoint2.getLatitude());
		double long2 = Math.toRadians(gpspoint2.getLongitude());
		
		double deltaLat = lat2 - lat1;
		double deltaLong = long2 - long1;
		
		//a = (sin(deltaLat/2))^2 + cos(lat1)*cos(lat2)*(sin(deltaLong/2))^2
		
		double ledd1 = Math.sin(deltaLat/2);
		double ledd2 = Math.sin(deltaLong/2);
		
		//a = ledd1^2 + cos(lat1)*cos(lat2)*ledd2^2 = ledd1*ledd1 + cos(lat1)*cos(lat2)*ledd2*ledd2 
		double a = ledd1*ledd1 + Math.cos(lat1)*Math.cos(lat2)*ledd2*ledd2;
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return R * c;

	}

	//return in km/h
	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double distance = distance(gpspoint1, gpspoint2);
		int deltaTime = gpspoint2.getTime() - gpspoint1.getTime();

		return (distance / deltaTime) * 3.6;	//meters_per_second * 3.6

	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";

		// TODO - START

		throw new UnsupportedOperationException(TODO.method());
		
		// TODO - SLUTT

	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		/*String tall = (String.format("%.2f", d));
		int length = tall.length();
		
		for(int i = length + 1; i <= TEXTWIDTH; i++) {
			
			tall = " " + tall;
			
			return tall;*/
		}

		
	}

