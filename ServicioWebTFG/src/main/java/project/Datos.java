package project;

public class Datos {

	 private final long id;
	  private final String content;
	 private final String type;

	  public Datos(long id, String content, String type) {
	    this.id = id;
	    this.content = content;
	    this.type=type;
	  }

	  public long getId() {
	    return id;
	  }

	  public String getContent() {
	    return content;
	  }
	  
	  public String getType() {
		  return type;
	  }
	  
	  public String getJSON() {
		  return "{\"type\":\"FeatureCollection\",\"features\":[\r\n" + 
		  		"	{\"type\":\"Feature\",\"properties\":{\"idConcello\": \"xxx\",\"identificador\":\"xxx\",\"concello\":\"xxx\",\"dataUltimaAct\":\"xxx\",\"imaxeCamara\":\"xxx\",\"nomeCamara\":\"xxx\",\"provincia\":\"xxx\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-5.9765625,42.293564192170095]}},\r\n" + 
		  		"	{\"type\":\"Feature\",\"properties\":{\"idConcello\": \"xxx\",\"identificador\":\"xxx\",\"concello\":\"xxx\",\"dataUltimaAct\":\"xxx\",\"imaxeCamara\":\"xxx\",\"nomeCamara\":\"xxx\",\"provincia\":\"xxx\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-6.712646484375,42.50450285299051]}},\r\n" + 
		  		"	{\"type\":\"Feature\",\"properties\":{\"idConcello\": \"xxx\",\"identificador\":\"xxx\",\"concello\":\"xxx\",\"dataUltimaAct\":\"xxx\",\"imaxeCamara\":\"xxx\",\"nomeCamara\":\"xxx\",\"provincia\":\"xxx\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-7.780566406250001,42.342305278572816]}},\r\n" + 
		  		"	{\"type\":\"Feature\",\"properties\":{\"idConcello\": \"xxx\",\"identificador\":\"xxx\",\"concello\":\"xxx\",\"dataUltimaAct\":\"xxx\",\"imaxeCamara\":\"xxx\",\"nomeCamara\":\"xxx\",\"provincia\":\"xxx\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-7.880566406250001,42.342305278572816]}},\r\n" + 
		  		"	{\"type\":\"Feature\",\"properties\":{\"idConcello\": \"xxx\",\"identificador\":\"xxx\",\"concello\":\"xxx\",\"dataUltimaAct\":\"xxx\",\"imaxeCamara\":\"xxx\",\"nomeCamara\":\"xxx\",\"provincia\":\"xxx\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-7.980566406250001,42.342305278572816]}},\r\n" + 
		  		"	{\"type\":\"Feature\",\"properties\":{\"idConcello\": \"xxx\",\"identificador\":\"xxx\",\"concello\":\"xxx\",\"dataUltimaAct\":\"xxx\",\"imaxeCamara\":\"xxx\",\"nomeCamara\":\"xxx\",\"provincia\":\"xxx\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-7.280566406250001,42.342305278572816]}},\r\n" + 
		  		"	{\"type\":\"Feature\",\"properties\":{\"idConcello\": \"xxx\",\"identificador\":\"xxx\",\"concello\":\"xxx\",\"dataUltimaAct\":\"xxx\",\"imaxeCamara\":\"xxx\",\"nomeCamara\":\"xxx\",\"provincia\":\"xxx\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-7.180566406250001,42.342305278572816]}},\r\n" + 
		  		"	{\"type\":\"Feature\",\"properties\":{\"idConcello\": \"xxx\",\"identificador\":\"xxx\",\"concello\":\"xxx\",\"dataUltimaAct\":\"xxx\",\"imaxeCamara\":\"xxx\",\"nomeCamara\":\"xxx\",\"provincia\":\"xxx\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-7.480566406250001,42.342305278572816]}},\r\n" + 
		  		"	{\"type\":\"Feature\",\"properties\":{\"idConcello\": \"xxx\",\"identificador\":\"xxx\",\"concello\":\"xxx\",\"dataUltimaAct\":\"xxx\",\"imaxeCamara\":\"xxx\",\"nomeCamara\":\"xxx\",\"provincia\":\"xxx\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-8.80566406250001,42.342305278572816]}},\r\n" + 
		  		"	{\"type\":\"Feature\",\"properties\":{\"idConcello\": \"xxx\",\"identificador\":\"xxx\",\"concello\":\"xxx\",\"dataUltimaAct\":\"xxx\",\"imaxeCamara\":\"xxx\",\"nomeCamara\":\"xxx\",\"provincia\":\"xxx\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-8.580566406250001,42.342305278572816]}}\r\n" + 
		  		"	]}";
		  		
	  }
	
}
