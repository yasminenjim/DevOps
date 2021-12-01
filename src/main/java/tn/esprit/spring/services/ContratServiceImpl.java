package tn.esprit.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.repository.ContratRepository;

@Service
public class ContratServiceImpl implements IContratService {


	@Autowired
	ContratRepository contratRepository;


	public List<Contrat> getAllContrats() {
		return (List<Contrat>) contratRepository.findAll();
	}
	@Override
	public int ajouterContrat(Contrat contrat) {
		contratRepository.save(contrat);
		 return contrat.getReference();
	}
	@Override
	public void deleteContratById(int contratId) {
		if(contratRepository.existsById(contratId)) {
			Contrat contratManagedEntity = contratRepository.findById(contratId).orElseThrow(NullPointerException::new);
			contratRepository.delete(contratManagedEntity);
		}
	}

	@Override
	public Contrat getById(int id) {
		return contratRepository.findById(id).orElse(null);
	}

	@Override
	public long nombreDeContrats() {
		return contratRepository.count();
	}

	@Override
	public Contrat findContratById(int id) {
			return contratRepository.findById(id).orElseThrow(NullPointerException::new);
	}

}
