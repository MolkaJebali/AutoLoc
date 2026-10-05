package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Client;

import java.util.List;

public interface iClientService {
    List<Client> retrieveAllClients();
    Client addClient(Client c);
    Client updateClient(Client c);
    Client retrieveClient(Long idClient);
    void removeClient(Long idClient);
    List<Client> addClients (List<Client> clients);

}
