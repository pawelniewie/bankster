package com.pawelniewiadomski.budget.pageobjects.mbank;

import org.joda.time.DateTime;

public class TransactionDescription {

    protected DateTime operationDate;
    protected DateTime accountDate;
    protected String operationDescription;
    protected Double amount;

    public TransactionDescription(DateTime operationDate, DateTime accountDate, String operationDescription, Double amount) {
        this.operationDate = operationDate;
        this.accountDate = accountDate;
        this.operationDescription = operationDescription;
        this.amount = amount;
    }

    public DateTime getOperationDate() {
        return operationDate;
    }

    public DateTime getAccountDate() {
        return accountDate;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public Double getAmount() {
        return amount;
    }
}
