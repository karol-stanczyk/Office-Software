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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contractor that = (Contractor) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (pesel != null ? !pesel.equals(that.pesel) : that.pesel != null) return false;
        if (nip != null ? !nip.equals(that.nip) : that.nip != null) return false;
        if (contractList != null ? !contractList.equals(that.contractList) : that.contractList != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (pesel != null ? pesel.hashCode() : 0);
        result = 31 * result + (nip != null ? nip.hashCode() : 0);
        result = 31 * result + (contractList != null ? contractList.hashCode() : 0);
        return result;
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
                ", contractList=" + contractList +
                '}';
    }
}
