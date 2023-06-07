package project;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

import org.bson.Document;
import org.json.*;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;

@RestController
public class DatosController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getMeteogalicia", produces = "application/json")
	public String datosEstaticos() throws IOException {
		Datos info = new Datos(1, "prueba", "test");
		URL url = new URL("http://servizos.meteogalicia.gal/rss/observacion/jsonCamaras.action");
		Scanner scan = new Scanner(url.openStream());
		String str = new String();
		while (scan.hasNext())
			str += scan.nextLine();
		scan.close();
		JSONObject obj = new JSONObject(str);
		JSONArray arr = obj.getJSONArray("listaCamaras");
		String geoJSONFinal = "{\"type\":\"FeatureCollection\",\"features\":[\r\n";
		for (int i = 0; i < arr.length(); i++) {
			String post_id = arr.getJSONObject(i).toString();
			int idConcello = arr.getJSONObject(i).getInt("idConcello");
			int identificador = arr.getJSONObject(i).getInt("identificador");
			String concello = arr.getJSONObject(i).getString("concello");
			String dataUltimaAct = arr.getJSONObject(i).getString("dataUltimaAct");
			String imaxeCamara = arr.getJSONObject(i).getString("imaxeCamara");
			String nomeCamara = arr.getJSONObject(i).getString("nomeCamara");
			String provincia = arr.getJSONObject(i).getString("provincia");
			double lat = arr.getJSONObject(i).getDouble("lat");
			double lon = arr.getJSONObject(i).getDouble("lon");
			int nextInt = i + 1;
			if (nextInt < arr.length()) {
				geoJSONFinal = geoJSONFinal + "{\"type\":\"Feature\",\"properties\":{\"idConcello\": \" " + idConcello
						+ " \",\"identificador\":\" " + identificador + " \",\"concello\":\" " + concello
						+ " \",\"dataUltimaAct\":\" " + dataUltimaAct + " \",\"imaxeCamara\":\" " + imaxeCamara
						+ " \",\"nomeCamara\":\" " + nomeCamara + " \",\"provincia\":\" " + provincia
						+ " \"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[" + lon + "," + lat + "]}},\r\n";
			} else {
				geoJSONFinal = geoJSONFinal + "{\"type\":\"Feature\",\"properties\":{\"idConcello\": \" " + idConcello
						+ " \",\"identificador\":\" " + identificador + " \",\"concello\":\" " + concello
						+ " \",\"dataUltimaAct\":\" " + dataUltimaAct + " \",\"imaxeCamara\":\" " + imaxeCamara
						+ " \",\"nomeCamara\":\" " + nomeCamara + " \",\"provincia\":\" " + provincia
						+ " \"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[" + lon + "," + lat + "]}}\r\n";
			}
		}
		geoJSONFinal = geoJSONFinal + "	]}";
		return geoJSONFinal;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getDBData", produces = "application/json")
	public String infoBaseDatos(@RequestParam(value = "id", defaultValue = "0") String id) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("myNewDatabase");
		MongoCollection<Document> coll = database.getCollection("myCollection");
		FindIterable<Document> documents;
		if (id.equals("0")) {
			documents = coll.find();
		} else {
			int idNum = Integer.parseInt(id);
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("id", idNum);
			documents = coll.find(whereQuery);
		}
		MongoCursor<Document> cursor = documents.iterator();
		String valorFinal = "{ \"listaObjetos\": [ \r\n";
		while (cursor.hasNext()) {
			Document document = cursor.next();
			if (cursor.hasNext()) {
				valorFinal = valorFinal + document.toJson() + ", \r\n";
			} else {
				valorFinal = valorFinal + document.toJson() + "\r\n";
			}
		}
		valorFinal = valorFinal + " ]}";
		mongoClient.close();
		return valorFinal;
	}
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getGeoDB", produces = "application/json")
	public String infoBaseDatosGeo(@RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "num", defaultValue = "0") String num) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("myNewDatabase");
		MongoCollection<Document> coll = database.getCollection("myCollection");
		FindIterable<Document> documents;
		if (id.equals("0")) {
			documents = coll.find();
		} else {
			int idNum = Integer.parseInt(id);
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("id", idNum);
			documents = coll.find(whereQuery);
		}
		MongoCursor<Document> cursor = documents.iterator();
		int tamLista=0;
		while (cursor.hasNext()) {
			cursor.next();
			tamLista++;
		}
		cursor = documents.iterator();
		String str = "{ \"listaObjetos\": [ \r\n";
		if(num.equals("0")) {
			while (cursor.hasNext()) {
				Document document = cursor.next();
				if (cursor.hasNext()) {
					str+= document.toJson() + ", \r\n";
				} else {
					str+= document.toJson() + "\r\n";
				}	
			}	
		}else {
			int cont=1;
			int contAux=1;
			cont = (int) (tamLista- Integer.parseInt(num));
			int contAux2 = cont;
			while (cursor.hasNext()) {
				Document document = cursor.next();
				if(contAux<=contAux2) {
					contAux++;
				}else {
					if (cursor.hasNext() && cont!=tamLista) {
						str+= document.toJson() + ", \r\n";
						cont++;
					} else {
						str+= document.toJson() + "\r\n";
						cont++;
					}	
				}	
			}
			contAux=1;
		}

		str+= " ]}";
		JSONObject obj = new JSONObject(str);
		JSONArray arr = obj.getJSONArray("listaObjetos");
		String geoJSONFinal = "{\"type\":\"FeatureCollection\",\"features\":[\r\n";
		for (int i = 0; i < arr.length(); i++) {
			String post_id = arr.getJSONObject(i).toString();
			int identificador = arr.getJSONObject(i).getInt("id");
			String dataUltimaAct = arr.getJSONObject(i).getString("ultimaAct");
			double lat = arr.getJSONObject(i).getDouble("lat");
			double lon = arr.getJSONObject(i).getDouble("lon");
			int nextInt = i + 1;
			if (nextInt < arr.length()) {
				geoJSONFinal = geoJSONFinal + "{\"type\":\"Feature\",\"properties\":{\"identificador\":\" " + identificador + 
						" \",\"dataUltimaAct\":\" " + dataUltimaAct + 
						" \"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[" + lon + "," + lat + "]}},\r\n";
			} else {
				geoJSONFinal = geoJSONFinal + "{\"type\":\"Feature\",\"properties\":{\"identificador\":\" " + identificador+
						" \",\"dataUltimaAct\":\" " + dataUltimaAct+ " \"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[" + lon + "," + lat + "]}}\r\n";
			}
		}
		geoJSONFinal = geoJSONFinal + "	]}";
		mongoClient.close();
		return geoJSONFinal;
		
		
		
	}
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getGeoDBCoords", produces = "application/json")
	public String arrayCoordsBaseDatos(@RequestParam(value = "id", defaultValue = "0") String id) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("myNewDatabase");
		MongoCollection<Document> coll = database.getCollection("myCollection");
		FindIterable<Document> documents;
		if (id.equals("0")) {
			documents = coll.find();
		} else {
			int idNum = Integer.parseInt(id);
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("id", idNum);
			documents = coll.find(whereQuery);
		}
		MongoCursor<Document> cursor = documents.iterator();
		String str = "{ \"listaObjetos\": [ \r\n";
		while (cursor.hasNext()) {
			Document document = cursor.next();
			if (cursor.hasNext()) {
				str+= document.toJson() + ", \r\n";
			} else {
				str+= document.toJson() + "\r\n";
			}
			
		}
		str+= " ]}";
		JSONObject obj = new JSONObject(str);
		JSONArray arr = obj.getJSONArray("listaObjetos");
		String geoJSONFinal = "[\r\n";
		for (int i = 0; i < arr.length(); i++) {
			double lon = arr.getJSONObject(i).getDouble("lat");
			double lat = arr.getJSONObject(i).getDouble("lon");
			int nextInt = i + 1;
			if (nextInt < arr.length()) {
				geoJSONFinal = geoJSONFinal + "[" + lon + "," + lat + "],\r\n";
			} else {
				geoJSONFinal = geoJSONFinal + "[" + lon + "," + lat + "]\r\n";
			}
		}
		geoJSONFinal = geoJSONFinal + "]";
		mongoClient.close();
		return geoJSONFinal;
		
		
		
	}

}