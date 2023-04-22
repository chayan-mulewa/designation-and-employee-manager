package com.chayan.mulewa.hr.bl.manager;
import com.chayan.mulewa.hr.bl.exceptions.*;
import com.chayan.mulewa.hr.bl.interfaces.pojo.*;
import com.chayan.mulewa.hr.bl.pojo.*;
import com.chayan.mulewa.hr.bl.interfaces.manager.*;
import com.chayan.mulewa.hr.dl.interfaces.dto.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.dao.*;
import com.chayan.mulewa.hr.dl.dto.*;
import com.chayan.mulewa.enums.*;
import java.util.*;
import java.math.*;
public class EmployeeManager implements EmployeeManagerInterface
{
    private Map<String,EmployeeInterface> employeeIdWiseEmployeesMap;
    private Map<String,EmployeeInterface> panNumberWiseEmployeesMap;
    private Map<String,EmployeeInterface> aadharNumberWiseEmployeesMap;
    private Set<EmployeeInterface> employeesSet;
    private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeeMap;
    private static EmployeeManager employeeManager=null;
    private EmployeeManager() throws BLException
    {
    populateDataStructures();
    }
    private void populateDataStructures() throws BLException
    {
        this.employeeIdWiseEmployeesMap=new HashMap<>();
        this.panNumberWiseEmployeesMap=new HashMap<>();
        this.aadharNumberWiseEmployeesMap=new HashMap<>();
        this.employeesSet=new TreeSet< >();
        this.designationCodeWiseEmployeeMap=new HashMap<>();
        try
        {
        Set<EmployeeDTOInterface> dlEmployees;
        dlEmployees=new EmployeeDAO().getAll();
        DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
        DesignationInterface designation;
        EmployeeInterface employee;
        Set<EmployeeInterface> ets;
        for(EmployeeDTOInterface dlEmployee:dlEmployees)
        {
            employee=new Employee();
            employee.setEmployeeID(dlEmployee.getEmployeeID());
            employee.setName(dlEmployee.getName());
            designation=designationManager.getDesignationByCode(dlEmployee.getDesignationCode());
            employee.setDesignation(designation);
            employee.setDateOfBirth(dlEmployee.getDateOfBirth());
            if(dlEmployee.getGender()=='M')
            {
                employee.setGender(GENDER.MALE);   
            }
            else
            {
                employee.setGender(GENDER.FEMALE);
            }
            employee.setIsIndian(dlEmployee.getIsIndian());
            employee.setBasicSalary(dlEmployee.getBasicSalary());
            employee.setPanNumber(dlEmployee.getPanNumber());
            employee.setAadharNumber(dlEmployee.getAadharNumber());
            this.employeeIdWiseEmployeesMap.put(employee.getEmployeeID().toUpperCase(),employee);
            this.panNumberWiseEmployeesMap.put(employee.getPanNumber().toUpperCase(),employee);
            this.aadharNumberWiseEmployeesMap.put(employee.getAadharNumber().toUpperCase(),employee);
            this.employeesSet.add(employee);
            ets=designationCodeWiseEmployeeMap.get(designation.getCode());
            if(ets==null)
            {
                ets=new TreeSet<>();
                ets.add(employee);
                designationCodeWiseEmployeeMap.put(designation.getCode(), ets);
            }
            else
            {
                ets.add(employee);
            }
        }
        } catch (DAOException daoException) 
        {
            BLException blException=new BLException();
            blException.setGenericException(daoException.getMessage());
            throw blException;
        }
    }
    public static EmployeeManagerInterface getEmployeeManager() throws BLException
    {
        if(employeeManager==null)
        {
            employeeManager=new EmployeeManager();
        }
        return employeeManager;
    }
    public void addEmployee (EmployeeInterface employee) throws BLException
    {
        BLException blException=new BLException();
        String employeeID=employee.getEmployeeID();
        String name=employee.getName();
        DesignationInterface designation=employee.getDesignation();
        int designationCode=0;
        Date dateOfBirth=employee.getDateOfBirth();
        char gender=employee.getGender();
        boolean isIndian=employee.getIsIndian();
        BigDecimal basicSalary=employee.getBasicSalary();
        String panNumber=employee.getPanNumber();
        String aadharNumber=employee.getAadharNumber();
        if(employeeID!=null)
        {
            employeeID=employeeID.trim();
            if(employeeID.length()>0)
            {
                blException.addException("employeeID","Employee Should Be Nil/Empty");
            }
        }
        if(name==null)
        {
            blException.addException("name","Name Required");
        }
        else
        {
            name=name.trim();
            if(name.length()==0)
            {
                blException.addException("name","Name Required");
            }
        }
        DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
        if(designation==null)
        {
            blException.addException("designation","Designation Required");
        }
        else
        {
            designationCode=designation.getCode();
            if(designationManager.designationCodeExists(designationCode)==false)
            {
                blException.addException("designation","Designation Required");
            }
        }
        if(dateOfBirth==null)
        {
            blException.addException("dateOfBirth","Date Of Birth Required");
        }
        if(gender==' ')
        {
            blException.addException("gender","Gender Required");
        }
        if(basicSalary==null)
        {
            blException.addException("basicSalary","Basic Salary Required");
        }
        else
        {
            if(basicSalary.signum()==-1)
            {
                blException.addException("basicSalary","Basic Salary Cannot Be Negetive");
            }
        }
        if(panNumber==null)
        {
            blException.addException("panNumber","Pan Number Required");
        }
        else
        {
            panNumber=panNumber.trim();
            if(panNumber.length()==0)
            {
                blException.addException("panNumber","Pan Number Required");
            }
        }
        if(aadharNumber==null)
        {
            blException.addException("aadharNumber","Aadhar Number Required");
        }
        else
        {
            aadharNumber=aadharNumber.trim();
            if(aadharNumber.length()==0)
            {
                blException.addException("aadharNumber","Aadhar Number Required");
            }
        }
        if(panNumber!=null && panNumber.length()>0)
        {
            if(panNumberWiseEmployeesMap.containsKey(panNumber))
            {
                blException.addException("panNumber","Pan Number Exists");
            }
        }
        if(aadharNumber!=null && aadharNumber.length()>0)
        {
            if(aadharNumberWiseEmployeesMap.containsKey(aadharNumber))
            {
                blException.addException("aadharNumber","Aadhar Number Exists");
            }
        }
        if(blException.hasExceptions())
        {
            throw blException;
        }
        try
        {
            EmployeeDAOInterface employeeDAO=new EmployeeDAO();
            EmployeeDTOInterface dlEmployee=new EmployeeDTO();
            dlEmployee.setName(name);
            dlEmployee.setDesignationCode(designation.getCode());
            dlEmployee.setDateOfBirth(dateOfBirth);
            dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
            dlEmployee.setIsIndian(isIndian);
            dlEmployee.setBasicSalary(basicSalary);
            dlEmployee.setPanNumber(panNumber);
            dlEmployee.setAadharNumber(aadharNumber);
            employeeDAO.add(dlEmployee);
            employee.setEmployeeID(dlEmployee.getEmployeeID());
            EmployeeInterface dsEmployee=new Employee();
            dsEmployee.setEmployeeID(employee.getEmployeeID());
            dsEmployee.setName(name);
            dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
            dsEmployee.setDateOfBirth(dateOfBirth);
            dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
            dsEmployee.setIsIndian(isIndian);
            dsEmployee.setBasicSalary(basicSalary);
            dsEmployee.setPanNumber(panNumber);
            dsEmployee.setAadharNumber(aadharNumber);
            employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeID().toUpperCase(),dsEmployee);
            panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
            aadharNumberWiseEmployeesMap.put(aadharNumber.toUpperCase(),dsEmployee);
            employeesSet.add(dsEmployee);
            Set<EmployeeInterface> ets;
            ets=designationCodeWiseEmployeeMap.get(dsEmployee.getDesignation().getCode());
            if(ets==null)
            {
                ets=new TreeSet<>();
                ets.add(employee);
                designationCodeWiseEmployeeMap.put(dsEmployee.getDesignation().getCode() , ets);
            }
            else
            {
                ets.add(employee);
            }
        }
        catch(DAOException daoException)
        {
            blException.setGenericException(daoException.getMessage());
        }
    }
    public void updateEmployee (EmployeeInterface employee) throws BLException
    {
        BLException blException=new BLException();
        String employeeID=employee.getEmployeeID();
        String name=employee.getName();
        DesignationInterface designation=employee.getDesignation();
        int designationCode=0;
        Date dateOfBirth=employee.getDateOfBirth();
        char gender=employee.getGender();
        boolean isIndian=employee.getIsIndian();
        BigDecimal basicSalary=employee.getBasicSalary();
        String panNumber=employee.getPanNumber();
        String aadharNumber=employee.getAadharNumber();
        if(employeeID==null)
        {
            blException.addException("employeeID","Employee Required");
        }
        else
        {
            employeeID=employeeID.trim();
            if(employeeID.length()==0)
            {
                blException.addException("employeeID","Employee Required");
            }
            else
            {
                if(employeeIdWiseEmployeesMap.containsKey(employeeID.toUpperCase())==false)
                {
                    blException.addException("employeeID","Invalid Employee ID");
                    throw blException;  
                }
            }
        }
        if(name==null)
        {
            blException.addException("name","Name Required");
        }
        else
        {
            name=name.trim();
            if(name.length()==0)
            {
                blException.addException("name","Name Required");
            }
        }
        DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
        if(designation==null)
        {
            blException.addException("designation","Designation Required");
        }
        else
        {
            designationCode=designation.getCode();
            if(designationManager.designationCodeExists(designationCode)==false)
            {
                blException.addException("designation","Designation Required");
            }
        }
        if(dateOfBirth==null)
        {
            blException.addException("dateOfBirth","Date Of Birth Required");
        }
        if(gender==' ')
        {
            blException.addException("gender","Gender Required");
        }
        if(basicSalary==null)
        {
            blException.addException("basicSalary","Basic Salary Required");
        }
        else
        {
            if(basicSalary.signum()==-1)
            {
                blException.addException("basicSalary","Basic Salary Cannot Be Negetive");
            }
        }
        if(panNumber==null)
        {
            blException.addException("panNumber","Pan Number Required");
        }
        else
        {
            panNumber=panNumber.trim();
            if(panNumber.length()==0)
            {
                blException.addException("panNumber","Pan Number Required");
            }
        }
        if(aadharNumber==null)
        {
            blException.addException("aadharNumber","Aadhar Number Required");
        }
        else
        {
            aadharNumber=aadharNumber.trim();
            if(aadharNumber.length()==0)
            {
                blException.addException("aadharNumber","Aadhar Number Required");
            }
        }
        if(panNumber!=null && panNumber.length()>0)
        {
            EmployeeInterface e=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
            if(e!=null && e.getEmployeeID().equalsIgnoreCase(employeeID)==false)
            {
                blException.addException("panNumber","Pan Number Exists");
            }
        }
        if(aadharNumber!=null && aadharNumber.length()>0)
        {
            EmployeeInterface e=aadharNumberWiseEmployeesMap.get(aadharNumber.toUpperCase());
            if(e!=null && e.getEmployeeID().equalsIgnoreCase(employeeID)==false)
            {
                blException.addException("aadharNumber","Aadhar Number Exists");
            }
        }
        if(blException.hasExceptions())
        {
            throw blException;
        }
        try
        {
            EmployeeInterface dsEmployee=employeeIdWiseEmployeesMap.get(employeeID.toUpperCase());
            String oldPanNumber=dsEmployee.getPanNumber();
            String oldAadharNumber=dsEmployee.getAadharNumber();
            int oldDesignationCode=dsEmployee.getDesignation().getCode();
            EmployeeDAO dlEmployeeDAO=new EmployeeDAO();
            EmployeeDTOInterface dlEmployee=new EmployeeDTO();
            dlEmployee.setEmployeeID(dsEmployee.getEmployeeID());
            dlEmployee.setName(name);
            dlEmployee.setDesignationCode(designation.getCode());
            dlEmployee.setDateOfBirth(dateOfBirth);
            dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
            dlEmployee.setIsIndian(isIndian);
            dlEmployee.setBasicSalary(basicSalary);
            dlEmployee.setPanNumber(panNumber);
            dlEmployee.setAadharNumber(aadharNumber);
            dlEmployeeDAO.update(dlEmployee);
            employeesSet.remove(dsEmployee);
            employeeIdWiseEmployeesMap.remove(dsEmployee.getEmployeeID().toUpperCase());
            panNumberWiseEmployeesMap.remove(oldPanNumber);
            aadharNumberWiseEmployeesMap.remove(oldAadharNumber);
            dsEmployee.setEmployeeID(dsEmployee.getEmployeeID());
            dsEmployee.setName(name);
            dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
            dsEmployee.setDateOfBirth(dateOfBirth);
            dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
            dsEmployee.setIsIndian(isIndian);
            dsEmployee.setBasicSalary(basicSalary);
            dsEmployee.setPanNumber(panNumber);
            dsEmployee.setAadharNumber(aadharNumber);
            employeesSet.add(dsEmployee);
            employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeID().toUpperCase(),dsEmployee);
            panNumberWiseEmployeesMap.put(dsEmployee.getPanNumber().toUpperCase(), dsEmployee);
            aadharNumberWiseEmployeesMap.put(dsEmployee.getAadharNumber().toUpperCase(),dsEmployee);
            if(oldDesignationCode!=dsEmployee.getDesignation().getCode())
            {
                Set<EmployeeInterface> ets;
                ets=designationCodeWiseEmployeeMap.remove(oldDesignationCode);
                ets.remove(dsEmployee);
                ets=designationCodeWiseEmployeeMap.get(dsEmployee.getDesignation().getCode());
                if(ets==null)
                {
                    ets=new TreeSet<>();
                    ets.add(employee);
                    designationCodeWiseEmployeeMap.put(dsEmployee.getDesignation().getCode() , ets);
                }
                else
                {
                    ets.add(employee);
                }
            }
        }
        catch(DAOException daoException)
        {
            blException.setGenericException(daoException.getMessage());
        }
    }
    public void removeEmployee (String employeeID) throws BLException
    {
        BLException blException=new BLException();
        if(employeeID==null)
        {
            blException.addException("employeeID","Employee Required");
            throw blException;
        }
        else
        {
            employeeID=employeeID.trim();
            if(employeeID.length()==0)
            {
                blException.addException("employeeID","Employee Required");
                throw blException;
            }
            else
            {
                if(employeeIdWiseEmployeesMap.containsKey(employeeID.toUpperCase())==false)
                {
                    blException.addException("employeeID","Invalid Employee ID");
                    throw blException;  
                }
            }
        }
        try
        {
            EmployeeInterface dsEmployee=employeeIdWiseEmployeesMap.get(employeeID.toUpperCase());
            EmployeeDAOInterface employeeDAO=new EmployeeDAO();
            employeeDAO.delete(dsEmployee.getEmployeeID());
            employeesSet.remove(dsEmployee);
            employeeIdWiseEmployeesMap.remove(dsEmployee.getEmployeeID().toUpperCase(),dsEmployee);
            panNumberWiseEmployeesMap.remove(dsEmployee.getPanNumber().toUpperCase(),dsEmployee);
            aadharNumberWiseEmployeesMap.remove(dsEmployee.getAadharNumber().toUpperCase(),dsEmployee);
            Set<EmployeeInterface> ets;
            ets=designationCodeWiseEmployeeMap.remove(dsEmployee.getDesignation().getCode());
            ets.remove(dsEmployee);

        }
        catch(DAOException daoException)
        {
            blException.setGenericException(daoException.getMessage());
        }
    }
    public EmployeeInterface getByEmployeeId(String employeeID) throws BLException
    { 
        BLException blException=new BLException();
        if(employeeID==null)
        {
            blException.addException("employeeID","Employee Required");
            throw blException; 
        }
        else
        {
            employeeID=employeeID.trim();
            if(employeeID.length()==0)
            {
                blException.addException("employeeID","Employee Required");
                throw blException;
            }
            else
            {
                if(employeeIdWiseEmployeesMap.containsKey(employeeID.toUpperCase())==false)
                {
                    blException.addException("employeeID","Invalid Employee ID");
                    throw blException;  
                }
            }
        }
        EmployeeInterface dsEmployee=employeeIdWiseEmployeesMap.get(employeeID.toUpperCase());
        DesignationInterface dsDesignation=new Designation();
        dsDesignation.setCode(dsEmployee.getDesignation().getCode());
        dsDesignation.setTitle(dsEmployee.getDesignation().getTitle());
        EmployeeInterface employee=new Employee();
        employee.setEmployeeID(dsEmployee.getEmployeeID());
        employee.setName(dsEmployee.getName());
        employee.setDesignation(dsDesignation);
        employee.setDateOfBirth(dsEmployee.getDateOfBirth());
        employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
        employee.setIsIndian(dsEmployee.getIsIndian());
        employee.setBasicSalary(dsEmployee.getBasicSalary());
        employee.setPanNumber(dsEmployee.getPanNumber());
        employee.setAadharNumber(dsEmployee.getAadharNumber());
        return employee;
    }
    public EmployeeInterface getByPanNumber(String panNumber) throws BLException
    {
        BLException blException=new BLException();
        if(panNumber==null)
        {
            blException.addException("panNumber","Pan Number Required");
            throw blException; 
        }
        else
        {
            panNumber=panNumber.trim();
            if(panNumber.length()==0)
            {
                blException.addException("panNumber","Pan Number Required");
                throw blException;
            }
            else
            {
                if(panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase())==false)
                {
                    blException.addException("panNumber","Invalid Pan Number");
                    throw blException;  
                }
            }
        }
        EmployeeInterface dsEmployee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
        DesignationInterface dsDesignation=new Designation();
        dsDesignation.setCode(dsEmployee.getDesignation().getCode());
        dsDesignation.setTitle(dsEmployee.getDesignation().getTitle());
        EmployeeInterface employee=new Employee();
        employee.setEmployeeID(dsEmployee.getEmployeeID());
        employee.setName(dsEmployee.getName());
        employee.setDesignation(dsDesignation);
        employee.setDateOfBirth(dsEmployee.getDateOfBirth());
        employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
        employee.setIsIndian(dsEmployee.getIsIndian());
        employee.setBasicSalary(dsEmployee.getBasicSalary());
        employee.setPanNumber(dsEmployee.getPanNumber());
        employee.setAadharNumber(dsEmployee.getAadharNumber());
        return employee;
    }
    public EmployeeInterface getByAadharNumber(String aadharNumber) throws BLException
    {
        BLException blException=new BLException();
        if(aadharNumber==null)
        {
            blException.addException("aadharNUmber","Aadhar Number Required");
            throw blException; 
        }
        else
        {
            aadharNumber=aadharNumber.trim();
            if(aadharNumber.length()==0)
            {
                blException.addException("aadharNumber","Aadhar Number Required");
                throw blException;
            }
            else
            {
                if(panNumberWiseEmployeesMap.containsKey(aadharNumber.toUpperCase())==false)
                {
                    blException.addException("aadharNumber","Invalid Aadhar Number");
                    throw blException;  
                }
            }
        }
        EmployeeInterface dsEmployee=panNumberWiseEmployeesMap.get(aadharNumber.toUpperCase());
        DesignationInterface dsDesignation=new Designation();
        dsDesignation.setCode(dsEmployee.getDesignation().getCode());
        dsDesignation.setTitle(dsEmployee.getDesignation().getTitle());
        EmployeeInterface employee=new Employee();
        employee.setEmployeeID(dsEmployee.getEmployeeID());
        employee.setName(dsEmployee.getName());
        employee.setDesignation(dsDesignation);
        employee.setDateOfBirth(dsEmployee.getDateOfBirth());
        employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
        employee.setIsIndian(dsEmployee.getIsIndian());
        employee.setBasicSalary(dsEmployee.getBasicSalary());
        employee.setPanNumber(dsEmployee.getPanNumber());
        employee.setAadharNumber(dsEmployee.getAadharNumber());
        return employee;
    }
    public int getEmployeeCount () throws BLException
    {
        return employeesSet.size();
    }
    public boolean employeeIdExists(String  employeeID) throws BLException
    {
        BLException blException=new BLException();
        if(employeeID==null)
        {
            blException.addException("employeeID","Employee Required");
            throw blException; 
        }
        else
        {
            employeeID=employeeID.trim();
            if(employeeID.length()==0)
            {
                blException.addException("employeeID","Employee Required");
                throw blException;
            }
            else
            {
                if(employeeIdWiseEmployeesMap.containsKey(employeeID.toUpperCase())==false)
                {
                    blException.addException("employeeID","Invalid Employee ID");
                    throw blException;  
                }
            }
        }
        return employeeIdWiseEmployeesMap.containsKey(employeeID);
    }
    public boolean panNumberExists(String  panNumber) throws BLException
    {
        BLException blException=new BLException();
        if(panNumber==null)
        {
            blException.addException("panNumber","Pan Number Required");
            throw blException; 
        }
        else
        {
            panNumber=panNumber.trim();
            if(panNumber.length()==0)
            {
                blException.addException("panNumber","Pan Number Required");
                throw blException;
            }
            else
            {
                if(panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase())==false)
                {
                    blException.addException("panNumber","Invalid Pan Number ID");
                    throw blException;  
                }
            }
        }
        return panNumberWiseEmployeesMap.containsKey(panNumber);
    }
    public boolean aadharNumberExists(String aadharNumber) throws BLException
    {
        BLException blException=new BLException();
        if(aadharNumber==null)
        {
            blException.addException("aadharNumber","Aadhar Number Required");
            throw blException; 
        }
        else
        {
            aadharNumber=aadharNumber.trim();
            if(aadharNumber.length()==0)
            {
                blException.addException("aadharNumber","aadharNumber Required");
                throw blException;
            }
            else
            {
                if(aadharNumberWiseEmployeesMap.containsKey(aadharNumber.toUpperCase())==false)
                {
                    blException.addException("aadharNumber","Invalid Employee ID");
                    throw blException;  
                }
            }
        }
        return aadharNumberWiseEmployeesMap.containsKey(aadharNumber);
    }
    public Set<EmployeeInterface> getEmployee() throws BLException
    {
        Set<EmployeeInterface> employeeSet=new TreeSet<>();
        EmployeeInterface employee;
        for(EmployeeInterface dsEmployee:employeesSet)
        {
            DesignationInterface dsDesignation=new Designation();
            dsDesignation.setCode(dsEmployee.getDesignation().getCode());
            dsDesignation.setTitle(dsEmployee.getDesignation().getTitle());
            employee=new Employee();
            employee.setEmployeeID(dsEmployee.getEmployeeID());
            employee.setName(dsEmployee.getName());
            employee.setDesignation(dsDesignation);
            employee.setDateOfBirth(dsEmployee.getDateOfBirth());
            employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
            employee.setIsIndian(dsEmployee.getIsIndian());
            employee.setBasicSalary(dsEmployee.getBasicSalary());
            employee.setPanNumber(dsEmployee.getPanNumber());
            employee.setAadharNumber(dsEmployee.getAadharNumber());
            employeeSet.add(employee);
        }
        return employeeSet;
    }
    public Set<EmployeeInterface> getEmployeeByDesignationCode(int designationCode) throws BLException
    {
        DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
        if(designationManager.designationCodeExists(designationCode)==false)
        {
            BLException blException=new BLException();
            blException.setGenericException("Invalid Code : "+designationCode);
            throw blException;
        }
        Set<EmployeeInterface> employeeSet=new TreeSet<>();
        Set<EmployeeInterface> ets=designationCodeWiseEmployeeMap.get(designationCode);
        if(ets==null)
        {
            return employeeSet;
        }
        EmployeeInterface employee;
        for(EmployeeInterface dsEmployee:ets)
        {
            DesignationInterface dsDesignation=new Designation();
            dsDesignation.setCode(dsEmployee.getDesignation().getCode());
            dsDesignation.setTitle(dsEmployee.getDesignation().getTitle());
            employee=new Employee();
            employee.setEmployeeID(dsEmployee.getEmployeeID());
            employee.setName(dsEmployee.getName());
            employee.setDesignation(dsDesignation);
            employee.setDateOfBirth(dsEmployee.getDateOfBirth());
            employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
            employee.setIsIndian(dsEmployee.getIsIndian());
            employee.setBasicSalary(dsEmployee.getBasicSalary());
            employee.setPanNumber(dsEmployee.getPanNumber());
            employee.setAadharNumber(dsEmployee.getAadharNumber());
            employeeSet.add(employee);
        }
        return employeeSet;   
    }
    public boolean isDesignationAlloted(int designationCode) throws BLException
    {
        DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
        if(designationManager.designationCodeExists(designationCode)==false)
        {
            BLException blException=new BLException();
            blException.setGenericException("Invalid Designation Code : "+designationCode);
            throw blException;
        }
        return designationCodeWiseEmployeeMap.containsKey(designationCode);
    }
    public int getEmployeeCountByDesignationCode(int designationCode) throws BLException
    {
        Set<EmployeeInterface> ets;
        ets=designationCodeWiseEmployeeMap.get(designationCode);
        if(ets==null)
        {
            return 0;
        }
        else
        {
            return ets.size();
        }
    }
}