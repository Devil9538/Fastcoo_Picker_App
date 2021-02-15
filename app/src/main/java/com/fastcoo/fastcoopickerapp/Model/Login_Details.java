package com.fastcoo.fastcoopickerapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login_Details {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("result")
    @Expose
    private Login_Result result;

    /**
     * No args constructor for use in serialization
     *
     */
    public Login_Details() {
    }

    /**
     *
     * @param result
     * @param status
     */
    public Login_Details(Integer status, Login_Result result) {
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

    public Login_Result getResult() {
        return result;
    }

    public void setResult(Login_Result result) {
        this.result = result;
    }
}
