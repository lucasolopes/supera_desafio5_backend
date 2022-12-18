package br.com.banco.domain.Transferencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.banco.domain.Conta.Conta;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DadosTransferencia {
    private Date dataTransferencia;
    private Double valor;
    private Tipo tipo;
    private String nomeOperadorTransacao;
    private Conta conta;

    public DadosTransferencia(Transferencia transferencia) {
        this.dataTransferencia = formatarData(transferencia.getDataTransferencia());
        this.valor = transferencia.getValor();
        this.tipo = transferencia.getTipo();
        this.conta = transferencia.getContaId();
        this.nomeOperadorTransacao = transferencia.getNomeOperadorTransacao();
    }

    private Date formatarData(Date data) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String stringDataFormatada = sdf.format(data);
        try {
            Date dataFormatada = sdf.parse(stringDataFormatada);
            return dataFormatada;
        } catch (ParseException e) {
            return data;
        }
    }

}
