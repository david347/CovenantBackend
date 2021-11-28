package com.covenant.core;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import com.covenant.Utils.DataQueries;
import com.covenant.Utils.Utils;
import com.covenant.tools.BarCodeGenerator;
import com.covenant.tools.FileProcessor;
import com.covenant.tools.PDFGenerator;
import javax.swing.JScrollPane;

public class PanelNew extends Panel {
	JFileChooser chooser;
	String choosertitle;
	String filepath;
	String filename;
	JLabel lblNewLabel;
	JButton btnGenerarVoletas;
	JButton btnIniciar;
	JTextArea txtrType;
	PanelQuorum main;
	
	private JTextField txtMyproyecto;
	

	/**
	 * Create the panel.
	 * @param main 
	 */
	public PanelNew(PanelQuorum main) {
		this.main = main;
		setLayout(null);
		
		JButton btnNewButton = new JButton("Seleccionar Archivo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser = new JFileChooser(); 
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle(choosertitle);
			    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			    //
			    // disable the "All files" option.
			    //
			    chooser.setAcceptAllFileFilterUsed(false);
			    
			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
			    	
			        System.out.println("getCurrentDirectory(): " 
			           +  chooser.getCurrentDirectory());
			        System.out.println("getSelectedFile() : " 
			           +  chooser.getSelectedFile());
			       
			        }
			    	
				else {
					System.out.println("No Selection ");
				}
			       
			    
			    lblNewLabel.setText(chooser.getSelectedFile().getPath());
			    btnGenerarVoletas.setVisible(true);
			}
		});
		btnNewButton.setBounds(529, 45, 172, 23);
		add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 133, 509, 9);
		add(separator);
		
		btnGenerarVoletas = new JButton("Crear Proyecto");
		btnGenerarVoletas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		        _Process process = new _Process();
		        process.start();
			}
		});
		btnGenerarVoletas.setBounds(529, 79, 172, 23);
		btnGenerarVoletas.setVisible(false);
		add(btnGenerarVoletas);
		
		JLabel lblSeleccionaLosDatos = new JLabel("Selecciona los Datos");
		lblSeleccionaLosDatos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSeleccionaLosDatos.setBounds(285, 49, 234, 14);
		add(lblSeleccionaLosDatos);
		
		lblNewLabel = new JLabel("<-->");
		lblNewLabel.setForeground(Color.GRAY);
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 10));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 108, 509, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Nombre del Proyecto:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(285, 20, 234, 14);
		add(lblNewLabel_2);
		
		txtMyproyecto = new JTextField();
		txtMyproyecto.setText("MiProyecto");
		txtMyproyecto.setBounds(529, 17, 172, 20);
		add(txtMyproyecto);
		txtMyproyecto.setColumns(10);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new goBack());
		btnCancelar.setBounds(606, 222, 95, 23);
		add(btnCancelar);
		
		btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goPow();
			}
		});
		btnIniciar.setBounds(529, 153, 172, 44);
		btnIniciar.setVisible(false);
		add(btnIniciar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 153, 487, 133);
		add(scrollPane);
		
		txtrType = new JTextArea();
		scrollPane.setViewportView(txtrType);
		txtrType.setFont(new Font("Consolas", Font.PLAIN, 10));
		txtrType.setBackground(UIManager.getColor("Button.background"));
		
		
	}
	
	public class goBack implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			btnGenerarVoletas.setVisible(false);
			main.Back();
		}
	}
	
	
	public Calendar getOffDate(){
		Calendar cal = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			cal.setTime(sdf.parse("2021-11-29"));	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cal;
	}
	
	public class _Process extends Thread {
		public void run() {
			if(Calendar.getInstance().after( getOffDate())) {
				txtrType.setText("La licencia expiró");	
				return;
			}
			
			main.projectName= txtMyproyecto.getText();
			txtMyproyecto.setEditable(false);
			File mainfolder = new File(chooser.getCurrentDirectory().getPath()+File.separator+main.projectName);
			File database = new File("./Resources/DBSeed");
			try {
				Utils.copyFolder(database, mainfolder);
			} catch (IOException e1) {
				e1.printStackTrace();
				txtrType.setText(e1.getMessage());
			} catch (Exception e) {
				// TODO Auto-generated catch block

				txtrType.setText(e.getMessage());
				e.printStackTrace();
			}
			
			txtrType.setText(txtrType.getText()+"\n"+"Generando datos...");
	        FileProcessor process = new FileProcessor(mainfolder.getPath(),chooser.getSelectedFile().getPath());
	        
	        String result = process.processCSVData();
	        txtrType.setText(txtrType.getText()+"\n"+result);
	        txtrType.setText(txtrType.getText()+"\nProyecto <"+ main.projectName+ "> creado exitosamente"
					+ "\n   "+mainfolder);

	        //save properties
	        try {
		        DataQueries.savePropery("name", main.projectName);
		        DataQueries.savePropery("date", "28/11/2021");
		        DataQueries.savePropery("path", mainfolder.getPath());
				
			} catch (Exception e) {
				txtrType.setText(txtrType.getText()+"\n"+e.getMessage());
			}
	        
	        //Generate BarCodes
	        try {
	        	BarCodeGenerator.generate(mainfolder.getPath()+File.separator+"BarCodes");	
			} catch (Exception e) {
				txtrType.setText(txtrType.getText()+"\n"+e.getMessage());
			}
	        
	        
	        //Generar PDFs
	        try {
	        	PDFGenerator.process(mainfolder.getPath(),"Blank_8","Ballots", this.getClass());
		        txtrType.setText(txtrType.getText()+"\n"+"PDF generado para impresion en:"
		        		+ "\n   "+mainfolder+File.separator+"Ballots.pdf");	
			} catch (Exception e) {
				txtrType.setText(txtrType.getText()+"\n"+e.getMessage());
			}
	        
	        btnIniciar.setVisible(true);
	        
	        
		}
	}
	
	public void goPow(){
		main.Pow();
	}
}
