import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.dao.*;
public class EmployeeAadharNumberExistsTestCase
{
    public static void main (String gg[])
    {
        String aadharNumber=gg[0];
        try
        {
            EmployeeDAOInterface employeeDAO=new EmployeeDAO();
            System.out.println("");
            System.out.println("**************************************************");
            System.out.println("");
            System.out.println("Pan Number Exists : "+employeeDAO.aadharNumberExists(aadharNumber));
            System.out.println("");
            System.out.println("**************************************************");
        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}