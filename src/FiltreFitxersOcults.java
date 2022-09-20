import java.io.File;
import java.io.FilenameFilter;
import ioc.dam.m6.exemples.gestiofitxers.TipusOrdre;

public class FiltreFitxersOcults implements FilenameFilter{

	private TipusOrdre ordenat;
	private boolean mostrarOcults;
	private final FiltreFitxersOcults filtreFitxersOcults = new FiltreFitxersOcults();
	
	@Override
	public boolean accept(File pfile, String string) {
		File file = new File(pfile, string);
		return !file.isHidden();
	}
	
	public boolean getMostrarOcults() {
		return mostrarOcults;
	}
	
	public void setMostrarOcults(boolean ocults) {
		this.mostrarOcults=ocults;
		actualitza();
	}

	private void actualitza() {
		// TODO Auto-generated method stub
		
	}

}
