package com.technotroop.mqttdemo.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.technotroop.mqttdemo.R;
import com.technotroop.mqttdemo.utils.mqttService.MQTTBaseService;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements MqttCallback {


    //variables fo mqtt client
    private String clientId;
    private MqttAndroidClient client;
    private IMqttToken token, subToken;

    private String TAG = "CONNECTION STATUS";

    private TextView textConnectionStatus, textMessage;
    private Button btnPublish, btnSubscribe, btnConnect, btnDisconnect;
    private EditText editHost, editPort, editTopic, editMessage;

    private MQTTBaseService mqttBaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mqttBaseService = new MQTTBaseService();

        textConnectionStatus = (TextView) findViewById(R.id.textConnectionStatus);
        textMessage = (TextView) findViewById(R.id.textMessage);

        btnPublish = (Button) findViewById(R.id.btnPublish);
        btnSubscribe = (Button) findViewById(R.id.btnSubscribe);
        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnDisconnect = (Button) findViewById(R.id.btnDisconnect);

        editHost = (EditText) findViewById(R.id.editHost);
        editPort = (EditText) findViewById(R.id.editPort);
        editTopic = (EditText) findViewById(R.id.editTopic);
        editMessage = (EditText) findViewById(R.id.editMessage);

        //establish connection to MQTT
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String host = editHost.getText().toString();
                String port = editPort.getText().toString();

                mqttBaseService.mqttConnection(host, port);
            }
        });

        //publish message
        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTopic.setHint("Enter Topic to Publish");
                String topic = editTopic.getText().toString();
                String message = editMessage.getText().toString();

                mqttBaseService.publishMessage(topic, message);
            }
        });

        //subscribe mqtt topic
        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTopic.setHint("Enter Topic to Subscribe");
                String topic = editTopic.getText().toString();

                mqttBaseService.subscribeMessage(topic);
            }
        });

        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttBaseService.disconnectMQTTConnection();
            }
        });
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(message);
        textMessage.setText(topic + " = " + message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
