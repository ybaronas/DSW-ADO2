package br.senac.tads.dsw.exemplo3.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.senac.tads.dsw.exemplo3.model.Avaliacao;
import br.senac.tads.dsw.exemplo3.repository.AvaliacaoRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/Avaliacao")
public class AvaliacaoController {
    
    private AvaliacaoRepository repository;

    public AvaliacaoController(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    @PostMapping()
    public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody @Valid Avaliacao Avaliacao) {
        
        Avaliacao AvaliacaoSalvo = repository.save(Avaliacao);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(AvaliacaoSalvo.getId())
            .toUri();

        return ResponseEntity.created(location).body(AvaliacaoSalvo);
    }

    @GetMapping()
    public List<Avaliacao> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> buscarPorId(@PathVariable Long id) {
        Optional<Avaliacao> AvaliacaoBuscado = repository.findById(id);

        if (AvaliacaoBuscado.isPresent()) {
            return ResponseEntity.ok(AvaliacaoBuscado.get());
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> atualizarAvaliacao(@PathVariable Long id, 
                                                    @RequestBody @Valid Avaliacao AvaliacaoAtualizado) {

        Optional<Avaliacao> AvaliacaoBuscado = repository.findById(id);

        if (AvaliacaoBuscado.isPresent()) {
            Avaliacao AvaliacaoExistente = AvaliacaoBuscado.get();

            AvaliacaoExistente.setNome(AvaliacaoAtualizado.getNome());
            AvaliacaoExistente.setComentario(AvaliacaoAtualizado.getComentario());
            AvaliacaoExistente.setNota(AvaliacaoAtualizado.getNota());

            repository.save(AvaliacaoExistente);

            return ResponseEntity.ok(AvaliacaoExistente);
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarAvaliacao(@PathVariable Long id) {
        Optional<Avaliacao> AvaliacaoBuscado = repository.findById(id);

        if (AvaliacaoBuscado.isPresent()) {
            repository.deleteById(id);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

}
