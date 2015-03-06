package edu.columbia.cs.psl.coverage.callstack.runtime;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class DebugPrintingMethodVisitor extends MethodVisitor{

	public DebugPrintingMethodVisitor(MethodVisitor mv) {
		super(Opcodes.ASM5, mv);
	}
	@Override
	public void visitInsn(int opcode) {
		super.visitInsn(opcode);
		//System.out.println("Visit instruction: "+Printer.OPCODES[opcode]);
	}

}
