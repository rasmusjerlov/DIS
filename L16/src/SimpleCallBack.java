import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
public class SimpleCallBack implements MqttCallback {
        int status = 0;
        String power;

    public void connectionLost(Throwable throwable) {
            System.out.println("Connection to MQTT broker lost!");
        }

        @Override
        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
            double temp;
            double hum;
            String res= new String(mqttMessage.getPayload());
            if (res.contains("POWER")) {
                JSONObject jo = new JSONObject(res);
                power = jo.getString("POWER");
            }
            if (res.contains("AM2301")) {
                JSONObject jo = new JSONObject(res);
                JSONObject jo2 = jo.getJSONObject("AM2301");
//            System.out.println(jo2.toString());
                temp = jo2.getDouble("Temperature");
                hum = jo2.getDouble("Humidity");
                System.out.println("Tempereature is: " + temp + " C\n" + "Humidity is: " + hum);
//            if (hum > )
            }


//            if (temp > 10) {
//                MQTTprogram.publishMessage(MQTTprogram.sampleClient, "cmnd/grp7764/Power1", "1");
//            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                // not used in this example
        }
    }
