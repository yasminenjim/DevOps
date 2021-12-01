package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {
	private static final Logger l = Logger.getLogger(EmployeServiceImpl.class);
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	@Override
	public Employe authenticate(String login, String password) {
		return employeRepository.getEmployeByEmailAndPassword(login, password);
	}

	
	//----------------------------------------------------------------------------------------------------------------------------------
		//Debut wael
	@Override
	public int addOrUpdateEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}

	public String mettreAjourEmailByEmployeId(String email, int employeId) {
		String msg="";
		Employe x = new Employe ();
		try {
		l.info("employe existe");	
		l.debug("mis a jour mail");
		Optional<Employe> y = employeRepository.findById(employeId) ;
		if (y.isPresent())
		{
			x = y.get();
		}
		x.setEmail(email);
		l.info("mis a jour mail avec Succès");
		msg="success";
	
		employeRepository.save(x);
		l.info("mis a jour sans erreur");
		}catch (Exception e) {
			l.error("Erreur avec la  mis a jour   email " +e);
			msg="erreur";
		}
		return msg;
	}
	
	public List<Employe> getAllEmployes() {
		
		List<Employe> employes = null; 
		try {
	
			
			l.info("In Method getAllEmployes");
			employes = (List<Employe>) employeRepository.findAll();
				 l.info("Employes");
					l.debug("Connexion Bd");
					
					for (Employe employe : employes) {
						l.info("Employe "+employe.getNom());
					} 
					l.info("out with succes");

				}catch (Exception e) {
					l.error("out of method with error"+e);		}

				return employes;
			}
	
	public String getEmployePrenomById(int employeId) {
		
		Employe x = new Employe();
		try{
			l.info("affichage d'une employe par id : "+employeId);
			l.debug("entrain d'afficher employe ... ");
			
			
			Optional<Employe> y = employeRepository.findById(employeId) ;
			if (y.isPresent())
			{
				x = y.get();
			}
								
			l.debug("je viens d'afficher employe: ");
			l.info("affichage sans erreurs " );
		}
		catch(Exception e){
			l.error("Erreur dans l'affichage de employe: "+e);
		}finally{
			l.info("Methode affichage");
	
		
		
	
		}
	return x.getPrenom();
}

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}

	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}
	public String mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		String msg="valider";
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);
       return msg;
	}
	@Override
	public String deleteEmploye(int id) {
		String msg="";
		try{
			l.info("Finding Employe with id = %d"+id);
			employeRepository.deleteById(id);
			l.info("Employe Deleted Successfuly ");
			msg="emloyé supprimé";
		}catch (Exception e) {

			l.error("The emp with id = %d does not Exist"+id);
			msg="error";
		}
   return msg;
	}
	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}
		//fin wael
	//----------------------------------------------------------------------------------------------------------------------------------
	
	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		Optional<Departement> depManagedEntity = deptRepoistory.findById(depId);
		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
		
		if (depManagedEntity.isPresent() &&  employeManagedEntity.isPresent()) {
			
		

		if(depManagedEntity.get().getEmployes() == null){

			List<Employe> employes = new ArrayList<>();
			employes.add(employeManagedEntity.get());
			depManagedEntity.get().setEmployes(employes);
		}else{

			depManagedEntity.get().getEmployes().add(employeManagedEntity.get());
		}

		// à ajouter? 
		deptRepoistory.save(depManagedEntity.get()); }

	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		Optional<Departement> dep = deptRepoistory.findById(depId);
		if (dep.isPresent()) {
			
			int employeNb = dep.get().getEmployes().size();
			for(int index = 0; index < employeNb; index++){
				if(dep.get().getEmployes().get(index).getId() == employeId){
					dep.get().getEmployes().remove(index);
					break;//a revoir
				}
			}
		}
		
	} 
	
	// Tablesapce (espace disque) 

	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}

	public Contrat affecterContratAEmploye(int contratId, int employeId) {
		Optional<Contrat> contratManagedEntity = contratRepoistory.findById(contratId);
		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
		if (contratManagedEntity.isPresent() && employeManagedEntity.isPresent()) {
			contratManagedEntity.get().setEmploye(employeManagedEntity.get());
			contratRepoistory.save(contratManagedEntity.get());
			return contratManagedEntity.get();
		}
		return null;
		

	}
	 


	public void deleteContratById(int contratId) {
		Optional<Contrat> contratManagedEntity = contratRepoistory.findById(contratId);
		if (contratManagedEntity.isPresent()) {
			contratRepoistory.delete(contratManagedEntity.get());
		}

	}

	

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	
	public void deleteAllContratJPQL() {
		employeRepository.deleteAllContratJPQL();
	}



	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}
	
}

