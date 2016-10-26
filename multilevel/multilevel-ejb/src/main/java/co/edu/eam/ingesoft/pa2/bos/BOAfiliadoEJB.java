package co.edu.eam.ingesoft.pa2.bos;

import javax.ejb.Stateless;

import co.edu.eam.ingesoft.pa2.excepcion.ExcepcionFuncional;
import co.edu.eam.ingesoft.pa2.implementacion.EJBGenerico;
import co.edu.eam.ingesoft.pa2.implementacion.InterfaceEJBRemote;
import co.edu.eam.ingesoft.pa2.negocio.entidades.Afiliado;

@Stateless
public class BOAfiliadoEJB extends EJBGenerico<Afiliado> implements InterfaceEJBRemote<Afiliado> {

	@Override
	public Class getClase() {
		return Afiliado.class;
	}

	@Override
	public void crear(Afiliado entidad) {
		if (buscar(entidad.getId()) != null) {
			throw new ExcepcionFuncional("Ya existe un afiliado con este codigo " + entidad.getId());
		} else {
			dao.crear(entidad);
		}

	}

	@Override
	public Afiliado buscar(Object pk) {
		return dao.buscar(pk);
	}

	@Override
	public void editar(Afiliado entidad) {
		if (buscar(entidad.getId()) != null) {
			dao.editar(entidad);
		} else {
			throw new ExcepcionFuncional("Aùn no existe un afiliado con este codigo " + entidad.getId());
		}
	}

	@Override
	public void eliminar(Afiliado entidad) {
		if (buscar(entidad.getId()) != null) {
			dao.borrar(entidad);
		} else {
			throw new ExcepcionFuncional("Aùn no existe un afiliado con este codigo " + entidad.getId());
		}
	}

}