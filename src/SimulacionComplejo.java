public class SimulacionComplejo {
    public static void main(String[] args) {
        ComplejoCaidaRapida escuela = new ComplejoCaidaRapida();

        // Crear y ejecutar esquiadores
        for (int i = 0; i < 150; i++) {
            new Thread(new Esquiador(i+1, escuela)).start();
        }
    }
}
