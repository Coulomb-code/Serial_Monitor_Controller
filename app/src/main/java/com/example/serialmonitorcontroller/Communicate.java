package com.example.serialmonitorcontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

class Communicate extends MainActivity implements Runnable {
    private static final String TAG = "Communicate";
    BluetoothAdapter btAdapter;
    BluetoothSocket btSocket;
    final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    Communicate(BluetoothSocket btSocket, BluetoothAdapter btAdapter){
        this.btSocket = btSocket;
        this.btAdapter = btAdapter;
    }

    @Override
    public void run() {
        /// write communication streams here
        Log.d(TAG,"Begin Talking via input/output Streams");
        InputStream inputStream = null;
        // loop communication to keep it going
        while(true) {
            try {
                OutputStream outputStream = btSocket.getOutputStream();
                outputStream.write(48);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                byte[] buffer = new byte[1024];
                int bytes;
                final StringBuilder sb = new StringBuilder();
                inputStream = btSocket.getInputStream();

                while(true) {
                    //bytes = inputStream.read(buffer);
                    byte b =(byte) inputStream.read();
                    char c = (char)b;
                    if(c == '\n'){ break; }
                    sb.append(c);
                }
                System.out.println(sb);
                serialMonitorMessege.setText(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        }

    }

}

// 10/15/2020 : I fixed the \n character read such that it keeps reading the incoming data until
// \n appears. then it shows the fully concatenated data.
// I also added the way to show the incoming data onto the Textview on the android screen

// What i need to do now is add a way to close the socket when the user is done.
// i also need to optimize the code
// make the UI look better