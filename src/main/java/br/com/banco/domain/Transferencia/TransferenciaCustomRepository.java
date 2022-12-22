package br.com.banco.domain.Transferencia;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class TransferenciaCustomRepository {

    private final EntityManager em;

    public TransferenciaCustomRepository(EntityManager em) {
        this.em = em;
    }

    public Saldo getSaldoByNumConta(Long idConta, Date dataTransferenciaInicio, Date dataTransferenciaFim) {
        String query = "select SUM(VALOR) as saldoTotal from TRANSFERENCIA as P where P.CONTA_ID = " + idConta;
        String condicao = "and";
        String querySaldoPeriodo = query + condicao;

        String dataTransferenciaInicioFormatada;
        String dataTransferenciaFimFormatada;

        BigDecimal saldoPeriodo = BigDecimal.ZERO;
        BigDecimal saldoTotal = BigDecimal.ZERO;

        Query createSaldoTotalNativeQuery = em.createNativeQuery(query);
        saldoTotal = (BigDecimal) createSaldoTotalNativeQuery.getSingleResult();
        saldoPeriodo = (BigDecimal) createSaldoTotalNativeQuery.getSingleResult();
        if (dataTransferenciaInicio != null && dataTransferenciaFim == null) {
            dataTransferenciaInicioFormatada = formatarData(dataTransferenciaInicio);

            querySaldoPeriodo += " P.DATA_TRANSFERENCIA >=" + "'" + dataTransferenciaInicioFormatada + "'";

            dataTransferenciaFim = adicionarDia(dataTransferenciaInicio);
            dataTransferenciaFimFormatada = formatarData(dataTransferenciaFim);
            querySaldoPeriodo += condicao + " P.DATA_TRANSFERENCIA <" + "'" + dataTransferenciaFimFormatada + "'";
            dataTransferenciaFim = null;
            System.out.println(querySaldoPeriodo);
            Query createSaltoPeriodoNativeQuery = em.createNativeQuery(querySaldoPeriodo);
            saldoPeriodo = (BigDecimal) createSaltoPeriodoNativeQuery.getSingleResult();
        }
        if (dataTransferenciaInicio != null && dataTransferenciaFim != null) {
            dataTransferenciaInicioFormatada = formatarData(dataTransferenciaInicio);

            querySaldoPeriodo += " P.DATA_TRANSFERENCIA >=" + "'" + dataTransferenciaInicioFormatada + "'";

            dataTransferenciaFimFormatada = formatarData(dataTransferenciaFim);
            querySaldoPeriodo += condicao + " P.DATA_TRANSFERENCIA <" + "'" + dataTransferenciaFimFormatada + "'";
            dataTransferenciaFim = null;
            System.out.println(querySaldoPeriodo);
            Query createSaltoPeriodoNativeQuery = em.createNativeQuery(querySaldoPeriodo);
            saldoPeriodo = (BigDecimal) createSaltoPeriodoNativeQuery.getSingleResult();
        }

        Saldo saldos = new Saldo(saldoTotal, saldoPeriodo);

        return saldos;

    }

    public Page<Transferencia> find(Long idConta, Date dataTransferenciaInicio, Date dataTransferenciaFim,
            String nomeOperadorTransacao, Pageable pageable) {

        String dataTransferenciaInicioFormatada;
        String dataTransferenciaFimFormatada;

        String query = "select * from TRANSFERENCIA as P";
        String condicao = " where ";

        query += condicao + "P.CONTA_ID = " + idConta;
        condicao = " and ";

        if (dataTransferenciaInicio != null && dataTransferenciaFim == null) {

            dataTransferenciaInicioFormatada = formatarData(dataTransferenciaInicio);
            query += condicao + " P.DATA_TRANSFERENCIA >= " + "'" + dataTransferenciaInicioFormatada + "'";
            condicao = " and ";

            dataTransferenciaFim = adicionarDia(dataTransferenciaInicio);
            dataTransferenciaFimFormatada = formatarData(dataTransferenciaFim);

            query += condicao + " P.DATA_TRANSFERENCIA <" + "'" + dataTransferenciaFimFormatada + "'";
            dataTransferenciaFim = null;
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
        //////////////////////////////////////////////////////////////////////////////////////////
        Query queryResultado = em.createNativeQuery(query, Transferencia.class);

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        queryResultado.setFirstResult((pageNumber) * pageSize);
        queryResultado.setMaxResults(pageSize);
        System.out.println(query);
        List<Transferencia> fooList = queryResultado.getResultList();
        /////////////////////////////////////////////////////////////////////////////////////
        Query queryTotal = em.createQuery("Select count(P.id) From TRANSFERENCIA P");

        long countResult1 = (long) queryTotal.getSingleResult();
        int i1 = (int) countResult1;
        ////////////////////////////////////////////////////////////////////////////////////////////
        return new PageImpl<>(fooList, pageable, i1);

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
