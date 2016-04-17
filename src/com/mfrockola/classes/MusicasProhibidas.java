package com.mfrockola.classes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class MusicasProhibidas 
{
	int prohibidos [] = {-1};
	
	public MusicasProhibidas()
	{
		int delay = 1000*1800;
		
		Timer temporizador = new Timer(delay, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evento) 
			{
				int [] provisional = {10000};
				prohibidos = provisional;
			}
		});
		
		temporizador.start();
	}
	
	public void agregarProhibido(int numero)
	{
		
			int provisional []= new int[prohibidos.length+1];
			
			for(int contador = 0; contador < prohibidos.length; ++contador)
			{
				provisional[contador]=prohibidos[contador];
			}
			
			provisional[provisional.length-1] = numero;
			
			prohibidos = provisional;		
	}
	
	public boolean revisarProhibido(int numero)
	{
		boolean retorno = true;		
		
		for(int contador = 0; contador<prohibidos.length;++contador)
		{
			if(numero == prohibidos[contador])
				retorno = false;
		}
		
		return retorno;
	}
}
