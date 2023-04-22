import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.dao.*;
public class EmployeeGetCountByDesignationCodeTestCase
{
    public static void main (String gg[])
    {
        int designationCode=Integer.parseInt(gg[0]);
        try
        {
            EmployeeDAOInterface employeeDAO=new  EmployeeDAO();
            System.out.println("");
            System.out.println("**************************************************");
            System.out.println("Employee Count By Designation Code : "+employeeDAO.getCountByDesignationCode(designationCode));
            System.out.println("**************************************************");
        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}
