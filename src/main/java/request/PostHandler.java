package request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.*;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import utils.CustomAdminDeserializer;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
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
        } finally {
            Base.close();
        }
    }
    public  static Integer registerHandler(InputStream in ){
        Base.open(
                "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
        Integer id = null;
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            String[] parameters = line.split("%1%1%");

            UserLoginData userLoginData = new UserLoginData();
            String userLoginDataString = parameters[0];
            userLoginData.fromMap(JsonHelper.toMap(userLoginDataString));

            Donor donor = new Donor();
            String donorString = parameters[1];
            donor.fromMap(JsonHelper.toMap(donorString));
            User donorFinal = new Donor(donor.getCNP(),donor.getName(), LocalDate.of(1911,12,13),donor.getMail(),donor.getPhone(),donor.getBloodGroup(),donor.getWeight());
            LazyList<Donor> listDonor =Donor.where("CNP = ? AND Mail = ?",donor.getCNP(),donor.getMail());
            if(listDonor.size()>0){
                return 409;
            }
            else{
                LazyList<UserLoginData> listUser= UserLoginData.where("Username = ? and UserType = ?",userLoginData.getUsername(),userLoginData.getUserType());
                if (listUser.size()>0)
                    return 409;
                else {
                   if(donorFinal.saveIt()) {
                       LazyList<Donor> list = Donor.where("CNP = ?",donorFinal.getCNP());
                       for (Donor donor1:list) {
                           id=donor1.getIdU();
                       }

                       UserLoginData fianlUser = new UserLoginData();
                       fianlUser.set("Username",userLoginData.getUsername());
                       fianlUser.set("Password",userLoginData.getPassword());
                       fianlUser.set("UserType",userLoginData.getUserType());
                       fianlUser.setId(id);
                       fianlUser.insert();
                       return 201;

                   }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 401;
        }
        System.out.print("Aiciex1");
        return 401;

    }

    public static String statusDonationScheduleUpdateHandler(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String idDS = params[0].split("=")[1];
            String idDC = params[1].split("=")[1];
            String status = params[2].split("=")[1];
            System.out.println(idDS+" "+idDC+" "+status);
            //// AICI
            Base.exec("Update DonationSchedule Set Status = ? Where IdDS = ? and IdDC = ?",status,idDS,idDC);
            return "Success";
        }
        catch (Exception e) {
            return e.getMessage();

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

    public static void addAdmin(InputStream in){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String cnp = params[0].split("=")[1];
            String nume = params[1].split("=")[1];
            String data = params[2].split("=")[1];
            String mail = params[3].split("=")[1];
            String phone = params[4].split("=")[1];
            String user = params[5].split("=")[1];
            String pass = params[6].split("=")[1];
            String type = params[7].split("=")[1];
            Admin a=new Admin();
            a.setCnp(cnp);
            a.setName(nume);
            a.setBirthday(data);
            a.setMail(mail);
            a.setPhone(phone);
            a.saveIt();
            Admin a2=Admin.findFirst("CNP=?",cnp);
            System.out.println(a2.getId());
            UserLoginData u=new UserLoginData();
            u.setId(a2.getId());
            u.setUsername(user);
            u.setPassword(pass);
            u.setType(type);
            u.insert();
            System.out.println(10);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Base.close();
        }
    }

    public static void addMedic(InputStream in){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String cnp = params[0].split("=")[1];
            String nume = params[1].split("=")[1];
            String data = params[2].split("=")[1];
            String mail = params[3].split("=")[1];
            String phone = params[4].split("=")[1];
            String user = params[5].split("=")[1];
            String pass = params[6].split("=")[1];
            String spital = params[7].split("=")[1];
            //String type = params[7].split("=")[1];
            Medic m=new Medic();
            m.setCnp(cnp);
            m.setName(nume);
            m.setBirthday(data);
            m.setMail(mail);
            m.setPhone(phone);
            m.setIdH(Integer.parseInt(spital));
            m.insert();
            Medic m2=Medic.findFirst("CNP=?",cnp);
            UserLoginData u=new UserLoginData();
            u.setId(m2.getIdU());
            u.setUsername(user);
            u.setPassword(pass);
            u.setType("Medic");
            u.insert();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Base.close();
        }
    }

    public static void addTCP(InputStream in){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String cnp = params[0].split("=")[1];
            String nume = params[1].split("=")[1];
            String data = params[2].split("=")[1];
            String mail = params[3].split("=")[1];
            String phone = params[4].split("=")[1];
            String user = params[5].split("=")[1];
            String pass = params[6].split("=")[1];
            String spital = params[7].split("=")[1];
            //String type = params[7].split("=")[1];
            TCP tcp=new TCP();
            tcp.setCnp(cnp);
            tcp.setName(nume);
            tcp.setBirthday(data);
            tcp.setMail(mail);
            tcp.setPhone(phone);
            tcp.setIdDC(Integer.parseInt(spital));
            tcp.insert();
            TCP tcp2=TCP.findFirst("CNP=?",cnp);
            UserLoginData u=new UserLoginData();
            u.setId(tcp2.getIdU());
            u.setUsername(user);
            u.setPassword(pass);
            u.setType("TCP");
            u.insert();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Base.close();
        }
    }

    public static String getHospitals(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String type = params[0].split("=")[1];
            List<Integer> list=new ArrayList<>();
            Gson gson=new Gson();
            if (type.equals("Medic")){
                List<Hospital> hospitals=Hospital.findAll();
                for (Hospital h:hospitals)
                    list.add(h.getIdH());
                String transfer=gson.toJson(list);
                return transfer;
            }
            else if(type.equals("TCP")) {
                List<DonationCenter> centers = DonationCenter.findAll();
                for (DonationCenter c : centers)
                    list.add(c.getIdDC());
                String transfer = gson.toJson(list);
                return transfer;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
            List<SuffersOf> suf=SuffersOf.where("IdU=?",idU);
            long nr =Illness.count();
            int i=(int) nr;
            if (suf.size() == 0 || suf.get(suf.size()-1).getIdI()<15){
                Illness ill=new Illness();
                ill.setIdI(i+1);
                ill.setName("Suplimentar");
                ill.setDescription(additional);
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

    public static String diseasesChecksHandler(InputStream in){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String idU = params[0].split("=")[1];
            List<SuffersOf> suf=SuffersOf.where("IdU=?",idU);
            List<Integer> list=new ArrayList<>();
            Gson gson = new Gson();
            for (SuffersOf s:suf){
                list.add(s.getIdI());
            }
            String transfer=gson.toJson(list);
            return transfer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
            List<Adress> ad=new ArrayList<>();
            List<SuffersOf> suf=SuffersOf.where("IdU=?",idU);
            Donor d=donors.get(0);
            UserLoginData uld=users.get(0);
            if (adresses.size() != 0) {
                Adress a = adresses.get(0);
                if (suf.size()!=0 && suf.get(suf.size()-1).getIdI()>=15)
                  {
                    int idI=suf.get(suf.size()-1).getIdI();
                    List<Illness> illnesses=Illness.where("IdI=?",idI);
                    Illness i=illnesses.get(0);
                    String urlParameters = String.format("street=%s&streetNr=%s&blockNr=%s&nr=%s&entrance=%s&floor=%s&apartNr=%s&city=%s&county=%s&country=%s&weight=%s&phone=%s&mail=%s&cnp=%s&nume=%s&date=%s&sange=%s&user=%s&nr=%s&desc=%s", d.getCNP(), d.getName(), d.getBirthday(), d.getBloodGroup(), uld.getUsername(), Double.toString(d.getWeight()), d.getPhone(), d.getMail(), "1", a.getStreet(), Integer.toString(a.getStreetNr()), Integer.toString(a.getBlock()), a.getEntrance(), Integer.toString(a.getFloor()), Integer.toString(a.getApartNr()), a.getCity(), a.getCounty(), a.getCountry(),"2", i.getDescription());
                    return urlParameters;
                }
                else{
                    String urlParameters = String.format("street=%s&streetNr=%s&blockNr=%s&nr=%s&entrance=%s&floor=%s&apartNr=%s&city=%s&county=%s&country=%s&weight=%s&phone=%s&mail=%s&cnp=%s&nume=%s&date=%s&sange=%s&user=%s&nr=%s",d.getCNP(), d.getName(), d.getBirthday(), d.getBloodGroup(), uld.getUsername(), Double.toString(d.getWeight()), d.getPhone(), d.getMail(), "1", a.getStreet(), Integer.toString(a.getStreetNr()), Integer.toString(a.getBlock()), a.getEntrance(), Integer.toString(a.getFloor()), Integer.toString(a.getApartNr()), a.getCity(), a.getCounty(), a.getCountry(), d.getCNP(), d.getName(), d.getBirthday(), d.getBloodGroup(), uld.getUsername(),"1");
                    return urlParameters;
                }
            }
            else {
                String urlParameters=String.format("cnp=%s&nume=%s&date=%s&sange=%s&user=%s&a=%s&b=%s&c=%s&d=%s",d.getCNP(), d.getName(), d.getBirthday(), d.getBloodGroup(), uld.getUsername(),Double.toString(d.getWeight()),d.getPhone(),d.getMail(),"0");
                return urlParameters;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Base.close();
        }
    }

    public static void updateDonor(InputStream in){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String cnp = params[0].split("=")[1];
            String cnpNou = params[1].split("=")[1];
            String nume = params[2].split("=")[1];
            String data = params[3].split("=")[1];
            String mail = params[4].split("=")[1];
            String phone = params[5].split("=")[1];
            String blood = params[6].split("=")[1];
            String weight = params[7].split("=")[1];
            Donor.update("CNP=?,Name=?,Birthday=?,Mail=?,Phone=?,BloodGroup=?,Weight=?","CNP=?",cnpNou,nume,data,mail,phone,blood,weight,cnp);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            Base.close();
        }
    }

    public static String updateMedicHospital(InputStream in){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String cnp = params[0].split("=")[1];
            String spital = params[1].split("=")[1];
            String send="no";
            try {
                Medic.update("IdH=?", "CNP=?", spital, cnp);
                send = "yes";
            }catch (Exception e){}
            return send;

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Base.close();
        }
    }

    public static String updateTCPCenter(InputStream in){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String cnp = params[0].split("=")[1];
            String center = params[1].split("=")[1];
            String send="no";
            try {
                TCP.update("IdDC=?", "CNP=?", center, cnp);
                send = "yes";
            }catch (Exception e){}
            return send;

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Base.close();
        }
    }

    public static void updateAdmin(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String cnp = params[0].split("=")[1];
            String cnpNou = params[1].split("=")[1];
            String nume = params[2].split("=")[1];
            String data = params[3].split("=")[1];
            String mail = params[4].split("=")[1];
            String phone = params[5].split("=")[1];
            Admin a=Admin.findFirst("CNP=?",cnp);
            Admin.update("CNP=?,Name=?,Birthday=?,Mail=?,Phone=?","CNP=?",cnpNou,nume,data,mail,phone,cnp);
            System.out.println(cnp);

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            Base.close();
        }
    }

    public static void deleteUser(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String type = params[0].split("=")[1];
            String cnp = params[1].split("=")[1];
            if (type.equals("Donor")) {
                Donor donor = Donor.findFirst("CNP = ?", cnp);
                UserLoginData u = UserLoginData.findFirst("IdLD=?", donor.getId());
                u.delete();
            } else if (type.equals("Admin")) {
                Adress adress;
                Admin admin = Admin.findFirst("CNP = ?", cnp);
                if (admin.getIdA() != null) {
                    adress = Adress.findFirst("IdA=?", admin.getIdA());
                    UserLoginData u = UserLoginData.findFirst("IdLD=?", admin.getId());
                    u.delete();
                    admin.delete();
                    adress.delete();
                } else {
                    UserLoginData u = UserLoginData.findFirst("IdLD=?", admin.getId());
                    u.delete();
                    admin.delete();
                }
            } else if (type.equals("Medic")) {
                Adress adress;
                Medic medic = Medic.findFirst("CNP = ?", cnp);
                if (medic.getIdA() != null) {
                    adress = Adress.findFirst("IdA=?", medic.getIdA());
                    UserLoginData u = UserLoginData.findFirst("IdLD=?", medic.getId());
                    u.delete();
                    medic.delete();
                    adress.delete();
                } else {
                    UserLoginData u = UserLoginData.findFirst("IdLD=?", medic.getId());
                    u.delete();
                    medic.delete();
                }
            } else if (type.equals("TCP")) {
                Adress adress;
                TCP tcp = TCP.findFirst("CNP = ?", cnp);
                if (tcp.getIdA() != null) {
                    adress = Adress.findFirst("IdA=?", tcp.getIdA());
                    UserLoginData u = UserLoginData.findFirst("IdLD=?", tcp.getId());
                    u.delete();
                    tcp.delete();
                    adress.delete();
                } else {
                    UserLoginData u = UserLoginData.findFirst("IdLD=?", tcp.getId());
                    u.delete();
                    tcp.delete();
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            Base.close();
        }
    }

    public static String getUsers(InputStream in){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String type = params[0].split("=")[1];
            if(type.equals("Donor")) {
                LazyList<Donor> lazy = Donor.findBySQL("Select * from Users u inner join LoginData d on u.idU=d.idLD where d.UserType=?", type);
                String response = lazy.toJson(false);
                return response;
            }
            else if (type.equals("Admin")){
                LazyList<Admin> lazy=Admin.findBySQL("Select * from Users u inner join LoginData d on u.idU=d.idLD where d.UserType=?", type);
                String response = lazy.toJson(false);
                return response;
            }
            else if (type.equals("Medic")){
                LazyList<Medic> lazy=Medic.findBySQL("Select * from Users u inner join LoginData d on u.idU=d.idLD where d.UserType=?", type);
                String response = lazy.toJson(false);
                return response;
            }
            else if (type.equals("TCP")) {
                LazyList<TCP> lazy = TCP.findBySQL("Select * from Users u inner join LoginData d on u.idU=d.idLD where d.UserType=?", type);
                String response = lazy.toJson(false);
                return response;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Base.close();
        }
    }

    public static String getUsernames(InputStream in){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            List<UserLoginData> loginData = UserLoginData.findAll();
            String line = reader.readLine();
            String[] params = line.split("&");
            String type = params[0].split("=")[1];
            List<String> users=new ArrayList<>();
            for (UserLoginData u:loginData){
                if(u.getUserType().equals(type)) {
                    users.add(u.getId().toString());
                    users.add(u.getUsername());
                }
            }
            Gson gson=new Gson();
            String transfer=gson.toJson(users);
            return transfer;
        } catch (Exception e) {
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
    public static BloodDemand addDemandHandler(InputStream in){
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(in))){
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line=reader.readLine();
            BloodDemand bd = new BloodDemand();
            String[] argumente = line.split("&");
            Integer idH=Integer.parseInt(argumente[0].split("=")[1]);
            String neededType=argumente[1].split("=")[1];
            String description=argumente[2].split("=")[1];
            String priority=argumente[3].split("=")[1];
            Integer quantity=Integer.parseInt(argumente[4].split("=")[1]);
            bd.set("IdH", idH).set("NeededType", neededType).set("Description", description).set("Priority",priority).set("Quantity",quantity).saveIt();
            return bd;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
        finally {

            Base.close();
        }

    }

    public static String addDonationHandler(InputStream in) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String name = params[0].split("=")[1];
            String cnp=params[1].split("=")[1];
            String status=params[2].split("=")[1];
            Double quantity=Double.parseDouble(params[3].split("=")[1]);
            Donor user=Donor.findFirst("CNP = ?",cnp);
            Donation donation= Donation.create("IdDC",user.get("IdDC"),"Quantity",quantity,"Status",status,"IdU",user.getIdU());
            if(donation.save()){
                return "Success";
            }
            else {
                return donation.errors().toString();
            }


        } catch (Exception e) {
            return e.getMessage();
        }
        finally {
            Base.close();
        }

    }

    public static String modifyDonationHandler(InputStream in) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            String status = params[0].split("=")[1];
            Integer idD =Integer.parseInt( params[1].split("=")[1]);
            Donation donation=Donation.findFirst("IdD = ?",idD);
            donation.set("Status",status);
        //    donation.saveIt();
            Base.exec("UPDATE Donation SET Status = ? WHERE IdD = ?",status,donation.getIdD());
            return "Success";

        } catch (Exception e) {
            return e.getMessage();
        }
        finally {
            Base.close();
        }
    }

    public static Integer modifyDemandHandler(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            System.out.println("Intra aici si imi afiseaza "+line);
            String[] argumente = line.split("&");
            List<BloodDemand> list = BloodDemand.where("idH = ?",Integer.parseInt(argumente[1].split("=")[1]));
            if(list==null){
                System.out.println("Intra aicisaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                return 2;}
            System.out.println("Face update-ul!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            String updateString=argumente[1].split("=")[0]+" = ?, " +argumente[2].split("=")[0] + " = ?, " +
                    argumente[3].split("=")[0] +" = ?, " + argumente[4].split("=")[0] +" = ?, " + argumente[5].split("=")[0]+" = ?";
            Integer idH=Integer.parseInt(argumente[1].split("=")[1]);
            String neededType=argumente[2].split("=")[1];
            String description=argumente[3].split("=")[1];
            String priority=argumente[4].split("=")[1];
            Integer quantity=Integer.parseInt(argumente[5].split("=")[1]);
            String[] conditie=argumente[0].split("=");
            String conditie1=conditie[0]+" = ?";
            Integer conditie2=Integer.parseInt(conditie[1]);
            BloodDemand.update(updateString, conditie1,idH,neededType,description,priority,quantity,conditie2);
            return 1;

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        } finally {
            Base.close();
        }
    }

    public static LazyList<BloodDemand> findDemands(InputStream in){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");

            String id = reader.readLine();
            System.out.println(id);
            Integer idM=Integer.parseInt(id);
            LazyList<Medic> m=Medic.findAll();
            Integer idH=0;

            for(Medic mu:m){
                System.out.println(m.get(0).getIdU());
                if(mu.getIdU().equals(idM))
                    idH=mu.getIdH();
            }
            if(idH==0)
                return null;
            LazyList<BloodDemand> list=BloodDemand.where("IdH = ?",idH);
            if(list.size()==0)
                return null;
            return list;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            Base.close();
        }
    }
}
