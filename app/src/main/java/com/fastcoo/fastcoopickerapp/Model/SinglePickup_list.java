package com.fastcoo.fastcoopickerapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SinglePickup_list {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("result")
    @Expose
    private List<SinglePickup_Result> result = null;

    public SinglePickup_list() {
    }


    public SinglePickup_list(Integer status, List<SinglePickup_Result> result) {
        super();
        this.status = status;
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SinglePickup_Result> getResult() {
        return result;
    }

    public void setResult(List<SinglePickup_Result> result) {
        this.result = result;
    }
}

