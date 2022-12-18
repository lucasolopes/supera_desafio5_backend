package br.com.banco.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.domain.Transferencia.Transferencia;
import br.com.banco.domain.Transferencia.TransferenciaCustomRepository;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    TransferenciaCustomRepository transferenciaCustomRepository;

    @GetMapping()
    public List<Transferencia> findTransferenciaBycustom(
            @RequestParam(name = "IdConta", required = false) Long idConta,
            @RequestParam(name = "DataInicio", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataTransferenciaInicio,
            @RequestParam(name = "DataFim", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataTransferenciaFim,
            @RequestParam(name = "NomeOperador", required = false) String nomeOperadorTransacao) throws ParseException {

        return this.transferenciaCustomRepository.find(idConta,
                dataTransferenciaInicio,
                dataTransferenciaFim,
                nomeOperadorTransacao);

    }

}
