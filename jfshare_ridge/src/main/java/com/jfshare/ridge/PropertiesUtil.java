package com.jfshare.ridge;

import com.jfshare.ridge.json.template.KafkaTemplate;

public class PropertiesUtil{
	public static String getProperty(String appKey, String configKey){
		return ConfigManager.configManager.getConfigValue(appKey, configKey);
	}

	public static String getProperty(String appKey, String configKey, String defValue){
		return ConfigManager.configManager.getConfigValue(appKey, configKey, defValue);
	}

	public static KafkaTemplate getKafkaProperty(String appKey, String configKey){
		return ConfigManager.configManager.getKafkaConfig(appKey, configKey);
	}
}
