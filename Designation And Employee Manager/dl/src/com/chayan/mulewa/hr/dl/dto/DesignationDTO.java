package com.chayan.mulewa.hr.dl.dto;
import com.chayan.mulewa.hr.dl.interfaces.dto.*;
import com.chayan.mulewa.hr.dl.exception.*;
public class DesignationDTO implements DesignationDTOInterface
{
    private int code;
    public String title;
    public void setCode (int code)
    {
        this.code=code;
    }
    public int getCode ()
    {
        return this.code;
    }
    public void setTitle (String title)
    {
        this.title=title;
    }
    public String getTitle ()
    {
        return this.title;
    }
    public boolean equals(Object other)
    {
        if (!(other instanceof DesignationDTOInterface))
        {
            return false;
        }
        DesignationDTOInterface designationDTO;
        designationDTO=(DesignationDTOInterface) other;
        return this.code==designationDTO.getCode();
    }
    public int compareTo (DesignationDTOInterface designationDTO)
    {
        return this.code-designationDTO.getCode();
    }
    public int hashCode()
    {
        return code;
    }
}