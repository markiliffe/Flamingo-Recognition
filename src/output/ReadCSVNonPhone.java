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
import core.ReportInstanceNonPhone;
public class ReadCSVNonPhone{

	private Vector<ReportInstance> reports = new Vector<ReportInstance>();
	private StringTokenizer stringTokenizer;

	private int id;
	private double latitude;
	private double longitude;
	private String time;
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


	public ReadCSVNonPhone() {
		System.out.println("Constructor Created");
	}

	public void read(String filepath){
		try {
			System.out.println("Class Loaded");
			BufferedReader in = new BufferedReader(new FileReader(filepath));
			//Read CSV file, get past the first line then work into the StringTokenizer
			String tempLine = in.readLine();
			int row = 0;
			int column = 0;
			while ((tempLine = in.readLine()) != null) {
				System.out.println("Column:" + column);
				tempLine = in.readLine();
				stringTokenizer = new StringTokenizer(tempLine,",");
				while(stringTokenizer.hasMoreTokens()){
					switcher(column, stringTokenizer.nextToken());
//					System.out.print(stringTokenizer.nextToken() + ", ");
					System.out.println(row + " " + column);
					row++;
				}	
				column++;
				//new ReportInstanceNonPhone(latitude, longitude, time, lake, lowerEstimate, higherEstimate, agreedEstimate, 0.0, 0.0, 0.0, altitude, accuracy);
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
			remover(item);
			break;
		case 1:
			this.latitude = Double.parseDouble(remover(item));
			break;
	
		case 2:
			this.longitude = Double.parseDouble(remover(item));
			break;
			
		case 3:
			this.time = remover(item);
			break;
			
		case 4:
			this.lake = remover(item);
			break;
			
		case 5:
			this.lowerEstimate = Integer.parseInt(remover(item));
			break;
			
		case 6:
			this.higherEstimate = Integer.parseInt(remover(item));
			break;
		
		case 7:
			this.agreedEstimate = Integer.parseInt(remover(item));
			break;
			
		case 8:
			this.altitude = Double.parseDouble(remover(item));
			break;
		
		case 9:	
			this.accuracy = Float.parseFloat(remover(item));
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
		if(word.startsWith(" ")){
			System.out.println("Before substring:" + tempString);
			tempString = tempString.substring(1);
			System.out.println("After substring:" + tempString + " !");
		}
		//System.out.println(tempString + " !");
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