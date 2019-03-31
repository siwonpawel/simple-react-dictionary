package psk.dictionary.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application.dictionary")
@Configuration("applicationProperties")
public class DictionaryProperties {
	
	private String dictionaryFilePath;

	public String getDictionaryFilePath() {
		return dictionaryFilePath;
	}

	public void setDictionaryFilePath(String dictionaryFilePath) {
		this.dictionaryFilePath = dictionaryFilePath;
	}
	
}
