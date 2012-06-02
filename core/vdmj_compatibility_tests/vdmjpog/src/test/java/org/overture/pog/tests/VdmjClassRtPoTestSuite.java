package org.overture.pog.tests;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.overture.pog.tests.framework.BaseTestSuite;
import org.overture.pog.tests.framework.ClassRtPoTestCase;

import junit.framework.Test;
import junit.framework.TestSuite;



public class VdmjClassRtPoTestSuite extends BaseTestSuite {
	
	public static Test suite() throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException
	{
		String name = "VDMJ PO Class PP TestSuite";
		String root = "src\\test\\resources\\classesRT\\";
		TestSuite test = createTestCompleteFile(name, root, ClassRtPoTestCase.class);
		return test;
	}
}
