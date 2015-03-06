package edu.columbia.cs.psl.coverage.callstack.test;


import org.junit.Test;

import edu.columbia.cs.psl.coverage.callstack.test.CCT;

public class CCTTest {

	CCT c;
	@Test
	public void testAddChild() {
		c = new CCT("0");
		CCT.Node node = new CCT.Node();
		node.setData("1");
		c.getRoot().addChild(node);
		assert(node.getParent().getData().equals("0"));
	}

	@Test
	public void testgetChild() {
		c = new CCT("0");
		CCT.Node node = new CCT.Node("1", c.getRoot());
		assert(c.getRoot().getChild("1")==node);
	}

}
