package com.study.maha360;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.android.gms.common.internal.Constants;

import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment implements OnUserEarnedRewardListener {
//    private static final long START_TIME_IN_MILLIS = 54000000; // Time for

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private TextView mTextViewCountDown;
    private long mStartTimeInMillis = 54000000 ;  // change here also

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        MobileAds.initialize(getContext());

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


            }
        });

        view.findViewById(R.id.watch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAd();

            }
        });

        return view;
    }

    private void loadAd() {
        RewardedInterstitialAd.load(getContext(), "ca-app-pub-3940256099942544/5354046379", new AdRequest.Builder().build(), new RewardedInterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedInterstitialAd rewardedInterstitialAd) {
                super.onAdLoaded(rewardedInterstitialAd);
                rewardedInterstitialAd.show(getActivity(), HomeFragment.this::onUserEarnedReward);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                loadAd();
                resetTimer(); // temporary added this
                startTimer();
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences prefs = this.getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
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

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 54000000);  // change time here also
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        mTextViewCountDown.setText(timeLeftFormatted);

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;

                 hours = (int) (mTimeLeftInMillis / 1000) / 3600;
                 minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
                 seconds = (int) (mTimeLeftInMillis / 1000) % 60;

                if (hours > 0) {
                    timeLeftFormatted = String.format(Locale.getDefault(),
                            "%d:%02d:%02d", hours, minutes, seconds);
                } else {
                    timeLeftFormatted = String.format(Locale.getDefault(),
                            "%02d:%02d", minutes, seconds);
                }
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

                int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
                int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
                int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
                String timeLeftFormatted;
                if (hours > 0) {
                    timeLeftFormatted = String.format(Locale.getDefault(),
                            "%d:%02d:%02d", hours, minutes, seconds);
                } else {
                    timeLeftFormatted = String.format(Locale.getDefault(),
                            "%02d:%02d", minutes, seconds);
                }
                mTextViewCountDown.setText(timeLeftFormatted);
            }

//            private void updateCountDownText() {
//                int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
//                int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
//                int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
//                String timeLeftFormatted;
//                if (hours > 0) {
//                    timeLeftFormatted = String.format(Locale.getDefault(),
//                            "%d:%02d:%02d", hours, minutes, seconds);
//                } else {
//                    timeLeftFormatted = String.format(Locale.getDefault(),
//                            "%02d:%02d", minutes, seconds);
//                }
//                mTextViewCountDown.setText(timeLeftFormatted);
//            }

            @Override
            public void onFinish() {
                mTimerRunning = false;

            }
        }.start();
        mTimerRunning = true;
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;

        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        mTextViewCountDown.setText(timeLeftFormatted);
    }



    @Override
    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
        resetTimer();
        startTimer();

        Toast.makeText(getContext(), "Unlimited Downloading Started...", Toast.LENGTH_SHORT).show();
    }
}