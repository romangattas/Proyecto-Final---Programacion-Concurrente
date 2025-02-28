public class GestionDelTiempo implements Runnable {
    private ComplejoCaidaRapida complejo;
    private int tiempoApertura; // Tiempo en milisegundos que los  medios permanecen abiertos

    public GestionDelTiempo(ComplejoCaidaRapida complejo, int tiempoApertura) {
        this.complejo = complejo;
        this.tiempoApertura = tiempoApertura;
    }
    
    @Override
    public void run() {
        try {
            complejo.avisoAperturaMedios();
            // Simula el tiempo en que el complejo está abierto
            Thread.sleep(tiempoApertura);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Luego de transcurrido el tiempo, se cierran los medios de elevacion
        complejo.cerrarMediosElevacion();
    }
}
