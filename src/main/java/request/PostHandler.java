package request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;
import model.DTO.BloodRequestHospitalDTO;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
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


    public  static Integer registerHandler(InputStream in ){
        try{
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String[] parameters = reader.readLine().split("%1%1%");
            UserLoginData userLoginData = new UserLoginData();
            userLoginData.fromMap(JsonHelper.toMap(parameters[0]));
            Donor donor = new Donor();
            System.out.print(parameters[1]);
            User donorFinal = JsonHelper.DonorJsonParser(parameters[1]);
            LazyList<Donor> listDonor = Donor.where("CNP = ? AND Mail = ?",donor.getCNP(),donor.getMail());
            LazyList<UserLoginData> listUser= UserLoginData.where("Username = ? and UserType = ?",userLoginData.getUsername(),userLoginData.getUserType());
            if(listDonor.size()>0 || listUser.size()>0){
                return 409;
            }
            else {
                    if(donorFinal.saveIt()) {
                        LazyList<Donor> list = Donor.where("CNP = ?",donorFinal.getCNP());
                        Integer id = list.get(0).getIdU();
                        UserLoginData fianlUser = new UserLoginData();
                        fianlUser.set("Username",userLoginData.getUsername());
                        fianlUser.set("Password",userLoginData.getPassword());
                        fianlUser.set("UserType",userLoginData.getUserType());
                        fianlUser.setId(id);
                        fianlUser.insert();
                        return 201;
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 401;
        }
        finally {
            Base.close();
        }
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
            //String idDC = params[1].split("=")[1];
            String status = params[1].split("=")[1];
            System.out.println(idDS+" "+status);
            //// AICI
            Base.exec("Update Reservation Set Status = ? Where IdDS = ?",status,idDS);
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
            List<Donor> donors= Donor.where("IdU=?",idU);
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
            Integer idU=Integer.parseInt(argumente[0].split("=")[1]);
            LazyList<Medic> list1= Medic.where("IdU = ?",idU);
            Integer idH=list1.get(0).getIdH();
            String neededType=argumente[1].split("=")[1];
            String description=argumente[2].split("=")[1];
            String priority=argumente[3].split("=")[1];
            Double quantity=Double.parseDouble(argumente[4].split("=")[1]);
            String bloodType=argumente[5].split("=")[1];
            bd.set("IdH", idH).set("NeededType", neededType).set("Description", description).set("Priority",priority).set("Quantity",quantity).set("BloodProductType",bloodType).saveIt();
            return bd;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
        finally{
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
            System.out.println(line);
            Integer idU =Integer.parseInt(params[0].split("=")[1]);
            String cnp=params[1].split("=")[1];
            String status=params[2].split("=")[1];
            Double quantity=Double.parseDouble(params[3].split("=")[1]);
            String receiverName;
            String[] receiverData=params[4].split("=");
            if (receiverData.length==2)
                receiverName=receiverData[1];
            else
                receiverName=null;
            Donor user= Donor.findFirst("CNP = ?",cnp);
            TCP tcp=TCP.findFirst("IdU = ?",idU);
            Donation donation= Donation.create("IdDC",tcp.get("IdDC"),"Quantity",quantity,"Status",status,"IdU",user.getIdU(),"ReceiverName",receiverName);
            donation.saveIt();
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        finally {
            Base.close();
        }

    }

    public static String addReportHandler(InputStream in) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            Integer id = Integer.parseInt(params[0].split("=")[1]);
            LocalDate data=LocalDate.parse(params[1].split("=")[1]);
            Boolean status=Boolean.parseBoolean(params[2].split("=")[1]);
            String observatii=params[3].split("=")[1];
            DonationReport report= DonationReport.create("IdDR",id,"SamplingDate",data,"BloodStatus",status,"BloodReport",observatii);
            System.out.println(report.getIdName());
            report.saveIt();
            return "Success";

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        finally {
            Base.close();
        }

    }

    public static String addBloodProductHandler(InputStream in) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            String[] params = line.split("&");
            Integer idD = Integer.parseInt(params[0].split("=")[1]);
            String type=params[1].split("=")[1];
            LocalDate date=LocalDate.parse(params[2].split("=")[1]);
            Double quantity=Double.parseDouble(params[3].split("=")[1]);
            AvailableBloodProducts bloodProduct= AvailableBloodProducts.create("IdD",idD,"ProductType",type,"ValidUntil",date,"Quantity",quantity);
            bloodProduct.saveIt();
            return "Success";

        } catch (Exception e) {
            e.printStackTrace();
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
            Donation.update("Status=?","IdD=?",status,idD);
            Donation donation=Donation.findFirst("IdD = ?",idD);
            System.out.println(donation.getStatus());
            return "Success";

        } catch (Exception e) {
            e.printStackTrace();
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
            String updateString="IdH = ?, " +argumente[2].split("=")[0] + " = ?, " +
                    argumente[3].split("=")[0] +" = ?, " + argumente[4].split("=")[0] +" = ?, " + argumente[5].split("=")[0]+" = ?,"+argumente[6].split("=")[0]+" = ?";
            Integer idU=Integer.parseInt(argumente[1].split("=")[1]);
            LazyList<Medic> list1= Medic.where("IdU = ?",idU);
            Integer idH=list1.get(0).getIdH();
            String neededType=argumente[2].split("=")[1];
            String description=argumente[3].split("=")[1];
            String priority=argumente[4].split("=")[1];
            Double quantity=Double.parseDouble(argumente[5].split("=")[1]);
            String bloodType=argumente[6].split("=")[1];
            String[] conditie=argumente[0].split("=");
            String conditie1=conditie[0]+" = ?";
            Integer conditie2=Integer.parseInt(conditie[1]);
            BloodDemand.update(updateString, conditie1,idH,neededType,description,priority,quantity,bloodType,conditie2);
            return 1;

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        } finally {
            Base.close();
        }
    }

    public static List<BloodDemandDTO> findDemands(InputStream in){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            Double suma;
            BloodDemandDTO dto;
            List<BloodDemandDTO> listBD=new ArrayList<>();
            String idU = reader.readLine();
            LazyList<Medic> list1= Medic.where("IdU = ?",idU);
            Integer idH=list1.get(0).getIdH();
            LazyList<BloodDemand> list=BloodDemand.where("IdH = ?",idH);
            if(list.size()==0)
                return null;
            for(BloodDemand d:list){
                suma=0.0;
                List<Donation> donations=Donation.findBySQL("SELECT Donation.* FROM BloodDemand, BloodProductsShippment" +
                        ", AvailableBloodProducts, Donation WHERE BloodDemand.IdBd=BloodProductsShippment.IdBd AND BloodProductsShippment.IdBP=AvailableBloodProducts.IdBP " +
                        "AND AvailableBloodProducts.IdD=Donation.IdD AND BloodDemand.IdBd="+d.getIdBd());


                for(Donation don : donations){
                    suma=suma+don.getQuantity();
                }
                if(Math.abs(suma-d.getQuantity())<=0.0000001)
                    listBD.add(new BloodDemandDTO(d,suma,"Livrata"));
                else if(!(suma.equals(0.0)))
                    listBD.add(new BloodDemandDTO(d,suma,"Initiata"));
                else
                    listBD.add(new BloodDemandDTO(d,suma,"Plasata"));

            }

            return listBD;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            Base.close();
        }
    }

    public static String removeDemandHandler(InputStream in){
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(in))){
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line=reader.readLine();
            Integer idBd=Integer.parseInt(line.split("=")[1]);
            BloodDemand e = BloodDemand.findFirst("IdBd = ?", idBd);
            String nume=e.getDescription();
            e.delete();
            return "Cererea cu id-ul: "+idBd+" pentru pacientul: "+nume+" a fost stearsa";
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        finally{
            Base.close();
        }
    }
    public static String vizualizareLivrariHandler(InputStream in){
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(in))){
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line=reader.readLine();

            Integer idBd=Integer.parseInt(line.split("=")[1]);
            List<DonationCenter> donations=DonationCenter.findBySQL("SELECT DonationCenter.* FROM BloodDemand, BloodProductsShippment" +
                    ", AvailableBloodProducts, Donation,DonationCenter WHERE BloodDemand.IdBd=BloodProductsShippment.IdBd AND BloodProductsShippment.IdBP=AvailableBloodProducts.IdBP " +
                    "AND AvailableBloodProducts.IdD=Donation.IdD AND Donation.IdDC=DonationCenter.IdDC AND BloodDemand.IdBd="+idBd);
            String centre="Centrele de la care s-au primit donatii: ";
            for(DonationCenter dc : donations){
                centre=centre+dc.getCenterName()+", "+dc.getPhoneNumber()+"; ";
            }
            centre=centre.substring(0, centre.length()-2);
            return centre;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        finally{
            Base.close();
        }
    }

    public static List<BloodDemandDTO> cereriPlasateHandler(InputStream in){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            Double suma;
            BloodDemandDTO dto;
            List<BloodDemandDTO> listBD=new ArrayList<>();
            String idU = reader.readLine();
            LazyList<Medic> list1= Medic.where("IdU = ?",idU);
            Integer idH=list1.get(0).getIdH();
            LazyList<BloodDemand> list=BloodDemand.where("IdH = ?",idH);
            if(list.size()==0)
                return null;
            for(BloodDemand d:list){
                suma=0.0;
                List<Donation> donations=Donation.findBySQL("SELECT Donation.* FROM BloodDemand, BloodProductsShippment" +
                        ", AvailableBloodProducts, Donation WHERE BloodDemand.IdBd=BloodProductsShippment.IdBd AND BloodProductsShippment.IdBP=AvailableBloodProducts.IdBP " +
                        "AND AvailableBloodProducts.IdD=Donation.IdD AND BloodDemand.IdBd="+d.getIdBd());


                for(Donation don : donations){
                    suma=suma+don.getQuantity();
                }
                if(Math.abs(suma-d.getQuantity())<=0.0000001)
                    continue;
                else if(!(suma.equals(0.0)))
                    listBD.add(new BloodDemandDTO(d,suma,"Initiata"));
                else
                    listBD.add(new BloodDemandDTO(d,suma,"Plasata"));

            }

            return listBD;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            Base.close();
        }
    }

    public static List<BloodDemandDTO> cereriLivrateHandler(InputStream in){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            Double suma;
            BloodDemandDTO dto;
            List<BloodDemandDTO> listBD=new ArrayList<>();
            String idU = reader.readLine();
            LazyList<Medic> list1= Medic.where("IdU = ?",idU);
            Integer idH=list1.get(0).getIdH();
            LazyList<BloodDemand> list=BloodDemand.where("IdH = ?",idH);
            if(list.size()==0)
                return null;
            for(BloodDemand d:list){
                suma=0.0;
                List<Donation> donations=Donation.findBySQL("SELECT Donation.* FROM BloodDemand, BloodProductsShippment" +
                        ", AvailableBloodProducts, Donation WHERE BloodDemand.IdBd=BloodProductsShippment.IdBd AND BloodProductsShippment.IdBP=AvailableBloodProducts.IdBP " +
                        "AND AvailableBloodProducts.IdD=Donation.IdD AND BloodDemand.IdBd="+d.getIdBd());


                for(Donation don : donations){
                    suma=suma+don.getQuantity();
                }
                if(Math.abs(suma-d.getQuantity())<=0.0000001)
                    listBD.add(new BloodDemandDTO(d,suma,"Livrata"));


            }

            return listBD;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            Base.close();
        }
    }


    public static Boolean deleteBloodProduct(InputStream requestBody) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            Integer idBD = Integer.parseInt(line.split("=")[1]);
            AvailableBloodProducts bloodProduct = AvailableBloodProducts.findById(idBD);
            bloodProduct.setDeleted(true);
            bloodProduct.saveIt();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Base.close();
        }
    }

    public static Boolean sendBloodProduct(InputStream requestBody) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            Integer idBP = Integer.parseInt(line.split("&")[0].split("=")[1]);
            Integer idBD = Integer.parseInt(line.split("&")[1].split("=")[1]);
            BloodProductsShippment shippment = new BloodProductsShippment(idBP,idBD);
            shippment.saveIt();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Base.close();
        }
    }

    public static Integer splitBloodProduct(InputStream requestBody) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            Base.open(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://localhost;database=222BloodDonationProjectDB;integratedSecurity=true", "TestUser", "123456789");
            String line = reader.readLine();
            Integer idBP = Integer.parseInt(line.split("&")[0].split("=")[1]);
            Double quantity = Double.parseDouble(line.split("&")[1].split("=")[1]);

            AvailableBloodProducts oldProduct = AvailableBloodProducts.findById(idBP);
            AvailableBloodProducts newProduct1 = new AvailableBloodProducts(oldProduct.getIdD(),oldProduct.getProductType(),oldProduct.getValidUntil(),oldProduct.getQuantity()-quantity);
            AvailableBloodProducts newProduct2 = new AvailableBloodProducts(oldProduct.getIdD(),oldProduct.getProductType(),oldProduct.getValidUntil(),quantity);
            oldProduct.delete();
            newProduct1.saveIt();
            newProduct2.saveIt();
            return Integer.parseInt(newProduct2.getId().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Base.close();
        }
    }
}
