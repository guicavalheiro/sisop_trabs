import java.util.Random;

/*

AUTORES: Guilherme Cavalheiro, Jamil Bannura e Marco Goedert

DESCRIÇÃO: A estratégia utilizada foi de que quando um filosofo pega o garfo da esquerda
           ele é obrigado a pegar o garfo da direita. Caso ele pegue o primeiro garfo e 
           não consiga pegar o segundo, ele obrigatoriamente tem que largar o primeiro. 

*/ 

public class Philo {
    
    Filosofo[] filosofos;
    Garfo[] garfos;
    Thread[] threads;

    int numeroDeFilosofos;

    public void init(){

        System.out.println("JANTAR DOS FILÓSOFOS SEM DEADOLOCK\n\nIniciando o jantar...");

        numeroDeFilosofos = 5;
        
        filosofos = new Filosofo[numeroDeFilosofos];
        garfos = new Garfo[numeroDeFilosofos];
        threads = new Thread[numeroDeFilosofos];
    
        for(int i = 0; i < numeroDeFilosofos; i++){
            filosofos[i] = new Filosofo(i + 1);
            garfos[i] = new Garfo(i + 1);
        }
    }

    public void pensaCome(){

        for(int i = 0; i < numeroDeFilosofos; i++){

            final int index = i;

            threads[i] = new Thread(new Runnable(){

                public void run(){

                    try{
                        filosofos[index].start(garfos[index], garfos[(index + 1) % (numeroDeFilosofos)]);
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
            threads[i].start();
        }

    }

    public class Garfo{

        private int garfoId;
        private boolean isAvailable;

        public Garfo(int _garfoId){
            this.garfoId = _garfoId;
            this.isAvailable = true;
        }

        public synchronized void soltarGarfo() throws InterruptedException{
            isAvailable = true;
        }

        public synchronized boolean pegarGarfo(int filoId) throws InterruptedException{

            int contador = 0;
            int hold = new Random().nextInt(10) + 5;

            while(!isAvailable){

                Thread.sleep(new Random().nextInt(100) + 50);
                contador++;

                if(contador > hold){
                    return false;
                }
            }

            isAvailable = false;
            return true;
        }
    }

    public class Filosofo{

        private int filoId;
        private Garfo esq, dir;

        public Filosofo(int _filoId){
            filoId = _filoId;
        }

        public void start(Garfo _esq, Garfo _dir) throws InterruptedException{

            esq = _esq;
            dir = _dir;

            while(true){
                if(new Random().nextBoolean()){
                    come();
                }
                else{
                    pensa();
                }
            }
        }

        public void pensa() throws InterruptedException{

            System.out.println("O Filósofo " + filoId + " está pensando.");
            Thread.sleep(new Random().nextInt(1000) + 100);
            System.out.println("O Filósofo " + filoId + " parou de pensar.");
        }

        public void come() throws InterruptedException{

            boolean garfoDireita = false;
            boolean garfoEsquerda = false;

            System.out.println("O Filósofo " + filoId + " está com fome e deseja comer.");
            System.out.println("O Filósofo " + filoId + " está pegando o garfo da esquerda, com id: " + esq.garfoId);
            garfoEsquerda = esq.pegarGarfo(filoId);


            if(!garfoEsquerda){
                return;
            }

            System.out.println("O Filósofo " + filoId + " está pegando o garfo da direita, com id: " + dir.garfoId);
            garfoDireita = dir.pegarGarfo(filoId);

            if(!garfoDireita){
                esq.soltarGarfo();
                System.out.println("O Filósofo " + filoId + " pegou o primeiro garfo, mas não conseguiu pegar o segundo, então ele solta o primeiro.");
                return;
            }

            System.out.println("O Filósofo " + filoId + " está comendo.");
            
            Thread.sleep(new Random().nextInt(1000) + 100);

            esq.soltarGarfo();
            dir.soltarGarfo();

            System.out.println("O Filósofo " + filoId + " está satisfeito.");
        }
    }

    public static void main(String args[]){
        
        Philo ob = new Philo();
        ob.init();
        ob.pensaCome();

    }  
}