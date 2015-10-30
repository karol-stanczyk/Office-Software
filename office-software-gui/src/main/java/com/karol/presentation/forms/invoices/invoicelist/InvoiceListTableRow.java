package com.karol.presentation.forms.invoices.invoicelist;

import com.karol.model.Invoice;
import com.karol.presentation.forms.AbstractTableRow;
import com.karol.utils.DateFormatter;
import com.karol.utils.NumberFormatter;

public class InvoiceListTableRow extends AbstractTableRow {

    private Invoice invoice;

    private String number;
    private String netValue;
    private String grossValue;
    private String VAT;
    private String creationDate;
    private String paymentDate;

    public InvoiceListTableRow(Invoice invoice, int index) {
        setIndex(index);
        this.invoice = invoice;
        this.number = invoice.getNumber();
        this.netValue = NumberFormatter.fromDouble(invoice.getNetValue());
        this.grossValue = NumberFormatter.fromDouble(invoice.getGrossValue());
        this.VAT = NumberFormatter.fromDoubleToSingleValue(invoice.getVAT());
        this.creationDate = DateFormatter.toString(invoice.getCreationDate());
        this.paymentDate = DateFormatter.toString(invoice.getPaymentDate());
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public String getNumber() {
        return number;
    }

    public String getNetValue() {
        return netValue;
    }

    public String getGrossValue() {
        return grossValue;
    }

    public String getVAT() {
        return VAT;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }
}
