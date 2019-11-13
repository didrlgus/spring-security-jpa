package com.sprint_security_jpa.domain.status;

public enum MemberStatus {
    LOGIN("로그인"), LOGOUT("로그아웃");

    private String status;

    MemberStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
