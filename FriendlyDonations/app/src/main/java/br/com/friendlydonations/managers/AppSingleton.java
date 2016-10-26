package br.com.friendlydonations.managers;

import android.util.Log;

import java.util.concurrent.TimeUnit;
import br.com.friendlydonations.network.NetworkInterface;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by brunogabriel on 9/13/16.
 */
public class AppSingleton {

    private static AppSingleton ourInstance = new AppSingleton();

    public static final int READ_TIMEOUT = 1; // Minutes
    public static final int CONNECT_TIMEOUT = 1; // Minutes

    // Rest adapter e client
    private Retrofit mRetrofit;
    private NetworkInterface mNetworkInterface;
    private OkHttpClient mOKHttp;

    private AppSingleton() {
        initializeRest();
    }

    private void initializeRest() {
        mOKHttp = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Response response = chain.proceed(chain.request());
                    Log.d("WEB_REQUEST", "" + response);
                    return response;
                })
                .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES).build();


        mRetrofit = new Retrofit.Builder()
                .baseUrl(NetworkInterface.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
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
