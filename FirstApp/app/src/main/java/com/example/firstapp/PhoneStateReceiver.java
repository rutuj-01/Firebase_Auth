
package com.example.firstapp;


import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;

//import static android.content.ContentValues.TAG;


public class PhoneStateReceiver extends BroadcastReceiver {

    TelephonyManager telephonyManager;
    AudioManager audioManager;
    //ITelephony telephonyService;

    @Override
    public void onReceive(Context context, Intent intent) {
        ITelephony telephonyService;
        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            System.out.println("Receiver start");
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                String message = "Hey! I am driving right now. Please call me back after some time";
                Toast.makeText(context, "Ringing State Number is -" + incomingNumber, Toast.LENGTH_LONG).show();
               SmsManager smsmanage = SmsManager.getDefault();
                smsmanage.sendTextMessage(incomingNumber, null, message, null, null);
                Toast.makeText(context, "SMS send to : " + incomingNumber, Toast.LENGTH_LONG).show();
                if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }

                // Wait for 1 second
                // Direct changing won't set the Do Not Disturb on
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//                Toast.makeText(context, "as"+am , Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Silent Mode Enable", Toast.LENGTH_LONG).show();

                try {

                Class c = Class.forName(telephonyManager.getClass().getName());
                Method m = c.getDeclaredMethod("getITelephony");
                m.setAccessible(true);
                telephonyService = (ITelephony) m.invoke(telephonyManager);
                telephonyService.endCall();
                Toast.makeText(context, "Ending the call from: " + incomingNumber, Toast.LENGTH_LONG).show();

                }
            catch (Exception e) {
                //rejectCall();
                e.printStackTrace();
                Toast.makeText(context, "Not ended: " + incomingNumber, Toast.LENGTH_LONG).show();
            }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


}



