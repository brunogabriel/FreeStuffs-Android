package br.com.friendlydonations;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.com.friendlydonations.shared.network.RetrofitHelper;

/**
 * Created by brunogabriel on 26/04/17.
 */

public class Helper {

    public static String readFileContent(@NonNull Object callable, @NonNull String filePath) throws IOException {
        InputStream inputStream = callable.getClass().getClassLoader().getResourceAsStream(filePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        StringBuilder stringBuilder = new StringBuilder();
        String currentLine;
        while ((currentLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(currentLine);
        }

        return stringBuilder.toString();
    }

    public static <T> T createService(Class<T> callable) {
        return RetrofitHelper.getInstance().getRetrofit().create(callable);
    }
}
