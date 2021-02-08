package com.covenant.tools;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.covenant.Utils.DataBase;

public class FileProcessor {
	
	private String filePath;
	private String folder;
	DataBase db;
	public FileProcessor(String project_folder, String scv_path) {
		this.folder=project_folder;
		this.filePath=scv_path;
		db = new DataBase(folder+File.separator+"Covenant.db");
	}

	public String processCSVData() {
		StringBuilder errorCollector = new StringBuilder("@Error@ ");
		if (filePath == null || filePath.equals("")) {
			errorCollector.append("missing file path");
			return errorCollector.toString();
		}
		try {
			
			FileReader reader = new FileReader(filePath);
			CsvListReader csv = new CsvListReader(reader, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
			String[] header = csv.getHeader(true);
		
			int rowNumber = 1;
			List<Object> row;

			while ((row = csv.read(createCellProcessor(csv.length()))) != null) {
				String SQL = " INSERT INTO cvn_user(ref_id,name, coefficient) VALUES (";
				
				SQL+="'"+row.get(0)+"'"+",";
				SQL+="'"+row.get(1)+"'"+",";
				SQL+=row.get(2).toString().replace(",", ".");
				
				SQL+=")";
				
				db.execute(SQL);
				rowNumber++;
			}

		} catch (Exception ex) {
			errorCollector.append(ex.getMessage());
		}
		if (errorCollector.length() >= 9) {
			return errorCollector.toString();
		} else {
			return "@Success@";
		}
	}
	
	private static CellProcessor[] createCellProcessor(int size) {
		CellProcessor[] cellProcessor = new CellProcessor[size];
		for (int i = 0; i < size; i++) {
			cellProcessor[i] = new ConvertNullTo("");
		}

		return cellProcessor;
	}

}
