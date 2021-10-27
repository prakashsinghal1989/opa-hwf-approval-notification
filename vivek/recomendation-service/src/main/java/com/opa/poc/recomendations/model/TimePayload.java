package com.opa.poc.recomendations.model;

public class TimePayload {
    private String processName;

    @Override
    public String toString() {
        return "TimePayload{" +
                "processName='" + processName + '\'' +
                ", category='" + category + '\'' +
                ", amount='" + amount + '\'' +
                ", priority='" + priority + '\'' +
                ", hasAttachment='" + hasAttachment + '\'' +
                '}';
    }

    private String category;
    private String amount;
    private String priority;
    private String hasAttachment;

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getHasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachment(String hasAttachment) {
        this.hasAttachment = hasAttachment;
    }
}
