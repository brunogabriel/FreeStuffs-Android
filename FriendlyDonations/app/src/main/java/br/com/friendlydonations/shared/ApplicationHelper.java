package br.com.friendlydonations.shared;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by brunogabriel on 13/02/17.
 */

public class ApplicationHelper {

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

    public static Bitmap createBitmapFromURI(File imagePath) {
        Bitmap originalBitmap = BitmapFactory.decodeFile(imagePath.getPath());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, 75, out);
        return BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
    }
}
