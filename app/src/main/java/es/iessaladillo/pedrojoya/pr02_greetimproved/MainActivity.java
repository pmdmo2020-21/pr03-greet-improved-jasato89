package es.iessaladillo.pedrojoya.pr02_greetimproved;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;
import es.iessaladillo.pedrojoya.pr02_greetimproved.utils.SoftInputUtils;

import androidx.appcompat.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr02_greetimproved.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;
    String treatment = "", name, surname;
    int count = 0;
    int charCount = 20;
    boolean polite = false;
    private TextWatcher nameWatcher;
    private TextWatcher surnameWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupViews();
        checkPremium();


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void setupViews() {

        binding.rdgTreatment.setOnCheckedChangeListener((radioGroup, i) -> checkTreatment());
        binding.greetBtn.setOnClickListener(l -> printResult());
        binding.checkGreetStyle.setOnClickListener(l -> checkStyle());
        binding.lblpremiumSwitcher.setOnClickListener(l -> showBar());
        binding.lblCountBar.setText(R.string.countBarText);
        binding.lblNameCharsLeft.setText(getResources().getQuantityString(R.plurals.CharsLeft, charCount, charCount));
        binding.lblSurnameCharsLeft.setText(getResources().getQuantityString(R.plurals.CharsLeft, charCount, charCount));


    }

    private void checkTreatment() {
        if (binding.rdMr.isChecked()) {
            binding.imgTreatment.setImageResource(R.drawable.ic_mr);
            treatment = binding.rdMr.getText().toString();
        } else if (binding.rdMrs.isChecked()) {
            binding.imgTreatment.setImageResource(R.drawable.ic_mrs);
            treatment = binding.rdMrs.getText().toString();
        } else {
            binding.imgTreatment.setImageResource(R.drawable.ic_ms);
            treatment = binding.rdMs.getText().toString();

        }

    }

    private void printResult() {
        name = binding.inputName.getText().toString();
        surname = binding.inputSurname.getText().toString();
        SoftInputUtils.hideSoftKeyboard(binding.inputSurname);
        if (count > 10) {
            Toast.makeText(this, getString(R.string.buyPremium), Toast.LENGTH_SHORT).show();
        }else if(!name.isEmpty() && !surname.isEmpty()) {
            checkPremium();
            checkTreatment();
            checkStyle();
            if (polite) {
                Toast.makeText(this, getString(R.string.greetFormal, treatment, name, surname), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, getString(R.string.greetInformal, name, surname), Toast.LENGTH_SHORT).show();

            }
        }
    }



    private void checkStyle() {

        if (binding.checkGreetStyle.isChecked()) {
            polite = true;
        } else {
            polite = false;
        }
    }

    private void showBar() {
        count = 0;
        binding.progresBar.setProgress(count);
        if (binding.lblpremiumSwitcher.isChecked()) {
            binding.progresBar.setVisibility(View.GONE);
            binding.lblCountBar.setVisibility(View.GONE);

        } else {
            checkPremium();
            binding.progresBar.setVisibility(View.VISIBLE);
            binding.lblCountBar.setVisibility(View.VISIBLE);

        }
    }

    private void checkPremium() {
        if(binding.lblpremiumSwitcher.isChecked()) {
            binding.progresBar.setProgress(count);
            binding.lblCountBar.setText(getResources().getString(R.string.countBarText, count));

        } else {

            binding.progresBar.setProgress(count);
            binding.lblCountBar.setText(getResources().getString(R.string.countBarText, count));

            count++;
        }
    }


}