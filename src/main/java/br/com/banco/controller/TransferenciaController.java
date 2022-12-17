package br.com.banco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.domain.Transferencia.Transferencia;
import br.com.banco.domain.Transferencia.TransferenciaRepository;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {
	@Autowired
	TransferenciaRepository transferenciaRepository;

	@GetMapping
	public ResponseEntity<Transferencia> listar() {
		Transferencia findById = transferenciaRepository.findById(5l).get();
		return ResponseEntity.ok().body(findById);
	}
}
