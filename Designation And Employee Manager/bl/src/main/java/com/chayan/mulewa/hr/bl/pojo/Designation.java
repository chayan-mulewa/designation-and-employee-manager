package com.chayan.mulewa.hr.bl.pojo;
import com.chayan.mulewa.hr.bl.interfaces.pojo.*;
public class Designation implements DesignationInterface
{
    private int code;
    private String title;
    public void setCode(int code)
    {
        this.code=code;
    }
    public int getCode()
    {
        return this.code;
    }
    public void setTitle(String title)
    {
        this.title=title;
    }
    public String getTitle()
    {
        return this.title;
    }
    public int hashCode(int code)
    {
        return this.code;
    }
    public boolean equels(Object object)
    {
        if(!(object instanceof DesignationInterface))
        {
            return false;
        }
        DesignationInterface designation=(DesignationInterface)object;
        return this.code==designation.getCode();
    }
    public int compareTo(DesignationInterface designation)
    {
        return this.code-designation.getCode();
    }

}