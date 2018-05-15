package request;

import com.google.gson.Gson;
import model.*;
import model.DTO.BloodProductShipmentAddressDTO;
import model.DTO.BloodRequestHospitalDTO;
import model.DTO.DonationReceiverNameBloodGroupDTO;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import utils.DTOutils;
import utils.DonationDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

public class GetHandler {

    public static List<DonationDTO> getDonationsHandler() {
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

    public static LazyList<Donor> getAdminsHandler(){
        try {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            List<Donor> donors =new ArrayList<>();
            List<UserLoginData> loginData = UserLoginData.findAll();
            for(UserLoginData u:loginData){
                if(u.getUserType().equals("Donor"))
                    donors.add(Donor.findFirst("IdU = ?",u.getId()));
            }
            LazyList<Donor> lazy=Donor.findBySQL("Select * from Users u inner join LoginData d on u.idU=d.idLD where d.UserType=?","Donor");
            for (Donor d:lazy)
                System.out.println(d.getIdU());
            return lazy;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Base.close();
        }
    }

    public static LazyList<Donor> getDonorsHandler() {
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
        //LazyList<DonationSchedule> donationSchedules = null;
        try {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            LazyList<DonationSchedule> donationSchedules = DonationSchedule.findAll();
            /*System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
            System.out.println(donationSchedules.size());*/
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

    public static LazyList<Adress> adressHandler(){
        try {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            LazyList<Adress> adresses = Adress.findAll();
            System.out.println("Adress size: "+adresses.size());
            return adresses;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }
    }

    public static LazyList<UserPacient> userPacientsHandler(){
        try {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            LazyList<UserPacient> userPacients = UserPacient.findAll();
            System.out.println("UserPacient size: "+userPacients.size());
            return userPacients;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }
    }

    public static LazyList<Reservation> reservationHandler(){
        LazyList<Reservation> reservations = null;
        try {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            /*LazyList<Reservation>*/ reservations = Reservation.findAll();
            System.out.println("Reservation size: "+reservations.size());
            return reservations;
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
            String sqlQuerry = String.format("SELECT abp.IdBP,abp.IdD,abp.ProductType,abp.ValidUntil,abp.Quantity FROM AvailableBloodProducts abp INNER JOIN Donation d on abp.IdD = d.IdD INNER JOIN DonationCenter dc on d.IdDC = dc.IdDC WHERE dc.IdDC = %s AND abp.Deleted = 0 ",idDC);

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

                String query = String.format("SELECT abp.Quantity FROM BloodDemand bd INNER JOIN BloodProductsShippment bps ON bd.IdBD = bps.IdBD INNER JOIN AvailableBloodProducts abp ON abp.IdBP = bps.IdBP WHERE bd.IdBD = %s",bloodDemand.getIdBd());
                LazyList<AvailableBloodProducts> shipped = AvailableBloodProducts.findBySQL(query);
                Double shippedQuantity = 0.0;
                for(AvailableBloodProducts product:shipped){
                    shippedQuantity +=product.getQuantity();
                }

                Hospital hospital = Hospital.findById(bloodDemand.getIdH());
                Adress adress = Adress.findById(hospital.getIdA());
                String fomattedAdress = adress.getStreet()+" "+adress.getStreetNr()+" "+adress.getCity()+" "+adress.getCountry();
                if(bloodDemand.getQuantity()-shippedQuantity>0) {
                    donationReceiverNameDTOArrayList.add(new BloodRequestHospitalDTO((Integer) bloodDemand.getId(), bloodDemand.getPriority(), (bloodDemand.getQuantity() - shippedQuantity), bloodDemand.getDescription(), hospital.getHospitalName(), hospital.getPhoneNumber(), fomattedAdress));
                }
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

    public static String getAllBloodProductShipmentForDonationCenter(InputStream requestBody) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            ArrayList<BloodProductShipmentAddressDTO> bloodProductShipmentAddressDTOs = new ArrayList<>();
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            Integer idDC = Integer.parseInt( line.split("=")[1]);

            String query = String.format("SELECT abp.IdBP\n" +
                    "FROM Donation d INNER JOIN DonationCenter dc \n" +
                    "\t\t\t    ON d.IdDC = dc.IdDC \n" +
                    "\t\t\t\tINNER JOIN AvailableBloodProducts abp \n" +
                    "\t\t\t\tON abp.IdD = d.IdD \n" +
                    "\t\t\t\tINNER JOIN BloodProductsShippment bps\n" +
                    "\t\t\t\tON bps.IdBP = abp.IdBP\n" +
                    "WHERE dc.IdDC = %s",idDC);
            LazyList<AvailableBloodProducts> availableBloodProducts = AvailableBloodProducts.findBySQL(query);
            for(AvailableBloodProducts product:availableBloodProducts){
                query = String.format("SELECT a.Street,a.StreetNumber,a.City,a.Country\n" +
                        "FROM BloodProductsShippment bps INNER JOIN BloodDemand bd\n" +
                        "\t\t\t\t\t\t\t\ton bps.IdBD = bd.IdBD\n" +
                        "\t\t\t\t\t\t\t\tINNER JOIN Hospital h\n" +
                        "\t\t\t\t\t\t\t\tON h.IdH = bd.IdH\n" +
                        "\t\t\t\t\t\t\t\tINNER JOIN Adress a\n" +
                        "\t\t\t\t\t\t\t\tON a.IdA = h.IdA\n" +
                        "WHERE bps.IdBP = %s",product.getIdBP());
                LazyList<Adress> addressList = Adress.findBySQL(query);
                Adress address = addressList.get(0);
                query = String.format("SELECT h.HospitalName, h.PhoneNumber\n" +
                        "FROM BloodProductsShippment bps INNER JOIN BloodDemand bd\n" +
                        "\t\t\t\t\t\t\t\ton bps.IdBD = bd.IdBD\n" +
                        "\t\t\t\t\t\t\t\tINNER JOIN Hospital h\n" +
                        "\t\t\t\t\t\t\t\tON h.IdH = bd.IdH\n" +
                        "WHERE bps.IdBP = %s",product.getIdBP());
                LazyList<Hospital> hospitalList = Hospital.findBySQL(query);
                Hospital hospital = hospitalList.get(0);
                bloodProductShipmentAddressDTOs.add(new BloodProductShipmentAddressDTO(product.getIdBP(),address.getStreet(),address.getStreetNr(),address.getCity(),address.getCountry(),hospital.getHospitalName(),hospital.getHospitalName()));
            }
            Gson gson = new Gson();
            return gson.toJson(bloodProductShipmentAddressDTOs);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }
    }

    public static Integer getDonationCenterIdForTCP(InputStream requestBody) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            Integer idTCP = Integer.parseInt( line.split("=")[1]);

            TCP tcp = TCP.findById(idTCP);
            return tcp.getIdDC();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }
    }

    public static LazyList<Adress> donationCentersAdressesHandler() {
        try {
            System.out.print("Am ajuns aici");
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            LazyList<DonationCenter> donationCenters = DonationCenter.findAll();
            List<Integer> ids = new ArrayList<>();
            donationCenters.forEach(x->{ids.add(x.getIdA());});
            LazyList<Adress> adresses = Adress.findAll();
            adresses.removeIf(x-> !ids.contains(x.getIdA()));
            return adresses;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }
    }
}
