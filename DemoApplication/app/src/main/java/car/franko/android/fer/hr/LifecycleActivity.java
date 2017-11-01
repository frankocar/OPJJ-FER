package car.franko.android.fer.hr;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import car.franko.android.fer.hr.modules.ImageResponse;
import car.franko.android.fer.hr.networking.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LifecycleActivity extends AppCompatActivity {

    public static final int SCREEN_REQUEST_CODE = 100;

    private EditText firstInput;
    private EditText secondInput;
    private Button calculate;
    private TextView result;
    private Button openNewScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        Log.d("JAVA TEČAJ", "onCreate");

        firstInput = (EditText) findViewById(R.id.firstInput);
        secondInput = (EditText) findViewById(R.id.secondInput);
        calculate = (Button) findViewById(R.id.calculate);
        result = (TextView) findViewById(R.id.result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://m.uploadedit.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitService service = retrofit.create(RetrofitService.class);

        openNewScreen = (Button) findViewById(R.id.openNewScreen);

        openNewScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.getImage().enqueue(new Callback<ImageResponse>() {
                    @Override
                    public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                        ImageResponse successfulResponse = response.body();

                        Intent intent = new Intent(LifecycleActivity.this, PreviewActivity.class);
                        Bundle extras = new Bundle();

                        extras.putSerializable(PreviewActivity.PAYLOAD_KEY, successfulResponse);
                        intent.putExtras(extras);
                        startActivityForResult(intent, SCREEN_REQUEST_CODE);
                        Log.d("JAVA TEČAJ", "Završen onCreate()");
                    }

                    @Override
                    public void onFailure(Call<ImageResponse> call, Throwable t) {
                        Toast.makeText(LifecycleActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first = firstInput.getText().toString();
                String second = secondInput.getText().toString();

                int firstNumber = 0;
                try {
                    firstNumber = Integer.parseInt(first);
                } catch (NumberFormatException ex) {
                    Toast.makeText(LifecycleActivity.this, "Unesite prvi broj", Toast.LENGTH_SHORT).show();
                }

                int secondNumber = 0;
                try {
                    secondNumber = Integer.parseInt(second);
                } catch (NumberFormatException ex) {
                    Toast.makeText(LifecycleActivity.this, "Unesite drugi broj", Toast.LENGTH_SHORT).show();
                }

                int sum = firstNumber + secondNumber;

                result.setText(Integer.toString(sum));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("JAVA TEČAJ", "onActivityResult");
        if (requestCode == SCREEN_REQUEST_CODE) {
            if (data != null) {
                String json = data.getStringExtra(PreviewActivity.PAYLOAD_KEY);

                Gson gson = new Gson();
                ImageResponse imageResponse = gson.fromJson(json, ImageResponse.class);

                Toast.makeText(LifecycleActivity.this, gson.toJson(imageResponse), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LifecycleActivity.this, ((resultCode==RESULT_OK) ? "OK" : "NOT OK"), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("JAVA TEČAJ", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("JAVA TEČAJ", "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("JAVA TEČAJ", "onStart");
    }
}
