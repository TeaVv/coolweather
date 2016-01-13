package com.coolweather.db;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.model.City;
import com.coolweather.model.County;
import com.coolweather.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {
	/**
	 * 数据库名
	 */
	public static final String DB_NAME = "cool_weather";
	
	/**
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	/**
	 * 将构造方法私有化
	 */
	private CoolWeatherDB(Context context) {
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/**
	 * 获取CoolWeatherDB的实例
	 */
	public synchronized static CoolWeatherDB getInstance(Context context) {
		if (coolWeatherDB == null) {
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	/**
	 * 将Province实例存储到数据库
	 */
	public void saveProvince(Province province) {
		if (province != null) {
			/*ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getPrivinceCode());
			db.insert("Province", null, values);*/
			db.execSQL("insert into Province (province_name,province_code) values (?,?)", 
					new String[] {province.getProvinceName(), province.getProvinceCode()});
		}
	}
	
	/**
	 * 从数据库读取全国所有的省份信息
	 */
	public List<Province> loadProvinces() {
		List<Province> list = new ArrayList<Province>();
		/*Cursor cursor = db
				.query("Province", null, null, null, null, null, null);*/
		Cursor cursor = db.rawQuery("select * from Province", null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
	
	/**
	 * 将City实例存储到数据库
	 */
	public void saveCity(City city) {
		if (city != null) {
			/*ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceID());
			db.insert("City", null, values);*/
			db.execSQL("insert into City (city_name,city_code,province_id) values (?,?,?)", 
					new String[] {city.getCityName(), city.getCityCode(), String.valueOf(city.getProvinceID())});
		}
	}
	
	/**
	 * 从数据库读取某省下所有的城市信息
	 */
	public List<City> loadCities(int provinceID) {
		List<City> list = new ArrayList<City>();
		/*Cursor cursor = db
				.query("City", null, "province_id = ?", new String[] {String.valueOf(provinceID)}, null, null, null);*/
		Cursor cursor = db.rawQuery("select * from City where province_id=?", new String[] {String.valueOf(provinceID)});
		if (cursor.moveToFirst()) {
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceID(provinceID);
				list.add(city);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
	
	/**
	 * 将County实例存储到数据库
	 */
	public void saveCounty(County county) {
		if (county != null) {
			/*ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityID());
			db.insert("County", null, values);*/
			db.execSQL("insert into County (county_name,county_code,city_id) values (?,?,?)", 
					new String[] {county.getCountyName(), county.getCountyCode(), String.valueOf(county.getCityID())});
		}
	}
	
	/**
	 * 从数据库读取某城市下所有的县信息
	 */
	public List<County> loadCounties(int cityID) {
		List<County> list = new ArrayList<County>();
		/*Cursor cursor = db.query("County", null, "city_id = ?", 
				new String[] {String.valueOf(cityID) }, null, null, null);*/
		Cursor cursor = db.rawQuery("select * from County where city_id=?", new String[] {String.valueOf(cityID)});
		if (cursor.moveToFirst()) {
			do {
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityID(cityID);
				list.add(county);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
}
