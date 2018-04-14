package request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;
import org.javalite.activejdbc.Base;

import javax.xml.ws.soap.Addressing;
import java.io.*;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class PostHandler {

    public static UserLoginData loginHandler(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String username = params[0].split("=")[1];
            String password = params[1].split("=")[1];
            System.out.println(username);
            System.out.println(password);
            List<UserLoginData> users = UserLoginData.where("Username = ? and Password = ? ", username, password);
            if (users.size() != 0) {
                return users.get(0);
            }
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            Base.close();
        }
    }

    public static void userUpdateHandler(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String idU = params[0].split("=")[1];
            String weight = params[1].split("=")[1];
            String phone = params[2].split("=")[1];
            String mail=params[3].split("=")[1];
            Donor.update("Weight=?,Phone=?,Mail=?","IdU=?",weight,phone,mail,idU);
        }
        catch (IOException e) {
            e.printStackTrace();

        } finally {
            Base.close();
        }
    }

    public static void additionalHandler(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String additional = params[0].split("=")[1];
            String idU = params[1].split("=")[1];
            System.out.println(idU);
            System.out.println(additional);
            List<SuffersOf> suf=SuffersOf.where("IdU=?",idU);
            long nr =Illness.count();
            int i=(int) nr;
            System.out.println(suf.size());
            if (suf.get(suf.size()-1).getIdI()<15){
                Illness ill=new Illness();
                ill.setIdI(i+1);
                ill.setName("Suplimentar");
                ill.setDescription(additional);
                System.out.println(2);
                ill.saveIt();
                SuffersOf s=new SuffersOf();
                s.setIdI(i+1);
                s.setIdU(Integer.parseInt(idU));
                s.saveIt();
            }
            else{
                Illness.update("Description=?","IdI=?",additional,suf.get(suf.size()-1).getIdI());
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            Base.close();
        }
    }

    public static void diseasesHandler(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            DataInputStream inp=new DataInputStream(in);
            String read=inp.readUTF();
            Gson gson=new Gson();
            Type collectionType = new TypeToken<List<Integer>>(){}.getType();
            List<Integer> list = gson.fromJson(read, collectionType);
            int id=list.remove(0);
            int contor=1;
            for (int i:list){
                if (i==1){
                    int nr=2;
                    List<SuffersOf> suf=SuffersOf.where("IdU=? and IdI=?",id,contor);
                    if (suf.size()==0) {
                        SuffersOf s = new SuffersOf();
                        s.setIdU(id);
                        s.setIdI(contor);
                        s.saveIt();
                    }
                }
                contor++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Base.close();
        }
    }

    public static String fieldsHandler(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String idU = params[0].split("=")[1];
            List<Donor> donors = Donor.where("IdU=?", idU);
            List<UserLoginData> users = UserLoginData.where("IdLD=?", idU);
            List<Adress> adresses = Adress.where("IdA=?",donors.get(0).getIdA() );
            if (adresses.size() != 0) {
                Donor d=donors.get(0);
                UserLoginData uld=users.get(0);
                Adress a = adresses.get(0);
                String urlParameters = String.format("street=%s&streetNr=%s&blockNr=%s&nr=%s&entrance=%s&floor=%s&apartNr=%s&city=%s&county=%s&country=%s&weight=%s&phone=%s&mail=%s&cnp=%s&nume=%s&date=%s&sange=%s&user=%s",Float.toString(d.getWeight()),d.getPhone(),d.getMail(),"1",a.getStreet(),Integer.toString(a.getStreetNr()),Integer.toString(a.getBlock()),a.getEntrance(),Integer.toString(a.getFloor()),Integer.toString(a.getApartNr()),a.getCity(),a.getCounty(),a.getCountry(),d.getCNP(),d.getName(),d.getBirthday(),d.getBloodGroup(),uld.getUsername());
                return urlParameters;
            }
            else {
                Donor d=donors.get(0);
                String urlParameters=String.format("a=%s&b=%s&c=%s&d=%s",Float.toString(d.getWeight()),d.getPhone(),d.getMail(),"0");
                return urlParameters;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            Base.close();
        }
    }

        public static void adressHandler(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String street = params[0].split("=")[1];
            String streetNr = params[1].split("=")[1];
            String blockNr = params[2].split("=")[1];
            String entrance = params[3].split("=")[1];
            String floor = params[4].split("=")[1];
            String apartNr = params[5].split("=")[1];
            String city = params[6].split("=")[1];
            String county = params[7].split("=")[1];
            String country = params[8].split("=")[1];
            String idU=params[9].split("=")[1];
            List<Donor> donors=Donor.where("IdU=?",idU);
            Adress a=new Adress();
            if (donors.size() != 0) {
                if (donors.get(0).getIdA()==null){
                    a.setStreet(street);
                    a.setStreetNr(Integer.parseInt(streetNr));
                    a.setBlock(Integer.parseInt(blockNr));
                    a.setEntrance(entrance);
                    a.setFloor(Integer.parseInt(floor));
                    a.setApartNr(Integer.parseInt(apartNr));
                    a.setCity(city);
                    a.setCounty(county);
                    a.setCountry(country);
                    a.saveIt();
                    List<Adress> adresses=Adress.where("Street=?",street);
                    int idA=adresses.get(adresses.size()-1).getIdA();
                    Donor.update("IdA=?","IdU=?",idA,idU);
                }
                else{
                    int idA=donors.get(0).getIdA();
                    Adress.update("Street=?,StreetNumber=?,BlockNumber=?,Entrance=?,Floor=?,ApartmentNumber=?,City=?,County=?,Country=?","IdA=?",street,streetNr,blockNr,entrance,floor,apartNr,city,county,country,idA);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            Base.close();
        }

    }
}
