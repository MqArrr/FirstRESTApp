package by.makar.firstrestapp.services;

import by.makar.firstrestapp.models.Person;
import by.makar.firstrestapp.repos.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.PersonNotFoundException;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepo personRepo;

    @Autowired
    public PersonService(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Transactional(readOnly = true)
    public List<Person> findAll(){
        return personRepo.findAll();
    }
    @Transactional(readOnly = true)
    public Person findOne(int id){
        return personRepo.findById(id).orElseThrow(PersonNotFoundException::new);
    }
    @Transactional
    public void save(Person p){
        personRepo.save(p);
    }
}
