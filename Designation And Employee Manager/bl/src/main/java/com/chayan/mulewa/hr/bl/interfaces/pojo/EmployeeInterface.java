package com.chayan.mulewa.hr.bl.interfaces.pojo;
import com.chayan.mulewa.enums.*;
import java.util.*;
import java.math.*;
public interface EmployeeInterface extends Comparable<EmployeeInterface>,java.io.Serializable
{
    public void setEmployeeID(String employeeID);
    public String getEmployeeID();
    public void setName(String name);
    public String getName();
    public void setDesignation(DesignationInterface designation);
    public DesignationInterface getDesignation();
    public void setDateOfBirth(Date dateOfBirth);
    public Date getDateOfBirth();
    public void setGender(GENDER gender);
    public char getGender();
    public void setIsIndian(boolean isIndian);
    public boolean getIsIndian();
    public void setBasicSalary(BigDecimal basicSalary);
    public BigDecimal getBasicSalary();
    public void setPanNumber(String panNumber);
    public String getPanNumber();
    public void setAadharNumber(String aadharNumber);
    public String getAadharNumber();
}
