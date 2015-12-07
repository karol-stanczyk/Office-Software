package com.karol.presentation.forms.contracts.contractlist;

import com.karol.model.Contract;
import com.karol.presentation.forms.AbstractTableRow;
import com.karol.utils.Bundles;
import com.karol.utils.DateFormatter;

public class ContractListTableRow extends AbstractTableRow {

    private Contract contract;

    private String period;
    private String number;
    private String validityPeriodFrom;
    private String validityPeriodTo;
    private String paymentDate;

    public ContractListTableRow(Contract contract, int index) {
        setIndex(index);
        this.contract = contract;
        this.period = Bundles.get(contract.getPeriod().name().toLowerCase());
        this.number = contract.getNumber();
        this.validityPeriodFrom = DateFormatter.toString(contract.getValidityPeriodFrom());
        this.validityPeriodTo = DateFormatter.toString(contract.getValidityPeriodTo());
        this.paymentDate = DateFormatter.toString(contract.getPaymentDate());
    }

    public String getPeriod() {
        return period;
    }

    public String getNumber() {
        return number;
    }

    public String getValidityPeriodFrom() {
        return validityPeriodFrom;
    }
    public String getValidityPeriodTo() {
        return validityPeriodTo;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public Contract getContract() {
        return contract;
    }

    @Override
    public String toString() {
        return number;
    }
}
