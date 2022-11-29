package com.xing.christmas.raffle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xing.christmas.raffle.repository.EntryRepository;
import com.xing.christmas.raffle.util.EmailHelper;
import com.xing.christmas.raffle.util.RaffleHelper;

@Controller
public class WelcomeController {

	@Autowired
	EntryRepository entryRepository;

	private static final String URI_PREFFIX = "/christmas-raffle";

//    // inject via application.properties
//    @Value("${welcome.message}")
//    private String message;

	@GetMapping({ "/", "/home" })
	public String main(Model model) {
		List<String> names = entryRepository.findAll().stream().map(Entry::getName).collect(Collectors.toList());
		try {
			model.addAttribute("endDate", new SimpleDateFormat("yyyy-MM-dd").parse("2022-12-03"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("names=" + names);
		model.addAttribute("names", names);

		return "welcome"; // view
	}

	@GetMapping("/hello")
	public String mainWithParam(@RequestParam(name = "name", required = false, defaultValue = "") String name,
			Model model) {

		model.addAttribute("message", name);

		return "welcome"; // view
	}

	@GetMapping("/entry")
	public String showAllEntries() {
		List<Entry> entries = entryRepository.findAll();
		System.out.println(entries);

		return "welcome";
	}

	@GetMapping("/entry/add")
	public String addEntry(Model model) {
		Entry entry = new Entry();
		model.addAttribute("entry", entry);
		return "entry-form";
	}

	@PostMapping("/entry/add")
	public String saveAddEntry(Model model, @ModelAttribute("entry") Entry requestEntry) {
		model.addAttribute("user", requestEntry);
		if (requestEntry.getName().isBlank() || requestEntry.getEmail().isBlank()) {
			return "entry-fail";
		}
		Optional<Entry> optionalEntry = entryRepository.findByNameIgnoreCaseAndEmailIgnoreCase(requestEntry.getName(),
				requestEntry.getEmail());
		if (optionalEntry.isPresent()) {
			Entry entry = optionalEntry.get();
//			entry.setPresents(entry.getPresents() + " ; " + requestEntry.getPresents());
			entry.setPresents1(requestEntry.getPresents1());
			entry.setPresents2(requestEntry.getPresents2());
			entry.setPresents3(requestEntry.getPresents3());
			entryRepository.save(entry);
		} else if (entryRepository.findByNameIgnoreCase(requestEntry.getName()).isPresent()) {
			return "entry-fail";
		} else {
			entryRepository.save(requestEntry);
		}
		return "entry-success";
	}

	@GetMapping("/entry/edit")
	public String editEntry(Model model) {
		Entry entry = new Entry();
		model.addAttribute("entry", entry);
		return "entry-edit-form";
	}

	@PostMapping("/entry/edit")
	public String saveEditEntry(Model model, @ModelAttribute("entry") Entry requestEntry) {

		Optional<Entry> entry = entryRepository.findByNameIgnoreCaseAndEmailIgnoreCase(requestEntry.getName(),
				requestEntry.getEmail());

		if (entry.isEmpty()) {
			model.addAttribute("entry", requestEntry);
			return "entry-fail";
		}
		Entry editedEntry = entry.get();
		editedEntry.setPresents1(requestEntry.getPresents1());
		editedEntry.setPresents2(requestEntry.getPresents2());
		editedEntry.setPresents3(requestEntry.getPresents3());
		entryRepository.save(editedEntry);
		model.addAttribute("entry", editedEntry);
		return "entry-success";
	}

	@GetMapping("/entry/self")
	public String getSelfDetails(Model model) {
		Entry entry = new Entry();
		model.addAttribute("entry", entry);
		return "entry-self";
	}

	@PostMapping("/entry/self")
	public String getSelfDetails(Model model, @ModelAttribute("entry") Entry requestEntry) {
		Optional<Entry> entry = entryRepository.findByNameIgnoreCaseAndEmailIgnoreCase(requestEntry.getName(),
				requestEntry.getEmail());
		if (entry.isEmpty()) {
			model.addAttribute("entry", requestEntry);
			return "entry-fail";
		}
		Entry selfEntry = entry.get();
		model.addAttribute("entry", selfEntry);
		return "entry-self-edit";
	}

	@PostMapping("/entry/self/edit")
	public String editSelfDetails(Model model, @ModelAttribute("entry") Entry requestEntry) {
		System.out.println("requestEntry: " + requestEntry);
		Optional<Entry> oldEntry = entryRepository.findByNameIgnoreCaseAndEmailIgnoreCase(requestEntry.getName(),
				requestEntry.getEmail());
		if (oldEntry.isEmpty()) {
			model.addAttribute("entry", requestEntry);
			return "entry-fail";
		}
		Entry selfEntry = oldEntry.get();
		selfEntry.setPresents1(requestEntry.getPresents1());
		selfEntry.setPresents2(requestEntry.getPresents2());
		selfEntry.setPresents3(requestEntry.getPresents3());

		entryRepository.save(selfEntry);
		model.addAttribute("entry", selfEntry);
		return "entry-success";
	}

	@GetMapping("/admin/dump")
	public String adminDumpAll() {
		List<Entry> entries = entryRepository.findAll();
		String json = "notset";
		try {
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(entries);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		try {
			// write converted json data to a file named "file.json"
			FileWriter writer = new FileWriter("./entries.json");
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "welcome";
	}

	@GetMapping("/admin/raffle")
	public String adminRaffle() {
		List<Entry> entries = entryRepository.findAll();
		List<Entry> raffleEntries = RaffleHelper.raffle(entries);
		System.out.println("Raffle done: " + raffleEntries.stream().map(Entry::getName).collect(Collectors.toList()));
		return "welcome";
	}

	@GetMapping("/admin/load")
	public String adminLoadAll() {
		try {
			System.out.println("LOADING");
			List<Entry> entries = Arrays
					.asList(new ObjectMapper().readValue(new File("./entries.json"), Entry[].class));
			entryRepository.deleteAll();
			System.out.println("saving:" + entries.stream().map(Entry::getName).collect(Collectors.toList()));
			entryRepository.saveAll(entries);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "welcome";
	}

	@GetMapping("/email/test")
	public String emailTestSend() {
		EmailHelper emailHelper = new EmailHelper();
		emailHelper.sendMail("noreply.xing@gmail.com", "noreply.xing@gmail.com", "noreply.xing@gmail.com", "Kahol123",
				"My example body");
		return "welcome";
	}
}