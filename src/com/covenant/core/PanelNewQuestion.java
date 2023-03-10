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
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.SystemColor;

public class PanelNewQuestion extends Panel {
	private JTextField textField_A;
	private JTextField textField_B;
	private JTextField textField_D;
	private JTextField textField_C;
	private JLabel lblA;
	private JLabel lblB;
	private JLabel lblC;
	private JLabel lblD;
	QType type;
	JTextArea textAreaQuestion;

	MainFramePanelQuorum main;
	PanelPoll poll;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final Action action = new SwingAction();
	private JRadioButton rdbtnNewRadioButton_3;

	/**
	 * Create the panel.
	 */
	public PanelNewQuestion(MainFramePanelQuorum main, PanelPoll poll) {
		this.main = main;
		this.poll = poll;

		Panel panelOptions = new Panel();
		panelOptions.setBackground(SystemColor.controlHighlight);
		panelOptions.setLayout(null);

		JButton btnTerminar = new JButton("Cancelar");
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.toPoll();
			}
		});
		btnTerminar.setBounds(52, 175, 98, 23);
		panelOptions.add(btnTerminar);

		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addQuestion();
			}
		});
		btnIniciar.setBounds(52, 150, 98, 23);
		panelOptions.add(btnIniciar);

		JScrollPane scrollPane = new JScrollPane();
		
				lblB = new JLabel("B");
				lblB.setHorizontalAlignment(SwingConstants.RIGHT);
		
				lblA = new JLabel("A");
				lblA.setHorizontalAlignment(SwingConstants.RIGHT);
		
				textField_A = new JTextField();
				textField_A.setColumns(10);
		
				textField_B = new JTextField();
				textField_B.setColumns(10);
		
				lblC = new JLabel("C");
				lblC.setHorizontalAlignment(SwingConstants.RIGHT);
		
				textField_C = new JTextField();
				textField_C.setColumns(10);
		
				lblD = new JLabel("D");
				lblD.setHorizontalAlignment(SwingConstants.RIGHT);
		
				textField_D = new JTextField();
				textField_D.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(lblA, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_A, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(lblB, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_B, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(lblC, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_C, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(lblD, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_D, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addGap(10)
					.addComponent(panelOptions, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panelOptions, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_A, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
								.addComponent(lblA))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_B, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
								.addComponent(lblB))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_C, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblC, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_D, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblD, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(44))))
		);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("AB");
		rdbtnNewRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()  == ItemEvent.SELECTED) {
					type = QType.AB;
					setResOptions(true, true, false, false);
				} 
			}
		});
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(10, 68, 109, 23);
		panelOptions.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("S/N");
		rdbtnNewRadioButton_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()  == ItemEvent.SELECTED) {
					type = QType.SN;
					setResOptions(false, false, false, false);
				} 
			}
		});
		rdbtnNewRadioButton_1.setSelected(true);
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(10, 41, 109, 23);
		panelOptions.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("ABC");
		rdbtnNewRadioButton_2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange()  == ItemEvent.SELECTED) {
					type = QType.ABC;
					setResOptions(true, true, true, false);
				} 
				
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_2);

		rdbtnNewRadioButton_2.setBounds(10, 94, 109, 23);
		panelOptions.add(rdbtnNewRadioButton_2);
		
		rdbtnNewRadioButton_3 = new JRadioButton("ABCD");
		buttonGroup.add(rdbtnNewRadioButton_3);
		rdbtnNewRadioButton_3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()  == ItemEvent.SELECTED) {
					type = QType.ABCD;
					setResOptions(true, true, true, true);
				}
			}
		});
		rdbtnNewRadioButton_3.setBounds(10, 120, 109, 23);
		panelOptions.add(rdbtnNewRadioButton_3);
		
		JLabel lblNewLabel = new JLabel("Tipo de pregunta");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 11, 187, 23);
		panelOptions.add(lblNewLabel);
		
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

	public void addQuestion() {

		// textArea.setEditable(false);

		poll.setQuestion(textAreaQuestion.getText());
		String SQL = "INSERT INTO cvn_question(name,type) VALUES ('" + textAreaQuestion.getText().replace("'", ".")
				+ "','" + type.toString() + "')";
		System.out.println(DataBase.get().execute(SQL));
		String SQLq = "SELECT cvn_question_id FROM cvn_question order by  cvn_question_id desc ";
		ResultSet rs = DataBase.get().executeQuery(SQLq);

		try {
			if (rs.next()) {
				int q_id = rs.getInt("cvn_question_id");
				poll.setActualQuestionById(q_id);

				if (type == QType.SN) {
					DataBase.get().execute("INSERT INTO cvn_response(value,name,cvn_question_id) "
							+ " VALUES ('SI','SI'," + q_id + ");");

					DataBase.get().execute(" INSERT INTO cvn_response(value,name,cvn_question_id) "
							+ " VALUES ('NO','NO'," + q_id + ");");
				}

				if (type == QType.AB || type == QType.ABC || type == QType.ABCD) {
					DataBase.get().execute("INSERT INTO cvn_response(value,name,cvn_question_id) " + " VALUES ('A','"
							+ textField_A.getText() + "'," + q_id + ");");

					DataBase.get().execute("INSERT INTO cvn_response(value,name,cvn_question_id) " + "VALUES ('B','"
							+ textField_B.getText() + "'," + q_id + ");");
				}
				if (type == QType.ABC || type == QType.ABCD) {
					DataBase.get().execute("INSERT INTO cvn_response(value,name,cvn_question_id) " + " VALUES ('C','"
							+ textField_C.getText() + "'," + q_id + ");");
				}
				if (type == QType.ABCD) {
					DataBase.get().execute("INSERT INTO cvn_response(value,name,cvn_question_id) " + " VALUES ('D','"
							+ textField_D.getText() + "'," + q_id + ");");
				}

			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		poll.addQuestion();
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
