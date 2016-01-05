package org.overture.codegen.vdm2x;

import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.overture.codegen.cgast.INode;
import org.overture.codegen.cgast.SExpCG;
import org.overture.codegen.cgast.SStmCG;
import org.overture.codegen.cgast.analysis.AnalysisException;
import org.overture.codegen.cgast.declarations.AFieldDeclCG;
import org.overture.codegen.cgast.declarations.AFormalParamLocalParamCG;
import org.overture.codegen.cgast.declarations.AMethodDeclCG;
import org.overture.codegen.logging.Logger;
import org.overture.codegen.merging.MergeVisitor;
import org.overture.codegen.merging.TemplateCallable;
import org.overture.codegen.merging.TemplateManager;
import org.overture.codegen.merging.TemplateStructure;
import org.overture.codegen.trans.TempVarPrefixes;
import org.overture.codegen.utils.GeneralUtils;


public class XFormat {

	private MergeVisitor mergeVisitor;

	public XFormat(TempVarPrefixes varPrefixes) {
		TemplateManager templateManager = new TemplateManager(
				new TemplateStructure("MyTemplates"));
		TemplateCallable[] templateCallables = new TemplateCallable[] { new TemplateCallable(
				"XFormat", this) };
		this.mergeVisitor = new MergeVisitor(templateManager, templateCallables);
	}

	public String format(INode node) throws AnalysisException {
		StringWriter writer = new StringWriter();
		node.apply(mergeVisitor, writer);

		return writer.toString();
	}

	private String format(INode node, boolean ignoreContext)
			throws AnalysisException
	{
		StringWriter writer = new StringWriter();
		node.apply(mergeVisitor, writer);

		return "hi";//writer.toString() + "";//getNumberDereference(node, ignoreContext);
	}
	
	public String format(SExpCG exp, boolean leftChild)
			throws AnalysisException
	{
		String formattedExp = format(exp);

		XPrecedence precedence = new XPrecedence();

		INode parent = exp.parent();

		if (!(parent instanceof SExpCG))
		{
			return formattedExp;
		}

		boolean isolate = precedence.mustIsolate((SExpCG) parent, exp, leftChild);

		return isolate ? "(" + formattedExp + ")" : formattedExp;
	}
	
	public MergeVisitor GetMergeVisitor() {
		return mergeVisitor;
	}

	public String formatOperationBody(SStmCG body) throws AnalysisException {
		String NEWLINE = "\n";
		if (body == null) {
			return ";";
		}

		StringWriter generatedBody = new StringWriter();

		generatedBody.append("{" + NEWLINE + NEWLINE);
		generatedBody.append(handleOpBody(body));
		generatedBody.append(NEWLINE + "}");

		return generatedBody.toString();
	}

	private String handleOpBody(SStmCG body) throws AnalysisException {
		AMethodDeclCG method = body.getAncestor(AMethodDeclCG.class);

		if (method == null) {
			Logger.getLog().printErrorln(
					"Could not find enclosing method when formatting operation body. Got: "
							+ body);
		} else if (method.getAsync() != null && method.getAsync()) {
			return "new VDMThread(){ " + "\tpublic void run() {" + "\t "
					+ format(body) + "\t} " + "}.start();";
		}

		return format(body);
	}

	public String format(List<AFormalParamLocalParamCG> params)
			throws AnalysisException
	{
		StringWriter writer = new StringWriter();

		if (params.size() <= 0)
		{
			return "";
		}

		AFormalParamLocalParamCG firstParam = params.get(0);
		writer.append(format(firstParam));

		for (int i = 1; i < params.size(); i++)
		{
			AFormalParamLocalParamCG param = params.get(i);
			writer.append(", ");
			writer.append(format(param));
		}
		return writer.toString();
	}
	
	public String formatArgs(List<? extends SExpCG> exps)
			throws AnalysisException {
		StringWriter writer = new StringWriter();

		if (exps.size() <= 0) {
			return "";
		}

		SExpCG firstExp = exps.get(0);
		writer.append(format(firstExp));

		for (int i = 1; i < exps.size(); i++) {
			SExpCG exp = exps.get(i);
			writer.append(", " + format(exp));
		}

		return writer.toString();
	}

	public List<AMethodDeclCG> getMethodsByAccess(List<AMethodDeclCG> methods,
			String access) {
		LinkedList<AMethodDeclCG> matches = new LinkedList<AMethodDeclCG>();

		for (AMethodDeclCG m : methods) {
			if (m.getAccess().equals(access)) {
				matches.add(m);
			}
		}

		return matches;
	}
	
	public boolean isNull(INode node)
	{
		return node == null;
	}
	
	public String escapeChar(char c)
	{
		return GeneralUtils.isEscapeSequence(c) ? StringEscapeUtils.escapeJavaScript(c
				+ "")
				: c + "";
	}

	public List<AFieldDeclCG> getFieldsByAccess(List<AFieldDeclCG> fields,
			String access) {
		LinkedList<AFieldDeclCG> matches = new LinkedList<AFieldDeclCG>();

		for (AFieldDeclCG f : fields) {
			if (f.getAccess().equals(access)) {
				matches.add(f);
			}
		}

		return matches;
	}
	
	public String getTVPtype(){
		return "TVP";
	}
}