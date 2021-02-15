package com.fastcoo.fastcoopickerapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SlipDetail {

    @SerializedName("slip_no")
    @Expose
    private String slipNo;
    @SerializedName("tods_barcode")
    @Expose
    private String todsBarcode;
    @SerializedName("pieces")
    @Expose
    private String pieces;
    @SerializedName("scaned")
    @Expose
    private Integer scaned;
    @SerializedName("scan_status")
    @Expose
    private Boolean scanStatus;

    public String getSlipNo() {
        return slipNo;
    }

    public void setSlipNo(String slipNo) {
        this.slipNo = slipNo;
    }

    public String getTodsBarcode() {
        return todsBarcode;
    }

    public void setTodsBarcode(String todsBarcode) {
        this.todsBarcode = todsBarcode;
    }

    public String getPieces() {
        return pieces;
    }

    public void setPieces(String pieces) {
        this.pieces = pieces;
    }

    public Integer getScaned() {
        return scaned;
    }

    public void setScaned(Integer scaned) {
        this.scaned = scaned;
    }

    public Boolean getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(Boolean scanStatus) {
        this.scanStatus = scanStatus;
    }

}
