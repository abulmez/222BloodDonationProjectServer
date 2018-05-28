package model.dto;

public class DonationReceiverNameBloodGroupDTO {
    private Integer idD;
    private String receiverName;
    private String bloodGroup;

    public DonationReceiverNameBloodGroupDTO(Integer idD, String receiverName, String bloodGroup) {
        this.idD = idD;
        this.receiverName = receiverName;
        this.bloodGroup = bloodGroup;
    }

    public Integer getIdD() {
        return idD;
    }

    public void setIdD(Integer idD) {
        this.idD = idD;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof DonationReceiverNameBloodGroupDTO){
            DonationReceiverNameBloodGroupDTO other = (DonationReceiverNameBloodGroupDTO)o;
            return other.idD.equals(this.idD);
        }
        else return false;
    }
}
