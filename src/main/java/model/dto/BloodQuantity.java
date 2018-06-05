package model.dto;


public class BloodQuantity{
    private String productType;
    private String bloodGroup;
    private Double totalQuantity;

    public BloodQuantity (String productType,String bloodGroup,Double totalQuantity){
        this.productType=productType;
        this.bloodGroup=bloodGroup;
        this.totalQuantity=totalQuantity;
    }

    public String getProductType() {
        return productType;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public Double getTotalQuantity() {
        return totalQuantity;
    }
}
