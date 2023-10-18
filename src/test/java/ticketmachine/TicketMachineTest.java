package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
		// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
		// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
		// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		// Les montants ont été correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
		// S3 : on n’imprime pas le ticket si le montant inséré est insuffisant.
	void printticketcorrectly() {
		machine.insertMoney(PRICE / 10);
		assertFalse(machine.printTicket());
	}

	@Test
		//S4 : on imprime le ticket si le montant inséré est suffisant
	void printticketcorrectammount() {
		machine.insertMoney(PRICE * 10);
		assertTrue(machine.printTicket());
	}
	//S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket

	@Test
	void balancerefreshafterprint() {
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(0, machine.getBalance());
	}

	@Test
		//S6:le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void balanceverifafterprint() {
		machine.insertMoney(PRICE);
		assertEquals(PRICE, machine.getBalance());
		machine.insertMoney(PRICE);
		assertEquals(PRICE * 2, machine.getBalance());
		machine.printTicket();
		assertEquals(PRICE, machine.getBalance());
	}

	@Test
		//S7: refund()rendcorrectement la monnaie
	void verifrefund() {
		machine.insertMoney(PRICE + 40);
		machine.printTicket();
		assertEquals(40, machine.refund());
	}

	@Test
		//S8 : refund()remet la balance à zéro
	void verifrefunresetbalance() {
		machine.insertMoney(PRICE + 40);
		machine.refund();
		assertEquals(0, machine.getBalance());
	}

	@Test
		//S9: on ne peut pas insérer un montant négatif
	void verifnegativammountinsert() {
		try {
			machine.insertMoney(-40);
			fail("Argument must not be negative");

		} catch (IllegalArgumentException e)
		{
		}
	}
	@Test
	// S10 :on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void verifpriceticket(){
		try {
			TicketMachine machine2 = new TicketMachine (-23);
			fail("Ticket price must be positive");

		} catch (IllegalArgumentException e)
		{
		}

	}
}
