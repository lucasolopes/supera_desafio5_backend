package br.com.banco.domain.Transferencia;

import java.util.Date;

import br.com.banco.domain.Conta.Conta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DadosTransferencia {
    private Date dataTransferencia;
    private Double valor;
    private Tipo tipo;
    private String nomeOperadorTransacao;
    private Conta conta;

    public DadosTransferencia(Transferencia transferencia) {
        this.dataTransferencia = transferencia.getDataTransferencia();
        this.valor = transferencia.getValor();
        this.tipo = transferencia.getTipo();
        this.conta = transferencia.getContaId();
        this.nomeOperadorTransacao = transferencia.getNomeOperadorTransacao();
    }

}
