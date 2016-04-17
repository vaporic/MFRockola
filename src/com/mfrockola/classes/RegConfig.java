package com.mfrockola.classes;

import java.awt.Color;
import java.io.Serializable;

@SuppressWarnings("serial")
public class RegConfig implements Serializable
{
	private String direccionMusicas;
	private String direccionVideos;
	private String direccionImagenes;
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
	private int teclaCambiarLista;
	private int cantidadCreditosUsados;
	private int cantidadMonedasInsertadas;
	private String direccionFondo;
	private boolean mostrarPublicidad;
	private Color color1;
	private Color color2;
	
	public RegConfig(String direccionMusicas, String direccionVideos, String direccionImagenes, String direccionVlc,
			String direccionVideoPromocional, int musicAleatoria,int reinicioMusicas,int cantidadCreditos, boolean libre,
			boolean videoPromocional, int clickCreditos, boolean selectVideoProm, int teclaSubirLista, int teclaBajarLista,
			int teclaSubirGenero, int teclaBajarGenero, int teclaPantallaCompleta, int teclaBorrar, int teclaCambiarLista,
			int cantidadCreditosUsados, int CantidadMonedasInsertadas, String direccionFondo, boolean mostrarPublicidad,
			Color color1, Color color2)
	{
		this.setDireccionMusicas(direccionMusicas);
		this.setDireccionVideos(direccionVideos);
		this.setDireccionImagenes(direccionImagenes);
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
		this.setTeclaCambiarLista(teclaCambiarLista);
		this.setCantidadCreditosUsados(cantidadCreditosUsados);
		this.setCantidadMonedasInsertadas(CantidadMonedasInsertadas);
		this.setDireccionFondo(direccionFondo);
		this.setMostrarPublicidad(mostrarPublicidad);
		this.setColor1(color1);
		this.setColor2(color2);
	}
	
	public String getDireccionMusicas() 
	{
		return direccionMusicas;
	}
	public void setDireccionMusicas(String direccionMusicas) 
	{
		this.direccionMusicas = direccionMusicas;
	}
	public String getDireccionVideos() 
	{
		return direccionVideos;
	}
	public void setDireccionVideos(String direccionVideos) 
	{
		this.direccionVideos = direccionVideos;
	}

	public String getDireccionImagenes() {
		return direccionImagenes;
	}

	public void setDireccionImagenes(String direccionImagenes) {
		this.direccionImagenes = direccionImagenes;
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

	public int getTeclaCambiarLista() {
		return teclaCambiarLista;
	}

	public void setTeclaCambiarLista(int teclaCambiarLista) {
		this.teclaCambiarLista = teclaCambiarLista;
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

	public String getDireccionFondo() {
		return direccionFondo;
	}

	public void setDireccionFondo(String direccionFondos) {
		this.direccionFondo = direccionFondos;
	}

	public boolean isMostrarPublicidad() {
		return mostrarPublicidad;
	}

	public void setMostrarPublicidad(boolean mostrarPublicidad) {
		this.mostrarPublicidad = mostrarPublicidad;
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
}
