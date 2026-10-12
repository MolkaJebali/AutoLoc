package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.repository.EquipementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipementService implements iEquipementService {
    private final EquipementRepository equipementRepo;

    @Override
    public List<Equipement> retrieveAllEquipements() {
        return (List<Equipement>) equipementRepo.findAll();
    }

    @Override
    public Equipement addEquipement(Equipement e) {
        return equipementRepo.save(e);
    }

    @Override
    public Equipement updateEquipement(Equipement e) {
        return equipementRepo.save(e);
    }

    @Override
    public Equipement retrieveEquipement(Long idEquipement) {
        return equipementRepo.findById(idEquipement).orElse(null);
    }

    @Override
    public void removeEquipement(Long idEquipement) {
        equipementRepo.deleteById(idEquipement);
    }

    @Override
    public List<Equipement> addEquipements(List<Equipement> equipements) {
        return (List<Equipement>) equipementRepo.saveAll(equipements);
    }
}
