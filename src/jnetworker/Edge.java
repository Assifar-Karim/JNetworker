package jnetworker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.lang.Math;

public class Edge
{
	public Color color = new Color(0x000000);
	private Node node1;
	private Node node2;
	private int weight;
	
	public Edge()
	{
		this.node1 = new Node();
		this.node2 = new Node();
		this.weight = 0;
	}
	
	public Edge(Node node1,Node node2)
	{
		this.node1.setX(node1.getX());
		this.node1.setY(node1.getY());
		this.node2.setX(node2.getX());
		this.node2.setY(node2.getY());
		this.weight = 1;
	}
	public Edge(Node node1,Node node2,int weight)
	{
		this.node1.setX(node1.getX());
		this.node1.setY(node1.getY());
		this.node2.setX(node2.getX());
		this.node2.setY(node2.getY());
		this.weight = weight;
	}
	public void setEdge(Node node1, Node node2)
	{
		this.node1.setX(node1.getX());
		this.node1.setY(node1.getY());
		this.node2.setX(node2.getX());
		this.node2.setY(node2.getY());
	}
	
	public void setNode1(int x,int y)
	{
		this.node1.setX(x);
		this.node1.setY(y);
	}
	public void setNode2(int x,int y)
	{
		this.node2.setX(x);
		this.node2.setY(y);
	}
	public void setWeight(int weight)
	{
		this.weight = weight;
	}
	public Node getNode1()
	{
		return this.node1;
	}
	public Node getNode2()
	{
		return this.node2;
	}
	public int getWeight()
	{
		return this.weight;
	}
	public boolean hasInitialEnd()
	{
		return (node1.getX()>0 && node1.getY()>0);
	}
	public boolean hasTerminalEnd()
	{
		return (node2.getX()>0 && node2.getY()>0);
	}
	public boolean hasNode (Node node)
	{
		return (this.node1.equals(node) || this.node2.equals(node));
	}
	public boolean onEdge(int x, int y)
	{
		int xO1 = node1.getX()+16;
		int yO1 = node1.getY()+16;
		int xO2 = node2.getX()+16;
		int yO2 = node2.getY()+16;
		int norm = node1.equals(node2)||(node1.isEmpty()&& node2.isEmpty())?1:(int)Math.sqrt(Math.pow(xO2-xO1, 2)+Math.pow(yO2-yO1, 2)); 
		float a = (float)(yO2-yO1)/(float)(xO2-xO1);
		float b = yO1-a*xO1;
		int dist = (int)Math.sqrt(Math.pow(x-xO1, 2)+Math.pow(y-yO1, 2));
		return(dist<=norm && y-a*x-b>=-5 && y-a*x-b<=5);
	
	}
	public void draw(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.setPaint(color);
		g2d.setStroke(new BasicStroke(2));
		int xO1 = node1.getX()+16;
		int yO1 = node1.getY()+16;
		int xO2 = node2.getX()+16;
		int yO2 = node2.getY()+16;
		int norm = node1.equals(node2)||(node1.isEmpty()&& node2.isEmpty())?1:(int)Math.sqrt(Math.pow(xO2-xO1, 2)+Math.pow(yO2-yO1, 2)); 
		int dx = 16*(xO2-xO1)/norm;
		int dy = 16*(yO2-yO1)/norm;
		g2d.drawLine(node1.getX()+16+dx, node1.getY()+16+dy, node2.getX()+16-dx, node2.getY()+16-dy);
		g2d.setPaint(new Color(0xEEEEEE));
		g2d.drawString(String.valueOf(this.weight), (xO1+xO2)/2, (yO1+yO2)/2+24);
	}
}
