package tn.esprit.autoloc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.Reservation;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    // ==========================================
    // Partie V – Dates, valeurs nulles et booléens
    // ==========================================

    // Scénario 14 : Rechercher les réservations dont la date de début est postérieure à une date donnée
    List<Reservation> findByDateDebutAfter(LocalDate dateDebut);

    // Variante équivalente avec GreaterThan
    List<Reservation> findByDateDebutGreaterThan(LocalDate dateDebut);
}
