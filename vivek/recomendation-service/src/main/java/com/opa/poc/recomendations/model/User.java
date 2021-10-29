package com.opa.poc.recomendations.model;

public class User {
    private String name;
    private String email;
    private String canAutoApprove;

    public String getCanAutoApprove() {
        return canAutoApprove;
    }

    public void setCanAutoApprove(String canAutoApprove) {
        this.canAutoApprove = canAutoApprove;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

