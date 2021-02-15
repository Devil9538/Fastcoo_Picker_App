package com.fastcoo.fastcoopickerapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sku_Detail {

    @SerializedName("pickupId")
    @Expose
    private String pickupId;
    @SerializedName("new_id")
    @Expose
    private String newId;
    @SerializedName("tods_barcode")
    @Expose
    private String todsBarcode;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("ean_no")
    @Expose
    private String eanNo;
    @SerializedName("piece")
    @Expose
    private Integer piece;
    @SerializedName("shelve_no")
    @Expose
    private String shelveNo;
    @SerializedName("sku_path")
    @Expose
    private String skuPath;
    @SerializedName("slip_details")
    @Expose
    private List<SlipDetail> slipDetails = null;

    public String getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(String stockLocation) {
        this.stockLocation = stockLocation;
    }

    @SerializedName("stockLocation")
    @Expose
    private String stockLocation;

    private boolean isSelected;

    private  int update;

    private String indicator="";

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

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

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public String getTodsBarcode() {
        return todsBarcode;
    }

    public void setTodsBarcode(String todsBarcode) {
        this.todsBarcode = todsBarcode;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getEanNo() {
        return eanNo;
    }

    public void setEanNo(String eanNo) {
        this.eanNo = eanNo;
    }

    public Integer getPiece() {
        return piece;
    }

    public void setPiece(Integer piece) {
        this.piece = piece;
    }

    public String getShelveNo() {
        return shelveNo;
    }

    public void setShelveNo(String shelveNo) {
        this.shelveNo = shelveNo;
    }

    public String getSkuPath() {
        return skuPath;
    }

    public void setSkuPath(String skuPath) {
        this.skuPath = skuPath;
    }

    public List<SlipDetail> getSlipDetails() {
        return slipDetails;
    }

    public void setSlipDetails(List<SlipDetail> slipDetails) {
        this.slipDetails = slipDetails;
    }

}
