


//
// THIS FILE IS AUTOMATICALLY GENERATED!!
//
// Generated at 2009-10-16 by the VDM++ to JAVA Code Generator
// (v8.2.1b - Wed 15-Jul-2009 14:09:22)
//
// Supported compilers: jdk 1.4/1.5/1.6
//

// ***** VDMTOOLS START Name=HeaderComment KEEP=NO
// ***** VDMTOOLS END Name=HeaderComment

// ***** VDMTOOLS START Name=package KEEP=NO
package org.overturetool.umltrans.uml;

// ***** VDMTOOLS END Name=package

// ***** VDMTOOLS START Name=imports KEEP=NO

import jp.co.csk.vdm.toolbox.VDM.*;
import java.util.*;
import org.overturetool.ast.itf.*;
import org.overturetool.ast.imp.*;
import org.overturetool.api.io.*;
import org.overturetool.api.io.*;
import org.overturetool.api.xml.*;
import org.overturetool.umltrans.api.*;
import org.overturetool.umltrans.*;
import org.overturetool.umltrans.uml.*;
import org.overturetool.umltrans.uml2vdm.*;
import org.overturetool.umltrans.vdm2uml.*;
// ***** VDMTOOLS END Name=imports



@SuppressWarnings({"all","unchecked","unused"})
public abstract class IUmlConstraint extends IUmlModelElement {

// ***** VDMTOOLS START Name=vdmComp KEEP=NO
  static UTIL.VDMCompare vdmComp = new UTIL.VDMCompare();
// ***** VDMTOOLS END Name=vdmComp


// ***** VDMTOOLS START Name=vdm_init_IUmlConstraint KEEP=NO
  private void vdm_init_IUmlConstraint () throws CGException {}
// ***** VDMTOOLS END Name=vdm_init_IUmlConstraint


// ***** VDMTOOLS START Name=IUmlConstraint KEEP=NO
  public IUmlConstraint () throws CGException {
    vdm_init_IUmlConstraint();
  }
// ***** VDMTOOLS END Name=IUmlConstraint


// ***** VDMTOOLS START Name=getConstraintElements KEEP=NO
  abstract public HashSet getConstraintElements () throws CGException ;
// ***** VDMTOOLS END Name=getConstraintElements


// ***** VDMTOOLS START Name=getSpecification KEEP=NO
  abstract public IUmlValueSpecification getSpecification () throws CGException ;
// ***** VDMTOOLS END Name=getSpecification

}
;