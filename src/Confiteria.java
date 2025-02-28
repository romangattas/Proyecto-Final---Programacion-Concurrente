import java.util.concurrent.Semaphore;

public class Confiteria {
    private Semaphore mostradorComida;
    private Semaphore mostradorPostre;
    private static final int CAPACIDAD_CONFITERIA = 100;
    private int ocupacion;


    public Confiteria(){
        ocupacion = 0;
        //2 mostradores para la comida
        mostradorComida = new Semaphore(2);
        //1 para el postre
        mostradorPostre = new Semaphore(1);
    }


    public synchronized void entrarYPagar(Esquiador esquiador) throws InterruptedException{
        while(ocupacion == CAPACIDAD_CONFITERIA){
            this.wait();
        }
        ocupacion++;
        System.out.println("💵Esquiador " +esquiador.getIdEsquiador()+" ingreso a la confiteria y pago su menu.");
    }

    public void retirarComida(Esquiador esquiador) throws InterruptedException{
        //Cada esquiador se acerca al mostrador a retirar la comida
        mostradorComida.acquire();
        System.out.println("🍕Esquiador " +esquiador.getIdEsquiador()+" esta retirando la comida.");
    }

    public void finRetirarComida(Esquiador esquiador) throws InterruptedException{
        //Se retira del mostrador
        mostradorComida.release();
    }

    public void retirarPostre(Esquiador esquiador) throws InterruptedException{
        //El esquiador que compro postre se acerca al mostrador a retiralo
        mostradorPostre.acquire();
        System.out.println("🍧Esquiador " +esquiador.getIdEsquiador()+" esta retirando el postre.");
    }

    public void finRetirarPostre(Esquiador esquiador) throws InterruptedException{
        //Se retira del mostrador
        mostradorPostre.release();
    }

    public synchronized void salirDeConfiteria(Esquiador esquiador){
        //El esquiador se retira
        System.out.println("Esquiador " +esquiador.getIdEsquiador()+" se retira de la confiteria.");
        ocupacion--;
        this.notify();
    }
}
