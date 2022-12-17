package br.com.banco.domain.Transferencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

    @Query(value = "SELECT * FROM TRANSFERENCIA u WHERE u.CONTA_ID = ?1", nativeQuery = true)
    Page<Transferencia> findByContaBancaria(Long l, Pageable pageable);

}
