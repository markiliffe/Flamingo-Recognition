package core;

import gui.Main_GUI;

import java.util.HashMap;
import java.util.Vector;

import output.WriteCSV;

/**
 * The main class, launches the GUI and stores a vector of reports and a hashmap to link the values contained within the CSV files to physical photographs
 * 
 * 
 * @author mark
 *
 */
public class Main {

	private static Vector<ReportInstance> reports = new Vector<ReportInstance>();
	private static Vector<ReportInstanceNonPhone> reportInstanceNonPhoneVector = new Vector<ReportInstanceNonPhone>();
	private static HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

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

	/**
	 * 
	 * 
	 * @param reportInstanceNonPhone
	 */
	public static void addReports(ReportInstanceNonPhone reportInstanceNonPhone) {
		reportInstanceNonPhoneVector.add(reportInstanceNonPhone);
	}


	/**
	 * @param reportInstanceNonPhone the reportInstanceNonPhone to set
	 */
	public static void setReportInstanceNonPhone(Vector<ReportInstanceNonPhone> reportInstanceNonPhone) {
		Main.setReportInstanceNonPhone(reportInstanceNonPhone);
	}


	/**
	 * @return the reportInstanceNonPhone
	 */
	public static Vector<ReportInstanceNonPhone> getReportInstanceNonPhoneVector() {
		return reportInstanceNonPhoneVector;
	}

	/**
	 * @param hashMap the hashMap to set
	 */
	public static void addElementHashMap(int index, String filepath) {
		System.out.println("Adding to Hashmap Index: " + index + " Filepath :" + filepath);
		Main.hashMap.put(index, filepath);
	}


	/**
	 * @return the value of 'key'
	 */
	public static String getHashMapItem(int key) {
		return Main.hashMap.get(key);
	}
}
