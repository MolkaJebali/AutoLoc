package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeService implements iVehiculeService {
    private final VehiculeRepository vehiculeRepo;
    private final AgenceRepository agenceRepo;
    private final EquipementRepository equipementRepo;

    @Override
    public List<Vehicule> retrieveAllVehicules() {
        return (List<Vehicule>) vehiculeRepo.findAll();
    }

    @Override
    public Vehicule addVehicule(Vehicule v) {
        return vehiculeRepo.save(v);
    }

    @Override
    public Vehicule updateVehicule(Vehicule v) {
        return vehiculeRepo.save(v);
    }

    @Override
    public Vehicule retrieveVehicule(Long idVehicule) {
        return vehiculeRepo.findById(idVehicule).orElse(null);
    }

    @Override
    public void removeVehicule(Long idVehicule) {
        vehiculeRepo.deleteById(idVehicule);
    }

    @Override
    public List<Vehicule> addVehicules(List<Vehicule> vehicules) {
        return (List<Vehicule>) vehiculeRepo.saveAll(vehicules);
    }

    /**
     * Scénario : un véhicule neuf arrive et est rattaché à une agence (@ManyToOne).
     * Met à jour la clé étrangère id_agence dans la table vehicule.
     */
    @Override
    public Vehicule affecterVehicule(Long idVehicule, Long idAgence) {
        Vehicule vehicule = vehiculeRepo.findById(idVehicule)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable : " + idVehicule));
        Agence agence = agenceRepo.findById(idAgence)
                .orElseThrow(() -> new RuntimeException("Agence introuvable : " + idAgence));
        vehicule.setAgence(agence);
        return vehiculeRepo.save(vehicule);
    }

    /**
     * Scénario siège bébé – installation (@ManyToMany) :
     * Ajoute un équipement optionnel à la liste du véhicule
     * (insère une ligne dans la table de jointure vehicule_equipement).
     */
    @Override
    public Vehicule affecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = vehiculeRepo.findById(idVehicule)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable : " + idVehicule));
        Equipement equipement = equipementRepo.findById(idEquipement)
                .orElseThrow(() -> new RuntimeException("Équipement introuvable : " + idEquipement));
        if (!vehicule.getEquipements().contains(equipement)) {
            vehicule.getEquipements().add(equipement);
        }
        return vehiculeRepo.save(vehicule);
    }

    /**
     * Scénario siège bébé – retrait (@ManyToMany) :
     * Retire un équipement du véhicule au retour du client
     * (supprime la ligne correspondante dans vehicule_equipement).
     */
    @Override
    public Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = vehiculeRepo.findById(idVehicule)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable : " + idVehicule));
        Equipement equipement = equipementRepo.findById(idEquipement)
                .orElseThrow(() -> new RuntimeException("Équipement introuvable : " + idEquipement));
        vehicule.getEquipements().remove(equipement);
        return vehiculeRepo.save(vehicule);
    }
}
