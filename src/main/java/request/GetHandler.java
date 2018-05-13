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
import model.DonationCenter;
import model.DonationSchedule;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

public class GetHandler {

    public static List<DonationDTO> donationsHandler() {
        try {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            List<DonationDTO> donations = DTOutils.getDTOs(Donation.findAll());
            System.out.println("Donations size:" + donations.size());
            return donations;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Base.close();
        }
    }

    public static LazyList<Donor> donorsHandler() {
        try {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            LazyList<Donor> donors=Donor.findAll();
            System.out.println("Donors size:" + donors.size());
            return donors;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Base.close();
        }
    }


    public static LazyList<DonationCenter> donationCentersHandler(){
        try {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            LazyList<DonationCenter> donationCenters = DonationCenter.findAll();
            System.out.println("Donation Center size:"+donationCenters.size());
            for(DonationCenter dc : donationCenters){
                System.out.println(dc.getCenterName());
            }
            return donationCenters;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }
    }

    public static LazyList<DonationSchedule> donationSchedulesHandler(){
        try {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            LazyList<DonationSchedule> donationSchedules = DonationSchedule.findAll();
            System.out.println("Donation Schedule size: "+donationSchedules.size());
            return donationSchedules;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }
    }
}
