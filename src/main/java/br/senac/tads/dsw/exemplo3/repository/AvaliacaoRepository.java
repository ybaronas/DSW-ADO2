package br.senac.tads.dsw.exemplo3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.tads.dsw.exemplo3.model.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

}
