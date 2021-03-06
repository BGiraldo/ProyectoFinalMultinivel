package co.edu.eam.ingesoft.pa2.bos;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.edu.eam.ingesoft.pa2.excepcion.ExcepcionFuncional;
import co.edu.eam.ingesoft.pa2.implementacion.EJBGenerico;
import co.edu.eam.ingesoft.pa2.implementacion.InterfaceEJBRemote;
import co.edu.eam.ingesoft.pa2.negocio.entidades.Ciudad;

@Stateless
@LocalBean
public class BOCiudadEJB extends EJBGenerico<Ciudad> implements InterfaceEJBRemote<Ciudad>{
	@Override
	public Class getClase() {
		return Ciudad.class;
	}

	@Override
	public void crear(Ciudad entidad) {
		if (buscar(entidad.getId()) != null) {
			throw new ExcepcionFuncional("Ya existe una Ciudad con este codigo " + entidad.getId());
		} else {
			dao.crear(entidad);
		}

	}

	@Override
	public Ciudad buscar(Object pk) {
		return dao.buscar(pk);
	}

	@Override
	public void editar(Ciudad entidad) {
		if (buscar(entidad.getId()) != null) {
			dao.editar(entidad);
		} else {
			throw new ExcepcionFuncional("Aùn no existe una Ciudad con este codigo " + entidad.getId());
		}
	}

	@Override
	public void eliminar(Ciudad entidad) {
		if (buscar(entidad.getId()) != null) {
			dao.borrar(entidad);
		} else {
			throw new ExcepcionFuncional("Aùn no existe una Ciudad con este codigo " + entidad.getId());
		}
	}

	/**
	 * Lista las ciudades que hay en la BD
	 * @author Brayan Giraldo
	 * Correo : giraldo97@outlook.com
	 */
	public List<Ciudad> listarCiudades(){
		return dao.ejecutarNamedQuery(Ciudad.LISTAR_CIUDADES);
	}
	

}
