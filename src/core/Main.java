package core;

import java.util.Vector;

import gui.*;
import output.*;

/**
 * @author mark
 *
 */

public class Main {

	private static Vector<ReportInstance> reports = new Vector<ReportInstance>();

	/**
	 * @param 
	 */
	public static void addReports(ReportInstance reportInstance) {
		reports.add(reportInstance);
	}


	/**
	 * @return the reports vector
	 */
	public static Vector<ReportInstance> getReports() {
		return reports;
	} 


	/**
	 * @param Main 
	 */
	public static void main(String[] args) {

		Main_GUI gui = new Main_GUI();
		gui.run();
		WriteCSV csv = new WriteCSV(getReports());
		csv.write();

	}
}
