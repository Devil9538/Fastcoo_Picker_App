package com.fastcoo.fastcoopickerapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sku {

    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("piece")
    @Expose
    private String piece;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("shelve_no")
    @Expose
    private String shelveNo;

    private  int update;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getShelveNo() {
        return shelveNo;
    }

    public void setShelveNo(String shelveNo) {
        this.shelveNo = shelveNo;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

}
