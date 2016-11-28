package br.com.friendlydonations.network;

import br.com.friendlydonations.models.category.CategoryAnswerModel;
import br.com.friendlydonations.models.donation.DonationAnswerModel;
import br.com.friendlydonations.models.login.LoginModel;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by brunogabriel on 9/13/16.
 */
public interface NetworkInterface {

    public static String SERVER_URL = "https://rededoar.thiagosf.net/";

    @FormUrlEncoded
    @POST("users/login")
    Observable<LoginModel> doLogin(
            @Field("name") String name,
            @Field("userId") String userId,
            @Field("email") String email,
            @Field("birthday") String birthday,
            @Field("gender") String gender,
            @Field("token") String token,
            @Field("pushId") String pushId,
            @Field("platform") String platform,
            @Field("language") String language,
            @Field("terms_of_use") boolean isTermsOfUse
    );

    @FormUrlEncoded
    @POST("product_categories")
    Observable<CategoryAnswerModel> loadCategories(@Field("token") String token);

    @FormUrlEncoded
    @POST("products")
    Observable<DonationAnswerModel> loadDonations(@Field("token") String token);
}
