package Controladores;

/**
 * Programa que permite el acceso a una base de datos de comics. Mediante JDBC con mySql
 * Las ventanas graficas se realizan con JavaFX.
 * El programa permite:
 *  - Conectarse a la base de datos.
 *  - Ver la base de datos completa o parcial segun parametros introducidos.
 *  - Guardar el contenido de la base de datos en un fichero .txt y .xlsx,CSV
 *  - Copia de seguridad de la base de datos en formato .sql
 *  - Introducir comics a la base de datos.
 *  - Modificar comics de la base de datos.
 *  - Eliminar comics de la base de datos(Solamente cambia el estado de "En posesion" a "Vendido". Los datos siguen en la bbdd pero estos no los muestran el programa
 *  - Ver frases de personajes de comics
 *  - Opcion de escoger algo para leer de forma aleatoria.
 *
 *  Esta clase permite acceder a la ventana para modificar datos.
 *
 *  Version Final
 *
 *  Por Alejandro Rodriguez
 *
 *  Twitter: @silverAlox
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import Funcionamiento.Comic;
import Funcionamiento.ConexionBBDD;
import Funcionamiento.Libreria;
import Funcionamiento.NavegacionVentanas;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ModificarDatosController {

	@FXML
	private Button botonModificar;

	@FXML
	private Button botonLimpiarComic;

	@FXML
	private Button botonMostrarParametro;

	@FXML
	private Button botonSalir;

	@FXML
	private Button botonVolver;

	@FXML
	private Button botonbbdd;

	@FXML
	private TextField anioPublicacion;

	@FXML
	private TextField anioPublicacionMod;

	@FXML
	private TextField idComic;

	@FXML
	private TextField idComicMod;

	@FXML
	private TextField nombreComic;

	@FXML
	private TextField nombreComicMod;

	@FXML
	private TextField nombreDibujante;

	@FXML
	private TextField nombreDibujanteMod;

	@FXML
	private TextField nombreEditorial;

	@FXML
	private TextField nombreEditorialMod;

	@FXML
	private TextField nombreFirma;

	@FXML
	private TextField nombreFirmaMod;

	@FXML
	private TextField nombreFormato;

	@FXML
	private TextField nombreFormatoMod;

	@FXML
	private TextField nombreGuionista;

	@FXML
	private TextField nombreGuionistaMod;

	@FXML
	private TextField nombreProcedencia;

	@FXML
	private TextField nombreProcedenciaMod;

	@FXML
	private TextField nombreVariante;

	@FXML
	private TextField nombreVarianteMod;

	@FXML
	private TextField numeroComic;

	@FXML
	private TextField numeroComicMod;

	@FXML
	private TextArea pantallaInformativa;

	@FXML
	private Label idMod;

	@FXML
	public TableView<Comic> tablaBBDD;

	@FXML
	private TableColumn<Comic, String> ID;

	@FXML
	private TableColumn<Comic, String> numero;

	@FXML
	private TableColumn<Comic, String> procedencia;

	@FXML
	private TableColumn<Comic, String> variante;

	@FXML
	private TableColumn<Comic, String> dibujante;

	@FXML
	private TableColumn<Comic, String> editorial;

	@FXML
	private TableColumn<Comic, String> fecha;

	@FXML
	private TableColumn<Comic, String> firma;

	@FXML
	private TableColumn<Comic, String> formato;

	@FXML
	private TableColumn<Comic, String> guionista;

	@FXML
	private TableColumn<Comic, String> nombre;

	private NavegacionVentanas nav = new NavegacionVentanas();

	private Libreria libreria = new Libreria();

	private Comic comic = new Comic();

	private Connection conn = ConexionBBDD.conexion();

	/**
	 * Limpia todos los datos de los textField que hay en pantalla
	 * @param event
	 */
	@FXML
	void limpiarDatos(ActionEvent event) {

		// Campos de busqueda por parametro
		idComic.setText("");

		nombreComic.setText("");

		anioPublicacion.setText("");

		nombreComic.setText("");

		numeroComic.setText("");

		nombreDibujante.setText("");

		nombreEditorial.setText("");

		nombreFirma.setText("");

		nombreFormato.setText("");

		nombreGuionista.setText("");

		nombreProcedencia.setText("");

		nombreVariante.setText("");

		numeroComic.setText("");

		// Campos de datos a modificar
		idComicMod.setText("");

		nombreComicMod.setText("");

		numeroComicMod.setText("");

		nombreVarianteMod.setText("");

		nombreFirmaMod.setText("");

		nombreEditorialMod.setText("");

		nombreFormatoMod.setText("");

		nombreProcedenciaMod.setText("");

		anioPublicacionMod.setText("");

		nombreGuionistaMod.setText("");

		nombreDibujanteMod.setText("");

		pantallaInformativa.setText(null);

		pantallaInformativa.setOpacity(0);
	}

	/**
	 * Llamada a funcion que modifica los datos de 1 comic en la base de datos.
	 * @param event
	 */
	@FXML
	void modificarDatos(ActionEvent event) {

		modificacionDatos();
		libreria.ordenarBBDD();
		libreria.reiniciarBBDD();
	}

	/////////////////////////////////
	//// FUNCIONES////////////////////
	/////////////////////////////////

	/**
	 * Devuelve un array con los datos de los TextField correspondientes a la los comics que se encuentran en la bbdd
	 * @return
	 */
	public String[] camposComicActuales() {
		String campos[] = new String[11];

		campos[0] = idComic.getText();

		campos[1] = nombreComic.getText();

		campos[2] = numeroComic.getText();

		campos[3] = nombreVariante.getText();

		campos[4] = nombreFirma.getText();

		campos[5] = nombreEditorial.getText();

		campos[6] = nombreFormato.getText();

		campos[7] = nombreProcedencia.getText();

		campos[8] = anioPublicacion.getText();

		campos[9] = nombreGuionista.getText();

		campos[10] = nombreDibujante.getText();

		return campos;
	}

	/**
	 * Devuelve un array con los datos de los TextField correspondientes a la los comics se van a modificar
	 * @return
	 */
	public String[] camposComicModificar() {
		String campos[] = new String[11];

		campos[0] = idComicMod.getText();

		campos[1] = nombreComicMod.getText();

		campos[2] = numeroComicMod.getText();

		campos[3] = nombreVarianteMod.getText();

		campos[4] = nombreFirmaMod.getText();

		campos[5] = nombreEditorialMod.getText();

		campos[6] = nombreFormatoMod.getText();

		campos[7] = nombreProcedenciaMod.getText();

		campos[8] = anioPublicacionMod.getText();

		campos[9] = nombreGuionistaMod.getText();

		campos[10] = nombreDibujanteMod.getText();

		return campos;
	}

	/**
	 * Funcion que modifica 1 comic de la base de datos con los parametros que queramos
	 */
	public void modificacionDatos() {

		String sentenciaSQL = "UPDATE Comicsbbdd set nomComic = ?,numComic = ?,nomVariante = ?,"
				+ "Firma = ?,nomEditorial = ?,formato = ?,Procedencia = ?,anioPubli = ?,"
				+ "nomGuionista = ?,nomDibujante = ? where ID = ?";

		comic = libreria.comicDatos(idComicMod.getText());

		if (nav.alertaModificar()) { //Llamada a alerta

			try {
				PreparedStatement ps = null;

				ps = conn.prepareStatement(sentenciaSQL);

				if (idComicMod.getText().length() != 0) {
					ps.setString(11, idComicMod.getText());
					ComicBorrar(ps);
				} else { //Si no hay ID en los parametros, saltara el siguiente error
					pantallaInformativa.setOpacity(1);
					pantallaInformativa.setStyle("-fx-background-color: #F53636");
					idComicMod.setStyle("-fx-background-color: #F53636");
					idMod.setStyle("-fx-background-color: #F53636");
					pantallaInformativa.setText("ERROR. No ha puesto ningun \nID en la busqueda.");
				}

				if (ps.executeUpdate() == 1) { //Si se ha modificado correctamente, saltara el siguiente mensaje
					pantallaInformativa.setOpacity(1);
					pantallaInformativa.setStyle("-fx-background-color: #A0F52D");
					pantallaInformativa.setText("Ha modificado correctamente: " + comic.toString());
				}
			} catch (SQLException ex) {
				nav.alertaException(ex.toString());
			}
		} else { // Si se cancela el borra del comic, saltara el siguiente mensaje.
			pantallaInformativa.setOpacity(1);
			pantallaInformativa.setStyle("-fx-background-color: #F53636");
			pantallaInformativa.setText("Modificacion cancelada.");
		}
	}

	/**
	 * Devuelve un objeto Comic con los nuevos datos de un comic. En caso de tener el espacio en blanco, el valor del parametro sera el que tenia originalmente.
	 * @param ps
	 * @return
	 */
	public Comic ComicBorrar(PreparedStatement ps) {

		String nombre = "", numero = "", variante = "", firma = "", editorial = "", formato = "", procedencia = "",
				fecha = "", guionista = "", dibujante = "";

		String datosModificados[] = camposComicModificar();

		try {
			if (datosModificados[1].length() != 0) {
				ps.setString(1, datosModificados[1]);
				nombre = datosModificados[1];
			} else {
				nombre = comic.getNombre();
				ps.setString(1, nombre);
			}
			if (datosModificados[2].length() != 0) {
				ps.setString(2, datosModificados[2]);
				numero = datosModificados[2];
			} else {
				numero = comic.getNumero();
				ps.setString(2, numero);
			}
			if (datosModificados[3].length() != 0) {
				ps.setString(3, datosModificados[3]);
				variante = datosModificados[3];
			} else {
				variante = comic.getVariante();
				ps.setString(3, variante);
			}
			if (datosModificados[4].length() != 0) {
				ps.setString(4, datosModificados[4]);
				firma = datosModificados[4];
			} else {
				firma = comic.getFirma();
				ps.setString(4, firma);
			}
			if (datosModificados[5].length() != 0) {
				ps.setString(5, datosModificados[5]);
				editorial = datosModificados[5];
			} else {
				editorial = comic.getEditorial();
				ps.setString(5, editorial);
			}
			if (datosModificados[6].length() != 0) {
				ps.setString(6, datosModificados[6]);
				formato = datosModificados[6];
			} else {
				formato = comic.getFormato();
				ps.setString(6, formato);
			}
			if (datosModificados[7].length() != 0) {
				ps.setString(7, datosModificados[7]);
				procedencia = datosModificados[7];
			} else {
				procedencia = comic.getProcedencia();
				ps.setString(7, procedencia);
			}
			if (datosModificados[8].length() != 0) {
				ps.setString(8, datosModificados[8]);
				fecha = datosModificados[8];
			} else {
				fecha = comic.getFecha();
				ps.setString(8, fecha);
			}
			if (datosModificados[9].length() != 0) {
				ps.setString(9, datosModificados[9]);
				guionista = datosModificados[9];
			} else {
				guionista = comic.getGuionista();
				ps.setString(9, guionista);
			}
			if (datosModificados[10].length() != 0) {
				ps.setString(10, datosModificados[10]);
				dibujante = datosModificados[10];
			} else {
				dibujante = comic.getDibujante();
				ps.setString(10, dibujante);
			}
		} catch (SQLException ex) {
			nav.alertaException(ex.toString());
		}

		return comic;
	}

	/**
	 * Muestra datos por parametro
	 * @param event
	 */
	@FXML
	void mostrarPorParametro(ActionEvent event) {

		nombreColumnas();
		listaPorParametro();
	}

	/**
	 * Muestra toda la base de datos.
	 *
	 * @param event
	 */
	@FXML
	void verTodabbdd(ActionEvent event) throws SQLException {

		nombreColumnas();
		tablaBBDD(libreriaCompleta());

	}

	/**
	 * Funcion que busca en el arrayList el o los comics que tengan coincidencia con los datos introducidos en el TextField
	 * @return
	 */
	public void listaPorParametro() {
		String datosComic[] = camposComicActuales();

		Comic comic = new Comic(datosComic[0], datosComic[1], datosComic[2], datosComic[3], datosComic[4],
				datosComic[5], datosComic[6], datosComic[7], datosComic[8], datosComic[9], datosComic[10], "");

		tablaBBDD(libreriaParametro(comic));
	}

	/**
	 * Muestra los comics que coincidan con los parametros introducidos
	 * @param comic
	 * @return
	 */
	public List<Comic> libreriaParametro(Comic comic) {
		List<Comic> listComic = FXCollections.observableArrayList(libreria.filtadroBBDD(comic));

		if (listComic.size() == 0) {
			pantallaInformativa.setOpacity(1);
			pantallaInformativa.setStyle("-fx-background-color: #F53636");
			pantallaInformativa.setText("ERROR. No hay ningun dato en la base de datos");
		}
		return listComic;
	}

	/**
	 * Muestra todos los comics de la base de datos
	 * @return
	 */
	public List<Comic> libreriaCompleta() {
		List<Comic> listComic = FXCollections.observableArrayList(libreria.verLibreria());

		if (listComic.size() == 0) {
			pantallaInformativa.setOpacity(1);
			pantallaInformativa.setStyle("-fx-background-color: #F53636");
			pantallaInformativa.setText("ERROR. No hay ningun dato en la base de datos");
		}

		return listComic;
	}

	/**
	 * Obtiene los datos de los comics de la base de datos y los devuelve en el textView
	 * @param listaComic
	 */
	@SuppressWarnings("unchecked")
	public void tablaBBDD(List<Comic> listaComic) {
		tablaBBDD.getColumns().setAll(ID, nombre, numero, variante, firma, editorial, formato, procedencia, fecha,
				guionista, dibujante);
		tablaBBDD.getItems().setAll(listaComic);
	}

	/**
	 *  Permite dar valor a las celdas de la TableView
	 */
	private void nombreColumnas() {
		ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
		variante.setCellValueFactory(new PropertyValueFactory<>("variante"));
		firma.setCellValueFactory(new PropertyValueFactory<>("firma"));
		editorial.setCellValueFactory(new PropertyValueFactory<>("editorial"));
		formato.setCellValueFactory(new PropertyValueFactory<>("formato"));
		procedencia.setCellValueFactory(new PropertyValueFactory<>("procedencia"));
		fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		guionista.setCellValueFactory(new PropertyValueFactory<>("guionista"));
		dibujante.setCellValueFactory(new PropertyValueFactory<>("dibujante"));
	}

	/////////////////////////////////
	//// METODO LLAMADA A VENTANA////
	/////////////////////////////////

	/**
	 * Permite volver al menu de conexion a la base de datos.
	 *
	 * @param event
	 */
	@FXML
	void volverMenu(ActionEvent event) {

		nav.verMenuPrincipal();

		Stage myStage = (Stage) this.botonVolver.getScene().getWindow();
		myStage.close();
	}

	/////////////////////////////
	//// FUNCIONES PARA SALIR////
	/////////////////////////////

	/**
	 * Permite salir completamente del programa.
	 *
	 * @param event
	 */
	@FXML
	public void salirPrograma(ActionEvent event) {

		if (nav.salirPrograma(event)) {
			Stage myStage = (Stage) this.botonSalir.getScene().getWindow();
			myStage.close();
		}
	}

	/**
	 * Al cerrar la ventana, se cargara la ventana de verBBDD
	 *
	 */
	public void closeWindows() {

		nav.verMenuPrincipal();

		Stage myStage = (Stage) this.botonVolver.getScene().getWindow();
		myStage.close();
	}
}