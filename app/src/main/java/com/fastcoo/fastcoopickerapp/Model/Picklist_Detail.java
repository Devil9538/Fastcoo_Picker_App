package com.fastcoo.fastcoopickerapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Picklist_Detail {

    @SerializedName("pickupId")
    @Expose
    private String pickupId;
    @SerializedName("new_id")
    @Expose
    private String newId;
    @SerializedName("tod_no")
    @Expose
    private String todNo;
    @SerializedName("slip_no")
    @Expose
    private String slipNo;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("sku_details")
    @Expose
    private List<Picklist_SkuDetail> skuDetails = null;

    public String getPickupId() {
        return pickupId;
    }

    public void setPickupId(String pickupId) {
        this.pickupId = pickupId;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public String getTodNo() {
        return todNo;
    }

    public void setTodNo(String todNo) {
        this.todNo = todNo;
    }

    public String getSlipNo() {
        return slipNo;
    }

    public void setSlipNo(String slipNo) {
        this.slipNo = slipNo;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public List<Picklist_SkuDetail> getSkuDetails() {
        return skuDetails;
    }

    public void setSkuDetails(List<Picklist_SkuDetail> skuDetails) {
        this.skuDetails = skuDetails;
    }
}
