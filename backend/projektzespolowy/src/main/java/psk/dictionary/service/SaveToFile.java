package psk.dictionary.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import psk.dictionary.model.Dictionary;
import psk.dictionary.model.DictionaryNode;

public class SaveToFile {
	
	private final static String BASE_LANGUAGE = "base_language";
	private final static String TRANSLATED_LANGUAGE = "translated_language";
	
	public static void saveDictionary(Dictionary dic, String path) throws IOException {
		try (BufferedWriter out = new BufferedWriter(new FileWriter(path, false))) {
			DictionaryNode rootNode = dic.getRootNode();
			out.write(prepareToSave(BASE_LANGUAGE, dic.getBaseLanguage()));
			out.write(prepareToSave(TRANSLATED_LANGUAGE, dic.getTranslatedLanguage()));
			SaveToFile.saveDictionaryNode(rootNode, "", out);
		} catch (IOException e) {
			throw e;
		}		
	}
	
	private static void saveDictionaryNode(DictionaryNode dn, String word, BufferedWriter out) throws IOException {
		if(dn == null) {
			return;
		}
		
		if(word != null && !dn.getTranslations().isEmpty()) {
			for(String w : dn.getTranslations()) {
				out.write(prepareToSave(word, w));
			}
		}
		
		Map<Character, DictionaryNode> nextNodes = dn.getNextNodes();
		for(Character c : nextNodes.keySet()) {
			SaveToFile.saveDictionaryNode(nextNodes.get(c), word + c, out);
		}
	}
	
	private static String prepareToSave(String properties, String value) {
		return properties + " = " + value + "\n";
	}
	
}
