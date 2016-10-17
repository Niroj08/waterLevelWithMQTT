package com.technotroop.mqttdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity implements MqttCallback {


    //variables fo mqtt client
    private String clientId;
    private MqttAndroidClient client;
    private IMqttToken token, subToken;

    private String TAG = "CONNECTION STATUS";

    private TextView textConnectionStatus, textMessage;
    private Button btnPublish, btnSubscribe, btnConnect;
    private EditText editHost, editPort, editTopic, editMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textConnectionStatus = (TextView) findViewById(R.id.textConnectionStatus);
        textMessage = (TextView) findViewById(R.id.textMessage);

        btnPublish = (Button) findViewById(R.id.btnPublish);
        btnSubscribe = (Button) findViewById(R.id.btnSubscribe);
        btnConnect = (Button) findViewById(R.id.btnConnect);

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

                mqttConnection(host, port);
            }
        });

        //publish message
        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTopic.setHint("Enter Topic to Publish");
                String topic = editTopic.getText().toString();
                String message = editMessage.getText().toString();

                publishMessage(topic, message);
            }
        });

        //subscribe mqtt topic
        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTopic.setHint("Enter Topic to Subscribe");
                String topic = editTopic.getText().toString();

                subscribeMessage(topic);
            }
        });
    }

    private void subscribeMessage(String topic) {
        if (TextUtils.isEmpty(topic)) {

            editTopic.setError("Topic Required");
            return;
        } else {
            int qos = 1;
            try {
                subToken = client.subscribe(topic, qos);
                subToken.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Toast.makeText(getApplicationContext(), "Topic was subscribed successfully!", Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken,
                                          Throwable exception) {
                        Toast.makeText(getApplicationContext(), "Topic Subscription failed", Toast.LENGTH_SHORT)
                                .show();

                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    private void publishMessage(String topic, String payload) {
        if (TextUtils.isEmpty(topic)) {

            editTopic.setError("Topic Required");
            return;
        } else if (TextUtils.isEmpty(payload)) {

            editMessage.setError("Message Required");
            return;
        } else {
            byte[] encodedPayload = new byte[0];
            if (client.isConnected()) {
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topic, message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "No Mqtt Connection", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void mqttConnection(String host, String port) {
        if (TextUtils.isEmpty(host)) {

            editHost.setError("Required");
            return;
        } else if (TextUtils.isEmpty(port)) {

            editPort.setError("Required");
            return;
        } else {
            clientId = MqttClient.generateClientId();

            String hostAndPort = "tcp://" + host + ":" + port;
            client = new MqttAndroidClient(this.getApplicationContext(),
                    hostAndPort,
                    clientId);

            try {
                token = client.connect();
                client.setCallback(this);
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        textConnectionStatus.setText("CONNECTION SUCCESS");
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        exception.printStackTrace();
                        textConnectionStatus.setText("CONNECTION FAILED");

                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
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
