package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.ClientRepository;

import java.util.List;

public class ClientService implements iClientService{
    ClientRepository clRepo;
    @Override
    public List<Client> retrieveAllClients() {
        return (List<Client>) clRepo.findAll();
    }

    @Override
    public Client addClient(Client c) {
        return clRepo.save(c);
    }

    @Override
    public Client updateClient(Client c) {
        return clRepo.save(c);
    }

    @Override
    public Client retrieveClient(Long idClient) {

        return clRepo.findById(idClient).orElse(null);

    }

    @Override
    public void removeClient(Long idClient) {
        clRepo.deleteById(idClient);

    }

    @Override
    public List<Client> addClients(List<Client> clients) {
        return (List<Client>) clRepo.saveAll(clients);
    }
}