package com.example.weddingelements_android.interfaces;

import com.example.weddingelements_android.model.Advertisement;
import com.example.weddingelements_android.model.BusinessOwner;
import com.example.weddingelements_android.model.BusinessOwnerMobile;
import com.example.weddingelements_android.model.BusinessUpdateRequest;
import com.example.weddingelements_android.model.Customer;
import com.example.weddingelements_android.model.CustomerUpdateRequest;
import com.example.weddingelements_android.model.Inquiry;
import com.example.weddingelements_android.model.LoggedInUser;
import com.example.weddingelements_android.model.LoginRequest;
import com.example.weddingelements_android.model.Review;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestApi {
    static String BASE_URL = "http://192.168.1.4:8080/mobile/";

     /*
    Review APIS
     */

    @DELETE("logout/{email}")
    Call<Void> logoutUser(@Path("email") String email);

    @POST("login")
    Call<LoggedInUser> loginUser(@Body LoginRequest request);

    @GET("reviews/{email}")
    Call<List<Review>> getReviewsByCustomer(@Path("email") String email);

    @DELETE("review/{reviewId}")
    Call<List<Review>> deleteReview(@Path("reviewId") String reviewId);

    /*
       Advertisement APIS
        */

    @GET("advertisements")
    Call<List<Advertisement>> getAdvertisements();

    @POST("advertisement")
    Call<Advertisement> addAdvertisement(@Body Advertisement advertisement);

    @PUT("advertisement")
    Call<List<Advertisement>> updateAdvertisement(@Body Advertisement advertisement);

    @DELETE("advertisement/{advertisementId}")
    Call<Void> deleteAdvertisement(@Path("advertisementId") String advertisementId);

    @GET("advertisement/reviews/{advertisementId}")
    Call<List<Review>> getReviewsByAdvertisement(@Path("advertisementId") String advertisementId);

     /*
    Customer APIS
     */

    @GET("customer/{email}")
    Call<Customer> getCustomerDetails(@Path("email") String email);

    @GET("customers")
    Call<List<Customer>> listCustomers();

    @POST("customer")
    Call<String> registerCustomer(@Body Customer customer);

    @PUT("customer")
    Call<Void> updateCustomer(@Body CustomerUpdateRequest customer);

    @DELETE("customer/{email}")
    Call<Void> deleteCustomer(@Path("email") String email);

    /*
    Business Owner APIS
     */

    @GET("business-owner/{email}")
    Call<BusinessOwnerMobile> getBusinessOwner(@Path("email") String email);

    @POST("business-owner")
    Call<BusinessOwner> registerBusiness(@Body BusinessOwner businessOwner);

    @PUT("business-owner")
    Call<Void> updateBusinessDetails(@Body BusinessUpdateRequest request);

    @DELETE("business-owner/{email}")
    Call<Void> deleteBusiness(@Path("email") String email);

    @GET("business-owners")
    Call<List<BusinessOwner>> listBusinesses();
    /*
    Inquiry APIS
     */
    @POST("inquiry")
    Call<Inquiry> sendInquiry(@Body Inquiry inquiry);








}
