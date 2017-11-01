package hw0036493852.android.fer.hr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity that does simple arithmetics
 */
public class CalculusActivity extends AppCompatActivity {

    /**
     * Request code for display activity
     */
    public static final int SCREEN_REQUEST_CODE = 100;

    /**
     * Key for result data in a bundle
     */
    public static final String RESULT_KEY = "result";

    /**
     * Radio button group
     */
    @BindView(R.id.operationGroup)
    RadioGroup operationGroup;

    /**
     * Input field for the first number
     */
    @BindView(R.id.firstInput)
    EditText firstInput;

    /**
     * Input field for the second number
     */
    @BindView(R.id.secondInput)
    EditText secondInput;

    /**
     * Button that starts a calculation
     */
    @BindView(R.id.calculate)
    Button calculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);

        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCREEN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                firstInput.setText("");
                secondInput.setText("");
            }
        }
    }

    /**
     * Initiates a calculation when the button in pressed
     */
    @OnClick(R.id.calculate)
    void calculate() {
        Intent intent = new Intent(CalculusActivity.this, DisplayActivity.class);
        String operation = "";
        double result = 0;
        double firstNumber = 0;
        double secondNumber = 0;
        String message;

        Bundle extras = new Bundle();
        try {
            firstNumber = Double.parseDouble(firstInput.getText().toString());
            secondNumber = Double.parseDouble(secondInput.getText().toString());

            int operationId = operationGroup.getCheckedRadioButtonId();

            switch (operationId) {
                case (R.id.addRadioButton):
                    result = firstNumber + secondNumber;
                    operation = "+";
                    break;
                case (R.id.subRadioButton):
                    result = firstNumber - secondNumber;
                    operation = "-";
                    break;
                case (R.id.mulRadioButton):
                    result = firstNumber * secondNumber;
                    operation = "*";
                    break;
                case (R.id.divRadioButton):
                    result = firstNumber /+ secondNumber;
                    operation = "/";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation");
            }

            message = String.format(
                    "Rezultat operacije %s je %.4f.%n",
                    operation,
                    result
            );
        } catch (Exception ex) {
            message = String.format(
                    "Prilikom obavljanja operacije %s nad unosima %s i %s došlo je do sljedeće greške: %s%n",
                    operation,
                    firstInput.getText().toString(),
                    secondInput.getText().toString(),
                    ex.getMessage()
            );
        }
        extras.putString(RESULT_KEY, message);

        intent.putExtras(extras);
        startActivityForResult(intent, SCREEN_REQUEST_CODE);
    }
}
