package by.makar.firstrestapp.repos;

import by.makar.firstrestapp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person, Integer> {
}
