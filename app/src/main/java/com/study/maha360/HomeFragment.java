package com.study.maha360;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {
    private static final long START_TIME_IN_MILLIS = 600000;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private TextView mTextViewCountDown;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        view.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, " " + getString(R.string.app_name));
                    String msg = "स्टॉक मार्केट, ट्रेडिंग अॅप हे मराठी लोकांमध्ये शेअर मार्केट, ट्रेडिंग आणि क्रिप्टोकरन्सी ज्ञान प्रदान करण्यासाठी आहे, जिथे तुम्ही स्टॉक मार्केट,ट्रेडिंग मराठी भाषेत शिकू शकता. हे अॅप तुम्हाला शेअर बाजारातील मूलभूत गोष्टी, प्रकारांसह कॅण्डल्स आणि मराठीत क्रिप्टोकरन्सी बद्दल माहिती पुरवते. \n" +
                            "\n" +
                            "शेअर मार्केट, ट्रेडिंग आणि क्रिप्टो करेंसी बद्दल माहिती मिळवण्यासाठी लगेच डाउनलोड करा \n https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n"; // Change your message
                    intent.putExtra(Intent.EXTRA_TEXT, msg);
                    startActivity(Intent.createChooser(intent, "Share App with your friends"));
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
        view.findViewById(R.id.telegram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    new AlertDialog.Builder(getContext()) //alert the person knowing they are about to close
                            .setTitle("Join Telegram Channel")
                            .setMessage("Which Channel You Want to join")
                            .setPositiveButton("Diploma", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/joinchat/V3H9kU_aQwoOs-96")));
                                }
                            })
                            .setNegativeButton("Degree", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/joinchat/V3H9kU_aQwoOs-96")));
                                }
                            })
                            //.setNegativeButton("No", null)
                            .show();
                    // Add privacy policy url
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.diploma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startTimer();

            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences prefs = this.getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        editor.apply();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = this.getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                 minutes = (int) (mTimeLeftInMillis / 1000) / 60;
                 seconds = (int) (mTimeLeftInMillis / 1000) % 60;
              timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                mTextViewCountDown.setText(timeLeftFormatted);

            } else {
                startTimer();
            }
        }
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
                int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                mTextViewCountDown.setText(timeLeftFormatted);
            }

            private void updateCountDownText() {
                int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
                int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                mTextViewCountDown.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;

            }
        }.start();
        mTimerRunning = true;
    }



}