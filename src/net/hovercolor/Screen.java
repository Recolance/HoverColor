package net.hovercolor;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Screen{
	
	private static Screen screen;
	
	private JFrame frame;
	private JPanel colorDisplayBox;
	private JLabel textInfoBox;
	private JPanel[] colorSaves;
	
	private HoverColor currentColor;
	
	public Screen(){
		screen = this;
		
		setFrame();
		setTopInfoSection();
		update(new HoverColor(255, 255, 255));
		setBottomSavedColorsSection();
		setKeyListener();
		
		displayScreen();
		
		runIdentifier();
	}
	
	private void setFrame(){
		this.frame = new JFrame();
		this.frame.setSize(new Dimension(300, 300));
		this.frame.setLayout(new BoxLayout(this.frame.getContentPane(), BoxLayout.Y_AXIS));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void displayScreen(){
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	private void setTopInfoSection(){
		JPanel topContainer = new JPanel(new FlowLayout());
		
		this.colorDisplayBox = new JPanel();
		this.colorDisplayBox.setPreferredSize(new Dimension(85, 85));
		topContainer.add(this.colorDisplayBox);
		
		this.textInfoBox = new JLabel();
		this.textInfoBox.setPreferredSize(new Dimension(100, 50));
		this.textInfoBox.setFont(new Font(this.textInfoBox.getFont().getName(), Font.PLAIN, 10));
		topContainer.add(this.textInfoBox);
		
		this.frame.add(topContainer);
	}
	
	private void setBottomSavedColorsSection(){
		JPanel bottomContainer = new JPanel(new FlowLayout());
		
		this.colorSaves = new JPanel[5];
		
		HoverColor color = new HoverColor(255, 255, 255);
		for(int i = 0; i < 5; i++){
			JPanel colorPanel = new JPanel();
			colorPanel.setPreferredSize(new Dimension(25, 25));
			colorPanel.setBackground(color.getColor());
			this.colorSaves[i] = colorPanel;
			bottomContainer.add(colorPanel);
		}
		
		this.frame.add(bottomContainer);
		
		JLabel inscructionLabel = new JLabel("Press 's' to save a color.");
		inscructionLabel.setFont(new Font(inscructionLabel.getFont().getName(), Font.PLAIN, 10));
		this.frame.add(inscructionLabel);
		
	}
	
	private void setKeyListener(){
		this.frame.addKeyListener(new SaveColorListener());
	}
	
	private void update(HoverColor hoverColor){
		this.colorDisplayBox.setBackground(hoverColor.getColor());
		
		Point point = MouseInfo.getPointerInfo().getLocation();
		int mouseX = (int)point.getX();
		int mouseY = (int)point.getY();
		int[] rgb = hoverColor.getRGB();
		String displayText = "<html>Pixel: " + mouseX + ", " + mouseY + "<br>RGB: " + rgb[0] + "," + rgb[1] + ", " + rgb[2] + "<br>Hex: " + hoverColor.getHex() + "</html>";
		
		this.textInfoBox.setText(displayText);
		this.currentColor = hoverColor;
	}
	
	public void addSavedColor(HoverColor hoverColor){
		JPanel[] colorSaves = this.colorSaves;
		
		for(int i = 4; i >= 0; i--){
			if(i - 1 == -1) break;
			Color colorToApply = colorSaves[i - 1].getBackground();
			colorSaves[i].setBackground(colorToApply);
		}
		
		colorSaves[0].setBackground(hoverColor.getColor());
	}
	
	private void runIdentifier(){
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			
			@Override
			public void run(){
				int x = (int)MouseInfo.getPointerInfo().getLocation().getX();
				int y = (int)MouseInfo.getPointerInfo().getLocation().getY();
				try{
					Robot robot = new Robot();
					update(new HoverColor(robot.getPixelColor(x, y)));
				}catch(AWTException e){
					e.printStackTrace();
				}
			}
		}, 0, 50);
	}
	
	public HoverColor getCurrentColor(){
		return this.currentColor;
	}
	
	public static Screen getScreen(){
		return screen;
	}
}
