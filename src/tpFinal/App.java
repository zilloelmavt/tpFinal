package tpFinal;

import java.util.Random;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {

		// Estados de los loops
		// Loop principal
		String  CORRIENDO = "corriendo",
				FINALIZADO = "finalizado",
		// Loop secundario
				PELEANDO = "peleando",
				PELEA_FINALIZADA = "peleaFinalizada";

		// Instancias
		Scanner scanner = new Scanner(System.in);
		Random rand = new Random();

		// Enemigos
		String[][] enemigos = { 
			{ "Esqueleto", "Bandido", "Lobo" }, 
			{ "Troll", "Orco", "Zombie" },
			{ "Dragón", "Demonio" } 
		};

		// Variables del juego
		String 	estadoPrincipal,
				subEstado,
				siguienteNivel;
		int nivel = 1;

		// Max vida, max ataque y posibilidad de soltar una recompensa cuando se muere.
		int[][] propiedadesEnemigosPorNivel = { { 50, 20, 70 }, { 85, 30, 25 }, { 120, 40, 10 } };

		// Variables de personaje
		String 	nombrePersonaje;

		// Propiedades
		int vida = 100,
			pociones = 2,
			vidaPocion = 30,
			puntosMaximosDeAtaque = 30;
		
		imprimirEnConsola("¡Bienvenido a una nueva aventura!", "-");

		// Funcion para definir el nombre del jugador. No sale del ciclo hasta que el
		// usuario presione "s".
		nombrePersonaje = crearPersonaje(scanner);
		
		// Cambio el estado del juego a "corriendo" para que comience el loop.
		estadoPrincipal = CORRIENDO;

		imprimirEnConsola("> Tu aventura comienza aquí. Encontraste una cueva y decidiste explorarla.", "#");
		
		// Loop principal del juego.
		while (estadoPrincipal.equals(CORRIENDO)) {
			int maximaVidaEnemigo = 0, 
				ataqueMaximoEnemigo = 0, 
				posibilidadRecompensa = 0,
				opcion = 0;
			int[] propiedadesEnemigos;
			String enemigo = "";

			// Seteo de cada partida
			switch (nivel) {
				case 1:
					propiedadesEnemigos = propiedadesEnemigosPorNivel[0];
					maximaVidaEnemigo = rand.nextInt(propiedadesEnemigos[0]);
					ataqueMaximoEnemigo = rand.nextInt(propiedadesEnemigos[1]);
					posibilidadRecompensa = rand.nextInt(propiedadesEnemigos[2]);
					enemigo = enemigos[0][rand.nextInt(enemigos[0].length)];
					break;
				case 2:
					propiedadesEnemigos = propiedadesEnemigosPorNivel[1];
					maximaVidaEnemigo = rand.nextInt(propiedadesEnemigos[0]) + 50;
					ataqueMaximoEnemigo = rand.nextInt(propiedadesEnemigos[1]) + 10;
					posibilidadRecompensa = rand.nextInt(propiedadesEnemigos[2]);
					enemigo = enemigos[1][rand.nextInt(enemigos[1].length)];
					break;
				case 3:
					propiedadesEnemigos = propiedadesEnemigosPorNivel[2];
					maximaVidaEnemigo = rand.nextInt(propiedadesEnemigos[0]) + 80;
					ataqueMaximoEnemigo = rand.nextInt(propiedadesEnemigos[1]) + 15;
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
				imprimirLinea(50, "-");
				System.out.println("> " + nombrePersonaje);
				System.out.println("> Salud: " + vida);
				System.out.println("> Tenés: " + pociones + " pociones curativas.");

				// Muestra info del enemigo.
				imprimirLinea(50, "-");
				System.out.println("> " + enemigo);
				System.out.println("> Salud: " + maximaVidaEnemigo);
				imprimirLinea(50, "-");

				// Muestra las opciones
				System.out.println("> ¿Que querés hacer?");
				System.out.println("  1. Pelear!!!");
				System.out.println("  2. Tomar una poción.");
				System.out.println("  3. Huir :(");

				// Defino la acción de lo que va a hacer el usuario de a cuerdo a lo que escriba
				// en la próxima linea.
				opcion = scanner.nextInt();

				// Pelea.
				if (opcion == 1) {
					int danio = rand.nextInt(puntosMaximosDeAtaque);
					int danioEnemigo = rand.nextInt(ataqueMaximoEnemigo);
					vida = vida - danioEnemigo;
					maximaVidaEnemigo = maximaVidaEnemigo - danio;

					imprimirLinea(50, "-");
					System.out.println("> El enemigo recibió " + danio + " puntos de daño.");
					System.out.println("> Recibiste " + danioEnemigo + " puntos de daño.");
					imprimirLinea(50, "-");

					if (maximaVidaEnemigo <= 0) {
						System.out.println("> " + enemigo + " murió.");

						// Mecanica para soltar una pocion.
						if (rand.nextInt(100) > posibilidadRecompensa) {
							System.out.println("> El enemigo soltó una poción.");
							pociones++;
						}
						
						imprimirLinea(50, "-");
						
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
					imprimirLinea(50, "-");
					
					if (pociones > 0) {
						// Recupera vida pero el enemigo tambien ataca.
						int danioEnemigo = rand.nextInt(ataqueMaximoEnemigo);
						vida = vida + vidaPocion;
						pociones--;
						vida = vida - danioEnemigo;

						System.out.println("> Tomaste una poción. Recuperaste " + vidaPocion + " puntos de vida.");
						System.out.println("> Recibiste " + danioEnemigo + " puntos de daño.");
					} else {
						System.out.println("> No tienes más pociones.");
					}
					imprimirLinea(50, "-");
				}

				// Huye.
				if (opcion == 3) {
					System.out.println("Huiste del " + enemigo + " :(");
					subEstado = PELEA_FINALIZADA;
				}
			}
			
			if(vida > 0) {
				if (nivel == 3) {
					imprimirEnConsola("Derrotaste todos los enemigos y saliste ileso de la cueva.", "-");
					estadoPrincipal = FINALIZADO;
				} else {
					imprimirEnConsola("¿Querés seguir con el siguiente nivel? S/N", "-");
					siguienteNivel = scanner.nextLine();

					while (!siguienteNivel.equalsIgnoreCase("s") && !siguienteNivel.equalsIgnoreCase("n")) {
						siguienteNivel = scanner.nextLine();
					}
						
					if(siguienteNivel.equalsIgnoreCase("s")) {
						if (nivel < 3 && opcion != 3) {
							nivel = nivel + 1;
						}
						subEstado = PELEANDO;
						imprimirEnConsola("Seguís al siguiente nivel de la cueva.", "-");
					} else if (siguienteNivel.equalsIgnoreCase("n")) {
						estadoPrincipal = FINALIZADO;
						imprimirEnConsola("Saliste ileso de la cueva.", "-");
					}
				}
			} else {
				imprimirEnConsola("Los enemigos te derrotaron.", "-");
				estadoPrincipal = FINALIZADO;
			}
		}

		imprimirEnConsola("GAME OVER", "-");
		scanner.close();
	}
	
	public static void imprimirEnConsola(String mensaje, String caracter) {
		int largo = mensaje.length() + 2;
		
		imprimirLinea(largo, caracter);
		System.out.println("# " + mensaje + " #");
		imprimirLinea(largo, caracter);
	}
	
	public static void imprimirLinea(int largo, String caracter) {
		String linea = "#";
		
		for(int i = 0; i < largo; i++) {
			linea += caracter;
		}
		
		linea += "#";
		
		System.out.println(linea);
	}

	public static String crearPersonaje(Scanner scanner) {
		String 	nombrePersonaje, 
				aceptaNombre;
		
		do {
			System.out.println("> ¿Cuál es tu nombre aventurero?");
			nombrePersonaje = scanner.nextLine();
			System.out.println("> Tu nombre es " + nombrePersonaje + ". ¿Esto es correcto? (S/N)");
			aceptaNombre = scanner.nextLine();
		} while (!aceptaNombre.equalsIgnoreCase("S"));
		
		return nombrePersonaje;
	}
}
