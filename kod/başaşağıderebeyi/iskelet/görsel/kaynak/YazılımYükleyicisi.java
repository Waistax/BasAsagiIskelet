/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.12.2 / 20 Mar 2021 / 22:25:04
 */
package başaşağıderebeyi.iskelet.görsel.kaynak;

import static org.lwjgl.opengl.GL20.*;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

import org.lwjgl.opengl.*;

class YazılımYükleyicisi {
	private final List<Integer> yazılımları;
	
	YazılımYükleyicisi() {
		yazılımları = new ArrayList<>();
	}
	
	void yokEt() {
		glUseProgram(0);
		yazılımları.forEach(GL20::glDeleteProgram);
		yazılımları.clear();
	}
	
	int yükle() {
		final int yazılım = glCreateProgram();
		yazılımları.add(yazılım);
		return yazılım;
	}
	
	int gölgelendiriciYükle(final URI kaynağı, final int türü) {
		final int gölgelendirici = glCreateShader(türü);
		
		glShaderSource(gölgelendirici, yazıyıYükle(kaynağı));
		glCompileShader(gölgelendirici);
		
		if (glGetShaderi(gölgelendirici, GL_COMPILE_STATUS) == 0)
			throw new RuntimeException(
				"Gölgelendirici derlenemedi! Hata: " +
					glGetShaderInfoLog(gölgelendirici, 1024));
		
		return gölgelendirici;
	}
	
	String yazıyıYükle(final URI kaynağı) {
		try {
			return Files.readString(Path.of(kaynağı));
		} catch (final IOException hata) {
			throw new RuntimeException(
				"Yazı kaynağı " + kaynağı + " yüklenemedi!",
				hata);
		}
	}
	
	List<String> satırlarınıYükle(final URI kaynağı) {
		try {
			return Files.readAllLines(Path.of(kaynağı));
		} catch (final IOException hata) {
			throw new RuntimeException(
				"Yazı kaynağı " + kaynağı + " yüklenemedi!",
				hata);
		}
	}
}
