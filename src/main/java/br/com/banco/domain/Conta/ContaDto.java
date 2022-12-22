package br.com.banco.domain.Conta;

import org.springframework.data.domain.Page;

import br.com.banco.domain.Transferencia.DadosTransferencia;
import br.com.banco.domain.Transferencia.Saldo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContaDto {
    private Conta conta;
    private Saldo saldos;
    private Page<DadosTransferencia> transferencias;
}
