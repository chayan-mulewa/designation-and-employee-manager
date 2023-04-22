import com.chayan.mulewa.hr.bl.exceptions.*;
import com.chayan.mulewa.hr.bl.interfaces.manager.*;
import com.chayan.mulewa.hr.bl.interfaces.pojo.*;
import com.chayan.mulewa.hr.bl.pojo.*;
import com.chayan.mulewa.enums.*;
import com.chayan.mulewa.hr.bl.manager.*;
import java.math.BigDecimal;
import java.util.*;
import java.math.*;
import java.text.*;
class EmployeeManagerGetEmployeeByDesignationCode
{
    public static void main(String gg[]) 
    {
        
        try
        {
            int  designationCode=Integer.parseInt(gg[0]);
            EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
            Set<EmployeeInterface> employeeSet=employeeManager.getEmployeeByDesignationCode(designationCode);
            SimpleDateFormat simpleDateformat=new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("");
            System.out.println("**************************************************");
            for(EmployeeInterface employee:employeeSet)
            {
                System.out.println("");
                System.out.println(employee.getEmployeeID());
                System.out.println(employee.getName());
                System.out.println(employee.getDesignation().getCode());
                System.out.println(simpleDateformat.format(employee.getDateOfBirth()));
                System.out.println(employee.getGender());
                System.out.println(employee.getIsIndian());
                System.out.println(employee.getBasicSalary());
                System.out.println(employee.getPanNumber());
                System.out.println(employee.getAadharNumber());
                System.out.println("");
                System.out.println("**************************************************");
            }
        }catch(BLException blException)
        {
             if(blException.hasGenericException())
             {
                System.out.println(blException.getGenericException());
             }
             List<String> properties=blException.getProperties();
             for(String property:properties)
             {
                System.out.println(blException.getException(property));
             }
        }
    }
}