import java.util.ArrayList;
import java.util.List;

public class SimulacionComplejo {
    public static void main(String[] args) throws InterruptedException {
        // Se crea la instancia del complejo
        ComplejoCaidaRapida complejo = new ComplejoCaidaRapida();
        
        // Se define el tiempo que los medios permanecerán abiertos 
        int tiempoApertura = 20000;
        
        /* Se inicia el hilo que gestiona el horario (imprime que los medios estan abiertos y despues
        del tiempoApertura, los cierra) */
        Thread gestionDelTiempo = new Thread(new GestionDelTiempo(complejo, tiempoApertura));
        gestionDelTiempo.start();
        
        // Se crean e inician hilos esquiadores
        List<Thread> hilosEsquiadores = new ArrayList<>();        
        for (int i = 1; i <= 100; i++) {
            Esquiador esquiador = new Esquiador(i, complejo);
            Thread hiloEsquiador = new Thread(esquiador);
            hiloEsquiador.start();
            hilosEsquiadores.add(hiloEsquiador);

        }

        // Espera a que el termine GestionDelTiempo(cierre de medios)
        gestionDelTiempo.join();
        
        // Espera a que todos los esquiadores terminen
        for (Thread hilo : hilosEsquiadores) {
            hilo.join();
        }
        
        // Una vez finalizados todos los hilos, se muestran los contadores
        complejo.mostrarContadoresMolinete();
    }
}
