package net.hovercolor;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Screen{
	
	private JFrame jframe; 
	private JPanel jpanel;
	private JLabel jlabel;
	
	public Screen(){
		setScreen();
		setDisplaySquare();
		setTextDisplay();
		update(new HoverColor(255, 255, 255));
		runIdentifier();
		
		displayScreen();
	}
	
	private void setScreen(){
		this.jframe = new JFrame();
		this.jframe.setLayout(new FlowLayout());
		this.jframe.setSize(200, 125);
		this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.jframe.setResizable(false);
	}
	
	private void setDisplaySquare(){
		this.jpanel = new JPanel(new FlowLayout());
		this.jpanel.setPreferredSize(new Dimension(85, 85));
		this.jframe.add(this.jpanel);
	}
	
	private void setTextDisplay(){
		this.jlabel = new JLabel();
		this.jlabel.setPreferredSize(new Dimension(100, 50));
		this.jlabel.setFont(new Font(this.jlabel.getFont().getName(), Font.PLAIN, 10));	
		this.jframe.add(this.jlabel);
	}
	
	private void displayScreen(){
		this.jframe.pack();
		this.jframe.setVisible(true);
	}
	
	private void update(HoverColor hoverColor){
		updateText(hoverColor);
		updateDisplayColor(hoverColor);
	}
	
	private void updateText(HoverColor hoverColor){
		int[] rgb = hoverColor.getRGB();
		String hex = hoverColor.getHex();
		int x = (int)MouseInfo.getPointerInfo().getLocation().getX();
		int y = (int)MouseInfo.getPointerInfo().getLocation().getY();
		String text = "<html>Pixel: " + x + ", " + y + "<br>" + "RGB: " + rgb[0] + ", " + rgb[1] + ", " + rgb[2] + "<br>Hex: " + hex + " </html>";
		this.jlabel.setText(text);
	}
	
	private void updateDisplayColor(HoverColor hoverColor){
		this.jpanel.setBackground(hoverColor.getColor());
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
}
