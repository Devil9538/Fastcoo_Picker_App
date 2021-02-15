package com.fastcoo.fastcoopickerapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {



        @SerializedName("slip_no")
        @Expose
        private String slipNo;
        @SerializedName("sku")
        @Expose
        private List<Sku> sku = null;
        @SerializedName("picked_status")
        @Expose
        private String pickedStatus;
        @SerializedName("booking_id")
        @Expose
        private String bookingId;


        public Result() {
        }


        public Result(String slipNo, List<Sku> sku, String pickedStatus, String bookingId) {
            super();
            this.slipNo = slipNo;
            this.sku = sku;
            this.pickedStatus = pickedStatus;
            this.bookingId = bookingId;
        }

        public String getSlipNo() {
            return slipNo;
        }

        public void setSlipNo(String slipNo) {
            this.slipNo = slipNo;
        }

        public List<Sku> getSku() {
            return sku;
        }

        public void setSku(List<Sku> sku) {
            this.sku = sku;

        }

        public String getPickedStatus() {
            return pickedStatus;
        }

        public void setPickedStatus(String pickedStatus) {
            this.pickedStatus = pickedStatus;
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

    }

