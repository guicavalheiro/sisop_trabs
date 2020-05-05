package ReadAndWrite;

public class Ler extends Thread {

    private static int leitores = 0;

    private int id;
    private Dados dados;

    public Ler(Dados dados){
        this.dados = dados;
        this.id = Ler.leitores++;
    }

    public void run(){
        while(true){
            final int delay = 5000;

            try{
                Thread.sleep((int) (Math.random() * delay));
            }
            catch(InterruptedException e){}
            this.dados.le(this.id);
        }
    }
}