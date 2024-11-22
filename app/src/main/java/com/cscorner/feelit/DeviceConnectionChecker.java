package com.cscorner.feelit;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Set;
public class DeviceConnectionChecker {

    private Context mContext;
    private BluetoothAdapter mBluetoothAdapter;
    private AudioManager mAudioManager;
    private boolean mBluetoothConnected = false;
    private boolean mEarphonesConnected = false;

    public DeviceConnectionChecker(Context context) {
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        registerReceiver();
        updateConnections();
    }

    private void updateConnections() {
        mBluetoothConnected = mAudioManager.isBluetoothA2dpOn() || mAudioManager.isBluetoothScoOn();
        mEarphonesConnected = mAudioManager.isWiredHeadsetOn();
    }

    public boolean isBluetoothConnected() {
        return mBluetoothConnected;
    }

    public boolean areEarphonesConnected() {
        return mEarphonesConnected;
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(Intent.ACTION_HEADSET_PLUG);

        mContext.registerReceiver(receiver, filter);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED) ||
                        action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED) ||
                        action.equals(Intent.ACTION_HEADSET_PLUG)) {
                    updateConnections();

                }
            }
        }
    };

    public void unregisterReceiver() {
        mContext.unregisterReceiver(receiver);}
}
