package com.supermarket.model;

public class Staff {
    private int id;
    private String role;
    private Member member;
    
    public Staff() {
    }
    
    public Staff(int id, String role) {
        this.id = id;
        this.role = role;
    }
    
    public Staff(int id, String role, Member member) {
        this.id = id;
        this.role = role;
        this.member = member;
        if (member != null) {
            this.member.setId(id);
        }
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
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public Member getMember() {
        return member;
    }
    
    public void setMember(Member member) {
        this.member = member;
        // Sync IDs
        if (member != null) {
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
    
    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", member=" + (member != null ? member.getFullname() : "null") +
                '}';
    }
}
