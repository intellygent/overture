/*******************************************************************************
 *
 *	Copyright (C) 2016 Fujitsu Services Ltd.
 *
 *	Author: Nick Battle
 *
 *	This file is part of VDMJ.
 *
 *	VDMJ is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	VDMJ is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with VDMJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package org.overture.pog.obligation;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.AInSetBinaryExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstExpressionFactory;
import org.overture.ast.patterns.PPattern;
import org.overture.pog.pub.IPOContextStack;
import org.overture.pog.pub.IPogAssistantFactory;
import org.overture.pog.pub.POType;

public class SetMembershipObligation extends ProofObligation
{
	private static final long serialVersionUID = -1385749421110721860L;

	public SetMembershipObligation(PPattern pattern, PExp set, IPOContextStack ctxt,
			IPogAssistantFactory af) throws AnalysisException
	{
		super(set, POType.SET_MEMBER, ctxt, set.getLocation(), af);

		AInSetBinaryExp inSetExp = AstExpressionFactory.newAInSetBinaryExp(patternToExp(pattern), set.clone());

		stitch = inSetExp;
		valuetree.setPredicate(ctxt.getPredWithContext(inSetExp));
	}
}
