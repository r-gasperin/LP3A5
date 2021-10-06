public class Main {
    public static volatile Object lock = new Object();
    public static volatile int produtos = 0;

    public static void main(String[] args) {
        Produtor p1 = new Produtor(1);
        Produtor p2 = new Produtor(2);
        Consumidor c1 = new Consumidor(1);
        Consumidor c2 = new Consumidor(2);
        p1.start();
        p2.start();
        c1.start();
        c2.start();
    }
}

class Produtor extends Thread {
    int id;
    Produtor(int novoId) {
        this.id = novoId;
    }
    public void run() {
        for (int i = 0; i < 100; i ++) {
            synchronized( Main.lock ) {
                if (Main.produtos < 100)
                    Main.produtos = Main.produtos + 1; // Produz

                if (Main.produtos > 0)
                    Main.lock.notify(); // Notifica consumidor

                System.out.println("\nProdutor " + id + "; estoque = " + Main.produtos);

                if (Main.produtos == 100) {
                    try {
                        System.out.println("\nLimite de produtos atingido... aguardando consumo");
                        Main.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

class Consumidor extends Thread {
    int id;
    Consumidor(int novoId) {
        this.id = novoId;
    }
    public void run() {
        for (int i = 0; i < 100; i++) {
            synchronized( Main.lock ) {
                if (Main.produtos == 0) {
                    try {
                        System.out.println("\nLimite de consumo atingido... aguardando produtor");
                        Main.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("\nConsumidor " + id + "; estoque = " + Main.produtos);

                if (Main.produtos > 0)
                    Main.produtos = Main.produtos - 1; // Consome

                if (Main.produtos < 100)
                    Main.lock.notify(); // Notifica produtor
            }
        }
    }
}
