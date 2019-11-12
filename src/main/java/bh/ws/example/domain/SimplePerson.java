package bh.ws.example.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SimplePerson {

    private BigDecimal partyId;
    private String name;
    private String lastName;
    private String citizenshipCode;
    private Timestamp birthdate;
    private String fatherName;
    private String gender;


    public SimplePerson(String name, String citizenshipCode) {
        this.name = name;
        this.citizenshipCode = citizenshipCode;
    }

    public SimplePerson(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCitizenshipCode() {
        return citizenshipCode;
    }

    public void setCitizenshipCode(String citizenshipCode) {
        this.citizenshipCode = citizenshipCode;
    }

    public BigDecimal getPartyId() {
        return partyId;
    }

    public void setPartyId(BigDecimal partyId) {
        this.partyId = partyId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "SimplePerson{" +
                "name='" + name + '\'' +
                ", citizenshipCode='" + citizenshipCode + '\'' +
                '}';
    }
}
