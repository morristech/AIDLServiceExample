package com.academy.android.aidlserviceexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;


public class DarthVaderActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.darth_vader_activity);
        findViewById(R.id.iv_dva_build_death_star).setOnClickListener(this);
        findViewById(R.id.iv_dva_interact_with_luke).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, EmpireService.class);
        Messenger messenger = new Messenger(new DarthVaderHandler(this));
        intent.putExtra("EmperialMessenger", messenger);
        switch (v.getId()) {
            case R.id.iv_dva_build_death_star:
                intent.putExtra("Command type",
                        EmpireService.EmpireServiceCommands.BUILD_DEATH_STAR);
                break;
            case R.id.iv_dva_interact_with_luke:
                intent.putExtra("Command type",
                        EmpireService.EmpireServiceCommands.FIND_LUKE);
                break;
        }
        startService(intent);
    }

    private static class DarthVaderHandler extends Handler {

        private final WeakReference<DarthVaderActivity> clientRef;

        public DarthVaderHandler(DarthVaderActivity client) {
            this.clientRef = new WeakReference<>(client);
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            DarthVaderActivity client = clientRef.get();
            if (client != null && msg.what == EmpireService.CALLBACK_MSG && data != null) {
                Toast.makeText(client, "Death Star deployed and ready for your command, my lord",
                        Toast.LENGTH_LONG).show();
            }
        }


    }
}
