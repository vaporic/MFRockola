package com.mfrockola.classes;

import java.awt.Color;
import java.io.Serializable;
import java.net.URL;

@SuppressWarnings("serial")
public class UserSettings implements Serializable {

	// This string saves the path of the videos folder
	private String pathSongs;

	// In case you try to play an mp3, will play a video that is in this
	private String pathVideosMp3;

	private String pathVlc;
	private String direccionVideoPromocional;
	private int musicAleatoria;
	private int reinicioMusicas;
	private int cantidadCreditos;
	private boolean libre;
	private boolean lockScreen;
	private boolean videoPromocional;
	private boolean defaultPromotionalVideo;
	private int clickCreditos;
	private int teclaSubirLista;
	private int teclaBajarLista;
	private int teclaSubirGenero;
	private int teclaBajarGenero;
	private int teclaPantallaCompleta;
	private int teclaBorrar;
	private int teclaSaltarCancion;
	private int teclaAgregarCredito;
	private int teclaBorrarCredito;
	private boolean cancelMusic;
	private String password;
	private int cantidadCreditosUsados;
	private int cantidadMonedasInsertadas;

	// Tab apariencia

	private boolean defaultBackground;
	private URL direccionFondo;
	private Color color1;
	private Color color2;
	private String fontCeldasName;
	private int fontCeldasSize;
	private int fontSelectorSize;
	private Color fontCeldasColor;
	private int fontCeldasNegrita;

	// Tab promociones

	private boolean agregarAdicional;
	private int numeroDeCreditosAdicionales;
	private int cadaCantidadDeCreditos;
	private boolean creditosContinuos;
	private boolean entregarPremio;
	private int cantidadDePremios;
	private int cantidadDeCreditosPorPremio;
	private String tipoDePremio;

	private int creditosGuardados;

	public UserSettings(String pathSongs, String pathVideosMp3, String pathVlc,
						String direccionVideoPromocional, int musicAleatoria,int reinicioMusicas,int cantidadCreditos, boolean libre,
						boolean lockScreen,	boolean videoPromocional, boolean defaultPromotionalVideo, int clickCreditos,
						int teclaSubirLista, int teclaBajarLista, int teclaSubirGenero, int teclaBajarGenero,
						int teclaPantallaCompleta, int teclaBorrar,	int teclaSaltarCancion, int teclaAgregarCredito,
						int teclaBorrarCredito,	boolean cancelMusic,String password, int cantidadCreditosUsados,
						int CantidadMonedasInsertadas, boolean defaultBackground, URL direccionFondo, Color color1,
						Color color2, String fontCeldasName, int fontCeldasSize, int fontSelectorSize,
						Color fontCeldasColor, int fontCeldasNegrita, boolean agregarAdicional, int numeroDeCreditosAdicionales,
						int cadaCantidadDeCreditos, boolean creditosContinuos, boolean entregarPremio, int cantidadDePremios,
						int cantidadDeCreditosPorPremio, String tipoDePremio, int creditosGuardados)
	{
		setPathSongs(pathSongs);
		setPathVideosMp3(pathVideosMp3);
		setPathVlc(pathVlc);
		setMusicAleatoria(musicAleatoria);
		setReinicioMusicas(reinicioMusicas);
		setDireccionVideoPromocional(direccionVideoPromocional);
		setCantidadCreditos(cantidadCreditos);
		setLibre(libre);
		setLockScreen(lockScreen);
		setVideoPromocional(videoPromocional);
		setDefaultPromotionalVideo(defaultPromotionalVideo);
		setClickCreditos(clickCreditos);
		setTeclaSubirLista(teclaSubirLista);
		setTeclaBajarLista(teclaBajarLista);
		setTeclaSubirGenero(teclaSubirGenero);
		setTeclaBajarGenero(teclaBajarGenero);
		setTeclaPantallaCompleta(teclaPantallaCompleta);
		setTeclaBorrar(teclaBorrar);
		setTeclaSaltarCancion(teclaSaltarCancion);
		setTeclaAgregarCredito(teclaAgregarCredito);
		setTeclaBorrarCredito(teclaBorrarCredito);
		setCancelMusic(cancelMusic);
		setPassword(password);
		setCantidadCreditosUsados(cantidadCreditosUsados);
		setCantidadMonedasInsertadas(CantidadMonedasInsertadas);
		setDefaultBackground(defaultBackground);
		setDireccionFondo(direccionFondo);
		setColor1(color1);
		setColor2(color2);
		setFontCeldasName(fontCeldasName);
		setFontCeldasSize(fontCeldasSize);
		setFontSelectorSize(fontSelectorSize);
		setFontCeldasColor(fontCeldasColor);
		setFontCeldasNegrita(fontCeldasNegrita);
		setAgregarAdicional(agregarAdicional);
		setNumeroDeCreditosAdicionales(numeroDeCreditosAdicionales);
		setCadaCantidadDeCreditos(cadaCantidadDeCreditos);
		setCreditosContinuos(creditosContinuos);
		setEntregarPremio(entregarPremio);
		setCantidadDePremios(cantidadDePremios);
		setCantidadDeCreditosPorPremio(cantidadDeCreditosPorPremio);
		setTipoDePremio(tipoDePremio);
		setCreditosGuardados(creditosGuardados);
	}

	public String getPathSongs()
	{
		return pathSongs;
	}

	public void setPathSongs(String pathSongs)
	{
		this.pathSongs = pathSongs;
	}

	public String getPathVlc() {
		return pathVlc;
	}

	public void setPathVlc(String pathVlc) {
		this.pathVlc = pathVlc;
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

	public boolean isAgregarAdicional() {
		return agregarAdicional;
	}

	public void setAgregarAdicional(boolean agregarAdicional) {
		this.agregarAdicional = agregarAdicional;
	}

	public boolean isEntregarPremio() {
		return entregarPremio;
	}

	public void setEntregarPremio(boolean entregarPremio) {
		this.entregarPremio = entregarPremio;
	}

	public int getNumeroDeCreditosAdicionales() {
		return numeroDeCreditosAdicionales;
	}

	public void setNumeroDeCreditosAdicionales(int numeroDeCreditosAdicionales) {
		this.numeroDeCreditosAdicionales = numeroDeCreditosAdicionales;
	}

	public int getCadaCantidadDeCreditos() {
		return cadaCantidadDeCreditos;
	}

	public void setCadaCantidadDeCreditos(int cadaCantidadDeCreditos) {
		this.cadaCantidadDeCreditos = cadaCantidadDeCreditos;
	}

	public boolean isCreditosContinuos() {
		return creditosContinuos;
	}

	public void setCreditosContinuos(boolean creditosContinuos) {
		this.creditosContinuos = creditosContinuos;
	}

	public int getCantidadDePremios() {
		return cantidadDePremios;
	}

	public void setCantidadDePremios(int cantidadDePremios) {
		this.cantidadDePremios = cantidadDePremios;
	}

	public int getCantidadDeCreditosPorPremio() {
		return cantidadDeCreditosPorPremio;
	}

	public void setCantidadDeCreditosPorPremio(int cantidadDeCreditosPorPremio) {
		this.cantidadDeCreditosPorPremio = cantidadDeCreditosPorPremio;
	}

	public String getTipoDePremio() {
		return tipoDePremio;
	}

	public void setTipoDePremio(String tipoDePremio) {
		this.tipoDePremio = tipoDePremio;
	}

	public String getFontCeldasName() {
		return fontCeldasName;
	}

	public void setFontCeldasName(String fontCeldasName) {
		this.fontCeldasName = fontCeldasName;
	}

	public int getFontCeldasSize() {
		return fontCeldasSize;
	}

	public void setFontCeldasSize(int fontCeldasSize) {
		this.fontCeldasSize = fontCeldasSize;
	}

	public Color getFontCeldasColor() {
		return fontCeldasColor;
	}

	public void setFontCeldasColor(Color fontCeldasColor) {
		this.fontCeldasColor = fontCeldasColor;
	}

	public int getFontCeldasNegrita() {
		return fontCeldasNegrita;
	}

	public void setFontCeldasNegrita(int fontCeldasNegrita) {
		this.fontCeldasNegrita = fontCeldasNegrita;
	}

	public int getTeclaSaltarCancion() {
		return teclaSaltarCancion;
	}

	public void setTeclaSaltarCancion(int teclaSaltarCancion) {
		this.teclaSaltarCancion = teclaSaltarCancion;
	}

	public int getTeclaAgregarCredito() {
		return teclaAgregarCredito;
	}

	public void setTeclaAgregarCredito(int teclaAgregarCredito) {
		this.teclaAgregarCredito = teclaAgregarCredito;
	}

	public int getTeclaBorrarCredito() {
		return teclaBorrarCredito;
	}

	public void setTeclaBorrarCredito(int teclaBorrarCredito) {
		this.teclaBorrarCredito = teclaBorrarCredito;
	}

	public String getPathVideosMp3() {
		return pathVideosMp3;
	}

	public void setPathVideosMp3(String pathVideosMp3) {
		this.pathVideosMp3 = pathVideosMp3;
	}

	public int getCreditosGuardados() {
		return creditosGuardados;
	}

	public void setCreditosGuardados(int creditosGuardados) {
		this.creditosGuardados = creditosGuardados;
	}

	public int getFontSelectorSize() {
		return fontSelectorSize;
	}

	public void setFontSelectorSize(int fontSelectorSize) {
		this.fontSelectorSize = fontSelectorSize;
	}

	public boolean isDefaultPromotionalVideo() {
		return defaultPromotionalVideo;
	}

	public void setDefaultPromotionalVideo(boolean defaultPromotionalVideo) {
		this.defaultPromotionalVideo = defaultPromotionalVideo;
	}

	public boolean isLockScreen() {
		return lockScreen;
	}

	public void setLockScreen(boolean lockScreen) {
		this.lockScreen = lockScreen;
	}
}
