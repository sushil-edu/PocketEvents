package kestone.com.pocketevents.UI;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kestone.com.pocketevents.MODEL.Manager.MySingleton;
import kestone.com.pocketevents.MODEL.Register.REQUEST.RegisterRequest;
import kestone.com.pocketevents.MODEL.Register.RESPONSE.InsertSignUPResponse;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.Utilities.CONSTANTS;
import kestone.com.pocketevents.Utilities.GeneralUtils;
import kestone.com.pocketevents.Utilities.GenericRequest;
import kestone.com.pocketevents.Utilities.PrefEntities;
import kestone.com.pocketevents.Utilities.StorageUtilities;
import qiu.niorgai.StatusBarCompat;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UpdateDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 101;
    private static final int CAMERA_REQUEST = 102;
    EditText fname,email,designation,organisation,address,mobile;
    CircleImageView profilePicture;
    String encodedImage = "";
    StorageUtilities storage;
    TextView submit;
    Dialog dialog;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);
        progressDialog = new ProgressDialog(UpdateDetailsActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
       // StatusBarCompat.translucentStatusBar(UpdateDetailsActivity.this,true);
        fname = (EditText)findViewById(R.id.update_fName);
//        lname = (EditText)findViewById(R.id.update_lName);
        email = (EditText)findViewById(R.id.update_email);
        designation = (EditText)findViewById(R.id.update_designation);
        organisation = (EditText)findViewById(R.id.update_organisation);
        mobile = (EditText)findViewById(R.id.update_mobile);
        profilePicture = (CircleImageView)findViewById(R.id.update_profileImage);
        address=(EditText)findViewById(R.id.update_address);
        profilePicture.setOnClickListener(this);



        storage = new StorageUtilities(getApplicationContext());

        encodedImage = storage.loadProfilePic();

        dialog = new Dialog(UpdateDetailsActivity.this);


        checkAndRequestPermissions();

        dialog.setCancelable(true);

        submit = (TextView) findViewById(R.id.update_btn_update);
        submit.setOnClickListener(this);
        fname.setText(storage.loadfirstName());
//        lname.setText(storage.loadLastName());
        email.setText(storage.loadEmail());
        mobile.setText(storage.loadPhone());
        if (!storage.LoadKey(PrefEntities.DESIGNATION).equals("9999"))
            designation.setText(storage.LoadKey(PrefEntities.DESIGNATION));
        if (!storage.LoadKey(PrefEntities.COMPANY).equals("9999"))
            organisation.setText(storage.LoadKey(PrefEntities.COMPANY));
        if (!storage.LoadKey(PrefEntities.ADDRESSS).equals("9999"))
            address.setText(storage.LoadKey(PrefEntities.ADDRESSS));


        if (storage.loadProfilePic().length()>5) {

            try {
                byte[] decodedString = Base64.decode(storage.loadProfilePic(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                profilePicture.setImageBitmap(decodedByte);
            }catch (Exception e){profilePicture.setImageResource(R.drawable.user_profile_image_grey);}
        }else {
            profilePicture.setImageResource(R.drawable.user_profile_image_grey);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.update_btn_update :
                if (GeneralUtils.isEmailValid(email.getText().toString())){
                    if (fname.getText().toString().length()>1){
                        if (GeneralUtils.isValidMobNumber(mobile.getText().toString())) {

                            if (GeneralUtils.isNetworkAvailable(UpdateDetailsActivity.this)){
                                progressDialog.show();
                                Update(fname.getText().toString(), "", email.getText().toString(), designation.getText().toString(), organisation.getText().toString(),
                                        mobile.getText().toString() ,address.getText().toString());
                            }
                            else {
                                GeneralUtils.displayNetworkAlert(UpdateDetailsActivity.this,false);
                            }

                        }else {
                            GeneralUtils.ShowAlert(UpdateDetailsActivity.this,getString(R.string.ValidMobile));
                        }
                    }else {
                        GeneralUtils.ShowAlert(UpdateDetailsActivity.this,getString(R.string.ValidName));
                    }
                }else {
                    GeneralUtils.ShowAlert(UpdateDetailsActivity.this,getString(R.string.ValidEmail));
                }
                break;
            case R.id.update_profileImage :
                dialog.setContentView(R.layout.dialogue_profile_picture);
                TextView takePicture = (TextView) dialog.findViewById(R.id.takePicture);
                TextView selectFromGallery = (TextView) dialog.findViewById(R.id.selectFromGallery);
                LinearLayout galleryLl = (LinearLayout) dialog.findViewById(R.id.galleryLl);
                LinearLayout cameraLl = (LinearLayout) dialog.findViewById(R.id.cameraLl);

                cameraLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ContextCompat.checkSelfPermission(UpdateDetailsActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(UpdateDetailsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(UpdateDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            dispatchTakePictureIntent();
                            dialog.dismiss();
                        }


                    }
                });
                galleryLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ContextCompat.checkSelfPermission(UpdateDetailsActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(UpdateDetailsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(UpdateDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            Intent intent = new Intent();
                            // Show only images, no videos or anything else
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            // Always show the chooser (if there are multiple options available)
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            dialog.dismiss();
                        }


                    }
                });
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Result from Gallery :
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()/2,bitmap.getHeight()/2,true);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                // Log.d(TAG, String.valueOf(bitmap));

                profilePicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Result from Camera :
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }






    private void previewCapturedImage() {
        try {


            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 2;

            final Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath,
                    options);

            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap rotatedBitmap = null;
            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                    rotatedBitmap = bitmap;
                    break;

                default:
                    rotatedBitmap = bitmap;
                    break;
            }



            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            rotatedBitmap = Bitmap.createScaledBitmap(rotatedBitmap,rotatedBitmap.getWidth()/3,rotatedBitmap.getHeight()/3,true);
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

            profilePicture.setImageBitmap(rotatedBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }




    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "kestone.com.kestone.Utilities.GenericFileProvider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }




    private boolean checkAndRequestPermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);


        int storagePermission = ContextCompat.checkSelfPermission(this,


                Manifest.permission.READ_EXTERNAL_STORAGE);



        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,


                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), CAMERA_REQUEST);
            return false;
        }

        return true;
    }

    void Update(final String fname, final String lname, final String email, String designation, String organisation, final String mobile,final String address){

        //storage.storeProfilePic(encodedImage);
        GeneralUtils.log("Profile Image",encodedImage);
        GenericRequest<InsertSignUPResponse> request = new GenericRequest<InsertSignUPResponse>(Request.Method.POST, CONSTANTS.URL_INSERT_SIGNUP, InsertSignUPResponse.class, new RegisterRequest(email,
                "", fname, lname, organisation, designation, encodedImage, "0", storage.loadDeviceToken(), "1",mobile,address,""),
                new Response.Listener<InsertSignUPResponse>() {
            @Override
            public void onResponse(InsertSignUPResponse response) {
                progressDialog.dismiss();
                if (Boolean.valueOf(response.getResponse().get(0).getStatus())){
                   // GeneralUtils.ShowAlert(UpdateDetailsActivity.this,getString(R.string.UpdateSuccessful));
                   final ScrollView update_detail=(ScrollView)findViewById(R.id.update_detail);
                    Snackbar snackbar = Snackbar.make(update_detail, getString(R.string.UpdateSuccessful),Snackbar.LENGTH_LONG);
                    snackbar.show();

                    storage.storeProfilePic(encodedImage);
                    storage.storeFirstName(fname);
//                   storage.storeLastName(lname);
                    storage.storeEmail(email);
                    storage.storePhone(mobile);
                    storage.StoreKey(PrefEntities.DESIGNATION,response.getResponse().get(0).getPayloads().get(0).getDesignation());
                    storage.StoreKey(PrefEntities.COMPANY,response.getResponse().get(0).getPayloads().get(0).getOrganisation());
                    storage.StoreKey(PrefEntities.ADDRESSS,response.getResponse().get(0).getPayloads().get(0).getAddress());
//
                    Intent i = new Intent(UpdateDetailsActivity.this, CitySearchActivity.class);
                    startActivity(i);
                } else {
                   final ScrollView update_detail=(ScrollView)findViewById(R.id.update_detail);
                    Snackbar snackbar = Snackbar.make(update_detail, MySingleton.getInstance().getModel().getResponse().get(0).getMessage(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(UpdateDetailsActivity.this,getString(R.string.VolleyTimeout));

            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
