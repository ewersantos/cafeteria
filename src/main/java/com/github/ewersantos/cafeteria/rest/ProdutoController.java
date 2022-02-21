package com.github.ewersantos.cafeteria.rest;

import com.github.ewersantos.cafeteria.model.entity.Ingrediente;
import com.github.ewersantos.cafeteria.model.entity.Produto;
import com.github.ewersantos.cafeteria.model.repository.IngredienteRepository;
import com.github.ewersantos.cafeteria.model.repository.ProdutoRepository;
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
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;

    @Autowired
    public ProdutoController(ProdutoRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Produto> obterTodos(){
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto salvar(@RequestBody @Valid Produto produto){
        return repository.save(produto);
    }

    @GetMapping("{id}")
    public Produto acharPorId(@PathVariable Integer id ){
        return repository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository.findById(id).map( produto -> {
            repository.delete(produto);
            return Void.TYPE;
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer id, @RequestBody @Valid Produto produtoAtualizado){
        repository.findById(id).map( produto -> {
            produto.setNome(produtoAtualizado.getNome());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setImagem(produtoAtualizado.getImagem());
            return repository.save(produto);
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @PutMapping("{id}/imagem")
    public byte[] addPhoto(@PathVariable Integer id, @RequestParam("imagem") Part arquivo){
        Optional<Produto> produto = repository.findById(id);
        return produto.map(p -> {
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
