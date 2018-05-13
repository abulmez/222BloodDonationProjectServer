package request;

import com.google.gson.Gson;
import model.*;
import model.DTO.BloodRequestHospitalDTO;
import model.DTO.DonationReceiverNameBloodGroupDTO;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import utils.DTOutils;
import utils.DonationDTO;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

    public static String getAllAvailableBloodProducts(InputStream requestBody) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {

            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            Integer idU = Integer.parseInt(line.split("=")[1]);
            Integer idDC = (Integer)TCP.findById(idU).get("IdDC");
            String sqlQuerry = String.format("SELECT * FROM AvailableBloodProducts abp INNER JOIN Donation d on abp.IdD = d.IdD INNER JOIN DonationCenter dc on d.IdDC = dc.IdDC WHERE dc.IdDC = %s AND abp.Deleted = 0",idDC);
            LazyList<AvailableBloodProducts> products = AvailableBloodProducts.findBySQL(sqlQuerry);
            return products.toJson(false);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }
    }

    public static String getAllDonationReceiverNames(InputStream requestBody) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            ArrayList<DonationReceiverNameBloodGroupDTO> donationReceiverNameDTOArrayList = new ArrayList<>();
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            Integer idU = Integer.parseInt(line.split("=")[1]);
            Integer idDC = (Integer)TCP.findById(idU).get("IdDC");
            String sqlQuerry = String.format("SELECT * FROM Donation d INNER JOIN AvailableBloodProducts abp ON d.IdD = abp.IdD WHERE d.IdDC = %s",idDC);
            LazyList<Donation> donations = Donation.findBySQL(sqlQuerry);
            for(Donation d:donations){
                Donor donor = Donor.findById(d.getIdU());
                donationReceiverNameDTOArrayList.add(new DonationReceiverNameBloodGroupDTO(d.getIdD(),d.getReceiverName(),donor.getBloodGroup()));
            }
            Gson gson = new Gson();
            return gson.toJson(donationReceiverNameDTOArrayList);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }
    }

    public static String getAllBloodRequestsAndHospitalInfoForProductTypeAndGroup(InputStream requestBody) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            ArrayList<BloodRequestHospitalDTO> donationReceiverNameDTOArrayList = new ArrayList<>();
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String args[] = line.split("&");
            String productType = args[0].split("=")[1];
            String bloodGroup = args[1].split("=")[1];
            if(productType.equals("GlobuleRosii"))
                productType = "Globule Rosii";
            LazyList<BloodDemand> bloodDemands = BloodDemand.where("BloodProductType = ? AND NeededType = ?",productType, bloodGroup);
            for(BloodDemand bloodDemand:bloodDemands){
                Hospital hospital = Hospital.findById(bloodDemand.getIdH());
                Adress adress = Adress.findById(hospital.getIdA());
                String fomattedAdress = adress.getStreet()+" "+adress.getStreetNr()+" "+adress.getCity()+" "+adress.getCountry();
                donationReceiverNameDTOArrayList.add(new BloodRequestHospitalDTO((Integer) bloodDemand.getId(),bloodDemand.getPriority(),bloodDemand.getQuantity(),bloodDemand.getDescription(),hospital.getHospitalName(),hospital.getPhoneNumber(),fomattedAdress));
            }
            Gson gson = new Gson();
            return gson.toJson(donationReceiverNameDTOArrayList);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }
    }

    public static String getDonationCenterAddressFromDonationId(InputStream requestBody) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            ArrayList<BloodRequestHospitalDTO> donationReceiverNameDTOArrayList = new ArrayList<>();
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            Integer idD = Integer.parseInt( line.split("=")[1]);

            Donation donation = Donation.findById(idD);
            DonationCenter donationCenter = DonationCenter.findById(donation.getIdDC());
            Adress adress = Adress.findById(donationCenter.getIdA());
            return adress.getStreet()+" "+adress.getStreetNr()+" "+adress.getCity()+" "+adress.getCountry();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }
    }
}
