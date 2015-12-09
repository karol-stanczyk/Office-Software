package com.karol.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = Transfer.MONTHLY_REPORT, query = "SELECT t FROM Transfer t WHERE t.transferDate BETWEEN :dateFrom AND :dateTo GROUP BY t.transferDate")
})
@XmlRootElement
public class Transfer implements Serializable {

    public static final String MONTHLY_REPORT = "Transfer.MONTHLY_REPORT";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double netValue;
    private double grossValue;
    private double VAT;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date transferDate;

    @JsonIgnore
    @ManyToOne
    private Invoice invoice;

    public Transfer() {
    }

    public Transfer(Date transferDate, double grossValue, double netValue) {
        this.transferDate = transferDate;
        this.grossValue = grossValue;
        this.netValue = netValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getNetValue() {
        return netValue;
    }

    public void setNetValue(double netValue) {
        this.netValue = netValue;
    }

    public double getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(double grossValue) {
        this.grossValue = grossValue;
    }

    public double getVAT() {
        return VAT;
    }

    public void setVAT(double VAT) {
        this.VAT = VAT;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
