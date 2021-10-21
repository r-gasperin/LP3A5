import java.util.Random;

public class Jantar {

    public static final int NUM_LUGARES = 5;
    public static final Object[] hashis = new Object[NUM_LUGARES];

    public static void main(String[] args) {

        for (int i = 0; i < NUM_LUGARES; i++)
            hashis[i] = new Object();

        for (int i = 0; i < NUM_LUGARES; i++)
            new Filosofo(i).start();
    }
}

class Filosofo extends Thread {

    public static final int TEMPO_MAXIMO = 2_000; // Tempo (ms) para filosofo pensar ou comer

    private Random r; // Objeto para randomizar os tempos de espera
    private int lugar; // Lugar ("cadeira") em que o filosofo esta' na mesa
    private int direita; // Posicao do vetor onde esta' o hashi "aa direita" do filosofo
    private int esquerda; // Posicao do vetor onde esta' o hashi "aa esquerda" do filosofo

    Filosofo(int lugar) {

        if (lugar < 0 || lugar >= Jantar.NUM_LUGARES) {
            System.out.println("Filósofo está fora da mesa!!");
            return;
        }

        this.r = new Random();
        this.lugar = lugar;
        this.direita = this.lugar;
        this.esquerda = (this.direita + 1) % Jantar.NUM_LUGARES;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.pensar();
                this.comer();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Filósofo saiu da mesa");
            }
        }
    }

    private void pensar() throws InterruptedException {
        System.out.printf("Filósofo #%d está pensando...\n", lugar+1);
        Thread.sleep(r.nextInt(TEMPO_MAXIMO)); // Pensa durante um tempo
    }

    private void comer() throws InterruptedException {
        System.out.printf("Filósofo #%d está pegando o 1o hashi...\n", lugar+1);
        synchronized (Jantar.hashis[direita]) {

            System.out.printf("Filósofo #%d está pegando o 2o hashi...\n", lugar+1);
            synchronized (Jantar.hashis[esquerda]) {

                System.out.printf("Filósofo #%d está comendo...\n", lugar+1);
                Thread.sleep(r.nextInt(TEMPO_MAXIMO)); // Come durante um tempo
            }
        }
    }
}
