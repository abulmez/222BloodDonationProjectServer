package request;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
//import javafx.geometry.Pos;
import model.UserLoginData;

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
