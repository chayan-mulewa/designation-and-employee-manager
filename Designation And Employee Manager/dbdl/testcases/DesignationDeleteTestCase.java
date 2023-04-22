import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.dao.*;
public class DesignationDeleteTestCase
{
    public static void main (String gg[])
    {
        int code=Integer.parseInt(gg[0]);
        try
        {
            DesignationDAOInterface designationDAO=new DesignationDAO();
            designationDAO.delete(code);
            System.out.println("");
            System.out.println("******************************");
            System.out.println("");
            System.out.println("Designation Deleted");
            System.out.println("");
            System.out.println("******************************");

        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}