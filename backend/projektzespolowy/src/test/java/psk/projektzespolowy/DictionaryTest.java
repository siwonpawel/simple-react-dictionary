package psk.projektzespolowy;

import org.junit.Test;

import psk.dictionary.model.Dictionary;

public class DictionaryTest {
	
	Dictionary dictionary;

	@Test
	public void create() {
		dictionary = new Dictionary("PL", "EN");
		assert dictionary != null;
	}
	
	@Test
	public void addWord() {
		
		dictionary = new Dictionary("PL", "EN");
		
		String baseLanguageWord = "kot";
		String translatedWord = "cat";
		
		dictionary.addWord(baseLanguageWord, translatedWord);
	}
    
}