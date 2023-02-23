package com.covenant.core;

import java.awt.Panel;
import javax.swing.JTextField;

import com.covenant.Pojo.User;
import com.covenant.Utils.DataQueries;
import com.covenant.tools.PDFGenerator;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelOut extends Panel{
	private JTextField textField;
	MainFramePanelQuorum main;
	
	public PanelOut(MainFramePanelQuorum main) {
		this.main = main;
		setLayout(null);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(KeyEvent.VK_ENTER == e.getKeyCode()) {
					doScan();
					
				}
			}
		});
		textField.setBounds(36, 92, 112, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblGraciasPorAsistir = new JLabel("Gracias Por Asistir");
		lblGraciasPorAsistir.setBounds(36, 67, 135, 14);
		add(lblGraciasPorAsistir);
		
		JButton btnTerminar = new JButton("Terminar");
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = DataQueries.getPropery("path");
				try {
					PDFGenerator.process(path,"Report2","Resultados", this.getClass());
					PDFGenerator.process(path,"Detailed","Detalle", this.getClass());
					PDFGenerator.process(path,"Assist","Asistencia", this.getClass());
				}catch(Exception err) {
					err.printStackTrace();
					System.out.println(err.getMessage());
				}
			}
		});
		btnTerminar.setBounds(59, 123, 89, 23);
		add(btnTerminar);
	}

	protected void doScan() {
		
		int length =textField.getText().length();
		String ref_id = textField.getText().substring(0,length-1);
		String code=  textField.getText().substring(length-1,length);
		User user = DataQueries.getUserByRef(ref_id+"");
		
		if(code.equals("0")) {
			
			DataQueries.addPresence(user,"OUT");
			textField.setText("");
			return;
		}
		textField.setText("");
	}
}
