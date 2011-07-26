/*******************************************************************************
 *
 *	Copyright (C) 2008 Fujitsu Services Ltd.
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

package org.overturetool.vdmj.values;

import org.overture.interpreter.ast.types.ARationalNumericBasicTypeInterpreter;
import org.overture.interpreter.ast.types.PTypeInterpreter;
import org.overturetool.vdmj.runtime.Context;
import org.overturetool.vdmj.runtime.ValueException;


public class RationalValue extends RealValue
{
	private static final long serialVersionUID = 1L;

	public RationalValue(double value) throws Exception
	{
		super(value);
	}

	public RationalValue(long value)
	{
		super(value);
	}

	@Override
	public String kind()
	{
		return "rat";
	}

	@Override
	public Value convertValueTo(PTypeInterpreter to, Context ctxt) throws ValueException
	{
		if (to instanceof ARationalNumericBasicTypeInterpreter)
		{
			return this;
		}
		else
		{
			return super.convertValueTo(to, ctxt);
		}
	}

	@Override
	public Object clone()
	{
		try
		{
			return new RationalValue(value);
		}
		catch (Exception e)
		{
			// Can't happen?
			return null;
		}
	}
}
