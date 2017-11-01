package hw0036493852.android.fer.hr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.health.PackageHealthStats;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;

import java.text.Normalizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hw0036493852.android.fer.hr.modules.FormResponse;
import hw0036493852.android.fer.hr.networking.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST = 101;

    @BindView(R.id.pathInput)
    EditText pathInput;
    @BindView(R.id.loadButton)
    Button loadButton;
    @BindView(R.id.avatarView)
    ImageView avatarView;
    @BindView(R.id.nameInput)
    TextView nameInput;
    @BindView(R.id.lastnameInput)
    TextView lastnameInput;
    @BindView(R.id.phoneNumberInput)
    TextView phoneNumberInput;
    @BindView(R.id.emailInput)
    TextView emailInput;
    @BindView(R.id.spouseInput)
    TextView spouseInput;
    @BindView(R.id.ageInput)
    TextView ageInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://m.uploadedit.com")
//                .baseUrl("https://api.myjson.com") //test json at https://api.myjson.com/bins/h6ieb
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitService service = retrofit.create(RetrofitService.class);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.getForm(pathInput.getText().toString()).enqueue(new Callback<FormResponse>() {
                    @Override
                    public void onResponse(Call<FormResponse> call, Response<FormResponse> response) {
                        FormResponse result = response.body();

                        try {
                            setData(result);
                        } catch (Exception ex) {
                            onFailure(call, ex);
                        }
                    }

                    @Override
                    public void onFailure(Call<FormResponse> call, Throwable t) {
                        Toast.makeText(FormActivity.this, "Something went wrong: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setData(FormResponse result) {
        if (result.getAvatarUrl() == null || result.getAvatarUrl().isEmpty()) {
            avatarView.setVisibility(View.GONE);
        } else {
            Glide.with(FormActivity.this).load(result.getAvatarUrl()).into(avatarView);
        }

        if (result.getName() != null && !result.getName().isEmpty()) {
            nameInput.setText(result.getName().toString());
        }

        if (result.getLastName() != null && !result.getLastName().isEmpty()) {
            lastnameInput.setText(result.getLastName().toString());
        }

        if (result.getPhoneNumber() != null && !result.getPhoneNumber().isEmpty()) {
            phoneNumberInput.setText(result.getPhoneNumber().toString());
        }

        if (result.getEmail() != null && !result.getEmail().isEmpty()) {
            emailInput.setText(result.getEmail().toString());
        }

        if (result.getSpouse() != null && !result.getSpouse().isEmpty()) {
            spouseInput.setText(result.getSpouse().toString());
        }

        if (result.getAge() != null && !result.getAge().isEmpty()) {
            ageInput.setText(result.getAge().toString());
        }
    }

    @OnClick(R.id.emailInput)
    void sendEmail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{emailInput.getText().toString()});
        try {
            startActivity(Intent.createChooser(i, "Pošalji email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(FormActivity.this, "Ne postoji email klijent", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.phoneNumberInput)
    void callNumber() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumberInput.getText().toString()));
        startActivity(callIntent);
    }
}
