package model.DTO;

import com.google.gson.annotations.SerializedName;
import model.AvailableBloodProducts;
import model.DonationCenter;

import java.util.Date;
import java.util.List;

public class DonationCenterDTO {




    @SerializedName("centername")
    private String CenterName;
    @SerializedName("phonenumber")
    private String PhoneNumber;
    @SerializedName("adress")
    private String Adress;
    @SerializedName("id")
    private Integer id;
    @SerializedName("idD")
    private Integer idD;
    @SerializedName("producttype")
    private String ProductType;
    @SerializedName("validuntil")
    private Date ValidUntil;
    @SerializedName("quantity")
    private Double Quantity;

    @SerializedName("receivername")
    private String receivername;


    @SerializedName("bloodgroup")
    private String bloodGroup;

    public DonationCenterDTO(String BloodGroup,String receivername,Integer idD,Integer id,String CenterName,String PhoneNumber, String Adress,String ProductType,Date ValidUntil,Double Quantity) {
        this.bloodGroup=BloodGroup;
        this.receivername=receivername;
        this.idD=idD;
        this.id=id;
        this.CenterName=CenterName;
        this.PhoneNumber=PhoneNumber;
        this.Adress=Adress;
        this.ProductType=ProductType;
        this.ValidUntil=ValidUntil;
        this.Quantity=Quantity;
    }


    public String getCenterName() {
            return this.CenterName;
        }

    public String getPhoneNumber() {
        return this.PhoneNumber;
    }



    public void setCenterName(String centerName) {
        CenterName = centerName;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }


    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public Date getValidUntil() {
        return ValidUntil;
    }

    public void setValidUntil(Date validUntil) {
        ValidUntil = validUntil;
    }

    public Double getQuantity() {
        return Quantity;
    }

    public void setQuantity(Double quantity) {
        Quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getIdD() {
        return idD;
    }

    public void setIdD(Integer idD) {
        this.idD = idD;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }


    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }



}
