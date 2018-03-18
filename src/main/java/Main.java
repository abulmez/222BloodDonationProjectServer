import org.javalite.activejdbc.Base;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        System.out.println("merge");
        try {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            System.out.println("S-a conectat cu succes!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
