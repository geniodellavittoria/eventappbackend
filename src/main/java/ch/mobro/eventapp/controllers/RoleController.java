package ch.mobro.eventapp.controllers;

import ch.mobro.eventapp.models.Role;
import ch.mobro.eventapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static ch.mobro.eventapp.config.PathConstants.ID;
import static ch.mobro.eventapp.config.PathConstants.ROLE;


@RestController
@RequestMapping(ROLE)
public class RoleController {

    private final UserRepository repository;

    @Autowired
    public RoleController(UserRepository repository) {
        this.repository = repository;
    }

    /*
    @GetMapping
    public List<Role> getRoles() {
        return repository.findAll();
    }

    @GetMapping(ID)
    public Optional<Role> getRole(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return repository.insert(role);
    }

    @PutMapping(ID)
    public Role updateRole(@RequestBody Role role) {
        return repository.save(role);
    }

    @DeleteMapping(ID)
    public void deleteRole(@PathVariable("id") String id) {
        repository.deleteById(id);
    }
    */
}
