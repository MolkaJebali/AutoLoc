package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.ReservationRepository;

import java.util.List;

public class ReservationService implements iReservationService {
    ReservationRepository reservationRepo;

    @Override
    public List<Reservation> retrieveAllReservations() {
        return (List<Reservation>) reservationRepo.findAll();
    }

    @Override
    public Reservation addReservation(Reservation r) {
        return reservationRepo.save(r);
    }

    @Override
    public Reservation updateReservation(Reservation r) {
        return reservationRepo.save(r);
    }

    @Override
    public Reservation retrieveReservation(Long idReservation) {
        return reservationRepo.findById(idReservation).orElse(null);
    }

    @Override
    public void removeReservation(Long idReservation) {
        reservationRepo.deleteById(idReservation);
    }

    @Override
    public List<Reservation> addReservations(List<Reservation> reservations) {
        return (List<Reservation>) reservationRepo.saveAll(reservations);
    }
}
