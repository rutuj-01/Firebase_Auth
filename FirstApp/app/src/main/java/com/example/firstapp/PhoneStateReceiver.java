
package com.example.theappguruz.phonestatereceiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import android.telephony.SmsManager;
import java.lang.String;
import java.lang.reflect.Method;
import com.android.internal.telephony.ITelephony;
import android.telecom.TelecomManager;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//import static android.content.ContentValues.TAG;


public class PhoneStateReceiver extends BroadcastReceiver {
    TelephonyManager telephonyManager;
    AudioManager am;
    //ITelephony telephonyService;

    @Override
    public void onReceive(Context context, Intent intent) {
        ITelephony telephonyService;
        am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

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
                am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
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



