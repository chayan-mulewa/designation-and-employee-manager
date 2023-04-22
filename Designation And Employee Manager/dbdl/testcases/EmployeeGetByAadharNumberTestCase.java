import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dto.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.dao.*;
import java.text.*;
public class EmployeeGetByAadharNumberTestCase
{
    public static void main (String gg[])
    {
        String aadharNumber=gg[0];
        try
        {
            EmployeeDAOInterface employeeDAO=new  EmployeeDAO();
            EmployeeDTOInterface employeeDTO=employeeDAO.getByAadharNumber(aadharNumber);
            SimpleDateFormat simpleDateformat=new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("");
            System.out.println("**************************************************");
            System.out.println("");
            System.out.println("Employee ID : "+employeeDTO.getEmployeeID());
            System.out.println("Name : "+employeeDTO.getName());
            System.out.println("Designation : "+employeeDTO.getDesignationCode());
            System.out.println("Date Of Birth : "+simpleDateformat.format(employeeDTO.getDateOfBirth()));
            System.out.println("Geneder : "+employeeDTO.getGender());
            System.out.println("Is Indian : "+employeeDTO.getIsIndian());
            System.out.println("Basic Salary : "+employeeDTO.getBasicSalary());
            System.out.println("Pan Number : "+employeeDTO.getPanNumber());
            System.out.println("Aadhar Number : "+employeeDTO.getAadharNumber());
            System.out.println("");
            System.out.println("**************************************************");
        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}