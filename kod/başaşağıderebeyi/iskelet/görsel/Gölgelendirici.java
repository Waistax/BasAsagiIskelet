/**
 * başaşağıderebeyi.iskelet.görsel.Gölgelendirici.java
 * 0.3 / 4 Mar 2021 / 14:44:35
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 
 * Waistax Framework'den alındı.
 * ? / ? / ?
 */
package başaşağıderebeyi.iskelet.görsel;

import static org.lwjgl.opengl.GL20.*;

import başaşağıderebeyi.kütüphane.matematik.*;

import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.util.*;

import org.lwjgl.*;

/** Ekran kartına yüklenen köşe dizilerinin nasıl çizileceğini anlatan
 * yazılım. */
public class Gölgelendirici {
	private static final FloatBuffer DİZEY_TAMPONU =
		BufferUtils.createFloatBuffer(16);
	
	private final int işaretçisi;
	private final Map<String, Integer> değerlerininKonumları;
	
	/** Gölgelendiriciler klasöründeki verilen addaki gölgelendirici çiftini
	 * yükler. */
	public Gölgelendirici(final Yükleyici yükleyici, final String adı) {
		this(
			yükleyici,
			"gölgelendiriciler/" + adı + ".kgöl",
			"gölgelendiriciler/" + adı + ".bgöl");
	}
	
	/** Gölgelendirici yükler ve yazılımı derler. */
	public Gölgelendirici(
		final Yükleyici yükleyici,
		final String köşeGölgelendiricisininDosyaYolu,
		final String benekGölgelendiricisininDosyaYolu) {
		işaretçisi = yükleyici.yazılımYükle();
		
		try {
			gölgelendiricileriAyarla(
				yükleyici
					.gölgelendiriciYükle(
						Files
							.readString(
								Paths.get(köşeGölgelendiricisininDosyaYolu)),
						GL_VERTEX_SHADER),
				yükleyici
					.gölgelendiriciYükle(
						Files
							.readString(
								Paths.get(benekGölgelendiricisininDosyaYolu)),
						GL_FRAGMENT_SHADER));
		} catch (final IOException hata) {
			throw new RuntimeException(
				"Gölgelendirici dosyaları okunamadı!",
				hata);
		}
		
		değerlerininKonumları = new HashMap<>();
	}
	
	/** Yazılımı kullanmaya başlar. */
	public void bağla() {
		glUseProgram(işaretçisi);
	}
	
	/** Yazılımı kullanmayı bırakır. */
	public void kopar() {
		glUseProgram(0);
	}
	
	/** Bir değerin konumunu bulur ve içerideki haritaya koyar. Bu yöntemin
	 * kullanılabilinmesi için önce yazılımın bağlanmalıdır. */
	public void değerinKonumunuBul(final String değerinAdı) {
		final int değerinKonumu = glGetUniformLocation(işaretçisi, değerinAdı);
		if (değerinKonumu == 0xFFFFFFFF)
			throw new RuntimeException("Değer bulunamadı: " + değerinAdı);
		değerlerininKonumları.put(değerinAdı, değerinKonumu);
	}
	
	/** Int değeri değiştirir. */
	public void değeriDeğiştir(final String değerinAdı, final int yeniDeğer) {
		glUniform1i(değerlerininKonumları.get(değerinAdı), yeniDeğer);
	}
	
	/** Float değeri değiştirir. */
	public void değeriDeğiştir(final String değerinAdı, final float yeniDeğer) {
		glUniform1f(değerlerininKonumları.get(değerinAdı), yeniDeğer);
	}
	
	/** Vec2 değeri değiştirir. */
	public void değeriDeğiştir(
		final String değerinAdı,
		final Yöney2 yeniDeğer) {
		glUniform2f(
			değerlerininKonumları.get(değerinAdı),
			yeniDeğer.birinciBileşeni,
			yeniDeğer.ikinciBileşeni);
	}
	
	/** Vec3 değeri değiştirir. */
	public void değeriDeğiştir(
		final String değerinAdı,
		final Yöney3 yeniDeğer) {
		glUniform3f(
			değerlerininKonumları.get(değerinAdı),
			yeniDeğer.birinciBileşeni,
			yeniDeğer.ikinciBileşeni,
			yeniDeğer.üçüncüBileşeni);
	}
	
	/** Vec4 değeri değiştirir. */
	public void değeriDeğiştir(
		final String değerinAdı,
		final Yöney4 yeniDeğer) {
		glUniform4f(
			değerlerininKonumları.get(değerinAdı),
			yeniDeğer.birinciBileşeni,
			yeniDeğer.ikinciBileşeni,
			yeniDeğer.üçüncüBileşeni,
			yeniDeğer.dördüncüBileşeni);
	}
	
	/** Mat4 değeri değiştirir. */
	public void değeriDeğiştir(
		final String değerinAdı,
		final Dizey4 yeniDeğer) {
		glUniformMatrix4fv(
			değerlerininKonumları.get(değerinAdı),
			false,
			DİZEY_TAMPONU.put(yeniDeğer.girdileri).flip());
	}
	
	private void gölgelendiricileriAyarla(
		final int köşeGölgelendiricisininİşaretçisi,
		final int benekGölgelendiricisininİşaretçisi) {
		glAttachShader(işaretçisi, köşeGölgelendiricisininİşaretçisi);
		glAttachShader(işaretçisi, benekGölgelendiricisininİşaretçisi);
		
		yazılımıDerle();
		
		glDetachShader(işaretçisi, köşeGölgelendiricisininİşaretçisi);
		glDetachShader(işaretçisi, benekGölgelendiricisininİşaretçisi);
		glDeleteShader(köşeGölgelendiricisininİşaretçisi);
		glDeleteShader(benekGölgelendiricisininİşaretçisi);
	}
	
	private void yazılımıDerle() {
		glLinkProgram(işaretçisi);
		if (glGetProgrami(işaretçisi, GL_LINK_STATUS) == 0)
			throw new RuntimeException(
				"Gölgelendirici bağdaştırılamadı! Hata: " +
					glGetProgramInfoLog(işaretçisi, 1024));
		
		glValidateProgram(işaretçisi);
		if (glGetProgrami(işaretçisi, GL_VALIDATE_STATUS) == 0)
			throw new RuntimeException(
				"Gölgelendirici doğrulanamadı! Hata: " +
					glGetProgramInfoLog(işaretçisi, 1024));
	}
}
