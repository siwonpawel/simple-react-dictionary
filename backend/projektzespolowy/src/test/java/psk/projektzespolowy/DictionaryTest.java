package psk.projektzespolowy;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import psk.dictionary.exception.NodeNotFound;
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
		
		String baseLanguageWord = "kot";
		String translatedWord = "cat";
		
		addWord();
		
		List<String> getWord = null;
		try {
			getWord = dictionary.getTranslations(baseLanguageWord);
		} catch (NodeNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
		assertTrue(getWord.contains(translatedWord));
	}
    
}