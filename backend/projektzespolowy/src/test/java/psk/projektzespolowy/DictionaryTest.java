package psk.projektzespolowy;

import org.junit.Before;
import org.junit.Test;

import psk.dictionary.model.Dictionary;

public class DictionaryTest {
	
	Dictionary dictionary;
	
	@Before
	public void createDictionary() {
		dictionary = new Dictionary("PL", "EN");
	}

	@Test
	public void create() {
		dictionary = new Dictionary("PL", "EN");
		assert dictionary != null;
	}
	
	@Test
	public void addWord() {
		
		String baseLanguageWord = "kot";
		String translatedWord = "cat";
		
		dictionary.addWord(baseLanguageWord, translatedWord);
	}
	
	@Test
	public void getWord() {

	}
    
}