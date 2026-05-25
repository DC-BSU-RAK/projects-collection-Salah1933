package com.example.journeyplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences prefs = requireActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        String name = prefs.getString("user_name", "Traveller");
        String theme = prefs.getString("ritual_theme", "Forest");
        boolean reminders = prefs.getBoolean("reminders_on", true);

        TextView tvGreeting = view.findViewById(R.id.tvGreeting);
        TextView tvSubtitle = view.findViewById(R.id.tvSubtitle);
        TextView tvQuote = view.findViewById(R.id.tvQuote);
        TextView tvThemeDisplay = view.findViewById(R.id.tvThemeDisplay);
        TextView tvReminder = view.findViewById(R.id.tvReminder);

        // Time-based greeting
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String timeGreeting;
        String timeEmoji;
        if (hour < 12) {
            timeGreeting = "Good Morning";
            timeEmoji = "🌅";
        } else if (hour < 17) {
            timeGreeting = "Good Afternoon";
            timeEmoji = "☀️";
        } else {
            timeGreeting = "Good Evening";
            timeEmoji = "🌙";
        }

        tvGreeting.setText(timeEmoji + " " + timeGreeting + ", " + name + "!");
        tvSubtitle.setText("Your " + theme + " journey continues...");

        // Theme-specific quotes
        String quote;
        String themeIcon;
        switch (theme) {
            case "Ocean":
                quote = "\"The sea does not reward those who are too anxious, too greedy, or too impatient.\"";
                themeIcon = "🌊";
                break;
            case "Mountain":
                quote = "\"The mountains are calling and I must go.\"";
                themeIcon = "⛰️";
                break;
            case "Desert":
                quote = "\"In the desert, the only paradise is a shade.\"";
                themeIcon = "🏜️";
                break;
            case "City":
                quote = "\"The city is not a jungle — it's a garden of human stories.\"";
                themeIcon = "🏙️";
                break;
            default: // Forest
                quote = "\"The clearest way into the Universe is through a forest wilderness.\"";
                themeIcon = "🌲";
                break;
        }

        tvQuote.setText(quote);
        tvThemeDisplay.setText(themeIcon + " Theme: " + theme);
        tvReminder.setText(reminders ? "🔔 Daily reminders: ON" : "🔕 Daily reminders: OFF");

        return view;
    }
}
