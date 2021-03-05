package com.example.agendasqlite.Model;

import java.io.Serializable;

public class Contactos implements Serializable {

    private int idContacto;
    private String name;
    private String surname;
    private String mail;
    private String address;
    private String phone;

    public Contactos() {

    }

    public Contactos(int idContacto, String name, String surname, String mail, String address, String phone) {
        this.idContacto = idContacto;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.address = address;
        this.phone = phone;
    }

    public int getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(int idContacto) {
        this.idContacto = idContacto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
