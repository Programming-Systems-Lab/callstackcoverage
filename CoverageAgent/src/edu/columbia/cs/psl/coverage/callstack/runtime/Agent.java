package edu.columbia.cs.psl.coverage.callstack.runtime;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Arrays;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public class Agent {
	public static void premain(String args, Instrumentation inst) {
		ClassFileTransformer transformer = new ClassFileTransformer() {

			@Override
			public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
				System.out.println("Loading " + className);
				//ASM documentation - http://asm.ow2.org/current/asm-transformations.pdf
				ClassReader cr = new ClassReader(classfileBuffer);
				ClassWriter cw = new ClassWriter(0);
				ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) {
					@Override
					public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
						super.visit(version, access, name, signature, superName, interfaces);
						System.out.println("ASM Visit class " + name + ", super: " + superName + ", interfaces: " + Arrays.toString(interfaces));
					}
				};
				cr.accept(cv, 0);
				return null; // or return the bytes you want the class to be
			}

		};
		inst.addTransformer(transformer);
	}
}
