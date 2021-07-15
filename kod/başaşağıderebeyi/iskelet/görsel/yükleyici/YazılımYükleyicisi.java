/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.12.2 / 20 Mar 2021 / 22:25:04
 */
package başaşağıderebeyi.iskelet.görsel.yükleyici;

import static org.lwjgl.opengl.GL20.*;

import java.io.*;
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
	
	int gölgelendiriciYükle(final Path dosyaYolu, final int türü) {
		final int gölgelendirici = glCreateShader(türü);
		
		glShaderSource(gölgelendirici, yazıYükle(dosyaYolu));
		glCompileShader(gölgelendirici);
		
		if (glGetShaderi(gölgelendirici, GL_COMPILE_STATUS) == 0)
			throw new RuntimeException(
				"Gölgelendirici derlenemedi! Hata: " +
					glGetShaderInfoLog(gölgelendirici, 1024));
		
		return gölgelendirici;
	}
	
	String yazıYükle(final Path dosyaYolu) {
		try {
			return Files.readString(dosyaYolu);
		} catch (final IOException hata) {
			throw new RuntimeException(
				"Yazı " + dosyaYolu + " yüklenemedi!",
				hata);
		}
	}
	
	List<String> satırlarYükle(final Path dosyaYolu) {
		try {
			return Files.readAllLines(dosyaYolu);
		} catch (final IOException hata) {
			throw new RuntimeException(
				"Yazı " + dosyaYolu + " yüklenemedi!",
				hata);
		}
	}
}
