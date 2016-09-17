package br.com.friendlydonations.network;

import org.json.JSONObject;

import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by brunogabriel on 9/13/16.
 */
public interface NetworkInterface {

    public static final String SERVER_URL = "http://rede-doar-api.dev.azk.io";

    @FormUrlEncoded
    @POST("/users/login")
    Observable<Object> doLogin(@Body JSONObject user);

}
