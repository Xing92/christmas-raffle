package com.xing.christmas.raffle.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xing.christmas.raffle.Entry;

public class RaffleHelperTest {

	@Test
	public void raffleTestOne() {
		List<Entry> entries = null;
		try {
			entries = Arrays.asList(new ObjectMapper().readValue(new File("./entries.json"), Entry[].class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		RaffleHelper helper = new RaffleHelper();
		List<Entry> raffleEntries = helper.raffle(entries);
		System.out.println("Raffle:" + raffleEntries.stream().map(Entry::getName).collect(Collectors.toList()));
		System.out.println("Done");
	}
}
