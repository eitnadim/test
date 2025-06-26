/**
 * 
 */
package com.eit.gateway.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eit.gateway.dataservice.ApplicationSettingService;
import com.eit.gateway.dataservice.CompanySettingsService;
import com.eit.gateway.dataservice.CustomService;
import com.eit.gateway.entity.ApplicationSetting;
import com.eit.gateway.entity.CompanySettings;

import jakarta.annotation.PostConstruct;

/**
 * 
 */
@Component
public class CacheManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheManager.class);

	private static Map<String, String> ápplicationSettings = new HashMap<String, String>();
	private static Map<String, String> companySettings = new HashMap<String, String>();
	private static Set<String> imeiNumbers = new HashSet<String>();
	
	private static final String UNDER_SCORE = "_";
	private static final String ERROR = "Error";

	@Autowired
	private CustomService customService;
	
	@Autowired
	private ApplicationSettingService appSettingService;
	
	@Autowired
	private CompanySettingsService compSettingsService;
	
	/**
	 * 
	 */
	public CacheManager() {
		// TODO Auto-generated constructor stub
	}

	public void loadActiveIMEINumbers() {

		LOGGER.info("Loading IMEI numbers....");
		
		CacheManager.imeiNumbers.addAll(customService.getAllActiveIMEINumbers());
		
		LOGGER.info("Total IMEI numbers loaded in Cache:"+ CacheManager.imeiNumbers.size());
	}
	
	public void loadApplicationSettings() {

		LOGGER.info("Loading application settings....");

		for (ApplicationSetting as : appSettingService.getAllApplicationSettings()) {
			CacheManager.ápplicationSettings.put(as.getApplicationKey(), as.getAppValue());
		}
		
		LOGGER.info("Total application settings loaded in Cache:"+ CacheManager.ápplicationSettings.size());
	}

	public void loadCompanySettings() {

		LOGGER.info("Loading company settings....");
		
		for (CompanySettings cs : compSettingsService.getAllCompanySettings()) {
			CacheManager.companySettings.put(cs.getAppSettingsKey(), cs.getComValues());
		}
		
		LOGGER.info("Total company settings loaded in Cache:"+ CacheManager.companySettings.size());
	}

	public static boolean isImeiNoExist(String imeiNo) {
		return CacheManager.imeiNumbers.contains(imeiNo);
	}
	
	public static String getPreference(String key, String compId) {

		String value = CacheManager.ERROR;

		try {
			if (key != null && key.length() > 0) {
				if (compId != null && compId.length() > 0) {
					StringBuilder keyBuilder = new StringBuilder(key).append(CacheManager.UNDER_SCORE)
							.append(compId.toLowerCase());
					value = companySettings.get(keyBuilder.toString());

				} else {
					value = ápplicationSettings.get(key);
				}
			}

			if (value == null)
				value = CacheManager.ERROR;

		} catch (Exception e) {
			LOGGER.error("Exception in getPreference [key=" + key + ",company=" + compId + "]. Calling method->"
					+ Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		
		return value;
	}

	public static void setPreference(String key, String value, String compId) {
		if (compId != null && compId.length() > 0) {
			StringBuilder keyBuilder = new StringBuilder(key).append(CacheManager.UNDER_SCORE)
					.append(compId.toLowerCase());
			companySettings.put(keyBuilder.toString(), value);
		} else
			ápplicationSettings.put(key, value);

	}

	@PostConstruct
	public void loadSettings() {
		loadApplicationSettings();
		loadCompanySettings();
		loadActiveIMEINumbers();
	}
}
