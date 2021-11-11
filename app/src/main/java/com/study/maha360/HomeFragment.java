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

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.startapp.sdk.adsbase.adlisteners.VideoListener;
import com.vungle.warren.InitCallback;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.error.VungleException;

import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static com.unity3d.services.core.properties.ClientProperties.getApplicationContext;


public class HomeFragment extends Fragment  {
//    private static final long START_TIME_IN_MILLIS = 43200000; // Time for

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private TextView mTextViewCountDown;
    private String eurl;
    private int checkad;
    private long mStartTimeInMillis = 3600000;  // change here also
    ReviewManager manager;
    ReviewInfo reviewInfo;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        StartAppAd.disableSplash();

        IronSource.setMetaData("Facebook_IS_CacheFlag", "IMAGE");
        IronSource.init(getActivity(), "111f3449d", IronSource.AD_UNIT.REWARDED_VIDEO);


        if (checkad == 10) {
            mTimerRunning = true;
            checkad = 10;
        } else {
            mTimerRunning = false;
            checkad = 20;
        }


        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        view.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, " " + getString(R.string.app_name));
                    String msg = "Hey, I found a nice app that have all the Question papers of MSBTE and SPPU. This is the best study resource app for MSBTE students, It have answers of manuals, question papers, model answer papers, syllabus, notes and many more download now \n https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n"; // Change your message
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
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/MSBTE_Guide")));
                                }
                            })
                            .setNegativeButton("Degree", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/SPPU_Group")));
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

        view.findViewById(R.id.watch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Please wait a movment...", Toast.LENGTH_LONG).show();
                loadAd();


            }
        });

        if (checkad == 10) {
            mTimerRunning = true;
            checkad = 10;
        } else {
            mTimerRunning = false;
            checkad = 20;
        }

        view.findViewById(R.id.diploma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                eurl = "https://drive.google.com/drive/folders/1MD4fBpaBoememAplzAsGztQID2Tu2XtK?usp=sharing";
                intent.putExtra("url", eurl);
                intent.putExtra("ad", checkad);
                startActivity(intent);


            }
        });

        view.findViewById(R.id.degree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                eurl = "https://drive.google.com/drive/folders/1isnCY4o2N_UFQIl6Hc90z2ENrt1WoBmt?usp=sharing";
                intent.putExtra("url", eurl);
                intent.putExtra("ad", checkad);
                startActivity(intent);

            }
        });

        return view;
    }

    private void loadAd() {



        IronSource.showRewardedVideo("DefaultRewardedVideo");
        IronSource.setRewardedVideoListener(new RewardedVideoListener() {

            @Override
            public void onRewardedVideoAdOpened() {
            }

            @Override
            public void onRewardedVideoAdClosed() {
            }

            @Override
            public void onRewardedVideoAvailabilityChanged(boolean available) {
                //Change the in-app 'Traffic Driver' state according to availability.
            }

            @Override
            public void onRewardedVideoAdRewarded(Placement placement) {
                // gift of users
                resetTimer();
                startTimer();

                Toast.makeText(getContext(), "Unlimited Downloading Started...", Toast.LENGTH_LONG).show();
                mTimerRunning = true;
                if (mTimerRunning) {
                    mTimerRunning = true;
                    checkad = 10;
                } else {
                    mTimerRunning = false;
                    checkad = 20;
                }

            }

            @Override
            public void onRewardedVideoAdShowFailed(IronSourceError error) {

                resetTimer(); // temporary added this
                startTimer();
                Toast.makeText(getActivity(), "No Ads Found, Unlimited Downloading Started...", Toast.LENGTH_SHORT).show();
                // sdk
                Vungle.init(getString(R.string.vengal_appid), getContext(), new InitCallback() {  // change app id
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(VungleException exception) {

                    }

                    @Override
                    public void onAutoCacheAdAvailable(String placementId) {

                    }
                });
                Vungle.loadAd(getString(R.string.vangel_video), new LoadAdCallback() {
                    @Override
                    public void onAdLoad(String placementId) {
                        if ( Vungle.canPlayAd(getString(R.string.vangel_video))){
                            Vungle.playAd(getString(R.string.vangel_video),null,null);
                        }
                    }

                    @Override
                    public void onError(String placementId, VungleException exception) {
                        StartAppAd.showAd(getApplicationContext());

                    }
                });
                // review
                manager = ReviewManagerFactory.create(getActivity());
                Task<ReviewInfo> request = manager.requestReviewFlow();
                request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
                    @Override
                    public void onComplete(@NonNull Task<ReviewInfo> task) {

                        if (task.isSuccessful()){
                            reviewInfo = task.getResult();
                            Task<Void> flow = manager.launchReviewFlow(getActivity(),reviewInfo);

                            flow.addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void result) {

                                }
                            });
                        }else {
                            Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                // review end

            }
            /*Invoked when the end user clicked on the RewardedVideo ad
             */
            @Override
            public void onRewardedVideoAdClicked(Placement placement){
            }

            @Override
            public void onRewardedVideoAdStarted(){
                resetTimer(); // temporary added this
                startTimer();
            }
            /* Invoked when the video ad finishes plating. */
            @Override
            public void onRewardedVideoAdEnded(){

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

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 3600000);  // change time here also
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false); //

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
                checkad = 20;

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
                mTimerRunning = true;
                checkad = 10;

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
                checkad = 20;

            }
        }.start();
        mTimerRunning = true;
        checkad = 10;
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



    public void onResume() {
        super.onResume();
        IronSource.onResume(getActivity());

    }
    public void onPause() {
        super.onPause();
        IronSource.onPause(getActivity());

    }
}