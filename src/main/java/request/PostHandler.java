package request;

import model.*;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        }
        finally {
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


}
