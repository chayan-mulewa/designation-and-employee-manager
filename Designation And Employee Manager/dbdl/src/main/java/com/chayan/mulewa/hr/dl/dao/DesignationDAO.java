package com.chayan.mulewa.hr.dl.dao;
import com.chayan.mulewa.hr.dl.dto.DesignationDTO;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.interfaces.dto.*;
import com.chayan.mulewa.hr.dl.exception.*;
import java.util.*;
import java.sql.*;
public class DesignationDAO implements DesignationDAOInterface
{
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
            Connection connection=SQLConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("select code from designation where title=?");
            preparedStatement.setString(1,title);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                resultSet.close();
                preparedStatement.close();
                connection.close();
                throw new DAOException("Designation : "+title+" Exists");
            }
            resultSet.close();
            preparedStatement.close();
            preparedStatement=connection.prepareStatement("insert into designation (title) values(?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,title);
            preparedStatement.executeUpdate();
            resultSet=preparedStatement.getGeneratedKeys();
            int code;
            resultSet.next();
            code=resultSet.getInt(1);
            resultSet.close();
            preparedStatement.close();
            connection.close();
            designationDTO.setCode(code);
           
        }catch (SQLException sqlException)
        {
            throw new DAOException("Designation Title : "+sqlException.getMessage()+" Exists");
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
            Connection connection=SQLConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("select code from designation where title=? and code!=?");
            preparedStatement.setString(1,title);
            preparedStatement.setInt(2,code);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()==false)
            {
                resultSet.close();
                preparedStatement.close();
                connection.close();
                throw new DAOException("Code :  "+title+"Does Not Exists");
            }
            if(resultSet.next())
            {
                resultSet.close();
                preparedStatement.close();
                connection.close();
                throw new DAOException("Designation :  "+title+" Exists");
            }
            resultSet.close();
            preparedStatement.close();
            preparedStatement=connection.prepareStatement("Update code from designation where title=? and code!=?");
            preparedStatement.setString(1,title);
            preparedStatement.setInt(2,code);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();               
        }catch (SQLException sqlException)
        {
            throw new DAOException(sqlException.getMessage());
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
            Connection connection=SQLConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("select code from designation where code=?");
            preparedStatement.setInt(1,code);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()==false)
            {
                resultSet.close();
                preparedStatement.close();
                connection.close();
                throw new DAOException("Code :  "+code+"Does Not Exists");
            }
            resultSet.close();
            preparedStatement.close();
            preparedStatement=connection.prepareStatement("delete from designation where code=?");
            preparedStatement.setInt(1,code);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();               
        }catch (SQLException sqlException)
        {
            throw new DAOException(sqlException.getMessage());
        }
    }
    public Set<com.chayan.mulewa.hr.dl.interfaces.dto.DesignationDTOInterface> getAll() throws DAOException
    {
        Set<DesignationDTOInterface> designations=new TreeSet<>();
        try
        {
            Connection connection=SQLConnection.getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select * from designation ");
            while(resultSet.next())
            {
                DesignationDTOInterface designationDTO=new DesignationDTO();
                designationDTO.setCode(resultSet.getInt("code"));
                designationDTO.setTitle(resultSet.getString("title"));
                designations.add(designationDTO);   
            }
            resultSet.close();
            statement.close();
            connection.close();
            return designations;
        }catch (SQLException sqlException)
        {
            throw new DAOException(sqlException.getMessage());
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
            Connection connection=SQLConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("select * from designation where code=?");
            preparedStatement.setInt(1,code);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()==false)
            {
                resultSet.close();
                preparedStatement.close();
                connection.close();
                throw new DAOException("Code :  "+code+"Does Not Exists");
            }
            DesignationDTOInterface designationDTO=new DesignationDTO();
            designationDTO.setCode(code);
            designationDTO.setTitle(resultSet.getString("titel"));
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return designationDTO;
        }catch (SQLException sqlException)
        {
            throw new DAOException(sqlException.getMessage());
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
            Connection connection=SQLConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("select * from designation where code=?");
            preparedStatement.setString(1,title);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()==false)
            {
                resultSet.close();
                preparedStatement.close();
                connection.close();
                throw new DAOException("Designation :  "+title+"Does Not Exists");
            }
            DesignationDTOInterface designationDTO=new DesignationDTO();
            designationDTO.setCode(resultSet.getInt("code"));
            designationDTO.setTitle(resultSet.getString("titel"));
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return designationDTO;
        }catch (SQLException sqlException)
        {
            throw new DAOException(sqlException.getMessage());
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
            boolean exists;
            Connection connection=SQLConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("select code from designation where code=?");
            preparedStatement.setInt(1,code);
            ResultSet resultSet=preparedStatement.executeQuery();
            exists=resultSet.next();
            resultSet.close();
            preparedStatement.close();
            connection.close();    
            return false;
           
        }catch (SQLException sqlException)
        {
            throw new DAOException(sqlException.getMessage());
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
            boolean exists;
            Connection connection=SQLConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("select code from designation where title=?");
            preparedStatement.setString(1,title);
            ResultSet resultSet=preparedStatement.executeQuery();
            exists=resultSet.next();
            resultSet.close();
            preparedStatement.close();
            connection.close();    
            return false;
           
        }catch (SQLException sqlException)
        {
            throw new DAOException(sqlException.getMessage());
        }
    }
    public int getCount () throws DAOException
    {
        try
        {
            Connection connection=SQLConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("select count(*) as cnt from designation");
            ResultSet resultSet=preparedStatement.executeQuery();
            resultSet.next();
            int count=resultSet.getInt("count");
            resultSet.close();
            preparedStatement.close();
            connection.close();    
            return count;
        }catch (SQLException sqlException)
        {
            throw new DAOException(sqlException.getMessage());
        }
    }
}