package edu.columbia.cs.psl.coverage.callstack.runtime;

public class Test {
	public static void main(String[] args) {
		method1();
		method1();
		method1();
		method2();
		method3();
		//factorial(2);
	}
	public static void method1(){
		method2();
	}
	public static void method2(){
	}

	public static void method3(){
		method1();
	}

	//		public static int factorial( int integer){
	//			if( integer == 1)
	//				return 1;
	//			else{
	//				return(integer*(factorial(integer-1)));
	//			}
	//		}

}
