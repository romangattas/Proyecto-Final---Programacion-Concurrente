import java.util.concurrent.Semaphore;

public class MedioElevador {
    private Semaphore molinetes;
    private int contMolinete;
    private int idElevador;
    private boolean abierto;  // Estado: true = abierto, false = cerrado

    public MedioElevador(int idE) {
        idElevador = idE;
        // Se asume que la cantidad de molinetes es igual al id del medio
        molinetes = new Semaphore(idE);
        contMolinete = 0;
        abierto = true;
    }

    public int getIdMedio() {
        return idElevador;
    }

    public int getContadorMolinete() {
        return contMolinete;
    }

    //Método sincronizado para consultar el estado del medio
    public synchronized boolean estaAbierto() {
        return abierto;
    }

    //Método sincronizado para cerrar el medio
    public synchronized void cerrar() {
        if (abierto) {
            abierto = false;
            System.out.println("Medio de elevación " + getIdMedio() + " cerrado.");
        }
    }

    public int utilizarMolinete(Esquiador esquiador) {

        //Resultado indica: 0 = Paso exitoso, 1 = Medio cerrado, 2 = No tiene pase
        int puedePasar = 0;

        // Verifica antes de intentar adquirir el molinete
        if (estaAbierto()) {
            try {
                molinetes.acquire();

                // Vuelve a verificar si el medio sigue abierto después de adquirir el recurso.
                if (!estaAbierto()) {
                    System.out.println("❌ Medio de elevación " + getIdMedio() 
                            + " se cerró mientras el esquiador " + esquiador.getIdEsquiador() + " intentaba usarlo.");
                    molinetes.release();
                    puedePasar = 1;
                } else {
                    if (esquiador.tienePase()) {
                        System.out.println("⬆️ Esquiador " + esquiador.getIdEsquiador() 
                                + " está usando un molinete del medio " + getIdMedio() + ".");
                        contMolinete++;  // Incrementa el contador de usos
                        // puedePasar se mantiene en 0 (paso exitoso)
                    } else {
                        System.out.println("🎫 Esquiador " + esquiador.getIdEsquiador()  
                                + " NO tenía pase, vuelve a comprarlo.");
                        molinetes.release();
                        puedePasar = 2;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }else{
            System.out.println("❌ Medio de elevación " + getIdMedio() + " cerrado. ❌");
            puedePasar = 1;
        }
        
        return puedePasar;
    }

    public void soltarMolinete() {
        molinetes.release();
    }
}
