package com.covenant.tools;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import com.covenant.Utils.DataBase;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class PDFGenerator {

	static HashMap  hm = null;
	public static void process(String output, String jasperName, String fileName) {
		try {
			   System.out.println("Start ....");
			   // Get jasper report
			   String jrxmlFileName = "./Resources/Jasper/"+jasperName+".jrxml";
			   String jasperFileName = "./Resources/Jasper/"+jasperName+".jasper";
			   String pdfFileName = output+File.separator+fileName+".pdf";

			   JasperCompileManager.compileReportToFile(jrxmlFileName, jasperFileName);
			   
			   
			   // Get the connection
			   Connection conn = DataBase.get().conn;

			   // Generate jasper print
			   JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperFileName, null, conn);

			   // Export pdf file
			   JasperExportManager.exportReportToPdfFile(jprint, pdfFileName);
			   
			   System.out.println("Done exporting reports to pdf");
			   
			  } catch (Exception e) {
			   System.out.print("Exceptiion" + e);
			  }
	}
}
