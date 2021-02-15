package com.fastcoo.fastcoopickerapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SinglePickup_Result {

    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("piece")
    @Expose
    private String piece;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("shelve_no")
    @Expose
    private String shelveNo;

    @SerializedName("slip_no")
    @Expose
    private String slipNo;
    @SerializedName("picked_status")
    @Expose
    private String pickedStatus;
    @SerializedName("sku_details")
    @Expose
    private List<Sku> skuDetails = null;
    @SerializedName("booking_d")
    @Expose
    private String bookingD;

    private  int update;

    @SerializedName("pickupId")
    @Expose
    private String pickupId;

    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_pending")
    @Expose
    private String totalPending;

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public String getPickupId() {
        return pickupId;
    }

    public void setPickupId(String pickupId) {
        this.pickupId = pickupId;
    }
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalPending() {
        return totalPending;
    }

    public void setTotalPending(String totalPending) {
        this.totalPending = totalPending;
    }


    public String getSlipNo() {
        return slipNo;
    }

    public void setSlipNo(String slipNo) {
        this.slipNo = slipNo;
    }

    public String getPickedStatus() {
        return pickedStatus;
    }

    public void setPickedStatus(String pickedStatus) {
        this.pickedStatus = pickedStatus;
    }

    public List<Sku> getSkuDetails() {
        return skuDetails;
    }

    public void getSkuDetails(List<Sku> skuDetails) {
        this.skuDetails = skuDetails;
    }

    public String getBookingD() {
        return bookingD;
    }

    public void setBookingD(String bookingD) {
        this.bookingD = bookingD;
    }

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

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getShelveNo() {
        return shelveNo;
    }

    public void setShelveNo(String shelveNo) {
        this.shelveNo = shelveNo;
    }


}
