import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.dao.*;
public class DesignationCodeExistsTestCase
{
    public static void main (String gg[])
    {
        int code=Integer.parseInt(gg[0]);
        try
        {
            DesignationDAOInterface designationDAO=new DesignationDAO();
            System.out.println("");
            System.out.println("******************************");
            System.out.println("");
            System.out.println("Code Exists : "+designationDAO.codeExists(code));
            System.out.println("");
            System.out.println("******************************");
        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}