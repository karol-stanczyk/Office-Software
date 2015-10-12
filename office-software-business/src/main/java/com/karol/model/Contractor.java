package com.karol.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@SuppressWarnings("unused")
@NamedQueries({
        @NamedQuery(name = Contractor.FIND_ALL, query = "select u from Contractor u"),
        @NamedQuery(name = Contractor.FIND_BY_NAME, query = "select u from Contractor u where u.name = :name"),
        @NamedQuery(name = Contractor.FIND_BY_PESEL, query = "select u from Contractor u where u.pesel = :pesel")
})
public class Contractor implements Serializable {

    public static final String FIND_ALL = "Contractor.FIND_ALL";
    public static final String FIND_BY_NAME = "Contractor.FIND_BY_NAME";
    public static final String FIND_BY_PESEL = "Contractor.FIND_BY_PESEL";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String lastName;
    private String address;
    private String pesel;
    private String nip;

    @OneToMany(mappedBy = "contractor")
    private List<Contract> contractList;

    public Contractor() {
        this.contractList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public List<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }
}
