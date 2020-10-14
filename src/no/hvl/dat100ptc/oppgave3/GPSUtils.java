package no.hvl.dat100ptc.oppgave3;

import java.util.Locale;

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

	//3a)
	public static double findMin(double[] da) {
		
		double min = da[0]; 
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		
		return min;

	}

	//3b)
	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] ret = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; ++i) {
			ret[i] = gpspoints[i].getLatitude();
		}
		
		return ret;
		
	}

	//3c)
	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] ret = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; ++i) {
			ret[i] = gpspoints[i].getLongitude();
		}
		
		return ret;
	}

	private static int R = 6371000; // jordens radius

	//3d)
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

	//3e)
	//return in km/h
	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double distance = distance(gpspoint1, gpspoint2);
		int deltaTime = gpspoint2.getTime() - gpspoint1.getTime();

		return (distance / deltaTime) * 3.6;	//meters_per_second * 3.6

	}

	//3f)
	public static String formatTime(int secs) {

		int hh = secs / 3600;
		int mm = secs % 3600 / 60;
		int ss = secs % 60;	//samme som: secs % 3600 % 60
		
		return String.format("  %02d:%02d:%02d", hh, mm, ss);

	}
	private static int TEXTWIDTH = 10;

	//3g)
	public static String formatDouble(double d) {


		//eit problem på min PC, fekk "," i staden for ".". Måtte bruke Locale.US
		
		/*String tall = String.format(Locale.US, "%.2f", d);
		String mellomrom = "";
		
		while (mellomrom.length() + tall.length() < TEXTWIDTH) mellomrom += " ";
		
		return mellomrom + tall;*/

		//eit problem, fekk "," i staden for ".". Måtte bruke Locale.US
		//1$10 = length 10, .2 = 2 desimaler, %f = flyttal
		
		
		return String.format(Locale.US, "%1$" + TEXTWIDTH + ".2f", d);
		
		/*String tall = (String.format(Locale.US, "%.2f", d));
		
		int length = tall.length();
		
		for(int i = length + 1; i <= TEXTWIDTH; i++) {
			
			tall = " " + tall;
		
		return tall;*/
		
	}
	
	//for 6c)
	public static double[] arrayExtend(double[] arr) {
		double[] ret = new double[arr.length+1];
		System.arraycopy(arr, 0, ret, 1, arr.length);
		return ret;
	}
	
	//for 6c)
	public static double average(double[] arr) {
		return sum(arr) / arr.length;
	}
	
	//for 6c)
	public static double sum(double[] arr) {
		double sum = 0;
		for (double d : arr) sum += d;
		return sum;
	}
	
}

