package com.covenant.Utils;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;

public class Utils {

	public static void copyFolder(File sourceFolder, File destinationFolder) throws Exception
    {
        //Check if sourceFolder is a directory or file
        //If sourceFolder is file; then copy the file directly to new location
		if(!sourceFolder.exists()) {
			throw new Exception("el archivo no existe");
		}
        if (sourceFolder.isDirectory()) 
        {
            //Verify if destinationFolder is already present; If not then create it
            if (!destinationFolder.exists()) 
            {
                destinationFolder.mkdir();
                System.out.println("Directory created :: " + destinationFolder);
            }
             
            //Get all files from source directory
            String files[] = sourceFolder.list();
             
            //Iterate over all files and copy them to destinationFolder one by one
            for (String file : files) 
            {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);
                 
                //Recursive function call
                copyFolder(srcFile, destFile);
            }
        }
        else
        {
        	//Verify if destinationFolder is already present; If not then create it
            if (!destinationFolder.exists()) 
            {
                destinationFolder.mkdir();
                System.out.println("Directory created :: " + destinationFolder);
            }
        	// File sourceFolder_ = new File(sourceFolder.getCanonicalPath().replace("%20", " "));
        	// File destinationFolder_ = new File(destinationFolder.getCanonicalPath().replace("%20", " "));
            //Copy the file content from one place to another 
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied :: " + destinationFolder);
        }
    }
	
	public static String getRounded(float n) {
		return String.format("%.1f", n);
	}
	
	public static String getAsPer(float n) {
		if(Float.isNaN(n) )
			return "0%";
		return getRounded(n*100) + "%";
	}
}
