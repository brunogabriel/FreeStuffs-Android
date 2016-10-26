package br.com.friendlydonations.network;

import org.json.JSONObject;

import br.com.friendlydonations.models.LoginModel;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by brunogabriel on 9/13/16.
 */
public interface NetworkInterface {
    String SERVER_URL = "http://192.168.50.4:3000/";

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
            @Field("language") String language
    );
}
