package tn.esprit.spring;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.services.IContratService;
import tn.esprit.spring.services.IEmployeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContratTest {
	@Autowired
	IContratService cs;
	@Autowired
	IEmployeService es;

	@MockBean
	private ContratRepository cr;

	private static final Logger l = LogManager.getLogger(ContratTest.class);

	@Test
	public void getAllTest() {
		l.info("entring to test getAllContrats");
		Contrat c1 = new Contrat();
		c1.setDateDebut(new Date());
		c1.setSalaire(11);
		c1.setTypeContrat("CDD");

		Contrat c2 = new Contrat();
		c2.setDateDebut(new Date());
		c2.setSalaire(12);
		c2.setTypeContrat("CDI");

		Contrat c3 = new Contrat();
		c3.setDateDebut(new Date());
		c3.setSalaire(13);
		c3.setTypeContrat("SIVP");

		when(cr.findAll()).thenReturn(Stream.of(c1, c2, c3).collect(Collectors.toList()));
		assertEquals(3, cs.getAllContrats().size());
	}

	@Test
	public void findByIdTest() {
		Contrat c1 = new Contrat();
		c1.setReference(100);
		c1.setDateDebut(new Date());
		c1.setSalaire(11);
		c1.setTypeContrat("CDD");
		when(cr.findById(100)).thenReturn(Optional.of(c1));
		assertEquals(c1, cs.findContratById(100));
	}

	@Test(timeout = 2000)
	public void addandDeleteContratTest() {

		
		/********** with mock ***********/
		Contrat c = new Contrat();
		c.setReference(24);
		c.setDateDebut(new Date());
		c.setSalaire(1000);
		c.setTypeContrat("CDI");

		when(cr.save(c)).thenReturn(c);
		l.info("affichage c: " + c);
		assertEquals(c.getReference(), cs.ajouterContrat(c));
		l.info("test add contrat success");
		
	}

	@Test
	public void affectEmplToContrat() {

		Contrat c = new Contrat();
		c.setReference(250);
		c.setDateDebut(new Date());
		c.setSalaire(100);
		c.setTypeContrat("CDD");
		when(cr.findById(250)).thenReturn(Optional.of(c));
		List<Employe> allEmloyes = es.getAllEmployes();
		c = es.affecterContratAEmploye(c.getReference(), allEmloyes.get(0).getId());
		assertEquals(c.getEmploye().getId(), cs.getById(c.getReference()).getEmploye().getId());
	}
}


/*test push for jenkins*/

