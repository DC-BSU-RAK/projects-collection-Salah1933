package com.example.journeyplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class PreferencesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preferences, container, false);

        SharedPreferences prefs = requireActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);

        EditText etName = view.findViewById(R.id.etName);
        RadioGroup rgTheme = view.findViewById(R.id.rgTheme);
        Switch swReminders = view.findViewById(R.id.swReminders);
        Button btnSave = view.findViewById(R.id.btnSave);

        // Load existing prefs
        etName.setText(prefs.getString("user_name", ""));
        swReminders.setChecked(prefs.getBoolean("reminders_on", true));

        String savedTheme = prefs.getString("ritual_theme", "Forest");
        switch (savedTheme) {
            case "Ocean":   ((RadioButton) view.findViewById(R.id.rbOcean)).setChecked(true); break;
            case "Mountain": ((RadioButton) view.findViewById(R.id.rbMountain)).setChecked(true); break;
            case "Desert":  ((RadioButton) view.findViewById(R.id.rbDesert)).setChecked(true); break;
            case "City":    ((RadioButton) view.findViewById(R.id.rbCity)).setChecked(true); break;
            default:        ((RadioButton) view.findViewById(R.id.rbForest)).setChecked(true); break;
        }

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) name = "Traveller";

            int selectedId = rgTheme.getCheckedRadioButtonId();
            String theme = "Forest";
            if (selectedId == R.id.rbOcean) theme = "Ocean";
            else if (selectedId == R.id.rbMountain) theme = "Mountain";
            else if (selectedId == R.id.rbDesert) theme = "Desert";
            else if (selectedId == R.id.rbCity) theme = "City";

            prefs.edit()
                .putString("user_name", name)
                .putString("ritual_theme", theme)
                .putBoolean("reminders_on", swReminders.isChecked())
                .apply();

            Toast.makeText(requireContext(), "✅ Preferences saved!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
