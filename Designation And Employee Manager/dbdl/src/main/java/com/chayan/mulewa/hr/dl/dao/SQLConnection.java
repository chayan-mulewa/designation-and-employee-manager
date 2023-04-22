package com.chayan.mulewa.hr.dl.dao;
import com.chayan.mulewa.hr.dl.exception.*;
import java.sql.*;
public class SQLConnection
{
    private SQLConnection()
    {

    }
    public static Connection getConnection() throws DAOException
    {
        Connection connection=null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/hrdb","hr","hr");
            
        }catch (Exception exception)
        {
            DAOException daoException=new DAOException(exception.getMessage());
            throw daoException;
        }
        return connection;
    }
}