package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.iClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Clients", description = "API de gestion des clients (opérations CRUD)")
public class ClientRestController {

    private final iClientService clientService;

    @Operation(summary = "Récupérer tous les clients", description = "Retourne la liste de tous les clients enregistrés dans la plateforme.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des clients récupérée avec succès")
    })
    @GetMapping({"", "/retrieve-all-clients"})
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.retrieveAllClients();
        return ResponseEntity.ok(clients);
    }

    @Operation(summary = "Récupérer un client par son ID", description = "Retourne les informations d'un client à partir de son identifiant unique.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client trouvé avec succès"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    @GetMapping({"/{id}", "/retrieve-client/{id}"})
    public ResponseEntity<Client> getClientById(
            @Parameter(description = "Identifiant unique du client", required = true, example = "1")
            @PathVariable("id") Long id) {
        Client c = clientService.retrieveClient(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(c);
    }

    @Operation(summary = "Ajouter un nouveau client", description = "Enregistre un nouveau client dans le système.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données du client invalides")
    })
    @PostMapping({"", "/add-client"})
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        Client saved = clientService.addClient(client);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Ajouter une liste de clients", description = "Enregistre plusieurs clients en une seule opération (création en lot).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Clients créés avec succès")
    })
    @PostMapping({"/batch", "/add-clients"})
    public ResponseEntity<List<Client>> addClients(@RequestBody List<Client> clients) {
        List<Client> savedList = clientService.addClients(clients);
        return new ResponseEntity<>(savedList, HttpStatus.CREATED);
    }

    @Operation(summary = "Mettre à jour un client existant", description = "Modifie les coordonnées ou informations d'un client existant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé pour mise à jour")
    })
    @PutMapping({"", "/update-client"})
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        Client updated = clientService.updateClient(client);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Mettre à jour un client par son ID", description = "Modifie un client spécifié par son identifiant unique dans le chemin.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClientById(
            @Parameter(description = "Identifiant du client à mettre à jour", required = true, example = "1")
            @PathVariable("id") Long id,
            @RequestBody Client client) {
        client.setIdClient(id);
        Client updated = clientService.updateClient(client);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Supprimer un client par ID", description = "Supprime un client du système à partir de son identifiant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    @DeleteMapping({"/{id}", "/remove-client/{id}"})
    public ResponseEntity<Void> deleteClient(
            @Parameter(description = "Identifiant unique du client à supprimer", required = true, example = "1")
            @PathVariable("id") Long id) {
        Client c = clientService.retrieveClient(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        clientService.removeClient(id);
        return ResponseEntity.noContent().build();
    }
}
