package model.dto;

public class BloodProductShipmentAddressDTO {
    private Integer idBP;
    private String street;
    private Integer streetNumber;
    private String city;
    private String country;
    private String hospitalName;
    private String hospitalPhone;

    public BloodProductShipmentAddressDTO(Integer idBP, String street, Integer streetNumber, String city, String country, String hospitalName, String hospitalPhone) {
        this.idBP = idBP;
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.country = country;
        this.hospitalName = hospitalName;
        this.hospitalPhone = hospitalPhone;
    }

    public Integer getIdBP() {
        return idBP;
    }

    public void setIdBP(Integer idBP) {
        this.idBP = idBP;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalPhone() {
        return hospitalPhone;
    }

    public void setHospitalPhone(String hospitalPhone) {
        this.hospitalPhone = hospitalPhone;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof BloodProductShipmentAddressDTO){
            return ((BloodProductShipmentAddressDTO) other).getIdBP().equals(getIdBP());
        }
        return false;
    }
}
