package no.hvl.dat100ptc.oppgave6;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class CycleMultiComputer extends EasyGraphics {
	
	private static final boolean TIME_BASED = true;
	
	private CycleComputer[] cyclecomputers;
	
	public CycleMultiComputer() {
		System.out.println("Constructing " + this.getClass().getCanonicalName() + " ...");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void run() {
		makeWindow("Cycle Multi Computer", CycleComputerSettings.WINDOW_WIDTH, CycleComputerSettings.WINDOW_HEIGHT);
		
		setFont("Courier",12);
		
		CycleComputerSettings.setSpeedscale(Integer.parseInt(JOptionPane.showInputDialog("speedscale:")));
		CycleComputerSettings.setSpeedfactor(Double.parseDouble(JOptionPane.showInputDialog("speedfactor:")));
		
		int n = Integer.parseInt(JOptionPane.showInputDialog("Antal CycleComputers:"));
		cyclecomputers = new CycleComputer[n];
		
		//https://stackoverflow.com/questions/4106935/determine-rows-columns-needed-given-a-number
		int columns = (int)Math.sqrt(n);
		int rows = (int)Math.ceil(n / (float)columns);

		int cs = CycleComputerSettings.WINDOW_WIDTH / columns;
		int rs = CycleComputerSettings.WINDOW_HEIGHT / rows;
		
		for (int i = 0; i < n; ++i) {
			GPSComputer c = new GPSComputer(JOptionPane.showInputDialog("GPS data filnavn:"));
			
			int row = i/columns;
			int col = i%columns;
			
			cyclecomputers[i] = new CycleComputer(this, c, cs, rs, cs*col, rs*row);
		}
		
		for (CycleComputer c : cyclecomputers) c.prepeareBikeRoute();
		
		//tids-basert
		if (TIME_BASED) {
			int counter = 0;
			boolean alive = true;
			while (alive) {
				alive = false;
				for (CycleComputer c : cyclecomputers) {
					if (c.secondsUntilNextTick(counter) > 0) alive = true; else if (c.tick()) alive = true;
				}
				counter++;
				pause(1000 / CycleComputerSettings.speedscale);
			}
		} else {
			boolean alive = true;
			while (alive) {
				alive = false;
				for (CycleComputer c : cyclecomputers) {
					if (c.tick()) alive = true;
				}
				pause(1000 / CycleComputerSettings.speedscale);
			}
		}
		
	}

}
