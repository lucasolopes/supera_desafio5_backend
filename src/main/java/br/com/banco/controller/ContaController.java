package br.com.banco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.domain.Transferencia.DadosTransferencia;
import br.com.banco.domain.Transferencia.TransferenciaRepository;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    TransferenciaRepository transferenciaRepository;

    @GetMapping()
    public ResponseEntity<Page<DadosTransferencia>> listar(@PageableDefault(sort = "id") Pageable paginacao,
            @RequestParam(required = false) Long id) {
        Page<DadosTransferencia> page;
        if (id == null) {
            page = transferenciaRepository.findAll(paginacao).map(DadosTransferencia::new);

        } else {
            page = transferenciaRepository.findByContaBancaria(id, paginacao).map(DadosTransferencia::new);

        }
        return ResponseEntity.ok().body(page);
    }

}
