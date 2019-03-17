package psk.dictionary.model;

import java.util.List;

import psk.dictionary.exception.NodeNotFound;

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
    	
    	DictionaryNode activeNode = initNode;
    	for(int i = 0; i < baseLanguageWordChars.length; i++) {
    		try {
				activeNode = activeNode.getNode(baseLanguageWordChars[i]);
			} catch (NodeNotFound e) {
				DictionaryNode newNode = new DictionaryNode();
				activeNode.addNode(baseLanguageWordChars[i], newNode);
				activeNode = newNode;
			}
    	}
    	
    	activeNode.addTranslation(translatedWord);
    }
    
    public List<String> getTranslations(String baseLanguageWord) throws NodeNotFound {
    	char[] baseLanguageWordChars = baseLanguageWord.toCharArray();
    	
    	DictionaryNode activeNode = initNode;
    	for(int i = 0; i < baseLanguageWordChars.length; i++) {
			activeNode = activeNode.getNode(baseLanguageWordChars[i]);
    	}
    	
    	return activeNode.getTranslations();
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