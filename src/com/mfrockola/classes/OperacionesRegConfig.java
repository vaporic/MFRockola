package com.mfrockola.classes;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class OperacionesRegConfig 
{
	RegConfig datos;
		
	private ObjectInputStream entrada;
	
	public void abrirRegConfigLectura()
	{
		try
		{
			entrada = new ObjectInputStream(new FileInputStream("config.mfr"));
		}
		catch (IOException ioException)
		{
			System.err.println("Error al abrir el archivo");
		}
	}
	

	
	public RegConfig leerRegConfigLectura()
	{
		datos = null;
		
		try
		{
			datos = (RegConfig) entrada.readObject();
		}
		catch (EOFException endOfFileExcepcion)
		{
			return datos;
		}
		catch (ClassNotFoundException classNotFoundExcepcion)
		{
			System.err.println("Error al leer el archivo.");
		}
		catch (IOException ioExcepcion)
		{
			System.err.println("Error al leer el archivo.");
		}
		
		return datos;
	}
	
	public void cerrarRegConfigLectura()
	{
		try
		{
			if(entrada != null)
			{
				entrada.close();
				System.exit(0);
			}
		}
		catch (IOException ioExcepcion)
		{
			System.err.println("Error al cerrar el archivo");
			System.exit(1);
		}
	}
}
