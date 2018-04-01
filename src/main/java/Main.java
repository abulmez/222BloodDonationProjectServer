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
