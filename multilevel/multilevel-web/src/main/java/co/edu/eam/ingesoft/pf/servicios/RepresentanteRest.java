package co.edu.eam.ingesoft.pf.servicios;


import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.edu.eam.ingesoft.pa2.bos.BOInventarioEJB;
import co.edu.eam.ingesoft.pa2.bos.BOProductoEJB;
import co.edu.eam.ingesoft.pa2.bos.BORepresentanteEJB;
import co.edu.eam.ingesoft.pa2.dto.RespuestaDTO;
import co.edu.eam.ingesoft.pa2.dto.invDTO;
import co.edu.eam.ingesoft.pa2.implementacion.InventarioEJB;
import co.edu.eam.ingesoft.pa2.negocio.entidades.Inventario;
import co.edu.eam.ingesoft.pa2.negocio.entidades.InventarioPK;
import co.edu.eam.ingesoft.pa2.negocio.entidades.Producto;
import co.edu.eam.ingesoft.pa2.negocio.entidades.Representante;


@Path("/representante")
public class RepresentanteRest {
	
	@EJB
	private BOInventarioEJB invEjb;
	
	@EJB
	private BOProductoEJB proE;
	@EJB
	private BORepresentanteEJB reE;
	
	
	@POST
	@Path("/inventario")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public RespuestaDTO listarInventario(@FormParam(value = "ced") int cedula) {
		
		List<Inventario> u = invEjb.listarInventario(cedula);
		if (u != null) {
			
			return new RespuestaDTO(u);
		} else {
			return new RespuestaDTO(null, "no hay inventario", "-1");
		}
	}
	
	@POST
	@Path("/productos")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public RespuestaDTO listarPro(@FormParam(value = "ced") int cedula) {
		
		List<Producto> u = proE.listarProductos();
		if (u != null) {
			
			return new RespuestaDTO(u);
		} else {
			return new RespuestaDTO(null, "no hay inventario", "-1");
		}
	}
//	
	
	@POST
	@Path("/editarprecio")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public RespuestaDTO editaPrecio(@FormParam(value = "re") int re,@FormParam(value = "idpro") int idpro,@FormParam(value = "precio") double pre) {
		InventarioPK pk = new InventarioPK(re, idpro);
		Inventario in = invEjb.buscar(pk);
		System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"+in.getPrecioBase());
		
		
		if (in != null) {
			in.setPrecioBase(pre);
			invEjb.editar(in);
			return new RespuestaDTO(true);
		} else {
			return new RespuestaDTO(null, "no hay inventario", "-1");
		}
	}
}
