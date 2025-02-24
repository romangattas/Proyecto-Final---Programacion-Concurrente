import java.util.concurrent.CountDownLatch;

public class Instructor {
    private int identificacion;

    public Instructor(int id) {
        identificacion = id;
    }

    public int getIdInstructor() {
        return identificacion;
    }

    public void darClase(CountDownLatch finClaseLatch) {
        try {
            System.out.println("Instructor " + getIdInstructor() + " dando clase...");
            Thread.sleep(5000); // Simular el tiempo de la clase
            System.out.println("Instructor " + getIdInstructor() + " ha terminado la clase.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            finClaseLatch.countDown(); // Notificar que la clase ha terminado
        }
    }
}