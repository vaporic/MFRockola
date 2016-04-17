package com.mfrockola.classes;

public class Genero 
{
	private int indice;
	private String nombre;
	
	public Genero(int indice, String nombre)
	{
		setIndice(indice);
		setNombre(nombre);
	}
	
	public String toString()
	{
		String texto = String.format("%s", getNombre());
		return texto;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
