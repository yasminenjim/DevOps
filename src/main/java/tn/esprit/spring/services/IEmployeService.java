package tn.esprit.spring.services;

import java.util.Date;
import java.util.List;


import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;

public interface IEmployeService {
	
	public Employe authenticate(String login, String password) ;
	public String mettreAjourEmailByEmployeId(String email, int employeId);
	int addOrUpdateEmploye(Employe employe);
	public String getEmployePrenomById(int employeId);
	public String deleteEmploye(int id) ;
	public String mettreAjourEmailByEmployeIdJPQL(String email, int employeId);
	public List<Employe> getAllEmployes();
	public int getNombreEmployeJPQL();
	public List<String> getAllEmployeNamesJPQL();
	public float getSalaireByEmployeIdJPQL(int employeId);
	//Fin wael
	
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise);
	public void deleteAllContratJPQL();
	public Double getSalaireMoyenByDepartementId(int departementId);
	public void deleteContratById(int contratId);
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, 
	Date dateDebut, Date dateFin);
	public void desaffecterEmployeDuDepartement(int employeId, int depId);
	public int ajouterContrat(Contrat contrat);
	public Contrat affecterContratAEmploye(int contratId, int employeId);
	public void affecterEmployeADepartement(int employeId, int depId);
	
	
	
	
	

	
}
