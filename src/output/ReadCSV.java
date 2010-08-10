package output;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import core.*;
public class ReadCSV{

	private Vector<ReportInstance> reports = new Vector<ReportInstance>();
	private StringTokenizer stringTokenizer;

	public ReadCSV() {
		System.out.println("Constructor Created");
	}

	public void read(String filepath){
		try {
			System.out.println("Class Loaded");
			BufferedReader in = new BufferedReader(new FileReader(filepath));
			System.out.println("Class Loaded");

			//Form the CSV file
			String tempLine = in.readLine();
			//System.out.println(tempLine);
			while ((tempLine = in.readLine()) != null) {
				//System.out.println("Reading File");
				//tempLine = in.readLine();
				stringTokenizer = new StringTokenizer(tempLine,",");
				while(stringTokenizer.hasMoreTokens()){
					System.out.print(stringTokenizer.nextToken() + ", ");
				}				
				System.out.println();
				//stringReader = new StringReader(tempLine);
				//				while (stringReader.read() != -1){
				//					stringReader.read();
				//					
				//				}
				//new ReportInstance(latitude, _longitude, _time, _lake, _lowerEstimate, _higherEstimate, _agreedEstimate, 0.0, 0.0, 0.0, _altitude, _accuracy)
			}
			JOptionPane.showMessageDialog(new JFrame(), "CSV File: " + filepath + " Uploaded!", "Information Message", JOptionPane.INFORMATION_MESSAGE);
			in.close();
			System.out.println("Input Finished");
		} catch (IOException e) {
			System.out.println("Error");
		}
	}

	/**
	 * @param reports the reports to set
	 */
	public void setReports(Vector<ReportInstance> reports) {
		this.reports = reports;
	}

	/**
	 * @return the reports
	 */
	public Vector<ReportInstance> getReports() {
		return reports;
	}
}
//}
