package com.karol.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = Contractor.FIND_ALL, query = "select u from Contractor u"),
        @NamedQuery(name = Contractor.FIND_BY_NAME, query = "select u from Contractor u where u.name = :name"),
        @NamedQuery(name = Contractor.FIND_BY_PESEL, query = "select u from Contractor u where u.pesel = :pesel"),
        @NamedQuery(name = Contractor.FIND_BY_LAST_NAME, query = "select u from Contractor u where u.lastName = :lastName"),
        @NamedQuery(name = Contractor.FIND_BY_NIP, query = "select u from Contractor u where u.nip = :nip")
})
public class Contractor implements Serializable {

    public static final String FIND_ALL = "Contractor.FIND_ALL";
    public static final String FIND_BY_NAME = "Contractor.FIND_BY_NAME";
    public static final String FIND_BY_PESEL = "Contractor.FIND_BY_PESEL";
    public static final String FIND_BY_LAST_NAME = "Contractor.FIND_BY_LAST_NAME";
    public static final String FIND_BY_NIP = "Contractor.FIND_BY_NIP";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;
    @Column
    private String lastName;
    @Column
    private String address;

    private String pesel;
    private String nip;

    @OneToMany(mappedBy = "contractor", cascade = CascadeType.ALL)
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
