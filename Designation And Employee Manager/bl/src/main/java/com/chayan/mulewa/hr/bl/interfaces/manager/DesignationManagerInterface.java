package com.chayan.mulewa.hr.bl.interfaces.manager;
import com.chayan.mulewa.hr.bl.exceptions.*;
import com.chayan.mulewa.hr.bl.interfaces.pojo.*;
import java.util.*;
public interface DesignationManagerInterface
{
    public void addDesignation(DesignationInterface designation)throws BLException;
    public void removeDesignation(int code)throws BLException;
    public void updateDesignation(DesignationInterface designation)throws BLException;
    public DesignationInterface getDesignationByCode(int code)throws BLException;
    public DesignationInterface getDesignationByTitle(String title)throws BLException;
    public Set<DesignationInterface> getDesignations()throws BLException;
    public boolean designationCodeExists(int code)throws BLException;
    public boolean designationTitleExists(String title)throws BLException;
    public int getDesignationsCount()throws BLException;
 }