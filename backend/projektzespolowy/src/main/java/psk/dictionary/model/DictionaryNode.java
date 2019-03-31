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
    	translations.add(translation);
    }
    
    public void addTranslation(Collection<String> newTranslations) {
    	
    	for(String translation : newTranslations) {
    		if(!this.translations.contains(translation))
    			translations.add(translation);
    	}
    }
    
    public void addNode(char c, DictionaryNode node) {
    	nextNodes.put(c, node);
    }
}