package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.ContratRepository;

import java.util.List;

public class ContratService implements iContratService {
    ContratRepository contratRepo;

    @Override
    public List<Contrat> retrieveAllContrats() {
        return (List<Contrat>) contratRepo.findAll();
    }

    @Override
    public Contrat addContrat(Contrat c) {
        return contratRepo.save(c);
    }

    @Override
    public Contrat updateContrat(Contrat c) {
        return contratRepo.save(c);
    }

    @Override
    public Contrat retrieveContrat(Long idContrat) {
        return contratRepo.findById(idContrat).orElse(null);
    }

    @Override
    public void removeContrat(Long idContrat) {
        contratRepo.deleteById(idContrat);
    }

    @Override
    public List<Contrat> addContrats(List<Contrat> contrats) {
        return (List<Contrat>) contratRepo.saveAll(contrats);
    }
}
