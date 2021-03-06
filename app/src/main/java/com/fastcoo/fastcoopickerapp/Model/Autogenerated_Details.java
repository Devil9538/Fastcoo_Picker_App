package com.fastcoo.fastcoopickerapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class Autogenerated_Details   {

    @SerializedName("pickupId")
    @Expose
    private String pickupId;
    @SerializedName("picked_status")
    @Expose
    private String pickedStatus;
    @SerializedName("entrydate")
    @Expose
    private String entrydate;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_pending")
    @Expose
    private String totalPending;

    public String getPickupId() {
        return pickupId;
    }

    public void setPickupId(String pickupId) {
        this.pickupId = pickupId;
    }

    public String getPickedStatus() {
        return pickedStatus;
    }

    public void setPickedStatus(String pickedStatus) {
        this.pickedStatus = pickedStatus;
    }

    public String getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate;
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

    public static Comparator<Autogenerated_Details> detail= new Comparator<Autogenerated_Details>() {
        @Override
        public int compare(Autogenerated_Details o1, Autogenerated_Details o2) {

            return o2.entrydate.compareTo(o1.entrydate);
        }
    };



}
