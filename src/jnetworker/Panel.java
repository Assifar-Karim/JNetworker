package jnetworker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class Panel extends JPanel 
{
	final int infinity = (int)(1.0/0.0);
	
	public NodeListener nodeListener = new NodeListener();
	public EdgeListener edgeListener = new EdgeListener();
	public NodeMvtListener nodeMvtListener = new NodeMvtListener();
	public DragListener dragListener = new DragListener();
	public NodeRemListener nodeRemListener = new NodeRemListener();
	public WeightListener weightListener = new WeightListener();
	public DFSListener dfsListener = new DFSListener();
	public BFSListener bfsListener = new BFSListener();
	public DIJListener dijListener = new DIJListener();
	public ArrayList<Edge>[] adjacencyList;
	public int newWeight = 1;
	public JLabel label = new JLabel();

	private Edge edge = new Edge();
	private Point previousNodeCoord = new Point();
	private int nodeIndex;
	private Node tempNodeImm;
	private ArrayList<Node> nodeList = new ArrayList<Node>();
	private ArrayList<Edge> edgeList = new ArrayList<Edge>();
	
	public Panel()
	{
		this.setBackground(new Color(0x393E46));
		this.setVisible(true);
	}
	public void paint(Graphics g) 
	{
		 super.paint(g);
		 Graphics2D g2d = (Graphics2D)g;
		 if(nodeList.size()>0) 
		 {
			for(int i=0;i<nodeList.size();i++) 
			{
				nodeList.get(i).draw(g);
				nodeList.get(i).setId(i);
				g2d.setPaint(new Color(0xEEEEEE));
				g2d.drawString(String.valueOf(i),nodeList.get(i).getX()+13,nodeList.get(i).getY()+20);
			}
		}
		 if(edgeList.size()>0)
		 {
			 for(int i=0;i<edgeList.size();i++)
			 {
				 edgeList.get(i).draw(g);
			 }
		 }
	}
	public void NewGraph()
	{
		edgeList.clear();
		nodeList.clear();
		repaint();
	}
	public void ColorRefresh()
	{
		for(int i=0;i<nodeList.size();i++)
		{
			nodeList.get(i).image = new ImageIcon(getClass().getClassLoader().getResource("poligon.png")).getImage();
		}
		for(int i=0;i<edgeList.size();i++)
		{
			edgeList.get(i).color = new Color(0x000000);
		}
		label.setText("");
		repaint();
	}
	// This method creates/Refreshes the adjacency list that will be used for the algorithms
	public void adjListRefresh()
	{
		int n = nodeList.size();
		this.adjacencyList = new ArrayList[n];
		for(int i=0;i<n;i++)
		{
			adjacencyList[i] = new ArrayList<Edge>();
			for(int j=0;j<edgeList.size();j++)
			{
				if(edgeList.get(j).getNode1().equals(nodeList.get(i)))
				{
					adjacencyList[i].add(edgeList.get(j));
				}
			}
		}
	}
	//Graph Manipulation Algorithms
	public void DFS(int nodeId, boolean[] visited)
	{
		if(visited[nodeId]==true)
		{
			return;
		}
		visited[nodeId] = true;
		nodeList.get(nodeId).image = new ImageIcon(getClass().getClassLoader().getResource("Node.png")).getImage();
		label.setText(label.getText()+" "+String.valueOf(nodeId));
		ArrayList<Edge> neighbours = adjacencyList[nodeId];
		for(Edge next:neighbours)
		{
			next.color = new Color(0xEEEEEE);
			repaint();
			DFS(next.getNode2().getId(),visited);
		}
	}
	public void BFS(int nodeId)
	{
		Queue<Node> priorityQueue = new LinkedList<>();
		priorityQueue.offer(nodeList.get(nodeId));
		boolean[] visited = new boolean[nodeList.size()];
		visited[nodeId] = true;
		nodeList.get(nodeId).image = new ImageIcon(getClass().getClassLoader().getResource("Node.png")).getImage();
		label.setText(label.getText()+" "+String.valueOf(nodeId));
		while(!priorityQueue.isEmpty())
		{
			Node node = priorityQueue.poll();
			ArrayList<Edge> neighbours = adjacencyList[node.getId()];
			for(Edge next:neighbours)
			{
				if(!visited[next.getNode2().getId()])
				{
					int nextId = next.getNode2().getId();
					priorityQueue.offer(next.getNode2());
					visited[nextId] = true;
					next.color = new Color(0xEEEEEE);
					nodeList.get(nextId).image = new ImageIcon(getClass().getClassLoader().getResource("Node.png")).getImage();
					label.setText(label.getText()+" "+String.valueOf(nextId));
				}
			}
			repaint();
		}
	}
	private int HelperDfs(int i, int nodeId, boolean[] visited, int[] ordering)
	{
		visited[nodeId] = true;
		ArrayList<Edge> neighbours = adjacencyList[nodeId];
		for(Edge edge : neighbours)
		{
			if(!visited[edge.getNode2().getId()])
			{
				i = HelperDfs(i,edge.getNode2().getId(),visited,ordering);
			}
		}
		ordering[i] = nodeId;
		return i-1;
	}
	public int[] TopologicalSort()
	{
		int n = adjacencyList.length;
		boolean[] visited = new boolean[n];
		int[] ordering = new int[n];
		int i = n-1;
		for(int at=0;at<n;at++)
		{
			if(!visited[at])
			{
				i = HelperDfs(i,at,visited,ordering);
			}
		}
		return ordering;
	}
	public int[] KahnAlgorithm()
	{
		int n = adjacencyList.length;
		int[] inDeg = new int[n];
		for(int i=0;i<n;i++)
		{
			for(Edge edge:adjacencyList[i])
			{
				inDeg[edge.getNode2().getId()]++;
			}
		}
		Queue<Node> priorityQueue = new LinkedList<>();
		for(int i=0;i<n;i++)
		{
			if(inDeg[i]==0)
			{
				priorityQueue.offer(nodeList.get(i));
			}
		}
		int index = 0;
		int[] ordering = new int[n];
		while(!priorityQueue.isEmpty())
		{
			Node node = priorityQueue.poll();
			ordering[index++] = node.getId();
			for(Edge edge:adjacencyList[node.getId()])
			{
				int id = edge.getNode2().getId();
				inDeg[id]--;
				if(inDeg[id]==0)
				{
					priorityQueue.offer(nodeList.get(id));
				}
			}
		}
		if(index!=n)
		{
			return null;
		}
		return ordering;
	}
	private AbstractMap.SimpleEntry<int[], Node[]> Dijkstra(int startingNodeId)
	{
		int n = adjacencyList.length;
		boolean[] visited = new boolean[n];
		int[] distances = new int[n];
		Node[] previousNode = new Node[n];
		Arrays.fill(distances, 2147483647);
		distances[startingNodeId] = 0;
		PriorityQueue<AbstractMap.SimpleEntry<Node, Integer>> priorityQueue = new PriorityQueue<>((x,y) ->Integer.compare(x.getValue(),y.getValue()));
		priorityQueue.offer(new AbstractMap.SimpleEntry<Node, Integer>(nodeList.get(startingNodeId), 0));
		while(!priorityQueue.isEmpty())
		{
			AbstractMap.SimpleEntry<Node, Integer> curr = priorityQueue.poll();
			int id = curr.getKey().getId();
			visited[id] = true;
			if(distances[id]<curr.getValue())
			{
				continue;
			}
			for(Edge edge:adjacencyList[id])
			{
				if(!visited[edge.getNode2().getId()])
				{	
					int distance = distances[id] + edge.getWeight();
					if(distance<distances[edge.getNode2().getId()])
					{
						previousNode[edge.getNode2().getId()] = nodeList.get(id);
						distances[edge.getNode2().getId()] = distance;
						priorityQueue.offer(new AbstractMap.SimpleEntry<Node, Integer>(edge.getNode2(), distance));
					}
				}
			}
		}
		return new AbstractMap.SimpleEntry<int[], Node[]>(distances,previousNode);
	}
	public ArrayList<Integer> ShortestPath(int startingNodeId,int endNodeId)
	{
		AbstractMap.SimpleEntry<int[], Node[]> initialDij = Dijkstra(startingNodeId);
		ArrayList<Integer> path = new ArrayList<Integer>(); 
		for(int currentId=endNodeId;currentId!=startingNodeId && initialDij.getValue()[currentId]!=null;currentId=initialDij.getValue()[currentId].getId())
		{
			System.out.println(currentId);
			nodeList.get(currentId).image = new ImageIcon(getClass().getClassLoader().getResource("Node.png")).getImage();
			path.add(currentId);
		}
		if(path.size()!=0)
		{
			nodeList.get(startingNodeId).image = new ImageIcon(getClass().getClassLoader().getResource("Node.png")).getImage();
			path.add(startingNodeId);
			Collections.reverse(path);
			for(int i=0;i<path.size()-1;i++)
			{
				for(Edge edge:adjacencyList[path.get(i)])
				{
					if(edge.hasNode(nodeList.get(path.get(i+1))))
					{
						edge.color = new Color(0xEEEEEE);
					}
				}
			}
			repaint();
		}
		return path;
	}
	private class NodeListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			Node node = new Node();
			int x = e.getX()-16;	
			int y = e.getY()-16;
			node.setX(x);
			node.setY(y);
			nodeList.add(node);
			repaint();
		}
	}
	private class EdgeListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX()-16;
			int y = e.getY()-16;
			for(int i=0;i<nodeList.size();i++)
			{
				int x1 = nodeList.get(i).getX();
				int y1 = nodeList.get(i).getY();
				int id = nodeList.get(i).getId();
				if(x<=x1+24 && x>=x1-24 && y<=y1+24 && y>=y1-24)
				{
					//System.out.println("x = "+x1+" y ="+y1+" id = "+nodeList.get(i).getId());
					nodeList.get(i).image = new ImageIcon(getClass().getClassLoader().getResource("Node.png")).getImage();
					repaint();
					if(edge.hasInitialEnd())
					{
						edge.setNode2(x1, y1);
						edge.setWeight(1);
						edge.getNode2().setId(id);
						break;
					}
					else
					{
						edge.setNode1(x1, y1);
						edge.setWeight(1);
						edge.getNode1().setId(id);
						break;
					}
				}
			}
			if(edge.hasInitialEnd() && edge.hasTerminalEnd())
			{
				edgeList.add(edge);
				edge = new Edge();
				for(int i=0;i<nodeList.size();i++)
				{
					nodeList.get(i).image = new ImageIcon(getClass().getClassLoader().getResource("poligon.png")).getImage();
				}
				repaint();
			}
		}
	}
	private class NodeMvtListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			int x = e.getX()-16;
			int y = e.getY()-16;
			for(nodeIndex=0;nodeIndex<nodeList.size();nodeIndex++)
			{
				int x1 = nodeList.get(nodeIndex).getX();
				int y1 = nodeList.get(nodeIndex).getY();
				if(x<=x1+24 && x>=x1-24 && y<=y1+24 && y>=y1-24)
				{
					nodeList.get(nodeIndex).image = new ImageIcon(getClass().getClassLoader().getResource("Node.png")).getImage();
					previousNodeCoord.x = x1;
					previousNodeCoord.y = y1;
					repaint();
					break;
				}
			}
			if(nodeIndex<nodeList.size())
			{
				tempNodeImm = new Node(nodeList.get(nodeIndex).getX(),nodeList.get(nodeIndex).getY());
			}
		}
		public void mouseReleased(MouseEvent e)
		{
			if(nodeIndex<nodeList.size())
			{
				Node tempNode = nodeList.get(nodeIndex);
				nodeList.get(nodeIndex).image = new ImageIcon(getClass().getClassLoader().getResource("poligon.png")).getImage();
				for(int i=0;i<edgeList.size();i++)
				{
					if(edgeList.get(i).getNode1().equals(tempNodeImm))
					{
						edgeList.get(i).setNode1(tempNode.getX(), tempNode.getY());
					}
					else if(edgeList.get(i).getNode2().equals(tempNodeImm))
					{
						edgeList.get(i).setNode2(tempNode.getX(), tempNode.getY());
					}
				}
				previousNodeCoord.x = 0;
				previousNodeCoord.y = 0;
				repaint();
			}
		}
	}
	private class DragListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e)
		{
			if(nodeIndex<nodeList.size())
			{
				Node tempNode = nodeList.get(nodeIndex);
				tempNode.setX((int) (tempNode.getX()+e.getX()-previousNodeCoord.getX()));
				tempNode.setY((int) (tempNode.getY()+e.getY()-previousNodeCoord.getY()));
				previousNodeCoord.x=tempNode.getX();
				previousNodeCoord.y=tempNode.getY();
				repaint();
			}
		}
	}
	private class NodeRemListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			int x = e.getX()-16;
			int y = e.getY()-16;
			for(nodeIndex=0;nodeIndex<nodeList.size();nodeIndex++)
			{
				int x1 = nodeList.get(nodeIndex).getX();
				int y1 = nodeList.get(nodeIndex).getY();
				if(x<=x1+24 && x>=x1-24 && y<=y1+24 && y>=y1-24)
				{
					nodeList.get(nodeIndex).image = new ImageIcon(getClass().getClassLoader().getResource("Node.png")).getImage();
					repaint();
					break;
				}
			}
			tempNodeImm = new Node(nodeList.get(nodeIndex).getX(),nodeList.get(nodeIndex).getY());
		}
		public void mouseReleased(MouseEvent e)
		{
			
			Iterator<Edge> edgeIterator = edgeList.iterator();
			while(edgeIterator.hasNext())
			{
				Edge elem = edgeIterator.next();
				if(elem.hasNode(tempNodeImm))
				{
					edgeIterator.remove();
				}
			}
			nodeList.remove(nodeIndex);
			repaint();
		}
	}
	private class WeightListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();
			for(int i=0;i<edgeList.size();i++)
			{
				if(edgeList.get(i).onEdge(x, y))
				{
					edgeList.get(i).setWeight(newWeight);
					repaint();
				}
			}
		}
	}
	private class DFSListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX()-16;
			int y = e.getY()-16;
			for(int i=0;i<nodeList.size();i++)
			{
				int x1 = nodeList.get(i).getX();
				int y1 = nodeList.get(i).getY();
				if(x<=x1+24 && x>=x1-24 && y<=y1+24 && y>=y1-24)
				{
					nodeList.get(i).image = new ImageIcon(getClass().getClassLoader().getResource("Node.png")).getImage();
					repaint();
					adjListRefresh(); 
					boolean[] visited = new boolean[adjacencyList.length];
					DFS(i, visited);
					break;
				}
			}
		}
	}
	private class BFSListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX()-16;
			int y = e.getY()-16;
			for(int i=0;i<nodeList.size();i++)
			{
				int x1 = nodeList.get(i).getX();
				int y1 = nodeList.get(i).getY();
				if(x<=x1+24 && x>=x1-24 && y<=y1+24 && y>=y1-24)
				{
					nodeList.get(i).image = new ImageIcon(getClass().getClassLoader().getResource("Node.png")).getImage();
					repaint();
					adjListRefresh(); 
					BFS(i);
					break;
				}
			}
		}
	}
	private class DIJListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX()-16;
			int y = e.getY()-16;
			for(int i=0;i<nodeList.size();i++)
			{
				int x1 = nodeList.get(i).getX();
				int y1 = nodeList.get(i).getY();
				int id = nodeList.get(i).getId();
				if(x<=x1+24 && x>=x1-24 && y<=y1+24 && y>=y1-24)
				{
					nodeList.get(i).image = new ImageIcon(getClass().getClassLoader().getResource("Node.png")).getImage();
					repaint();
					//This is not a real edge we only use it as a data structure to store the start and end nodes that enable us to use the algorithm
					if(edge.hasInitialEnd())
					{
						edge.setNode2(x1, y1);
						edge.getNode2().setId(id);
						break;
					}
					else
					{
						edge.setNode1(x1, y1);
						edge.getNode1().setId(id);
						break;
					}
				}
			}
			if(edge.hasInitialEnd() && edge.hasTerminalEnd())
			{
				adjListRefresh();
				ArrayList<Integer> path = ShortestPath(edge.getNode1().getId(),edge.getNode2().getId());
				label.setText("Shortest Path : "+path.toString());
				edge = new Edge();
			}
		}
	}
}
