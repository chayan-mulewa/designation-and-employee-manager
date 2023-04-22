import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.dao.*;
public class DesignationGetCountTestCase
{
    public static void main (String gg[])
    {
        try
        {
            DesignationDAOInterface designationDAO=new DesignationDAO();
            System.out.println("");
            System.out.println("******************************");
            System.out.println("");
            System.out.println("Designation Count : "+designationDAO.getCount());
            System.out.println("");
            System.out.println("******************************");
        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}