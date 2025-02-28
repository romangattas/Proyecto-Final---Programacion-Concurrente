public class SimulacionComplejo {
    public static void main(String[] args) {
        // Se crea la instancia del complejo
        ComplejoCaidaRapida complejo = new ComplejoCaidaRapida();
        
        // Se define el tiempo que los medios permanecerán abiertos (10hs-17hs)
        int tiempoApertura = 20000;
        
        /* Se inicia el hilo que gestiona el horario (imprime que los medios estan abiertos y despues
        del tiempoApertura, los cierra) */
        Thread gestionDelTiempo = new Thread(new GestionDelTiempo(complejo, tiempoApertura));
        gestionDelTiempo.start();
        
        // Se crean e inician hilos esquiadores
        for (int i = 1; i <= 100; i++) {
            Esquiador esquiador = new Esquiador(i, complejo);
            Thread hiloEsquiador = new Thread(esquiador);
            hiloEsquiador.start();
        }
    }
}
