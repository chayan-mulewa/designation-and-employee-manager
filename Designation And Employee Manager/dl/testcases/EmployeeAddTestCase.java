import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.chayan.mulewa.hr.dl.dao.*;
import com.chayan.mulewa.hr.dl.dto.EmployeeDTO;
import com.chayan.mulewa.enums.*;
import com.chayan.mulewa.hr.dl.exception.*;
import com.chayan.mulewa.hr.dl.interfaces.dao.*;
import com.chayan.mulewa.hr.dl.interfaces.dto.*;
public class EmployeeAddTestCase
{
    public static void main (String gg[])
    {
        String name=gg[0];
        int designationCode=Integer.parseInt(gg[1]);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        Date dateOfBirth=null;
        try
        {
            dateOfBirth=simpleDateFormat.parse(gg[2]);
        }catch (ParseException parseException)
        {
            System.out.println(parseException.getMessage());
        }
        char gender=gg[3].charAt(0);
        boolean isIndian=Boolean.parseBoolean(gg[4]);
        BigDecimal basicSalary=new BigDecimal(gg[5]);
        String panNumber=gg[6];
        String aadharNumber=gg[7];
        try
        {
            EmployeeDTOInterface employeeDTO=new EmployeeDTO();
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
            employeeDAO.add(employeeDTO);
            System.out.println("");
            System.out.println("**************************************************");
            System.out.println("");
            System.out.println("Employee Added With Employee ID : "+employeeDTO.getEmployeeID());
            System.out.println("");
            System.out.println("**************************************************");
        }catch (DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}