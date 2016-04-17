package com.mfrockola.classes;

public class Cancion 
{
	private int numeroCancion;
	private String genero;
	private String nombreCancion;
	
	public Cancion(int numero, String genero, String cancion)
	{
		this.establecerNumeroCancion(numero);
		this.setGenero(genero);
		this.establecerNombreCancion(cancion);
	}
	
	public String obtenerNombreCancion()
	{
		return nombreCancion;
	}
	
	public int obtenerNumero()
	{
		return numeroCancion;
	}
	
	public void establecerNumeroCancion(int numeroCancion)
	{
		this.numeroCancion = numeroCancion;
	}
	
	public void establecerNombreCancion(String nombreCancion)
	{
		this.nombreCancion = nombreCancion;
	}
	
	public String toString()
	{
		return String.format("%04d - %s", obtenerNumero() , obtenerNombreCancion());
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}
}
