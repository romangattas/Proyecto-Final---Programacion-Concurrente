import java.util.concurrent.Semaphore;

public class MedioElevador {
    private Semaphore molinetes;
    private int contMolinete;
    private int idElevador;

    public MedioElevador(int idE){

        idElevador = idE;
        molinetes = new Semaphore(idE);
        contMolinete = 0;   

    }

    public int getIdMedio(){
        return idElevador;
    }

    public int getContadorMolinete(){
        return contMolinete;
    }

    public boolean utilizarMolinete(Esquiador esquiador){

            boolean puedePasar = false;
            
            try {

                molinetes.acquire();

                if(esquiador.tienePase()){
                    //Si tiene el pase entonces utiliza el molinete y suma 1 al contador
                    System.out.println("Esquiador " + esquiador.getIdEsquiador() + " está usando un molinete del medio " + getIdMedio()+".");
                    contMolinete+=1; // Suma 1 uso del molinete al contador
                    puedePasar = true;
                    
                }else{
                    //No puede pasar porque no tiene pase, suelta el molinete inmediatamente y se retira
                    System.out.println("Esquiador " + esquiador.getIdEsquiador() + " NO tenia pase y se retira.");
                    molinetes.release();
                }   

            } catch (InterruptedException e) {

                e.printStackTrace();

            } 
            return puedePasar;

    }

    public void soltarMolinete(){
        //Suelta el molinete que utilizo
        molinetes.release();
    }




}
