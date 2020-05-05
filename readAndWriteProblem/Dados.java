package ReadAndWrite;

public class Dados {
    
    private int leitores;

    public Dados(){
        this.leitores = 0;
    }
    
    public void le(int id){
        synchronized(this){
            this.leitores++;
            System.out.println("Leitor " + id + " começou a ler.");            
        }
    
        final int delay = 5000;
        try{
            Thread.sleep((int) (Math.random() * delay));
        }
        catch (InterruptedException e){}

        synchronized(this){
            this.leitores--;
            System.out.println("Leitor " + id + " parou de ler.");            
            
            if(this.leitores == 0){
                this.notifyAll();
            }
        }
    }

    public synchronized void escreve(int id){

        while(this.leitores != 0){
            try{
                this.wait();
            }
            catch(InterruptedException e) {}
        }
        System.out.println("Escritor " + id + " começou a escrever.");
        
        final int delay = 5000;
        try{
            Thread.sleep((int) (Math.random() * delay));
        }
        catch (InterruptedException e){}

        System.out.println("Escritor " + id + " parou de escrever.");
        this.notifyAll();
    }
}