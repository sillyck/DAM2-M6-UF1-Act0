import java.io.File;

import ioc.dam.m6.exemples.gestiofitxers.FormatVistes;
import ioc.dam.m6.exemples.gestiofitxers.GestioFitxers;
import ioc.dam.m6.exemples.gestiofitxers.GestioFitxersException;
import ioc.dam.m6.exemples.gestiofitxers.TipusOrdre;

public class GestioFitxersImpl implements GestioFitxers{

	private int files=0;
	private int columnes = 3;
	
	private Object[][] contingut;
	private File carpetaDeTreball = null;
	
	//Contructor
	public GestioFitxersImpl() {
		carpetaDeTreball = File.listRoots()[0];
		actualitza();
	}

	public void actualitza() {	
		String[] fitxers = carpetaDeTreball.list(); //obtenir els noms
		
		//Calcular el nombre de files necessaries
		files = fitxers.length / columnes;
		if(files*columnes < fitxers.length) {
			files++; //si hi ha residu necessitem una fila mes
		}
		
		//dimensionar la matriu de contingut d'acord als resultats
		contingut = new String[files][columnes];
		
		//Omplir el contingut amb els noms obtinguts
		for(int i=0; i<columnes; i++) {
			for(int j=0; j<files; j++) {
				int ind = j*columnes+i;
				if(ind<fitxers.length) {
					contingut[j][i] = fitxers[ind];
				}else {
					contingut[j][i]="";
				}
			}
		}
	}
	
	@Override
	public void amunt() {
		if(carpetaDeTreball.getParentFile()!=null) {
			carpetaDeTreball = carpetaDeTreball.getParentFile();
			actualitza();
		}
		
	}

	@Override
	public void creaCarpeta(String arg0) throws GestioFitxersException {
		throw new UnsupportedOperationException("Not supported yet.");
		
	}

	@Override
	public void creaFitxer(String arg0) throws GestioFitxersException {
		throw new UnsupportedOperationException("Not supported yet.");
		
	}

	@Override
	public void elimina(String arg0) throws GestioFitxersException {
		throw new UnsupportedOperationException("Not supported yet.");
		
	}

	@Override
	public void entraA(String nomCarpeta) throws GestioFitxersException {
		File file = new File(carpetaDeTreball, nomCarpeta);
		
		//es controla que el nom correspongui a una carpeta existent
		if (!file.isDirectory()) {
			throw new GestioFitxersException("Error. S'esperava un directori però "+ file.getAbsolutePath()+" no és un directori");
		}
		
		//es controla que es tinguin permisos per llegir la carpeta
		if(!file.canRead()) {
			throw new GestioFitxersException("Alerta. No podeu accedir a " + file.getAbsolutePath() + ". No teniu prou permisos");
		}
		//nova assignació de la carpeta de treball
		carpetaDeTreball = file;
				
		//es requereix actualitzar el contingut
		actualitza();
	}

	@Override
	public boolean esPotEscriure(String arg0) throws GestioFitxersException {
		throw new UnsupportedOperationException("Not supported yet.");
		//return false;
	}

	@Override
	public boolean esPotExecutar(String arg0) throws GestioFitxersException {
		throw new UnsupportedOperationException("Not supported yet.");
		//return false;
	}

	@Override
	public boolean esPotLlegir(String arg0) throws GestioFitxersException {
		throw new UnsupportedOperationException("Not supported yet.");
		//return false;
	}

	@Override
	public String getAdrecaCarpeta() {
		return carpetaDeTreball.getAbsolutePath();
	}

	@Override
	public int getColumnes() {
		return columnes;
	}

	@Override
	public Object[][] getContingut() {
		return contingut;
	}

	@Override
	public String getEspaiDisponibleCarpetaTreball() {
		throw new UnsupportedOperationException("Not supported yet.");
		//return null;
	}

	@Override
	public String getEspaiTotalCarpetaTreball() {
		throw new UnsupportedOperationException("Not supported yet.");
		//return null;
	}

	@Override
	public int getFiles() {
		return files;
	}

	@Override
	public FormatVistes getFormatContingut() {
		throw new UnsupportedOperationException("Not supported yet.");
		//return null;
	}

	@Override
	public String getInformacio(String arg0) throws GestioFitxersException {
		throw new UnsupportedOperationException("Not supported yet.");
		//return null;
	}

	@Override
	public boolean getMostrarOcults() {
		throw new UnsupportedOperationException("Not supported yet.");
		//return false;
	}

	@Override
	public String getNomCarpeta() {
		return carpetaDeTreball.getAbsolutePath();
	}

	@Override
	public TipusOrdre getOrdenat() {
		throw new UnsupportedOperationException("Not supported yet.");
		//return null;
	}

	@Override
	public String[] getTitolColumnes() {
		throw new UnsupportedOperationException("Not supported yet.");
		//return null;
	}

	@Override
	public long getUltimaModificacio(String arg0) throws GestioFitxersException {
		throw new UnsupportedOperationException("Not supported yet.");
		//return 0;
	}

	@Override
	public String nomArrel(int arg0) {
		throw new UnsupportedOperationException("Not supported yet.");
		//return null;
	}

	@Override
	public int numArrels() {
		throw new UnsupportedOperationException("Not supported yet.");
		//return 0;
	}

	@Override
	public void reanomena(String arg0, String arg1) throws GestioFitxersException {
		throw new UnsupportedOperationException("Not supported yet.");
		
	}

	@Override
	public void setAdrecaCarpeta(String adreca) throws GestioFitxersException {
		File file = new File(adreca);
		//es controla que l'adreça passada existeixi i sigui un directori
		
		if (!file.isDirectory()) {
			throw new GestioFitxersException("Error. S'esperava un directori però "+ file.getAbsolutePath()+" no és un directori");
		}
		
		//es controla que es tinguin permisos per llegir la carpeta
		if(!file.canRead()) {
			throw new GestioFitxersException("Alerta. No podeu accedir a " + file.getAbsolutePath() + ". No teniu prou permisos");
		}
		
		//nova assignació de la carpeta de treball
		carpetaDeTreball = file;
		
		//es requereix actualitzar el contingut
		actualitza();
	}

	@Override
	public void setColumnes(int columnes) {
		this.columnes = columnes;
		
	}

	@Override
	public void setEsPotEscriure(String arg0, boolean arg1) throws GestioFitxersException {
		throw new UnsupportedOperationException("Not supported yet.");
		
	}

	@Override
	public void setEsPotExecutar(String arg0, boolean arg1) throws GestioFitxersException {
		throw new UnsupportedOperationException("Not supported yet.");
		
	}

	@Override
	public void setEsPotLlegir(String arg0, boolean arg1) throws GestioFitxersException {
		throw new UnsupportedOperationException("Not supported yet.");
		
	}

	@Override
	public void setFormatContingut(FormatVistes arg0) {
		throw new UnsupportedOperationException("Not supported yet.");
		
	}

	@Override
	public void setMostrarOcults(boolean arg0) {
		throw new UnsupportedOperationException("Not supported yet.");
		
	}

	@Override
	public void setOrdenat(TipusOrdre arg0) {
		throw new UnsupportedOperationException("Not supported yet.");
		
	}

	@Override
	public void setUltimaModificacio(String arg0, long arg1) throws GestioFitxersException {
		throw new UnsupportedOperationException("Not supported yet.");
		
	}

}
