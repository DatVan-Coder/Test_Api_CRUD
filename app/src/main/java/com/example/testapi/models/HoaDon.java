package com.example.testapi.models;
import com.google.gson.annotations.SerializedName;
public class HoaDon {

    @SerializedName("_id")
    private String id;
    private String maHD;
    private String tenHD;
    private String tenSP;
    private String createAt, updateAt;
    private String gia;
    private String SLuong;

    public HoaDon() {
    }

    public HoaDon(String id, String maHD, String tenHD, String tenSP, String createAt, String updateAt, String gia, String SLuong) {
        this.id = id;
        this.maHD = maHD;
        this.tenHD = tenHD;
        this.tenSP = tenSP;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.gia = gia;
        this.SLuong = SLuong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getTenHD() {
        return tenHD;
    }

    public void setTenHD(String tenHD) {
        this.tenHD = tenHD;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getSLuong() {
        return SLuong;
    }

    public void setSLuong(String SLuong) {
        this.SLuong = SLuong;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", maHD='" + maHD + '\'' +
                ", tenHD='" + tenHD + '\'' +
                ", tenSP='" + tenSP + '\'' +
                ", gia=" + gia +
                ", createAt='" + createAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                ", SLuong='" + SLuong + '\'' +
                '}';
    }
}
