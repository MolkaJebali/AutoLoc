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
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.iAgenceService;

import java.util.List;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Agences", description = "API de gestion des agences de location (opérations CRUD)")
public class AgenceRestController {

    private final iAgenceService agenceService;

    @Operation(summary = "Récupérer toutes les agences", description = "Retourne la liste de l'ensemble des agences de location enregistrées.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des agences récupérée avec succès")
    })
    @GetMapping({"", "/retrieve-all-agences"})
    public ResponseEntity<List<Agence>> getAllAgences() {
        List<Agence> agences = agenceService.retrieveAllAgences();
        return ResponseEntity.ok(agences);
    }

    @Operation(summary = "Récupérer une agence par son ID", description = "Retourne les détails complets d'une agence selon son identifiant unique.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agence trouvée avec succès"),
            @ApiResponse(responseCode = "404", description = "Agence non trouvée")
    })
    @GetMapping({"/{id}", "/retrieve-agence/{id}"})
    public ResponseEntity<Agence> getAgenceById(
            @Parameter(description = "Identifiant unique de l'agence", required = true, example = "1")
            @PathVariable("id") Long id) {
        Agence a = agenceService.retrieveAgence(id);
        if (a == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(a);
    }

    @Operation(summary = "Ajouter une nouvelle agence", description = "Enregistre une nouvelle agence dans le système.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agence créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données d'agence invalides")
    })
    @PostMapping({"", "/add-agence"})
    public ResponseEntity<Agence> addAgence(@RequestBody Agence agence) {
        Agence saved = agenceService.addAgence(agence);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Ajouter une liste d'agences", description = "Enregistre plusieurs agences en une seule opération (création en lot).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agences créées avec succès")
    })
    @PostMapping({"/batch", "/add-agences"})
    public ResponseEntity<List<Agence>> addAgences(@RequestBody List<Agence> agences) {
        List<Agence> savedList = agenceService.addAgences(agences);
        return new ResponseEntity<>(savedList, HttpStatus.CREATED);
    }

    @Operation(summary = "Mettre à jour une agence existante", description = "Modifie les informations d'une agence existante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agence mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Agence non trouvée pour la mise à jour")
    })
    @PutMapping({"", "/update-agence"})
    public ResponseEntity<Agence> updateAgence(@RequestBody Agence agence) {
        Agence updated = agenceService.updateAgence(agence);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Mettre à jour une agence par son ID", description = "Modifie une agence spécifiée par son identifiant unique dans le chemin.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agence mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Agence non trouvée")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Agence> updateAgenceById(
            @Parameter(description = "Identifiant de l'agence à mettre à jour", required = true, example = "1")
            @PathVariable("id") Long id,
            @RequestBody Agence agence) {
        agence.setIdAgence(id);
        Agence updated = agenceService.updateAgence(agence);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Supprimer une agence par ID", description = "Supprime définitivement une agence de location par son identifiant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agence supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Agence non trouvée")
    })
    @DeleteMapping({"/{id}", "/remove-agence/{id}"})
    public ResponseEntity<Void> deleteAgence(
            @Parameter(description = "Identifiant unique de l'agence à supprimer", required = true, example = "1")
            @PathVariable("id") Long id) {
        Agence a = agenceService.retrieveAgence(id);
        if (a == null) {
            return ResponseEntity.notFound().build();
        }
        agenceService.removeAgence(id);
        return ResponseEntity.noContent().build();
    }
}
