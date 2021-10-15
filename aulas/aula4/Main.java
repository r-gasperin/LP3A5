public class Main {
	public static volatile Object lock = new Object();
	public static volatile int produtos = 0;
	
	public static void main(String[] args) {
		for(int i = 1; i <= 100; i++) {			
			Produtor p = new Produtor(i);
			p.start();
			Consumidor c = new Consumidor(i);
			c.start();
		}
	}
}

class Produtor extends Thread {
	int id = 0;
	Produtor(int novoId) {
		this.id = novoId;
	}
	public void run() {		
		synchronized( Main.lock )
		{	
			System.out.println("\nProdutor " + this.id + "; estoque = " + Main.produtos);
			while (Main.produtos >= 3) { // Tamanho do buffer, tamanho da mem�ria, m�ximo do estoque
				try {
					Main.lock.wait(); // Espera notifica��o para iniciar produ��o
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Main.produtos = Main.produtos + 1; // Produz algo
			System.out.println("\nProdutor " + this.id + "; produziu item = " + Main.produtos);
			Main.lock.notifyAll(); // Notifica que produziu!
		}
		
	}
}

class Consumidor extends Thread {
	int id = 0;
	Consumidor(int novoId) {
		this.id = novoId;
	}
	public void run() {		
		synchronized( Main.lock )
		{	
			System.out.println("\nConsumidor " + this.id + "; estoque = " + Main.produtos);
			while (Main.produtos <= 0) { // Verifica se o estoque est� vazio
				try {
					Main.lock.wait(); // Espera para receber notifica��o
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Main.produtos = Main.produtos - 1; // Consome apenas um produto
			System.out.println("\nConsumidor " + this.id + " consumiu!!");
			Main.lock.notifyAll(); // Notifica que consumiu
		}
	}
}
