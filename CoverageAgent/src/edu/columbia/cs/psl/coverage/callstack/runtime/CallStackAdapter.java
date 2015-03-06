package edu.columbia.cs.psl.coverage.callstack.runtime;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

public class CallStackAdapter extends AdviceAdapter{

	private String name;
	private String className;
	private String desc;

	protected CallStackAdapter(int api, MethodVisitor mv, int access,
			String name, String desc, String className) {

		super(api, mv, access, name, desc);
		this.name= name;
		this.desc = desc;
		this.className= className;

	}
	@Override
	protected void onMethodEnter() {
		mv.visitLdcInsn(className +" "+ name+" "+desc);
		mv.visitMethodInsn(INVOKESTATIC, Type.getInternalName(CallStackHelper.class), "addCallee", "(Ljava/lang/String;)V", false);
	}
	@Override
	protected void onMethodExit(int opcode) {
		mv.visitLdcInsn(className +" "+ name+" "+desc);
		mv.visitMethodInsn(INVOKESTATIC, Type.getInternalName(CallStackHelper.class), "returnToCaller", "(Ljava/lang/String;)V", false);

	}



}
