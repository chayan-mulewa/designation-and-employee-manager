import com.chayan.mulewa.hr.bl.exceptions.*;
import com.chayan.mulewa.hr.bl.interfaces.manager.*;
import com.chayan.mulewa.hr.bl.interfaces.pojo.*;
import com.chayan.mulewa.hr.bl.pojo.*;
import com.chayan.mulewa.hr.bl.manager.*;
import java.util.*;
class DesignationManagerAddTestCase
{
    public static void main(String gg[]) 
    {
        DesignationInterface designation=new Designation();
        String title=gg[0];
        designation.setTitle(title);
        try
        {
            DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
            designationManager.addDesignation(designation);
            System.out.println("");
            System.out.println("**************************************************");
            System.out.println("");
            System.out.println("Designation : "+designation.getTitle()+" Added With Code : "+designation.getCode());
            System.out.println("**************************************************");
            System.out.println("");
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