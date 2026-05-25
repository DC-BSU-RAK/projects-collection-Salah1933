package com.example.journeyplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class RitualsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rituals, container, false);

        SharedPreferences prefs = requireActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);

        int[] checkboxIds = {
            R.id.cbRitual1, R.id.cbRitual2, R.id.cbRitual3,
            R.id.cbRitual4, R.id.cbRitual5, R.id.cbRitual6
        };
        String[] keys = {
            "ritual_water", "ritual_breathe", "ritual_move",
            "ritual_journal", "ritual_gratitude", "ritual_reflect"
        };

        for (int i = 0; i < checkboxIds.length; i++) {
            CheckBox cb = view.findViewById(checkboxIds[i]);
            final String key = keys[i];
            cb.setChecked(prefs.getBoolean(key, false));
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                prefs.edit().putBoolean(key, isChecked).apply();
                updateProgress(view, prefs, checkboxIds, keys);
            });
        }

        updateProgress(view, prefs, checkboxIds, keys);
        return view;
    }

    private void updateProgress(View view, SharedPreferences prefs, int[] ids, String[] keys) {
        int done = 0;
        for (String key : keys) {
            if (prefs.getBoolean(key, false)) done++;
        }
        TextView tvProgress = view.findViewById(R.id.tvProgress);
        String[] stars = {"🌑", "🌒", "🌓", "🌔", "🌕", "🌕✨", "🌟"};
        tvProgress.setText(stars[done] + " " + done + " / " + keys.length + " rituals complete");
    }
}
