package com.nighteye.myapplication;

public class UserHelper {
    private String email, name, username, password;
    private String AuthEmail, AuthName, AuthUsername, AuthPassword, AuthContact, AuthPosition, AuthDocumentType, AuthCity, AuthState;
    private String contact, Address1, Address2, City, State, PostCode;

    public UserHelper(String email, String name, String username, String password) {
        this.email = email;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserHelper(String contact, String address1, String address2, String city, String state, String postCode) {
        this.contact = contact;
        Address1 = address1;
        Address2 = address2;
        City = city;
        State = state;
        PostCode = postCode;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
    }

    public UserHelper(String authEmail, String authName, String authUsername, String authPassword, String authContact, String authPosition, String authDocumentType, String authCity, String authState) {
        AuthEmail = authEmail;
        AuthName = authName;
        AuthUsername = authUsername;
        AuthPassword = authPassword;
        AuthContact = authContact;
        AuthPosition = authPosition;
        AuthDocumentType = authDocumentType;
        AuthCity = authCity;
        AuthState = authState;
    }

    public String getAuthEmail() {
        return AuthEmail;
    }

    public void setAuthEmail(String authEmail) {
        AuthEmail = authEmail;
    }

    public String getAuthName() {
        return AuthName;
    }

    public void setAuthName(String authName) {
        AuthName = authName;
    }

    public String getAuthUsername() {
        return AuthUsername;
    }

    public void setAuthUsername(String authUsername) {
        AuthUsername = authUsername;
    }

    public String getAuthPassword() {
        return AuthPassword;
    }

    public void setAuthPassword(String authPassword) {
        AuthPassword = authPassword;
    }

    public String getAuthContact() {
        return AuthContact;
    }

    public void setAuthContact(String authContact) {
        AuthContact = authContact;
    }

    public String getAuthPosition() {
        return AuthPosition;
    }

    public void setAuthPosition(String authPosition) {
        AuthPosition = authPosition;
    }

    public String getAuthDocumentType() {
        return AuthDocumentType;
    }

    public void setAuthDocumentType(String authDocumentType) {
        AuthDocumentType = authDocumentType;
    }

    public String getAuthCity() {
        return AuthCity;
    }

    public void setAuthCity(String authCity) {
        AuthCity = authCity;
    }

    public String getAuthState() {
        return AuthState;
    }

    public void setAuthState(String authState) {
        AuthState = authState;
    }
}
