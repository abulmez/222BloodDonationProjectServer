package request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.*;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.LazyList;
import utils.DonationDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
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
        if(t.getRequestHeaders().getFirst("Content-type").equals("application/register"))
        {
            Integer resisterResult = PostHandler.registerHandler(t.getRequestBody());
            String response;
            response = "niceString!";
            t.sendResponseHeaders(resisterResult, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/addBloodDemand")){
            BloodDemand demand=PostHandler.addDemandHandler(t.getRequestBody());
            String response;
            if(demand!=null){
                response=String.format("Cererea pentru pacientul "+demand.getDescription()+" a fost inregistrata");
                t.sendResponseHeaders(200,response.length());
            }
            else{
                response="Adaugarea nu a putut fi realizata";
                t.sendResponseHeaders(423,response.length());
            }
            OutputStream os=t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/modifyBloodDemand")){
            Integer code=PostHandler.modifyDemandHandler(t.getRequestBody());
            String response;
            if(code==1){
                response=String.format("Update-ul a fost realizat");
                t.sendResponseHeaders(200,response.length());
            }
            else if(code==2){
                response=String.format("Cererea ce se doreste a fi updatata nu exista");
                t.sendResponseHeaders(404,response.length());
            }
            else{
                response=String.format("Eroare la citirea datelor trimise. Incercati din nou.");
                t.sendResponseHeaders(422,response.length());
            }
            System.out.println(response+" ASTA E RASPUNSUL");
            OutputStream os=t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/findBloodDemands")){

            List<BloodDemandDTO> list=PostHandler.findDemands(t.getRequestBody());
            String response="";
            if(list!=null){
                Gson g=new Gson();
                response=g.toJson(list);

                t.sendResponseHeaders(200,response.length());
            }
            else
            {
                response=String.format("Eroare la incarcarea tabelului");
                t.sendResponseHeaders(422,response.length());
            }
            OutputStream os=t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/vizualizareBloodDemands")){

            String list=PostHandler.vizualizareLivrariHandler(t.getRequestBody());
            String response="";
            if(list!=null){
                response=String.format(list);

                t.sendResponseHeaders(200,response.length());
            }
            else
            {
                response=String.format("Eroare la incarcarea tabelului");
                t.sendResponseHeaders(422,response.length());
            }
            OutputStream os=t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/filtrarePlasateBloodDemands")){

            List<BloodDemandDTO> list=PostHandler.cereriPlasateHandler(t.getRequestBody());
            String response="";
            if(list!=null){
                Gson g=new Gson();
                response=g.toJson(list);

                t.sendResponseHeaders(200,response.length());
            }
            else
            {
                response=String.format("Eroare la incarcarea tabelului");
                t.sendResponseHeaders(422,response.length());
            }
            OutputStream os=t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/filtrareLivrateBloodDemands")){

            List<BloodDemandDTO> list=PostHandler.cereriLivrateHandler(t.getRequestBody());
            String response="";
            if(list!=null){
                Gson g=new Gson();
                response=g.toJson(list);

                t.sendResponseHeaders(200,response.length());
            }
            else
            {
                response=String.format("Eroare la incarcarea tabelului");
                t.sendResponseHeaders(422,response.length());
            }
            OutputStream os=t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }


        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/removeBloodDemand")){

            String list=PostHandler.removeDemandHandler(t.getRequestBody());
            String response="";
            if(list!=null){
                response=String.format(list);

                t.sendResponseHeaders(200,response.length());
            }
            else
            {
                response=String.format("Eroare la stergere");
                t.sendResponseHeaders(422,response.length());
            }
            OutputStream os=t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/adress")){
            PostHandler.adressHandler(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200,response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/fields")){
            String response=PostHandler.fieldsHandler(t.getRequestBody());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/diseasesChecks")){
            String response=PostHandler.diseasesChecksHandler(t.getRequestBody());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/userUpdate")){
            PostHandler.userUpdateHandler(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/diseases")){
            PostHandler.diseasesHandler(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/additional")){
            PostHandler.additionalHandler(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200, response.length());
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

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/getDonors")){
            LazyList<Donor> donors=GetHandler.donorsHandler();
            String response;
            if(donors.size() != 0){
                response = donors.toJson(true);
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

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/addBloodProduct")){
            String response = PostHandler.addBloodProduct(t.getRequestBody());
            if(response.equals("Success")){
                t.sendResponseHeaders(200, response.length());
            }
            else{
                t.sendResponseHeaders(422, response.length());
            }
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/modifyDonation")){
            String response = PostHandler.modifyDonationHandler(t.getRequestBody());
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

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/isDecomposed")){
            String response = PostHandler.isDecomposedHandler(t.getRequestBody());
            if(Boolean.parseBoolean(response)){
                t.sendResponseHeaders(200, response.length());
            }
            else{
                t.sendResponseHeaders(404, response.length());
            }
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/addReport")){
            String response = PostHandler.addReportHandler(t.getRequestBody());
            if(response.equals("Success")){
                t.sendResponseHeaders(200, response.length());
            }
            else{
                t.sendResponseHeaders(409, response.length());
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
                System.out.println("DONATION CENTERS");
                System.out.println(response);
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
                System.out.println("==========");
                System.out.println(response);
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

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/getAdress")){
            LazyList<Adress> adresses = GetHandler.adressHandler();
            String response;

            if(adresses.size() != 0){
                response=adresses.toJson(true);
                System.out.println("Adress");
                System.out.println(response);
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

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/getReservation")){
            LazyList<Reservation> reservations = GetHandler.reservationHandler();
            String response;

            if(reservations.size() != 0){
                response=reservations.toJson(true);
                System.out.println("Reservation");
                System.out.println(response);
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

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/updateDonationScheduleStatus")){
            String response=PostHandler.statusDonationScheduleUpdateHandler(t.getRequestBody());
            //String response="raspuns";
            if(response == "Success"){
                t.sendResponseHeaders(200, response.length());
            }
            else{
                t.sendResponseHeaders(401, response.length());
            }
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/getAllAvailableBloodProducts")) {

            String response = "";
            String productsJson = GetHandler.getAllAvailableBloodProducts(t.getRequestBody());
            if (productsJson != null) {
                response = productsJson;
                t.sendResponseHeaders(200, response.length());
            } else {
                t.sendResponseHeaders(404, response.length());
            }
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();

        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/getAllDonationReceiverNames")) {

                String response = "";
                String donationsDTOJson = GetHandler.getAllDonationReceiverNames(t.getRequestBody());
                if (donationsDTOJson != null) {
                    response = donationsDTOJson;
                    t.sendResponseHeaders(200, response.length());
                } else {
                    t.sendResponseHeaders(404, response.length());
                }
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }


        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/getAllBloodRequestsAndHospitalInfoForProductTypeAndGroup")) {

            String response = "";
            String bloodRequestsDTOJson = GetHandler.getAllBloodRequestsAndHospitalInfoForProductTypeAndGroup(t.getRequestBody());
            if (bloodRequestsDTOJson != null) {
                response = bloodRequestsDTOJson;
                t.sendResponseHeaders(200, response.length());
            } else {
                t.sendResponseHeaders(404, response.length());
            }
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/getDonationCenterAddressFromDonationId")) {

            String response = "";
            String address = GetHandler.getDonationCenterAddressFromDonationId(t.getRequestBody());
            if (address != null) {
                response = address;
                t.sendResponseHeaders(200, response.length());
            } else {
                t.sendResponseHeaders(404, response.length());
            }
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/deleteBloodProduct")) {

            String response = "";
            Boolean ok = PostHandler.deleteBloodProduct(t.getRequestBody());
            if (ok.equals(true)) {
                t.sendResponseHeaders(200, response.length());
            } else {
                t.sendResponseHeaders(404, response.length());
            }
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        t.close();
    }
}
