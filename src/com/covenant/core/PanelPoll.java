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

import com.covenant.Pojo.Question;
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
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.GridLayout;

public class PanelPoll extends Panel{
	JTextArea textArea;
	int fontSize =20;
	int actualQuestionIndex=0;
	Question actualQuestion;
	JButton btnIniciar;
	JButton btnTerminar;
	JPanel panelDrow;
	MainFramePanelQuorum main;
	boolean isMouseOver =false;
	// public List<Response> responses =new ArrayList<Response>();
	Color mainColor =new Color(150,150,150);
	private JTextField textField_1;
	public enum QType{
		SN,
		AB,
		ABC,
		ABCD
	}
	
	String question; 
	private JPanel panelOptions;
	private JLabel lblInfo;
	private JButton btnNewButton_2;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JLabel lblQuestionId;
	
	public PanelPoll(MainFramePanelQuorum main) {
		this.main = main;
		

		panelDrow = new JPanel();
		panelDrow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				isMouseOver = true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				isMouseOver = false;
			}
		});
		panelDrow.setBackground(Color.WHITE);
		
		panelOptions = new JPanel();
		panelOptions.setLayout(null);
		
		btnTerminar = new JButton("Terminar");
		btnTerminar.setBounds(71, 222, 98, 23);
		panelOptions.add(btnTerminar);
		
		btnIniciar = new JButton("Iniciar - NQ");
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
		
		lblInfo = new JLabel("-");
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
								.addComponent(panelDrow, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panelOptions, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblInfo, GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelDrow, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
						.addComponent(panelOptions, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblInfo)
					.addGap(24))
		);
		
		JLabel lblPregunta = new JLabel("Pregunta :");
		lblPregunta.setFont(new Font("Calibri", Font.BOLD, 20));
		scrollPane.setColumnHeaderView(lblPregunta);
		lblPregunta.setHorizontalAlignment(SwingConstants.LEFT);
		
		JPanel panel = new JPanel();
		scrollPane.setRowHeaderView(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Calibri", Font.BOLD, 20));
		textArea.setRows(3);
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		
		JButton btnSalir = new JButton("Reportes");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.toAssist();
			}
		});
		btnSalir.setBounds(71, 245, 98, 23);
		panelOptions.add(btnSalir);
		
		btnNewButton_2 = new JButton("?");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInfo.setText(DataQueries.getMissed(actualQuestion.getCvn_question_id()));
			}
		});
		btnNewButton_2.setBounds(129, 346, 43, 23);
		panelOptions.add(btnNewButton_2);
		
		btnNewButton = new JButton(">");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Question> allQuestions = DataQueries.getAllQuestion();
				if(allQuestions.size() == 0)
					return;
				actualQuestionIndex = (actualQuestionIndex+1) % allQuestions.size();
				actualQuestion = allQuestions.get(actualQuestionIndex);

				refreshPanelDraw();
			}
		});
		btnNewButton.setBounds(139, 11, 43, 23);
		panelOptions.add(btnNewButton);
		
		btnNewButton_1 = new JButton("<");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				List<Question> allQuestions = DataQueries.getAllQuestion();
				if(allQuestions.size() == 0)
					return;
				actualQuestionIndex = (actualQuestionIndex-1);
				if (actualQuestionIndex<0)
					actualQuestionIndex= allQuestions.size()-1;
				actualQuestion = allQuestions.get(actualQuestionIndex);
				refreshPanelDraw();
			}
		});
		btnNewButton_1.setBounds(10, 11, 51, 23);
		panelOptions.add(btnNewButton_1);
		
		lblQuestionId = new JLabel("-");
		lblQuestionId.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuestionId.setBounds(71, 15, 58, 14);
		panelOptions.add(lblQuestionId);
		setLayout(groupLayout);
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setEditable(false);
				btnIniciar.setEnabled(false);
				btnTerminar.setEnabled(true);
				main.toNewQuestion();
				// setResOptions(false, false, false, false);
			}
		});
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setEditable(true);
				btnIniciar.setEnabled(true);
				btnTerminar.setEnabled(false);
				
				panelDrow.getGraphics().clearRect(0, 0,  panelDrow.getWidth(), panelDrow.getHeight());
				
			}
		});
		
		
	}
	
	public void init () {
		List<Question> allQuestions = DataQueries.getAllQuestion();
		if (allQuestions.size() != 0 && actualQuestionIndex == 0) {
			actualQuestionIndex= allQuestions.size()-1;
			actualQuestion = allQuestions.get(actualQuestionIndex);
		
		}
		refreshPanelDraw();
	}

	public void addQuestion() {
		main.toPoll();
		textArea.setEditable(false);
		btnIniciar.setEnabled(false);
		btnTerminar.setEnabled(true);
		// setResOptions(false, false, false, false);
		
		refreshPanelDraw();
	}
	
	
	
	
	protected void refreshPanelDraw() {
		if (actualQuestion== null)
			return;
		textArea.setText(actualQuestion.getName());
		lblQuestionId.setText(actualQuestion.getCvn_question_id()+"");
		
		//panel_1.getGraphics().fillRect(0, 0, panel_1.getWidth(), panel_1.getHeight());
		refreshTotals();
		doScan();
	}
	
	public void refreshTotals() {
		QType type = QType.valueOf(actualQuestion.getType());
		
		if(isMouseOver) {
			Color color = panelDrow.getGraphics().getColor();
			//panel.getGraphics().fillRect(0, 0, panel.getWidth(), panel.getHeight());
			Graphics g =panelDrow.getGraphics(); 
			g.setColor(Color.WHITE);
			g.fillRect(0, 0,  panelDrow.getWidth(), panelDrow.getHeight());
			float i =2.0f;
			for (Response res : actualQuestion.getResponses()) {
				paintText(panelDrow, 0.02f, 0.07f+i*0.08f, mainColor, res.getValue()+" : "+res.getName());
				i++;
			}
		}else {
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
		paintText(panelDrow, 0.02f, 0.07f, mainColor, "Totales: ");
		paintText(panelDrow, 0.15f, 0.07f, mainColor, "Coeficiente "+ Utils.getAsPer(DataQueries.getCff(actualQuestion.getCvn_question_id())));
		paintText(panelDrow, 0.45f, 0.07f, mainColor, "Quorum "+ Utils.getAsPer(DataQueries.getCff(actualQuestion.getCvn_question_id())/main.getQuorum()));	
	}


	private Response filterByValue(String value) {
		List<Response> responses = DataQueries.getResponseByQuestionID(actualQuestion.getCvn_question_id());
		final String value_ =value;
		return responses.stream().filter(r -> r.getValue().equals(value_)).collect(Collectors.toList()).get(0);
	}
	
	public void paintAB() {
		Float cffA = DataQueries.getCff("A",actualQuestion.getCvn_question_id());
		Float cffB = DataQueries.getCff("B",actualQuestion.getCvn_question_id());
		paintBar(panelDrow, (1f/3f), 0, (cffA/main.getQuorum()),true,"green","A. ", Utils.getAsPer(cffA));
		paintBar(panelDrow, (2f/3f), 0, (cffB/main.getQuorum()),false,"blue","B. ", Utils.getAsPer(cffB));
	}
	
	public void paintABC() {
		Float cffA = DataQueries.getCff("A",actualQuestion.getCvn_question_id());
		Float cffB = DataQueries.getCff("B",actualQuestion.getCvn_question_id());
		Float cffC = DataQueries.getCff("C",actualQuestion.getCvn_question_id());
		paintBar(panelDrow, (1f/4f), 0, (cffA/main.getQuorum()),true,"green", "A. ",Utils.getAsPer(cffA));
		paintBar(panelDrow, (2f/4f), 0, (cffB/main.getQuorum()),false,"red","B. ",Utils.getAsPer(cffB));
		paintBar(panelDrow, (3f/4f), 0, (cffC/main.getQuorum()),false,"blue","C. ",Utils.getAsPer(cffC));
	}
	
	public void paintABCD() {
		Float cffA = DataQueries.getCff("A",actualQuestion.getCvn_question_id());
		Float cffB = DataQueries.getCff("B",actualQuestion.getCvn_question_id());
		Float cffC = DataQueries.getCff("C",actualQuestion.getCvn_question_id());
		Float cffD = DataQueries.getCff("D",actualQuestion.getCvn_question_id());
		paintBar(panelDrow, (1f/5f), 0, (cffA/main.getQuorum()),true,"green","A. ",Utils.getAsPer(cffA));
		paintBar(panelDrow, (2f/5f), 0, (cffB/main.getQuorum()),false, "red","B. ",Utils.getAsPer(cffB));
		paintBar(panelDrow, (3f/5f), 0, (cffC/main.getQuorum()),false, "blue","C. ",Utils.getAsPer(cffC));
		paintBar(panelDrow, (4f/5f), 0, (cffD/main.getQuorum()),false,"purple","D. ",Utils.getAsPer(cffD));
	}
	
	public void paintSN() {
		Float cffSi = DataQueries.getCff("SI",actualQuestion.getCvn_question_id());
		Float cffNo = DataQueries.getCff("NO",actualQuestion.getCvn_question_id());
		paintBar(panelDrow, (1f/3f), 0, (cffSi/main.getQuorum()),true,"green","SI",Utils.getAsPer(cffSi));
		paintBar(panelDrow, (2f/3f), 0, (cffNo/main.getQuorum()),false,"red","NO",Utils.getAsPer(cffNo));
	}
	
	
	
	
	public void paintBar(JPanel panel, float dx, float dy, float size, boolean clear, String bar_color, String name, String text) {
		
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
		float barSize =size*(h-b-l);
		
		BufferedImage image;
		try {
			image = ImageIO.read(new File("./Resources/Images/"+bar_color+"Bar.png"));
			g.drawImage(image,
					(int)(dx*w), 
					(int)(h-l-barSize), 
					(int)(0.18f*w), 
					(int)(barSize),null);
		}catch(Exception e) {
			g.fillRect(
					(int)(dx*w), 
					(int)(h-l-barSize), 
					(int)(0.18f*w), 
					(int)(barSize));	
			e.printStackTrace();
		}
		
		g.setColor(Color.BLACK);
		//border
		g.drawRect(
			(int)(dx*w), 
			(int)(b), 
			(int)(0.18f*w), 
			(int)(h-b-l));
		
		//text 1-100%
		g.setFont(new Font("Arial", Font.BOLD, (int)(w*dFont)));
		g.drawString(text, (int)(dx*w+w/20), (int)(h-h/10f));
		g.drawString(name , (int)(dx*w+w/18), (int)(h-h/5f));
		// g.drawString(getQuestion(), (int)(0), (int)(b/2));
		g.setColor(color);
				
	}
	
	public void paintText(JPanel panel, float x, float y, Color textColor, String text) {
		float dFont=0.03f;
		Color basecolor = panel.getGraphics().getColor();
		Graphics g =panel.getGraphics(); 
		
		float w=panel.getWidth();
		float h=panel.getHeight();
		
		g.setColor(textColor);
		g.setFont(new Font("Arial", Font.BOLD, (int)(w*dFont)));
		g.drawString(text, (int)(x*w), (int)(y*h));
		g.setColor(basecolor);
				
	}
	

	public void setActualQuestionById(int actual_question_id) {
		this.actualQuestion = DataQueries.getQuestion(actual_question_id);
	}
	
	private void setResOptions(boolean a, boolean b, boolean c, boolean d) {
		panelOptions.setVisible(false);
		panelOptions.setVisible(true);
	}
	
	private void doScan() {
		int user_id=0;
		int length =textField_1.getText().length();
		if(length==0) {
			return;
		}

		
		
		String ref_id = textField_1.getText().substring(0,length-1);
		String code=  textField_1.getText().substring(length-1,length);
		
		if(!main.containsUserByRef(ref_id)) {
			if (DataQueries.getUserByRef(ref_id) != null) {
				lblInfo.setText(ref_id +"> registrando...");
				main.addUser(DataQueries.getUserByRef(ref_id));
			}
//			refreshPanelDraw();
			textField_1.setText("");
		}else {
			lblInfo.setText(ref_id +" ");
		}
		
		
		String response = code;
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
		QType type = QType.valueOf(actualQuestion.getType());
		if(type == QType.SN && (!value.equals("SI") && !value.equals("NO"))) {
			refreshTotals();
			textField_1.setText("");
			return;
		}
		else
		if(type == QType.AB && (value !="A" && value !="B")){
			textField_1.setText("");
			refreshTotals();;
			return;
		}
		else
		if(type == QType.ABC && (value !="A" && value !="B" && value !="C")) {
			refreshTotals();
			textField_1.setText("");
			return;
		}else
		if(type == QType.ABCD && (value !="A" && value !="B" && value !="C" && value !="D")) {
			refreshTotals();
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
			String SQL_D = "delete from cvn_res_user where "
					+ "cvn_user_id = "+user_id
					+" AND cvn_question_id ="+actualQuestion.getCvn_question_id();
			DataBase.get().execute(SQL_D);
			
			String SQL_I = "insert into cvn_res_user(cvn_user_id,cvn_question_id,cvn_response_id) "
					+ "VALUES ("+user_id+","+actualQuestion.getCvn_question_id()+","+actualQuestion.getResponses().stream().filter(a -> a.getValue().equals(value_)).collect(Collectors.toList()).get(0).getID()+")";
			DataBase.get().execute(SQL_I);
		}
		
		refreshTotals();
		textField_1.setText("");
	}
	
	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}
}
