//opg5pgm

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTprogram {
    public static MemoryPersistence persistence = new MemoryPersistence();
    public static String broker = "tcp://192.168.0.115:1883";

    public static MqttClient sampleClient;

    static {
        try {
            sampleClient = new MqttClient(broker,  MqttClient.generateClientId(), persistence);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public static MqttConnectOptions connOpts = new MqttConnectOptions();
    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub
        try {
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            String topic1 = "tele/grp7764/SENSOR";
            String topic2 = "tele/grp7764/STATE";

            sampleClient.setCallback(new SimpleCallBack());

            sampleClient.connect(connOpts);
            System.out.println("Connected");

            sampleClient.subscribe(topic1);
            sampleClient.subscribe(topic2);

            Thread.sleep(200000);

//            sampleClient.disconnect();
//            System.out.println("Disconnected");
//            System.exit(0);
            publishMessage(sampleClient, topic1, "0");

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();

        }

    }
    public static void  publishMessage(MqttClient sampleClient,String topicsend,String content) throws MqttPersistenceException, MqttException {
        // Laver en publish p  sampleClient med topic topicsend og indhold content.
        MqttMessage message = new MqttMessage();
        message.setPayload(content.getBytes());
        System.out.println(content.getBytes());
        sampleClient.publish(topicsend, message);
        System.out.println("Message published");

    }


}
