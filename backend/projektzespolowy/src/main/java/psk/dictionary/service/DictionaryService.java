package psk.dictionary.service;

import java.util.List;
import java.util.stream.Collectors;

import psk.dictionary.model.Dictionary;
import psk.dictionary.rest.DictionaryDAO;

public class DictionaryService {

	public List<DictionaryDAO> convertDictionaryListToDAOList(List<Dictionary> dictionaries) {
		return dictionaries.stream().map(DictionaryDAO::new).collect(Collectors.toList());
	}
	
}
