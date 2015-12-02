package co.crazytech.crazytechutils.aviation.airports;

import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
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
	
	//public static String URL = "http://phpmysql-crazytechco.rhcloud.com/aviation/get_airports.php";

	public Airport(JsonObject jsonObj){
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

	public static String getUrl(){
		return ("http://phpmysql-crazytechco.rhcloud.com/aviation/get_airports.php"
				/*+ "?where=where "
				+ "name like '%"+keyword+"%' "
				+ "or city like '%"+keyword+"%' "
				+ "or country like '%"+keyword+"%' "
				+ "or iata like '%"+keyword+"%' "
				+ "or icao like '%"+keyword+"%'"*/).replaceAll(" ","%20");
	}

	public static String getWhere(String keyword) throws UnsupportedEncodingException {
		String where = "where=where "
				+ "name like '%"+keyword+"%' "
				+ "or city like '%"+keyword+"%' "
				+ "or country like '%"+keyword+"%' "
				+ "or iata like '%"+keyword+"%' "
				+ "or icao like '%"+keyword+"%'";
		return where.replaceAll("%","%25").replaceAll(" ","%20");
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
