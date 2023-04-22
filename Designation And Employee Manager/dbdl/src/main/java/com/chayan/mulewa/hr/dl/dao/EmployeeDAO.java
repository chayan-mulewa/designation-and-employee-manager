package com.chayan.mulewa.hr.dl.dao;
import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.interfaces.dto.*;
import com.chayan.mulewa.hr.dl.dto.*;
import com.chayan.mulewa.hr.dl.dto.*;
import com.chayan.mulewa.enums.*;
import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
    private static final String FILE_NAME="employee.data";
    public void add (com.chayan.mulewa.hr.dl.interfaces.dto.EmployeeDTOInterface employeeDTO) throws DAOException
    {
        if (employeeDTO==null)
        {
            throw new DAOException("Invalid Designation Code");
        }
        String employeeId;
        String name=employeeDTO.getName();
        if (name==null || name.trim().length()==0)
        {
            throw new DAOException("Length Of Name Is Null");
        }
        int designationCode=employeeDTO.getDesignationCode();
        if (designationCode<=0)
        {
            throw new DAOException("Invalid Designation Code");
        }
        DesignationDAOInterface designationDAO=new DesignationDAO();
        boolean isDesignationCodeValid=designationDAO.codeExists(designationCode);
        if (isDesignationCodeValid==false)
        {
            throw new DAOException("Invalid Designation Code");
        }
        Date dateOfBirth=employeeDTO.getDateOfBirth();
        if (dateOfBirth==null)
        {
            throw new DAOException("Date OF Birth Is Null");
        }
        char gender=employeeDTO.getGender();
        boolean isIndian=employeeDTO.getIsIndian();
        BigDecimal basicSalary=employeeDTO.getBasicSalary();
        if (basicSalary==null)
        {
            throw new DAOException("Basic Salary Is Null");
        }
        if (basicSalary.signum()==-1)
        {
            throw new DAOException("Basic Salary Is Null");
        }
        String panNumber=employeeDTO.getPanNumber();
        if (panNumber==null || panNumber.length()==0)
        {
            throw new DAOException("Invalid Pan Number");
        }
        String aadharNumber=employeeDTO.getAadharNumber();
        if (aadharNumber==null || aadharNumber.trim().length()==0)
        {
            throw new DAOException("Invalid Aadhar Number");
        }
        try
        {
            int lastGeneratedEmployeeId=0;
            int recordCount=0;
            String lastGeneratedEmployeeIdString="";
            String recordCountString="";
            File file=new File(FILE_NAME);
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                lastGeneratedEmployeeIdString=String.format("%-10s","0");
                randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
                recordCountString=String.format("%-10s","0");
                randomAccessFile.writeBytes(recordCountString+"\n");
            }
            else
            {
                lastGeneratedEmployeeId=Integer.parseInt(randomAccessFile.readLine().trim());
                recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
            }
            boolean panNumberExists,aadharNumberExists;
            panNumberExists=false;
            aadharNumberExists=false;
            String fPanNumber;
            String fAadharNumber;
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                for (int x=0 ; x<=6 ; x++)
                {
                    randomAccessFile.readLine();
                }
                fPanNumber = randomAccessFile.readLine();
                fAadharNumber = randomAccessFile.readLine();
                if (panNumberExists == false && fPanNumber.equalsIgnoreCase(panNumber))
                {
                    panNumberExists = true;
                }
                if (aadharNumberExists == false && fAadharNumber.equalsIgnoreCase(aadharNumber))
                {
                    aadharNumberExists = true;
                }
                if (panNumberExists && aadharNumberExists)
                {
                    break;
                }
            }
            if (panNumberExists && aadharNumberExists)
                {
                    randomAccessFile.close();
                    throw new DAOException("Pan Number : "+panNumber+" Exists and Aadhar Number : "+aadharNumber+" Exists");
                }
                if (panNumberExists)
                {
                    randomAccessFile.close();
                    throw new DAOException("Pan Number : "+panNumber+" Exists");
                }
                if (aadharNumberExists)
                {
                    randomAccessFile.close();
                    throw new DAOException("Aadhar Number : "+aadharNumber+" Exists");
                }
                lastGeneratedEmployeeId++;
                recordCount++;
                employeeId="A"+lastGeneratedEmployeeId;
                randomAccessFile.writeBytes(employeeId+"\n");
                randomAccessFile.writeBytes(name+"\n");
                randomAccessFile.writeBytes(designationCode+"\n");
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
                randomAccessFile.writeBytes(gender+"\n");
                randomAccessFile.writeBytes(isIndian+"\n");
                randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
                randomAccessFile.writeBytes(panNumber+"\n");
                randomAccessFile.writeBytes(aadharNumber+"\n");
                randomAccessFile.seek(0);
                lastGeneratedEmployeeIdString=String.format("%-10d",lastGeneratedEmployeeId);
                randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
                recordCountString=String.format("%-10d",recordCount);
                randomAccessFile.writeBytes(recordCountString+"\n");
                randomAccessFile.close();
                employeeDTO.setEmployeeID(employeeId);
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public void update (com.chayan.mulewa.hr.dl.interfaces.dto.EmployeeDTOInterface employeeDTO) throws DAOException
    {
        if (employeeDTO==null)
        {
            throw new DAOException("Invalid Designation Code");
        }
        String employeeId=employeeDTO.getEmployeeID();
        if (employeeId==null && employeeId.trim().length()==0)
        {
            throw new DAOException("Invalid Employee Id 1:  "+employeeId);
        }
        String name=employeeDTO.getName();
        if (name==null || name.trim().length()==0)
        {
            throw new DAOException("Length Of Name Is Null");
        }
        int designationCode=employeeDTO.getDesignationCode();
        if (designationCode<=0)
        {
            throw new DAOException("Invalid Designation Code");
        }
        DesignationDAOInterface designationDAO=new DesignationDAO();
        boolean isDesignationCodeValid=designationDAO.codeExists(designationCode);
        if (isDesignationCodeValid==false)
        {
            throw new DAOException("Invalid Designation Code");
        }
        Date dateOfBirth=employeeDTO.getDateOfBirth();
        if (dateOfBirth==null)
        {
            throw new DAOException("Date OF Birth Is Null");
        }
        char gender=employeeDTO.getGender();
        boolean isIndian=employeeDTO.getIsIndian();
        BigDecimal basicSalary=employeeDTO.getBasicSalary();
        if (basicSalary==null)
        {
            throw new DAOException("Basic Salary Is Null");
        }
        if (basicSalary.signum()==-1)
        {
            throw new DAOException("Basic Salary Is Null");
        }
        String panNumber=employeeDTO.getPanNumber();
        if (panNumber==null || panNumber.length()==0)
        {
            throw new DAOException("Invalid Pan Number");
        }
        String aadharNumber=employeeDTO.getAadharNumber();
        if (aadharNumber==null || aadharNumber.trim().length()==0)
        {
            throw new DAOException("Invalid Aadhar Number");
        }
        try
        {
            File file=new File(FILE_NAME);
            if (!file.exists())
            {
                throw new DAOException("Invalid Employee Id 2:  "+employeeId);
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                throw new DAOException("Invalid Employee Id 3:  "+employeeId);
            }
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            String fEmployeeId;
            String fPanNumber;
            String fAadharNumber;
            boolean employeeIdFound=false;
            boolean panNumebrFound=false;
            boolean aadharNumberFound=false;
            String panNumberFoundAgainstEmployeeId="";
            String aadharNumberFoundAgainstEmployeeId="";
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            long foundAt=0;
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                if (employeeIdFound==false)
                {
                    foundAt=randomAccessFile.getFilePointer();
                }
                fEmployeeId=randomAccessFile.readLine();
                for (int x=1;x<=6;x++)
                {
                    randomAccessFile.readLine();
                }
                fPanNumber=randomAccessFile.readLine();
                fAadharNumber=randomAccessFile.readLine();
                if (employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId))
                {
                    employeeIdFound=true;
                }
                if (panNumebrFound==false && fPanNumber.equalsIgnoreCase(panNumber))
                {
                    panNumebrFound=true;
                    panNumberFoundAgainstEmployeeId=fEmployeeId;
                }
                if(aadharNumberFound==false && fAadharNumber.equalsIgnoreCase(aadharNumber))
                {
                    aadharNumberFound=true;
                    aadharNumberFoundAgainstEmployeeId=fEmployeeId;
                }
                if (employeeIdFound && panNumebrFound && aadharNumberFound)
                {
                    break;
                }
            }
            if (employeeIdFound==false)
            {
                randomAccessFile.close();
                throw new DAOException("Invalid Employee Id 4:  "+employeeId);
            }
            boolean panNumberExists=false;
            if (panNumebrFound && panNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
            {
                panNumberExists=true;
            }
            boolean aadharNumberExists=false;
            if (aadharNumberFound && aadharNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
            {
                aadharNumberExists=true;
            }
            if (panNumberExists && aadharNumberExists)
            {
                throw new DAOException("Invalid Pan Number :  "+panNumber+" And Invalid Aadhar Numebr : "+aadharNumber);
            }
            if (panNumberExists)
            {
                randomAccessFile.close();
                throw new DAOException("Invalid Pan Number :  "+panNumber);
            }
            if (aadharNumberExists)
            {
                randomAccessFile.close();
                throw new DAOException("Invalid Aadhar Numebr :  "+aadharNumber);
            }
            randomAccessFile.seek(foundAt);
            for (int x=1;x<=9;x++)
            {
                randomAccessFile.readLine();
            }
            File tmpFile=new File("tmp.data");
            if (file.exists())
            {
                tmpFile.delete();
            }
            RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
            }
            randomAccessFile.seek(foundAt);
            randomAccessFile.writeBytes(employeeId+"\n");
            randomAccessFile.writeBytes(name+"\n");
            randomAccessFile.writeBytes(designationCode+"\n");
            randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
            randomAccessFile.writeBytes(gender+"\n");
            randomAccessFile.writeBytes(isIndian+"\n");
            randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
            randomAccessFile.writeBytes(panNumber+"\n");
            randomAccessFile.writeBytes(aadharNumber+"\n");
            tmpRandomAccessFile.seek(0);
            while (tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
            {
                randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
            }
            randomAccessFile.setLength(randomAccessFile.getFilePointer());
            tmpRandomAccessFile.setLength(0);
            randomAccessFile.close();
            tmpRandomAccessFile.close();
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public void delete (String employeeId) throws DAOException
    {
        if (employeeId==null && employeeId.trim().length()==0)
        {
            throw new DAOException("Invalid Employee Id 1:  "+employeeId);
        }
        try
        {
            File file=new File(FILE_NAME);
            if (!file.exists())
            {
                throw new DAOException("Invalid Employee Id 2:  "+employeeId);
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                throw new DAOException("Invalid Employee Id 3:  "+employeeId);
            }
            randomAccessFile.readLine();
            int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
            String fEmployeeId;
            boolean employeeIdFound=false;
            long foundAt=0;
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                foundAt=randomAccessFile.getFilePointer();
                fEmployeeId=randomAccessFile.readLine();
                for (int x=1;x<=8;x++)
                {
                    randomAccessFile.readLine();
                }
                if (fEmployeeId.equalsIgnoreCase(employeeId))
                {
                    employeeIdFound=true;
                    break;
                }
            }
            if (employeeIdFound==false)
            {
                randomAccessFile.close();
                throw new DAOException("Invalid Employee Id 4:  "+employeeId);
            }
            File tmpFile=new File("tmp.data");
            if (file.exists())
            {
                tmpFile.delete();
            }
            RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
            }
            randomAccessFile.seek(foundAt);
            tmpRandomAccessFile.seek(0);
            while (tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
            {
                randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
            }
            randomAccessFile.setLength(randomAccessFile.getFilePointer());
            recordCount--;
            String recordCountString=String.format("%-10d",recordCount);
            randomAccessFile.seek(0);
            randomAccessFile.readLine();
            randomAccessFile.writeBytes(recordCountString+"\n");
            tmpRandomAccessFile.setLength(0);
            randomAccessFile.close();
            tmpRandomAccessFile.close();
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public Set<EmployeeDTOInterface> getAll() throws DAOException
    {
        Set<EmployeeDTOInterface> employees=new TreeSet<>();
        try
        {
            File file=new File(FILE_NAME);
            if (!file.exists())
            {
                return employees;
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                randomAccessFile.close();
                return employees;
            }
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            EmployeeDTOInterface employeeDTO;
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            char fGender;
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                employeeDTO=new EmployeeDTO();
                employeeDTO.setEmployeeID(randomAccessFile.readLine());
                employeeDTO.setName(randomAccessFile.readLine());
                employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
                try
                {
                    employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
                }catch (ParseException parseException)
                {
                    throw new DAOException(parseException.getMessage());
                }
                fGender=randomAccessFile.readLine().charAt(0);
                if (fGender=='M')
                {
                    employeeDTO.setGender(GENDER.MALE);
                }
                if (fGender=='F')
                {
                    employeeDTO.setGender(GENDER.FEMALE);
                }
                employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
                employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
                employeeDTO.setPanNumber(randomAccessFile.readLine());
                employeeDTO.setAadharNumber(randomAccessFile.readLine());
                employees.add(employeeDTO);
            }
            randomAccessFile.close();
            return employees;
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
    {
        if (new DesignationDAO().codeExists(designationCode)==false)
        {
            throw new DAOException("Invalid Desigantion Code : "+designationCode);
        }
        Set<EmployeeDTOInterface> employees=new TreeSet<>();
        try
        {
            File file=new File(FILE_NAME);
            if (!file.exists())
            {
                return employees;
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                randomAccessFile.close();
                return employees;
            }
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            String fEmployeeID;
            String fName;
            int fDesignationCode;
            EmployeeDTOInterface employeeDTO;
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            char fGender;
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                fEmployeeID= randomAccessFile.readLine();
                fName= randomAccessFile.readLine();
                fDesignationCode= Integer.parseInt(randomAccessFile.readLine());
                if (fDesignationCode==designationCode)
                {
                    employeeDTO=new EmployeeDTO();
                    employeeDTO.setEmployeeID(fEmployeeID);
                    employeeDTO.setName(fName);
                    employeeDTO.setDesignationCode(fDesignationCode);
                    try
                    {
                        employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
                    }catch (ParseException parseException)
                    {
                        throw new DAOException(parseException.getMessage());
                    }
                    fGender=randomAccessFile.readLine().charAt(0);
                    if (fGender=='M')
                    {
                        employeeDTO.setGender(GENDER.MALE);
                    }
                    if (fGender=='F')
                    {
                        employeeDTO.setGender(GENDER.FEMALE);
                    }
                    employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
                    employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
                    employeeDTO.setPanNumber(randomAccessFile.readLine());
                    employeeDTO.setAadharNumber(randomAccessFile.readLine());
                    employees.add(employeeDTO);
                    continue;
                }
                for (int x=1;x<=6;x++)
                {
                    randomAccessFile.readLine();
                }
            }
            randomAccessFile.close();
            return employees;
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public boolean isDesignationAlloted(int designationCode) throws DAOException
    {
        if (new DesignationDAO().codeExists(designationCode)==false)
        {
            throw new DAOException("Invalid Desigantion Code : "+designationCode);
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
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            int fDesignationCode;
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                randomAccessFile.readLine();
                randomAccessFile.readLine();
                fDesignationCode = Integer.parseInt(randomAccessFile.readLine());
                if (fDesignationCode == designationCode)
                {
                    randomAccessFile.close();
                    return true;
                }
                for (int x = 0; x <= 5; x++)
                {
                    randomAccessFile.readLine();
                    continue;
                }
            }
            randomAccessFile.close();
            return false;
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
    {
        if (employeeId==null || employeeId.trim().length()==0)
        {
            throw new DAOException("Invalid Employee ID Code : "+employeeId);
        }
        try
        {
            File file=new File(FILE_NAME);
            if (!file.exists())
            {
                throw new DAOException("Invalid Employee ID Code : "+employeeId);
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                randomAccessFile.close();
                throw new DAOException("Invalid Employee ID Code : "+employeeId);
            }
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            String fEmployeeID;
            EmployeeDTOInterface employeeDTO;
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                fEmployeeID=randomAccessFile.readLine();
                if (fEmployeeID.equalsIgnoreCase(employeeId))
                {
                    employeeDTO=new EmployeeDTO();
                    employeeDTO.setEmployeeID(fEmployeeID);
                    employeeDTO.setName(randomAccessFile.readLine());
                    employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
                    try
                    {
                        employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
                    }catch (ParseException parseException)
                    {
                        throw new DAOException(parseException.getMessage());
                    }
                    employeeDTO.setGender((randomAccessFile.readLine().charAt(0)=='M')?GENDER.MALE:GENDER.FEMALE);
                    employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
                    employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
                    employeeDTO.setPanNumber(randomAccessFile.readLine());
                    employeeDTO.setAadharNumber(randomAccessFile.readLine());
                    randomAccessFile.close();
                    return employeeDTO;
                }
                for (int x=1;x<=8;x++)
                {
                    randomAccessFile.readLine();
                }
            }
            randomAccessFile.close();
            throw new DAOException("Invalid Employee ID Code : "+employeeId);
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public EmployeeDTOInterface getByPanNumber(String panNumber) throws DAOException
    {
        if (panNumber==null || panNumber.trim().length()==0)
        {
            throw new DAOException("Invalid panNumber : "+panNumber);
        }
        try
        {
            File file=new File(FILE_NAME);
            if (!file.exists())
            {
                throw new DAOException("Invalid panNumber : "+panNumber);
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                randomAccessFile.close();
                throw new DAOException("Invalid panNumber : "+panNumber);
            }
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            String fEmployeeId;
            String fName;
            int fDesigantionCode;
            Date fDateOfBirth;
            char fGender;
            boolean fIsIndian;
            BigDecimal fBasicSalary;
            String fPanNumber;
            String fAadharNumber;
            EmployeeDTOInterface employeeDTO;
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                fEmployeeId=randomAccessFile.readLine();
                fName= randomAccessFile.readLine();
                fDesigantionCode=Integer.parseInt(randomAccessFile.readLine());
                fDateOfBirth=null;
                try
                {
                    fDateOfBirth=simpleDateFormat.parse(randomAccessFile.readLine());
                }catch (ParseException parseException)
                {
                    throw new DAOException(parseException.getMessage());
                }
                fGender=randomAccessFile.readLine().charAt(0);
                fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
                fBasicSalary=new BigDecimal(randomAccessFile.readLine());
                fPanNumber=randomAccessFile.readLine();
                fAadharNumber=randomAccessFile.readLine();
                if (fPanNumber.equalsIgnoreCase(panNumber))
                {
                    employeeDTO=new EmployeeDTO();
                    employeeDTO.setEmployeeID(fEmployeeId);
                    employeeDTO.setName(fName);
                    employeeDTO.setDesignationCode(fDesigantionCode);
                    employeeDTO.setDateOfBirth(fDateOfBirth);
                    employeeDTO.setGender((fGender=='M')?GENDER.MALE:GENDER.FEMALE);
                    employeeDTO.setIsIndian(fIsIndian);
                    employeeDTO.setBasicSalary(fBasicSalary);
                    employeeDTO.setPanNumber(fPanNumber);
                    employeeDTO.setAadharNumber(fAadharNumber);
                    randomAccessFile.close();
                    return employeeDTO;
                }
            }
            randomAccessFile.close();
            throw new DAOException("Invalid panNumber : "+panNumber);
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public EmployeeDTOInterface getByAadharNumber(String aadharNumber) throws DAOException
    {
        if (aadharNumber==null || aadharNumber.trim().length()==0)
        {
            throw new DAOException("Invalid Aadhar Number : "+aadharNumber);
        }
        try
        {
            File file=new File(FILE_NAME);
            if (!file.exists())
            {
                throw new DAOException("Invalid Aadhar Number : "+aadharNumber);
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                randomAccessFile.close();
                throw new DAOException("Invalid Aadhar Number : "+aadharNumber);
            }
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            String fEmployeeId;
            String fName;
            int fDesigantionCode;
            Date fDateOfBirth;
            char fGender;
            boolean fIsIndian;
            BigDecimal fBasicSalary;
            String fPanNumber;
            String fAadharNumber;
            EmployeeDTOInterface employeeDTO;
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                fEmployeeId=randomAccessFile.readLine();
                fName= randomAccessFile.readLine();
                fDesigantionCode=Integer.parseInt(randomAccessFile.readLine());
                fDateOfBirth=null;
                try
                {
                    fDateOfBirth=simpleDateFormat.parse(randomAccessFile.readLine());
                }catch (ParseException parseException)
                {
                    throw new DAOException(parseException.getMessage());
                }
                fGender=randomAccessFile.readLine().charAt(0);
                fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
                fBasicSalary=new BigDecimal(randomAccessFile.readLine());
                fPanNumber=randomAccessFile.readLine();
                fAadharNumber=randomAccessFile.readLine();
                if (fAadharNumber.equalsIgnoreCase(aadharNumber))
                {
                    employeeDTO=new EmployeeDTO();
                    employeeDTO.setEmployeeID(fEmployeeId);
                    employeeDTO.setName(fName);
                    employeeDTO.setDesignationCode(fDesigantionCode);
                    employeeDTO.setDateOfBirth(fDateOfBirth);
                    employeeDTO.setGender((fGender=='M')?GENDER.MALE:GENDER.FEMALE);
                    employeeDTO.setIsIndian(fIsIndian);
                    employeeDTO.setBasicSalary(fBasicSalary);
                    employeeDTO.setPanNumber(fPanNumber);
                    employeeDTO.setAadharNumber(fAadharNumber);
                    randomAccessFile.close();
                    return employeeDTO;
                }
            }
            randomAccessFile.close();
            throw new DAOException("Invalid Aadhar Number : "+aadharNumber);
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public boolean employeeIdExists(String  employeeId) throws DAOException
    {
        if (employeeId==null || employeeId.trim().length()==0)
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
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            String fEmployeeID;
            EmployeeDTOInterface employeeDTO;
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                fEmployeeID=randomAccessFile.readLine();
                if (fEmployeeID.equalsIgnoreCase(employeeId))
                {
                    randomAccessFile.close();
                    return true;
                }
                for (int x=0;x<=7;x++)
                {
                    randomAccessFile.readLine();
                }
            }
            randomAccessFile.close();
            return false;
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public boolean panNumberExists(String  panNumber) throws DAOException
    {
        if (panNumber==null || panNumber.trim().length()==0)
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
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            String fPanNumber;
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                for (int x=0;x<=6;x++)
                {
                    randomAccessFile.readLine();
                }
                fPanNumber= randomAccessFile.readLine();
                if (fPanNumber.equalsIgnoreCase(panNumber))
                {
                    randomAccessFile.close();
                    return true;
                }
                randomAccessFile.readLine();
            }
            randomAccessFile.close();
            return false;
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
    public boolean aadharNumberExists(String aadharNumber) throws DAOException
    {
        if (aadharNumber==null || aadharNumber.trim().length()==0)
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
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            String fAadharNumber;
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                for (int x=0;x<=7;x++)
                {
                    randomAccessFile.readLine();
                }
                fAadharNumber= randomAccessFile.readLine();
                if (fAadharNumber.equalsIgnoreCase(aadharNumber))
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
               randomAccessFile.close();
               return 0;
           }
           randomAccessFile.readLine();
           int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
           randomAccessFile.close();
           return recordCount;
       }catch (IOException ioException)
       {
           throw new DAOException(ioException.getMessage());
       }
    }
    public int getCountByDesignationCode(int designationCode) throws DAOException
    {
        try
        {
            if (new DesignationDAO().codeExists(designationCode)==false)
            {
                throw new DAOException("Invalid Designation Code : "+designationCode);
            }
            File file=new File(FILE_NAME);
            if (!file.exists())
            {
                return 0;
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
            if (randomAccessFile.length()==0)
            {
                randomAccessFile.close();
                return 0;
            }
            randomAccessFile.readLine();
            randomAccessFile.readLine();
            int fDesignationCode=0;
            int count=0;
            while (randomAccessFile.getFilePointer()<randomAccessFile.length())
            {
                randomAccessFile.readLine();
                randomAccessFile.readLine();
                fDesignationCode= Integer.parseInt(randomAccessFile.readLine());
                if (fDesignationCode==designationCode)
                {
                    count++;
                }
                for (int x=1;x<=6;x++)
                {
                    randomAccessFile.readLine();
                }
            }
            randomAccessFile.close();
            return count;
        }catch (IOException ioException)
        {
            throw new DAOException(ioException.getMessage());
        }
    }
}