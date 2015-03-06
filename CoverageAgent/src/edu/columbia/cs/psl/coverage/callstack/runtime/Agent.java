package edu.columbia.cs.psl.coverage.callstack.runtime;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Agent {
	public static void premain(String args, Instrumentation inst) {
		ClassFileTransformer transformer = new ClassFileTransformer() {

			@Override
			public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
				//ASM documentation - http://asm.ow2.org/current/asm-transformations.pdf
				if(!toBeInstrumented(className))
					return classfileBuffer;
				ClassReader cr = new ClassReader(classfileBuffer);
				ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) {
					private String NameOfClass="";
					@Override
					public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
						super.visit(version, access, name, signature, superName, interfaces);
						NameOfClass = name;

					}
					public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
						MethodVisitor mv;
						mv = cv.visitMethod(access, name, desc, signature, exceptions);

						mv = new CallStackAdapter(api, mv, access, name, desc, NameOfClass);
						return mv;
					}
				};
				try{
					cr.accept(cv, ClassReader.EXPAND_FRAMES);
				}
				catch(Throwable t)
				{
					t.printStackTrace();
				}
				return cw.toByteArray(); //return the bytes you want the class to be
			}

		};
		inst.addTransformer(transformer);
	}

	/**
	 * Checks if the method should be instrumented
	 * @param cn classname
	 * @return
	 */
	private static boolean toBeInstrumented(String cn){
		if(cn.startsWith("java/"))
			return false;
		if(cn.startsWith("javax/"))
			return false;
		if(cn.startsWith("sun/") || cn.startsWith("com/sun"))
			return false;
		if(cn.startsWith("org/junit")||cn.startsWith("org/eclipse/jdt/internal"))
			return false;
		if(cn.startsWith("edu/columbia/cs/psl/coverage/callstack/runtime/"))
			return false;
		return true;
	}
}
