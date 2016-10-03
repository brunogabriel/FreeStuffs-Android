package br.com.friendlydonations.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.friendlydonations.managers.BaseActivity;

/**
 * Created by brunogabriel on 29/09/16.
 */

public class ViewUtility {

    public static void showSnackBar(View rootView, String message, int snackSize, String actionUndo, View.OnClickListener onClickListener) {
        Snackbar snackbar = null;
        if (actionUndo != null && onClickListener != null) {
            snackbar = Snackbar.make(rootView, message, snackSize).setAction(actionUndo, onClickListener);
        } else {
            snackbar = Snackbar.make(rootView, message, snackSize);
        }

        snackbar.show();
    }

    public static Uri storeImageOnDisk (Activity activity, Bitmap image) {
        File pictureFile = getOutputMediaFile(activity);
        if (pictureFile == null) {
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        return Uri.fromFile(pictureFile);
    }

    private static File getOutputMediaFile(Activity activity){
        // TODO: Verify size (MB)
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + activity.getApplicationContext().getPackageName()
                + "/Images");

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        // TODO: Refactor to use same as getOutputMediaFile
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "friendlydonations" + System.currentTimeMillis() + ".png", null);
        return Uri.parse(path);
    }
}
