public class Esquiador implements Runnable{
    private int identificacion;
    private boolean pase;
    private ComplejoCaidaRapida complejo;
    private boolean menuConPostre;
    private String tipoClase; // "ski" o "snowboard"

    
    public Esquiador(int id, ComplejoCaidaRapida com){
        identificacion = id;
        complejo = com;

        //Decide aleatoriamente si tiene el pase o no
        pase = (Math.random()<0.5);
        //Decide aleatoriamente si come postre o no
        menuConPostre = (Math.random() < 0.5);
        //Decide aleatoriamente el tipo de clase
        tipoClase = (Math.random() < 0.5) ? "ski" : "snowboard";

    }

    public int getIdEsquiador(){
        return this.identificacion;
    }

    public boolean getMenu(){
        return menuConPostre;
    }

    public String getTipoClase() {
        return tipoClase;
    }

    public boolean tienePase(){
        return pase;
    }

    public ComplejoCaidaRapida getComplejo(){
        return complejo;
    }


    public void run(){

        MedioElevador medioElegido = getComplejo().seleccionarMedioAleatorio();

        boolean puedePasar = medioElegido.utilizarMolinete(this);

        simularTiempo(1000);

        if(puedePasar){

            //Libera el molinete
            medioElegido.soltarMolinete();

            System.out.println("Esquiador "+getIdEsquiador()+" subiendo ...");
            simularTiempo(2000);

            //Decide aleatoriamente si tomar o no clases
            if(Math.random() < 0.5){

                System.out.println("Esquiador "+getIdEsquiador()+" intenta tomar clases.");
                complejo.intentarTomarClase(this);
                
            }

            //Descanso
            simularTiempo(3000);
            
            //Decide aleatoriamente si entrar o no a la confiteria
            if(Math.random() < 0.5){

                //Simula ingreso a la confiteria
                complejo.entrarYPagar(this);
                simularTiempo(1000);

                //Simula tiempo de retiro
                complejo.retirarComida(this);
                simularTiempo(1500);
                complejo.finRetirarComida(null);

                //Si pidio postre
                if(getMenu()){

                    //Simula tiempo de retiro
                    complejo.retirarPostre(this);
                    simularTiempo(1000);
                    complejo.finRetirarPostre(this);

                }
                
                complejo.salirDeConfiteria(this);
                
            }

        }

    }


    public void simularTiempo(int tiempo){

        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    
}
