package jnetworker;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Node 
{
	public Image image = new ImageIcon(getClass().getClassLoader().getResource("poligon.png")).getImage();
	private int x;
	private int y;
	private int id;

	public Node() 
	{
		x=y=0;
	}
	public Node(int x, int y) 
	{
		this.x=x;
		this.y=y;
	}
	public void setX(int x) 
	{
		this.x=x;
	}
	public void setY(int y) 
	{
		this.y=y;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public int getX() 
	{
		return this.x;
	}
	public int getY() 
	{
		return this.y;
	}
	public int getId()
	{
		return this.id;
	}
	public boolean isEmpty() 
	{
		if(this.x==0 && this.y==0) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	public boolean equals(Node node)
	{
		return (this.x == node.x && this.y == node.y);
	}

	public void draw(Graphics g)
	{
		g.drawImage(image, x, y, null);	
	}
}

