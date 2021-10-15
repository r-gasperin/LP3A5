public class Jantar {

	public Filosofos[] filosofos = new Filosofos[5];
	
	public static volatile Object[] hashi = new Object[5];
	
	public static void main(String[] args) {

	}
}

class Filosofos extends Thread {
	
	public void run() {
		
		synchronized(Jantar.hashi[0]) {
			
			synchronized(Jantar.hashi[1]) {
				System.out.println("Sou o Filosofo 1 e acabei de comer com dois hashis!!");
			}
		}
		System.out.println("Filosofo 1: Voltarei a filosofar!");
	}
	
}
