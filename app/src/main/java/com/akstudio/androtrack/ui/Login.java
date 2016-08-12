package com.akstudio.androtrack.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.akstudio.androtrack.R;
import com.akstudio.androtrack.model.CustomResponse;
import com.akstudio.androtrack.restclient.Api;
import com.akstudio.androtrack.restclient.UserApi;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Callback;

@Fullscreen
@EActivity(R.layout.activity_login)
public class Login extends AppCompatActivity {

    @ViewById
    EditText login_edittext_email;

    @ViewById
    EditText login_edittext_password;

    @Click(R.id.login_btn_login)
    public void btnLogin() {
        final String email = login_edittext_email.getText().toString();
        String password = login_edittext_password.getText().toString();
        if (email == null || email.isEmpty()) {
            return;
        }
        if (password == null || password.isEmpty()) {
            return;
        }
        UserApi service = Api.getInstance().getRetrofit().create(UserApi.class);
        Call<CustomResponse> call = service.loginUser(email, password);
        call.enqueue(new Callback<CustomResponse>() {
            @Override
            public void onResponse(Call<CustomResponse> call, retrofit2.Response<CustomResponse> response) {
                if (!response.isSuccessful()) {
                }
                CustomResponse decodedResponse = response.body();
                if (decodedResponse == null) return;
                if (decodedResponse.getSuccess()) {
                    startActivity(new Intent(Login.this, Settings.class));
                }
                Log.i("TAG", decodedResponse.toString());
            }

            @Override
            public void onFailure(Call<CustomResponse> call, Throwable t) {
                Log.i("TAG", "onFailure");
                Log.i("TAG", t.getMessage());
            }
        });

    }


    @Click(R.id.login_textview_signup)
    public void btnRegister() {
/*        Intent i = new Intent(this, Register_.class);
        startActivityForResult(i, 1);*/
    }

}
