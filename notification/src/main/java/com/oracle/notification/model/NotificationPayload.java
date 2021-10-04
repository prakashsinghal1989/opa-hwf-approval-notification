package com.oracle.notification.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationPayload {
    private String processDefinitionName;
    private String taskUrl;
    private String invoiceTitle;
    private String invoiceDate;
    private String invoiceAmount;
    private InvoiceCreator invoiceCreator;
    private String currency;
    private Assignee assignee;
    private String invoiceDescription;
    private AdditionalInvoiceDetails additionalInvoiceDetails;

    private String processName;
    private String subType;
    private String creatorLevel;

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getCreatorLevel() {
        return creatorLevel;
    }

    public void setCreatorLevel(String creatorLevel) {
        this.creatorLevel = creatorLevel;
    }

    public String getAssigneeLevel() {
        return assigneeLevel;
    }

    public void setAssigneeLevel(String assigneeLevel) {
        this.assigneeLevel = assigneeLevel;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    private String assigneeLevel;
    private String outcome;

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public InvoiceCreator getInvoiceCreator() {
        return invoiceCreator;
    }

    public void setInvoiceCreator(InvoiceCreator invoiceCreator) {
        this.invoiceCreator = invoiceCreator;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }



    public String getInvoiceDescription() {
        return invoiceDescription;
    }

    public void setInvoiceDescription(String invoiceDescription) {
        this.invoiceDescription = invoiceDescription;
    }



    public Assignee getAssignee() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    public AdditionalInvoiceDetails getAdditionalInvoiceDetails() {
        return additionalInvoiceDetails;
    }

    public void setAdditionalInvoiceDetails(AdditionalInvoiceDetails additionalInvoiceDetails) {
        this.additionalInvoiceDetails = additionalInvoiceDetails;
    }
    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }
}

