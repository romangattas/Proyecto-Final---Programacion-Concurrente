import java.util.concurrent.CountDownLatch;

public class Instructor {
    private int identificacion;

    public Instructor(int id) {
        identificacion = id;
    }

    public int getIdInstructor() {
        return identificacion;
    }

    public void darClase(CountDownLatch finClaseLatch, String tipoClase) {
        try {

            System.out.println(" Instructor " + getIdInstructor() + " dando clase...");
            Thread.sleep(5000); // Simular el tiempo de la clase
            System.out.println("🏁 Clase de " + tipoClase + "  finalizada. Los esquiadores pueden continuar.");
            System.out.println(" Instructor " + getIdInstructor() + " vuelve a estar disponible.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            finClaseLatch.countDown(); // Notificar que la clase ha terminado
        }
    }
}