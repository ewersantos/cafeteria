package com.github.ewersantos.cafeteria.rest;

import com.github.ewersantos.cafeteria.model.entity.Ingrediente;
import com.github.ewersantos.cafeteria.model.entity.Produto;
import com.github.ewersantos.cafeteria.model.repository.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {

    private final IngredienteRepository repository;

    @Autowired
    public IngredienteController(IngredienteRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Ingrediente> obterTodos(){
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ingrediente salvar(@RequestBody @Valid Ingrediente ingrediente){
        return repository.save(ingrediente);
    }

    @GetMapping("{id}")
    public Ingrediente acharPorId(@PathVariable Integer id ){
        return repository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado."));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository.findById(id).map( ingrediente -> {
            repository.delete(ingrediente);
            return Void.TYPE;
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado."));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer id, @RequestBody @Valid Ingrediente ingredienteAtualizado){
        repository.findById(id).map( ingrediente -> {
            ingrediente.setNome(ingredienteAtualizado.getNome());
            ingrediente.setPrecoUnitario(ingredienteAtualizado.getPrecoUnitario());
            ingrediente.setUnidadeMedida(ingredienteAtualizado.getUnidadeMedida());
            return repository.save(ingrediente);
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado."));
    }

    @PutMapping("{id}/imagem")
    public byte[] addPhoto(@PathVariable Integer id, @RequestParam("imagem") Part arquivo){
        Optional<Ingrediente> ingrediente = repository.findById(id);
        return ingrediente.map(p -> {
            try{
                InputStream is = arquivo.getInputStream();
                byte[] bytes = new byte[(int) arquivo.getSize()];
                IOUtils.readFully(is, bytes);
                p.setImagem(bytes);
                repository.save(p);
                is.close();
                return bytes;
            }catch (IOException e){
                return null;
            }
        }).orElse(null);
    }
}
