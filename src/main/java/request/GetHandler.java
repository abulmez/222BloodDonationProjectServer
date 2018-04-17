package request;

import model.Donation;
import model.Donor;
import model.UserLoginData;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import utils.DTOutils;
import utils.DonationDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class GetHandler {
    public static List<DonationDTO> donationsHandler(){
        try{
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            List<DonationDTO> donations=DTOutils.getDTOs(Donation.findAll());
            System.out.println("Donations size:"+donations.size());
            return donations;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }

    }
}
