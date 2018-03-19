import model.User;
import org.javalite.activejdbc.Base;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args){
        try {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            System.out.println("Connected!");
            User.deleteAll();
            User u1 = new User("1212451","Tom",LocalDate.of(1995,1,12),"tom@mail.com","0745678991","A",80);
            u1.saveIt();
            User u2 = new User("2314415","John",LocalDate.of(1978,2,3),"john@mail.com","0753111468","B",150);
            u2.saveIt();
            List<User> list = User.findAll();
            for(User u:list){
                System.out.println(u.getIdU()+" "+u.getName()+" "+u.getBirthday()+" "+u.getMail()+" "+u.getPhone()+" "+u.getBloodGroup()+" "+u.getWeight());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}
