package br.com.friendlydonations.network;

import br.com.friendlydonations.models.login.LoginModel;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by brunogabriel on 07/02/17.
 */

public interface NetworkLogin {

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
}
