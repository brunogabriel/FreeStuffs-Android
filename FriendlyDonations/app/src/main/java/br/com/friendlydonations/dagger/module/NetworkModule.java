package br.com.friendlydonations.dagger.module;

import android.net.Network;
import android.os.Build;
import android.support.compat.BuildConfig;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by brunogabriel on 05/02/17.
 */

@Module
public class NetworkModule {

    public static final String BASE_URL = "https://rededoar.thiagosf.net/";
    public static final String TAG = NetworkModule.class.getSimpleName();

    // Constants
    public static final int READ_TIMEOUT = 2;
    public static final int CONNECT_TIMEOUT = 2;

    String mBaseUrl;

    public NetworkModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Response response = chain.proceed(chain.request());
                    if(BuildConfig.DEBUG) {
                        Log.d(TAG, "Interceptor response: " + response);
                    }
                    return response;
                })
                .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES).build();

        return mOkHttpClient;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient mOkHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
    }
}
