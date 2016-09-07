package br.com.friendlydonations.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by brunogabriel on 9/6/16.
 */
public class TypefaceMaker {

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
        Typeface typeFace = null;
        if (mContext != null) {
            typeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/"
                    + mTypeName.getDescription());
        }

        return typeFace;
    }

}
