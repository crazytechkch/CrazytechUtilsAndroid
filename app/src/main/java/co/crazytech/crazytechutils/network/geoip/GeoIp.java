package co.crazytech.crazytechutils.network.geoip;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class GeoIp {
	private String ip, countryCode,country,regionCode,region,
		city,zipcode,timezone,metroCode,text;
	private Double latitude,longitude;
	
	public static String URL = "http://freegeoip.net/json/";
	
	public GeoIp() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GeoIp(JsonObject json){
		super();
		this.ip = json.get("ip").getAsString();
		this.countryCode = json.get("country_code").getAsString();
		this.country = json.get("country_name").getAsString();
		this.regionCode = json.get("region_code").getAsString();
		this.region = json.get("region_name").getAsString();
		this.city = json.get("city").getAsString();
		this.zipcode = json.get("zip_code").getAsString();
		this.timezone = json.get("time_zone").getAsString();
		this.metroCode = json.get("metro_code").getAsString();
		this.latitude = json.get("latitude").getAsDouble();
		this.longitude = json.get("longitude").getAsDouble();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return text;
	}

	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getMetroCode() {
		return metroCode;
	}
	public void setMetroCode(String metroCode) {
		this.metroCode = metroCode;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
