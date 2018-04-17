package utils;

import model.Donation;
import model.Donor;
import model.TCP;
import org.javalite.activejdbc.LazyList;

import java.util.ArrayList;
import java.util.List;

public class DTOutils {
    public static DonationDTO getDTO(Donation donation){
        Donor user=Donor.findFirst("IdU = ?",donation.getIdU());
        System.out.println(user.get("CNP"));
        DonationDTO dto=new DonationDTO(donation.getIdD(),user.getName(),donation.getIdU(),user.get("cnp").toString(),donation.getStatus(),donation.getQuantity());
        return dto;
    }

    public static List<DonationDTO> getDTOs(LazyList<Donation> donations){
        List<DonationDTO> rez=new ArrayList<>();
        for (Donation d:donations){
            DonationDTO dto=getDTO(d);
            rez.add(dto);
        }
        return rez;
    }

    public static Donation fromDTO(DonationDTO donationDTO){
        TCP user=TCP.findFirst("IdU = ?",donationDTO.getIdU());
        Donation donation=new Donation(donationDTO.getIdD(),user.getIdDC(),donationDTO.getIdU(),donationDTO.getQuantity(),donationDTO.getStatus());
        return donation;
    }
}
