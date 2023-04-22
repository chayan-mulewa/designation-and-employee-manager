package com.chayan.mulewa.hr.dl.interfaces.dao;
import com.chayan.mulewa.hr.dl.exception.*;
import java.util.Set;
public interface DesignationDAOInterface
{
    public void add (com.chayan.mulewa.hr.dl.interfaces.dto.DesignationDTOInterface designationDTO) throws DAOException;
    public void update (com.chayan.mulewa.hr.dl.interfaces.dto.DesignationDTOInterface designationDTO) throws DAOException;
    public void delete (int code) throws DAOException;;
    public Set<com.chayan.mulewa.hr.dl.interfaces.dto.DesignationDTOInterface> getAll() throws DAOException;
    public com.chayan.mulewa.hr.dl.interfaces.dto.DesignationDTOInterface getByCode(int code) throws DAOException;
    public com.chayan.mulewa.hr.dl.interfaces.dto.DesignationDTOInterface getByTitle(String title) throws DAOException;
    public boolean codeExists(int code) throws DAOException;
    public boolean titleExists(String title) throws DAOException;
    public int getCount () throws DAOException, DAOException;
}