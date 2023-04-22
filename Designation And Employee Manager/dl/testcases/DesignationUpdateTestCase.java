import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dto.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.dto.*;
import com.chayan.mulewa.hr.dl.dao.*;
public class DesignationUpdateTestCase
{
    public static void main (String gg[])
    {
        int code=Integer.parseInt(gg[0]);
        String title=gg[1];
        try
        {
            DesignationDTOInterface designationDTO=new DesignationDTO();
            designationDTO.setCode(code);
            designationDTO.setTitle(title);
            DesignationDAOInterface designationDAO=new DesignationDAO();
            designationDAO.update(designationDTO);
            System.out.println("");
            System.out.println("******************************");
            System.out.println("");
            System.out.println("Designation Updated");
            System.out.println("");
            System.out.println("******************************");
        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}