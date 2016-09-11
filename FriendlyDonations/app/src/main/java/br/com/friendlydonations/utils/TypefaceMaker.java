package br.com.friendlydonations.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brunogabriel on 9/6/16.
 */
public class TypefaceMaker {

    private static TypefaceMaker instance;
    private static final Map<String, Typeface> typefaces = new HashMap<>();

    public TypefaceMaker() {

    }

    public static TypefaceMaker getInstance() {
        if (instance == null) {
            instance = new TypefaceMaker();
        }

        return instance;
    }

    public enum FontFamily {
        // Montserrat
        MontserratRegular("Montserrat/Montserrat-Regular.ttf"),
        MontserratBold("Montserrat/Montserrat-Bold.ttf"),
        // Roboto
        RobotoRegular("Roboto/Roboto-Regular.ttf"),
        RobotoLight("Roboto/Roboto-Light.ttf"),
        RobotoMedium("Roboto/Roboto-Medium.ttf");

        private String description;

        FontFamily(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }

    }

    public static Typeface createTypeFace(Context mContext, FontFamily mTypeName) {
        try {
            Map<String, Typeface> currentFaces = getInstance().typefaces;
            Typeface typeFace = currentFaces.get(mTypeName.getDescription());

            if (mContext != null && typeFace == null) {
                typeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/"
                        + mTypeName.getDescription());

                getInstance().typefaces.put(mTypeName.getDescription(), typeFace);
            }

            return typeFace;
        } catch (Exception ex) {
            return null;
        }
    }

}
