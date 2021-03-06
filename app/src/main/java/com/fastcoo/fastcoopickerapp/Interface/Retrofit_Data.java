package com.fastcoo.fastcoopickerapp.Interface;


import com.fastcoo.fastcoopickerapp.Model.Autogenerated_Main;
import com.fastcoo.fastcoopickerapp.Model.Login_Details;
import com.fastcoo.fastcoopickerapp.Model.ModelPojo;
import com.fastcoo.fastcoopickerapp.Model.Picklist_Main;
import com.fastcoo.fastcoopickerapp.Model.SendTotes;
import com.fastcoo.fastcoopickerapp.Model.SinglePickup_list;
import com.fastcoo.fastcoopickerapp.Model.Sku_Main;
import com.fastcoo.fastcoopickerapp.Model.Update;
import com.fastcoo.fastcoopickerapp.Model.UpdateAutoPickup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Retrofit_Data {

    @GET("Picker/auth_login?")
    Call<Login_Details> login(@Query("email") String email, @Query("password") String password);

    @GET("Picker/BatchPicklist?")
    Call<SinglePickup_list> pickup_list(@Query("picker_id") String picker_id);

    @GET("Picker/SinglePicklist?")
    Call<SinglePickup_list>single_pickup(@Query("picker_id") String picker_id, @Query("pickupId") String pickup_id);

    @GET("Picker/SinglePicklist_sku?")
    Call<SinglePickup_list> singlepickup_data(@Query("picker_id") String picker_id, @Query("slip_no") String Slip_no);

    @GET("Picker/UpdateSinglePickup?")
    Call<Update> update(@Query("picker_id") String picker_id, @Query("slip_no") String Slip_no, @Query("pickupId") String pickup_id);

    @GET("Picker/BatchPicklist_sku?")
    Call<SinglePickup_list> batch_pickup(@Query("picker_id") String picker_id, @Query("pickupId") String pickup_id);

    @GET("Picker/UpdateBatchPickup?")
    Call<Update> update_batch(@Query("picker_id") String picker_id, @Query("pickupId") String pickup_id);

    @GET("Picker/CompletedPicklist?")
    Call<ModelPojo> complete(@Query("picker_id") String picker_id);

    @GET("Picker/UserAllPicklist?")
    Call<Autogenerated_Main> getAutoPickup_List(@Query("picker_id") String picker_id);

    @GET("Picker/GetShipemntCombolist?")
    Call<Picklist_Main> getPickList(@Query("picker_id") String picker_id, @Query("pickupId") String pickup_id, @Query("tod_no")String totes_value);

    @GET("Picker/GroupSlipNoSkuDetails?")
    Call<Sku_Main> getSkuList(@Query("picker_id")String picker_id, @Query("pickupId") String pickup_id
    ,@Query("tod_no") String totes ,@Query("slip_nos")String list);

    @GET("Picker/UpdateBatchPickup_new?")
    Call<UpdateAutoPickup> getUpdateStatus(@Query("picker_id") String picker_id,@Query("new_id") String new_id
    ,@Query("pickupId")String pickup_id);

    @GET("Picker/BatchUpdateTodsNo?")
    Call<SendTotes> sendTotesInfo(@Query("picker_id")String picker_id,@Query("pickupId")String pickup_id,
                                  @Query("slip_nos")String slip_list,@Query("tods_barcode")String totes_list,
                                  @Query("new_id")String new_id);
}
