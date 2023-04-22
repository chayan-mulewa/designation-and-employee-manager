import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dto.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.dto.*;
import com.chayan.mulewa.hr.dl.dao.*;
public class DesignationAddTestCase
{
    public static void main (String gg[])
    {
        String title=gg[0];
        try
        {
            DesignationDTOInterface designationDTO=new DesignationDTO();
            designationDTO.setTitle(title);
            DesignationDAOInterface designationDAO=new DesignationDAO();
            designationDAO.add(designationDTO);
            System.out.println("");
            System.out.println("******************************");
            System.out.println("");
            System.out.println("Designation : "+title+" Added Code :"+designationDTO.getCode());
            System.out.println("");
            System.out.println("******************************");
        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}