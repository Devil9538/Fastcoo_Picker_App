package com.fastcoo.fastcoopickerapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login_Result {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;

    /**
     * No args constructor for use in serialization
     *
     */
    public Login_Result() {
    }

    /**
     *
     * @param profilePic
     * @param mobileNo
     * @param userId
     * @param username
     */
    public Login_Result(String userId, String username, String mobileNo, String profilePic) {
        super();
        this.userId = userId;
        this.username = username;
        this.mobileNo = mobileNo;
        this.profilePic = profilePic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

}
