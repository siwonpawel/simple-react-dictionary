package psk.dictionary.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import psk.dictionary.exception.NodeNotFound;

public class DictionaryNode {

    private List<String> translations;
    private Map<Character, DictionaryNode> nextNodes;

    public DictionaryNode() {
        translations = new LinkedList<>();
        nextNodes = new LinkedHashMap<>();
    }
    
    public Map<Character, DictionaryNode> getNextNodes() {
    	return nextNodes;
    }
    
    public DictionaryNode getNode(char c) throws NodeNotFound {
    	if(!nextNodes.containsKey(c)) {
    		throw new NodeNotFound();
    	}
    	return nextNodes.get(c);
    }
    
    public List<String> getTranslations() {
    	return translations;
    }
    
    public void addTranslation(String translation) {
    	
    	for(String w : translations) {
    		if(w.equalsIgnoreCase(translation))
    			return;
    	}
    	
    	translations.add(translation);
    }
    
    public void removeTranslation(String translation) {
    	
    	int i = -1;
    	for(String word : translations) {
    		i++;
    		if(translation.equalsIgnoreCase(word))
    			break;
    	}
    	
    	translations.remove(i);
    }
    
    public void removeTranslations(List<String> translations) {
    	for(String word : translations) {
    		removeTranslation(word);
    	}
    }
    
    public void removeWord() {
    	translations.clear();
    }
    
    public void addTranslations(Collection<String> newTranslations) {
    	
    	for(String translation : newTranslations) {
    		addTranslation(translation);
    	}
    }
    
    public void addNode(char c, DictionaryNode node) {
    	nextNodes.put(c, node);
    }
}