package psk.dictionary.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import psk.dictionary.model.Dictionary;

@RestController
@RequestMapping("/api/dictionary")
public class DictionaryRestController {
	
	List<Dictionary> dcs = new ArrayList<>();
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public List<String> getDictionares() {
		return Arrays.asList("XD", "XDD", "XDDD");
	}

}