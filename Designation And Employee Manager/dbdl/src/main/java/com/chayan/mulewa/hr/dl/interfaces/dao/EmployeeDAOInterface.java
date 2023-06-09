package com.chayan.mulewa.hr.dl.interfaces.dao;
import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dto.*;
import java.util.*;

public interface EmployeeDAOInterface
{
    public void add (com.chayan.mulewa.hr.dl.interfaces.dto.EmployeeDTOInterface employeeDTO) throws DAOException;
    public void update (com.chayan.mulewa.hr.dl.interfaces.dto.EmployeeDTOInterface employeeDTO) throws DAOException;
    public void delete (String employeeId) throws DAOException;
    public Set<EmployeeDTOInterface> getAll() throws DAOException;
    public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException;
    public boolean isDesignationAlloted(int designationCode) throws DAOException;
    public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException;
    public EmployeeDTOInterface getByPanNumber(String panNumber) throws DAOException;
    public EmployeeDTOInterface getByAadharNumber(String aadharNumber) throws DAOException;
    public boolean employeeIdExists(String  employeeId) throws DAOException;
    public boolean panNumberExists(String  panNumber) throws DAOException;
    public boolean aadharNumberExists(String aadharNumber) throws DAOException;
    public int getCount () throws DAOException;
    public int getCountByDesignationCode(int designationCode) throws DAOException;
}
