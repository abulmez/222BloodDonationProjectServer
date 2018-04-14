
import com.sun.net.httpserver.HttpServer;
import model.Donor;
import model.Medic;
import model.User;
import model.UserLoginData;
import org.codehaus.jackson.map.ObjectMapper;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.common.JsonHelper;
import request.BaseHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;



public class Main {


    public static void main(String[] args){
        try {


            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            System.out.println("Connected!");
            LazyList<Medic> d = Medic.findAll();
            LazyList<UserLoginData> l2 = UserLoginData.findAll();
            System.out.println(d.size());

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
