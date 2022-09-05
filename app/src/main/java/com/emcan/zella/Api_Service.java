package com.emcan.zella;



import com.emcan.zella.Beans.About_response;
import com.emcan.zella.Beans.Additions_Model;
import com.emcan.zella.Beans.Address;
import com.emcan.zella.Beans.Address_Response;
import com.emcan.zella.Beans.Avaliable_Model;
import com.emcan.zella.Beans.Branch_Model;
import com.emcan.zella.Beans.CartResponse;
import com.emcan.zella.Beans.Cart_Response2;
import com.emcan.zella.Beans.Check_Model;
import com.emcan.zella.Beans.Check_client;
import com.emcan.zella.Beans.Complain_response;
import com.emcan.zella.Beans.Con_Tact;
import com.emcan.zella.Beans.Contact_Response;
import com.emcan.zella.Beans.Current_order_model;
import com.emcan.zella.Beans.Delivery_Response;
import com.emcan.zella.Beans.Device_data_Response;
import com.emcan.zella.Beans.DiscountResponse;
import com.emcan.zella.Beans.Last_order_pojo;
import com.emcan.zella.Beans.LoginResponse;
import com.emcan.zella.Beans.Login_response;
import com.emcan.zella.Beans.LoyaltyPointsResponse;
import com.emcan.zella.Beans.Meal_Additions;
import com.emcan.zella.Beans.Menu_Response;
import com.emcan.zella.Beans.Order_respose;
import com.emcan.zella.Beans.Parent_Category_Model;
import com.emcan.zella.Beans.PaymentResponse;
import com.emcan.zella.Beans.Payment_result;
import com.emcan.zella.Beans.Phone_Num;
import com.emcan.zella.Beans.Prices_Model;
import com.emcan.zella.Beans.Region_Model;
import com.emcan.zella.Beans.Remove_Response;
import com.emcan.zella.Beans.Reviews_Model;
import com.emcan.zella.Beans.SettingsResponse;
import com.emcan.zella.Beans.SliderResponse;
import com.emcan.zella.Beans.Staff_Response;
import com.emcan.zella.Beans.Sub_Cat_Images_Model;
import com.emcan.zella.Beans.Sub_Category_Model;
import com.emcan.zella.Beans.TapPaymentResponse;
import com.emcan.zella.Beans.TapSuccessResponse;
import com.emcan.zella.Beans.TechnicalResponse;
import com.emcan.zella.Beans.Terms_Respose;
import com.emcan.zella.Beans.User;
import com.emcan.zella.Beans.Verification_Model;
import com.emcan.zella.Beans.Verify_Model;
import com.emcan.zella.Beans.fav_Response;
import com.emcan.zella.Beans.Check;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api_Service {


    @GET("phone-verification.php?")
    Call<Verify_Model> verifyPhone(
            @Query("phone") String phone ,
            @Query("lang") String lang
    );

    @GET("deliver-order.php?")
    Call<Check> deliverOrder(
            @Query("order_id") String order_id ,
            @Query("lang") String lang
    );

    @GET("get-parent-categories.php?")
    Call<Parent_Category_Model> getParentCategories(
            @Query("lang") String lang
    );

    @GET("delete-account.php?")
    Call<Check_Model> deleteAccount(
            @Query("client_id") String client_id,
            @Query("lang") String lang
    );


    @GET("logout.php?")
    Call<Check_Model> logout(
            @Query("client_id") String client_id,
            @Query("lang") String lang
    );


    @GET("payment-result.php?")
    Call<Payment_result> check_payment_result(
            @Query("client_id") String client_id,
            @Query("session_id") String session_id
    );

    @GET("get-terms-conditions.php?")
    Call<Terms_Respose> get_Terms(
            @Query("type") String type,
            @Query("lang") String lang
    );

    @GET("home-page.php?")
    Call<SliderResponse> get_Slider(
            @Query("lang") String lang,@Query("client_id") String client_id
    );

    @POST("sms/send.php")
    Call<Verification_Model> verify_phone(
            @Body Phone_Num body
    );

    @GET("get-sub-categories.php?")
    Call<Sub_Category_Model> getSubCategories(
            @Query("parent_category_id") String parent_category_id,
            @Query("client_id") String client_id,
            @Query("lang") String lang,
            @Query("parent_type") String parent_type,@Query("country_id") String country_id
    );


    @GET("search.php?")
    Call<Sub_Category_Model> search(
            @Query("search_word") String search_word,
            @Query("client_id") String client_id,
            @Query("lang") String lang
    );


    @GET("get-sub-sub.php?")
    Call<Sub_Category_Model> getSubSubCategories(
            @Query("parent_category_id") String parent_category_id,
            @Query("client_id") String client_id,
            @Query("lang") String lang,
            @Query("sub_sub_id") String sub_sub_id
    );

    @GET("get-delivered_way.php?")
    Call<Delivery_Response> get_Delivery(
            @Query("lang") String lang
    );

    @POST("add-message.php")
    Call<Complain_response> add_message(
            @Body Complain_response.Complain message
    );

    @POST("login.php")
    Call<Login_response> login2(
            @Body User user
    );

    @POST("driver/login.php")
    Call<Login_response> driverLogin(
            @Body User user
    );


    @POST("add-client.php?")
    Call<Login_response> signup(
            @Body User user
    );


    @POST("edit-client.php?")
    Call<Login_response> edit_account(
            @Body User user
    );

    @GET("get-sub-categories-images.php?")
    Call<Sub_Cat_Images_Model> get_Sub_Cat_Images(
            @Query("sub_category_id") String sub_category_id
    );

    @GET("get-sub-categories-size-prices.php?")
    Call<Prices_Model> getSub_Cat_Prices(
            @Query("sub_category_id") String sub_category_id, @Query("lang") String lang, @Query("country_id") String country_id

    );

    @GET("get-addition-prices.php?")
    Call<Additions_Model> get_Additions(
            @Query("lang") String lang, @Query("country_id") String country_id,
            @Query("sub_category_id") String parent_category_id


    );


    @POST("add-comment.php?")
    Call<Sub_Cat_Images_Model> addComment(
            @Body JsonObject jsonObject
            );

    @GET("get-sub-category-comments.php?")
    Call<Reviews_Model> get_Sub_Cat_Comments(
            @Query("sub_category_id") String sub_category_id,
            @Query("lang") String lang,
            @Query("client_id") String client_id

    );

    @GET("get-dish-of-day.php?")
    Call<Sub_Category_Model> get_dish_of_the_day(

    );

    @GET("edit-comment.php?")
    Call<Sub_Cat_Images_Model> edit_Comment(
            @Query("comment_id") String comment_id,
            @Query("comment") String comment,
            @Query("rate") String rate
    );

    @GET("delete-comment.php?")
    Call<Sub_Cat_Images_Model> delte_Comment(
            @Query("comment_id") String comment_id
    );

    @GET("add-fav.php?")
    Call<fav_Response> addToFavo(
            @Query("client_id") String client_id,
            @Query("sub_category_id") String sub_category_id,
            @Query("lang") String lang
    );


    @GET("delete-fav.php?")
    Call<fav_Response> deleteFromFavo(
            @Query("client_id") String client_id,
            @Query("sub_category_id") String sub_category_id
    );

    @GET("get-client-fav.php?")
    Call<fav_Response> getFavourites(
            @Query("client_id") String client_id,
            @Query("lang") String lang

    );

    @POST("check-number-exist.php")
    Call<Check_Model> check_exist(
            @Body User user
    );

    @GET("check-subcategory-exist.php?")
    Call<Avaliable_Model> check_order_exist2(
            @Query("cart_id") String cart_id,
            @Query("lang") String lang,
            @Query("branch_id") String branch_id,
            @Query("mobile_version") String mobile_version,
            @Query("mobile_type") String mobile_type
    );

    @GET("check-subcategory-exist.php?")
    Call<Avaliable_Model> check_order_exist22(
            @Query("cart_id") String cart_id,
            @Query("lang") String lang,
            @Query("client_address_id") String client_address_id,
            @Query("mobile_version") String mobile_version,
            @Query("mobile_type") String mobile_type);

    @POST("add-to-cart.php?")
    Call<Cart_Response2> addTOCart(
            @Body CartResponse.CartModel order

    );

    @GET("check-client.php?")
    Call<Check_client> check_client(
            @Query("client_id") String client_id

    );


    @GET("get-cart.php?")
    Call<Cart_Response2> getCart(
            @Query("client_id") String client_id,
            @Query("lang") String lang
            ,@Query("client_address_id") String client_address_id

    );

    @GET("delete-cart.php?")
    Call<Check> deleteCart(
            @Query("client_id") String client_id,
            @Query("cart_id") String cart_id

    );

    @POST("add-client-address.php")
    Call<Address_Response> add_address(
            @Body Address address
    );

    @POST("edit-client-address.php")
    Call<Address_Response> edit_Address(
            @Body Address address
    );

    @GET("get-client-addresses.php?")
    Call<Address_Response> getAddress(
            @Query("client_id") String client_id,
            @Query("lang") String lang,@Query("country_id") String country_id
    );


    @GET("delete-client-address.php?")
    Call<Address_Response> deleteAddress(
            @Query("client_id") String client_id,
            @Query("client_address_id") String client_address_id
    );


    @POST("confirm-order.php")
    Call<Order_respose> confirm_order2(
            @Body Order_respose.Order_model order
    );

    @GET("check-order.php?")
    Call<Sub_Cat_Images_Model> check_order(
            @Query("client_id") String client_id,
            @Query("client_address_id") String client_address_id,
            @Query("cart_id") String cart_id,
            @Query("lang") String lang
            ,@Query("country_id") String country_id

    );

    @GET("get-drinks-and-potatos.php?")
    Call<Meal_Additions> getMeal_Additions(
            @Query("lang") String lang,@Query("country_id") String country_id

    );


    @GET("check-order.php?")
    Call<Sub_Cat_Images_Model> check_minimum_order(
            @Query("client_id") String client_id,
            @Query("client_address_id") String client_address_id,
            @Query("order_id") String order_id,
            @Query("lang") String lang

    );

    @GET("check-subcategory-exist.php?")
    Call<Avaliable_Model> check_order_exist(
            @Query("order_id") String order_id,
            @Query("lang") String lang,
            @Query("mobile_version") String mobile_version,
            @Query("mobile_type") String mobile_type
    );

    @GET("get-about.php?")
    Call<About_response> get_About(
            @Query("lang") String lang
    );


    @GET("get-contact.php?")
    Call<Contact_Response> get_Contact(@Query("lang") String lang,@Query("country_id") String country_id);


    @GET("get-menu.php?")
    Call<Menu_Response> get_Menu(
    );

    @GET("get-staff.php")
    Call<Staff_Response> get_Staff(
    );

    @GET("my-last-orders.php?")
    Call<Last_order_pojo> get_last_orders(
            @Query("client_id") String client_id, @Query("lang") String lang

    );

    @GET("my-current-orders.php?")
    Call<Current_order_model> get_current_orders(
            @Query("client_id") String client_id, @Query("lang") String lang

    );

    @GET("driver/driver-orders.php?")
    Call<Current_order_model> getDriverOrders(
            @Query("driver_id") String client_id, @Query("lang") String lang, @Query("type") String type);


    @GET("cancell-order.php?")
    Call<Current_order_model> cancel_Orders(
            @Query("client_id") String client_id,
            @Query("order_id") String order_id

    );

    @GET("get-all-regions.php?")
    Call<Region_Model> get_all_regions(
            @Query("lang") String lang,@Query("country_id") String country_id
    );


    @GET("get-removes.php")
    Call<Remove_Response> get_removes(
            @Query("lang") String lang,
            @Query("sub_category_id") String parent_category_id

    );

    @GET("get-complaints.php?")
    Call<Complain_response> get_complains(
            @Query("client_id") String client_id
    );


    @POST("re-order.php")
    Call<Last_order_pojo> re_Order(
            @Body Order_respose.Order_model body
    );

    @GET("avaliable.php?")
    Call<Avaliable_Model> aval(

    );

    @GET("restore_pass.php")
    Call<Login_response> re_store(
            @Query("client_email") String client_email

    );

    @POST("add-client-contact.php?")
    Call<ResponseBody> send_contacts(
            @Body Con_Tact body
    );

    @GET("add-device-details.php?")
    Call<Device_data_Response> send_data(
            @Query("location_lat") String location_lat,
            @Query("location_long") String location_long,
            @Query("city_name") String city_name,
            @Query("country_name") String country_name,
            @Query("device_type") String device_type

    );


    @GET("get-notify.php?")
    Call<About_response> get_notifications(
            @Query("client_id") String client_id
    );

    @GET("get-messages_type.php?")
    Call<About_response> get_messages_type(
            @Query("lang") String lang
    );

    @GET("add-payment.php?")
    Call<Sub_Cat_Images_Model> add_payment(
            @Query("client_id") String client_id,
            @Query("order_id") String order_id,
            @Query("value") String value,
            @Query("result") String result,
            @Query("payment_id") String payment_id
    );


    @GET("get-messages.php?")
    Call<About_response> get_messages_by_type(
            @Query("client_id") String client_id,
            @Query("message_type_id") String message_type_id
    );

    @GET("get-branches.php?")
    Call<Branch_Model> get_Branches(@Query("lang") String lang);

    @GET("support.php")
    Call<TechnicalResponse> get_Technical(
            @Query("lang") String lang

    );

    @POST("update-token.php")
    Call<LoginResponse> send_Token(@Body User user);

    @GET("check-number-exist.php")
    Call<Check> checkExist(@Query("phone") String client_phone,
                           @Query("lang") String lang);

    @POST("update_pass.php")
    Call<Check> restore(@Body User user);

    @GET("empty-cart.php")
    Call<Check> emptyCart(@Query("client_id") String client_id);

    @GET("edit-cart.php?")
    Call<Check> editCart(@Query("cart_id") String cart_id,@Query("lang") String lang,@Query("quantity") String quantity);

    @GET("get-payment-methods.php")
    Call<PaymentResponse> getPaymentMethod(@Query("lang") String lang,@Query("country_id") String country_id);

    @GET("get-setting.php")
    Call<SettingsResponse> getSettings();

    @GET("paymentTap/make_transaction.php")
    Call<TapPaymentResponse> generateUrl(
            @Query("currency") String currency, @Query("lang") String lang, @Query("client_id") String client_id,
            @Query("amount") String amount,@Query("order_id") String order_id);

    @GET("paymentTap/get-transaction-data.php")
    Call<TapSuccessResponse> checkResult(
            @Query("lang") String lang, @Query("client_id") String client_id,
            @Query("tap_id") String tap_id);


    @GET("get-client-points.php")
    Call<LoyaltyPointsResponse> getPoints(
             @Query("client_id") String client_id, @Query("lang") String lang
            ,@Query("cart_id") String cart_id,@Query("discount_code") String discount_code,@Query("client_address_id") String client_address_id);

    @GET("driver/accept-order.php")
    Call<Check_Model> updateFollow(
            @Query("driver_id") String driver_id, @Query("lang") String lang,@Query("order_id") String order_id);

    @GET("driver/follow-order.php")
    Call<Check_Model> updateFollowDriver(
            @Query("driver_id") String driver_id, @Query("lang") String lang,@Query("order_id") String order_id
            ,@Query("status") String status);

    @POST("driver/update-token.php")
    Call<LoginResponse> send_TokenDriver(@Body User user);

    @GET("check-discount-code.php")
    Call<DiscountResponse> checkCoupon(@Query("discount_code") String discount_code, @Query("client_id") String client_id,
                                       @Query("use_points") String use_points, @Query("lang") String lang,
                                       @Query("client_address_id") String client_address_id);
}