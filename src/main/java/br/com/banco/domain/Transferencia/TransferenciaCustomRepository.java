package br.com.banco.domain.Transferencia;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

@Repository
public class TransferenciaCustomRepository {

    private final EntityManager em;

    public TransferenciaCustomRepository(EntityManager em) {
        this.em = em;
    }

    public List<Transferencia> find(Long idConta, Date dataTransferenciaInicio, Date dataTransferenciaFim,
            String nomeOperadorTransacao) {

        String dataTransferenciaInicioFormatada;
        String dataTransferenciaFimFormatada;

        String query = "select * from TRANSFERENCIA as P";
        String condicao = " where ";

        if (idConta != null) {
            query += condicao + "P.CONTA_ID = " + idConta;
            condicao = " and ";
        }

        if (dataTransferenciaInicio != null && dataTransferenciaFim == null) {

            dataTransferenciaInicioFormatada = formatarData(dataTransferenciaInicio);
            query += condicao + " P.DATA_TRANSFERENCIA >= " + "'" + dataTransferenciaInicioFormatada + "'";
            condicao = " and ";

            dataTransferenciaFim = adicionarDia(dataTransferenciaInicio);
            dataTransferenciaFimFormatada = formatarData(dataTransferenciaFim);

            query += condicao + " P.DATA_TRANSFERENCIA <" + "'" + dataTransferenciaFimFormatada + "'";

        }
        if (dataTransferenciaInicio != null && dataTransferenciaFim != null) {
            dataTransferenciaInicioFormatada = formatarData(dataTransferenciaInicio);

            dataTransferenciaFim = adicionarDia(dataTransferenciaFim);
            dataTransferenciaFimFormatada = formatarData(dataTransferenciaFim);

            query += condicao + " P.DATA_TRANSFERENCIA >= " + "'" + dataTransferenciaInicioFormatada + "'";
            condicao = " and ";
            query += condicao + " P.DATA_TRANSFERENCIA <" + "'" + dataTransferenciaFimFormatada + "'";

        }

        if (nomeOperadorTransacao != null) {
            query += condicao + " P.NOME_OPERADOR_TRANSACAO =" + "'" + nomeOperadorTransacao + "'";
        }

        var q = em.createNativeQuery(query, Transferencia.class);

        return q.getResultList();
    }

    private Date adicionarDia(Date dt) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    private String formatarData(Date data) {
        data.setHours(0);
        data.setMinutes(0);
        data.setSeconds(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss+SS");

        return sdf.format(data);
    }
}
