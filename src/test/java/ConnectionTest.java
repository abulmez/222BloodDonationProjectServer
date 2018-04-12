import junit.framework.TestCase;
import model.Donor;
import model.User;
import org.javalite.activejdbc.Base;

import java.time.LocalDate;
import java.util.List;

public class ConnectionTest extends TestCase {
    public void setUp(){
        Base.open(
                "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
        System.out.println("Connected!");
    }

    public void testUp(){
        User.deleteAll();
        User u1 = new Donor("1212451","Tom", LocalDate.of(1995,1,12),"tom@mail.com","0745678991","A",(float)80);
        u1.saveIt();
        User u2 = new Donor("2314415","John",LocalDate.of(1978,2,3),"john@mail.com","0753111468","B",(float)150.6);
        u2.saveIt();
        List<Donor> list = Donor.findAll();
        for(Donor u:list){
            System.out.println(u.getIdU()+" "+u.getName()+" "+u.getBirthday()+" "+u.getMail()+" "+u.getPhone()+" "+u.getBloodGroup()+" "+u.getWeight());
        }
    }
}
