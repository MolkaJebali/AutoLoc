package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.MaintenanceRepository;

import java.util.List;

public class MaintenanceService implements iMaintenanceService {
    MaintenanceRepository maintenanceRepo;

    @Override
    public List<Maintenance> retrieveAllMaintenances() {
        return (List<Maintenance>) maintenanceRepo.findAll();
    }

    @Override
    public Maintenance addMaintenance(Maintenance m) {
        return maintenanceRepo.save(m);
    }

    @Override
    public Maintenance updateMaintenance(Maintenance m) {
        return maintenanceRepo.save(m);
    }

    @Override
    public Maintenance retrieveMaintenance(Long idMaintenance) {
        return maintenanceRepo.findById(idMaintenance).orElse(null);
    }

    @Override
    public void removeMaintenance(Long idMaintenance) {
        maintenanceRepo.deleteById(idMaintenance);
    }

    @Override
    public List<Maintenance> addMaintenances(List<Maintenance> maintenances) {
        return (List<Maintenance>) maintenanceRepo.saveAll(maintenances);
    }
}
