package br.com.friendlydonations.shared.network;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import br.com.friendlydonations.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by brunogabriel on 26/04/17.
 */

public class RetrofitHelper {

    private static final int READ_TIMEOUT = 60;
    private static final int CONNECTION_TIMEOUT = 60;

    // Elements
    private static Retrofit retrofit;
    private static Context context;
    private static OkHttpClient okHttpClient;

    private static final RetrofitHelper ourInstance = new RetrofitHelper();

    public static RetrofitHelper getInstance() {
        return ourInstance;
    }

    public static RetrofitHelper injectContext(@NonNull Context context) {
        ourInstance.context = context;
        return ourInstance;
    }

    public Retrofit getRetrofit() {
        // Setup Okhttp
        if (okHttpClient == null) {
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

            if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && context != null) {
                okHttpBuilder.addInterceptor(new ChuckInterceptor(context));
            }

            okHttpClient = okHttpBuilder
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }

        // Setup retrofit
        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BuildConfig.base_url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

            retrofit = builder.build();
        }

        return retrofit;
    }

}
