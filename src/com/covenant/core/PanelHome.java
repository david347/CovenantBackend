package com.covenant.core;

import java.awt.Panel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import com.covenant.Utils.DataBase;
import com.covenant.Utils.PropertiesManager;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class PanelHome extends Panel{
	PanelQuorum main;
	PropertiesManager properties;
	String lastProjectPath = "";
	private JLabel lblInfo;
	private JButton btnAbrir;
	
	public class goPow implements ActionListener{
		
		
		
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser(); 
		    chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("Selecciona un Proyecto Covenant");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    //
		    // disable the "All files" option.
		    //
		    chooser.setAcceptAllFileFilterUsed(false);
		    
		    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
		    	
		    	openProject(chooser.getSelectedFile().getPath());
		    	
	        }else {
				lblInfo.setText("No selecciono una carpeta ");
			}
			
		}
		
	}
	
	public class goNew implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
	    		main.New();
		}
	}
	
	public PanelHome(PanelQuorum main) {
		
		
		this.main = main;
		
		setLayout(null);
		
		JButton btnNewButton = new JButton("Nuevo");
		btnNewButton.addActionListener(new goNew());
		btnNewButton.setBounds(267, 106, 89, 23);
		add(btnNewButton);
		
		JButton btnCargar = new JButton("Cargar");
		btnCargar.addActionListener(new goPow());
		btnCargar.setBounds(267, 140, 89, 23);
		add(btnCargar);
		
		btnAbrir = new JButton(">Iniciar");
		btnAbrir.setFont(new Font("Tahoma", Font.ITALIC, 11));
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openProject(lastProjectPath);
			}
		});
		btnAbrir.setBounds(267, 174, 89, 23);
		add(btnAbrir);
		btnAbrir.setVisible(false);
		
		lblInfo = new JLabel("New label");
		lblInfo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblInfo.setBounds(70, 178, 187, 14);
		lblInfo.setVisible(false);
		
		setBounds(267, 174, 836, 435);
		add(lblInfo);
		
		try {
			properties =  new PropertiesManager("./Resources", "project.properties");
			properties.toGlobal();
			lastProjectPath= (String) properties.getProperty("last");
			System.out.println(">"+lastProjectPath+"<");
			if(lastProjectPath != null && !lastProjectPath.equals("")) {
				lblInfo.setVisible(true);
				lblInfo.setText(lastProjectPath);
				btnAbrir.setVisible(true);
			}else {
				lblInfo.setVisible(false);
				
				btnAbrir.setVisible(false);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openProject(String path) {
		File localDB= new File(path+File.separator+"Covenant.db");
    	if(localDB.exists()) {
    		lastProjectPath= path;
    		DataBase.create(localDB.getPath());
    		main.Pow();
    		properties.setProperty("last", path);
    		
    		try {
    			properties.saveProperties();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}else {
    		lblInfo.setText(path+"Proyecto No Valido");
    	}
    	
    	
	}
}
