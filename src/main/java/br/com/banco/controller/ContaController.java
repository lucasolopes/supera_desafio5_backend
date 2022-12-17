package br.com.banco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.domain.Conta.Conta;
import br.com.banco.domain.Conta.ContaRepository;

@RestController
@RequestMapping("/contas")
public class ContaController {

	@Autowired
	ContaRepository contaRepository;

	@GetMapping
	public ResponseEntity<Conta> listar() {
		Conta findById = contaRepository.findById(1l).get();
		return ResponseEntity.ok().body(findById);
	}

}
