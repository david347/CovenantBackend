package com.covenant.core;

import java.awt.Panel;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import com.covenant.Utils.DataBase;
import com.covenant.Utils.DataQueries;
import com.covenant.Utils.PropertiesManager;
import com.covenant.Utils.Utils;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class PanelHome extends Panel{
	MainFramePanelQuorum main;
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
		    	try {
					properties =  new PropertiesManager("./Resources", "project.properties");
					lastProjectPath=chooser.getSelectedFile().getPath();
					properties.setProperty("last", lastProjectPath);
					properties.toGlobal();
	    			properties.saveProperties();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	
	        }else {
				lblInfo.setText("No selecciono una carpeta ");
			}
			
		}
		
	}
	
	public class goNew implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	    		main.toNewProject();
		}
	}
	
	public PanelHome(MainFramePanelQuorum main) {
		this.main = main;
		
		JButton btnNewButton = new JButton("Nuevo");
		btnNewButton.addActionListener(new goNew());
		
		JButton btnCargar = new JButton("Cargar");
		btnCargar.addActionListener(new goPow());
		
		btnAbrir = new JButton(">Iniciar");
		btnAbrir.setFont(new Font("Tahoma", Font.ITALIC, 11));
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openProject(lastProjectPath);
			}
		});
		btnAbrir.setVisible(false);
		
		lblInfo = new JLabel("New label");
		lblInfo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblInfo.setVisible(false);
		
		setBounds(267, 174, 565, 435);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(21)
					.addComponent(lblInfo, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnAbrir, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCargar, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
					.addGap(27))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAbrir)
						.addComponent(lblInfo))
					.addGap(7)
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCargar)
					.addContainerGap(327, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
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
    		main.updateUsers();
    		main.updateQuorum();
    		main.toQuorum();
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
	
	@Override
	public void paint(Graphics g) {
		Utils.paintBackground(this, g, "home");
	}
}
