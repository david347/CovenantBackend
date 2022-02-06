package com.covenant.core;

import java.awt.Panel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.covenant.Pojo.Response;
import com.covenant.Utils.DataBase;
import com.covenant.core.PanelPoll.QType;

import javax.swing.JTextArea;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class PanelNewQuestion extends Panel {
	private JTextField textField_A;
	private JTextField textField_B;
	private JTextField textField_D;
	private JTextField textField_C;
	private JLabel lblA;
	private JLabel lblB;
	private JLabel lblC;
	private JLabel lblD;
	private JComboBox comboBox;
	QType type;
	JTextArea textAreaQuestion;

	MainFramePanelQuorum main;
	PanelPoll  poll;

	/**
	 * Create the panel.
	 */
	public PanelNewQuestion(MainFramePanelQuorum main, PanelPoll  poll) {
		this.main = main;
		this.poll = poll;
		
		Panel panelOptions = new Panel();
		panelOptions.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(comboBox.getSelectedIndex()==0) {
					type=QType.SN;
					setResOptions(false, false, false, false);
				}else				
				if(comboBox.getSelectedIndex()==1) {
					type=QType.AB;
					setResOptions(true, true, false, false);
				}else
				if(comboBox.getSelectedIndex()==2) {
					type=QType.ABC;
					setResOptions(true, true, true, false);
				}else
				if(comboBox.getSelectedIndex()==3) {
					type=QType.ABCD;
					setResOptions(true, true, true, true);
				}	
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Si / No", "AB", "ABC", "ABCD"}));
		comboBox.setBounds(102, 8, 67, 20);
		panelOptions.add(comboBox);
		
		textField_A = new JTextField();
		textField_A.setColumns(10);
		textField_A.setBounds(50, 75, 119, 20);
		panelOptions.add(textField_A);
		
		lblA = new JLabel("A");
		lblA.setHorizontalAlignment(SwingConstants.RIGHT);
		lblA.setBounds(10, 75, 30, 20);
		panelOptions.add(lblA);
		
		textField_B = new JTextField();
		textField_B.setColumns(10);
		textField_B.setBounds(50, 106, 119, 20);
		panelOptions.add(textField_B);
		
		lblB = new JLabel("B");
		lblB.setHorizontalAlignment(SwingConstants.RIGHT);
		lblB.setBounds(10, 106, 30, 20);
		panelOptions.add(lblB);
		
		JButton btnTerminar = new JButton("Cancelar");
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.toPoll();
			}
		});
		btnTerminar.setBounds(71, 222, 98, 23);
		panelOptions.add(btnTerminar);
		
		textField_D = new JTextField();
		textField_D.setColumns(10);
		textField_D.setBounds(50, 168, 119, 20);
		panelOptions.add(textField_D);
		
		textField_C = new JTextField();
		textField_C.setColumns(10);
		textField_C.setBounds(50, 137, 119, 20);
		panelOptions.add(textField_C);
		
		lblC = new JLabel("C");
		lblC.setHorizontalAlignment(SwingConstants.RIGHT);
		lblC.setBounds(10, 137, 30, 20);
		panelOptions.add(lblC);
		
		lblD = new JLabel("D");
		lblD.setHorizontalAlignment(SwingConstants.RIGHT);
		lblD.setBounds(10, 168, 30, 20);
		panelOptions.add(lblD);
		
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addQuestion();
			}
		});
		btnIniciar.setBounds(71, 199, 98, 23);
		panelOptions.add(btnIniciar);
		
		JButton btnNewButton_2 = new JButton("?");
		btnNewButton_2.setBounds(154, 346, 43, 23);
		panelOptions.add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panelOptions, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
						.addComponent(panelOptions, GroupLayout.PREFERRED_SIZE, 380, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		textAreaQuestion = new JTextArea();
		textAreaQuestion.setWrapStyleWord(true);
		textAreaQuestion.setRows(3);
		textAreaQuestion.setLineWrap(true);
		textAreaQuestion.setFont(new Font("Calibri", Font.BOLD, 20));
		scrollPane.setViewportView(textAreaQuestion);
		
		JLabel lblPregunta = new JLabel("Pregunta :");
		lblPregunta.setHorizontalAlignment(SwingConstants.LEFT);
		lblPregunta.setFont(new Font("Calibri", Font.BOLD, 20));
		scrollPane.setColumnHeaderView(lblPregunta);
		setLayout(groupLayout);

	}
	
	private void setResOptions(boolean a, boolean b, boolean c, boolean d) {
		lblA.setVisible(a);
		textField_A.setVisible(a);
		lblB.setVisible(b);
		textField_B.setVisible(b);
		lblC.setVisible(c);
		textField_C.setVisible(c);
		lblD.setVisible(d);
		textField_D.setVisible(d);
	}
	
	public void addQuestion () {

		// textArea.setEditable(false);
		
		type=QType.SN;
		if(comboBox.getSelectedIndex()==1) {
			type=QType.AB;
		}
		if(comboBox.getSelectedIndex()==2) {
			type=QType.ABC;
		}
		if(comboBox.getSelectedIndex()==3) {
			type=QType.ABCD;
		}
		
		poll.setQuestion(textAreaQuestion.getText());
		String SQL = "INSERT INTO cvn_question(name,type) VALUES ('"+textAreaQuestion.getText().replace("'", ".")+"','"+type.toString()+"')";
		System.out.println(DataBase.get().execute(SQL));
		String SQLq = "SELECT cvn_question_id FROM cvn_question order by  cvn_question_id desc ";
		ResultSet rs = DataBase.get().executeQuery(SQLq);
		
		try {
			if(rs.next()) {
				int q_id = rs.getInt("cvn_question_id");
				poll.setActualQuestionById(q_id);
				
				
				if(type == QType.SN) {
					DataBase.get().execute("INSERT INTO cvn_response(value,name,cvn_question_id) "+
							" VALUES ('SI','SI',"+q_id+");");
					
							
					DataBase.get().execute(" INSERT INTO cvn_response(value,name,cvn_question_id) "+
							" VALUES ('NO','NO',"+q_id+");");
				}
				
				if(type == QType.AB || type == QType.ABC ||type == QType.ABCD) {
					DataBase.get().execute("INSERT INTO cvn_response(value,name,cvn_question_id) "+
							" VALUES ('A','"+textField_A.getText()+"',"+q_id+");");
							
					DataBase.get().execute("INSERT INTO cvn_response(value,name,cvn_question_id) "+
							"VALUES ('B','"+textField_B.getText()+"',"+q_id+");");
				} 
				if(type == QType.ABC || type == QType.ABCD) {
					DataBase.get().execute("INSERT INTO cvn_response(value,name,cvn_question_id) "+
							" VALUES ('C','"+textField_C.getText()+"',"+q_id+");");
				}
				if(type == QType.ABCD ) {
					DataBase.get().execute("INSERT INTO cvn_response(value,name,cvn_question_id) "+
							" VALUES ('D','"+textField_D.getText()+"',"+q_id+");");
				}
				
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		poll.addQuestion();
	}
}
