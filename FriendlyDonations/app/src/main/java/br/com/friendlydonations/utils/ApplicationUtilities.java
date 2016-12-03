package br.com.friendlydonations.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.friendlydonations.R;

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

    public static void showPermissionRequest(Context context) {
        View dialogPermissionView = LayoutInflater.from(context).inflate(R.layout.alert_permissions, null, false);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setView(dialogPermissionView);
        final AlertDialog mAlertDialog = mBuilder.show();

        dialogPermissionView.findViewById(R.id.tvNo).setOnClickListener(v -> {
            mAlertDialog.dismiss();
        });

        dialogPermissionView.findViewById(R.id.tvYes).setOnClickListener(v -> {
            mAlertDialog.dismiss();
            Intent mIntent = new Intent();
            mIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri mUri = Uri.fromParts("package", context.getPackageName(), null);
            mIntent.setData(mUri);
            context.startActivity(mIntent);
        });
    }
}
