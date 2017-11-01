package hw0036493852.android.fer.hr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity that displays calculation result
 */
public class DisplayActivity extends AppCompatActivity {

    /**
     * List of email addresses to which to send reports
     */
    private static final String[] EMAIL = {"ana@baotic.org", "franko.car@hotmail.com"};
    /**
     * Subject of an email report
     */
    private static final String SUBJECT = "0036493852: dz report";

    /**
     * Label that shows a result
     */
    @BindView(R.id.resultLabel)
    TextView resultLabel;

    /**
     * Generated result message
     */
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null && extras.containsKey(CalculusActivity.RESULT_KEY)) {
            message = extras.getString(CalculusActivity.RESULT_KEY);
            resultLabel.setText(message);
        } else {
            resultLabel.setText("Error");
        }
    }

    /**
     * Reports an OK result invoked by the OK button
     */
    @OnClick(R.id.okButton)
    void closeScreen() {
        setResult(RESULT_OK);
        finish();
    }

    /**
     * Sends an email report when the button is clicked
     */
    @OnClick(R.id.reportButton)
    void sendEmail() {
        if (message == null) {
            return;
        }

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , EMAIL);
        i.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
        i.putExtra(Intent.EXTRA_TEXT   , message);
        try {
            startActivity(Intent.createChooser(i, "Pošalji email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DisplayActivity.this, "Email klijent nije pronađen", Toast.LENGTH_SHORT).show();
        }
    }
}
