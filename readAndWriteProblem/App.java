package ReadAndWrite;

/*

AUTORES: Guilherme Cavalheiro, Jamil Bannura e Marco Goedert

*/ 

public class App {
    
    public static void main(String args[]){

        System.out.println("RESOLVENDO O PROBLEMA DE ESCRITA E LEITURA\n\n Operações Simultaneas Permitidas:     - Leitura e Leitura\n\n Operações Simultaneas Não Permitidas: - Escrita e Leitura\n                                       - Leitura e Escrita\n                                       - Escrita e Escrita");

        final int leitores = 5;
        final int escritores = 5;

        Dados dados = new Dados();

        for(int i = 0; i < leitores; i++){
            new Ler(dados).start();
        }

        for(int i = 0; i < escritores; i++){
            new Escritor(dados).start();
        }
    }

}