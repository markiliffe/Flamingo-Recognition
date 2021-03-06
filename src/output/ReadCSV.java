package output;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.StringCharacterIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import core.ReportInstance;
public class ReadCSV{

	private Vector<ReportInstance> reports = new Vector<ReportInstance>();
	private StringTokenizer stringTokenizer;

	private int id;
	private double latitude;
	private double longitude;
	private long time;
	private String lake;
	private int lowerEstimate;
	private int agreedEstimate;
	private int higherEstimate;
	//	private double xAxis;
	//	private double yAxis;
	//	private double zAxis;
	private double altitude;
	private float accuracy;
	private int algorithm_count;
	private String photo_identifier;	
	
	StringCharacterIterator stringCharacterIterator;


	public ReadCSV() {
		System.out.println("Constructor Created");
	}

	public void read(String filepath){
		try {
			System.out.println("Class Loaded");
			BufferedReader in = new BufferedReader(new FileReader(filepath));
			System.out.println("Class Loaded");

			//Read CSV file, get past the first line then work into the StringTokenizer
			String tempLine = in.readLine();
			while ((tempLine = in.readLine()) != null) {
				//System.out.println("Reading File");
				//tempLine = in.readLine();
				stringTokenizer = new StringTokenizer(tempLine,",");
				int column = 0;
				while(stringTokenizer.hasMoreTokens()){
					switcher(column, stringTokenizer.nextToken());
//					System.out.print(stringTokenizer.nextToken() + ", ");
				}				
				//System.out.println();
				new ReportInstance(latitude, longitude, time, lake, lowerEstimate, higherEstimate, agreedEstimate, 0.0, 0.0, 0.0, altitude, accuracy);
			}
			JOptionPane.showMessageDialog(new JFrame(), "CSV File: " + filepath + " Uploaded!", "Information Message", JOptionPane.INFORMATION_MESSAGE);
			in.close();
			System.out.println("Input Finished");
		} catch (IOException e) {
			System.out.println("Error");
		}
	}

	public void switcher(int columnNumber, String item){
		switch (columnNumber){
		case 0:
			//System.out.println(item);
			remover(item);
			//id = Integer.parseInt(item);
			break;
		case 1:
			//System.out.print(item);
			remover(item);
			break;
	
		case 2:
			//System.out.print(item);
			remover(item);
			break;
			
		case 3:
			remover(item);
			//System.out.print(item);
			break;
			
		case 4:
			remover(item);
			//System.out.print(item);
			break;
			
		case 5:
			remover(item);
			//System.out.print(item);
			break;
			
		case 6:
			remover(item);
			//System.out.print(item);
			break;
		
		case 7:
			//System.out.print(item);
			remover(item);
			break;
			
		case 8:
			//System.out.print(item);
			remover(item);
			break;
		
		case 9:	
			//System.out.print(item);
			remover(item);
			break;

		case 10:
			//System.out.print(item);
			remover(item);
			break;
		}
	}
	
	/** This method removes the " quotations from the CSV files.
	 * 
	 * @param word
	 * @return
	 */

	public String remover(String word){
		String tempString;
		tempString = word.replace("\"", "");
		System.out.println(tempString);
		return tempString;
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