package br.com.banco.domain.Transferencia;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.banco.domain.Conta.Conta;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "TRANSFERENCIA")
@Entity(name = "TRANSFERENCIA")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transferencia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = true)
	private Long id;
	@Column(name = "DATA_TRANSFERENCIA", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataTransferencia;
	@Column(name = "VALOR", nullable = true, precision = 10, scale = 2)
	private Double valor;
	@Column(name = "TIPO", length = 15, nullable = true)
	@Enumerated(EnumType.STRING)
	private Tipo tipo;
	@Column(name = "NOME_OPERADOR_TRANSACAO", length = 50, nullable = true)
	private String nomeOperadorTransacao;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CONTA_ID", referencedColumnName = "ID_CONTA")
	private Conta contaId;

}
