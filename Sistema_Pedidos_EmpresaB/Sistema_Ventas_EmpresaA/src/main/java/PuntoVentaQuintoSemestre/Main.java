package PuntoVentaQuintoSemestre;

import VIEW.VistaPrincipal;
import CONTROLLER.ControladorPrincipal;

public class Main {
	public static void main(String[] args) {
		
	    System.out.println("🚀 Iniciando programa...");
	    
	    try {
	        System.out.println("1. Creando Vista...");
	        VistaPrincipal vista = new VistaPrincipal();
	        
	        System.out.println("2. Creando Controlador...");
	        
	        new ControladorPrincipal(vista, "Administrador");
	        
	        System.out.println("3. Haciendo visible la ventana...");
	        vista.setVisible(true);
	        
	        System.out.println("✅ Programa corriendo.");
	    } catch (Exception e) {
	        System.out.println("❌ EXPLOTÓ EN EL MAIN: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
}