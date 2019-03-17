package psk.dictionary.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import psk.dictionary.model.Dictionary;

public class DecodeFile {
	
	public enum type {
		BASE,
		TRANSLATED
	}
	
	public List<Dictionary> readDictionaryFromFile(String filename) {
		
		Dictionary dictionaryBaseLanguage;
		Dictionary dictionaryReverseBaseLanguage;
		Scanner file = null;
		
		try {
			file = new Scanner(new FileReader(filename));
			
			if(!file.hasNextLine())
				throw new IllegalStateException ();
		
			Map<DecodeFile.type, String> languages = readLanguage(file);
			
			dictionaryBaseLanguage = new Dictionary(languages.get(DecodeFile.type.BASE), languages.get(DecodeFile.type.TRANSLATED));
			dictionaryReverseBaseLanguage = new Dictionary(languages.get(DecodeFile.type.TRANSLATED), languages.get(DecodeFile.type.BASE));
			
			while(file.hasNextLine()) {
				String[] columns = file.nextLine().split(" = ");
				if(columns.length == 2)
					dictionaryBaseLanguage.addWord(columns[0], columns[1]);
					dictionaryReverseBaseLanguage.addWord(columns[1], columns[0]);
			}
		} catch (FileNotFoundException | ParseException | IllegalStateException e) {
			e.printStackTrace();
			return null;
		} finally {
			if(file != null)
				file.close();
		}
		
		return Arrays.asList(dictionaryBaseLanguage, dictionaryReverseBaseLanguage);
	}
	
	private Map<DecodeFile.type, String> readLanguage(Scanner file) throws FileNotFoundException, ParseException {
		
		Map<DecodeFile.type, String> map = new HashMap<>();
		
		String bLanguage = null;
		String tLanguage = null;
		
		
		String[] columns = file.nextLine().split(" = ");
		if("base_language".equals(columns[0])) {
			bLanguage = columns[1];
		}
		
		if(!file.hasNextLine())
			throw new FileNotFoundException();
		columns = file.nextLine().split(" = ");
		if("translated_language".equals(columns[0])) {
			tLanguage = columns[1];
		}
		
		if(bLanguage == null || tLanguage == null) {
			throw new ParseException();
		}
		
		map.put(type.BASE, bLanguage);
		map.put(type.TRANSLATED, tLanguage);
		
		return map;
	}
}
