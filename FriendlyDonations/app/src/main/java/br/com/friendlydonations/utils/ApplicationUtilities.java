package br.com.friendlydonations.utils;

import android.app.Activity;
import android.os.Environment;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by brunogabriel on 29/09/16.
 */
public class ApplicationUtilities {

    //private static final String TAG = "APP_UTIL";

    /** public static void showSnackBar(View rootView, String message, int snackSize, String actionUndo, View.OnClickListener onClickListener) {
        Snackbar snackbar;
        if (actionUndo != null && onClickListener != null) {
            snackbar = Snackbar.make(rootView, message, snackSize).setAction(actionUndo, onClickListener);
        } else {
            snackbar = Snackbar.make(rootView, message, snackSize);
        }
        snackbar.show();
    } **/

    /** public static Uri storeImageOnDiskAndGetUri (Activity activity, File mPictureFile, Bitmap image) {
        if (mPictureFile == null) {
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(mPictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
        }

        return Uri.fromFile(mPictureFile);
    } **/

    public static File getMediaStorageFile(Activity activity) {

        File mMediaStorageFile = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/" + activity.getApplicationContext().getPackageName() + "/Images");

        if (!mMediaStorageFile.exists()){
            if (!mMediaStorageFile.mkdirs()){
                return null;
            }
        }

        return mMediaStorageFile;
    }

    public static File getCameraOutputMediaFile(Activity activity){

        File mMediaStorageFile = getMediaStorageFile(activity);

        if (mMediaStorageFile == null) {
            return null;
        }

        File mediaFile;
        String mImageName="pic_camera" +".jpg";
        mediaFile = new File(mMediaStorageFile.getPath() + File.separator + mImageName);

        return mediaFile;
    }

    public static File getOutputMediaFile(Activity activity, boolean isCropped){

        File mMediaStorageFile = getMediaStorageFile(activity);

        if (mMediaStorageFile == null) {
            return null;
        }

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName="pic_" + (isCropped ? "small": "") + timeStamp +".jpg";
        mediaFile = new File(mMediaStorageFile.getPath() + File.separator + mImageName);

        return mediaFile;
    }
}
