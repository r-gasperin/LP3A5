/*
public class Main {
    public static void main(String[] args) {
        int faixa = 1000;
        MinhaThread mT1 = new MinhaThread(1, faixa);
        MinhaThread mT2 = new MinhaThread(2, faixa);
        MinhaThread mT3 = new MinhaThread(3, faixa);
        mT1.start();
        mT2.start();
        mT3.start();
        System.out.println("\nMain");
    }
}

class MinhaThread extends Thread {
    static volatile Object lock = new Object();
    static volatile int valor = 0;
    int id, limite;
    MinhaThread(int novoId, int novoLimite) {
        this.id = novoId;
        this.limite = novoLimite;
    }
    public void run() {
        for(int i = 0; i < limite; i++) {
            synchronized( lock )
            {
                valor = valor + 1;
            }
        }
        System.out.println("\nTerminou thread " + id + " com valor " + valor);
    }
}
*/

public class Main {
    public static volatile Object lock = new Object();
    public static volatile int produtos = 0;

    public static void main(String[] args) {
        Produtor p1 = new Produtor(1);
        p1.start();
        Consumidor c1 = new Consumidor(1);
        c1.start();
    }
}

class Produtor extends Thread {
    int id;
    Produtor(int novoId) {
        this.id = novoId;
    }
    public void run() {
        for(int i = 0; i < 100; i ++ ) {
            synchronized( Main.lock )
            {
                if (Main.produtos < 100)
                    Main.produtos = Main.produtos + 1;
                System.out.println("\nProdutor " + id + "; estoque = " + Main.produtos);
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
        for(int i = 0; i < 100; i++) {
            synchronized( Main.lock )
            {
                System.out.println("\nConsumidor " + id + "; estoque = " + Main.produtos);
                if (Main.produtos > 0)
                    Main.produtos = Main.produtos - 1;
            }
        }
    }
}
