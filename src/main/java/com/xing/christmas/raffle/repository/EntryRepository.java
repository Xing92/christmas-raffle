package com.xing.christmas.raffle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xing.christmas.raffle.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

	Optional<Entry> findByName(String name);

	Optional<Entry> findByNameAndEmail(String name, String email);

}
