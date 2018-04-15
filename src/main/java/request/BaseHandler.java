package request;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.DonationCenter;
import model.DonationSchedule;
import model.BloodDemand;
import model.UserLoginData;
import org.javalite.activejdbc.LazyList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class BaseHandler implements HttpHandler {
    //Handler method
    public void handle(HttpExchange t) throws IOException {

        System.out.println("Cineva s-a conectat");

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/login")){
            UserLoginData user =  PostHandler.loginHandler(t.getRequestBody());
            String response;
            if(user != null){
                response = String.format("Id=%s&UserType=%s",user.getId(),user.getUserType());
                t.sendResponseHeaders(200, response.length());
            }
            else{
                response = "nop";
                t.sendResponseHeaders(401, response.length());
            }
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/getDonationCenter")){
            LazyList<DonationCenter> donationCenters = GetHandler.donationCentersHandler();
            for(DonationCenter dc:donationCenters){
                System.out.println(dc.getCenterName());
            }
            String response;

            if(donationCenters.size() != 0){
                response=donationCenters.toJson(true);
                t.sendResponseHeaders(200,response.length());
            }
            else {
                response="nop";
                t.sendResponseHeaders(401,response.length());
            }

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/getDonationSchedule")){
            LazyList<DonationSchedule> donationSchedules = GetHandler.donationSchedulesHandler();
            String response;

            if(donationSchedules.size() != 0){
                response=donationSchedules.toJson(true);
                t.sendResponseHeaders(200,response.length());
            }
            else {
                response="nop";
                t.sendResponseHeaders(401,response.length());
            }

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        t.close();
    }
}
