package kestone.com.kestone.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import kestone.com.kestone.MODEL.More.RESPONSE.MoreResponse;
import kestone.com.kestone.MODEL.Venue.RESPONSE.GetVenueListResponse;

/**
 * Created by karan on 7/6/2017.
 */

public class StorageUtilities {
    private final String STORAGE = "9999";
    private SharedPreferences preferences;
    private Context context;



    public StorageUtilities(Context context){
        this.context = context;
    }

    public void storeEmail(String email){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PrefEntities.EMAIL,email);
        editor.apply();
    }
    public String loadEmail(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PrefEntities.EMAIL,STORAGE);
    }

    public void storePhone(String phone){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PrefEntities.PHONE,phone);
        editor.apply();
    }
    public String loadPhone(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PrefEntities.PHONE,STORAGE);
    }

    public void storeProfilePic(String email){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PrefEntities.PROFILEPIC,email);
        editor.apply();
    }
    public String loadProfilePic(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PrefEntities.PROFILEPIC,STORAGE);
    }

    public void storeFirstName(String name){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PrefEntities.FIRSTNAME,name);
        editor.apply();
    }

    public String loadfirstName(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PrefEntities.FIRSTNAME,STORAGE);
    }

//    public void storeLastName(String name){
//        preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("lname",name);
//        editor.apply();
//    }
//
//    public String loadLastName(){
//        preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        return preferences.getString("lname",STORAGE);
//    }
    public void storeID(String ID){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PrefEntities.USERID,ID);
        editor.apply();
    }

    public String loadID(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PrefEntities.USERID,STORAGE);
    }

    public void storeCityID(String ID){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PrefEntities.CITYID,ID);
        editor.apply();
    }

    public String loadCityID(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PrefEntities.CITYID,STORAGE);
    }

    public void storeCity(String name){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PrefEntities.CITYNAME,name);
        editor.apply();
    }

    public String loadCity(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PrefEntities.CITYNAME,STORAGE);
    }
    public void isLogIn(int login){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("login",login);
        editor.apply();
    }
    public int isLogged(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt("login",0);
    }

    public void storeEventName(String event){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PrefEntities.EVENTNAME,event);
        editor.apply();
    }

    public String loadEventName(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PrefEntities.EVENTNAME,STORAGE);
    }

    public void storeEventID(String event){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PrefEntities.EVENTID,event);
        editor.apply();
    }

    public String loadEventID(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PrefEntities.EVENTID,STORAGE);
    }

    public void storeEventDate(String date){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PrefEntities.EVENTDATE,date);
        editor.apply();
    }

    public String loadEventDate(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PrefEntities.EVENTDATE,STORAGE);
    }

    public void storeVenueID(String event){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PrefEntities.VENUEID,event);
        editor.apply();
    }

    public String loadVenueID(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PrefEntities.VENUEID,STORAGE);
    }

    public void storeHallID(String event){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PrefEntities.HALLID,event);
        editor.apply();
    }

    public String loadHallID(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PrefEntities.HALLID,STORAGE);
    }

    public void ClearEventID(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("eventID");
        editor.apply();
    }

    public void ClearVenueID(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("venueID");
        editor.apply();
    }

    public void ClearHallID(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("hallID");
        editor.apply();
    }


    public void clearStorage(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

    }

    public void ClearKey(String key){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public void StoreKey(String key, String value){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String LoadKey(String key){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key,STORAGE);
    }















    //Payloads
    public void storePayload1(List<kestone.com.kestone.MODEL.Filters.RESPONSE.Payload> payload3) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(payload3);
        editor.putString("payload1", json);
        editor.apply();
    }

    public List<kestone.com.kestone.MODEL.Filters.RESPONSE.Payload> loadPayload1() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString("payload1", null);
        Type type = new TypeToken<List<kestone.com.kestone.MODEL.Filters.RESPONSE.Payload>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
    public void storePayload1Original(List<kestone.com.kestone.MODEL.Filters.RESPONSE.Payload> payload3) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(payload3);
        editor.putString("payload1o", json);
        editor.apply();
    }

    public List<kestone.com.kestone.MODEL.Filters.RESPONSE.Payload> loadPayload1Original() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString("payload1o", null);
        Type type = new TypeToken<List<kestone.com.kestone.MODEL.Filters.RESPONSE.Payload>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void storePayload2(List<kestone.com.kestone.MODEL.Filters.RESPONSE.Payload> payload3) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(payload3);
        editor.putString("payload2", json);
        editor.apply();
    }

    public List<kestone.com.kestone.MODEL.Filters.RESPONSE.Payload> loadPayload2() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString("payload2", null);
        Type type = new TypeToken<List<kestone.com.kestone.MODEL.Filters.RESPONSE.Payload>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
    public void storePayload3(List<kestone.com.kestone.MODEL.Filters.RESPONSE.Payload> payload3) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(payload3);
        editor.putString("payload3", json);
        editor.apply();
    }

    public List<kestone.com.kestone.MODEL.Filters.RESPONSE.Payload> loadPayload3() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString("payload3", null);
        Type type = new TypeToken<List<kestone.com.kestone.MODEL.Filters.RESPONSE.Payload>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void storeDeviceToken(String deviceToken){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("devicetoken",deviceToken);
        editor.apply();
    }
    public String loadDeviceToken(){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("devicetoken",STORAGE);
    }


    //More

    public void storeMoreData(MoreResponse moreData) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(moreData);
        editor.putString("moreData", json);
        editor.apply();
    }

    public MoreResponse loadMoreData() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString("moreData", null);
        Type type = new TypeToken<MoreResponse>() {
        }.getType();
        return gson.fromJson(json, type);
    }


    public void storeMoreDataDefault(MoreResponse moreData) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(moreData);
        editor.putString("moreDataD", json);
        editor.apply();
    }

    public MoreResponse loadMoreDataDefault() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString("moreDataD", null);
        Type type = new TypeToken<MoreResponse>() {
        }.getType();
        return gson.fromJson(json, type);
    }


    //Venue

    public void storeVenueDefault(GetVenueListResponse Venues) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Venues);
        editor.putString("venuesData", json);
        editor.apply();
    }

    public GetVenueListResponse loadVenueDefault() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString("venuesData", null);
        Type type = new TypeToken<GetVenueListResponse>() {
        }.getType();
        return gson.fromJson(json, type);
    }






}
