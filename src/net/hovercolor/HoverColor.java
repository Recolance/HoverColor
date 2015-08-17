package net.hovercolor;

import java.awt.Color;

public class HoverColor{

	private int r;
	private int g;
	private int b;
	
	public HoverColor(int r, int g, int b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public HoverColor(Color color){
		this.r = color.getRed();
		this.g = color.getGreen();
		this.b = color.getBlue();
	}
	
	public int getR(){
		return this.r;
	}
	
	public int getG(){
		return this.g;
	}
	
	public int getB(){
		return this.b;
	}
	
	public int[] getRGB(){
		return new int[]{this.r, this.g, this.b};
	}
	
	public String getHex(){
		return String.format("#%02X%02X%02X", this.getR(), this.getG(), this.getB());
	}
	
	public Color getColor(){
		return new Color(getR(), getG(), getB());
	}
}
