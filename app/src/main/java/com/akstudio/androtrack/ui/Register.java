package com.akstudio.androtrack.ui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.akstudio.androtrack.R;
import com.akstudio.androtrack.model.CustomResponse;
import com.akstudio.androtrack.model.User;
import com.akstudio.androtrack.restclient.Api;
import com.akstudio.androtrack.restclient.UserApi;
import com.akstudio.androtrack.utils.Fields;
import com.akstudio.androtrack.utils.Util;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;

@Fullscreen
@EActivity(R.layout.activity_register)
public class Register extends AppCompatActivity {

    @ViewById
    EditText register_edittext_name;

    @ViewById
    EditText register_edittext_email;

    @ViewById
    EditText register_edittext_password;

    @ViewById
    EditText register_edittext_repassword;


    @Click(R.id.register_btn_register)
    public void btnRegister() {
        String name = register_edittext_name.getText().toString();
        String email = register_edittext_email.getText().toString();
        String password = register_edittext_password.getText().toString();
        String repassword = register_edittext_repassword.getText().toString();
        if (StringUtils.isBlank(name) || StringUtils.isBlank(email) || StringUtils.isBlank(password) || StringUtils.isBlank(repassword)) {
            showErrorMessage(Fields.ERROR_MISSING_INFORMATION);
            return;
        }
        if (!Util.isValidEmail(email)) {
            showErrorMessage(Fields.ERROR_EMAIL);
            return;
        }
        if (!password.equals(repassword)) {
            showErrorMessage(Fields.ERROR_PASSWORD_MISMATCH);
            return;
        }
        UserApi service = Api.getInstance().getRetrofit().create(UserApi.class);
        Call<CustomResponse> call = service.registerUser(new User(name, email, password));
        call.enqueue(new Callback<CustomResponse>() {
            @Override
            public void onResponse(Call<CustomResponse> call, retrofit2.Response<CustomResponse> response) {
                if (!response.isSuccessful()) {
                    Log.i("TAG", call.toString());
                    return;
                }
                CustomResponse decodedResponse = response.body();
                if (decodedResponse == null) return;
                if (!decodedResponse.getSuccess()){
                    showErrorMessage(decodedResponse.getMessage());
                }
                Log.i("TAG", decodedResponse.toString());
            }

            @Override
            public void onFailure(Call<CustomResponse> call, Throwable t) {
                showErrorMessage(Fields.ERROR_REGISTRATION);
                Log.i("TAG", t.getMessage());
            }
        });
    }

    public void showErrorMessage(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(Register.this).create();
        alertDialog.setTitle("Message");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}