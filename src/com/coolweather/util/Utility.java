package com.coolweather.util;

import android.text.TextUtils;
import com.coolweather.db.CoolWeatherDB;
import com.coolweather.model.City;
import com.coolweather.model.County;
import com.coolweather.model.Province;

public class Utility {

	/**
	 * �����ʹ������������ص�ʡ������
	 */
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response) {
		if (!TextUtils.isEmpty(response)) {
			String[] allProvinces = response.split(",");
			if (allProvinces != null && allProvinces.length > 0) {
				for (String p : allProvinces) {
					String array[] = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					//���������������ݴ洢��Province��
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * �����ʹ������������ص��м�����
	 */
	public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, String response, int provinceID) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCities = response.split(",");
			if (allCities != null && allCities.length > 0) {
				for (String c : allCities) {
					String[] array = c.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceID(provinceID);
					//���������������ݴ洢��City��
					coolWeatherDB.saveCity(city);
				}
				return true;
			}	
		}
		return false;
	}
	
	/**
	 * �����ʹ������������ص��ؼ�����
	 */
	public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, String response, int cityID) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCounties = response.split(",");
			if (allCounties != null && allCounties.length > 0) {
				for (String c : allCounties) {
					String[] array = c.split("\\|");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityID(cityID);
					//���������������ݴ洢��City��
					coolWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
}