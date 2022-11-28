package com.xing.christmas.raffle.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.xing.christmas.raffle.Entry;

public class RaffleHelper {

	public static List<Entry> raffle(List<Entry> inputEntries) {
		boolean rerun = false;
		List<Entry> tempEntries = new ArrayList<>(inputEntries);
		Collections.shuffle(tempEntries);
		List<Entry> outputEntries = new ArrayList<>();

		outputEntries.add(tempEntries.stream().findAny().get());
		tempEntries.removeAll(outputEntries);

		int size = tempEntries.size();
		try {
			for (int i = 0; i < size; i++) {
				Entry lastEntry = outputEntries.get(outputEntries.size() - 1);
				outputEntries.add(tempEntries.stream().filter(e -> e.getExcludeGroup() != lastEntry.getExcludeGroup())
						.findAny().get());
				tempEntries.removeAll(outputEntries);
			}
		} catch (Exception e) {
			System.out.println("Exception");
			rerun = true;
		}
		if (outputEntries.get(0).getExcludeGroup() == outputEntries.get(outputEntries.size() - 1).getExcludeGroup()
				|| rerun) {
			System.out.println("ReRaffle");
			return raffle(inputEntries);
		}

		return outputEntries;
	}
}
