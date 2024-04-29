package Progettino;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class Giochiamo {
	static String giocatore = null;
	static String avversario=null;
	static String scelta = null;
	static boolean mattina;
	static int anno;
	static int mese;
	static int giorno;
	static boolean isVivo;
	static Eroe eroe = new Eroe();
	static Licantropo lica = new Licantropo();
	static Vampiro vampi = new Vampiro();	
	
	public static void main (String [] args)  { 
	
		//Data e Ora Correnti
		LocalDateTime dataOra = LocalDateTime.now();
		String data = dataOra.getDayOfMonth() + "/" + dataOra.getMonthValue() + "/" + dataOra.getYear();
		anno = dataOra.getYear();
		mese = dataOra.getMonthValue();
		giorno = dataOra.getDayOfMonth();
		
		//Stabilire se è giorno o notte
		if (dataOra.getHour() >= 7 && dataOra.getHour() <= 19) {
			mattina = true;
			System.out.println("La battaglia inizia di giorno");			
		} else {
			mattina = false;
			System.out.println("La battaglia inizia di notte");
			if (faseLunare(anno, mese, giorno)<4) {
				System.out.println("La luna è calante");
			} else if (Math.abs(faseLunare(anno, mese, giorno) - 4) > 2){
				System.out.println("La luna è piena");
			} else {
				System.out.println("La luna è crescente");
			}
		}
		
		try (Scanner sceltaAzione = new Scanner(System.in)){
			while(true)
			{
				System.out.println("\nINIZIA UNA NUOVA MANCHE DEL GIOCO");
				System.out.println("Il giorno di inizio del combattimento è il " + data);
				//chiama la funzione per scegliere il primo combattente
				scegliGiocatore();
				
				//Viene chiesto di scegliere l'azione da compiere
				System.out.println("Cosa vuoi fare?\n	1 - Attaccare\n	2 - Recuperare le forze\n	3 - Uscire");
				scelta = sceltaAzione.nextLine();
				switch(scelta) {
					case "1":
						;
						//controlla se il giocatore è ancora vivo, altrimenti ne viene scelto un altro
						if (verificaVita() == false) {
							System.out.println("Non hai abbastanza vita per attaccare");
						} else {
					 		//Viene chiesto di scegliere il secondo combattente tra i possibili avversari
					 		System.out.println("Con che personaggio vuoi giocare?");
					 		if (giocatore == "Eroe") {
					 			System.out.println("[l=licantropo, v=vampiro]");
					 		} else 	if (giocatore == "Licantropo") {
					 			System.out.println("[e=eroe, v=vampiro]");
					 		} else 	if (giocatore == "Vampiro") {
					 			System.out.println("[e=eroe, l=licantropo]");
					 		}
					 		//inizia l'attacco in dipendenza dei due rivali
					 		scelta = sceltaAzione.nextLine();
							switch(scelta) {
						 		case "e":
						 			if (giocatore.equals("Vampiro")){
						 				vampi.attacca(eroe);
						 			} else if (giocatore.equals("Licantropo")) {
						 				lica.attacca(eroe);
						 			}
						 			avversario = "Eroe";
						 			break;
						 		case "l":
						 			if (giocatore.equals("Vampiro")){
						 				vampi.attacca(lica);
						 			} else if (giocatore.equals("Eroe")) {
						 				eroe.attacca(lica);
						 			}
						 			avversario = "Licantropo";
						 			break;
						 		case "v":
						 			if (giocatore.equals("Eroe")){
						 				eroe.attacca(vampi);
						 			} else if (giocatore.equals("Licantropo")) {
						 				lica.attacca(vampi);
						 			}
						 			avversario = "Vampiro";
						 			break;
						 		default:
						 			System.out.println("Scelta non prevista");
						 			break;
						 		}
							System.out.println("Il combattimento è tra " + giocatore + " e " + avversario);
						}
						//riepilogo della durata della battaglia
						Random oreBat= new Random(); 
						int oreBattaglia = oreBat.nextInt(100);
						dataOra = dataOra.plusHours(oreBattaglia);
						data = dataOra.getDayOfMonth() + "/" + dataOra.getMonthValue() + "/" + dataOra.getYear();
						System.out.println("Il giorno di fine del combattimento è il " + data);
						anno = dataOra.getYear();
						mese = dataOra.getMonthValue();
						giorno = dataOra.getDayOfMonth();
						//Stabilire se è giorno o notte
						if (dataOra.getHour() >= 7 && dataOra.getHour() <= 19) {
							mattina = true;
							System.out.println("La battaglia termina di giorno");			
						} else {
							mattina = false;
							System.out.println("La battaglia termina di notte");
							if (faseLunare(anno, mese, giorno)<3) {
								System.out.println("La luna è calante");
							//} else if (faseLunare(anno, mese, giorno)==4){
							} else if (Math.abs(faseLunare(anno, mese, giorno) - 4) < 2){
								System.out.println("La luna è piena");
							} else {
								System.out.println("La luna è crescente");
							}
						}
						break;
					case "2":
						//il giocatore sceglie di recuperare le forze
						if (giocatore.equals("Eroe")){
							eroe.recuperaForza();
						} else if (giocatore.equals("Licantropo")) {
							lica.recuperaForza();
						} else if (giocatore.equals("Vampiro")) {
							vampi.recuperaForza();
						}
						break;
					case "3":
						//riepiloga l'esito del gioco ed esce
						System.out.println("Fine gioco");
						sceltaAzione.close();
						//riepilogo di vita e forza dei personaggi
						System.out.println("Vita rimanente dell'eroe: " + eroe.getVita());
						System.out.println(eroe.getForza());
						System.out.println("Vita rimanente del vampiro: " + vampi.getVita());
						System.out.println(vampi.getForza());
						System.out.println("Vita rimanente del licantropo: " + lica.getVita());
						System.out.println(lica.getForza());
						//riepilogo della durata della guerra
						data = dataOra.getDayOfMonth() + "/" + dataOra.getMonthValue() + "/" + dataOra.getYear();
						System.out.println("Il giorno di fine del combattimento è il " + data);
						anno = dataOra.getYear();
						mese = dataOra.getMonthValue();
						giorno = dataOra.getDayOfMonth();
						//Stabilire se è giorno o notte
						if (dataOra.getHour() >= 7 && dataOra.getHour() <= 19) {
							mattina = true;
							System.out.println("La battaglia termina di giorno");			
						} else {
							mattina = false;
							System.out.println("La battaglia termina di notte");
							if (faseLunare(anno, mese, giorno)<4) {
								System.out.println("La luna è calante");
							} else if (Math.abs(faseLunare(anno, mese, giorno) - 4) < 2){
								System.out.println("La luna è piena");
							} else {
								System.out.println("La luna è crescente");
							}
						}
						
						return;
					default:
						System.out.println("Scelta non prevista");
						break;
				}				
			}
		}
	}
	
	public static int faseLunare(int year,int month,int day) {
	    /*Calcola la fase lunare in segmenti da 0 a 7
	      0 => luna nuova o crescente
	      4 => luna piena o calante
	    */
	   
	    int g, e;

	    if (month == 1) --day;
	    else if (month == 2) day += 30;
	    else // m >= 3
	    {
	        day += 28 + (month-2)*3059/100;

	        // adjust for leap years
	        if ((year & 3) == 0) ++day;
	        if ((year%100) == 0) --day;
	    }
	   
	    g = (year-1900)%19 + 1;
	    e = (11*g + 18) % 30;
	    if ((e == 25 && g > 11) || e == 24) e++;
	    return ((((e + day)*6+11)%177)/22 & 7);
	}
	
	public static void scegliGiocatore() {
		//Viene scelto random il primo combattente
		Random comb1= new Random();
		switch(comb1.nextInt(3)) {
			case 0:
				giocatore = "Eroe";
				break;
			case 1:
				giocatore = "Licantropo";
				break;
			case 2:
				giocatore = "Vampiro";
				break;
			default:
				System.out.println("Scelta non prevista");
				break;
		}
		System.out.println("Il personaggio che gioca è: **  " + giocatore + "  **");
		//se il giocatore è il licantropo, verifica se è umano o mostro
		if (giocatore.equals("Licantropo")) {
			if (!lica.isUomo) {
				System.out.println("Il licantropo ha forma di lupo");
			} else {
				System.out.println("Il licantropo ha forma umana");
			}
		}
	}
	
	public static boolean verificaVita() {
		//verifica che il giocatore abbia vita per poter attaccare
		if (giocatore.equals("Eroe")) {
			if (eroe.getVita() <= 0) {
				System.out.println("Non hai abbastanza vita per attaccare");
				isVivo = false;
			} else isVivo = true;
		} else 	if (giocatore.equals("Vampiro")) {
			if (vampi.getVita() <= 0) {
				System.out.println("Non hai abbastanza vita per attaccare");
				isVivo = false;
			} else isVivo = true;
		} else 	if (giocatore.equals("Licantropo")) {
			if (lica.getVita() <= 0) {
				System.out.println("Non hai abbastanza vita per attaccare");
				isVivo = false;
			} else isVivo = true;
		}
			
		return isVivo;
	}
} 
