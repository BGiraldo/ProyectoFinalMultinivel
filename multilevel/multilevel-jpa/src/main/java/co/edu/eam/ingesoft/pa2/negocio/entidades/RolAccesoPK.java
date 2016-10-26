package co.edu.eam.ingesoft.pa2.negocio.entidades;

import java.io.Serializable;

public class RolAccesoPK implements Serializable{

	/*
	 * atributos
	 */
	
	private int rol;
	
	private int acceso;

	/*
	 * Constructores
	 */
	
	public RolAccesoPK(int rol, int acceso) {
		super();
		this.rol = rol;
		this.acceso = acceso;
	}

	public RolAccesoPK() {
	
	}

	/*
	 * Getters and setters
	 */
	
	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public int getAcceso() {
		return acceso;
	}

	public void setAcceso(int acceso) {
		this.acceso = acceso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + acceso;
		result = prime * result + rol;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolAccesoPK other = (RolAccesoPK) obj;
		if (acceso != other.acceso)
			return false;
		if (rol != other.rol)
			return false;
		return true;
	}
	
	
	
	
	
}
