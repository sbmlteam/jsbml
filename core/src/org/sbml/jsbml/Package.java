package org.sbml.jsbml;

public enum Package 
{
    CORE(0),
    COMP(1_000_000),
    REQ(1_100_000),
    SPATIAL(1_200_000),
    RENDER(1_300_000),
    FBC(2_000_000),
    QUAL(3_000_000),
    GROUPS(4_000_000),
    DISTRIB(5_000_000),
    LAYOUT(6_000_000),
    MULTI(7_000_000),
    ARRAYS(8_000_000),
    DYN(9_000_000);
    
    public final int offset;
    
    private Package(int offset)
    {
	this.offset = offset;
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString().toLowerCase();
    }
    
    
    public static Package getPackageForError(int errorId)
    {
	// Clear the last five digits
	int offset = (int)((double)(errorId) / 100_000.0) * 100_000;
	
	return getPackageWithOffset(offset);
    }
    
    
    public static Package getPackageWithOffset(int offset)
    {
	for (Package p: values())
	{
	    if(p.offset == offset)
	    {
		return p;
	    }
	}
	
	return null;
    }
    
    
    public static Package getPackageWithName(String name)
    {
	if (name.isEmpty())
	{
	    return CORE;
	}
	
	String n = name.toLowerCase();
	
	for (Package p: values())
	{
	    if (n.equals(p.toString()))
	    {
		return p;
	    }
	}
	
	return null;
    }
}
