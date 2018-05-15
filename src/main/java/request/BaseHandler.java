package request;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.BloodDemand;
import model.BloodDemandDTO;
import model.UserLoginData;
import org.javalite.activejdbc.LazyList;

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
    }
}
