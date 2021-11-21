package com.covenant.core;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
		btnNewButton.setBounds(254, 45, 172, 23);
		add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 133, 430, 9);
		add(separator);
		
		btnGenerarVoletas = new JButton("Crear Proyecto");
		btnGenerarVoletas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		        _Process process = new _Process();
		        process.start();
			}
		});
		btnGenerarVoletas.setBounds(254, 79, 172, 23);
		btnGenerarVoletas.setVisible(false);
		add(btnGenerarVoletas);
		
		JLabel lblSeleccionaLosDatos = new JLabel("Selecciona los Datos");
		lblSeleccionaLosDatos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSeleccionaLosDatos.setBounds(10, 49, 234, 14);
		add(lblSeleccionaLosDatos);
		
		lblNewLabel = new JLabel("<-->");
		lblNewLabel.setForeground(Color.GRAY);
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 10));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 108, 416, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Nombre del Proyecto:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(10, 20, 234, 14);
		add(lblNewLabel_2);
		
		txtMyproyecto = new JTextField();
		txtMyproyecto.setText("MiProyecto");
		txtMyproyecto.setBounds(254, 17, 172, 20);
		add(txtMyproyecto);
		txtMyproyecto.setColumns(10);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new goBack());
		btnCancelar.setBounds(331, 222, 95, 23);
		add(btnCancelar);
		
		txtrType = new JTextArea();
		txtrType.setFont(new Font("Consolas", Font.PLAIN, 10));
		txtrType.setBackground(UIManager.getColor("Button.background"));
		txtrType.setBounds(20, 153, 224, 92);
		add(txtrType);
		
		btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goPow();
			}
		});
		btnIniciar.setBounds(254, 153, 172, 44);
		btnIniciar.setVisible(false);
		add(btnIniciar);
		
		
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
			cal.setTime(sdf.parse("2021-11-22"));	
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
			}
			
			txtrType.setText(txtrType.getText()+"\n"+"Generando datos...");
	        FileProcessor process = new FileProcessor(mainfolder.getPath(),chooser.getSelectedFile().getPath());
	        
	        String result = process.processCSVData();
	        txtrType.setText(txtrType.getText()+"\n"+result);
	        txtrType.setText(txtrType.getText()+"\nProyecto <"+ main.projectName+ "> creado exitosamente"
					+ "\n   "+mainfolder);
	        //save properties
	        DataQueries.savePropery("name", main.projectName);
	        DataQueries.savePropery("date", "Extraordinaria 2021");
	        DataQueries.savePropery("path", mainfolder.getPath());
	        
	        
	        //Generate BarCodes
	        BarCodeGenerator.generate(mainfolder.getPath()+File.separator+"BarCodes");
	        txtrType.setText(txtrType.getText()+"\n"+"Codigos de Barras Generados...");
	        //Generar PDFs
	        PDFGenerator.process(mainfolder.getPath(),"Blank_8","Ballots");
	        txtrType.setText(txtrType.getText()+"\n"+"PDF generado para impresion en:"
	        		+ "\n   "+mainfolder+File.separator+"Ballots.pdf");
	        btnIniciar.setVisible(true);
	        
	        
		}
	}
	
	public void goPow(){
		main.Pow();
	}
}
