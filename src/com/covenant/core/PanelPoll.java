package com.covenant.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.covenant.Pojo.Response;
import com.covenant.Utils.DataBase;
import com.covenant.Utils.DataQueries;
import com.covenant.Utils.Utils;
import com.covenant.tools.PDFGenerator;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class PanelPoll extends Panel{
	private JTextField textField;
	JComboBox comboBox;
	int actual_question_id=0;
	JButton btnIniciar;
	JButton btnTerminar;
	JPanel panelDrow;
	PanelQuorum main;
	List<Response> responces =new ArrayList<Response>();
	JLabel lblB;
	
	private JTextField textField_1;
	private enum QType{
		SN,
		AB,
		ABC,
		ABCD
	}
	QType type;
	
	String question; 
	

	private JTextField textField_A;
	private JTextField textField_B;
	private JTextField textField_D;
	private JTextField textField_C;
	private JLabel lblA;
	private JLabel lblC;
	private JLabel lblD;
	private JPanel panelOptions;
	private JLabel lblInfo;
	
	public PanelPoll(PanelQuorum main) {
		this.main = main;
		
		panelDrow = new JPanel();
		panelDrow.setBackground(Color.WHITE);
		
		panelOptions = new JPanel();
		panelOptions.setLayout(null);
		
		JLabel lblPregunta = new JLabel("Pregunta :");
		lblPregunta.setBounds(10, 11, 51, 14);
		panelOptions.add(lblPregunta);
		lblPregunta.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textField = new JTextField();
		textField.setBounds(10, 44, 159, 20);
		panelOptions.add(textField);
		
		textField.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
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
		
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
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
		comboBox.setBounds(102, 8, 67, 20);
		panelOptions.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"SI/NO", "AB","ABC","ABCD"}));
		
		textField_A = new JTextField();
		textField_A.setBounds(50, 75, 119, 20);
		panelOptions.add(textField_A);
		textField_A.setColumns(10);
		
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
		
		btnTerminar = new JButton("Terminar");
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
		
		btnIniciar = new JButton("Iniciar");
		btnIniciar.setBounds(71, 199, 98, 23);
		panelOptions.add(btnIniciar);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 200, 51, 20);
		panelOptions.add(textField_1);
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(KeyEvent.VK_ENTER == e.getKeyCode()) {
					doScan();
					
				}
			}
		});
		textField_1.setColumns(10);
		
		lblInfo = new JLabel("New label");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(panelDrow, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelOptions, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblInfo, GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelOptions, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panelDrow, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblInfo)
					.addGap(24))
		);
		
		JButton btnSalir = new JButton("Reportes");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.assist();
			}
		});
		btnSalir.setBounds(71, 245, 98, 23);
		panelOptions.add(btnSalir);
		setLayout(groupLayout);
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setEditable(false);
				btnIniciar.setEnabled(false);
				btnTerminar.setEnabled(true);
				setResOptions(false, false, false, false);
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
				
				setQuestion(textField.getText());
				String SQL = "INSERT INTO cvn_question(name,type) VALUES ('"+textField.getText()+"','"+type.toString()+"')";
				System.out.println(DataBase.get().execute(SQL));
				String SQLq = "SELECT cvn_question_id FROM cvn_question order by  cvn_question_id desc ";
				ResultSet rs = DataBase.get().executeQuery(SQLq);
				
				try {
					if(rs.next()) {
						int q_id = rs.getInt("cvn_question_id");
						setActual_question_id(q_id);
						
						
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
						
						
						
						String SQL_Res ="SELECT * FROM  cvn_response where cvn_question_id = "+q_id;
						ResultSet rsRes = DataBase.get().executeQuery(SQL_Res);
						responces = new ArrayList<Response>();
						while(rsRes.next()) {
							
							responces.add(new Response(rsRes.getInt("cvn_response_id"),rsRes.getString("value"),rsRes.getString("name")));
						}
						
						
					}
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				refreshPanelDraw();
			}
		});
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setEditable(true);
				btnIniciar.setEnabled(true);
				btnTerminar.setEnabled(false);
				
				panelDrow.getGraphics().clearRect(0, 0,  panelDrow.getWidth(), panelDrow.getHeight());
				
			}
		});
	}


	protected void refreshPanelDraw() {
		if(type == QType.SN) {
			paintSN();
		}else if(type == QType.AB){
			paintAB();
		}else if(type == QType.ABC){
			paintABC();
		}else if(type == QType.ABCD){
			paintABCD();
		}
		
	}


	public int getActual_question_id() {
		return actual_question_id;
	}

	private Response filterByValue(List<Response> res, String value) {
		final String value_ =value;
		return res.stream().filter(r -> r.getValue().equals(value_)).collect(Collectors.toList()).get(0);
	}
	
	public void paintAB() {
		List<Response> responses = DataQueries.getResponseByQuestionID(actual_question_id);
		paintBar(panelDrow, (1f/3f), 0, (DataQueries.getCff("A",actual_question_id)),true,"green","A. "+filterByValue(responses, "A").getName());
		paintBar(panelDrow, (2f/3f), 0, (DataQueries.getCff("B",actual_question_id)),false,"blue","B. "+filterByValue(responses, "B").getName());
	}
	
	public void paintABC() {
		List<Response> responses = DataQueries.getResponseByQuestionID(actual_question_id);
		paintBar(panelDrow, (1f/4f), 0, (DataQueries.getCff("A",actual_question_id)),true,"green", "A. "+filterByValue(responses, "A").getName());
		paintBar(panelDrow, (2f/4f), 0, (DataQueries.getCff("B",actual_question_id)),false,"red","B. "+filterByValue(responses, "B").getName());
		paintBar(panelDrow, (3f/4f), 0, (DataQueries.getCff("C",actual_question_id)),false,"blue","C. "+filterByValue(responses, "C").getName());
	}
	
	public void paintABCD() {
		List<Response> responses = DataQueries.getResponseByQuestionID(actual_question_id);
		paintBar(panelDrow, (1f/5f), 0, (DataQueries.getCff("A",actual_question_id)),true,"green","A. "+filterByValue(responses, "A").getName());
		paintBar(panelDrow, (2f/5f), 0, (DataQueries.getCff("B",actual_question_id)),false, "red","B. "+filterByValue(responses, "B").getName());
		paintBar(panelDrow, (3f/5f), 0, (DataQueries.getCff("C",actual_question_id)),false, "blue","C. "+filterByValue(responses, "C").getName());
		paintBar(panelDrow, (4f/5f), 0, (DataQueries.getCff("D",actual_question_id)),false,"purple","D. "+filterByValue(responses, "D").getName());
	}
	
	public void paintSN() {
		paintBar(panelDrow, (1f/3f), 0, (DataQueries.getCff("SI",actual_question_id)),true,"green","SI");
		paintBar(panelDrow, (2f/3f), 0, (DataQueries.getCff("NO",actual_question_id)),false,"red","NO");
	}
	
	
	
	
	public void paintBar(JPanel panel, float dx, float dy, float size, boolean clear, String bar_color, String name) {
		
		float dFont=0.03f;
		Color color = panel.getGraphics().getColor();
		//panel.getGraphics().fillRect(0, 0, panel.getWidth(), panel.getHeight());
		Graphics g =panel.getGraphics(); 
		if(clear) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0,  panel.getWidth(), panel.getHeight());
		}
		float w=panel.getWidth();
		float h=panel.getHeight();
		
		
		//progress bar
		float b = h/7f;//borde superior
		float l = h/20f;//borde inferior
		float pixel =size*(h-b-l);
		
		BufferedImage image;
		try {
			image = ImageIO.read(new File("./Resources/Images/"+bar_color+"Bar.png"));
			g.drawImage(image,
					(int)(dx*w), 
					(int)(b+(h-b-l)*(1-size)), 
					(int)(0.18f*w), 
					(int)(pixel),null);
		}catch(Exception e) {
			g.fillRect(
					(int)(dx*w), 
					(int)(b+(h-b-l)*(1-size)), 
					(int)(0.18f*w), 
					(int)(pixel));	
			e.printStackTrace();
		}
		
		
		System.out.println("pixel " +pixel);
		
		
		g.setColor(Color.black);
		//border
		g.drawRect(
			(int)(dx*w), 
			(int)(b), 
			(int)(0.18f*w), 
			(int)(h-b-l));
		
		//text 1-100%
		g.setFont(new Font("Arial", Font.BOLD, (int)(w*dFont)));
		g.drawString(Utils.getRounded(size*100) + "%", (int)(dx*w+w/20), (int)(h-h/10f));
		g.drawString(name , (int)(dx*w+w/18), (int)(h-h/5f));
		g.drawString(getQuestion(), (int)(0), (int)(b/2));
		g.setColor(color);
				
	}
	

	public void setActual_question_id(int actual_question_id) {
		this.actual_question_id = actual_question_id;
	}
	
	private void setResOptions(boolean a, boolean b, boolean c, boolean d) {
		panelOptions.setVisible(false);
		lblA.setVisible(a);
		textField_A.setVisible(a);
		lblB.setVisible(b);
		textField_B.setVisible(b);
		lblC.setVisible(c);
		textField_C.setVisible(c);
		lblD.setVisible(d);
		textField_D.setVisible(d);
		panelOptions.setVisible(true);
	}
	
	private void doScan() {
		int user_id=0;
		int length =textField_1.getText().length();
		String ref_id = textField_1.getText().substring(0,length-1);
		String code=  textField_1.getText().substring(length-1,length);
		
		if(code.equals("0")) {
			lblInfo.setText(ref_id +"> registrando...");
			main.addUser(DataQueries.getUserByRef(ref_id));
			refreshPanelDraw();
			
			textField_1.setText("");
			return;
		}else if(!main.containsUserByRef(ref_id)) {
			lblInfo.setText(ref_id +"> NO REGISTRADO");
			refreshPanelDraw();
			textField_1.setText("");
			return;
		}else {
			lblInfo.setText(ref_id +" ");
		}
		
		
		String response = textField_1.getText().substring(length-1,length);
		String  value = "null";
		switch (response) {
			case "1":
				value="SI";
				break;
			case "2":
				value="NO";
				break;
			case "3":
				value="A";
				break;
			case "4":
				value="B";
				break;
			case "5":
				value="C";
				break;
			case "6":
				value="D";
				break;
		}
		//get user id
		
		if(type == QType.SN && (!value.equals("SI") && !value.equals("NO"))) {
			paintSN();
			textField_1.setText("");
			return;
		}
		else
		if(type == QType.AB && (value !="A" && value !="B")){
			textField_1.setText("");
			paintAB();
			return;
		}
		else
		if(type == QType.ABC && (value !="A" && value !="B" && value !="C")) {
			paintABC();
			textField_1.setText("");
			return;
		}else
		if(type == QType.ABCD && (value !="A" && value !="B" && value !="C" && value !="D")) {
			paintABCD();
			textField_1.setText("");
			return;
		}
		
		String SQL="Select cvn_user_id from cvn_user where ref_id= '"+ref_id+"'";
		ResultSet rs = DataBase.get().executeQuery(SQL);
		try {
			if(rs.next()) {
				user_id= rs.getInt("cvn_user_id");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		final String  value_ =value;
		//insert response
		if(user_id!=0) {
			
			String SQL_I = "insert into cvn_res_user(cvn_user_id,cvn_question_id,cvn_response_id) "
					+ "VALUES ("+user_id+","+actual_question_id+","+responces.stream().filter(a -> a.getValue().equals(value_)).collect(Collectors.toList()).get(0).getID()+")";
			DataBase.get().execute(SQL_I);
		}
		
		refreshPanelDraw();
		
		textField_1.setText("");
	}
	
	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}
}
