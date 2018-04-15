package request;

import model.DonationCenter;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

public class GetHandlerDonationCenter {
    public static LazyList<DonationCenter> donationCentersHandler(){
        try {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            LazyList<DonationCenter> donationCenters = DonationCenter.findAll();
            System.out.println("Donation Center size:"+donationCenters.size());
            return donationCenters;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }
    }
}
