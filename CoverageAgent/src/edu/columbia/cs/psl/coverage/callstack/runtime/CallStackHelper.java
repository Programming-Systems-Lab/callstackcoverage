package edu.columbia.cs.psl.coverage.callstack.runtime;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 *
 * Two implementations of getting call sequences
 * 1. using regular stack: callstackpush, callstackrecord
 * 2. using CCT: addCallee, returnToCaller
 *
 */
public class CallStackHelper {

	private static Stack<String> stack = new Stack<String>();
	private static Set<String> callstackSet = new HashSet<String>();
	private static CCT cct = null;
	private static CCT.Node currentNode = null;

	/**
	 * add method to CCT and update pointer
	 * @param methodName classname+methodname+signature
	 */

	public static void addCallee(String methodName){

		//program starts
		if(cct==null){
			cct = new CCT(methodName);
			currentNode = cct.getRoot();
		}
		else{

			CCT.Node methodNode = currentNode.getChild(methodName);
			if(methodNode == null){
				methodNode = new CCT.Node(methodName, currentNode);
				currentNode.addChild(methodNode);

			}
			currentNode = methodNode;
		}
	}

	/**
	 * update pointer to point to parent node
	 * @param methodName classname+methodname+signature
	 */

	static{
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
			public void run()
			{
				System.out.println("CCT:\n");
				cct.printCCT();
				System.out.println("\ncalling sequences:\n");
				cct.printSubpaths(cct.getRoot(), new String[1000], 0);
			}
		}));
	}
	public static void returnToCaller(String methodName){

		assert(currentNode.getData().equals(methodName));

		//program ends
		if(currentNode == cct.getRoot()){
//			System.out.println("CCT:\n");
//			cct.printCCT();
//			System.out.println("\ncalling sequences:\n");
//			cct.printSubpaths(cct.getRoot(), new String[1000], 0);
		}
		else{
			currentNode = currentNode.getParent();
		}

	}

	/**
	 * push method to callstack
	 * @param methodName classname+methodname+signature
	 */

	public static void callstackpush(String methodName){
		stack.push(methodName);
	}

	/**
	 * record current callstack and pop
	 * calls outputstack() when the stack is empty after pop operation
	 * @param methodName classname+methodname+signature
	 */

	public static void callstackrecord(String methodName){

		String currentstack ="";
		for(String s: stack){
			currentstack += s+ "\n";
		}
		callstackSet.add(currentstack);

		assert(!stack.isEmpty());
		assert(stack.peek().equals(methodName));
		stack.pop();

		if(stack.isEmpty()){
			printcallstack();
		}
	}

	/**
	 * print calling sequences from CallstackSet
	 */

	public static void printcallstack(){
		for(String s: callstackSet){
			System.out.println(s);
		}
	}
}
