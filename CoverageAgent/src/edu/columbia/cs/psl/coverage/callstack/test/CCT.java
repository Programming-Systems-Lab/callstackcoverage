package edu.columbia.cs.psl.coverage.callstack.test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Calling context tree
 *
 */
public class CCT{

	private Node root;

	public CCT(String rootData) {
		root = new Node();
		root.data = rootData;
		root.children = new ArrayList<Node>();
	}

	public static class Node {

		private String data;
		private Node parent;
		private List<Node> children;

		public Node(){
			data = null;
			parent = null;
			children = new ArrayList<Node>();
		}

		public Node(String data, Node parent){
			this.data = data;
			this.parent = parent;
			this.children = new ArrayList<Node>();
		}

		public void addChild(Node childNode){
			this.children.add(childNode);
		}

		public Node getChild(String s){
			for(Node n: this.children){
				if(n.data.equals(s))
					return n;
			}
			return null;
		}
		public String getData(){
			return data;
		}
		public void setData(String s){
			this.data = s;
		}
		public Node getParent(){
			return parent;
		}
	}

	public Node getRoot(){
		return root;
	}

	public void printCCT(){
		postOrder(root, "|");
	}

	private void postOrder(Node node, String indentation) {

		System.out.println(indentation + node.data);
		for(Node n: node.children){
			postOrder(n, indentation+"___");
		}
	}

	public void printSubpaths(Node node, String[] path, int len) {

		if ( node == null )
			return;

		path[len] = node.data;
		len++;

		for(int i = 0; i < len; i++){
			System.out.println(path[i]);
		}
		System.out.println();

		for(Node n: node.children){
			printSubpaths(n, path, len);
		}
	}

}
