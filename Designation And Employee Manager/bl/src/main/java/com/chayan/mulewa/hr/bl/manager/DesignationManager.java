package com.chayan.mulewa.hr.bl.manager;
import com.chayan.mulewa.hr.bl.interfaces.pojo.*;
import com.chayan.mulewa.hr.bl.interfaces.manager.*;
import com.chayan.mulewa.hr.bl.exceptions.*;
import com.chayan.mulewa.hr.bl.pojo.*;
import com.chayan.mulewa.hr.dl.interfaces.dto.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.dao.*;
import com.chayan.mulewa.hr.dl.dto.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
    private Map<Integer,DesignationInterface> codeWiseDesignationsMap;
    private Map<String,DesignationInterface> titleWiseDesignationsMap;
    private Set<DesignationInterface> designationsSet;
    private static DesignationManager designationManager=null;
    private DesignationManager() throws BLException
    {
    populateDataStructures();
    }
    private void populateDataStructures() throws BLException
    {
        this.codeWiseDesignationsMap=new HashMap<>();
        this.titleWiseDesignationsMap=new HashMap<>();
        this.designationsSet=new TreeSet< >();
        try
        {
        Set<DesignationDTOInterface> dlDesignations;
        dlDesignations=new DesignationDAO().getAll();
        DesignationInterface designation;
        for(DesignationDTOInterface dlDesignation:dlDesignations)
        {
            designation=new Designation();
            designation.setCode(dlDesignation.getCode());
            designation.setTitle(dlDesignation.getTitle());
            this.codeWiseDesignationsMap.put(designation.getCode(),designation);
            this.titleWiseDesignationsMap.put(designation.getTitle().toUpperCase(),designation);
            this.designationsSet.add(designation);
        }
        } catch (DAOException daoException) 
        {
            BLException blException=new BLException();
            blException.setGenericException(daoException.getMessage());
            throw blException;
        }
    }
    public static DesignationManagerInterface getDesignationManager() throws BLException
    {
        if(designationManager==null)
        {
            designationManager=new DesignationManager();
        }
        return designationManager;
    }
    public void addDesignation(DesignationInterface designation)throws BLException
    {
        BLException blException=new BLException();
        if(designation==null)
        {
            blException.setGenericException("Designation Required");
            throw blException;
        }
        int code=designation.getCode();
        String title=designation.getTitle();
        if(code!=0)
        {
            blException.addException("code","Code Should Be Zero");
        }
        if(title==null)
        {
            blException.addException("title","Title Required");
            title="";
        }
        else
        {
            title=title.trim();
            if(title.length()==0)
            {
                blException.addException("title","Title Required");
            }
        }
        if(title.length()>0)
        {
            if(this.titleWiseDesignationsMap.containsKey(title.toUpperCase()))
            {
                blException.addException("title","Title : "+title+" Exists");
            }
        }
        if(blException.hasExceptions())
        {
            throw blException;
        }
        try 
        {
            DesignationDTOInterface designationDTO=new DesignationDTO();
            designationDTO.setTitle(title);
            DesignationDAOInterface designationDAO=new DesignationDAO();
            designationDAO.add(designationDTO);
            code=designationDTO.getCode();
            designation.setCode(code);
            Designation dsDesignation=new Designation();
            dsDesignation.setCode(code);
            dsDesignation.setTitle(title);
            codeWiseDesignationsMap.put(code,dsDesignation);
            titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
            designationsSet.add(dsDesignation);
        }catch (DAOException daoException)
        {
            blException.setGenericException(daoException.getMessage());
        }
    }
    public void updateDesignation(DesignationInterface designation)throws BLException
    {
        BLException blException=new BLException();
        if(designation==null)
        {
            blException.setGenericException("Designation Required");
            throw blException;
        }
        int code=designation.getCode();
        String title=designation.getTitle();
        if(code<=0)
        {
            blException.addException("code","Invalid Code : "+code);
        }
        if(code>0)
        {
            if(this.codeWiseDesignationsMap.containsKey(code)==false)
            {
                blException.addException("code","Invalid Code : "+code);
                throw blException;
            }
        }
        if(title==null)
        {
            blException.addException("title","Title Required");
            title="";
        }
        else
        {
            title=title.trim();
            if(title.length()==0)
            {
                blException.addException("title","Title Required");
            }
        }
        if(title.length()>0)
        {
            DesignationInterface d;
            d=this.titleWiseDesignationsMap.get(title.toUpperCase());
            if(d!=null && d.getCode()!=code)
            {
                blException.addException("title","Title Exists : "+title);
            }
        }
        if(blException.hasExceptions())
        {
            throw blException;
        }
        try 
        {
           DesignationInterface dsDesignation=codeWiseDesignationsMap.get(code);
           DesignationDTOInterface designationDTO=new DesignationDTO();
           designationDTO.setCode(code);
           designationDTO.setTitle(title);
           new DesignationDAO().update(designationDTO);
           this.codeWiseDesignationsMap.remove(code);
           this.titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
           this.designationsSet.remove(dsDesignation);
           dsDesignation.setTitle(title);
           this.codeWiseDesignationsMap.put(code,dsDesignation);
           this.titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
           designationsSet.add(dsDesignation);
        }catch (DAOException daoException)
        {
            blException.setGenericException(daoException.getMessage());
        }    
    }
    public void removeDesignation(int code)throws BLException
    {
        BLException blException=new BLException();
        if(code<=0)
        {
            blException.addException("code","Invalid Code : "+code);
            throw blException;
        }
        if(code>0)
        {
            if(this.codeWiseDesignationsMap.containsKey(code)==false)
            {
                blException.addException("code","Invalid Code : "+code);
                throw blException;
            }
        }
        try 
        {
           DesignationInterface dsDesignation=codeWiseDesignationsMap.get(code);
           new DesignationDAO().delete(code);
           this.codeWiseDesignationsMap.remove(code);
           this.titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
           this.designationsSet.remove(dsDesignation);
        }catch (DAOException daoException)
        {
            blException.setGenericException(daoException.getMessage());
        }    
    }
    public DesignationInterface getDesignationByCode(int code)throws BLException
    {
        DesignationInterface designation=codeWiseDesignationsMap.get(code);
        if(designation==null)
        {
            BLException blException=new BLException();
            blException.addException("code","Invalid Code : "+code);
            throw blException;
        }
        return designation;
    }
    DesignationInterface getDSDesignationByCode(int code)throws BLException
    {
        DesignationInterface designation=codeWiseDesignationsMap.get(code);
        if(designation==null)
        {
            BLException blException=new BLException();
            blException.addException("code","Invalid Code : "+code);
            throw blException;
        }
        return designation;
    }
    public DesignationInterface getDesignationByTitle(String title)throws BLException
    {
        DesignationInterface designation=titleWiseDesignationsMap.get(title.toUpperCase());
        if(designation==null)
        {
            BLException blException=new BLException();
            blException.addException("title","Invalid Designation : "+title);
            throw blException;
        }
        return designation;
    }
    public Set<DesignationInterface> getDesignations()throws BLException
    {
        Set<DesignationInterface> designations=new TreeSet<>();
        designationsSet.forEach((designation)->{
        DesignationInterface d=new Designation();
        d.setCode(designation.getCode());
        d.setTitle(designation.getTitle());
        designations.add(d);
        });
        return designations;
    }
    public boolean designationCodeExists(int code)throws BLException
    {
        return codeWiseDesignationsMap.containsKey(code);
    }
    public boolean designationTitleExists(String title)throws BLException
    {
        return titleWiseDesignationsMap.containsKey(title.toUpperCase());
    }
    public int getDesignationsCount()throws BLException
    {
        return designationsSet.size();
    }
}