package com.technotroop.mqttdemo.utils.mqttService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.technotroop.mqttdemo.MQTTApplicaiton;
import com.technotroop.mqttdemo.R;
import com.technotroop.mqttdemo.utils.Constants;
import com.technotroop.mqttdemo.utils.MQTTUtils;
import com.technotroop.mqttdemo.utils.enums.WaterLevelTopics;
import com.technotroop.mqttdemo.view.activity.WaterLevelActivity;
import com.technotroop.mqttdemo.view.interfaces.MQTTConnectionInterface;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import java.io.UnsupportedEncodingException;

/**
 * Created by technotroop on 10/20/16.
 */

public class MQTTBaseService extends Service implements MqttCallback {
    private String clientId;
    private static MqttAndroidClient client;
    private IMqttToken token;
    private IMqttToken subToken;
    private IMqttToken pubToken;

    private MQTTConnectionInterface mqttConnectionInterface;

    public MQTTBaseService(MQTTConnectionInterface connectionInterface) {

        String host = Constants.MQTT_HOST;
        String port = Constants.MQTT_PORT;

        mqttConnection(host, port);

        mqttConnectionInterface = connectionInterface;
    }

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
        Log.v(Constants.MQTT_CONNECTION, MQTTUtils.getContext().getString(R.string.connectionLost));

        //TODO: ask user to reconnect to the broker
        if (mqttConnectionInterface != null) {
            mqttConnectionInterface.onMQTTConnectionError(cause.getMessage());
        }

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.v(Constants.MQTT_CONNECTION, topic + message);
        String convertedMessage = String.valueOf(message);

        if (mqttConnectionInterface != null) {
            mqttConnectionInterface.onMessageReceived(topic, convertedMessage);
        }
        //WaterLevelActivity.progressWaterLevel.setProgress(waterLevel);

        //TODO: message arrived from the subscribed topic
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

        if (token == pubToken) {
            //TODO: show publish error for respective topics
        }
    }

    public void mqttConnection(String host, String port) {
        if (MQTTUtils.isInternetConnected()) {
            if (TextUtils.isEmpty(host) || TextUtils.isEmpty(port)) {

                Log.v(Constants.MQTT_CONNECTION, MQTTUtils.getContext().getString(R.string.invalidHostPort));
                return;
            } else {
                clientId = MqttClient.generateClientId();

                String hostAndPort = "tcp://" + host + ":" + port;
                client = new MqttAndroidClient(MQTTUtils.getContext(),
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
                            Log.v(Constants.MQTT_CONNECTION, MQTTUtils.getContext().getString(R.string.connectionSuccessful));

                            if (mqttConnectionInterface != null) {
                                mqttConnectionInterface.onMQTTConnectionSuccess();
                            }

                            //subscribe test topic color
                            subscribeMessage("aj_mjn/feeds/color");
                            //subscribe test topic seek
                            subscribeMessage("aj_mjn/feeds/seek");
                            //subscribe to test topic onoff
                            subscribeMessage("aj_mjn/feeds/onoff");
                            //subscribe to test topic water level
                            subscribeMessage("aj_mjn/feeds/water_level");
                            //TODO: Subscribe to all topics after the connection is established
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            exception.printStackTrace();

                            if (mqttConnectionInterface != null) {
                                mqttConnectionInterface.onMQTTConnectionError(exception.getMessage());
                            }

                            Log.v(Constants.MQTT_CONNECTION, MQTTUtils.getContext().getString(R.string.connectionFailed));

                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.v(Constants.MQTT, MQTTUtils.getContext().getString(R.string.noInternet));
            //TODO: info user for no internet connectivity
            if (mqttConnectionInterface != null) {
                mqttConnectionInterface.onErrorNoConnection();
            }
        }
    }

    public void publishMessage(String topic, String message) {

        if (TextUtils.isEmpty(topic)) {

            Log.v(Constants.MQTT_CONNECTION, MQTTUtils.getContext().getString(R.string.emptyTopic));

            //TODO: could also be required to notify the user
            if (mqttConnectionInterface != null) {
                mqttConnectionInterface.onMQTTPublishNoTopicError();
            }
            return;
        } else if (TextUtils.isEmpty(message)) {

            Log.v(Constants.MQTT_CONNECTION, MQTTUtils.getContext().getString(R.string.emptyMessage));

            //TODO: could also be required to notify the user
            if (mqttConnectionInterface != null) {
                mqttConnectionInterface.onMQTTPublishNoMessageError();
            }
            return;
        } else {

            byte[] encodedPayload = new byte[0];
            if (client.isConnected()) {
                try {
                    int qos = 1;
                    encodedPayload = message.getBytes("UTF-8");
                    //MqttMessage messageToPublish = new MqttMessage(encodedPayload);

                    pubToken = client.publish(topic, encodedPayload, qos, true);
                    //pubToken.waitForCompletion(100);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            } else {
                Log.v(Constants.MQTT_CONNECTION, MQTTUtils.getContext().getString(R.string.somethingWentWrongDuringPublish));

                //TODO: inform user connection have been terminated
                //connectionLost(null);
            }
        }
    }


    public void subscribeMessage(final String topic) {
        if (TextUtils.isEmpty(topic)) {

            Log.v(Constants.MQTT_CONNECTION, MQTTUtils.getContext().getString(R.string.emptyTopic));

            //TODO: could also be required to notify the user
            if (mqttConnectionInterface != null) {
                mqttConnectionInterface.onMQTTSubscribeNoTopicError();
            }
            return;
        } else {
            int qos = 1;
            try {
                if (client.isConnected()) {
                    subToken = client.subscribe(topic, qos);
                    subToken.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            Log.v(Constants.MQTT_CONNECTION, MQTTUtils.getContext().getString(R.string.subscribeSuccess) + topic);

                            if (mqttConnectionInterface != null) {
                                mqttConnectionInterface.onMQTTSubscriptionSuccess();
                            }
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken,
                                              Throwable exception) {
                            Log.v(Constants.MQTT_CONNECTION, MQTTUtils.getContext().getString(R.string.subscribeFailed) + topic);

                            if (mqttConnectionInterface != null) {
                                mqttConnectionInterface.onMQTTSubscriptionError(exception.getMessage());
                            }

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
                        Log.v(Constants.MQTT_CONNECTION, MQTTUtils.getContext().getString(R.string.connectionDisconnected));

                        if (mqttConnectionInterface != null) {
                            mqttConnectionInterface.onMQTTDisconnectSuccess();
                        }
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken,
                                          Throwable exception) {
                        exception.printStackTrace();
                        Log.v(Constants.MQTT_CONNECTION, MQTTUtils.getContext().getString(R.string.connectionDisconnectError));

                        if (mqttConnectionInterface != null) {
                            mqttConnectionInterface.onMQTTDisconnectError(exception.getMessage());
                        }
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

}
