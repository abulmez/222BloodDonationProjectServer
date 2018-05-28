package model;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class JsonHelper {
    public static Map toMap(String json) {
        final ObjectMapper MAPPER;
        MAPPER = new ObjectMapper();
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            return MAPPER.readValue(json, Map.class);
        } catch (IOException e) { throw new RuntimeException(e); }
    }
    public static Map[] toMaps(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, Map[].class);
        } catch (IOException e) { throw new RuntimeException(e); } }


    public static Donor DonorJsonParser(String jsonString){
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
        String cnp = jsonObject.get("cnp").getAsString();
        String name = jsonObject.get("name").getAsString();
        Integer year = jsonObject.getAsJsonObject("birthday").get("year").getAsInt();
        Integer month  = jsonObject.getAsJsonObject("birthday").get("month").getAsInt();
        Integer day = jsonObject.getAsJsonObject("birthday").get("day").getAsInt();
        LocalDate birtday = LocalDate.of(year,month,day);
        String email = jsonObject.get("mail").getAsString();
        String phone = jsonObject.get("phone").getAsString();
        String bloodGroup = jsonObject.get("bloodgroup").getAsString();
        Double weight = jsonObject.get("weight").getAsDouble();
        Donor donor = new Donor(cnp,name,birtday,email,phone,bloodGroup,weight);
        return donor;
    }

}