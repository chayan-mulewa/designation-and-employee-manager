import com.chayan.mulewa.enums.GENDER;
import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.interfaces.dto.*;
import com.chayan.mulewa.hr.dl.dao.*;
import com.chayan.mulewa.hr.dl.dto.*;
import java.math.*;
import java.util.*;
import java.text.*;
public class EmployeeUpdateTestCase
{
    public static void main (String gg[])
    {
        String employeeId=gg[0];
        String name=gg[1];
        int designationCode=Integer.parseInt(gg[2]);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        Date dateOfBirth=null;
        try
        {
            dateOfBirth=simpleDateFormat.parse(gg[3]);
        }catch (ParseException parseException)
        {
            System.out.println(parseException.getMessage());
        }
        char gender=gg[4].charAt(0);
        boolean isIndian=Boolean.parseBoolean(gg[5]);
        BigDecimal basicSalary=new BigDecimal(gg[6]);
        String panNumber=gg[7];
        String aadharNumber=gg[8];
        try
        {
            EmployeeDTOInterface employeeDTO=new EmployeeDTO();
            employeeDTO.setEmployeeID(employeeId);
            employeeDTO.setName(name);
            employeeDTO.setDesignationCode(designationCode);
            employeeDTO.setDateOfBirth(dateOfBirth);
            if (gender=='M')
            {
                employeeDTO.setGender(GENDER.MALE);
            }
            if (gender=='F')
            {
                employeeDTO.setGender(GENDER.FEMALE);
            }
            employeeDTO.setIsIndian(isIndian);
            employeeDTO.setBasicSalary(basicSalary);
            employeeDTO.setPanNumber(panNumber);
            employeeDTO.setAadharNumber(aadharNumber);
            EmployeeDAOInterface employeeDAO=new EmployeeDAO();
            employeeDAO.update(employeeDTO);
            System.out.println("");
            System.out.println("**************************************************");
            System.out.println("");
            System.out.println("Employee Updated");
            System.out.println("");
            System.out.println("**************************************************");
        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}