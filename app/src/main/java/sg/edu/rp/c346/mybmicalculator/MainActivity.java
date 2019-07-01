package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight, etHeight;
    Button btnCal, btnReset;
    TextView tvDate, tvBMI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCal = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewLastDate);
        tvBMI = findViewById(R.id.textViewLastBMI);
        
        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Step 1a: Get the user input from the EditText and store it in a variable
                float height = Float.parseFloat(etHeight.getText().toString());
                float weight = Float.parseFloat(etWeight.getText().toString());
                float bmi = weight/(height*height);
    
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                
                tvDate.setText(getString(R.string.lastdate)+ datetime);
                tvBMI.setText(getString(R.string.lastbmi) + String.valueOf(bmi));
                
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                
                SharedPreferences.Editor prefEdit = prefs.edit();
                
                prefEdit.putFloat("bmi", bmi);
                prefEdit.putString("date", datetime);
    
                prefEdit.commit();
                
            }
        });
        
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText("");
                etHeight.setText("");
                tvDate.setText(getString(R.string.lastdate));
                tvBMI.setText(getString(R.string.lastbmi));
                //Step 1b: Obtain an instance of the SharedPreferences
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                //Step 1c: Obtain an instance of the SharedPreferences Editor for update later
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putFloat("bmi", 0);
                prefEdit.putString("date", "");
                prefEdit.commit();
            }
        });
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        //Step 2b: Retrieve the saved data with the key "greeting" from the SharedPreferences
        String date = prefs.getString("date","");
        float BMI = prefs.getFloat("bmi",0);
        //Step 2c: Update the UI element with the value
        tvDate.setText(getString(R.string.lastdate)+ date);
        tvBMI.setText(getString(R.string.lastbmi) + String.valueOf(BMI));
    }
}
