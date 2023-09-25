package view;

import java.util.concurrent.Semaphore;

import controller.ControllerMultiprocessador;

public class Principal {

	public static void main(String[] args) {
		Semaphore semaforo = new Semaphore(1);
		
		for (int i = 1; i < 22; i++) {
			Thread t = new ControllerMultiprocessador(i, semaforo);
			t.start();
		}
	}

}
