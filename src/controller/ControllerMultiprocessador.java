package controller;

import java.util.concurrent.Semaphore;

public class ControllerMultiprocessador extends Thread {

	private int idThread;
	private double tempBD;
	private double tempCalc;
	private int transacoes;
	Semaphore semaforo;

	public ControllerMultiprocessador(int idThread, Semaphore semaforo) {
		this.idThread = idThread;
		this.semaforo = semaforo;
	}

	public void run() {
		Processamento();
	}

	private void Processamento() {
		int resp = 0;
		if (idThread % 3 == 1) {
			tempCalc = Math.random() * 0.8 + 0.2;
			tempCalc = (Math.round(tempCalc * Math.pow(10, 1)) / Math.pow(10, 1)) * 1000;
			tempBD = 1 * 1000;
			transacoes = 4;
		} else if (idThread % 3 == 2) {
			tempCalc = Math.random() * 1.0 + 0.5;
			tempCalc = (Math.round(tempCalc * Math.pow(10, 1)) / Math.pow(10, 1))* 1000;
			tempBD = 1.5 * 1000;
			transacoes = 6;
		} else if (idThread % 3 == 0) {
			tempCalc = Math.random() * 1.0 + 1.0;
			tempCalc = (Math.round(tempCalc * Math.pow(10, 1)) / Math.pow(10, 1))* 1000;
			tempBD = 1.5 * 1000;
			transacoes = 6;
		}
		
		
		do {
			Calculo();
			transacoes--;
			while (resp != 1) {
				try {
					semaforo.acquire();
					TransacaoBD();
					resp = 1;
					transacoes--;
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					semaforo.release();
				}
			}
			resp = 0;
		} while (transacoes > 0);

	}

	public void Calculo() {
		System.out.println("Thread " + idThread + ": Calculando...");
		try {
			sleep((long)tempCalc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void TransacaoBD() {
		System.err.println("Thread " + idThread + ": Subindo para BD");
		try {
			sleep((long)tempBD);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
