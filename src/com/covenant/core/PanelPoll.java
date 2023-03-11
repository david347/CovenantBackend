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
import com.covenant.Pojo.User;
import com.covenant.Utils.DataBase;
import com.covenant.Utils.DataQueries;
import com.covenant.Utils.Utils;
import com.covenant.tools.PDFGenerator;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JSplitPane;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.SpringLayout;
import java.awt.CardLayout;
import javax.swing.JTextPane;
import javax.swing.BoxLayout;

public class PanelPoll extends Panel{
	
	public enum QType{
		SN,
		AB,
		ABC,
		ABCD
	}
	
	public enum DRAW_CONFIG {
		FULL_INFO,
		QUESTION_SIMPLE,
		QUESTION_ANSWERS
	};
	
	private DRAW_CONFIG actualConfig = DRAW_CONFIG.FULL_INFO;  
	
	JTextPane QuestionFullPane;
	int fontSize =20;
	int fontSizeQuestion =20;
	int actualQuestionIndex=0;
	Question actualQuestion;
	JButton btnIniciar;
	JButton btnTerminar;
	MainFramePanelQuorum main;
	
	// public List<Response> responses =new ArrayList<Response>();
	Color mainColor =new Color(50,55,70);
	private BufferedImage imageFondo1;
	private JTextField textField_1;
	
	
	String question; 
	private JPanel panelOptions;
	private JLabel lblInfo;
	private JButton btnNewButton_2;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JLabel lblQuestionId;
	private JPanel panelDraw;
	private JPanel panelShowSimple;
	float YFix= 1f;
	private JPanel panelShowSimple_1;
	
	public PanelPoll(final MainFramePanelQuorum main) {
		this.main = main;
		try {
			imageFondo1 = ImageIO.read(new File("./Resources/Images/fondo1.png"));	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		panelOptions = new JPanel();
		panelOptions.setOpaque(true);
		panelOptions.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				updateFontSize(arg0.getWheelRotation());
			}
		});
		panelOptions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				actualConfig = DRAW_CONFIG.FULL_INFO;
				refreshTotals();
			}
		});
		panelOptions.setLayout(null);
		
		lblInfo = new JLabel("-");
		lblInfo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblInfo.setBackground(Color.ORANGE);
		
		panelDraw = new JPanel();
		
		QuestionFullPane = new JTextPane();
		QuestionFullPane.setBounds(0, 0, 492, 210);
		QuestionFullPane.setEditable(false);
		QuestionFullPane.setBackground(new Color(200, 225, 255));
		QuestionFullPane.setFont(new Font("Calibri", Font.BOLD, fontSizeQuestion));


		panelDraw.setBackground(new Color(200, 225, 255));
		fontSizeQuestion=40;
		
		btnNewButton_1 = new JButton("<");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				refreshAllQuestions((actualQuestionIndex-1));
			}
		});
		
		lblQuestionId = new JLabel("-");
		lblQuestionId.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnNewButton = new JButton(">");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshAllQuestions((actualQuestionIndex+1));
			}
		});
		
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(KeyEvent.VK_ENTER == e.getKeyCode()) {
					doScan();
				}
			}
		});
		textField_1.setColumns(10);
		
		btnIniciar = new JButton("Nueva");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.toNewQuestion();
				// setResOptions(false, false, false, false);
			}
		});
		
		btnTerminar = new JButton("Terminar");
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelDraw.getGraphics().clearRect(0, 0,  panelDraw.getWidth(), panelDraw.getHeight());
				
			}
		});
		
		JButton btnSalir = new JButton("Reportes");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.toAssist();
			}
		});
		
		btnNewButton_2 = new JButton("?");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInfo.setText(DataQueries.getMissed(actualQuestion.getCvn_question_id()));
			}
		});
		
		panelShowSimple = new JPanel();
		panelShowSimple.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				updateFontSize(arg0.getWheelRotation());
			}
		});
		panelShowSimple.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				actualConfig = DRAW_CONFIG.QUESTION_SIMPLE;
				refreshTotals();
			}
		});
		panelShowSimple.setLayout(null);
		panelShowSimple.setOpaque(true);
		
		panelShowSimple_1 = new JPanel();
		panelShowSimple_1.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				updateFontSize(e.getWheelRotation());
			}
		});
		panelShowSimple_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				actualConfig = DRAW_CONFIG.QUESTION_ANSWERS;
				refreshTotals();
			}
		});
		panelShowSimple_1.setLayout(null);
		panelShowSimple_1.setOpaque(true);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblInfo, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(8)
							.addComponent(panelDraw, GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnTerminar, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnIniciar, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnSalir, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(panelOptions, 0, 0, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnNewButton_2))
										.addComponent(panelShowSimple, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
										.addComponent(panelShowSimple_1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
									.addGap(23))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
									.addGap(10)
									.addComponent(lblQuestionId, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
									.addGap(10)
									.addComponent(btnNewButton)
									.addGap(20)))
							.addGap(5))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(13)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton_1)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(4)
									.addComponent(lblQuestionId))
								.addComponent(btnNewButton))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
								.addComponent(btnIniciar)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnTerminar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSalir, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(panelOptions, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelShowSimple, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelShowSimple_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addGap(240))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panelDraw, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
							.addGap(11)))
					.addComponent(lblInfo, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addGap(6))
		);
		panelDraw.setLayout(null);
		panelDraw.add(QuestionFullPane);
		setLayout(groupLayout);
		
	}

	protected void updateFontSize(int rotation) {
		fontSizeQuestion+=rotation;
		QuestionFullPane.setFont(new Font("Arial", Font.BOLD, fontSizeQuestion));
		
	}

	private int getFixedIndex(int index, int max) {
		if(index<0)
			return max-1;
		return index % max;
	}
	
	protected void refreshAllQuestions(int newIndex) {
		List<Question> allQuestions = DataQueries.getAllQuestion();
		if(allQuestions.size() != 0) {
			actualQuestionIndex = getFixedIndex(newIndex, allQuestions.size()) ;
			actualQuestion = allQuestions.get(actualQuestionIndex);	
		}
		refreshTotals();
	}

	public void init () {
		List<Question> allQuestions = DataQueries.getAllQuestion();
		refreshAllQuestions(-1);
	}

	public void addQuestion() {
		main.toPoll();
	}
	
	private void repaintBackground() {
		Graphics g =this.getGraphics(); 
		BufferedImage image;
		try {
			image = ImageIO.read(new File("./Resources/Images/sky.png"));
			g.drawImage(image, 0,0,this.getWidth(), this.getHeight(), null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.paint(this.getGraphics());
	}
	
	
	protected void refreshTotals() {
		System.out.println("actual Config:"+actualConfig.name()+", QuestionFullPane:"+QuestionFullPane.isVisible());
		repaintBackground();
		if (actualQuestion== null)
			return;
		
		String tmpQuestionStr = actualQuestion.getName()+"\n";
		if(actualConfig == DRAW_CONFIG.QUESTION_ANSWERS) {
			for (Response res : actualQuestion.getResponses()) {
				tmpQuestionStr+="\n"+ res.getValue()+". "+res.getName();
			}
		}
		QuestionFullPane.setText(tmpQuestionStr);
		//TODO mover esta linea
		lblQuestionId.setText(actualQuestion.getCvn_question_id()+"");
		
		if(actualConfig == DRAW_CONFIG.QUESTION_ANSWERS || actualConfig == DRAW_CONFIG.QUESTION_SIMPLE) {
			if(!QuestionFullPane.isVisible()) {
				QuestionFullPane.setBounds(10, 10, panelDraw.getWidth()-20, (int)(panelDraw.getHeight()*0.5f)-20);
				QuestionFullPane.setVisible(true);
			}
			YFix=0.5f;
			refreshResults();
			return;
		}else {
			if(QuestionFullPane.isVisible()) {
				QuestionFullPane.setVisible(false);
			}
			YFix=1f;
		}
		refreshResults();
		refreshQuorumInfo();
	}
	
	public void refreshResults() {
		QType type = QType.valueOf(actualQuestion.getType());
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
	
	public void refreshQuorumInfo() {
		main.cffQ = DataQueries.getCff(actualQuestion.getCvn_question_id());
		float fontSize = 0.018f;
		paintText(panelDraw, 0.02f, 0.04f, mainColor, "Quorum Total:  "+  Utils.getAsPer(main.quorum), fontSize);
		paintText(panelDraw, 0.02f, 0.08f, mainColor, "Quorum Actual: "+ Utils.getAsPer(main.cffQ), fontSize);
		paintText(panelDraw, 0.02f, 0.12f, mainColor, "Participación: "+ Utils.getAsPer(main.cffQ/main.quorum), fontSize);	
	}

	private Response filterByValue(String value) {
		List<Response> responses = DataQueries.getResponseByQuestionID(actualQuestion.getCvn_question_id());
		final String value_ =value;
		return (Response) responses.stream().filter(r -> r.getValue().equals(value_)).collect(Collectors.toList()).get(0);
	}
	
	public void paintAB() {
		Float cffA = DataQueries.getCff("A",actualQuestion.getCvn_question_id());
		Float cffB = DataQueries.getCff("B",actualQuestion.getCvn_question_id());
		paintBar(panelDraw, (1f/3f), 0, (cffA/main.cffQ),true,"green","A. ", Utils.getAsPer(cffA)+"("+Utils.getAsPer(cffA/main.cffQ)+")");
		paintBar(panelDraw, (2f/3f), 0, (cffB/main.cffQ),false,"blue","B. ", Utils.getAsPer(cffB)+"("+Utils.getAsPer(cffB/main.cffQ)+")");
	}
	
	public void paintABC() {
		Float cffA = DataQueries.getCff("A",actualQuestion.getCvn_question_id());
		Float cffB = DataQueries.getCff("B",actualQuestion.getCvn_question_id());
		Float cffC = DataQueries.getCff("C",actualQuestion.getCvn_question_id());
		paintBar(panelDraw, (1f/4f), 0, (cffA/main.cffQ),true,"green", "A. ",Utils.getAsPer(cffA)+"("+Utils.getAsPer(cffA/main.cffQ)+")");
		paintBar(panelDraw, (2f/4f), 0, (cffB/main.cffQ),false,"red","B. ",Utils.getAsPer(cffB)+"("+Utils.getAsPer(cffB/main.cffQ)+")");
		paintBar(panelDraw, (3f/4f), 0, (cffC/main.cffQ),false,"blue","C. ",Utils.getAsPer(cffC)+"("+Utils.getAsPer(cffC/main.cffQ)+")");
	}
	
	public void paintABCD() {
		Float cffA = DataQueries.getCff("A",actualQuestion.getCvn_question_id());
		Float cffB = DataQueries.getCff("B",actualQuestion.getCvn_question_id());
		Float cffC = DataQueries.getCff("C",actualQuestion.getCvn_question_id());
		Float cffD = DataQueries.getCff("D",actualQuestion.getCvn_question_id());
		paintBar(panelDraw, (1f/5f), 0, (cffA/main.cffQ),true,"green","A. ",Utils.getAsPer(cffA)+"("+Utils.getAsPer(cffA/main.cffQ)+")");
		paintBar(panelDraw, (2f/5f), 0, (cffB/main.cffQ),false, "red","B. ",Utils.getAsPer(cffB)+"("+Utils.getAsPer(cffB/main.cffQ)+")");
		paintBar(panelDraw, (3f/5f), 0, (cffC/main.cffQ),false, "blue","C. ",Utils.getAsPer(cffC)+"("+Utils.getAsPer(cffC/main.cffQ)+")");
		paintBar(panelDraw, (4f/5f), 0, (cffD/main.cffQ),false,"purple","D. ",Utils.getAsPer(cffD)+"("+Utils.getAsPer(cffD/main.cffQ)+")");
	}
	
	public void paintSN() {
		Float cffSi = DataQueries.getCff("SI",actualQuestion.getCvn_question_id());
		Float cffNo = DataQueries.getCff("NO",actualQuestion.getCvn_question_id());
		paintBar(panelDraw, (1f/3f), 0, (cffSi/main.cffQ),true,"green","SI",Utils.getAsPer(cffSi)+"("+Utils.getAsPer(cffSi/main.cffQ)+")");
		paintBar(panelDraw, (2f/3f), 0, (cffNo/main.cffQ),false,"red","NO",Utils.getAsPer(cffNo)+"("+Utils.getAsPer(cffNo/main.cffQ)+")");
	}
	
	
	
	
	public void paintBar(JPanel panel, float dx, float dy, float size, boolean clear, String bar_color, String name, String text) {
		
		dx=dx-0.1f;
		float dFont=0.026f;
		Color color = panel.getGraphics().getColor();
		float w=panel.getWidth();
		float h=panel.getHeight()*YFix;
		float hStart = (1f-YFix)*panel.getHeight();
		
		//panel.getGraphics().fillRect(0, 0, panel.getWidth(), panel.getHeight());
		Graphics g =panel.getGraphics(); 
		if(clear) {
			g.fillRect(0, (int)hStart,  panel.getWidth(), panel.getHeight());
			g.drawImage(imageFondo1, 0, (int)hStart,  panel.getWidth(), panel.getHeight(), null);
		}
		
		
		//progress bar
		float b = h/7f;//borde superior
		float l = h/20f;//borde inferior
		float barSize =size*(h-b-l);
		
		BufferedImage image;
		try {
			image = ImageIO.read(new File("./Resources/Images/"+bar_color+"Bar.png"));
			g.drawImage(image,
					(int)(dx*w), 
					(int)(h-l-barSize+hStart), 
					(int)(0.18f*w), 
					(int)(barSize),null);
		}catch(Exception e) {
			g.fillRect(
					(int)(dx*w), 
					(int)(h-l-barSize+hStart), 
					(int)(0.18f*w), 
					(int)(barSize));	
			e.printStackTrace();
		}
		
		g.setColor(Color.BLACK);
		//border
		g.drawRect(
			(int)(dx*w), 
			(int)(b+hStart), 
			(int)(0.18f*w), 
			(int)(h-b-l));
		
		//text 1-100%
		g.setFont(new Font("Arial", Font.BOLD, (int)(w*dFont)));
		g.drawString(text, (int)(dx*w+w/70), (int)(h-h/10f+hStart));
		g.drawString(name , (int)(dx*w+w/13), (int)(h-h/5f+hStart));
		// g.drawString(getQuestion(), (int)(0), (int)(b/2));
		g.setColor(color);
				
	}
	
	public void paintText(JPanel panel, float x, float y, Color textColor, String text) {
		paintText( panel, x, y, textColor, text, 0.03f);
	}
	
	public void paintText(JPanel panel, float x, float y, Color textColor, String text, float dFont) {
		
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
			User auxUser = DataQueries.getUserByRef(ref_id);
			if ( auxUser != null) {
				lblInfo.setText(ref_id +"> registrando...");
				main.addUser(auxUser);
			}
//			refreshPanelDraw();
			textField_1.setText("");
		}else {
			lblInfo.setText(ref_id +" ");
		}
		
		
		String response = code;
		String  value = "null";
		
		//extrae el valor de la respuesta
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
		
		//ignora respuestas que no corresponden a la pregunta
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
		
		
		// cargalos datos del usuario
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
		//insert user response 
		if(user_id!=0) {
			String SQL_D = "delete from cvn_res_user where "
					+ "cvn_user_id = "+user_id
					+" AND cvn_question_id ="+actualQuestion.getCvn_question_id();
			DataBase.get().execute(SQL_D);
			
			String SQL_I = "insert into cvn_res_user(cvn_user_id,cvn_question_id,cvn_response_id) "
					+ "VALUES ("+user_id+","+actualQuestion.getCvn_question_id()+","+actualQuestion.getResponses().stream().filter(a -> a.getValue().equals(value_)).collect(Collectors.toList()).get(0).getID()+")";
			DataBase.get().execute(SQL_I);
			System.out.println(String.format("[add-response][date-time:%s][question_id:%s][user_id_%s][SQL:%s]", 
					new Date().toString(),
					actualQuestion.getCvn_question_id(),
					user_id,
					getComponentListeners()));
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
