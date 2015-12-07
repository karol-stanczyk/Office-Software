package com.karol.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = Contractor.FIND_ALL, query = "select u from Contractor u"),
        @NamedQuery(name = Contractor.FIND_BY_NAME, query = "select u from Contractor u where u.name = :name"),
        @NamedQuery(name = Contractor.FIND_BY_PESEL, query = "select u from Contractor u where u.pesel = :pesel"),
        @NamedQuery(name = Contractor.FIND_BY_LAST_NAME, query = "select u from Contractor u where u.lastName = :lastName"),
        @NamedQuery(name = Contractor.FIND_BY_NIP, query = "select u from Contractor u where u.nip = :nip"),
        @NamedQuery(name = Contractor.DELETE_ALL, query = "delete from Contractor c")
})
@XmlRootElement
public class Contractor implements Serializable {

    public static final String FIND_ALL = "Contractor.FIND_ALL";
    public static final String FIND_BY_NAME = "Contractor.FIND_BY_NAME";
    public static final String FIND_BY_PESEL = "Contractor.FIND_BY_PESEL";
    public static final String FIND_BY_LAST_NAME = "Contractor.FIND_BY_LAST_NAME";
    public static final String FIND_BY_NIP = "Contractor.FIND_BY_NIP";
    public static final String DELETE_ALL = "Contractor.DELETE_ALL";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column
    @NotNull
    private String name;
    @NotNull
    @Column
    private String lastName;
    @NotNull
    @Column
    private String address;

    private String pesel = "";
    private String nip = "";

    @OneToMany(mappedBy = "contractor", cascade = CascadeType.ALL)
    private List<Contract> contractList;

    public Contractor() {
        this.contractList = new ArrayList<>();
    }

    public Contractor(String name, String lastName, String address) {
        this();
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.pesel = pesel;
        this.nip = nip;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public String toString() {
        return "Contractor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", pesel='" + pesel + '\'' +
                ", nip='" + nip + '\'' +
                '}';
    }
}
