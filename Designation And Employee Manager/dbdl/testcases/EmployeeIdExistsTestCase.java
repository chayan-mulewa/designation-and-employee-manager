import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.dao.*;
public class EmployeeIdExistsTestCase
{
    public static void main (String gg[])
    {
        String employeeId=gg[0];
        try
        {
            EmployeeDAOInterface employeeDAO=new  EmployeeDAO();
            System.out.println("");
            System.out.println("**************************************************");
            System.out.println("");
            System.out.println("Employee ID : "+employeeDAO.employeeIdExists(employeeId));
            System.out.println("");
            System.out.println("**************************************************");
        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}
