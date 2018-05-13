package serverStart;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpServer;
import model.*;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import request.BaseHandler;

import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;


public class Main {


    public static void main(String[] args){
        try {


            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            System.out.println("Connected!");

//            LazyList<Medic> d = Medic.findAll();
//            System.out.println(d.size());

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



//            LazyList<UserLoginData> l2 = UserLoginData.findAll();
//            System.out.println(l2.size());
//            LazyList<Donation> l3 = Donation.findAll();
//            System.out.println(l3.size());
//
//            LazyList<BloodDemand> l4 = BloodDemand.findAll();
//            for(BloodDemand asd: l4){
//                System.out.println(asd.getQuantity());
//            }
//            /*
//            String bla = l4.toJson(false);
//            System.out.println(bla);
//            Gson gson = new Gson();
//            Type collectionType = new TypeToken<Collection<BloodDemand2>>(){}.getType();
//            Collection<BloodDemand2> arrayList = gson.fromJson(bla,collectionType);
//               */
//            LazyList<Adress> l6 = Adress.findAll();
//            System.out.println(l6.size());
//
//
//            LazyList<Donor> l5 = Donor.findAll();
//            System.out.println(l5.size());
//
//            LazyList<Donor> l7 = Donor.findAll();
//            System.out.println(l7.size());

            HttpServer server = HttpServer.create(new InetSocketAddress(14423), 0);
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
