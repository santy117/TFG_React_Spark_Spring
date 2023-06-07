package prueba.SparkStreaming;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;
import kafka.serializer.DefaultDecoder;
import kafka.serializer.StringDecoder;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.bson.Document;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SparkStreamingJSONConsumer {
    private static Injection<GenericRecord, byte[]> recordInjection;

    private static final String USER_SCHEMA = "{"
            + "\"type\":\"record\","
            + "\"name\":\"myrecord\","
            + "\"fields\":["
            + "  { \"name\":\"id\", \"type\":\"int\" },"
            + "  { \"name\":\"lat\", \"type\":\"double\" },"
            + "  { \"name\":\"lon\", \"type\":\"double\" },"
            + "  { \"name\":\"ultimaAct\", \"type\":\"string\" }"
            + "]}";

    static {
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(USER_SCHEMA);
        recordInjection = GenericAvroCodecs.toBinary(schema);
    }

    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName("kafka-sandbox")
                .setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(10000));

        Set<String> topics = Collections.singleton("topic1");
        Map<String, String> kafkaParams = new HashMap<>();
        kafkaParams.put("metadata.broker.list", "localhost:9092");

        JavaPairInputDStream<String, byte[]> directKafkaStream = KafkaUtils.createDirectStream(ssc,
                String.class, byte[].class, StringDecoder.class, DefaultDecoder.class, kafkaParams, topics);
 
        directKafkaStream
                .map(message -> recordInjection.invert(message._2).get())
                .foreachRDD(rdd -> {
                    rdd.foreach(record -> {
                        System.out.println(record.get("id")
                                + ", " + record.get("lat")
                                + ", " + record.get("lon")
                                + ", " + record.get("ultimaAct"));
                        System.out.println(record.toString());
                        addToMongo(record.toString());

                       
                       
                    });
                });
      
        ssc.start();
        ssc.awaitTermination();
    }
    public static void addToMongo(String record) {
    	 MongoClient mongoClient = new MongoClient();
   	    MongoDatabase database = mongoClient.getDatabase("myNewDatabase");
   	    MongoCollection<Document> coll = database.getCollection("myCollection");
    	DBObject objeto = (DBObject)BasicDBObject.parse(record);
        @SuppressWarnings("unchecked")
		Document docObjeto= new Document(objeto.toMap());
        coll.insertOne(docObjeto);
        System.out.println("Se ha creado una nueva entrada en la bbdd");
        mongoClient.close();
    	
    }

}