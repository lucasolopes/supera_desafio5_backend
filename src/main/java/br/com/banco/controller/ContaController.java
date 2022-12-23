package br.com.banco.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.domain.Conta.Conta;
import br.com.banco.domain.Conta.ContaDto;
import br.com.banco.domain.Conta.ContaRepository;
import br.com.banco.domain.Transferencia.DadosTransferencia;
import br.com.banco.domain.Transferencia.Saldo;
import br.com.banco.domain.Transferencia.TransferenciaCustomRepository;
import br.com.banco.domain.Transferencia.TransferenciaRepository;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    TransferenciaCustomRepository transferenciaCustomRepository;

    @Autowired
    TransferenciaRepository transferenciaRepository;

    @Autowired
    ContaRepository contaRepository;

    @GetMapping()
    public ResponseEntity<ContaDto> findTransferenciaBycustom(
            @RequestParam(name = "NumConta", required = true) Long idConta,
            @RequestParam(name = "DataInicio", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataTransferenciaInicio,
            @RequestParam(name = "DataFim", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataTransferenciaFim,
            @RequestParam(name = "NomeOperador", required = false) String nomeOperadorTransacao,
            Pageable pageable) {

        Optional<Conta> conta = contaRepository.findById(idConta);
        if (conta.isPresent()) {
            Page<DadosTransferencia> transferencias = transferenciaCustomRepository.find(idConta,
                    dataTransferenciaInicio,
                    dataTransferenciaFim,
                    nomeOperadorTransacao, pageable).map(DadosTransferencia::new);

            Saldo saldos = transferenciaCustomRepository.getSaldoByNumConta(idConta, dataTransferenciaInicio,
                    dataTransferenciaFim);

            return ResponseEntity.ok().body(new ContaDto(conta.get(), saldos, transferencias));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/teste")
    public Saldo teste(
            @RequestParam(name = "IdConta", required = true) Long idConta,
            @RequestParam(name = "DataInicio", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataTransferenciaInicio,
            @RequestParam(name = "DataFim", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataTransferenciaFim) {

        return transferenciaCustomRepository.getSaldoByNumConta(idConta, dataTransferenciaInicio, dataTransferenciaFim);

    }

}
