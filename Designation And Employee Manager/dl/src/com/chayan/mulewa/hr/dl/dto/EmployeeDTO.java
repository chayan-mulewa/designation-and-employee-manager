package com.chayan.mulewa.hr.dl.dto;
import com.chayan.mulewa.hr.dl.interfaces.dto.EmployeeDTOInterface;
import com.chayan.mulewa.enums.*;
import java.util.*;
import java.math.*;
public class EmployeeDTO implements EmployeeDTOInterface
{
    private String employeeId;
    private String name;
    private int designationCode;
    private Date dateOfBirth;
    private char gender;
    private boolean isIndian;
    private BigDecimal basicSalary;
    private String panNumber;
    private String aadharNumber;
    public EmployeeDTO()
    {
        this.employeeId="";
        this.name="";
        this.designationCode=0;
        this.dateOfBirth=null;
        this.gender=' ';
        this.isIndian=false;
        this.basicSalary=null;
        this.panNumber="";
        this.aadharNumber="";
    }
    public void setEmployeeID(String employeeID)
    {
        this.employeeId=employeeID;
    }
    public String getEmployeeID()
    {
        return this.employeeId;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public String getName()
    {
        return this.name;
    }
    public void setDesignationCode(int designationCode)
    {
        this.designationCode=designationCode;
    }
    public int getDesignationCode()
    {
        return this.designationCode;
    }
    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth=dateOfBirth;
    }
    public Date getDateOfBirth()
    {
        return this.dateOfBirth;
    }
    public void setGender(GENDER gender)
    {
        if (gender==GENDER.MALE)
        {
            this.gender='M';
        }
        if (gender==GENDER.FEMALE)
        {
            this.gender='F';
        }
    }
    public char getGender()
    {
        return this.gender;
    }
    public void setIsIndian(boolean isIndian)
    {
        this.isIndian=isIndian;
    }
    public boolean getIsIndian()
    {
        return this.isIndian;
    }
    public void setBasicSalary(BigDecimal basicSalary)
    {
        this.basicSalary=basicSalary;
    }
    public BigDecimal getBasicSalary()
    {
        return this.basicSalary;
    }
    public void setPanNumber(String panNumber)
    {
        this.panNumber=panNumber;
    }
    public String getPanNumber()
    {
        return this.panNumber;
    }
    public void setAadharNumber(String aadharNumber)
    {
        this.aadharNumber=aadharNumber;
    }
    public String getAadharNumber()
    {
        return this.aadharNumber;
    }
    public boolean equals(Object other)
    {
        if(!(other instanceof  EmployeeDTOInterface))
        {
            return false;
        }
        EmployeeDTOInterface employeeDTO=(EmployeeDTOInterface)other;
        return this.employeeId.equalsIgnoreCase(employeeDTO.getEmployeeID());
    }
    public int compareTo(EmployeeDTOInterface other)
    {
        return this.employeeId.compareToIgnoreCase(other.getEmployeeID());
    }
    public int hashCode()
    {
        return this.employeeId.toUpperCase().hashCode();
    }
}
