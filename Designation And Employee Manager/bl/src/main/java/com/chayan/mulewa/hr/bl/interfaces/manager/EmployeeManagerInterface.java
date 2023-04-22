package com.chayan.mulewa.hr.bl.interfaces.manager;
import com.chayan.mulewa.hr.bl.exceptions.*;
import com.chayan.mulewa.hr.bl.interfaces.pojo.*;
import java.util.*;
public interface EmployeeManagerInterface
{
    public void addEmployee (EmployeeInterface employee) throws BLException;
    public void updateEmployee (EmployeeInterface employee) throws BLException;
    public void removeEmployee (String employeeID) throws BLException;
    public EmployeeInterface getByEmployeeId(String employeeID) throws BLException;
    public EmployeeInterface getByPanNumber(String panNumber) throws BLException;
    public EmployeeInterface getByAadharNumber(String aadharNumber) throws BLException;
    public int getEmployeeCount () throws BLException;
    public boolean employeeIdExists(String  employeeID) throws BLException;
    public boolean panNumberExists(String  panNumber) throws BLException;
    public boolean aadharNumberExists(String aadharNumber) throws BLException;
    public Set<EmployeeInterface> getEmployee() throws BLException;
    public Set<EmployeeInterface> getEmployeeByDesignationCode(int designationCode) throws BLException;
    public boolean isDesignationAlloted(int designationCode) throws BLException;
    public int getEmployeeCountByDesignationCode(int designationCode) throws BLException;
}
