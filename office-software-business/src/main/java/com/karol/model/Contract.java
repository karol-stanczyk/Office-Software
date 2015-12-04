package com.karol.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Contract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private Period period;

    private String number;

    @Temporal(TemporalType.TIMESTAMP)
    private Date validityPeriod;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    @ManyToOne
    private Contractor contractor;

    @OneToMany(mappedBy = "contract")
    private List<Invoice> invoiceList;

    public Contract() {
        this.invoiceList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Date validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (id != contract.id) return false;
        if (period != contract.period) return false;
        if (number != null ? !number.equals(contract.number) : contract.number != null) return false;
        if (validityPeriod != null ? !validityPeriod.equals(contract.validityPeriod) : contract.validityPeriod != null)
            return false;
        if (paymentDate != null ? !paymentDate.equals(contract.paymentDate) : contract.paymentDate != null)
            return false;
        if (contractor != null ? !contractor.equals(contract.contractor) : contract.contractor != null) return false;
        if (invoiceList != null ? !invoiceList.equals(contract.invoiceList) : contract.invoiceList != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (validityPeriod != null ? validityPeriod.hashCode() : 0);
        result = 31 * result + (paymentDate != null ? paymentDate.hashCode() : 0);
        result = 31 * result + (contractor != null ? contractor.hashCode() : 0);
        result = 31 * result + (invoiceList != null ? invoiceList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", period=" + period +
                ", number='" + number + '\'' +
                ", validityPeriod=" + validityPeriod +
                ", paymentDate=" + paymentDate +
                ", contractor=" + contractor +
                ", invoiceList=" + invoiceList +
                '}';
    }
}
