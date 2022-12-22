package br.com.banco.domain.Transferencia;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Saldo {
    private BigDecimal saldoTotal;
    private BigDecimal saldoPeriodo;
}
