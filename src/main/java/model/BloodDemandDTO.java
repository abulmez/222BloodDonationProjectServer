package model;

import com.google.gson.annotations.SerializedName;

public class BloodDemandDTO {


    @SerializedName("idbd")
    private Integer idBd;
    @SerializedName("idh")
    private Integer idH;
    @SerializedName("neededtype")
    private String NeededType;

    @SerializedName("description")
    private String Description;

    @SerializedName("priority")
    private String Priority;

    @SerializedName("quantity")
    private Double Quantity;

    @SerializedName("bloodproducttype")
    private String BloodProductType;

    @SerializedName("delivered")
    private Double Delivered;

    @SerializedName("status")
    private String Status;
    public BloodDemandDTO(BloodDemand bd,Double cantitateL,String status){
        this.idBd=bd.getIdBd();
        this.idH=bd.getIdH();
        this.NeededType=bd.getNeededType();
        this.Description=bd.getDescription();
        this.Priority=bd.getPriority();
        this.Quantity=bd.getQuantity();
        this.BloodProductType=bd.getBloodProductType();
        this.Delivered=cantitateL;
        this.Status=status;
    }


    public Integer getIdBd() {
        return idBd;
    }

    public void setIdBd(Integer idBd) {
        this.idBd = idBd;
    }

    public Integer getIdH() {
        return idH;
    }

    public void setIdH(Integer idH) {
        this.idH = idH;
    }

    public String getNeededType() {
        return NeededType;
    }

    public void setNeededType(String neededType) {
        NeededType = neededType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public Double getQuantity() {
        return Quantity;
    }

    public void setQuantity(Double quantity) {
        Quantity = quantity;
    }

    public String getBloodProductType() {
        return BloodProductType;
    }

    public void setBloodProductType(String bloodProductType) {
        BloodProductType = bloodProductType;
    }

    public Double getDelivered() {
        return Delivered;
    }

    public void setDelivered(Double delivered) {
        Delivered = delivered;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


}
