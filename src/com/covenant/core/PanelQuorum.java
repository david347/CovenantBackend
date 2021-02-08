package com.covenant.core;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.covenant.Pojo.User;
import com.covenant.Utils.Utils;

import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.Color;

public class PanelQuorum {


	private JFrame frmCovenant;
	private JTextField textField;
	JLabel lblNewLabel;
	private Panel panel;
	private PanelNew menu;
	private PanelAssist PAssist;
	private PanelHome home;
	private JPanel panel_1;
	private PanelPoll poll;
	private State state;
	public static HashMap<String,Float> Data =new HashMap<String,Float>();
	public String projectName;
	private JButton btnVotaciones;
	private List<User> users;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelQuorum window = new PanelQuorum();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	

	/**
	 * Create the application.
	 */
	public PanelQuorum() {
		state = new StateQuorum(this);
		users=new ArrayList<User>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCovenant = new JFrame();
		
		frmCovenant.setTitle("Covenant");
		frmCovenant.setBounds(100, 100, 450, 300);
		frmCovenant.getContentPane().setLayout(new BorderLayout());
		frmCovenant.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new Panel();
		panel.setBackground(UIManager.getColor("Button.background"));
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(KeyEvent.VK_ENTER == e.getKeyCode()) {
					System.out.println("Enter");
					drawBar(10, 50, 0.5f);
					String code=textField.getText();
					String opt = code.substring(code.length()-1, code.length());
					String ref = code.substring(0, code.length()-1);
					state.handle(code);
					lblNewLabel.setText(ref);
					textField.setText("");
				}
			}
		});
		textField.setColumns(10);
		
		JLabel lblUltimaVotacion = new JLabel(">>");
		
		lblNewLabel = new JLabel("--");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel(home);
			}
		});
		
		poll = new PanelPoll(this);
		menu =  new PanelNew(this);
		home =  new PanelHome(this);
		PAssist = new PanelAssist(this);
		frmCovenant.getContentPane().add(home, BorderLayout.CENTER);
		
		panel_1 = new JPanel();
		panel_1.setToolTipText("");
		panel_1.setBackground(UIManager.getColor("Button.disabledShadow"));
		
		btnVotaciones = new JButton("Votar");
		btnVotaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel(poll);
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblUltimaVotacion, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
								.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnVotaciones, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))))
					.addGap(35))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUltimaVotacion)
						.addComponent(lblNewLabel))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(16))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
							.addComponent(btnVotaciones)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton)
							.addContainerGap())))
		);
		panel_1.setLayout(null);
		panel.setLayout(gl_panel);
		panel.setVisible(true);
		frmCovenant.setVisible(true);
		
	}
	
	private void changePanel(Panel panel) {
		frmCovenant.getContentPane().removeAll();
		frmCovenant.getContentPane().add(panel,BorderLayout.CENTER);
		frmCovenant.setVisible(true);
	}



	public void Back() {
		changePanel(home);
		
	}
	
	public void Pow() {
		changePanel(panel);
		
	}

	public void assist() {
		changePanel(PAssist);
	}

	public void New() {
		changePanel(menu);
		
	}
	
	
	public void drawBar(float x, float y, float size) {
		panel_1.getGraphics().drawRect((int)(x*panel_1.getWidth()), (int)(y*panel_1.getWidth()), (int)(0.1f*panel_1.getWidth()), (int)(size*panel_1.getHeight()));
	}



	public void quorum(float total) {
		
		Color color = panel_1.getGraphics().getColor();
		//panel_1.getGraphics().fillRect(0, 0, panel_1.getWidth(), panel_1.getHeight());
		Graphics g =panel_1.getGraphics(); 
		g.setColor(Color.WHITE);
		g.fillRect(0, 0,  panel_1.getWidth(), panel_1.getHeight());
		float w=panel_1.getWidth();
		float h=panel_1.getHeight();
		
		if(0.5<=total) {
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.RED);
		}
		//progress bar 
		float size=total/100f;
		float pixel =size*(h-h/10f);
		
		BufferedImage image;
		try {
			image = ImageIO.read(new File("./Resources/Images/greenBar.png"));
			g.drawImage(image, 
					(int)(w/2f), 
					(int)(h/20f+(1f-size)*(h-h/10f)), 
					(int)(0.2f*w), 
					(int)(pixel),null);
		} catch (IOException e) {
			g.fillRect(
					(int)(w/2f), 
					(int)(h/20f+(1f-size)*(h-h/10f)), 
					(int)(0.2f*w), 
					(int)(pixel));
			e.printStackTrace();
		}
		
		
		g.setColor(new Color(100,100,100));
		//text 1-100%
		g.setFont(new Font("Arial", Font.BOLD, (int)(w*0.05f)));
		g.drawString(Utils.getRounded(total) + "%", (int)(w/4f), (int)(h/2));
		g.drawString("Quorum:", (int)(w/4f), (int)(h/3));
		
		//border
		g.drawRect(
				(int)(w/2f), 
				(int)(h/20f), 
				(int)(0.2f*w), 
				(int)(h-h/10f));
		g.setColor(color);
	}

	public void setData(String ref, float cff) {
		Data.put(ref, cff);
		
	}

	public boolean containsUserByRef(String ref) {
		try {
			return users.stream().filter(u -> u.getRef().equals(ref)).collect(Collectors.toList()).size()>0;
		}catch (Exception e) {
			return false;
		}
	}

	public void addUser(User user) {
		if(user!=null)
			users.add(user);
		
	}
	
	
	
	
}
