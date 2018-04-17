package request;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
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
            System.out.println("Aici mai fac un debug ca innebunesc plm: "+demand.getDescription());
            if(demand!=null){
                response=String.format("Cererea cu id-ul "+demand.getIdBd()+" a fost inregistrata");
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

            LazyList<BloodDemand> list=PostHandler.findDemands(t.getRequestBody());
            String response="";
            if(list!=null){
                response=list.toJson(true);

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


        t.close();
    }
}
