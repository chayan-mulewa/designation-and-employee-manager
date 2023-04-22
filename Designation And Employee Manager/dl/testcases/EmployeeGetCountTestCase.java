import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.dao.*;
public class EmployeeGetCountTestCase
{
    public static void main (String gg[])
    {
        try
        {
            EmployeeDAOInterface employeeDAO=new  EmployeeDAO();
            System.out.println("");
            System.out.println("**************************************************");
            System.out.println("");
            System.out.println("Employee Count : "+employeeDAO.getCount());
            System.out.println("");
            System.out.println("**************************************************");
        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}
