import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dto.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.dto.*;
import com.chayan.mulewa.hr.dl.dao.*;
import java.util.*;
public class DesignationGetAllTestCase
{
    public static void main (String gg[])
    {
        try
        {
            DesignationDAOInterface designationDAO=new  DesignationDAO();
            Set<DesignationDTOInterface> designations=designationDAO.getAll();
            System.out.println("");
            System.out.println("******************************");
            designations.forEach((designationDTO)->{
                System.out.println("");
                System.out.println("Code : "+designationDTO.getCode()+" And Title : "+designationDTO.getTitle());
                System.out.println("");
                System.out.println("******************************");
                
            });
            System.out.println();

        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}