package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EmployeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeService implements iEmployeService {
    private final EmployeRepository employeRepo;
    private final AgenceRepository agenceRepo;

    @Override
    public List<Employe> retrieveAllEmployes() {
        return (List<Employe>) employeRepo.findAll();
    }

    @Override
    public Employe addEmploye(Employe e) {
        return employeRepo.save(e);
    }

    @Override
    public Employe updateEmploye(Employe e) {
        return employeRepo.save(e);
    }

    @Override
    public Employe retrieveEmploye(Long idEmploye) {
        return employeRepo.findById(idEmploye).orElse(null);
    }

    @Override
    public void removeEmploye(Long idEmploye) {
        employeRepo.deleteById(idEmploye);
    }

    @Override
    public List<Employe> addEmployes(List<Employe> employes) {
        return (List<Employe>) employeRepo.saveAll(employes);
    }

    /**
     * Scénario RH – étape 1 : désaffecter un employé de son agence courante.
     * L'employé ne peut appartenir qu'à une seule agence à la fois ; avant toute
     * mutation, le service RH commence par retirer le lien agence (ManyToOne → null).
     */
    @Override
    public Employe desaffecterEmploye(Long idEmploye) {
        Employe employe = employeRepo.findById(idEmploye)
                .orElseThrow(() -> new RuntimeException("Employé introuvable : " + idEmploye));
        employe.setAgence(null);
        return employeRepo.save(employe);
    }

    /**
     * Scénario RH – étape 2 : affecter un employé à une nouvelle agence.
     * Cette méthode suppose que l'employé a d'abord été désaffecté
     * (ou qu'il n'appartient à aucune agence).
     */
    @Override
    public Employe affecterEmploye(Long idEmploye, Long idAgence) {
        Employe employe = employeRepo.findById(idEmploye)
                .orElseThrow(() -> new RuntimeException("Employé introuvable : " + idEmploye));
        Agence agence = agenceRepo.findById(idAgence)
                .orElseThrow(() -> new RuntimeException("Agence introuvable : " + idAgence));
        employe.setAgence(agence);
        return employeRepo.save(employe);
    }
}
