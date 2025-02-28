import java.util.concurrent.*;

public class ComplejoCaidaRapida {
   private static final int MAX_INSTRUCTORES = 5;
   private static final int MAX_MEDIOS_ELEVACION = 4;
   private MedioElevador[] mediosDeElevacion;
   private Confiteria confiteria;
   private CyclicBarrier barreraSki;
   private CyclicBarrier barreraSnow;
   private BlockingQueue<Instructor> colaInstructores;
   private boolean mediosAbiertos;
   

   public ComplejoCaidaRapida(){

      //Crea 4 medios de elevacion con 1, 2, 3 y 4 molinetes respectivamente
      mediosDeElevacion = new MedioElevador[MAX_MEDIOS_ELEVACION];
      for (int i = 0; i < mediosDeElevacion.length; i++) {
         mediosDeElevacion[i] = new MedioElevador(i+1);
      }

      colaInstructores = new LinkedBlockingQueue<>();

      //Registro de instructores
      for (int i = 1; i <= MAX_INSTRUCTORES; i++) {
         colaInstructores.offer(new Instructor(i));
      }

      barreraSki = new CyclicBarrier(4, () -> notificarInstructor("Ski⛷️"));
      barreraSnow = new CyclicBarrier(4, () -> notificarInstructor("Snowboard🏂"));

      confiteria = new Confiteria();

      mediosAbiertos = true;
   }

 


   //Metodo de la barrera
   private void notificarInstructor(String tipoClase) {
      Instructor instructor = colaInstructores.poll();
      if (instructor != null) {
          CountDownLatch finClaseLatch = new CountDownLatch(1); // Latch para la clase de ski o snow
          System.out.println("✅ Grupo de " + tipoClase + "  formado. Instructor " + instructor.getIdInstructor() + " comenzando clase.");
          instructor.darClase(finClaseLatch, tipoClase); // El instructor da la clase
          try {

              finClaseLatch.await(); // Esperar a que la clase termine

          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          colaInstructores.offer(instructor); // El instructor vuelve a estar disponible
      } else {
          System.out.println("❌ No hay instructores disponibles para " + tipoClase + ". Los alumnos desisten y se les devuelve el dinero.");
      }
   }






   //Medios de Elevacion
   public MedioElevador seleccionarMedioAleatorio(){
      int eleccionMedio = (int) (Math.random() * MAX_MEDIOS_ELEVACION);
      return mediosDeElevacion[eleccionMedio];
   }

   //Método que cierra cada medio y actualiza el estado de mediosAbiertos

   public void cerrarMediosElevacion(){
      for (MedioElevador medio : mediosDeElevacion) {
            medio.cerrar();
      }
      mediosAbiertos = false;
      System.out.println("\n ⛔️ MEDIOS DE ELEVACION CERRADOS. ⛔️ \n");
   }

   public void avisoAperturaMedios(){
      
      System.out.println("\n⏰ MEDIOS DE ELEVACION ABIERTOS. ⏰\n");

   }

   public synchronized boolean getMediosAbiertos(){
      return mediosAbiertos;
   }

   public void mostrarContadoresMolinete() {
      System.out.println("\n📊 USO DE LOS MEDIOS:\n");
      for (MedioElevador medio : mediosDeElevacion) {
          System.out.println("🎢 -Medio " + medio.getIdMedio() + " fue utilizado " + medio.getContadorMolinete() + " veces.");
      }
  }

   
   //Clases de Ski y Snowboard
   public void intentarTomarClase(Esquiador esquiador){
      String tipoClase = esquiador.getTipoClase();
      System.out.println("⏳ Esquiador " + esquiador.getIdEsquiador() + " esperando para formar grupo de " + tipoClase + ".");
      if (tipoClase.equals("ski")) {
         // Esperar en la barrera de ski
          try {
            barreraSki.await(7, TimeUnit.SECONDS);
         } catch (TimeoutException | InterruptedException | BrokenBarrierException e) {
            System.out.println("⌛️ Esquiador " + esquiador.getIdEsquiador() + " se canso de esperar para formar grupo de " + tipoClase + ".");
         } 
      } else {
         // Esperar en la barrera de snowboard
         try {
            barreraSnow.await(7, TimeUnit.SECONDS);
         } catch (TimeoutException | InterruptedException | BrokenBarrierException e) {
            System.out.println("⌛️ Esquiador " + esquiador.getIdEsquiador() + " se canso de esperar para formar grupo de " + tipoClase + ".");
         }       
      }
  }



   //Confiteria
   public void entrarYPagar(Esquiador esquiador) {
      try {
         confiteria.entrarYPagar(esquiador);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   public void retirarComida(Esquiador esquiador) {
      try {
         confiteria.retirarComida(esquiador);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   public void finRetirarComida(Esquiador esquiador) {
      try {
         confiteria.finRetirarComida(esquiador);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   public void retirarPostre(Esquiador esquiador) {
      try {
         confiteria.retirarPostre(esquiador);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   public void finRetirarPostre(Esquiador esquiador) {
      try {
         confiteria.finRetirarPostre(esquiador);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   public void salirDeConfiteria(Esquiador esquiador) {

      confiteria.salirDeConfiteria(esquiador);
  
   }

}
