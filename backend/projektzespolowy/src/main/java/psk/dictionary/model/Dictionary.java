package psk.dictionary.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import psk.dictionary.exception.NodeNotFound;
import psk.dictionary.exception.WordNotFound;

public class Dictionary {

    private String baseLanguage;
    private String translatedLanguage;

    private DictionaryNode initNode;

    public Dictionary(String baseLanguage, String translatedLanguage) {
    	initNode = new DictionaryNode();

        this.setBaseLanguage(baseLanguage);
        this.setTranslatedLanguage(translatedLanguage);
    }
    
    public void addWord(String baseLanguageWord, String translatedWord) {
    	
    	char[] baseLanguageWordChars = baseLanguageWord.toCharArray();

    	DictionaryNode activeNode = getWordNode(baseLanguageWordChars, initNode);
    	activeNode.addTranslation(translatedWord);
    }

	private DictionaryNode getWordNode(char[] baseLanguageWordChars, DictionaryNode activeNode) {
		for(int i = 0; i < baseLanguageWordChars.length; i++) {
    		try {
				activeNode = activeNode.getNode(baseLanguageWordChars[i]);
			} catch (NodeNotFound e) {
				DictionaryNode newNode = new DictionaryNode();
				activeNode.addNode(baseLanguageWordChars[i], newNode);
				activeNode = newNode;
			}
    	}
		return activeNode;
	}
    
    public void addTranslationsToWord(String baseLanguageWord, Collection<String> translatedWords) {
    	char[] baseLanguageWordChars = baseLanguageWord.toCharArray();
    	
    	DictionaryNode activeNode = getWordNode(baseLanguageWordChars, initNode);
    	activeNode.addTranslations(translatedWords);
    }
    
    public void addTranslationToWord(String baseLanguageWord, String translatedWords) {
    	char[] baseLanguageWordChars = baseLanguageWord.toCharArray();
    	
    	DictionaryNode activeNode = getWordNode(baseLanguageWordChars, initNode);
    	activeNode.addTranslation(translatedWords);
    }
    
    public List<String> getTranslations(String baseLanguageWord) throws WordNotFound {
    	char[] baseLanguageWordChars = baseLanguageWord.toCharArray();
    	
    	DictionaryNode activeNode = initNode;
    	for(int i = 0; i < baseLanguageWordChars.length; i++) {
    		try {
    			activeNode = activeNode.getNode(baseLanguageWordChars[i]);
    		} catch (NodeNotFound e) {
    			throw new WordNotFound();
    		}
    	}
    	
    	List<String> translations = activeNode.getTranslations();
    	
    	if(translations.size() == 0) {
    		throw new WordNotFound();
    	}
    	
    	return translations;
    }
    
    public void removeTranslation(String baseLanguageWord, String translatedWord) {
    	DictionaryNode activeNode = getWordNode(baseLanguageWord.toCharArray(), initNode);
    	activeNode.removeTranslation(translatedWord);
    }
    
    public void removeTranslations(String baseLanguageWord, List<String> translations) {
    	DictionaryNode activeNode = getWordNode(baseLanguageWord.toCharArray(), initNode);
    	activeNode.removeTranslations(translations);
    }
    
    public void removeWord(String baseLanguageWord) {
    	DictionaryNode activeNode = getWordNode(baseLanguageWord.toCharArray(), initNode);
    	activeNode.removeWord();
    }
    
    public List<String> getTips(String word) {
    	List<String> tips = new ArrayList<>();
    	
    	DictionaryNode activeNode = getWordNode(word.toCharArray(), initNode);
    	
    	addTips(word, tips, activeNode);
    	
    	Comparator<String> cmp = (s1, s2) -> {
    		if(s1.length() < s2.length())
    			return 2;
    		else if(s1.length() > s2.length())
    			return 2;
    		
    		return s1.compareTo(s2);
    	};
    	
    	tips.sort(cmp);
    	return tips;
    }
    
    private void addTips(String word, List<String> tips, DictionaryNode node) {
    	if(tips.size() > 10)
    		return;
    	
    	if(node.getTranslations().size() != 0) {
    		tips.add(word);
    	}
    	
    	Map<Character, DictionaryNode> nextNodes = node.getNextNodes();
    	Set<Character> keys = nextNodes.keySet();
    	
    	for(Character key : keys) {
    		addTips(word + key, tips, nextNodes.get(key));
    	}
    	
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