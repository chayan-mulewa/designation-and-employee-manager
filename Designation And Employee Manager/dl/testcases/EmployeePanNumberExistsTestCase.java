import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.dao.*;
public class EmployeePanNumberExistsTestCase
{
    public static void main (String gg[])
    {
        String panNumber=gg[0];
        try
        {
            EmployeeDAOInterface employeeDAO=new EmployeeDAO();
            System.out.println("");
            System.out.println("**************************************************");
            System.out.println("");
            System.out.println("Pan Number Exists : "+employeeDAO.panNumberExists(panNumber));
            System.out.println("");
            System.out.println("**************************************************");
        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}