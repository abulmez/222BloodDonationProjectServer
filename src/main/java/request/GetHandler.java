package request;

import com.google.gson.Gson;
import model.*;
import model.dto.BloodProductShipmentAddressDTO;
import model.dto.BloodRequestHospitalDTO;
import model.dto.DonationCenterDTO;
import model.dto.DonationReceiverNameBloodGroupDTO;
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

    public static List<DonationDTO> getDonationsHandler(InputStream requestBody) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            Integer idU = Integer.parseInt(line.split("=")[1]);
            TCP tcp=TCP.findFirst("IdU = ?",idU);
            List<DonationDTO> donations = DTOutils.getDTOs(Donation.where("IdDC = ?",tcp.get("IdDC")));
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

    public static Donor getDonorFromDonationHandler(InputStream requestBody) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            Integer idD = Integer.parseInt(line.split("=")[1]);
            Donation donation=Donation.findFirst("IdD = ?",idD);
            Donor donor=Donor.findFirst("IdU = ?",donation.getIdU());
            System.out.println(donor);
            return donor;
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

    public static List<DonationCenterDTO> getAllAvailableBloodProductsFromCenters(InputStream requestBody) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {

            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            List<DonationCenter> centers=DonationCenter.findAll();
            List<DonationCenterDTO> donationCenters=new ArrayList<>();
            for(DonationCenter d: centers){
                Adress a=Adress.findById(d.getIdA());
                String adress=a.getCountry()+" "+a.getCity()+" "+a.getStreet()+" "+a.getStreetNr();
                String sqlQuerry = String.format("SELECT abp.IdBP,abp.IdD,abp.ProductType,abp.ValidUntil,abp.Quantity FROM AvailableBloodProducts abp INNER JOIN Donation d on abp.IdD = d.IdD INNER JOIN DonationCenter dc on d.IdDC = dc.IdDC WHERE dc.IdDC = %s AND abp.Deleted = 0 ",d.getIdDC());
                List<AvailableBloodProducts> products = AvailableBloodProducts.findBySQL(sqlQuerry);
                for(AvailableBloodProducts product : products) {
                    List<BloodProductsShippment> listBPS = BloodProductsShippment.where("IdBP = ?", product.getIdBP());
                    if (listBPS.size() == 0) {
                        Donation don = Donation.findById(product.getIdD());
                        Donor donor = Donor.findById(don.getIdU());
                        donationCenters.add(new DonationCenterDTO(donor.getBloodGroup(), don.getReceiverName(), product.getIdD(), product.getIdBP(), d.getCenterName(), d.getPhoneNumber(), adress, product.getProductType(), product.getValidUntil(), product.getQuantity()));
                    }
                }
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

    public static LazyList<Illness> getAllIllnessPacient(InputStream requestBody) {
        System.out.println("------Get Illness method");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            Integer idU = Integer.parseInt(line.split("=")[1]);
            String sqlQuerry = String.format("SELECT * FROM Users u INNER JOIN SuffersOf s ON u.IdU = s.IdU INNER JOIN Illness i ON s.IdI = i.IdI WHERE u.IdU = %s",idU);
            LazyList<Illness> illnesses = Illness.findBySQL(sqlQuerry);
            System.out.println("Marimea Illness: " + illnesses.size());
            for(Illness illness : illnesses){
                System.out.println(illness);
            }
            /*for(Illness d:illnesses){
                illnessArrayList.add(d);
            }*/
            return illnesses;
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


    public static LazyList<DonationSchedule> getAllDonationSchedules(InputStream requestBody){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] reqResponse = line.split("&");
            Integer year = Integer.parseInt(reqResponse[0].split("=")[1]);
            Integer month = Integer.parseInt(reqResponse[1].split("=")[1]);
            Integer day = Integer.parseInt(reqResponse[2].split("=")[1]);
            System.out.println(year);
            System.out.println(month);
            System.out.println(day);

            String query = String.format("SELECT D.IdDS,D.IdDC,D.DonationDateTime ,D.AvailableSpots - (SELECT Count(*) " +
                    "FROM DonationSchedule AS DS INNER JOIN Reservation RS ON DS.IdDS = RS.IdDS AND D.IdDS = RS.IdDS" +
                    ") as AvailableSpots" +
                    " FROM DonationSchedule AS D WHERE YEAR(D.DonationDateTime)=%s AND month(D.DonationDateTime)=%s AND day(D.DonationDateTime) =%s",year,month,day);

            LazyList<DonationSchedule> donationScheduleLazyList = DonationSchedule.findBySQL(query);
            System.out.println(donationScheduleLazyList);
            return donationScheduleLazyList;
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
    public static String getAdresaSpital(InputStream requestBody) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            Integer idU = Integer.parseInt( line.split("=")[1]);
            LazyList<Medic> list1= Medic.where("IdU = ?",idU);
            Integer idH=list1.get(0).getIdH();
            Hospital h=Hospital.findById(idH);
            Integer idA=h.getIdA();
            Adress a=Adress.findById(idA);
            String adress=a.getCountry()+" "+a.getCity()+" "+a.getStreet()+" "+a.getStreetNr();
            return adress;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }
    }

    public static Integer setAvailableSpots(InputStream requestBody){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            Integer IdDS = Integer.parseInt( line.split("=")[1]);
            String query = String.format("SELECT D.IdDS,D.IdDC,D.DonationDateTime ,D.AvailableSpots - (SELECT Count(*) " +
                    "FROM DonationSchedule AS DS INNER JOIN Reservation RS ON DS.IdDS = RS.IdDS AND D.IdDS = RS.IdDS" +
                    " ) as AvailableSpots" +
                    " FROM DonationSchedule AS D WHERE D.IdDS=%s",IdDS);
            LazyList<DonationSchedule> donationSchedule = DonationSchedule.findBySQL(query);
            System.out.println(donationSchedule);
            return donationSchedule.get(0).getAvailableSpots();
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

    public static LazyList<Hospital> getAllHospitalsHandler(){
        try {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            LazyList<Hospital> hospitals = Hospital.findAll();
            System.out.println("Hospitals size: "+hospitals.size());
            return hospitals;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }
    }

    public static List<String> getEmailsForBloodType(InputStream requestBody) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String bloodType =line.split("=")[1];
            LazyList<UserPacient> userPacients = UserPacient.findAll();
            List<String> emails = new ArrayList<>();
            for (UserPacient userPacient:userPacients) {
                if(userPacient.getBloodGroup()!=null && userPacient.getBloodGroup().equalsIgnoreCase(bloodType))
                    emails.add(userPacient.getMail());
            }
            return emails;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }
    }
}
