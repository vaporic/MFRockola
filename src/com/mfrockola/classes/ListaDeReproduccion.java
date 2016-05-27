package com.mfrockola.classes;

public class ListaDeReproduccion 
{
	private Cancion [] cancionesEnLista;
	
	public ListaDeReproduccion()
	{
		cancionesEnLista = new Cancion[1];
	}
		
	public void agregarCanciones(Cancion musicaAgregar)
	{
		Cancion[] provisional = new Cancion[cancionesEnLista.length + 1];
		
		if (cancionesEnLista [0] == null)
		{
			cancionesEnLista[0] = musicaAgregar;
		}
		else
		{
			for (int contador = 0; contador < cancionesEnLista.length; ++contador)
			{
				provisional[contador] = cancionesEnLista[contador];
			}
			
			cancionesEnLista = provisional;
			
			cancionesEnLista[cancionesEnLista.length-1] = musicaAgregar;
		}
		
	}
	
	public String obtenerCancionAReproducir()
	{
		if (cancionesEnLista[0]==null)
			return null;
		else
			return cancionesEnLista[0].obtenerNombreCancion();
	}
	
	public int obtenerNumero()
	{
		return cancionesEnLista[0].obtenerNumero();
	}
	
	public String obtenerGenero()
	{
		return cancionesEnLista[0].getGenero();
	}

	public String obtenerArtista() { return cancionesEnLista[0].getArtista(); }
	
	public void quitarMusica()
	{	
		if (cancionesEnLista.length == 1)
		{
			cancionesEnLista[0]= null;
		}
		else
		{
			Cancion[] provisional = new Cancion [cancionesEnLista.length - 1];
			for (int contador = 1; contador < cancionesEnLista.length; ++contador)
			{
				provisional[contador-1] = cancionesEnLista[contador];
			}
			cancionesEnLista = provisional;
		}
	}
	
	public int obtenerTamanio()
	{
		return cancionesEnLista.length;
	}
	
	public Cancion[] obtenerCancionesEnLista()
	{
		return cancionesEnLista;
	}
}
