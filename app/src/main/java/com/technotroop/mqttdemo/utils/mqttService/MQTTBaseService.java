package com.technotroop.mqttdemo.utils.mqttService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.technotroop.mqttdemo.R;
import com.technotroop.mqttdemo.utils.Constants;

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

/**
 * Created by technotroop on 10/20/16.
 */

public class MQTTBaseService extends Service implements MqttCallback {
    private String clientId;
    private MqttAndroidClient client;
    private IMqttToken token;
    private IMqttToken subToken;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String host = Constants.MQTT_HOST;
        String port = Constants.MQTT_PORT;

        mqttConnection(host, port);
        return Service.START_NOT_STICKY;
    }


    @Override
    public void connectionLost(Throwable cause) {
        Log.v(Constants.MQTT_CONNECTION, getString(R.string.connectionLost));

        //TODO: ask user to reconnect to the broker

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

        //TODO: message arrived from the subscribed topic
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public void mqttConnection(String host, String port) {
        if (TextUtils.isEmpty(host) || TextUtils.isEmpty(port)) {

            Log.v(Constants.MQTT_CONNECTION, getString(R.string.invalidHostPort));
            return;
        } else {
            clientId = MqttClient.generateClientId();

            String hostAndPort = "tcp://" + host + ":" + port;
            client = new MqttAndroidClient(this.getApplicationContext(),
                    hostAndPort,
                    clientId);

            MqttConnectOptions options = new MqttConnectOptions();
            //options.setCleanSession(true);
            options.setUserName("aj_mjn");
            options.setPassword("8abfc8bfc06d469f8f391ff15bd0ff79".toCharArray());

            try {
                token = client.connect(options);
                client.setCallback(this);
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.v(Constants.MQTT_CONNECTION, getString(R.string.connectionSuccessful));
                        //TODO: Subscribe to all topics after the connection is established

                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        exception.printStackTrace();
                        Log.v(Constants.MQTT_CONNECTION, getString(R.string.connectionFailed));

                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void publishMessage(String topic, String message) {

        if (TextUtils.isEmpty(topic)) {

            //TODO: could also be required to notify the user
            Log.v(Constants.MQTT_CONNECTION, getString(R.string.emptyTopic));
            return;
        } else if (TextUtils.isEmpty(message)) {

            //TODO: could also be required to notify the user
            Log.v(Constants.MQTT_CONNECTION, getString(R.string.emptyMessage));
            return;
        } else {

            byte[] encodedPayload = new byte[0];
            if (client.isConnected()) {
                try {
                    encodedPayload = message.getBytes("UTF-8");
                    MqttMessage messageToPublish = new MqttMessage(encodedPayload);
                    client.publish(topic, messageToPublish);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            } else {
                Log.v(Constants.MQTT_CONNECTION, getString(R.string.somethingWentWrongDuringPublish));
            }
        }
    }


    public void subscribeMessage(final String topic) {
        if (TextUtils.isEmpty(topic)) {

            //TODO: could also be required to notify the user
            Log.v(Constants.MQTT_CONNECTION, getString(R.string.emptyTopic));
            return;
        } else {
            int qos = 1;
            try {
                if (client.isConnected()) {
                    subToken = client.subscribe(topic, qos);
                    subToken.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            Log.v(Constants.MQTT_CONNECTION, getString(R.string.subscribeSuccess) + topic);
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken,
                                              Throwable exception) {
                            Log.v(Constants.MQTT_CONNECTION, getString(R.string.subscribeFailed) + topic);

                        }
                    });
                }
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }


    public void disconnectMQTTConnection() {
        if (client.isConnected()) {
            try {
                IMqttToken disconToken = client.disconnect();
                disconToken.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.v(Constants.MQTT_CONNECTION, getString(R.string.connectionDisconnected));
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken,
                                          Throwable exception) {
                        exception.printStackTrace();
                        Log.v(Constants.MQTT_CONNECTION, getString(R.string.connectionDisconnectError));
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

}
