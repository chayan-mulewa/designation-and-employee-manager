import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dto.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.dto.*;
import com.chayan.mulewa.hr.dl.dao.*;
public class DesignationGetByTitle
{
    public static void main (String gg[])
    {
        String title=gg[0];
        try
        {
            DesignationDTOInterface designationDTO;
            DesignationDAOInterface designationDAO=new DesignationDAO();
            designationDTO=designationDAO.getByTitle(title);
            System.out.println("");
            System.out.println("******************************");
            System.out.println("");
            System.out.println("Code : "+designationDTO.getCode());
            System.out.println("Title : "+designationDTO.getTitle());
            System.out.println("");
            System.out.println("******************************");
        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}