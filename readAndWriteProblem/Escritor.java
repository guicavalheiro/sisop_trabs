package ReadAndWrite;

public class Escritor extends Thread {
    
    private static int escritores = 0;

    private int id;
    private Dados dados;

    public Escritor(Dados dados){
        this.dados = dados;
        this.id = Escritor.escritores++;
    }

    public void run(){
        while(true){
            final int delay = 5000;

            try{
                Thread.sleep((int) (Math.random() * delay));
            }
            catch(InterruptedException e){}
            this.dados.escreve(this.id);
        }
    }
}