package com.karol.presentation.forms.invoices.invoicelist;

import com.karol.model.Transfer;
import com.karol.presentation.forms.AbstractTableRow;
import com.karol.utils.DateFormatter;
import com.karol.utils.NumberFormatter;

public class TransferListTableRow extends AbstractTableRow {

    private Transfer transfer;

    private String netValue;
    private String grossValue;
    private String VAT;
    private String transferDate;

    public TransferListTableRow(Transfer transfer, int index) {
        setIndex(index);
        this.transfer = transfer;
        this.netValue = NumberFormatter.fromDouble(transfer.getNetValue());
        this.grossValue = NumberFormatter.fromDouble(transfer.getGrossValue());
        this.VAT = NumberFormatter.fromDoubleToSingleValue(transfer.getVAT());
        this.transferDate = DateFormatter.toString(transfer.getTransferDate());
    }

    public Transfer getTransfer() {
        return transfer;
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

    public String getTransferDate() {
        return transferDate;
    }
}
