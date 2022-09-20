import java.io.File;
import java.io.IOException;
import java.util.Date;

import ioc.dam.m6.exemples.gestiofitxers.ByteFormat;
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
		String[] fitxers = carpetaDeTreball.list(new FiltreFitxersOcults()); //obtenir els noms
		
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
	public String getInformacio(String nom) throws GestioFitxersException {
		ByteFormat byteFormat = new ByteFormat("#,##.0", ByteFormat.BYTE);
		StringBuilder strBuilder = new StringBuilder();
		File file = new File(carpetaDeTreball, nom);
		
		//Es controla que existeixi l'element a analitzar
		if(!file.exists()) {
			throw new GestioFitxersException("Error. No es pot obtenir informació de " + nom + ", no existeix.");
		}
		
		//S'escriu el titol
		strBuilder.append("INFORMACIÓ DEL SISTEMA);");
		strBuilder.append("\n\n");
		
		//S'afegeix un nom
		strBuilder.append("Nom: ");
		strBuilder.append(nom);
		strBuilder.append("\n");
		
		//El tipus de carpeta o fitxer
		strBuilder.append("Tipus: ");
		if(file.isFile()) {
			//es fitxer
			strBuilder.append("fitxer");
			strBuilder.append("\n");
			//S'escriu la mida
			strBuilder.append("Mida: ");
			strBuilder.append(byteFormat.format(file.length()));
			strBuilder.append("\n");
		}else {
			//es carpeta
			strBuilder.append("carpeta");
			strBuilder.append("\n");
			
			//S'indica el nombre d'elements continguts
			strBuilder.append("Contingut: ");
			strBuilder.append(file.list().length);
			strBuilder.append(" entrades\n");
		}
		//Afegim la ubicació
		strBuilder.append("Ubicació: ");
		/*
		 * Cal posar el try catch perque si
		 */
		try { 
			strBuilder.append(file.getCanonicalPath());
		} catch (IOException ex) {/*Mai es produira aquest error*/}
		strBuilder.append("\n");
		
		//Afegim la data de la ultima modificació
		strBuilder.append("Ultima modicicació: ");
		Date date = new Date(file.lastModified());
		strBuilder.append(date.toString());
		strBuilder.append("\n");
		
		//Indiquem si es o no un fitxer ocult
		strBuilder.append("Ocult: ");
		strBuilder.append(file.isHidden()?"Si":"No");
		strBuilder.append("\n");
		
		if(file.isDirectory()) {
			//Mostrem l'espai lliure
			strBuilder.append("Espai lliure: ");
			strBuilder.append(byteFormat.format(file.getFreeSpace()));
			strBuilder.append("\n");
			
			//Mostrem l'espai disponible
			strBuilder.append("Espai disponible: ");
			strBuilder.append(byteFormat.format(file.getUsableSpace()));
			strBuilder.append("\n");
			
			//Mostrem l'espai total
			strBuilder.append("Espai total: ");
			strBuilder.append(byteFormat.format(file.getTotalSpace()));
			strBuilder.append("\n");
		}
		return strBuilder.toString();
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
