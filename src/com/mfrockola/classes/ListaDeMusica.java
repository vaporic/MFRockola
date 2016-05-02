package com.mfrockola.classes;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class ListaDeMusica
{
	private Cancion [] ListaMusicas;
	private Genero [] generos;
	private String direccion;
	private int generoSeleccionado = 0;
	
	public ListaDeMusica(String direccion)
	{
		this.setDireccion(direccion);
		generos = new Genero [0];
		ListaMusicas = new Cancion[0];
		
		contarGeneros();
		generarListaMusicas();
	}
	
	public void contarGeneros()
	{	
		String [] listaArchivos = null;
		
		File directorio = new File(direccion);
		
		if (directorio.isDirectory())
			listaArchivos = directorio.list();
		
		for (int contador = 0; contador< listaArchivos.length;++contador)
		{
			File archivoActual = new File(String.format("%s\\%s", direccion, listaArchivos[contador]));
			
			if (archivoActual.isDirectory())
			{				
				Genero[] generosProvisional = new Genero[generos.length+1];	
				
				for (int contador2 = 0; contador2 < generos.length; contador2++)
				{
					generosProvisional[contador2] = new Genero(0, generos[contador2].getNombre());
				}
	
				generos = generosProvisional;
				generos[generos.length-1] = new Genero(0, listaArchivos[contador]);
			}
		}
	}
	
	public void generarListaMusicas()
	{
		int numeroCancion = 0;
		
		for (int contador = 0; contador < generos.length; contador++)
		{
			File generoActual = new File(String.format("%s\\%s", direccion,generos[contador]));
			
			generos[contador].setIndice(numeroCancion);
			
			String[] directorio = generoActual.list();
			
			Cancion listaMusicasProvisional [] = new Cancion [ListaMusicas.length + directorio.length];
			
			for (int contador2 = 0; contador2< ListaMusicas.length; contador2++)
			{
				listaMusicasProvisional[contador2] = ListaMusicas[contador2];
			}
			
			for (int contador3 = 0; contador3 < directorio.length; contador3++)
			{
				listaMusicasProvisional[contador3 + ListaMusicas.length] = new Cancion(numeroCancion++, generos[contador].getNombre(), directorio[contador3]);
			}
			
			ListaMusicas = listaMusicasProvisional;
		}
	}
	
	public int subirGenero()
	{
		if (generoSeleccionado >= 0 && generoSeleccionado < generos.length - 1)
			++generoSeleccionado;
		
		return generos[generoSeleccionado].getIndice();
	}
	
	public int bajarGenero()
	{
		if (generoSeleccionado > 0 && generoSeleccionado < generos.length)
			--generoSeleccionado;
		
		return generos[generoSeleccionado].getIndice();
	}

	public Cancion[] getListaMusicas()
	{
		return ListaMusicas;
	}

	public void setListaMusicas(Cancion[] listaMusicas) 
	{
		ListaMusicas = listaMusicas;
	}

	public Genero[] getGeneros() {
		return generos;
	}

	public void setGeneros(Genero[] generos) {
		this.generos = generos;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getIndiceSeleccionado() {
		return generoSeleccionado;
	}

	public void setIndiceSeleccionado(int indiceSeleccionado) {
		this.generoSeleccionado = indiceSeleccionado;
	}
	
	public void revisarGeneros()
	{
		for (int contador = 0; contador < generos.length; contador++)
		{
			JOptionPane.showMessageDialog(null, String.format("%s", generos[contador].getIndice()));
		}
	}
}
