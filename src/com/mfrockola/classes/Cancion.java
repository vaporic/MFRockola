package com.mfrockola.classes;

public class Cancion 
{
	private int numeroCancion;
	private String genero;
	private String artista;
	private String nombreCancion;
	
	public Cancion(int numero, String genero, String artista, String cancion)
	{
		establecerNumeroCancion(numero);
		setGenero(genero);
		setArtista(artista);
		establecerNombreCancion(cancion);
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
		return String.format("%04d - %s - %s", obtenerNumero(),getArtista(), obtenerNombreCancion());
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}
}
