package forum.hub.api.controller;

import forum.hub.api.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController("topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopicos dados, UriComponentsBuilder uriBuilder) {
        Topico topico = new Topico(dados);
        topico = repository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }


    @GetMapping("/topicos")
    public ResponseEntity<Page<DadosListagemTopico>> listarTopico(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer ano,
            @PageableDefault(size = 10, sort = {"titulo"}) Pageable paginacao) {

        Specification<Topico> esp = Specification.where((root, query, builder) -> builder.equal(root.get("status"), true));

        if (nome != null && !nome.isBlank()) {
            esp = esp.and(EspecificacaoTopico.byNome(nome));
        }

        if (ano != null) {
            esp = esp.and(EspecificacaoTopico.byAno(ano));
        }

        var page = repository.findAll(esp, paginacao).map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping
    @Transactional
    public ResponseEntity detalharTopico(@RequestParam Long id) {
        Optional<Topico> topico = repository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.ok(new DadosDetalhamentoTopico(topico.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizarTopico(@PathVariable Long id, @RequestBody @Valid DadosCadastroTopicos dados) {
        return repository.findById(id)
                .map(topico -> {
                    topico.atualizarInformacoes(dados);
                    return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
                })
                .orElse(ResponseEntity.notFound().build());
    }


}
