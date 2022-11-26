package com.xing.christmas.raffle;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xing.christmas.raffle.repository.EntryRepository;

@Component
public class InitialConfiguration {

	@Autowired
	EntryRepository entryRepository;

	@PostConstruct
	public void init() {
		try {
			System.out.println("LOADING");
			List<Entry> entries = Arrays
					.asList(new ObjectMapper().readValue(new File("./entries.json"), Entry[].class));
			entryRepository.deleteAll();
			entryRepository.saveAll(entries);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
