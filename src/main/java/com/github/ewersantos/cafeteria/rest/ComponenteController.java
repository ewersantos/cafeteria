package com.github.ewersantos.cafeteria.rest;

import com.github.ewersantos.cafeteria.model.entity.Componente;
import com.github.ewersantos.cafeteria.model.entity.Ingrediente;
import com.github.ewersantos.cafeteria.model.entity.Produto;
import com.github.ewersantos.cafeteria.model.repository.ComponenteRepository;
import com.github.ewersantos.cafeteria.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/componentes")
public class ComponenteController {

    private final ComponenteRepository repository;

    @Autowired
    public ComponenteController(ComponenteRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Componente> obterTodos(){
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Componente salvar(@RequestBody @Valid Componente componente){
        return repository.save(componente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository.findById(id).map( componente -> {
            repository.delete(componente);
            return Void.TYPE;
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Componente não encontrado."));
    }
}
