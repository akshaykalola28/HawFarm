package com.project.hawfarm;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    Button signInButton;
    EditText nameField, emailField, passField, cpassField, mobileField, addressField, pincodeField;
    ScrollView signUpScrollView;
    Button submitDataButton;
    String name, email, pass, cpass, address, mobileString, pincodeString, baseImg;
    ProgressDialog mDialog;
    ImageView profileField;
    Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameField = findViewById(R.id.input_name);
        emailField = findViewById(R.id.input_email);
        passField = findViewById(R.id.input_password);
        cpassField = findViewById(R.id.confirm_password);
        mobileField = findViewById(R.id.mo_number);
        addressField = findViewById(R.id.input_address);
        pincodeField = findViewById(R.id.input_pincode);
        submitDataButton = findViewById(R.id.btn_signup);
        profileField = findViewById(R.id.input_profile_submit);

        signUpScrollView = findViewById(R.id.sign_up_scroll);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            signUpScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY < oldScrollY) {
                        //Scroll Down
                    } else {
                        //Scroll Up
                    }
                }
            });
        }
        signUpScrollView.setSmoothScrollingEnabled(true);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        profileField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });

        signInButton = findViewById(R.id.link_login);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        submitDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getValidData()) {
                    mDialog = new ProgressDialog(SignUpActivity.this);
                    mDialog.setMessage("Please Wait..");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    submitData();
                }
            }
        });
        setAnimations();
        changeStatusBarColor();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void setAnimations() {
        CardView SignuoCardview = findViewById(R.id.sign_up_cardview);
        Animation fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        SignuoCardview.setAnimation(fromBottom);

        //TODO: Uncomment if you are adding a logo
        /*ImageView logoImageView = findViewById(R.id.company_logo);
        Animation fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        logoImageView.setAnimation(fromtop);*/
    }

    private boolean getValidData() {
        boolean valid = false;
        name = nameField.getText().toString().trim();
        email = emailField.getText().toString().trim();
        pass = passField.getText().toString().trim();
        cpass = cpassField.getText().toString().trim();
        mobileString = mobileField.getText().toString().trim();
        address = addressField.getText().toString().trim();
        pincodeString = pincodeField.getText().toString().trim();

        if (name.isEmpty()) {
            nameField.setError("Enter Name");
            nameField.requestFocus();
        } else if (email.isEmpty()) {
            emailField.setError("Enter Email");
            emailField.requestFocus();
        } else if (pass.isEmpty()) {
            passField.setError("Enter Password");
            passField.requestFocus();
        } else if (cpass.isEmpty() || !pass.equals(cpass)) {
            cpassField.setError("Password are not match");
            cpassField.requestFocus();
        } else if (mobileString.isEmpty() || mobileString.length() != 10) {
            mobileField.setError("Enter valid Mobile Number");
            mobileField.requestFocus();
        } else if (address.isEmpty()) {
            addressField.setError("Enter Address");
            addressField.requestFocus();
        } else if (pincodeString.isEmpty()) {
            pincodeField.setError("Enter Pincode");
            pincodeField.requestFocus();
        } else {
            valid = true;
        }
        return valid;
    }

    private void submitData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerData.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("data");
                            if (data.equals("ER_DUP_ENTRY")) {
                                mDialog.dismiss();
                                Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),
                                        "User Already Exists. Please try to LogIn", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Log In", new View.OnClickListener() {
                                            public void onClick(View v) {
                                                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                                                finish();
                                            }
                                        }).show();
                            } else {
                                mDialog.dismiss();
                                Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),
                                        data, Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Log In", new View.OnClickListener() {
                                            public void onClick(View v) {
                                                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                                                finish();
                                            }
                                        }).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mDialog.dismiss();
                        Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),
                                "Something is Wrong! Please try again.", Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("phone", mobileString);
                params.put("password", pass);
                //TODO: Change the user type
                params.put("user_type", "customer");
                params.put("address", address);
                params.put("pincode", pincodeString);
                params.put("base64Str", baseImg);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
        requestQueue.add(stringRequest);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        try {
            // When an Image is picked
            if (requestCode == 100 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                profileField.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                Bitmap bitmapForProfile = BitmapFactory.decodeFile(picturePath);

                //Base-64
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmapForProfile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                baseImg = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            } else {
                //TODO: remove resultCode on release
                Toast.makeText(SignUpActivity.this, "You haven't picked up Image: " + resultCode, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
