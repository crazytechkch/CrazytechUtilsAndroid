package co.crazytech.crazytechutils.aviation.airports;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.crazytech.json.JSONParser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;


public class Airport {
	private Integer id;
	private String name;
	private String city;
	private String country;
	private String iata;
	private String icao;
	private Double lat;
	private Double lng;
	private Integer alt;
	private Integer timezone;
	private String dst;
	
	public static String URL = "http://phpmysql-crazytechco.rhcloud.com/aviation/get_airports.php";
	
	public Airport(String name,String value) {
		String json = null;
		try {
			json = getJsonString(name,value);
			JsonObject jsonObj = new Gson().fromJson(json, JsonObject.class);
			if (jsonObj.get("success").getAsInt()==1) {
				JsonArray jsonArr = jsonObj.get("results").getAsJsonArray();
				for (JsonElement jsonElement : jsonArr) {
					this.name = jsonElement.getAsJsonObject().get("name").getAsString();
					this.city = jsonElement.getAsJsonObject().get("city").getAsString();
					this.country = jsonElement.getAsJsonObject().get("country").getAsString();
					this.iata = jsonElement.getAsJsonObject().get("iata").getAsString();
					this.icao = jsonElement.getAsJsonObject().get("icao").getAsString();
					this.lat = jsonElement.getAsJsonObject().get("latitude").getAsDouble();
					this.lng = jsonElement.getAsJsonObject().get("longitude").getAsDouble();
					this.alt = jsonElement.getAsJsonObject().get("altitude").getAsInt();
					this.timezone = jsonElement.getAsJsonObject().get("timezone").getAsInt();
					this.dst = jsonElement.getAsJsonObject().get("dst").getAsString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public Airport(JsonObject jsonObj) throws SQLException {
		super();
		this.name = jsonObj.get("name").getAsString();
		this.city = jsonObj.get("city").getAsString();
		this.country = jsonObj.get("country").getAsString();
		this.iata = jsonObj.get("iata").getAsString();
		this.icao = jsonObj.get("icao").getAsString();
		this.lat = jsonObj.get("latitude").getAsDouble();
		this.lng = jsonObj.get("longitude").getAsDouble();
		this.alt = jsonObj.get("altitude").getAsInt();
		this.timezone = jsonObj.get("timezone").getAsInt();
		this.dst = jsonObj.get("dst").getAsString();
	}
	
	public static String getJsonString(String name, String value) throws JSONException, IOException,Exception{
		// url - http://phpmysql-crazytechco.rhcloud.com/aviation/get_airports.php?where=where%20city%20like%20'ka%'
		return new JSONParser().getJsonString(URL+"?where=where%20"+name+"%20like%20'%"+value+"%'",5000);
		
	}
	
	@Override
	public String toString() {
		return name+", "+city+", "+country+", "+iata+", "+icao;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public String getIcao() {
		return icao;
	}

	public void setIcao(String icao) {
		this.icao = icao;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Integer getAlt() {
		return alt;
	}

	public void setAlt(Integer alt) {
		this.alt = alt;
	}

	public Integer getTimezone() {
		return timezone;
	}

	public void setTimezone(Integer timezone) {
		this.timezone = timezone;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
