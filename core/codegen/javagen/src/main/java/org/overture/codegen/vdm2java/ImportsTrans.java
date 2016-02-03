package org.overture.codegen.vdm2java;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.util.ClonableString;
import org.overture.codegen.ir.analysis.AnalysisException;
import org.overture.codegen.ir.analysis.DepthFirstAnalysisAdaptor;
import org.overture.codegen.ir.declarations.ADefaultClassDeclCG;
import org.overture.codegen.ir.declarations.ASystemClassDeclCG;
import org.overture.codegen.ir.declarations.SClassDeclCG;
import org.overture.codegen.ir.IRInfo;

public class ImportsTrans extends DepthFirstAnalysisAdaptor
{
	private IRInfo info;
	
	public ImportsTrans(IRInfo info)
	{
		this.info = info;
	}
	
	@Override
	public void caseADefaultClassDeclCG(ADefaultClassDeclCG node) throws AnalysisException
	{
		handleClass(node);
	}
	
	@Override
	public void caseASystemClassDeclCG(ASystemClassDeclCG node) throws AnalysisException
	{
		handleClass(node);
	}

	private void handleClass(SClassDeclCG node)
	{
		List<ClonableString> dep = new LinkedList<>();
		
		if(!info.getDeclAssistant().isInnerClass(node))
		{
			dep.add(new ClonableString(JavaCodeGen.JAVA_UTIL));
			dep.add(new ClonableString(JavaCodeGen.RUNTIME_IMPORT));
		}
		else if(!info.getDeclAssistant().isInnerClass(node) && isQuote(node))
		{
			dep.add(new ClonableString(JavaCodeGen.RUNTIME_IMPORT));
		}
		
		if(importTraceSupport(node))
		{
			dep.add(new ClonableString(JavaCodeGen.TRACE_IMPORT));
		}
		
		node.setDependencies(dep);
	}
	
	public static boolean isQuote(SClassDeclCG classCg)
	{
		return classCg != null && JavaCodeGen.JAVA_QUOTES_PACKAGE.equals(classCg.getPackage());
	}
	
	public boolean importTraceSupport(SClassDeclCG node)
	{
		return info.getSettings().generateTraces() && !node.getTraces().isEmpty();
	}
}
