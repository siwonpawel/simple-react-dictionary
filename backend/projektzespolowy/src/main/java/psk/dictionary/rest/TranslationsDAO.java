package psk.dictionary.rest;

import java.util.List;

public class TranslationsDAO {

	private List<String> translations;
	
	public TranslationsDAO() {}
	
	public TranslationsDAO(List<String> translations) {
		this.translations = translations;
	}

	public List<String> getTranslations() {
		return translations;
	}

	public void setTranslations(List<String> translations) {
		this.translations = translations;
	}
}
