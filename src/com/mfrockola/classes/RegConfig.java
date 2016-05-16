package com.mfrockola.classes;

import java.awt.Color;
import java.io.Serializable;
import java.net.URL;

@SuppressWarnings("serial")
public class RegConfig implements Serializable
{
	private String direccionVideos;
	private String direccionVlc;
	private String direccionVideoPromocional;
	private int musicAleatoria;
	private int reinicioMusicas;
	private int cantidadCreditos;
	private boolean libre;
	private boolean videoPromocional;
	private int clickCreditos;
	private int teclaSubirLista;
	private int teclaBajarLista;
	private int teclaSubirGenero;
	private int teclaBajarGenero;
	private int teclaPantallaCompleta;
	private int teclaBorrar;
	private boolean cancelMusic;
	private String password;
	private int cantidadCreditosUsados;
	private int cantidadMonedasInsertadas;
	private boolean defaultBackground;
	private URL direccionFondo;
	private Color color1;
	private Color color2;
	
	public RegConfig(String direccionVideos, String direccionVlc,
			String direccionVideoPromocional, int musicAleatoria,int reinicioMusicas,int cantidadCreditos, boolean libre,
			boolean videoPromocional, int clickCreditos, int teclaSubirLista, int teclaBajarLista,
			int teclaSubirGenero, int teclaBajarGenero, int teclaPantallaCompleta, int teclaBorrar,
			boolean cancelMusic,String password, int cantidadCreditosUsados, int CantidadMonedasInsertadas, boolean defaultBackground,
			URL direccionFondo, Color color1, Color color2)
	{
		this.setDireccionVideos(direccionVideos);
		this.setDireccionVlc(direccionVlc);
		this.setMusicAleatoria(musicAleatoria);
		this.setReinicioMusicas(reinicioMusicas);
		this.setDireccionVideoPromocional(direccionVideoPromocional);
		this.setCantidadCreditos(cantidadCreditos);
		this.setLibre(libre);
		this.setVideoPromocional(videoPromocional);
		this.setClickCreditos(clickCreditos);
		this.setTeclaSubirLista(teclaSubirLista);
		this.setTeclaBajarLista(teclaBajarLista);
		this.setTeclaSubirGenero(teclaSubirGenero);
		this.setTeclaPantallaCompleta(teclaPantallaCompleta);
		this.setTeclaBorrar(teclaBorrar);
		this.setCancelMusic(cancelMusic);
		this.setPassword(password);
		this.setCantidadCreditosUsados(cantidadCreditosUsados);
		this.setCantidadMonedasInsertadas(CantidadMonedasInsertadas);
		setDefaultBackground(defaultBackground);
		this.setDireccionFondo(direccionFondo);
		this.setColor1(color1);
		this.setColor2(color2);
	}

	public String getDireccionVideos() 
	{
		return direccionVideos;
	}
	public void setDireccionVideos(String direccionVideos) 
	{
		this.direccionVideos = direccionVideos;
	}

	public String getDireccionVlc() {
		return direccionVlc;
	}

	public void setDireccionVlc(String direccionVlc) {
		this.direccionVlc = direccionVlc;
	}

	public int getMusicAleatoria() {
		return musicAleatoria;
	}

	public void setMusicAleatoria(int musicAleatoria) {
		this.musicAleatoria = musicAleatoria;
	}

	public int getReinicioMusicas() {
		return reinicioMusicas;
	}

	public void setReinicioMusicas(int reinicioMusicas) {
		this.reinicioMusicas = reinicioMusicas;
	}

	public String getDireccionVideoPromocional() {
		return direccionVideoPromocional;
	}

	public void setDireccionVideoPromocional(String direccionVideoPromocional) {
		this.direccionVideoPromocional = direccionVideoPromocional;
	}

	public int getCantidadCreditos() {
		return cantidadCreditos;
	}

	public void setCantidadCreditos(int cantidadCreditos) {
		this.cantidadCreditos = cantidadCreditos;
	}

	public boolean isLibre() {
		return libre;
	}

	public void setLibre(boolean libre) {
		this.libre = libre;
	}

	public boolean isVideoPromocional() {
		return videoPromocional;
	}

	public void setVideoPromocional(boolean videoPromocional) {
		this.videoPromocional = videoPromocional;
	}

	public int getClickCreditos() {
		return clickCreditos;
	}

	public void setClickCreditos(int clickCreditos) {
		this.clickCreditos = clickCreditos;
	}

	public int getTeclaSubirLista() {
		return teclaSubirLista;
	}

	public void setTeclaSubirLista(int teclaSubirLista) {
		this.teclaSubirLista = teclaSubirLista;
	}

	public int getTeclaBajarLista() {
		return teclaBajarLista;
	}

	public void setTeclaBajarLista(int teclaBajarLista) {
		this.teclaBajarLista = teclaBajarLista;
	}

	public int getTeclaSubirGenero() {
		return teclaSubirGenero;
	}

	public void setTeclaSubirGenero(int teclaSubirGenero) {
		this.teclaSubirGenero = teclaSubirGenero;
	}

	public int getTeclaBajarGenero() {
		return teclaBajarGenero;
	}

	public void setTeclaBajarGenero(int teclaBajarGenero) {
		this.teclaBajarGenero = teclaBajarGenero;
	}

	public int getTeclaPantallaCompleta() {
		return teclaPantallaCompleta;
	}

	public void setTeclaPantallaCompleta(int teclaPantallaCompleta) {
		this.teclaPantallaCompleta = teclaPantallaCompleta;
	}

	public int getTeclaBorrar() {
		return teclaBorrar;
	}

	public void setTeclaBorrar(int teclaBorrar) {
		this.teclaBorrar = teclaBorrar;
	}

	public int getCantidadCreditosUsados() {
		return cantidadCreditosUsados;
	}

	public void setCantidadCreditosUsados(int cantidadCreditosUsados) {
		this.cantidadCreditosUsados = cantidadCreditosUsados;
	}

	public int getCantidadMonedasInsertadas() {
		return cantidadMonedasInsertadas;
	}

	public void setCantidadMonedasInsertadas(int cantidadMonedasInsertadas) {
		this.cantidadMonedasInsertadas = cantidadMonedasInsertadas;
	}

	public URL getDireccionFondo() {
		return direccionFondo;
	}

	public void setDireccionFondo(URL direccionFondos) {
		this.direccionFondo = direccionFondos;
	}

	public Color getColor1() {
		return color1;
	}

	public void setColor1(Color color1) {
		this.color1 = color1;
	}

	public Color getColor2() {
		return color2;
	}

	public void setColor2(Color color2) {
		this.color2 = color2;
	}

	public boolean isDefaultBackground() {
		return defaultBackground;
	}

	public void setDefaultBackground(boolean defaultBackground) {
		this.defaultBackground = defaultBackground;
	}

	public boolean isCancelMusic() {
		return cancelMusic;
	}

	public void setCancelMusic(boolean cancelMusic) {
		this.cancelMusic = cancelMusic;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
