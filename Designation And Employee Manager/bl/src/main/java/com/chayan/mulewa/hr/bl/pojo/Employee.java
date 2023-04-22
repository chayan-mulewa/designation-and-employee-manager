package com.chayan.mulewa.hr.bl.pojo;
import com.chayan.mulewa.hr.bl.interfaces.pojo.*;
import com.chayan.mulewa.enums.*;
import java.util.*;
import java.math.*;
public class Employee implements EmployeeInterface
{
    private String employeeID;
    private String name;
    private DesignationInterface designation;
    private Date dateOfBirth;
    private char gender;
    private boolean isIndian;
    private BigDecimal basicSalary;
    private String panNumber;
    private String aadharNumber;
    public Employee()
    {
        this.employeeID="";
        this.name="";
        this.designation=null;
        this.dateOfBirth=null;
        this.gender=' ';
        this.isIndian=false;
        this.basicSalary=null;
        this.panNumber="";
        this.aadharNumber="";
    }
    public void setEmployeeID(String employeeID)
    {
        this.employeeID=employeeID;
    }
    public String getEmployeeID()
    {
        return this.employeeID;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public String getName()
    {
        return this.name;
    }
    public void setDesignation(DesignationInterface designation)
    {
        this.designation=designation;
    }
    public DesignationInterface getDesignation()
    {
        return this.designation;
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
        if(!(other instanceof  EmployeeInterface))
        {
            return false;
        }
        EmployeeInterface Employee=(EmployeeInterface)other;
        return this.employeeID.equalsIgnoreCase(Employee.getEmployeeID());
    }
    public int compareTo(EmployeeInterface other)
    {
        return this.employeeID.compareToIgnoreCase(other.getEmployeeID());
    }
    public int hashCode()
    {
        return this.employeeID.toUpperCase().hashCode();
    }
}
