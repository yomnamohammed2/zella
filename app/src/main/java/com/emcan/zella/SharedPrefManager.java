package com.emcan.zella;


import android.content.Context;
import android.content.SharedPreferences;

import com.emcan.zella.Beans.Branch_Model;
import com.emcan.zella.Beans.User;


public class SharedPrefManager {
    //the constants
    private static final String SHARED_PREF_NAME = "pref1";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PHONE_TWO = "phone_two";
    private static final String NOTE = "note";
    private static final String KEY_ID = "id";
    private static final String KEY_REGION= "region";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHOTO = "photo";
    private static final String CART= "CART";
    private static final String LANG= "LANG";
    private static final String SHOW_ADV= "adv";

    private static final String DRIVER= "DRIVER";
    private static final String ID= "ID";
    private static final String NAME= "NAME";

    private static final String Update= "update";


    private static final String DATA= "DATA";
    private static final String CONTACT= "CONTACT";
    private static final String TOKEN= "token";
    private static final String KEY_EMAIL_remember = "email_remember";
    private static final String KEY_PASSWORD_REMEMBER= "password_remember";
    private static final String MOBILE_VERSION= "MOBILE_VERSION";


    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void setMobileVersion(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MOBILE_VERSION, token);
        editor.apply();

    }

    public String getMobileVersion(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return   sharedPreferences.getString(MOBILE_VERSION, null);
    }
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USERNAME, user.getClient_name());
        editor.putString(KEY_ADDRESS, user.getClient_address());
        editor.putString(KEY_ID, user.getClient_id());
        editor.putString(KEY_REGION, user.getClient_region());
        editor.putString(KEY_PHONE, user.getClient_phone());
        editor.putString(KEY_PASSWORD, user.getClient_password());
        editor.putString(KEY_PHOTO, user.getClient_image());
        editor.putString(KEY_PHONE_TWO, user.getClient_phone_two());
        editor.putString(NOTE, user.getClient_note());
        editor.apply();
    }

    public boolean isDriver() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(DRIVER, false) ;
    }
    public void setDriver(boolean driver) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(DRIVER,driver);
        editor.apply();
    }
    public String getId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ID, "") ;
    }
    public void setId(String id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID,id);
        editor.apply();
    }
    public String getName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NAME, "") ;
    }
    public void setName(String name) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME,name);
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID, null) != null;
    }

    public String getLang(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LANG,"en");
    }
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_PASSWORD, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_PHONE_TWO, null),
                sharedPreferences.getString(KEY_REGION, null),
                sharedPreferences.getString(KEY_ADDRESS, null),
                sharedPreferences.getString(KEY_PHOTO, null),
                sharedPreferences.getString(NOTE, null)
        );
    }

    public void remember(String email,String password){
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(KEY_EMAIL_remember, email);
    editor.putString(KEY_PASSWORD_REMEMBER, password);
    editor.apply();

}

    public void set_token(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, token);
        editor.apply();

    }

    public String get_token(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return   sharedPreferences.getString(TOKEN, null);
    }

    public void set_data(String data){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DATA, data);
        editor.apply();

    }



    public void setLatti(String data){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("latti", data);
        editor.apply();

    }

    public String getLatti(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return   sharedPreferences.getString("latti", null);
    }

    public void setLongi(String data){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("longi", data);
        editor.apply();

    }

    public String getLongi(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return   sharedPreferences.getString("longi", null);
    }

    public String get_data(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return   sharedPreferences.getString(DATA, null);
    }
    public void Adv_Cancel(String ok) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHOW_ADV,ok );
        editor.apply();
    }

    public String Adv_Check() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return   sharedPreferences.getString(SHOW_ADV, null);

    }


    public void update_cancel(String ok) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHOW_ADV,ok );
        editor.apply();
    }

    public String update_check() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return   sharedPreferences.getString(SHOW_ADV, null);

    }


    public void set_CONTACT(String contact){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CONTACT, contact);
        editor.apply();

    }

    public String get_contact(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return   sharedPreferences.getString(CONTACT, null);
    }
    public void   reset_Cart(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int cart=  sharedPreferences.getInt(CART, -1);
        cart=0;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CART, cart);
        editor.apply();

    }

    public int  get_Cart(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return   sharedPreferences.getInt(CART, -1);
    }

    public void add_Cart() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        int cart= sharedPreferences.getInt(CART, -1);
        if(cart<1){
            cart=1;
        }else {
            cart++;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CART, cart);
        editor.apply();

    }

    public void delete_Cart() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        int cart= sharedPreferences.getInt(CART, -1);
        if(cart>1){
            cart--;
        }else {
            cart=0;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CART, cart);
        editor.apply();

    }
    public String get_Remember_email(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
      return   sharedPreferences.getString(KEY_EMAIL_remember, null);
    }

    public String get_Remember_pass(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return   sharedPreferences.getString(KEY_PASSWORD_REMEMBER, null);
    }

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String email= sharedPreferences.getString(KEY_EMAIL_remember, null);
        String password=sharedPreferences.getString(KEY_PASSWORD_REMEMBER, null);
        editor.clear();
        editor.apply();
        editor.putString(KEY_EMAIL_remember, email);
        editor.putString(KEY_PASSWORD_REMEMBER, password);
        editor.apply();
       }
}
