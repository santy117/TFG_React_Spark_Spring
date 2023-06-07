package prueba.SparkStreaming;

import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;
import org.apache.avro.Schema;
import org.apache.avro.generic.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

public class ProductorJSON {

    public static final String USER_SCHEMA = "{"
            + "\"type\":\"record\","
            + "\"name\":\"myrecord\","
            + "\"fields\":["
            + "  { \"name\":\"id\", \"type\":\"int\" },"
            + "  { \"name\":\"lat\", \"type\":\"double\" },"
            + "  { \"name\":\"lon\", \"type\":\"double\" },"
            + "  { \"name\":\"ultimaAct\", \"type\":\"string\" }"
            + "]}";

    public static void main(String[] args) throws InterruptedException, ParseException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        String valorId= args[0];
        int valorIdFinal= Integer.parseInt(valorId);
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(USER_SCHEMA);
        Injection<GenericRecord, byte[]> recordInjection = GenericAvroCodecs.toBinary(schema);

        KafkaProducer<String, byte[]> producer = new KafkaProducer<>(props);
        for (int i = 1; i < 51; i++) {
            GenericData.Record avroRecord = new GenericData.Record(schema);
            avroRecord.put("id", valorIdFinal);
            DecimalFormat formatter = new DecimalFormat("#0.0000");
            NumberFormat nf = NumberFormat.getInstance();
            Double longitud = -8+(Double.valueOf(i)/10);
            Double latitud = 41+(Double.valueOf(i)/10);
            avroRecord.put("lat", nf.parse(formatter.format(latitud)).doubleValue());
            avroRecord.put("lon", nf.parse(formatter.format(longitud)).doubleValue());
            Date fechaAct = new Date();
            avroRecord.put("ultimaAct", fechaAct.toString());

            byte[] bytes = recordInjection.apply(avroRecord);

            ProducerRecord<String, byte[]> record = new ProducerRecord<>("topic1", bytes);
            producer.send(record);

            Thread.sleep(10000);
        }

        producer.close();
    }
}