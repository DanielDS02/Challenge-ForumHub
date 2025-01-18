package com.forumhub.controller;

import com.forumhub.dto.ResponseDTO;
import com.forumhub.dto.TopicoAtualizacaoDTO;
import com.forumhub.model.Topico;
import com.forumhub.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController<TopicRequestDTO> {

    @Autowired
    private TopicoRepository topicoRepository;

    // Endpoint para listar todos os tópicos
    @GetMapping
    public List<Topico> listar() {
        return topicoRepository.findAll();
    }

    // Endpoint para cadastrar um novo tópico
    @PostMapping
    public ResponseEntity<Topico> cadastrar(@RequestBody @Valid Topico topico) {
        if (topicoRepository.existsByTituloAndMensagem(topico.getTitulo(), topico.getMensagem())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Topico topicoSalvo = topicoRepository.save(topico);
        return ResponseEntity.status(HttpStatus.CREATED).body(topicoSalvo);
    }

    // Endpoint para detalhar um tópico específico
    @GetMapping("/{id}")
    public ResponseEntity<Topico> detalhar(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Endpoint para atualizar um tópico
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Topico> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoAtualizacaoDTO dados) {
        return topicoRepository.findById(id).map(topico -> {
            topico.setTitulo(dados.getTitulo());
            topico.setMensagem(dados.getMensagem());
            return ResponseEntity.ok(topico);
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Endpoint para deletar um tópico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return topicoRepository.findById(id).map(topico -> {
            topicoRepository.delete(topico);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/topics")
    public ResponseEntity<ResponseDTO<?>> createTopic(@RequestBody @Valid TopicRequestDTO topicRequest) {
        TopicoController<TopicRequestDTO> topicService = null;
        Topic topic = topicService.createTopic(topicRequest);
        return ResponseEntity.ok(new ResponseDTO<>(true, "Tópico criado com sucesso", topic));
    }

}
