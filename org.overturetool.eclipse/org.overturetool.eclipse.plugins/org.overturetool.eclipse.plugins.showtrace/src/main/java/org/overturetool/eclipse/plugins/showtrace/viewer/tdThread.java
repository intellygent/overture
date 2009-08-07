// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 31-07-2009 16:17:13
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   tdThread.java

package org.overturetool.eclipse.plugins.showtrace.viewer;

import java.util.Vector;

import jp.co.csk.vdm.toolbox.VDM.CGException;
import jp.co.csk.vdm.toolbox.VDM.UTIL;

// Referenced classes of package org.overturetool.tracefile.viewer:
//            tdHistory, tdCPU, TraceData, tdObject
@SuppressWarnings("unchecked")
public class tdThread extends tdHistory
{

    public tdThread()
        throws CGException
    {
        theCpu = null;
        id = null;
        curobj = null;
        blocked = null;
        try
        {
            curobj = new Vector();
            blocked = new Boolean(false);
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
            System.out.println(e.getMessage());
        }
    }

    public tdThread(tdCPU cpu, Long pthrid)
        throws CGException
    {
        this();
        theCpu = (tdCPU)UTIL.clone(cpu);
        id = UTIL.NumberToLong(UTIL.clone(pthrid));
    }

    public Long getId()
        throws CGException
    {
        return id;
    }

    public void setStatus(Boolean pblocked)
        throws CGException
    {
        blocked = (Boolean)UTIL.clone(pblocked);
    }

    public Boolean getStatus()
        throws CGException
    {
        return blocked;
    }

    public void pushCurrentObject(Long pobjid)
        throws CGException
    {
        Vector rhs_2 = null;
        Vector var1_3 = null;
        var1_3 = new Vector();
        var1_3.add(pobjid);
        rhs_2 = (Vector)var1_3.clone();
        rhs_2.addAll(curobj);
        curobj = (Vector)UTIL.ConvertToList(UTIL.clone(rhs_2));
    }

    public void popCurrentObject()
        throws CGException
    {
        curobj = (Vector)UTIL.ConvertToList(UTIL.clone(new Vector(curobj.subList(1, curobj.size()))));
    }

    public Boolean hasCurrentObject()
        throws CGException
    {
        return new Boolean((new Long(curobj.size())).intValue() > (new Long(0L)).intValue());
    }

    public tdObject getCurrentObject()
        throws CGException
    {
        if(!pre_getCurrentObject().booleanValue())
            UTIL.RunTime("Run-Time Error:Precondition failure in getCurrentObject");
        tdObject rexpr_1 = null;
        TraceData obj_2 = null;
        obj_2 = theCpu.getTraceData();
        rexpr_1 = obj_2.getObject(UTIL.NumberToLong(curobj.get(0)));
        return rexpr_1;
    }

    public Boolean pre_getCurrentObject()
        throws CGException
    {
        return hasCurrentObject();
    }

    @Override
	public void reset()
        throws CGException
    {
        curobj = (Vector)UTIL.ConvertToList(UTIL.clone(new Vector()));
        blocked = (Boolean)UTIL.clone(new Boolean(false));
    }

    static jp.co.csk.vdm.toolbox.VDM.UTIL.VDMCompare vdmComp = new jp.co.csk.vdm.toolbox.VDM.UTIL.VDMCompare();
    private tdCPU theCpu;
    private Long id;
    private Vector curobj;
    private Boolean blocked;

}