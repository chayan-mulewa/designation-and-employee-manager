import com.chayan.mulewa.hr.bl.exceptions.*;
import com.chayan.mulewa.hr.bl.interfaces.manager.*;
import com.chayan.mulewa.hr.bl.interfaces.pojo.*;
import com.chayan.mulewa.hr.bl.pojo.*;
import com.chayan.mulewa.hr.bl.manager.*;
import java.util.*;
class DesignationManagerGetDesignationTestCase
{
    public static void main(String gg[]) 
    {
        try 
        {
            DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
            Set<DesignationInterface> designations;
            designations=designationManager.getDesignations();
            System.out.println("");
            System.out.println("**************************************************");
            designations.forEach((designation)->{
            System.out.println("");
            System.out.println("Code : "+designation.getCode()+" And Title : "+designation.getTitle());
            System.out.println("**************************************************");    
            });

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