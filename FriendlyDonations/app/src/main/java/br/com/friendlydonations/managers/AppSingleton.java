package br.com.friendlydonations.managers;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import br.com.friendlydonations.network.NetworkInterface;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * Created by brunogabriel on 9/13/16.
 */
public class AppSingleton {

    // Singleton instance reference
    private static AppSingleton ourInstance = new AppSingleton();

    public static final int READ_TIMEOUT = 2; // Minutes
    public static final int CONNECT_TIMEOUT = 2; // Minutes

    // Rest adapter e client
    private Retrofit mRetrofit;
    private NetworkInterface mNetworkInterface;
    private OkHttpClient mOKHttp;


    private AppSingleton() {
        initializeRest();
    }

    private void initializeRest() {
        mOKHttp = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        Log.d("WEB_REQUEST", "" + response);
                        return response;
                    }
                })
                .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES).build();


        mRetrofit = new Retrofit.Builder()
                // TODO Validate if something wrong occurs without ConverterFactory
                // .GsonConverterFactory
                .baseUrl(NetworkInterface.SERVER_URL)
                .client(mOKHttp)
                .build();

        mNetworkInterface = mRetrofit.create(NetworkInterface.class);
    }

    public static AppSingleton getInstance() {
        if (ourInstance == null) {
            ourInstance = new AppSingleton();
        }

        return ourInstance;
    }

    public NetworkInterface getNetworkInterface () {
        return mNetworkInterface;
    }

}
