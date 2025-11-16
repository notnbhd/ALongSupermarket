package com.supermarket.model;

import java.sql.Date;

public class Customer {
    private int id;           
    private Member member;    

    public Customer() {
    }

    public Customer(int id) {
        this.id = id;
    }

    public Customer(int id, Member member) {
        this.id = id;
        this.member = member;
        if (member != null) {
            member.setId(id);
        }
    }

    public Customer(String username, String password, String fullname, 
                   String phone, String email, String address, Date dob) {
        this.member = new Member(username, password, fullname, phone, email, address, dob);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        if (member != null) {
            member.setId(id);
        }
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
        if (member != null && this.id > 0) {
            member.setId(this.id);
        } else if (member != null && member.getId() > 0) {
            this.id = member.getId();
        }
    }

    public String getUsername() {
        return member != null ? member.getUsername() : null;
    }

    public String getFullname() {
        return member != null ? member.getFullname() : null;
    }

    public String getEmail() {
        return member != null ? member.getEmail() : null;
    }

    public String getPhone() {
        return member != null ? member.getPhone() : null;
    }

    public String getAddress() {
        return member != null ? member.getAddress() : null;
    }

    public Date getDob() {
        return member != null ? member.getDob() : null;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", member=" + member +
                '}';
    }
}
