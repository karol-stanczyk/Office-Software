package com.karol.presentation.forms.contractor.contractorlist;

import com.karol.model.Contractor;
import com.karol.presentation.forms.AbstractTableRow;

public class ContractorTableRow extends AbstractTableRow {
    private Contractor contractor;

    private String name;
    private String lastName;
    private String address;
    private String pesel;
    private String nip;

    public ContractorTableRow(Contractor contractor, int index) {
        this.contractor = contractor;
        this.name = contractor.getName();
        this.lastName = contractor.getLastName();
        this.address = contractor.getAddress();
        this.pesel = contractor.getPesel();
        this.nip = contractor.getNip();
        this.index = index;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPesel() {
        return pesel;
    }

    public String getNip() {
        return nip;
    }

    @Override
    public String toString() {
        return "ContractorTableRow{" +
                "contractor=" + contractor +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", pesel='" + pesel + '\'' +
                ", nip='" + nip + '\'' +
                '}';
    }
}
