package com.tryjitsi;


import android.content.Intent;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;

import java.net.MalformedURLException;
import java.net.URL;

public class CustomModule extends ReactContextBaseJavaModule {
    private static final String TAG = "Custom module";
    private static ReactApplicationContext reactApplicationContext;

    public CustomModule(@Nullable ReactApplicationContext reactContext) {
        super(reactContext);
        reactApplicationContext = reactContext;
    }

    @ReactMethod
    public void joinJitsiMeet(ReadableMap options) {
        JitsiMeetUserInfo jitsiMeetUserInfo = new JitsiMeetUserInfo();

        Boolean chatEnabled = true;
        Boolean addPeopleEnabled = true;
        Boolean inviteEnabled = true;
        Boolean meetingNameEnabled = true;
        Boolean conferenceTimerEnabled = true;
        Boolean raiseHandEnabled = false;
        Boolean recordingEnabled = false;
        Boolean liveStreamEnabled = false;
        Boolean toolBoxEnabled = true;
        Boolean toolBoxAlwaysVisible = true;
        Boolean meetingPasswordEnabled = true;
        Boolean pipModeEnabled = true;
        String roomId = String.valueOf(Math.random());
        URL serverUrl = null;
        try {
            serverUrl = new URL("https://meet.jit.si");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        if (options != null) {
            if (options.hasKey("serverUrl") && options.getString("serverUrl") != null) {
                try {
                    serverUrl = new URL(options.getString("serverUrl"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            if (options.hasKey("roomId") && options.getString("roomId") != null) {
                roomId = options.getString("roomId");
            }

            if (options.hasKey("chatEnabled")) {
                if (options.getBoolean("chatEnabled") == true || (options.getBoolean("chatEnabled") == false)) {
                    chatEnabled = options.getBoolean("chatEnabled");
                }
            }

            if (options.hasKey("addPeopleEnabled")) {
                if (options.getBoolean("addPeopleEnabled") == true || (options.getBoolean("addPeopleEnabled") == false)) {
                    addPeopleEnabled = options.getBoolean("addPeopleEnabled");
                }
            }

            if (options.hasKey("inviteEnabled")) {
                if (options.getBoolean("inviteEnabled") == true || (options.getBoolean("inviteEnabled") == false)) {
                    inviteEnabled = options.getBoolean("inviteEnabled");
                }
            }

            if (options.hasKey("meetingNameEnabled")) {
                if (options.getBoolean("meetingNameEnabled") == true || (options.getBoolean("meetingNameEnabled") == false)) {
                    meetingNameEnabled = options.getBoolean("meetingNameEnabled");
                }
            }

            if (options.hasKey("conferenceTimerEnabled")) {
                if (options.getBoolean("conferenceTimerEnabled") == true || (options.getBoolean("conferenceTimerEnabled") == false)) {
                    conferenceTimerEnabled = options.getBoolean("conferenceTimerEnabled");
                }
            }

            if (options.hasKey("raiseHandEnabled")) {
                if (options.getBoolean("raiseHandEnabled") == true || (options.getBoolean("raiseHandEnabled") == false)) {
                    raiseHandEnabled = options.getBoolean("raiseHandEnabled");
                }
            }

            if (options.hasKey("recordingEnabled")) {
                if (options.getBoolean("recordingEnabled") == true || (options.getBoolean("recordingEnabled") == false)) {
                    recordingEnabled = options.getBoolean("recordingEnabled");
                }
            }

            if (options.hasKey("liveStreamEnabled")) {
                if (options.getBoolean("liveStreamEnabled") == true || (options.getBoolean("liveStreamEnabled") == false)) {
                    liveStreamEnabled = options.getBoolean("liveStreamEnabled");
                }
            }

            if (options.hasKey("toolBoxEnabled")) {
                if (options.getBoolean("toolBoxEnabled") == true || (options.getBoolean("toolBoxEnabled") == false)) {
                    toolBoxEnabled = options.getBoolean("toolBoxEnabled");
                }
            }

            if (options.hasKey("toolBoxAlwaysVisible")) {
                if (options.getBoolean("toolBoxAlwaysVisible") == true || (options.getBoolean("toolBoxAlwaysVisible") == false)) {
                    toolBoxAlwaysVisible = options.getBoolean("toolBoxAlwaysVisible");
                }
            }

            if (options.hasKey("meetingPasswordEnabled")) {
                if (options.getBoolean("meetingPasswordEnabled") == true || (options.getBoolean("meetingPasswordEnabled") == false)) {
                    meetingPasswordEnabled = options.getBoolean("meetingPasswordEnabled");
                }
            }

            if (options.hasKey("pipModeEnabled")) {
                if (options.getBoolean("pipModeEnabled") == true || (options.getBoolean("pipModeEnabled") == false)) {
                    pipModeEnabled = options.getBoolean("pipModeEnabled");
                }
            }

            // check for user info
            if (options.hasKey("userInfo")) {
                ReadableMap user = options.getMap("userInfo");
                if (user.hasKey("displayName")) {
                    jitsiMeetUserInfo.setDisplayName(user.getString("displayName"));
                }
                if (user.hasKey("email")) {
                    jitsiMeetUserInfo.setEmail(user.getString("email"));
                }
                if (user.hasKey("avatar")) {
                    String avatarURL = user.getString("avatar");
                    try {
                        jitsiMeetUserInfo.setAvatar(new URL(avatarURL));
                    } catch (MalformedURLException e) {
                    }
                }
            }
        }


        // build with options
        JitsiMeetConferenceOptions jitsiOptions
                = null;
        jitsiOptions = new JitsiMeetConferenceOptions.Builder()
                .setServerURL(serverUrl)
                .setRoom(roomId)
                .setUserInfo(jitsiMeetUserInfo)
                .setFeatureFlag("chat.enabled", chatEnabled)
                .setFeatureFlag("add-people.enabled", addPeopleEnabled)
                .setFeatureFlag("invite.enabled", inviteEnabled)
                .setFeatureFlag("meeting-name.enabled", meetingNameEnabled)
                .setFeatureFlag("conference-timer.enabled", conferenceTimerEnabled)
                .setFeatureFlag("pip.enabled", pipModeEnabled)
                .setFeatureFlag("help.enabled", false)
                .setFeatureFlag("raise-hand.enabled", raiseHandEnabled)
                .setFeatureFlag("recording.enabled", recordingEnabled)
                .setFeatureFlag("live-streaming.enabled", liveStreamEnabled)
                .setFeatureFlag("toolbox.enabled", toolBoxEnabled)
                .setFeatureFlag("toolbox.alwaysVisible", toolBoxAlwaysVisible)
                .setFeatureFlag("meeting-password.enabled", meetingPasswordEnabled)
                .build();
        // Launch the new activity with the given options. The launch() method takes care
        // of creating the required Intent and passing the options.
        JitsiMeetActivity.launch(getReactApplicationContext(), jitsiOptions);

        //        Toast.makeText(reactApplicationContext, "Hello!", Toast.LENGTH_SHORT).show();
    }

    @ReactMethod
    public void getDeviceID(Promise promise) {
        try {
            String android_id = Settings.Secure.getString(reactApplicationContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            promise.resolve(android_id);
        } catch (Exception e) {
            promise.reject("error", e);
        }
    }

    @ReactMethod
    public void moveToActivity() {
//        Intent newIntent = new Intent(getCurrentActivity(), CustomActivity.class);
//        getCurrentActivity().startActivity(newIntent);
    }

    @NonNull
    @Override
    public String getName() {
        return "customScreen";
    }
}
