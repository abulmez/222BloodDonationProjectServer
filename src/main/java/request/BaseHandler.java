package request;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Donation;
import model.Donor;
import model.UserLoginData;
import org.javalite.activejdbc.LazyList;
import utils.DonationDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

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

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/getDonations")){
            List<DonationDTO> donations=GetHandler.donationsHandler();
            String response;

            if(donations.size() != 0){
                response = new Gson().toJson(donations);
                System.out.println(response);
                t.sendResponseHeaders(200, response.length());
            }
            else{
                response="nop";
                t.sendResponseHeaders(401, response.length());
            }
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();

        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/addDonation")){
            String response = PostHandler.addDonationHandler(t.getRequestBody());
            if(response.equals("Success")){
                t.sendResponseHeaders(200, response.length());
            }
            else{
                t.sendResponseHeaders(401, response.length());
            }
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

    }

}
