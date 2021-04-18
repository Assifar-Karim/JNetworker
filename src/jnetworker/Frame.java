package jnetworker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Frame extends JFrame implements ActionListener
{
	private Panel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JMenuBar menuBar;
	
	private JButton nodeButton;
	private JButton edgeButton;
	private JButton nodeMvtButton;
	private JButton nodeRemButton;
	private JButton weightChangeButton;
	private JMenuItem newGraph;
	private JMenuItem saveAsImg;
	private JMenuItem quit;
	private JMenuItem editWeight;
	private JMenuItem help;
	private JMenuItem about;
	private JMenuItem dfs;
	private JMenuItem bfs;
	private JMenuItem topSort;
	private JMenuItem kahnAlgo;
	private JMenuItem dijAlgo;
	
	public Frame()
	{
		ImageIcon appIcon = new ImageIcon(getClass().getClassLoader().getResource("Logo.png"));
		ImageIcon nodeIcon = new ImageIcon(getClass().getClassLoader().getResource("add-button.png"));
		ImageIcon edgeIcon = new ImageIcon(getClass().getClassLoader().getResource("vector.png"));
		ImageIcon nodeMvtIcon = new ImageIcon(getClass().getClassLoader().getResource("modeling.png"));
		ImageIcon nodeRemIcon = new ImageIcon(getClass().getClassLoader().getResource("minus.png"));
		ImageIcon weightChangeIcon = new ImageIcon(getClass().getClassLoader().getResource("weight-tool-with-handle.png"));
		
		panel1 = new Panel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu algoMenu = new JMenu("Algorithms");
		JMenu helpMenu = new JMenu("Help");
		newGraph = new JMenuItem("New Graph");
		saveAsImg = new JMenuItem("Save as an Image");
		quit = new JMenuItem("Quit");
		editWeight = new JMenuItem("Edit weight value");
		help = new JMenuItem("Help");
		about = new JMenuItem("About JNetworker");
		dfs = new JMenuItem("DFS");
		bfs = new JMenuItem("BFS");
		topSort = new JMenuItem("Topological Sort");
		kahnAlgo = new JMenuItem("Kahn's Algorithm");
		dijAlgo = new JMenuItem("Dijkstra's Algorithm");
		nodeButton = new JButton();
		edgeButton = new JButton();
		nodeMvtButton = new JButton();
		nodeRemButton = new JButton();
		weightChangeButton = new JButton();
		
		panel1.label.setForeground(new Color(0xEEEEEE));
		
		panel2.setPreferredSize(new Dimension(40,100));
		panel2.setBackground(new Color(0x222831));
		panel2.add(nodeButton);
		panel2.add(edgeButton);
		panel2.add(nodeMvtButton);
		panel2.add(nodeRemButton);
		panel2.add(weightChangeButton);
		
		panel3.setPreferredSize(new Dimension(40,30));
		panel3.setBackground(new Color(0x222831));
		panel3.add(panel1.label);
		
		menuBar.setOpaque(true);
		menuBar.setBackground(new Color(0x222831));
		fileMenu.setForeground(new Color(0xEEEEEE));
		editMenu.setForeground(new Color(0xEEEEEE));
		algoMenu.setForeground(new Color(0xEEEEEE));
		helpMenu.setForeground(new Color(0xEEEEEE));
		
		newGraph.addActionListener(this);
		saveAsImg.addActionListener(this);
		quit.addActionListener(this);
		editWeight.addActionListener(this);
		help.addActionListener(this);
		about.addActionListener(this);
		dfs.addActionListener(this);
		bfs.addActionListener(this);
		topSort.addActionListener(this);
		kahnAlgo.addActionListener(this);
		dijAlgo.addActionListener(this);
		fileMenu.add(newGraph);
		fileMenu.add(saveAsImg);
		fileMenu.add(quit);
		editMenu.add(editWeight);
		helpMenu.add(help);
		helpMenu.add(about);
		algoMenu.add(dfs);
		algoMenu.add(bfs);
		algoMenu.add(topSort);
		algoMenu.add(kahnAlgo);
		algoMenu.add(dijAlgo);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(algoMenu);
		menuBar.add(helpMenu);
		
		
		nodeButton.setBackground(new Color(0x222831));
		nodeButton.setFocusPainted(false);
		nodeButton.setBorderPainted(false);
		nodeButton.setIcon(nodeIcon);
		nodeButton.addActionListener(this);
		edgeButton.setBackground(new Color(0x222831));
		edgeButton.setFocusPainted(false);
		edgeButton.setBorderPainted(false);
		edgeButton.setIcon(edgeIcon);
		edgeButton.addActionListener(this);
		nodeMvtButton.setBackground(new Color(0x222831));
		nodeMvtButton.setFocusPainted(false);
		nodeMvtButton.setBorderPainted(false);
		nodeMvtButton.setIcon(nodeMvtIcon);
		nodeMvtButton.addActionListener(this);
		nodeRemButton.setBackground(new Color(0x222831));
		nodeRemButton.setFocusPainted(false);
		nodeRemButton.setBorderPainted(false);
		nodeRemButton.setIcon(nodeRemIcon);
		nodeRemButton.addActionListener(this);
		weightChangeButton.setBackground(new Color(0x222831));
		weightChangeButton.setFocusPainted(false);
		weightChangeButton.setBorderPainted(false);
		weightChangeButton.setIcon(weightChangeIcon);
		weightChangeButton.addActionListener(this);
		
		this.setTitle("JNetworker");
		this.setSize(1280,720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(-1,0));
		this.setJMenuBar(menuBar);
		
		this.add(panel2,BorderLayout.WEST);
		this.add(panel1,BorderLayout.CENTER);
		this.add(panel3,BorderLayout.SOUTH);
		this.setVisible(true);
		this.setIconImage(appIcon.getImage());
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==nodeButton)
		{
			nodeButton.setEnabled(false);
			edgeButton.setEnabled(true);
			nodeMvtButton.setEnabled(true);
			nodeRemButton.setEnabled(true);
			weightChangeButton.setEnabled(true);
			panel1.addMouseListener(panel1.nodeListener);
			panel1.removeMouseListener(panel1.edgeListener);
			panel1.removeMouseListener(panel1.nodeMvtListener);
			panel1.removeMouseMotionListener(panel1.dragListener);
			panel1.removeMouseListener(panel1.nodeRemListener);
			panel1.removeMouseListener(panel1.weightListener);
			panel1.removeMouseListener(panel1.dfsListener);
			panel1.removeMouseListener(panel1.bfsListener);
			panel1.removeMouseListener(panel1.dijListener);
			panel1.ColorRefresh();
		}
		else if(e.getSource()==edgeButton)
		{
			nodeButton.setEnabled(true);
			edgeButton.setEnabled(false);
			nodeMvtButton.setEnabled(true);
			nodeRemButton.setEnabled(true);
			weightChangeButton.setEnabled(true);
			panel1.addMouseListener(panel1.edgeListener);
			panel1.removeMouseListener(panel1.nodeListener);
			panel1.removeMouseListener(panel1.nodeMvtListener);
			panel1.removeMouseMotionListener(panel1.dragListener);
			panel1.removeMouseListener(panel1.nodeRemListener);
			panel1.removeMouseListener(panel1.weightListener);
			panel1.removeMouseListener(panel1.dfsListener);
			panel1.removeMouseListener(panel1.bfsListener);
			panel1.removeMouseListener(panel1.dijListener);
			panel1.ColorRefresh();
		}
		else if(e.getSource()==nodeMvtButton)
		{
			nodeButton.setEnabled(true);
			edgeButton.setEnabled(true);
			nodeMvtButton.setEnabled(false);
			nodeRemButton.setEnabled(true);
			weightChangeButton.setEnabled(true);
			panel1.removeMouseListener(panel1.nodeListener);
			panel1.removeMouseListener(panel1.edgeListener);
			panel1.removeMouseListener(panel1.nodeRemListener);
			panel1.removeMouseListener(panel1.weightListener);
			panel1.addMouseListener(panel1.nodeMvtListener);
			panel1.addMouseMotionListener(panel1.dragListener);
			panel1.removeMouseListener(panel1.dfsListener);
			panel1.removeMouseListener(panel1.bfsListener);
			panel1.removeMouseListener(panel1.dijListener);
			panel1.ColorRefresh();
		}
		else if(e.getSource()==nodeRemButton)
		{
			nodeButton.setEnabled(true);
			edgeButton.setEnabled(true);
			nodeMvtButton.setEnabled(true);
			nodeRemButton.setEnabled(false);
			weightChangeButton.setEnabled(true);
			panel1.addMouseListener(panel1.nodeRemListener);
			panel1.removeMouseListener(panel1.nodeListener);
			panel1.removeMouseListener(panel1.edgeListener);
			panel1.removeMouseListener(panel1.nodeMvtListener);
			panel1.removeMouseMotionListener(panel1.dragListener);
			panel1.removeMouseListener(panel1.weightListener);
			panel1.removeMouseListener(panel1.dfsListener);
			panel1.removeMouseListener(panel1.bfsListener);
			panel1.removeMouseListener(panel1.dijListener);
			panel1.ColorRefresh();
		}
		else if(e.getSource()==weightChangeButton)
		{
			nodeButton.setEnabled(true);
			edgeButton.setEnabled(true);
			nodeMvtButton.setEnabled(true);
			nodeRemButton.setEnabled(true);
			weightChangeButton.setEnabled(false);
			panel1.addMouseListener(panel1.weightListener);
			panel1.removeMouseListener(panel1.nodeListener);
			panel1.removeMouseListener(panel1.edgeListener);
			panel1.removeMouseListener(panel1.nodeRemListener);
			panel1.removeMouseListener(panel1.nodeMvtListener);
			panel1.removeMouseMotionListener(panel1.dragListener);
			panel1.removeMouseListener(panel1.dfsListener);
			panel1.removeMouseListener(panel1.bfsListener);
			panel1.removeMouseListener(panel1.dijListener);
			panel1.ColorRefresh();
		}
		else if(e.getSource()==newGraph)
		{
			nodeButton.setEnabled(true);
			edgeButton.setEnabled(true);
			nodeMvtButton.setEnabled(true);
			nodeRemButton.setEnabled(true);
			weightChangeButton.setEnabled(true);
			panel1.removeMouseListener(panel1.nodeListener);
			panel1.removeMouseListener(panel1.edgeListener);
			panel1.removeMouseListener(panel1.nodeRemListener);
			panel1.removeMouseListener(panel1.nodeMvtListener);
			panel1.removeMouseMotionListener(panel1.dragListener);
			panel1.removeMouseListener(panel1.weightListener);
			panel1.removeMouseListener(panel1.dfsListener);
			panel1.removeMouseListener(panel1.bfsListener);
			panel1.removeMouseListener(panel1.dijListener);
			panel1.ColorRefresh();
			panel1.NewGraph();
		}
		else if(e.getSource()==quit)
		{
			System.exit(0);
		}
		else if(e.getSource()==saveAsImg)
		{
			try 
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} 
			catch (Exception e1) 
			{
			}
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Portable Network Graphics (.png)","png"));
			int resp = fileChooser.showSaveDialog(null);
			if(resp == JFileChooser.APPROVE_OPTION)
			{	
				BufferedImage cap = new BufferedImage(panel1.getWidth(),panel1.getHeight(),BufferedImage.TYPE_INT_RGB);
				panel1.paint(cap.getGraphics());
				try 
				{
					ImageIO.write(cap, "png", new File(fileChooser.getCurrentDirectory()+File.separator+fileChooser.getName(fileChooser.getSelectedFile())+".png"));
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
			try 
			{
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} 
			catch (Exception e1) 
			{
			}
		}
		else if(e.getSource()==editWeight)
		{
			String weightStr = JOptionPane.showInputDialog("Weight Value");
			weightStr = weightStr==null||weightStr.equals("")?String.valueOf(panel1.newWeight):weightStr;
			panel1.newWeight = Integer.parseInt(weightStr);
		}
		else if(e.getSource()==dfs)
		{
			panel1.ColorRefresh();
			nodeButton.setEnabled(true);
			edgeButton.setEnabled(true);
			nodeMvtButton.setEnabled(true);
			nodeRemButton.setEnabled(true);
			weightChangeButton.setEnabled(true);
			panel1.label.setText("Order of visited nodes: ");
			panel1.removeMouseListener(panel1.nodeListener);
			panel1.removeMouseListener(panel1.edgeListener);
			panel1.removeMouseListener(panel1.nodeRemListener);
			panel1.removeMouseListener(panel1.nodeMvtListener);
			panel1.removeMouseMotionListener(panel1.dragListener);
			panel1.removeMouseListener(panel1.weightListener);
			panel1.removeMouseListener(panel1.bfsListener);
			panel1.removeMouseListener(panel1.dijListener);
			panel1.addMouseListener(panel1.dfsListener);
		}
		else if(e.getSource()==bfs)
		{
			panel1.ColorRefresh();
			nodeButton.setEnabled(true);
			edgeButton.setEnabled(true);
			nodeMvtButton.setEnabled(true);
			nodeRemButton.setEnabled(true);
			weightChangeButton.setEnabled(true);
			panel1.label.setText("Order of visited nodes: ");
			panel1.removeMouseListener(panel1.nodeListener);
			panel1.removeMouseListener(panel1.edgeListener);
			panel1.removeMouseListener(panel1.nodeRemListener);
			panel1.removeMouseListener(panel1.nodeMvtListener);
			panel1.removeMouseMotionListener(panel1.dragListener);
			panel1.removeMouseListener(panel1.weightListener);
			panel1.removeMouseListener(panel1.dfsListener);
			panel1.removeMouseListener(panel1.dijListener);
			panel1.addMouseListener(panel1.bfsListener);
		}
		else if(e.getSource()==topSort)
		{
			panel1.ColorRefresh();
			nodeButton.setEnabled(true);
			edgeButton.setEnabled(true);
			nodeMvtButton.setEnabled(true);
			nodeRemButton.setEnabled(true);
			weightChangeButton.setEnabled(true);
			panel1.removeMouseListener(panel1.nodeListener);
			panel1.removeMouseListener(panel1.edgeListener);
			panel1.removeMouseListener(panel1.nodeRemListener);
			panel1.removeMouseListener(panel1.nodeMvtListener);
			panel1.removeMouseMotionListener(panel1.dragListener);
			panel1.removeMouseListener(panel1.weightListener);
			panel1.removeMouseListener(panel1.dfsListener);
			panel1.removeMouseListener(panel1.bfsListener);
			panel1.removeMouseListener(panel1.dijListener);
			panel1.adjListRefresh();
			panel1.label.setText("Topological order of the nodes: "+Arrays.toString(panel1.TopologicalSort()));
		}
		else if(e.getSource()==kahnAlgo)
		{
			panel1.ColorRefresh();
			nodeButton.setEnabled(true);
			edgeButton.setEnabled(true);
			nodeMvtButton.setEnabled(true);
			nodeRemButton.setEnabled(true);
			weightChangeButton.setEnabled(true);
			panel1.removeMouseListener(panel1.nodeListener);
			panel1.removeMouseListener(panel1.edgeListener);
			panel1.removeMouseListener(panel1.nodeRemListener);
			panel1.removeMouseListener(panel1.nodeMvtListener);
			panel1.removeMouseMotionListener(panel1.dragListener);
			panel1.removeMouseListener(panel1.weightListener);
			panel1.removeMouseListener(panel1.dfsListener);
			panel1.removeMouseListener(panel1.bfsListener);
			panel1.removeMouseListener(panel1.dijListener);
			panel1.adjListRefresh();
			panel1.label.setText("Topological order of the nodes: "+Arrays.toString(panel1.KahnAlgorithm()));
		}
		else if(e.getSource()==dijAlgo)
		{
			panel1.ColorRefresh();
			if(!Arrays.asList(panel1.getMouseListeners()).contains(panel1.dijListener))
			{	
				nodeButton.setEnabled(true);
				edgeButton.setEnabled(true);
				nodeMvtButton.setEnabled(true);
				nodeRemButton.setEnabled(true);
				weightChangeButton.setEnabled(true);
				panel1.removeMouseListener(panel1.nodeListener);
				panel1.removeMouseListener(panel1.edgeListener);
				panel1.removeMouseListener(panel1.nodeRemListener);
				panel1.removeMouseListener(panel1.nodeMvtListener);
				panel1.removeMouseMotionListener(panel1.dragListener);
				panel1.removeMouseListener(panel1.weightListener);
				panel1.removeMouseListener(panel1.dfsListener);
				panel1.removeMouseListener(panel1.bfsListener);
				panel1.addMouseListener(panel1.dijListener);
			}
		}
		else if(e.getSource()==help)
		{
			JOptionPane.showMessageDialog(null, "Help Section:\n"
					+ "1--Node Tool: Adds new nodes to the graph by clicking on the graph panel\n"
					+ "2--Edge Tool: Adds a directed edge between 2 nodes by selecting them \n"
					+ "3--Node Movement Tool: Moves the selected node by dragging in it and dropping it\n"
					+ "4--Node Removal Tool: Removes the selected node from the graph and rearranges the remaining nodes' order\n"
					+ "5--Weight Tool: Changes the initial 1 weight of the selected node to the new defined weight value in the edit weight menu\n"
					+ "6--Algorithm Menu: Contains different algorithms and shows when needed results in the status panel below "
					,"Help",JOptionPane.INFORMATION_MESSAGE);
		}
		else if(e.getSource()==about)
		{
			JOptionPane.showMessageDialog(null, "JNetworker v1.0\nAuthor: Assifar Karim", "Build Time: April 03 2021", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
}
