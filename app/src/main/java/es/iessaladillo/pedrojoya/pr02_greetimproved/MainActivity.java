package es.iessaladillo.pedrojoya.pr02_greetimproved;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import es.iessaladillo.pedrojoya.pr02_greetimproved.utils.SoftInputUtils;

import androidx.appcompat.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr02_greetimproved.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;
    String treatment = "", name, surname;
    int maxLength = 20;
    int count = 0;
    int nameCharsLeft, surnameCharsLeft;
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



    private void setupViews() {
        defaultValues();
        binding.rdgTreatment.setOnCheckedChangeListener((radioGroup, i) -> checkTreatment());
        binding.greetBtn.setOnClickListener(l -> printResult());
        binding.checkGreetStyle.setOnClickListener(l -> checkStyle());
        binding.lblpremiumSwitcher.setOnClickListener(l -> showBar());
        binding.inputName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        binding.inputSurname.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});





    }

    private void defaultValues() {

        nameCharsLeft = 20;
        surnameCharsLeft = 20;
        binding.lblNameCharsLeft.setText(getResources().getQuantityString(R.plurals.CharsLeft, nameCharsLeft, nameCharsLeft));
        binding.lblSurnameCharsLeft.setText(getResources().getQuantityString(R.plurals.CharsLeft, surnameCharsLeft, surnameCharsLeft));
        binding.lblCountBar.setText(R.string.countBarText);


        nameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int ch = nameCharsLeft - s.length();
                binding.lblNameCharsLeft.setText(getResources().getQuantityString(R.plurals.CharsLeft, ch, ch));


            }

            @Override
            public void afterTextChanged(Editable s) {

                checkValue(binding.inputName);

            }
        };

        surnameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int ch = surnameCharsLeft - s.length();
                binding.lblSurnameCharsLeft.setText(getResources().getQuantityString(R.plurals.CharsLeft, ch, ch));


            }

            @Override
            public void afterTextChanged(Editable s) {
                checkValue(binding.inputSurname);

            }
        };
    }

    @Override
    protected void onResume() {

        super.onResume();

        binding.inputName.addTextChangedListener(nameWatcher);
        binding.inputSurname.addTextChangedListener(surnameWatcher);

        binding.inputName.setOnFocusChangeListener((v, hasFocus) ->
                changeTextViewColor(hasFocus, binding.inputName, binding.lblNameCharsLeft));

        binding.inputSurname.setOnFocusChangeListener((v, hasFocus) ->
                changeTextViewColor(hasFocus, binding.inputSurname, binding.lblSurnameCharsLeft));


    }

    @Override
    protected void onPause() {

        super.onPause();
        binding.inputName.removeTextChangedListener(nameWatcher);
        binding.inputSurname.removeTextChangedListener(surnameWatcher);

        binding.inputName.setOnFocusChangeListener(null);
        binding.inputSurname.setOnFocusChangeListener(null);
    }

    private void changeTextViewColor(boolean hasFocus, EditText inputName, TextView textView) {

        if(hasFocus) {
            textView.setTextColor(getResources().getColor(R.color.colorAccent));

        } else {
            textView.setTextColor(getResources().getColor(R.color.textPrimary));
        }

    }

    private boolean checkValue(EditText inputName) {

        if (!inputName.getText().toString().equalsIgnoreCase("")) {
            inputName.setError(null);
            return true;
        } else {
            inputName.setError(getString(R.string.required));
            return false;
        }
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