import com.sun.net.httpserver.HttpServer;
import model.User;
import model.UserLoginData;
import org.javalite.activejdbc.Base;
import request.BaseHandler;

import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args){
        try {

            System.out.println("Connected!");

            String username = "test";
            String password = "1234";
            //List<UserLoginData> users = UserLoginData.where("Username = ? and Password = ? ",username,password);
            //for(UserLoginData user:users){
            //    System.out.println(user.getId()+" "+ user.getUserType());
           // }
            HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
            //Create the context for the server.
            server.createContext("/", new BaseHandler());

            server.setExecutor(null); // creates a default executor
            server.start();


        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}
