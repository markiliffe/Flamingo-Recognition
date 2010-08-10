package output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import core.*;
public class WriteCSV{

	private Vector<ReportInstance> reports = new Vector<ReportInstance>();

	public WriteCSV(Vector<ReportInstance> reports){
		this.setReports(reports);
	}

	public void write(){
//		if (reports.isEmpty()) {
//			System.out.println("Report Vector is Empty");
//		} else {
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter("outfilename.csv"));
				//Form the CSV file
				for(int i = 0; i<=10;i++){
				out.write("Mark Iliffe,");
				out.newLine();
				}
				out.close();
				System.out.println("Output Finished");
			} catch (IOException e) {
			}
		}

	/**
	 * @param reports the reports to set
	 */
	public void setReports(Vector<ReportInstance> reports) {
		getReports();
	}

	/**
	 * @return the reports
	 */
	public Vector<ReportInstance> getReports() {
		return Main.getReports();
	}
	}
//}
