package kestone.com.pocketevents.Utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kestone.com.pocketevents.R;
import kestone.com.pocketevents.UI.ForgotPasswordActivity;

/**
 * Created by karan on 7/27/2017.
 */

public class GeneralUtils {



    public static void ShowAlert(Context context, String message){

        final Dialog successDialog = new Dialog(context);
        successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        successDialog.setCancelable(true);
        successDialog.setContentView(R.layout.dialog_general_utils_custom);
        TextView messageTitle=(TextView)successDialog.findViewById(R.id.messageTitle);
        TextView messageBody=(TextView)successDialog.findViewById(R.id.messageBody);
        messageBody.setText(message);
        TextView gotIt = (TextView) successDialog.findViewById(R.id.button_ok);
        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                successDialog.dismiss();
            }
        });
        successDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        successDialog.show();
    }
    public static void ShowAlert(Context context, String message,String title ){

        final Dialog successDialog = new Dialog(context);
        successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        successDialog.setCancelable(true);
        successDialog.setContentView(R.layout.dialog_general_utils_custom);
        TextView messageTitle=(TextView)successDialog.findViewById(R.id.messageTitle);
        messageTitle.setText(title);
        TextView messageBody=(TextView)successDialog.findViewById(R.id.messageBody);
        messageBody.setText(message);
        TextView gotIt = (TextView) successDialog.findViewById(R.id.button_ok);
        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                successDialog.dismiss();
            }
        });
        successDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        successDialog.show();
    }


    public static void CustomDialog(Context context,String TitleMessage,String BodyMessage){

        final Dialog successDialog = new Dialog(context);
        successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        successDialog.setCancelable(true);
        successDialog.setContentView(R.layout.dialog_general_utils_custom);
        TextView messageTitle=(TextView)successDialog.findViewById(R.id.messageTitle);
        TextView messageBody=(TextView)successDialog.findViewById(R.id.messageBody);
        if (TitleMessage.equals("")||TitleMessage.equals(null)){
            messageTitle.setText(R.string.app_name);
        }
        else {
            messageTitle.setText(TitleMessage);
        }
        if (BodyMessage.equals("")||BodyMessage.equals(null)){
            messageBody.setText("Something doesn't see right.");
        }
        else {
            messageBody.setText(BodyMessage);
        }


        TextView gotIt = (TextView) successDialog.findViewById(R.id.button_ok);
        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                successDialog.dismiss();
            }
        });
        successDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        successDialog.show();

    }

    public static void CustomDialogSucessWithImage(Context context,String TitleMessage,String BodyMessage){

        final Dialog successDialog = new Dialog(context);
        successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        successDialog.setCancelable(true);
        successDialog.setContentView(R.layout.dialogue_event_saved);
        TextView messageTitle=(TextView)successDialog.findViewById(R.id.messageTitle);
        TextView messageBody=(TextView)successDialog.findViewById(R.id.messageBody);
        if (TitleMessage.equals("")||TitleMessage.equals(null)){
            messageTitle.setText(R.string.app_name);
        }
        else {
            messageTitle.setText(TitleMessage);
        }
        if (BodyMessage.equals("")||BodyMessage.equals(null)){
            messageBody.setText("Event configuration has been saved.");
        }
        else {
            messageBody.setText(BodyMessage);
        }


        TextView gotIt = (TextView) successDialog.findViewById(R.id.eventSaved);
        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                successDialog.dismiss();
            }
        });
        successDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        successDialog.show();

    }


    public static void log(String tag, String msg) {

        if (CONSTANTS.isDebug) {
            Log.d(tag, msg);
        }
    }







    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }






    public static boolean isValidMobNumber(String phoneNumber) {
        boolean check;
        if (phoneNumber.trim().length() == 10) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }

    public static void displayNetworkAlert(final Context context, final boolean isFinish) {

        new android.app.AlertDialog.Builder(context)
                .setMessage("Please Check Your Internet Connection and Try Again")
                .setTitle("Network Error")
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (isFinish) {
                                    dialog.dismiss();
                                    ((Activity) context).finish();
                                } else {
                                    dialog.dismiss();
                                }
                            }
                        }).show();
    }


    public static boolean isNetworkAvailable(Context mContext) {

		/* getting systems Service connectivity manager */
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (mConnectivityManager != null) {
            NetworkInfo[] mNetworkInfos = mConnectivityManager.getAllNetworkInfo();
            if (mNetworkInfos != null) {
                for (NetworkInfo mNetworkInfo : mNetworkInfos) {
                    if (mNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }





//    public static int calculateInSampleSize(BitmapFactory.Options options,
//                                            int reqWidth, int reqHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > reqHeight || width > reqWidth) {
//
//            // Calculate ratios of height and width to requested height and
//            // width
//            final int heightRatio = Math.round((float) height
//                    / (float) reqHeight);
//            final int widthRatio = Math.round((float) width / (float) reqWidth);
//
//            // Choose the smallest ratio as inSampleSize value, this will
//            // guarantee
//            // a final image with both dimensions larger than or equal to the
//            // requested height and width.
//            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//        }
//
//        return inSampleSize;
//    }






//    public static Bitmap getBitmapFromPath(String FilePath) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(FilePath, options);
//        int imageHeight = options.outHeight;
//        int imageWidth = options.outWidth;
//        String imageType = options.outMimeType;
//        if (imageWidth > imageHeight) {
//            options.inSampleSize = calculateInSampleSize(options, 512, 256);// if
//            // landscape
//        } else {
//            options.inSampleSize = calculateInSampleSize(options, 256, 512);// if
//            // portrait
//        }
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeFile(FilePath, options);
//    }
//
//
//
//
//    public static Bitmap decodeSampledBitmapFromResource(String path,
//                                                         Resources res, int resId, int reqWidth, int reqHeight) {
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        // BitmapFactory.decodeResource(res, resId, options);
//        BitmapFactory.decodeFile(path, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth,
//                reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeFile(path, options);
//    }
//
//
//
//
//
//    public static Bitmap makeBitmap(String path, Context mContext, int width,
//                                    int height) {
//        int orient = 0;
//
//		/*
//         * Display dispDefault = ((WindowManager)
//		 * mContext.getSystemService(Context.WINDOW_SERVICE))
//		 * .getDefaultDisplay(); DisplayMetrics dispMetrics = new
//		 * DisplayMetrics(); dispDefault.getMetrics(dispMetrics);
//		 *
//		 * Point mPoint = CommonUtils.getDisplaySize(dispDefault);
//		 */
//        Resources resource = mContext.getResources();
//        // int screenWidth = resource.getDisplayMetrics().widthPixels;
//        // int screenHeight = resource.getDisplayMetrics().heightPixels;
//
//        try {
//            ExifInterface ei = new ExifInterface(path);
//            orient = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Old code for load bitmaps
//        // BitmapFactory.Options opt = new BitmapFactory.Options();
//        // opt.inSampleSize = 4;
//        // Bitmap image = BitmapFactory.decodeFile(path, opt);
//
//        Bitmap image = decodeSampledBitmapFromResource(path, resource, 1,
//                width, height);
//
//        Matrix matrix = new Matrix();
//        switch (orient) {
//            case ExifInterface.ORIENTATION_ROTATE_180:
//                matrix.postRotate(180);
//                break;
//            case ExifInterface.ORIENTATION_ROTATE_270:
//                matrix.postRotate(270);
//                break;
//            case ExifInterface.ORIENTATION_ROTATE_90:
//                matrix.postRotate(90);
//                break;
//            default:
//                matrix.postRotate(0);
//        }
//        image = Bitmap.createBitmap(image, 0, 0, image.getWidth(),
//                image.getHeight(), matrix, true);
//        matrix.reset();
//
//        float scaleWidth = ((float) width) / image.getWidth();
//        float scaleHeight = ((float) height) / image.getHeight();
//        float scale = Math.min(scaleWidth, scaleHeight);
//        matrix.postScale(scale, scale);
//
//        return Bitmap.createBitmap(image, 0, 0, image.getWidth(),
//                image.getHeight(), matrix, true);
//    }


    public static String extractYoutubeId(String url) throws MalformedURLException {
        String query = new URL(url).getQuery();
        String[] param = query.split("&");
        String id = null;
        for (String row : param) {
            String[] param1 = row.split("=");
            if (param1[0].equals("v")) {
                id = param1[1];
            }
        }
        return id;
    }

//    public static boolean hasGingerbread() {
//        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
//    }
//
//
//    public static boolean hasHoneycomb() {
//        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
//    }

     public static boolean hasKitKat() {
     return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
     }

    public static boolean hasMarshMallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }


//    public static boolean hasHoneycombMR1() {
//        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
//    }
//
//
//    public static boolean hasICS() {
//        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
//    }






//    public static boolean isNetworkAvailable(Context mContext) {
//
//		/* getting systems Service connectivity manager */
//        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        if (mConnectivityManager != null) {
//            NetworkInfo[] mNetworkInfos = mConnectivityManager.getAllNetworkInfo();
//            if (mNetworkInfos != null) {
//                for (NetworkInfo mNetworkInfo : mNetworkInfos) {
//                    if (mNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }


}
