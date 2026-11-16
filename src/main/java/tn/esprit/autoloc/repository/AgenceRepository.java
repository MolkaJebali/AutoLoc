package tn.esprit.autoloc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.Agence;

import java.util.List;

@Repository
public interface AgenceRepository extends CrudRepository<Agence, Long> {

    // ==========================================
    // Partie I – Recherches simples
    // ==========================================

    // Scénario 4 : Rechercher les agences situées dans une ville donnée
    List<Agence> findByVille(String ville);
}
