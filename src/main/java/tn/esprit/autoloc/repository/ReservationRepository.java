package tn.esprit.autoloc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // ==========================================
    // Atelier JPQL – Jointures & Fonctions d'agrégation
    // ==========================================

    // Afficher les réservations effectuées par un client
    @Query("SELECT r FROM Reservation r WHERE r.client.idClient = :idClient")
    List<Reservation> getReservationsByClient(@Param("idClient") Long idClient);

    // Compter le nombre de réservations effectuées par chaque client
    @Query("SELECT r.client.nom, count(r) FROM Reservation r GROUP BY r.client.nom")
    List<Object[]> countReservationsByClient();
}
