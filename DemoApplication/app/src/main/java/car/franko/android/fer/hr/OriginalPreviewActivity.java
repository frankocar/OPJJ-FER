package car.franko.android.fer.hr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import car.franko.android.fer.hr.modules.ImageResponse;

public class OriginalPreviewActivity extends AppCompatActivity {

    public static final String PAYLOAD_KEY = "payload";

    private Button close;
    private EditText bundleInput;
    private ImageView cat;
    private boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        close = (Button) findViewById(R.id.close);
        bundleInput = (EditText) findViewById(R.id.bundleInput);
        cat = (ImageView) findViewById(R.id.cat);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(PAYLOAD_KEY)) {
            ImageResponse result = (ImageResponse) extras.getSerializable(PAYLOAD_KEY);

            Toast.makeText(OriginalPreviewActivity.this, result.getUrl(), Toast.LENGTH_SHORT).show();
            success = true;

            bundleInput.setText(result.getUrl());

            Glide.with(this).load(result.getUrl()).into(cat);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();

                ImageResponse response = new ImageResponse();
                response.setUrl(bundleInput.getText().toString());

                Gson gson = new Gson();
                String json = gson.toJson(response);

                extras.putString(PAYLOAD_KEY, json);

//                extras.putString(PAYLOAD_KEY, bundleInput.getText().toString());
//                setResult(success ? RESULT_OK : RESULT_CANCELED);
                Intent intent = new Intent();
                intent.putExtras(extras);

                setResult(success ? RESULT_OK : RESULT_CANCELED, intent);
                finish();
            }
        });

    }
}
