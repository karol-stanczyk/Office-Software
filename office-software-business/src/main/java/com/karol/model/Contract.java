package com.karol.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@XmlRootElement
public class Contract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private Period period;

    private String number;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date validityPeriodFrom;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date validityPeriodTo;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date paymentDate;

    @JsonIgnore
    @ManyToOne
    private Contractor contractor;

    @OneToMany(mappedBy = "contract")
    private List<Invoice> invoiceList;

    public Contract() {
        this.invoiceList = new ArrayList<>();
    }

    public Contract(Period period, String number, Date validityPeriodFrom, Date paymentDate) {
        this();
        this.period = period;
        this.number = number;
        this.validityPeriodFrom = validityPeriodFrom;
        this.paymentDate = paymentDate;
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

    public Date getValidityPeriodFrom() {
        return validityPeriodFrom;
    }

    public void setValidityPeriodFrom(Date validityPeriod) {
        this.validityPeriodFrom = validityPeriod;
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

    public Date getValidityPeriodTo() {
        return validityPeriodTo;
    }

    public void setValidityPeriodTo(Date validityPeriodTo) {
        this.validityPeriodTo = validityPeriodTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (id != contract.id) return false;
        if (period != contract.period) return false;
        if (number != null ? !number.equals(contract.number) : contract.number != null) return false;
        if (validityPeriodFrom != null ? !validityPeriodFrom.equals(contract.validityPeriodFrom) : contract.validityPeriodFrom != null)
            return false;
        if (validityPeriodTo != null ? !validityPeriodTo.equals(contract.validityPeriodTo) : contract.validityPeriodTo != null)
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
        result = 31 * result + (validityPeriodFrom != null ? validityPeriodFrom.hashCode() : 0);
        result = 31 * result + (validityPeriodTo != null ? validityPeriodTo.hashCode() : 0);
        result = 31 * result + (paymentDate != null ? paymentDate.hashCode() : 0);
        result = 31 * result + (contractor != null ? contractor.hashCode() : 0);
        result = 31 * result + (invoiceList != null ? invoiceList.hashCode() : 0);
        return result;
    }
}
