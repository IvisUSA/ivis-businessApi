package com.ivis.ApplicationModels;

public class Subcategories {

	Integer subcatid;
	
	String subcatname;

	public Integer getSubcatid() {
		return subcatid;
	}

	public void setSubcatid(Integer subcatid) {
		this.subcatid = subcatid;
	}

	public String getSubcatname() {
		return subcatname;
	}

	public void setSubcatname(String subcatname) {
		this.subcatname = subcatname;
	}
	
	public Subcategories(Integer subcatid, String subcatname) {
		
		this.subcatid = subcatid;
		this.subcatname = subcatname;

	}
	
    public Subcategories() {
		// TODO Auto-generated constructor stub
	}

	@Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if(obj instanceof Subcategories)
        {
        	Subcategories temp = (Subcategories) obj;
            if((this.subcatid == temp.subcatid) && this.subcatname.equals(temp.subcatname))
                return true;
        }
        return false;
    }
	
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        
        return (this.subcatid.hashCode() + this.subcatname.hashCode());        
    }
	
	
}
