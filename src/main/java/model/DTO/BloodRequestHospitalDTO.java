package model.DTO;

public class BloodRequestHospitalDTO {

    private Integer idBD;
    private String priority;
    private Double quantity;
    private String receiverName;
    private String hospitalName;
    private String hospitalPhone;
    private String hospitalAdress;

    public BloodRequestHospitalDTO(Integer idBD, String priority, Double quantity, String receiverName, String hospitalName, String hospitalPhone, String hospitalAdress) {
        this.idBD = idBD;
        this.priority = priority;
        this.quantity = quantity;
        this.receiverName = receiverName;
        this.hospitalName = hospitalName;
        this.hospitalPhone = hospitalPhone;
        this.hospitalAdress = hospitalAdress;
    }

    public Integer getIdBD() {
        return idBD;
    }

    public void setIdBD(Integer idBD) {
        this.idBD = idBD;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
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

    public String getHospitalAdress() {
        return hospitalAdress;
    }

    public void setHospitalAdress(String hospitalAdress) {
        this.hospitalAdress = hospitalAdress;
    }
}
