package output;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.StringCharacterIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import core.Main;
import core.ReportInstance;
import core.ReportInstanceNonPhone;

public class ReadCSVCamera{

	private Vector<ReportInstance> reports = new Vector<ReportInstance>();
	private StringTokenizer stringTokenizer;

	private String id;
	private String latitude;
	private String longitude;
	private String time;
	private String lake;
	
	private int recordNumber;
	
	StringCharacterIterator stringCharacterIterator;

	/**
	 * Default constructor, inform the user that the class was successfully instantiated.
	 * 
	 */
	public ReadCSVCamera() {
		System.out.println("Constructor Created");
	}

	
	/**
	 * Read Method, creates a string tokenizer and iterates through the CSV adding it to the main.ReportInstanceNonPhone
	 * 
	 * 
	 * @param filepath
	 */
	public void read(String filepath){
		try {
			System.out.println("Class Loaded");
			BufferedReader in = new BufferedReader(new FileReader(filepath));

			//Read CSV file, get past the first line then work into the StringTokenizer
			String tempLine = in.readLine();
			System.out.println("Reading Line");
			//Here row and column act as an iterator, while pulling the iterator class into the file may seem best practice, this is done so the 
			//import packages are minimized, to ensure larger compatibility when porting to the android API, which is not Java, just alot like it...
			int row = 0;
			int column = 0;
			while ((tempLine = in.readLine()) != null) {
				stringTokenizer = new StringTokenizer(tempLine,",");
				while(stringTokenizer.hasMoreTokens()){
					String tempTokenizerLine = stringTokenizer.nextToken();
					switcher(column, tempTokenizerLine);
					column++;
				}
				recordNumber++;
				column = 0;
				row++;
				Main.addReports(new ReportInstanceNonPhone(id, latitude, longitude, time, lake));
			}
			JOptionPane.showMessageDialog(new JFrame(), "CSV File: " + filepath + "\n" + recordNumber  + " Records Uploaded", "Records Successfully Uploaded", JOptionPane.INFORMATION_MESSAGE);
			in.close();
			System.out.println("Input Finished");
		} catch (IOException e) {
			System.out.println("Error");
		}
	}

	public void switcher(int columnNumber, String item){
		switch (columnNumber){
		case 0:
			String tempString = remover(item);
			this.id = tempString;
			System.out.print(this.id);

			break;
			
		case 1:
			String tempTime = remover(item);
			this.time = tempTime; 
			System.out.print(" " + this.time);
			break;
	
		case 2:
			String tempLake = remover(item);
			this.lake = tempLake;
			System.out.print(" " + this.lake);
			break;
			
		case 3:
			String tempLatitude = remover(item);
			this.latitude = tempLatitude;
			System.out.print(" " + this.latitude);
			break;

			
		case 4:
			String tempLongitude = remover(item);
			this.longitude = tempLongitude;
			System.out.println(" " + this.longitude);
			break;
			
		case 5:
			break;
		
		case 6:
			break;
		}
	}
	
	/** This method removes the " quotations from the CSV files.
	 * 
	 * @par am word
	 * @return
	 */

	public String remover(String word){
		String tempString;
		tempString = word.replace("\"", "");
		if(word.startsWith(" ")){
			tempString = tempString.substring(1);
		}
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