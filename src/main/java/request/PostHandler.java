package request;

import model.UserLoginData;
import org.javalite.activejdbc.Base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class PostHandler {

    public static UserLoginData loginHandler(InputStream in){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String username = params[0].split("=")[1];
            String password = params[1].split("=")[1];
            System.out.println(username);
            System.out.println(password);
            List<UserLoginData> users = UserLoginData.where("Username = ? and Password = ? ",username,password);
            if(users.size()!=0){
                return users.get(0);
            }
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            Base.close();
        }

    }
}
