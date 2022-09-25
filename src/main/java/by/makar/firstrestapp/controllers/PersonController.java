package by.makar.firstrestapp.controllers;

import by.makar.firstrestapp.models.Person;
import by.makar.firstrestapp.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import util.PersonErrorResponse;
import util.PersonNotCreatedException;
import util.PersonNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;
    private static Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getPeople(){
        return personService.findAll();
    }
    @GetMapping("/{id}")
    public Person getPerson(@PathVariable int id){
        return personService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            logger.info("Error thrown.");
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors)
                errorMsg.append(error).append(" - ").append(error.getDefaultMessage()).append(";");
            logger.debug(errorMsg.toString());
            throw new PersonNotCreatedException(errorMsg.toString());
        }
        personService.save(person);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e){
        PersonErrorResponse personErrorResponse = new PersonErrorResponse("Человек не найден.", System.currentTimeMillis());

        return new ResponseEntity<>(personErrorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e){
        PersonErrorResponse personErrorResponse = new PersonErrorResponse("Человек не создан", System.currentTimeMillis());
        return new ResponseEntity<>(personErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
