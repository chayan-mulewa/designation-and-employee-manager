import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.dao.*;
public class EmployeeDeleteTestCase
{
    public static void main (String gg[])
    {
        String employeeId=gg[0];
        try
        {
            EmployeeDAOInterface employeeDAO=new  EmployeeDAO();
            employeeDAO.delete(employeeId);
            System.out.println("");
            System.out.println("**************************************************");
            System.out.println("");
            System.out.println("Employee Deleted");
            System.out.println("");
            System.out.println("**************************************************");
        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}