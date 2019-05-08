package psk.dictionary.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import psk.dictionary.exception.DictionaryNotFound;
import psk.dictionary.exception.WordNotFound;
import psk.dictionary.model.Dictionary;
import psk.dictionary.repository.DictionaryRepository;
import psk.dictionary.rest.DictionaryDAO;
import psk.dictionary.rest.EditTranslationDAO;
import psk.dictionary.rest.TranslationsDAO;
import psk.dictionary.service.SaveToFile;

@CrossOrigin
@RestController
@RequestMapping("/api/dictionary")
public class DictionaryRestController {
	
	@Autowired
	private DictionaryRepository dictionaryRepository;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<DictionaryDAO>> getDictionares() {

		try {
			List<DictionaryDAO> dictionaries = dictionaryRepository.getDictionariesDAO();
			return ResponseEntity.status(HttpStatus.OK).body(dictionaries);
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RequestMapping(value="/{baseLanguage}/{translatedLanguage}/{word}", method = RequestMethod.GET)
	public ResponseEntity<TranslationsDAO> getTranslations(@PathVariable String baseLanguage, @PathVariable String translatedLanguage, @PathVariable String word){
		
		try {
			List<String> translations = dictionaryRepository.getWords(baseLanguage, translatedLanguage, word);
			return ResponseEntity.ok(new TranslationsDAO(translations));
		} catch (WordNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (DictionaryNotFound e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RequestMapping(value="/{baseLanguage}/{translatedLanguage}/{word}", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> addTranslations(@PathVariable String baseLanguage, @PathVariable String translatedLanguage, @PathVariable String word, @RequestBody TranslationsDAO translations){
		try {
			dictionaryRepository.addTranslations(baseLanguage, translatedLanguage, word, translations.getTranslations());
			for(String translation : translations.getTranslations()) {
				dictionaryRepository.addTranslation(translatedLanguage, baseLanguage, translation, word);
			}
			return ResponseEntity.ok().build();
		} catch (WordNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (DictionaryNotFound e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RequestMapping(value="/{baseLanguage}/{translatedLanguage}/{word}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> removeWord(@PathVariable String baseLanguage, @PathVariable String translatedLanguage, @PathVariable String word) {
		try {
			List<String> words = dictionaryRepository.getWords(baseLanguage, translatedLanguage, word);
			for(String w : words) {
				dictionaryRepository.removeWord(translatedLanguage, baseLanguage, w);
			}
			dictionaryRepository.removeWord(baseLanguage, translatedLanguage, word);
			return ResponseEntity.ok().build();
		} catch (WordNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (DictionaryNotFound e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RequestMapping(value="/tips/{baseLanguage}/{translatedLanguage}/{word}", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getTips(@PathVariable String baseLanguage, @PathVariable String translatedLanguage, @PathVariable String word) {
		try {
			List<String> tips = dictionaryRepository.getTips(baseLanguage, translatedLanguage, word);
			return ResponseEntity.ok().body(tips);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RequestMapping(value="/translation/{baseLanguage}/{translatedLanguage}/{word}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> removeTranslations(@PathVariable String baseLanguage, @PathVariable String translatedLanguage, @PathVariable String word, @RequestBody EditTranslationDAO editTranslation) {
		try {
			dictionaryRepository.removeTranslation(baseLanguage, translatedLanguage, word, editTranslation.getOldTranslation());
			dictionaryRepository.addTranslation(baseLanguage, translatedLanguage, word, editTranslation.getNewTranslation());
			
			dictionaryRepository.removeTranslation(baseLanguage, translatedLanguage, word, editTranslation.getOldTranslation());
			dictionaryRepository.addTranslation(baseLanguage, translatedLanguage, word, editTranslation.getNewTranslation());
			return ResponseEntity.ok().build();
		} catch (WordNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (DictionaryNotFound e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RequestMapping(value="/dictionary/{baseLanguage}/{translatedLanguage}", method = RequestMethod.PUT)
	public ResponseEntity<HttpStatus> saveToFile(@PathVariable String baseLanguage, @PathVariable String translatedLanguage) {
		try {
			Dictionary dictionary = dictionaryRepository.getDictionary(baseLanguage, translatedLanguage);
			String dictionaryFilePath = dictionaryRepository.getDictionaryProperties().getDictionaryFilePath();
			SaveToFile.saveDictionary(dictionary, dictionaryFilePath); 
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (DictionaryNotFound e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).build();		
		
	}
}