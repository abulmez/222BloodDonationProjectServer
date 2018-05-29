package serverStart;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpServer;
import model.*;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.common.JsonHelper;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import request.BaseHandler;

import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static request.GetHandler.getAdminsHandler;
import java.util.List;


public class Main {


    public static void main(String[] args){
        try {


            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            System.out.println("Connected!");

            LazyList<Medic> d = Medic.findAll();
            System.out.println(d.size());

            List<Donor> list = Donor.findAll();
            for(Donor u:list){
                System.out.println(u.getIdU()+" "+u.getName()+" "+u.getBirthday()+" "+u.getMail()+" "+u.getPhone()+" "+u.getBloodGroup()+" "+u.getWeight());
            }

            List<DonationSchedule> donationSchedulesList = DonationSchedule.where("IdDC=? AND IdDS=?",1,1);
            for(DonationSchedule ds:donationSchedulesList)
            {
                System.out.println(ds.getIdDC() + " " + ds.getIdDS() + " " + ds.getAvailableSpots() + " " + ds.getDonationDateTime());
            }

            List<DonationReport> donationReports = DonationReport.findAll();
            for(DonationReport dr:donationReports){
                System.out.println(dr.getIdDR() + " " + dr.getSamplingDate() + " " + dr.getBloodStatus() + " " + dr.getBloodReport());
            }

            List<Hospital> hospitals = Hospital.findAll();
            for(Hospital hospital:hospitals){
                System.out.println(hospital.getIdA().toString() + " " + hospital.getIdH().toString() + " " + hospital.getHospitalName() + " " + hospital.getPhoneNumber() );
            }

            List<DonationCenter> donationCenters = DonationCenter.findAll();
            for(DonationCenter donation:donationCenters)
            {
                System.out.println(donation.getCenterName() + donation.getPhoneNumber());
            }
            LazyList<UserLoginData> l2 = UserLoginData.findAll();
            System.out.println(l2.size());

            LazyList<Donation> l3 = Donation.findAll();
            System.out.println(l3.size());
            System.out.println(l3);

            LazyList<BloodDemand> l4 = BloodDemand.findAll();
            for(BloodDemand asd: l4){
                System.out.println(asd.getQuantity());
            }

//            String bla = l4.toJson(false);
//            System.out.println(bla);
//            Gson gson = new Gson();
//            Type collectionType = new TypeToken<Collection<BloodDemand2>>(){}.getType();
//            Collection<BloodDemand2> arrayList = gson.fromJson(bla,collectionType);
//
            LazyList<Adress> l6 = Adress.findAll();
            System.out.println(l6.size());


            LazyList<Donor> l5 = Donor.findAll();
            System.out.println(l5.size());
            LazyList <Admin> l8 = Admin.findAll();
            System.out.println(l8.size());
            LazyList <TCP> l9 = TCP.findAll();
            System.out.println(l9.size());


            try {
                Medic.update("IdH=?", "CNP=?", 10, "1234567890123");
            }catch (Exception e){
                System.out.println("banana");
            }

            LazyList<Reservation> list1 = Reservation.findAll();
            for (Reservation reservation : list1) {
                System.out.println(reservation);
            }

            HttpServer server = HttpServer.create(new InetSocketAddress("localhost",14423), 0);
            //Create the context for the server.
            server.createContext("/", new BaseHandler());
            server.setExecutor(null); // creates a default executor

            server.start();
            System.out.println(server.getAddress());
            System.out.println("Server started!");


        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}
