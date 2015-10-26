package com.karol.presentation.forms.contracts.contractlist;

import com.karol.model.Contract;
import com.karol.presentation.forms.AbstractTableRow;
import com.karol.utils.Bundles;
import com.karol.utils.DateFormatter;

public class ContractListTableRow extends AbstractTableRow {

    private Contract contract;

    private String period;
    private String number;
    private String validityPeriod;
    private String paymentDate;

    public ContractListTableRow(Contract contract, int index) {
        setIndex(index);
        this.contract = contract;
        this.period = Bundles.get(contract.getPeriod().name().toLowerCase());
        this.number = contract.getNumber();
        this.validityPeriod = DateFormatter.toString(contract.getValidityPeriod());
        this.paymentDate = DateFormatter.toString(contract.getPaymentDate());
    }

    public String getPeriod() {
        return period;
    }

    public String getNumber() {
        return number;
    }

    public String getValidityPeriod() {
        return validityPeriod;
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
