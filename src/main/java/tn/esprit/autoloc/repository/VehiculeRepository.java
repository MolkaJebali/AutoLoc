package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    Optional<Vehicule> findByImmatriculation(String immatriculation);

    // ==========================================
    // Partie I – Recherches simples
    // ==========================================

    // Scénario 1 : Rechercher tous les véhicules ayant une marque précise
    List<Vehicule> findByMarque(String marque);

    // Scénario 2 : Rechercher tous les véhicules d'une catégorie donnée
    List<Vehicule> findByCategorie(CategorieVehicule categorie);

    // Scénario 3 : Rechercher les véhicules ayant un statut DISPONIBLE
    List<Vehicule> findByStatut(StatutVehicule statut);

    // ==========================================
    // Partie II – Recherches partielles
    // ==========================================

    // Scénario 5 : Rechercher les véhicules dont la marque contient une chaîne saisie
    List<Vehicule> findByMarqueContaining(String marque);

    // Variante ignorant la casse
    List<Vehicule> findByMarqueContainingIgnoreCase(String marque);

    // ==========================================
    // Partie III – Comparaisons numériques
    // ==========================================

    // Scénario 8 : Rechercher les véhicules dont le tarif est supérieur à un montant donné (ex: 100 DT)
    List<Vehicule> findByTarifJournalierGreaterThan(BigDecimal tarifJournalier);

    // Variante Scénario 8 avec @Query spécifique pour > 100 DT
    @Query("SELECT v FROM Vehicule v WHERE v.tarifJournalier > 100")
    List<Vehicule> findVehiculesTarifSuperieurA100();

    // ==========================================
    // Partie IV – Combinaison de plusieurs critères
    // ==========================================

    // Scénario 11 : Rechercher les véhicules d'une marque donnée ET ayant le statut DISPONIBLE
    List<Vehicule> findByMarqueAndStatut(String marque, StatutVehicule statut);
}



