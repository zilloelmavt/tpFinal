package tpFinal;

import java.util.Random;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {

		// Estados del loop
		String CORRIENDO = "corriendo";
		String FINALIZADO = "finalizado";
		String PELEANDO = "peleando";
		String PELEA_FINALIZADA = "peleaFinalizada";

		// Instancias
		Scanner scanner = new Scanner(System.in);
		Random rand = new Random();

		// Personajes
		String[] clases = { "Guerrero", "Hechizero", "Asesino" };
		String[][] enemigos = { 
			{ "Esqueleto", "Bandido", "Lobo" }, 
			{ "Troll", "Orco", "Zombie" },
			{ "Dragón", "Demonio" } 
		};

		// Variables del juego
		String estadoPrincipal;
		String subEstado;
		int nivel = 1;
		String siguienteNivel;

		// Max vida, max ataque y posibilidad de soltar una recompensa cuando se muere.
		int[][] propiedadesEnemigosPorNivel = { { 50, 20, 70 }, { 85, 30, 25 }, { 120, 40, 10 } };

		// Variables de personaje
		String nombrePersonaje;
		String aceptaNombre;

		// Propiedades
		int vida = 100;
		int pociones = 2;
		int vidaPocion = 30;
		int tipo;
		int maxAtaque = 30;

		System.out.println("#-----------------------------------#");
		System.out.println("# ¡Bienvenido a una nueva aventura! #");
		System.out.println("#-----------------------------------#\n");

		// Loop para definir el nombre del jugador. No sale del ciclo hasta que el
		// usuario presione "s".
		do {
			System.out.println("> ¿Cuál es tu nombre aventurero?");
			nombrePersonaje = scanner.nextLine();
			System.out.println("> Tu nombre es " + nombrePersonaje + ". ¿Esto es correcto? (S/N)");
			aceptaNombre = scanner.nextLine();
		} while (!aceptaNombre.equalsIgnoreCase("S"));

		// Cambio el estado del juego a "corriendo" para que comience el loop.
		estadoPrincipal = CORRIENDO;

		System.out.println("###########################################################################");
		System.out.println("> Tu aventura comienza aquí. Encontraste una cueva y decidiste explorarla.");
		System.out.println("###########################################################################\n");

		// Loop principal del juego.
		while (estadoPrincipal.equals(CORRIENDO)) {
			int maxVidaEnemigo = 0, maxEnemigoAtaque = 0, posibilidadRecompensa = 0;
			int[] propiedadesEnemigos;
			String enemigo = "";

			// Seteo de cada partida
			switch (nivel) {
				case 1:
					propiedadesEnemigos = propiedadesEnemigosPorNivel[0];
					maxVidaEnemigo = rand.nextInt(propiedadesEnemigos[0]);
					maxEnemigoAtaque = rand.nextInt(propiedadesEnemigos[1]);
					posibilidadRecompensa = rand.nextInt(propiedadesEnemigos[2]);
					enemigo = enemigos[0][rand.nextInt(enemigos[0].length)];
					break;
				case 2:
					propiedadesEnemigos = propiedadesEnemigosPorNivel[1];
					maxVidaEnemigo = rand.nextInt(propiedadesEnemigos[0]) + 50;
					maxEnemigoAtaque = rand.nextInt(propiedadesEnemigos[1]) + 10;
					posibilidadRecompensa = rand.nextInt(propiedadesEnemigos[2]);
					enemigo = enemigos[1][rand.nextInt(enemigos[1].length)];
					break;
				case 3:
					propiedadesEnemigos = propiedadesEnemigosPorNivel[2];
					maxVidaEnemigo = rand.nextInt(propiedadesEnemigos[0]) + 80;
					maxEnemigoAtaque = rand.nextInt(propiedadesEnemigos[1]) + 15;
					posibilidadRecompensa = rand.nextInt(propiedadesEnemigos[2]);
					enemigo = enemigos[2][rand.nextInt(enemigos[2].length)];
					break;
			}

			subEstado = PELEANDO;

			// Info del enemigo
			System.out.println("> ¡Un " + enemigo + " aparece!");

			// Loop de cada round
			while (subEstado.equals(PELEANDO)) {

				// Muestra info del personaje.
				System.out.println("#-----------------------------------#");
				System.out.println("> Tu nombre es " + nombrePersonaje);
				System.out.println("> Tu salud es: " + vida);
				System.out.println("> Tenés: " + pociones + " pociones curativas.");

				// Muestra info del enemigo.
				System.out.println("#-----------------------------------#");
				System.out.println("> " + enemigo);
				System.out.println("> Salud: " + maxVidaEnemigo);
				System.out.println("#-----------------------------------#\n");

				// Muestra las opciones
				System.out.println("> ¿Que querés hacer?");
				System.out.println("  1. Pelear!!!");
				System.out.println("  2. Tomar una poción.");
				System.out.println("  3. Huir :(");

				// Defino la acción de lo que va a hacer el usuario de a cuerdo a lo que escriba
				// en la próxima linea.
				int opcion = scanner.nextInt();

				// Pelea.
				if (opcion == 1) {
					int danio = rand.nextInt(maxAtaque);
					int danioEnemigo = rand.nextInt(maxEnemigoAtaque);
					vida = vida - danioEnemigo;
					maxVidaEnemigo = maxVidaEnemigo - danio;

					System.out.println("#-----------------------------------#");
					System.out.println("> El enemigo recibió " + danio + " puntos de daño.");
					System.out.println("> Recibiste " + danioEnemigo + " puntos de daño.");
					System.out.println("#-----------------------------------#");

					if (maxVidaEnemigo <= 0) {
						System.out.println("> " + enemigo + " murió.");

						// Mecanica para soltar una pocion.
						if (rand.nextInt(100) > posibilidadRecompensa) {
							System.out.println("> El enemigo soltó una poción.");
							pociones++;
						}
						System.out.println("#-----------------------------------#\n");

						// Finaliza el loop.
						subEstado = PELEA_FINALIZADA;
					}

					// Muere el personaje
					if (vida <= 0) {
						subEstado = PELEA_FINALIZADA;
					}
				}

				// Toma una poción.
				if (opcion == 2) {
					System.out.println("#-----------------------------------#");
					if (pociones > 0) {
						// Recupera vida pero el enemigo tambien ataca.
						int danioEnemigo = rand.nextInt(maxEnemigoAtaque);
						vida = vida + vidaPocion;
						pociones--;
						vida = vida - danioEnemigo;

						System.out.println("> Tomaste una poción. Recuperaste " + vidaPocion + " puntos de vida.");
						System.out.println("> Recibiste " + danioEnemigo + " puntos de daño.");
					} else {
						System.out.println("> No tienes más pociones.");
					}
					System.out.println("#-----------------------------------#\n");
				}

				// Huye.
				if (opcion == 3) {
					System.out.println("Huiste del" + enemigo + " :(");
					subEstado = PELEA_FINALIZADA;
				}
			}
			
			if(vida > 0) {
				if (nivel == 3) {
					System.out.println("#-----------------------------------#");
					System.out.println("Derrotaste todos los enemigos y saliste ileso de la cueva.");
					System.out.println("#-----------------------------------#\n");
					estadoPrincipal = FINALIZADO;
				} else {
					System.out.println("#-----------------------------------#");
					System.out.println("¿Querés seguir con el siguiente nivel? S/N");
					System.out.println("#-----------------------------------#");
					siguienteNivel = scanner.nextLine();

					while (!siguienteNivel.equalsIgnoreCase("s") && !siguienteNivel.equalsIgnoreCase("n")) {
						siguienteNivel = scanner.nextLine();
					}
						
					if(siguienteNivel.equalsIgnoreCase("s")) {
						if (nivel < 3) {
							nivel = nivel + 1;
						}
						subEstado = PELEANDO;
						
						System.out.println("#-----------------------------------#");
						System.out.println("Seguís al siguiente nivel de la cueva.");
						System.out.println("#-----------------------------------#\n");
					} else if (siguienteNivel.equalsIgnoreCase("n")) {
						estadoPrincipal = FINALIZADO;
						
						System.out.println("#-----------------------------------#");
						System.out.println("Saliste ileso de la cueva.");
						System.out.println("#-----------------------------------#\n");
					}
				}
			} else {
				System.out.println("#-----------------------------------#");
				System.out.println("Los enemigos te derrotaron.");
				System.out.println("#-----------------------------------#\n");
				estadoPrincipal = FINALIZADO;
			}
		}

		scanner.close();

		// Selección de la clase
//		System.out.println("Seleccione un personaje:");
//		System.out.println("\t> Presione 1 para Guerrero");
//		System.out.println("\t> Presione 2 para Mago");
//		System.out.println("\t> Presione 3 para Arquero");
//		
//		tipo = Integer.parseInt(scanner.nextLine()) - 1;
//		
//		switch(type) {
//			case 1:
//				break;
//			case 2:
//				break;
//			case 3:
//				break;
//			default:
//				break;
//		}

//		System.out.println(clases[type]);
	}

}
