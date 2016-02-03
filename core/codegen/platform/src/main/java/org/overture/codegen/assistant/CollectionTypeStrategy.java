package org.overture.codegen.assistant;

import org.overture.codegen.ir.STypeCG;

public interface CollectionTypeStrategy
{
	boolean isCollectionType(STypeCG type);
	
	STypeCG getElementType(STypeCG type);
}
