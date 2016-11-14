package co.edu.eam.ingesoft.pf.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import co.edu.eam.ingesoft.pa2.bos.BOCategoriaRepresentanteEJB;
import co.edu.eam.ingesoft.pa2.bos.BOCiudadEJB;
import co.edu.eam.ingesoft.pa2.bos.BODireccionEJB;
import co.edu.eam.ingesoft.pa2.bos.BOLoginEJB;
import co.edu.eam.ingesoft.pa2.bos.BOPersonaDireccionEJB;
import co.edu.eam.ingesoft.pa2.bos.BOPersonaEJB;
import co.edu.eam.ingesoft.pa2.bos.BORepresentanteEJB;
import co.edu.eam.ingesoft.pa2.negocio.entidades.CategoriaRepresentante;
import co.edu.eam.ingesoft.pa2.negocio.entidades.Ciudad;
import co.edu.eam.ingesoft.pa2.negocio.entidades.Direccion;
import co.edu.eam.ingesoft.pa2.negocio.entidades.Login;
import co.edu.eam.ingesoft.pa2.negocio.entidades.Persona;
import co.edu.eam.ingesoft.pa2.negocio.entidades.PersonaDireccion;
import co.edu.eam.ingesoft.pa2.negocio.entidades.Representante;
import co.edu.eam.ingesoft.pa2.negocio.enumeraciones.EstadoRepresentanteENUM;
import co.edu.eam.ingesoft.pa2.negocio.enumeraciones.TipoDocumentoENUM;
import co.edu.eam.ingesoft.pa2.negocio.enumeraciones.TipoPersonaENUM;
import co.edu.eam.ingesoft.pf.interceptores.ExceptionLogger;

@Named("ControladorManejoUsuario")
@ViewScoped
@ExceptionLogger
public class ControladorManejoUsuario implements Serializable {

	private char tipeUser;

	private Representante persona;

	private List<Ciudad> ciudades;

	private List<CategoriaRepresentante> categorias;

	private List<Representante> afiliadores;

	private Direccion direccion;

	@EJB
	private BOPersonaEJB personaEJB;

	@EJB
	private BORepresentanteEJB representanteEJB;

	@EJB
	private BOCiudadEJB ciudadEJB;

	@EJB
	private BOCategoriaRepresentanteEJB categoriaEJB;

	@EJB
	private BODireccionEJB direccionEJB;

	@EJB
	private BOPersonaDireccionEJB personaDireccionEJB;

	private List<EstadoRepresentanteENUM> estados;

	private List<TipoDocumentoENUM> tipoDocumentos;

	private Login login;

	@EJB
	private BOLoginEJB loginEJB;

	@PostConstruct
	public void inicializar() {
		persona = new Representante();
		login = new Login();
		direccion = new Direccion();
		ciudades = ciudadEJB.listarCiudades();
		categorias = categoriaEJB.listarCategorias();
		afiliadores = representanteEJB.listarRepresentantes();
		cargarEstadosYtipoDocumentos();
	}

	/**
	 * Carga las listas para los combos estados representante y tipo documentos
	 * 
	 * @author Brayan Giraldo Correo : giraldo97@outlook.com
	 */
	public void cargarEstadosYtipoDocumentos() {
		estados = new ArrayList<>();
		tipoDocumentos = new ArrayList<>();
		EstadoRepresentanteENUM[] estays = EstadoRepresentanteENUM.values();
		TipoDocumentoENUM[] tipoDocs = TipoDocumentoENUM.values();

		for (int i = 0; i < estays.length; i++) {
			estados.add(estays[i]);
		}

		for (int i = 0; i < tipoDocs.length; i++) {
			tipoDocumentos.add(tipoDocs[i]);
		}

	}

	/**
	 * Carga los campos dependiendo del usuario a registrar
	 * 
	 * @author Brayan Giraldo Correo : giraldo97@outlook.com
	 */
	public boolean cargarCampos() {
		if (tipeUser == 'A' || tipeUser == 'C') {
			return false;
		} else if (tipeUser == 'R') {
			return true;
		}
		return false;

	}

	/**
	 * 
	 * @author Brayan Giraldo Correo : giraldo97@outlook.com
	 */
	public void registrarUsuario() {

		Persona person = personaEJB.buscar(persona.getId());

		if (person == null) {

			if (tipeUser == 'A' || tipeUser == 'C') {

				TipoPersonaENUM typer = null;

				if (tipeUser == 'A')
					typer = TipoPersonaENUM.ADMINISTRADOR;
				else
					typer = TipoPersonaENUM.CLIENTE;

				login.setId(persona.getId());

				person = new Persona(persona.getId(), persona.getEmail(), login, persona.getNombre(),
						persona.getApellido(), persona.getFechaNacimiento(), persona.getGenero(), persona.getTelefono(),
						typer, persona.getTipoDocumento());

				int valId = direccionEJB.cantidadDirecciones() + 1;

				Direccion dir = new Direccion(valId, direccion.getCiudad(), direccion.getDescripcion());

				direccionEJB.crear(dir);
				loginEJB.crear(login);
				personaEJB.crear(person);
				PersonaDireccion personaDir = new PersonaDireccion(person, dir);
				personaDireccionEJB.crear(personaDir);
				Messages.addGlobalInfo("Registrado Correctamente");
				inicializar();

			} else if(tipeUser == 'R'){

				login.setId(persona.getId());

				person = new Representante(persona.getId(), persona.getEmail(), login, persona.getNombre(),
						persona.getApellido(), persona.getFechaNacimiento(), persona.getGenero(), persona.getTelefono(),
						TipoPersonaENUM.REPRESENTANTE, persona.getTipoDocumento(), persona.getCategoria(),
						persona.getSueldoActual(), persona.getAcomuladoTotal(), persona.getEstado(),
						persona.getAfiliador(), persona.getFechaAfiliacion());

				int valId = direccionEJB.cantidadDirecciones()+1;
				Direccion dir = new Direccion(valId, direccion.getCiudad(), direccion.getDescripcion());

				direccionEJB.crear(dir);
				loginEJB.crear(login);
				representanteEJB.crear((Representante) person);
				PersonaDireccion personaDir = new PersonaDireccion(person, dir);
				personaDireccionEJB.crear(personaDir);
				Messages.addGlobalInfo("Registrado Correctamente");
				inicializar();

			}else{
				Messages.addGlobalWarn("Seleccione tipo de usuario");
			}
		} else {
			Messages.addGlobalWarn("Ya existe Usuario con esta identificación " + persona.getId());
		}
	}

	public void buscarUsuario() {

	}

	public void editarUsuario() {

	}

	public void removerUsuario() {

	}

	/**
	 * @return the tipeUser
	 */
	public char getTipeUser() {
		return tipeUser;
	}

	/**
	 * @param tipeUser
	 *            the tipeUser to set
	 */
	public void setTipeUser(char tipeUser) {
		this.tipeUser = tipeUser;
	}

	/**
	 * @return the persona
	 */
	public Representante getPersona() {
		return persona;
	}

	/**
	 * @param persona
	 *            the persona to set
	 */
	public void setPersona(Representante persona) {
		this.persona = persona;
	}

	/**
	 * @return the ciudades
	 */
	public List<Ciudad> getCiudades() {
		return ciudades;
	}

	/**
	 * @param ciudades
	 *            the ciudades to set
	 */
	public void setCiudades(List<Ciudad> ciudades) {
		this.ciudades = ciudades;
	}

	/**
	 * @return the categorias
	 */
	public List<CategoriaRepresentante> getCategorias() {
		return categorias;
	}

	/**
	 * @param categorias
	 *            the categorias to set
	 */
	public void setCategorias(List<CategoriaRepresentante> categorias) {
		this.categorias = categorias;
	}

	/**
	 * @return the afiliadores
	 */
	public List<Representante> getAfiliadores() {
		return afiliadores;
	}

	/**
	 * @param afiliadores
	 *            the afiliadores to set
	 */
	public void setAfiliadores(List<Representante> afiliadores) {
		this.afiliadores = afiliadores;
	}

	/**
	 * @return the direccion
	 */
	public Direccion getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            the direccion to set
	 */
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the personaEJB
	 */
	public BOPersonaEJB getPersonaEJB() {
		return personaEJB;
	}

	/**
	 * @param personaEJB
	 *            the personaEJB to set
	 */
	public void setPersonaEJB(BOPersonaEJB personaEJB) {
		this.personaEJB = personaEJB;
	}

	/**
	 * @return the representanteEJB
	 */
	public BORepresentanteEJB getRepresentanteEJB() {
		return representanteEJB;
	}

	/**
	 * @param representanteEJB
	 *            the representanteEJB to set
	 */
	public void setRepresentanteEJB(BORepresentanteEJB representanteEJB) {
		this.representanteEJB = representanteEJB;
	}

	/**
	 * @return the ciudadEJB
	 */
	public BOCiudadEJB getCiudadEJB() {
		return ciudadEJB;
	}

	/**
	 * @param ciudadEJB
	 *            the ciudadEJB to set
	 */
	public void setCiudadEJB(BOCiudadEJB ciudadEJB) {
		this.ciudadEJB = ciudadEJB;
	}

	/**
	 * @return the categoriaEJB
	 */
	public BOCategoriaRepresentanteEJB getCategoriaEJB() {
		return categoriaEJB;
	}

	/**
	 * @param categoriaEJB
	 *            the categoriaEJB to set
	 */
	public void setCategoriaEJB(BOCategoriaRepresentanteEJB categoriaEJB) {
		this.categoriaEJB = categoriaEJB;
	}

	/**
	 * @return the direccionEJB
	 */
	public BODireccionEJB getDireccionEJB() {
		return direccionEJB;
	}

	/**
	 * @param direccionEJB
	 *            the direccionEJB to set
	 */
	public void setDireccionEJB(BODireccionEJB direccionEJB) {
		this.direccionEJB = direccionEJB;
	}

	/**
	 * @return the personaDireccionEJB
	 */
	public BOPersonaDireccionEJB getPersonaDireccionEJB() {
		return personaDireccionEJB;
	}

	/**
	 * @param personaDireccionEJB
	 *            the personaDireccionEJB to set
	 */
	public void setPersonaDireccionEJB(BOPersonaDireccionEJB personaDireccionEJB) {
		this.personaDireccionEJB = personaDireccionEJB;
	}

	/**
	 * @return the estados
	 */
	public List<EstadoRepresentanteENUM> getEstados() {
		return estados;
	}

	/**
	 * @param estados
	 *            the estados to set
	 */
	public void setEstados(List<EstadoRepresentanteENUM> estados) {
		this.estados = estados;
	}

	/**
	 * @return the tipoDocumentos
	 */
	public List<TipoDocumentoENUM> getTipoDocumentos() {
		return tipoDocumentos;
	}

	/**
	 * @param tipoDocumentos
	 *            the tipoDocumentos to set
	 */
	public void setTipoDocumentos(List<TipoDocumentoENUM> tipoDocumentos) {
		this.tipoDocumentos = tipoDocumentos;
	}

	/**
	 * @return the login
	 */
	public Login getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(Login login) {
		this.login = login;
	}

}
