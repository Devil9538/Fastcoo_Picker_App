package com.fastcoo.fastcoopickerapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Picklist_Main {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("result")
    @Expose
    private List<List<Picklist_Detail>> result = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<List<Picklist_Detail>> getResult() {
        return result;
    }

    public void setResult(List<List<Picklist_Detail>> result) {
        this.result = result;
    }
}
