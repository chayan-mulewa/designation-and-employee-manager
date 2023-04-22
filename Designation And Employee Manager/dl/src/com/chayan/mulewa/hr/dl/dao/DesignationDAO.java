package com.chayan.mulewa.hr.dl.dao;
import com.chayan.mulewa.hr.dl.dto.DesignationDTO;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.interfaces.dto.*;
import com.chayan.mulewa.hr.dl.exception.*;
import java.util.*;
import java.io.*;
public class DesignationDAO implements DesignationDAOInterface
{
    private final static String FILE_NAME="designation.data";
    public void add (com.chayan.mulewa.hr.dl.interfaces.dto.DesignationDTOInterface designationDTO) throws DAOException
    {
        if (designationDTO==null)
        {
            throw new DAOException("Designation Is Null");
        }
        String title;
        title= designationDTO.getTitle();
        if(title==null)
        {
            throw new DAOException("Designation Is Null");
        }
        title=title.trim();
        if (title.length()==0)
        {
            throw new DAOException("Designation Is Null");
        }
        try
        {
            File file=new File(FILE_NAME);
            RandomAccessFile randomAccessFile;
            randomAccessFile=new RandomAccessFile(file,"rw");
            int lastGenretedCode=0;
            int recordCount=0;
            String lastGenretedCodeString="";
            String recordCountString="";
            if (randomAccessFile.length()==0)
            {
                lastGenretedCodeString="0";
                while (lastGenretedCodeString.length()<10)
                {
                    lastGenretedCodeString+=" ";
                }
                recordCountString="0";
                while (recordCountString.length()<10)
                {
                    recordCountString+=" ";
                }
                randomAccessFile.writeBytes(lastGenretedCodeString);
                randomAccessFile.writeBytes("\n");
                randomAccessFile.writeBytes(recordCountString);
                randomAccessFile.writeBytes("\n");
            }
            else
            {
                lastGenretedCodeString=randomAccessFile.readLine().trim();
                lastGenretedCode=Integer.parseInt(lastGenretedCodeString);
                recordCountString=randomAccessFile.readLine().trim();
                recordCount=Integer.parseInt(recordCountString);
            }
            int fCode;
            String fTitle;
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                fCode=Integer.parseInt(randomAccessFile.readLine());
                fTitle=randomAccessFile.readLine();
                if (fTitle.equalsIgnoreCase(title))
                {
                    throw new DAOException("Designation : "+title+" Exists");
                }
            }
            int code=lastGenretedCode+1;
            randomAccessFile.writeBytes(String.valueOf(code));
            randomAccessFile.writeBytes("\n");
            randomAccessFile.writeBytes(title);
            randomAccessFile.writeBytes("\n");
            designationDTO.setCode(code);
            lastGenretedCode++;
            recordCount++;
            lastGenretedCodeString=String.valueOf(lastGenretedCode);
            while (lastGenretedCodeString.length()<10)
            {
                lastGenretedCodeString+=" ";
            }
            recordCountString=String.valueOf(recordCount);
            while (recordCountString.length()<10)
            {
                recordCountString+=" ";
            }
            randomAccessFile.seek(0);
            randomAccessFile.writeBytes(lastGenretedCodeString);
            randomAccessFile.writeBytes("\n");
            randomAccessFile.writeBytes(recordCountString);
            randomAccessFile.writeBytes("\n");
            randomAccessFile.close();
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public void update (com.chayan.mulewa.hr.dl.interfaces.dto.DesignationDTOInterface designationDTO) throws DAOException
    {
        if (designationDTO==null)
        {
            throw new DAOException("Designation Is Null");
        }
        int code;
        code=designationDTO.getCode();
        if (code<=0)
        {
            throw new DAOException("Invalid Code : "+code);
        }
        String title;
        title= designationDTO.getTitle();
        if(title==null)
        {
            throw new DAOException("Designation Is Null");
        }
        title=title.trim();
        if (title.length()==0)
        {
            throw new DAOException("Length of Designation is Zero");
        }
        try
        {
            File file=new File(FILE_NAME);
            if(!file.exists())
            {
                throw new DAOException("Invalid Code : "+code);
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                randomAccessFile.close();
                throw new DAOException("Invalid Code : "+code);
            }
            int fCode=0;
            String fTitle="";
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            boolean found=false;
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                fCode=Integer.parseInt(randomAccessFile.readLine());
                fTitle=randomAccessFile.readLine();
                if(fCode==code)
                {
                    found=true;
                    break;
                }
            }
            if (found==false)
            {
                throw new DAOException("Invalid Code : "+code);
            }
            if (new EmployeeDAO().isDesignationAlloted(code))
            {
                randomAccessFile.close();
                throw new DAOException("Employee Alloted With Desigantion : "+fTitle);
            }
            randomAccessFile.seek(0);
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            while (randomAccessFile.getFilePointer()< randomAccessFile.length())
            {
                fCode=Integer.parseInt(randomAccessFile.readLine());
                fTitle=randomAccessFile.readLine();
                if (fCode!=code && title.equalsIgnoreCase(fTitle)==true)
                {
                    randomAccessFile.close();
                    throw new DAOException("Title : "+title+" Exists");
                }
            }
            File tmpFile=new File("tmp.data");
            if(tmpFile.exists())
            {
                tmpFile.delete();
            }
            RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
            randomAccessFile.seek(0);
            tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
            tmpRandomAccessFile.writeBytes("\n");
            tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
            tmpRandomAccessFile.writeBytes("\n");
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                fCode=Integer.parseInt(randomAccessFile.readLine());
                fTitle=randomAccessFile.readLine();
                if (code!=fCode)
                {
                    tmpRandomAccessFile.writeBytes(String.valueOf(fCode));
                    tmpRandomAccessFile.writeBytes("\n");
                    tmpRandomAccessFile.writeBytes(fTitle);
                    tmpRandomAccessFile.writeBytes("\n");
                }
                else
                {
                    tmpRandomAccessFile.writeBytes(String.valueOf(code));
                    tmpRandomAccessFile.writeBytes("\n");
                    tmpRandomAccessFile.writeBytes(title);
                    tmpRandomAccessFile.writeBytes("\n");
                }
            }
            randomAccessFile.seek(0);
            tmpRandomAccessFile.seek(0);
            while (tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
            {
                randomAccessFile.writeBytes(tmpRandomAccessFile.readLine());
                randomAccessFile.writeBytes("\n");
            }
            randomAccessFile.setLength(tmpRandomAccessFile.length());
            tmpRandomAccessFile.setLength(0);
            randomAccessFile.close();
            tmpRandomAccessFile.close();
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public void delete (int code) throws DAOException
    {
        if (code<=0)
        {
            throw new DAOException("Invalid Code : "+code);
        }
        try
        {
            File file=new File(FILE_NAME);
            if(!file.exists())
            {
                throw new DAOException("Invalid Code : "+code);
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                randomAccessFile.close();
                throw new DAOException("Invalid Code : "+code);
            }
            EmployeeDAOInterface employeeDAOInterface=new EmployeeDAO();
            if(employeeDAOInterface.isDesignationAlloted(code))
            {
                throw new DAOException("Cannot Delete Designation Alloted");
            }
            int fCode;
            String fTitle;
            randomAccessFile.readLine();
            int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
            boolean found=false;
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                fCode=Integer.parseInt(randomAccessFile.readLine());
                if(fCode==code)
                {
                    found=true;
                    break;
                }
                randomAccessFile.readLine();
            }
            if (found==false)
            {
                throw new DAOException("Invalid Code : "+code);
            }
            File tmpFile=new File("tmp.data");
            if(tmpFile.exists())
            {
                tmpFile.delete();
            }
            RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
            randomAccessFile.seek(0);
            tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
            tmpRandomAccessFile.writeBytes("\n");
            tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
            tmpRandomAccessFile.writeBytes("\n");
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                fCode=Integer.parseInt(randomAccessFile.readLine());
                fTitle=randomAccessFile.readLine();
                if (code!=fCode)
                {
                    tmpRandomAccessFile.writeBytes(String.valueOf(fCode));
                    tmpRandomAccessFile.writeBytes("\n");
                    tmpRandomAccessFile.writeBytes(fTitle);
                    tmpRandomAccessFile.writeBytes("\n");
                }
            }
            randomAccessFile.seek(0);
            tmpRandomAccessFile.seek(0);
            randomAccessFile.writeBytes(tmpRandomAccessFile.readLine());
            randomAccessFile.writeBytes("\n");
            tmpRandomAccessFile.readLine();
            String recordCountString=String.valueOf(recordCount-1);
            while (recordCountString.length()<10)
            {
                recordCountString+=" ";
            }
            randomAccessFile.writeBytes(recordCountString);
            randomAccessFile.writeBytes("\n");
            while (tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
            {
                randomAccessFile.writeBytes(tmpRandomAccessFile.readLine());
                randomAccessFile.writeBytes("\n");
            }
            randomAccessFile.setLength(tmpRandomAccessFile.length());
            tmpRandomAccessFile.setLength(0);
            randomAccessFile.close();
            tmpRandomAccessFile.close();
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public Set<com.chayan.mulewa.hr.dl.interfaces.dto.DesignationDTOInterface> getAll() throws DAOException
    {
        Set<DesignationDTOInterface> designations=new TreeSet<>();
        try
        {
            File file=new File(FILE_NAME);
            if (!file.exists())
            {
                return designations;
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                randomAccessFile.close();
                return designations;
            }
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                DesignationDTOInterface designationDTO=new DesignationDTO();
                designationDTO.setCode(Integer.parseInt(randomAccessFile.readLine()));
                designationDTO.setTitle(randomAccessFile.readLine());
                designations.add(designationDTO);
            }
            randomAccessFile.close();
            return designations;
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public com.chayan.mulewa.hr.dl.interfaces.dto.DesignationDTOInterface getByCode(int code) throws DAOException
    {
        if (code<=0)
        {
            throw new DAOException("Invalid Code : "+code);
        }
        try
        {
            File file=new File(FILE_NAME);
            if (!file.exists())
            {
                throw new DAOException("Invalid Code : "+code);
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                randomAccessFile.close();
                throw new DAOException("Invalid Code : "+code);
            }
            int fCode;
            String fTitle;
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            while (randomAccessFile.getFilePointer()< randomAccessFile.length())
            {
                fCode=Integer.parseInt(randomAccessFile.readLine());
                fTitle=randomAccessFile.readLine();
                if (fCode==code)
                {
                    randomAccessFile.close();
                    DesignationDTOInterface designationDTO=new DesignationDTO();
                    designationDTO.setCode(fCode);
                    designationDTO.setTitle(fTitle);
                    return designationDTO;
                }
            }
            randomAccessFile.close();
            throw new DAOException("Invalid Code : "+code);
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public com.chayan.mulewa.hr.dl.interfaces.dto.DesignationDTOInterface getByTitle(String title) throws DAOException
    {
        if (title==null || title.trim().length()==0)
        {
            throw new DAOException("Invalid Title : "+title);
        }
        try
        {
            File file=new File(FILE_NAME);
            if (!file.exists())
            {
                throw new DAOException("Invalid Title : "+title);
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                randomAccessFile.close();
                throw new DAOException("Invalid Title : "+title);
            }
            int fCode;
            String fTitle;
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            while (randomAccessFile.getFilePointer()< randomAccessFile.length())
            {
                fCode=Integer.parseInt(randomAccessFile.readLine());
                fTitle=randomAccessFile.readLine();
                if (title.equalsIgnoreCase(fTitle))
                {
                    randomAccessFile.close();
                    DesignationDTOInterface designationDTO=new DesignationDTO();
                    designationDTO.setCode(fCode);
                    designationDTO.setTitle(fTitle);
                    return designationDTO;
                }
            }
            randomAccessFile.close();
            throw new DAOException("Invalid Title : "+title);
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public boolean codeExists(int code) throws DAOException
    {
        if (code<=0)
        {
         return false;
        }
        try
        {
            File file=new File(FILE_NAME);
            if (!file.exists())
            {
                return false;
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                randomAccessFile.close();
                return false;
            }
            int fCode=0;
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            while (randomAccessFile.getFilePointer()< randomAccessFile.length())
            {
                fCode=Integer.parseInt(randomAccessFile.readLine());
                if (fCode==code)
                {
                    randomAccessFile.close();
                    return true;
                }
                randomAccessFile.readLine();
            }
            if (fCode!=code)
            {
                return false;
            }
            randomAccessFile.close();
            throw new DAOException("Invalid Code : "+code);
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public boolean titleExists(String title) throws DAOException
    {
        if (title==null || title.trim().length()==0)
        {
            return false;
        }
        try
        {
            File file=new File(FILE_NAME);
            if (!file.exists())
            {
                return false;
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                randomAccessFile.close();
                return false;
            }
            String fTitle;
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            while (randomAccessFile.getFilePointer()< randomAccessFile.length())
            {
                randomAccessFile.readLine();
                fTitle=randomAccessFile.readLine();
                if (title.equalsIgnoreCase(fTitle))
                {
                    randomAccessFile.close();
                    return true;
                }
            }
            randomAccessFile.close();
          return false;
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public int getCount () throws DAOException
    {
        try
        {
            File file=new File(FILE_NAME);
            if (!file.exists())
            {
                return 0;
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                return 0;
            }
            randomAccessFile.readLine();
            int recordCount=0;
            recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
            randomAccessFile.close();
            return recordCount;
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
}