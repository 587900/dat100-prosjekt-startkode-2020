package no.hvl.dat100ptc.oppgave6;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

//BUGGY, only for showcasing funny bugs

public class ThreadedCycleMultiComputer extends EasyGraphics {

	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	
	private CycleComputer[] cyclecomputers;
	
	public ThreadedCycleMultiComputer() {
		System.out.println("Constructing " + this.getClass().getCanonicalName() + " ...");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void run() {
		makeWindow("Cycle Multi Computer", WINDOW_WIDTH, WINDOW_HEIGHT);
		
		setFont("Courier",12);
		
		CycleComputerSettings.setSpeedscale(Integer.parseInt(JOptionPane.showInputDialog("speedscale:")));
		CycleComputerSettings.setSpeedfactor(Double.parseDouble(JOptionPane.showInputDialog("speedfactor:")));
		
		int n = Integer.parseInt(JOptionPane.showInputDialog("Antal CycleComputers:"));
		cyclecomputers = new CycleComputer[n];
		for (int i = 0; i < n; ++i) cyclecomputers[i] = new CycleComputer(this, new GPSComputer(JOptionPane.showInputDialog("GPS data filnavn: ")));
		
		for (CycleComputer c : cyclecomputers) new Threader(c).start();
		
	}
	
	class Threader extends Thread {
		CycleComputer comp;
		public Threader(CycleComputer comp) {
			this.comp = comp;
		}
		@Override
		public void run() { comp.bikeRoute(); }
		
	}
	
}
