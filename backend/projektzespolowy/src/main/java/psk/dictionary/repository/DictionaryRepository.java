package psk.dictionary.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import psk.dictionary.config.DictionaryProperties;
import psk.dictionary.exception.DictionaryNotFound;
import psk.dictionary.exception.WordNotFound;
import psk.dictionary.model.Dictionary;
import psk.dictionary.rest.DictionaryDAO;
import psk.dictionary.service.DecodeFile;
import psk.dictionary.service.DictionaryService;

@Repository
public class DictionaryRepository {
	
	List<Dictionary> dictionaries;
	DictionaryService dictionaryService;
	
	@Autowired
	DictionaryProperties dictionaryProperties;

	public DictionaryRepository() {
		dictionaries = new ArrayList<>();
		dictionaryService = new DictionaryService();
	}
	
	@PostConstruct
	public void loadDictionaries() {
		DecodeFile decode = new DecodeFile();
		dictionaries.addAll(decode.readDictionaryFromFile(dictionaryProperties.getDictionaryFilePath()));
	}
	
	public List<Dictionary> getDictionaries() {
		return dictionaries;
	}
	
	public List<DictionaryDAO> getDictionariesDAO() {
		return dictionaryService.convertDictionaryListToDAOList(dictionaries);
	}
	
	public List<String> getWords(String baseLanguage, String translatedLanguage, String word) throws DictionaryNotFound, WordNotFound {
		Dictionary dictionary = getDictionary(baseLanguage, translatedLanguage);
		return dictionary.getTranslations(word);
	}
	
	public void addTranslations(String baseLanguage, String translatedLanguage, String word, List<String> translations) throws DictionaryNotFound, WordNotFound {
		Dictionary dictionary = getDictionary(baseLanguage, translatedLanguage);
		dictionary.addTranslationsToWord(word, translations);
	}
	
	private Dictionary getDictionary(String baseLanguage, String translatedLanguage) throws DictionaryNotFound {
		
		Optional<Dictionary> dictionary = dictionaries.stream()
			.filter(e -> baseLanguage.equalsIgnoreCase(e.getBaseLanguage()))
			.filter(e -> translatedLanguage.equalsIgnoreCase(e.getTranslatedLanguage()))
			.findFirst();
		
		if(dictionary.isPresent()) {
			return dictionary.get();
		}
		
		throw new DictionaryNotFound();
	}

	public DictionaryProperties getDictionaryProperties() {
		return dictionaryProperties;
	}

	public void setDictionaryProperties(DictionaryProperties dictionaryProperties) {
		this.dictionaryProperties = dictionaryProperties;
	}
	
}
