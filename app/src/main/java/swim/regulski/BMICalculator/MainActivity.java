package swim.regulski.BMICalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import swim.regulski.BMICalculator.logic.IBMICalc;
import swim.regulski.BMICalculator.logic.ImperialBMICalc;
import swim.regulski.BMICalculator.logic.MetricBMICalc;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.weight_ET)
    EditText weightET;
    @BindView(R.id.height_ET)
    EditText heightET;
    @BindView(R.id.metric_imperial_S)
    Switch metricImperialS;
    @BindView(R.id.weight_unit_TV)
    TextView weightUnitTV;
    @BindView(R.id.height_unit_TV)
    TextView heightUnitTV;
    @BindView(R.id.bmi_value_TV)
    TextView bmiTV;
    @BindView(R.id.bmi_message_TV)
    TextView bmiMessageTV;
    @BindView(R.id.bmi_info_TV)
    TextView bmiInfoTV;

    @BindString(R.string.invalid_weight)
    String INVALID_WEIGHT;
    @BindString(R.string.invalid_height)
    String INVALID_HEIGHT;
    @BindString(R.string.my_bmi)
    String MY_BMI;
    @BindString(R.string.i_am)
    String I_AM;
    @BindString(R.string.sent_via_bmi_calc)
    String SENT_VIA_BMI_CALC;

    Menu menu = null;

    IBMICalc metricBMICalc = new MetricBMICalc();
    IBMICalc imperialBMICalc = new ImperialBMICalc();
    IBMICalc currentBMICalc;

    SharedPreferences preferences;
    SharedPreferences.Editor preferencesEditor;

    boolean isCorrectWeight;
    boolean isCorrectHeight;
    boolean isMetric;
    double bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        preferences = getPreferences(0);

        if (savedInstanceState != null) {
            isMetric = savedInstanceState.getBoolean("isMetric");
            setCalc(isMetric);
            isCorrectWeight = savedInstanceState.getBoolean("isCorrectWeight");
            isCorrectHeight = savedInstanceState.getBoolean("isCorrectHeight");
            bmi = savedInstanceState.getDouble("bmi");
            setTextViews(bmi);
        } else {
            isMetric = preferences.getBoolean("isMetric", true);
            setCalc(isMetric);
            weightET.setText(preferences.getString("weight", ""));
            heightET.setText(preferences.getString("height", ""));
            metricImperialS.setChecked(!isMetric);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        setSaveButtonStatus();
        setShareButtonStatus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveAll();
                Toast.makeText(getApplicationContext(), R.string.saved, Toast.LENGTH_SHORT).show();
                return true;

            case R.id.share:
                String textToShare = String.format(getResources().getConfiguration().locale,
                        "%s %.2f. %s %s. %s.",
                        MY_BMI,
                        bmi,
                        I_AM,
                        about(bmi).toLowerCase(),
                        SENT_VIA_BMI_CALC);

                Intent share = new Intent(Intent.ACTION_SEND).putExtra(Intent.EXTRA_TEXT, textToShare).setType("text/plain");
                startActivity(share);
                return true;

            case R.id.clear:
                clearAll();
                saveAll();
                Toast.makeText(getApplicationContext(), R.string.cleared, Toast.LENGTH_SHORT).show();
                return true;

            case R.id.about:
                Intent about = new Intent(getApplicationContext(), About.class);
                startActivity(about);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isCorrectWeight", isCorrectWeight);
        outState.putBoolean("isCorrectHeight", isCorrectHeight);
        outState.putBoolean("isMetric", isMetric);
        outState.putDouble("bmi", bmi);
    }

    @OnTextChanged(value = R.id.weight_ET, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    protected void weightWatcher() {
        validateWeight(weightET.getText().toString());
        setSaveButtonStatus();
    }

    @OnTextChanged(value = R.id.height_ET, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    protected void heightWatcher() {
        validateHeight(heightET.getText().toString());
        setSaveButtonStatus();
    }

    @OnTextChanged(value = R.id.bmi_value_TV, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    protected void BMIWatcher() {
        setShareButtonStatus();
    }

    @OnClick(R.id.metric_imperial_S)
    protected void switchClickListener() {
        setCalc(!metricImperialS.isChecked());
        validateWeight(weightET.getText().toString());
        validateHeight(heightET.getText().toString());
        setSaveButtonStatus();
    }

    @OnClick(R.id.fab)
    protected void calculateBMI() {
        String weightString = weightET.getText().toString();
        String heightString = heightET.getText().toString();
        bmi = 0;

        if (isCorrectHeight && isCorrectWeight) {
            double weight = Double.parseDouble(weightString);
            double height = Double.parseDouble(heightString);
            bmi = currentBMICalc.calcBMI(weight, height);
        }

        setTextViews(bmi);
    }

    protected void validateWeight(String weightString) {
        if (!weightString.equals("")) {
            double weight = Double.parseDouble(weightString);
            isCorrectWeight = currentBMICalc.isValidWeight(weight);

            if (isCorrectWeight) {
                weightET.setError(null);
            } else {
                weightET.setError(INVALID_WEIGHT);
            }
        } else {
            isCorrectWeight = false;
        }
    }

    protected void validateHeight(String heightString) {
        if (!heightString.equals("")) {
            double height = Double.parseDouble(heightString);
            isCorrectHeight = currentBMICalc.isValidHeight(height);

            if (isCorrectHeight) {
                heightET.setError(null);
            } else {
                heightET.setError(INVALID_HEIGHT);
            }
        } else {
            isCorrectHeight = false;
        }
    }

    protected void setSaveButtonStatus() {
        if (menu != null) {
            menu.findItem(R.id.save).setEnabled(isCorrectWeight && isCorrectHeight);
        }
    }

    protected void setShareButtonStatus() {
        if (menu != null) {
            menu.findItem(R.id.share).setEnabled(bmi != 0);
        }
    }

    protected void clearAll() {
        weightET.setText("");
        heightET.setText("");
        metricImperialS.setChecked(false);
        bmi = 0;
        setMetric();
        setTextViews(bmi);
    }

    protected void saveAll() {
        preferencesEditor = preferences.edit();
        preferencesEditor.putString("weight", weightET.getText().toString());
        preferencesEditor.putString("height", heightET.getText().toString());
        preferencesEditor.putBoolean("isMetric", isMetric);
        preferencesEditor.apply();
    }

    protected void setCalc(boolean isMetric) {
        if (isMetric) {
            setMetric();
        } else {
            setImperial();
        }
    }

    protected void setMetric() {
        isMetric = true;
        currentBMICalc = metricBMICalc;
        weightUnitTV.setText(R.string.metric_weight_unit);
        heightUnitTV.setText(R.string.metric_height_unit);
    }

    protected void setImperial() {
        isMetric = false;
        currentBMICalc = imperialBMICalc;
        weightUnitTV.setText(R.string.imperial_weight_unit);
        heightUnitTV.setText(R.string.imperial_height_unit);
    }

    protected void setTextViews(double bmi) {
        if (bmi == 0) {
            bmiMessageTV.setText("");
            bmiTV.setText("");
            bmiInfoTV.setText("");
        } else {
            bmiMessageTV.setText(R.string.bmi_message);
            bmiTV.setText(getFormattedBMI(bmi));
            bmiInfoTV.setText(about(bmi));
        }
    }

    protected String about(double bmi) {
        int id;

        if (bmi > 30) {
            id = R.string.obese;
        } else if (bmi > 25) {
            id = R.string.overweight;
        } else if (bmi > 18.5) {
            id = R.string.normal_range;
        } else {
            id = R.string.underweight;
        }

        return getString(id);
    }

    protected String getFormattedBMI(double bmi) {
        return String.format(getResources().getConfiguration().locale, "%.2f", bmi);
    }
}
