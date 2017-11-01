package hw0036493852.android.fer.hr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main activity that gives the user an option to open {@link CalculusActivity}
 * or {@link FormActivity}
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Button that opens {@link CalculusActivity}
     */
    @BindView(R.id.mathButton)
    Button mathButton;

    /**
     * Button that opens {@link FormActivity}
     */
    @BindView(R.id.statButton)
    Button statButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalculusActivity.class);
                startActivity(intent);
            }
        });

        statButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });
    }
}
