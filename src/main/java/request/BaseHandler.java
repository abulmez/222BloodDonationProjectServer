package request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.*;
import model.DTO.DonationCenterDTO;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.LazyList;
import utils.DonationDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Modifier.STATIC;
import static java.lang.reflect.Modifier.TRANSIENT;

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
        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/currentReservationDC")){
            LazyList<DonationCenter>donationCenters =  PostHandler.currentReservationDC(t.getRequestBody());
            String response;
            System.out.print("Magie2"+donationCenters.size());
            if(donationCenters.size() != 0){
                response=donationCenters.toJson(true);
                System.out.println("MULTA MULTA MAGIE" +"\n===============\n"+response);
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
        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/emailsForBloodType")){
            List<String> emails=GetHandler.getEmailsForBloodType(t.getRequestBody());
            String response;
            if(emails.size() != 0){
                response = new Gson().toJson(emails);
                t.sendResponseHeaders(200, response.length());
            }
            else{
                response="no";
                t.sendResponseHeaders(401, response.length());
            }
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/addCentre")){
            DonationCenter dc = PostHandler.addCentreHandler(t.getRequestBody());
            String response;
            if(dc!=null){
                response = String.format("Cererea pentru adaugarea centrului" + dc.getCenterName() + " a fost inregistrat");
                t.sendResponseHeaders(200,response.length());
            }else{
                response="Adaugarea nu a putut fi realizata";
                t.sendResponseHeaders(401,response.length());
            }

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/addHospital")){
            Hospital hospital = PostHandler.addHospitalHandler(t.getRequestBody());
            String response;
            if(hospital!=null){
                response = String.format("Cererea pentru adaugarea centrului" + hospital.getHospitalName() + " a fost inregistrat");
                t.sendResponseHeaders(200,response.length());
            }else{
                response="Adaugarea nu a putut fi realizata";
                t.sendResponseHeaders(401,response.length());
            }

            OutputStream os = t.getResponseBody();
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
            if(list!=null && list.size()>0){
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
            if(!list.equals("")){
                response=String.format(list);
            }
            else
            {
                response=String.format("Eroare la incarcarea livrarilor");
            }
            t.sendResponseHeaders(200,response.length());
            OutputStream os=t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/getUsers")){
            String response=PostHandler.getUsers(t.getRequestBody());
            /*String response="";
            Gson gson = new Gson();
            System.out.println(1);
            response=donors.toJson(false);
            Type collectionType = new TypeToken<ArrayList<Donor>>(){}.getType();
            System.out.println(2);
            ArrayList<Donor> donors2 = gson.fromJson(response, collectionType);
            for (Donor d:donors2)
                System.out.println(d.getIdU());
            System.out.println(2);*/
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();

        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/filtrarePlasateBloodDemands")){

            List<BloodDemandDTO> list=PostHandler.cereriPlasateHandler(t.getRequestBody());
            String response="";
            if(list!=null && list.size()>0){
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

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/productsFromCenters")){

            List<DonationCenterDTO> list=GetHandler.getAllAvailableBloodProductsFromCenters(t.getRequestBody());
            String response="";
            if(list!=null && list.size()>0){
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
            if(list!=null && list.size()>0){
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

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/updateAddress")){
            PostHandler.updateAddress(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200,response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/addAddress")){
            PostHandler.addAddress(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200,response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }



        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/deleteCentre")){
            PostHandler.deleteCentreHandle(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200,response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/deleteHospital"))
        {
            PostHandler.deleteHospitalHandle(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200,response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/getUsernames")){
            String response=PostHandler.getUsernames(t.getRequestBody());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/checkCnp")){
            String response=PostHandler.checkCnp(t.getRequestBody());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/checkAdminId")){
            String response=PostHandler.checkAdminId(t.getRequestBody());
            t.sendResponseHeaders(200, response.length());
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

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/address")){
            String response=PostHandler.getAddress(t.getRequestBody());
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

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/updateCentre"))
        {
            PostHandler.updateCentreHandle(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/updateHospital"))
        {
            PostHandler.updateHospitalHandle(t.getRequestBody());
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

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/updateUsername")){
            PostHandler.updateUsername(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/updateAdmin")){
            PostHandler.updateAdmin(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/getHospitals")){
            String response=PostHandler.getHospitals(t.getRequestBody());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/addAdmin")){
            PostHandler.addAdmin(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/addMedic")){
            PostHandler.addMedic(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/updateMedicHospital")){
            String response=PostHandler.updateMedicHospital(t.getRequestBody());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/updateTCPCenter")){
            String response=PostHandler.updateTCPCenter(t.getRequestBody());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/addTCP")){
            PostHandler.addTCP(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/updateDonor")){
            PostHandler.updateDonor(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/deleteUser")){
            PostHandler.deleteUser(t.getRequestBody());
            String response="raspuns";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }



        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/getDonations")){
            List<DonationDTO> donations=GetHandler.getDonationsHandler(t.getRequestBody());
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

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/getDonorFromDonation")){
            Donor donor=GetHandler.getDonorFromDonationHandler(t.getRequestBody());
            String response;
            if(donor != null){
                response = donor.toJson(false);
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
            LazyList<Donor> donors=GetHandler.getDonorsHandler();
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
            String response = PostHandler.addBloodProductHandler(t.getRequestBody());
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
        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/getDonationCenterAddresses")){

            LazyList<Adress> donationCenters = GetHandler.donationCentersAdressesHandler();
            String response="";
            if((donationCenters != null ? donationCenters.size() : 0) != 0){
                try {
                    response = donationCenters.toJson(true);
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                t.sendResponseHeaders(200,response.length());
            }
            else {
                response="nop";
                System.out.print(response);
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

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/getUserPacient")){
            LazyList<UserPacient> userPacients = GetHandler.userPacientsHandler();
            String response;

            if(userPacients.size() != 0){
                response=userPacients.toJson(true);
                System.out.println("UserPacient");
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

        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/getAllHospitalsHandler"))
        {
            LazyList<Hospital> hospitals = GetHandler.getAllHospitalsHandler();
            String response;

            if(hospitals.size() != 0){
                response=hospitals.toJson(true);
                System.out.println("=======");
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

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/getAdresaSpital")) {

            String response = "";
            String donationsDTOJson = GetHandler.getAdresaSpital(t.getRequestBody());
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

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/sendBloodProduct")) {
            String response = "";
            Boolean ok = PostHandler.sendBloodProduct(t.getRequestBody());
            if (ok.equals(true)) {
                t.sendResponseHeaders(200, response.length());
            } else {
                t.sendResponseHeaders(404, response.length());
            }
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/getAllBloodProductShipmentForDonationCenter")) {

            String response = "";
            String bloodProductShipmentAdressDTOJson = GetHandler.getAllBloodProductShipmentForDonationCenter(t.getRequestBody());
            if (bloodProductShipmentAdressDTOJson != null) {
                response = bloodProductShipmentAdressDTOJson;
                t.sendResponseHeaders(200, response.length());
            } else {
                t.sendResponseHeaders(404, response.length());
            }
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/getDonationCenterIdForTCP")) {

            String response = "";
            Integer idDC = GetHandler.getDonationCenterIdForTCP(t.getRequestBody());
            if (idDC != null) {
                response = idDC.toString();
                t.sendResponseHeaders(200, response.length());
            } else {
                t.sendResponseHeaders(404, response.length());
            }
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/splitBloodProduct")) {

            String response = "";
            Integer idBP = PostHandler.splitBloodProduct(t.getRequestBody());
            if (idBP != null) {
                response = idBP.toString();
                t.sendResponseHeaders(200, response.length());
            } else {
                t.sendResponseHeaders(404, response.length());
            }
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (t.getRequestHeaders().getFirst("Content-Type").equals("application/donationreport")) {
            String response = PostHandler.getDonationReport(t.getRequestBody());
            t.sendResponseHeaders(200,response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }


        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/donations"))
        {
            String response = PostHandler.getAllDonationsForAUser(t.getRequestBody());
            t.sendResponseHeaders(200,response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();

        }






        if(t.getRequestHeaders().getFirst("Content-Type").equals("application/donationSchedule"))
        {
            String response = PostHandler.donationScheduleHandler(t.getRequestBody());
            t.sendResponseHeaders(200,response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }


        t.close();
    }
}
