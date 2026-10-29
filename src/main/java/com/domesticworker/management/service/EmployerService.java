
package com.domesticworker.management.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domesticworker.management.repository.WorkerRepository;

import com.domesticworker.management.domain.Employer;
import com.domesticworker.management.repository.EmployerRepository;

@Service
public class EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
private WorkerRepository workerRepository;

public boolean workerExists(UUID workerId) {
    return workerRepository.existsById(workerId);
}

    // CREATE
    public Employer saveEmployer(Employer employer) {
        return employerRepository.save(employer);
    }

    // READ ALL
    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    // READ ONE
    public Optional<Employer> getEmployerById(UUID id) {
        return employerRepository.findById(id);
    }

    // UPDATE
    public Employer updateEmployer(UUID id, Employer employer) {
        Optional<Employer> existingEmployer =
                employerRepository.findById(id);

        if (existingEmployer.isPresent()) {
            Employer e = existingEmployer.get();

            e.setFirstName(employer.getFirstName());
            e.setLastName(employer.getLastName());
            e.setNationalId(employer.getNationalId());
            e.setPhone(employer.getPhone());
            e.setAddress(employer.getAddress());

            return employerRepository.save(e);
        }

        return null;
    }

    // DELETE
    public String deleteEmployer(UUID id) {
        if (employerRepository.existsById(id)) {
            employerRepository.deleteById(id);
            return "Employer deleted successfully";
        }

        return "The employer with that id does not exist";
    }

    //B1

    public List<Employer> findByLastName(String lastName) {
    return employerRepository
            .findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
}
//C4

public List<Employer> findEmployersByWorkerId(UUID workerId) {
    return employerRepository.findEmployersByWorkerId(workerId);
}
}
