import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.dao.*;
public class DesignationTitleExistsTestCase
{
    public static void main (String gg[])
    {
        String title=gg[0];
        try
        {
            DesignationDAOInterface designationDAO=new DesignationDAO();
            System.out.println("");
            System.out.println("******************************");
            System.out.println("");
            System.out.println("Title Exists : "+designationDAO.titleExists(title));
            System.out.println("");
            System.out.println("******************************");
        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}