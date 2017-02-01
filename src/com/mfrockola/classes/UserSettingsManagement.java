package com.mfrockola.classes;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

class UserSettingsManagement
{

	// We create an object input stream to read the file on the hard disk
	private ObjectInputStream mInputStream;

	// We open the user configuration file to extract the data and verify if it is available
	void openUserSettings() {
		try {
			mInputStream = new ObjectInputStream(new FileInputStream("config.mfr"));
		} catch (IOException ioException) {
			System.err.println("Error al abrir el archivo");
		}
	}

	// We convert the file into a UserSettings object readable by MFRockola
	UserSettings readUserSettings() {
		UserSettings data = null;
		
		try {
			data = (UserSettings) mInputStream.readObject();
		} catch (EOFException endOfFileException) {
			return null;
		} catch (ClassNotFoundException | IOException classNotFoundExcepcion) {
			System.err.println("Error al leer el archivo.");
		}
		return data;
	}

	// We close the configuration file in case we are not going to use it at some point
	public void closeUserSettings() {
		try {
			if(mInputStream != null) {
				mInputStream.close();
				System.exit(0);
			}
		} catch (IOException ioExcepcion) {
			System.err.println("Error al cerrar el archivo");
			System.exit(1);
		}
	}
}
