package psk.dictionary.restDAO;

import psk.dictionary.model.Dictionary;

public class DictionaryDAO {
	
	private String baseLanguage;
    private String translatedLanguage;
	
	public DictionaryDAO(Dictionary dictionary) {
		this.baseLanguage = dictionary.getBaseLanguage();
		this.translatedLanguage = dictionary.getTranslatedLanguage();
	}

	public String getBaseLanguage() {
		return baseLanguage;
	}

	public void setBaseLanguage(String baseLanguage) {
		this.baseLanguage = baseLanguage;
	}

	public String getTranslatedLanguage() {
		return translatedLanguage;
	}

	public void setTranslatedLanguage(String translatedLanguage) {
		this.translatedLanguage = translatedLanguage;
	}
}
