/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.3 / 4 Mar 2021 / 14:44:35
 * 
 * Waistax Framework'den alındı.
 * ? / ? / ?
 */
package başaşağıderebeyi.iskelet.görsel;

import static org.lwjgl.opengl.GL20.*;

import başaşağıderebeyi.iskelet.görsel.kaynak.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

import java.nio.file.*;
import java.util.*;

/** Ekran kartına yüklenen köşe dizilerinin nasıl çizileceğini anlatan
 * yazılım. */
public class Gölgelendirici {
	public static final String KLASÖRÜ = "gölgelendiriciler";
	public static final String KÖŞE_GÖLGELENDİRİCİSİNİN_DOSYA_UZANTISI =
		".kgöl";
	public static final String BENEK_GÖLGELENDİRİCİSİNİN_DOSYA_UZANTISI =
		".bgöl";
	
	private final int yazılımı;
	private final Map<String, Integer> değerlerininKonumları;
	
	/** Gölgelendirici yükler ve yazılımı derler. */
	public Gölgelendirici(
		final Path köşesininKaynağı,
		final Path beneğininKaynağı) {
		yazılımı = Yükleyici.NESNESİ.yazılımYükle();
		
		gölgelendiricileriAyarla(
			Yükleyici.NESNESİ
				.gölgelendiriciYükle(köşesininKaynağı, GL_VERTEX_SHADER),
			Yükleyici.NESNESİ
				.gölgelendiriciYükle(beneğininKaynağı, GL_FRAGMENT_SHADER));
		
		değerlerininKonumları = new HashMap<>();
	}
	
	/** Yazılımı kullanmaya başlar. */
	public void bağla() {
		glUseProgram(yazılımı);
	}
	
	/** Yazılımı kullanmayı bırakır. */
	public void kopar() {
		glUseProgram(0);
	}
	
	/** Bir değerin konumunu bulur ve içerideki haritaya koyar. Bu yöntemin
	 * kullanılabilinmesi için önce yazılımın bağlanmalıdır. */
	public void değerinKonumunuBul(final String değerinAdı) {
		final int değerinKonumu = glGetUniformLocation(yazılımı, değerinAdı);
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
	
	/** Double değeri değiştirir. */
	public void değeriDeğiştir(
		final String değerinAdı,
		final double yeniDeğer) {
		glUniform1f(değerlerininKonumları.get(değerinAdı), (float)yeniDeğer);
	}
	
	/** Doğrusal Sayı Dizisi değeri değiştirir. */
	public Gölgelendirici değeriDeğiştir(
		final String değerinAdı,
		final DoğrusalSayıDizisi yeniDeğer) {
		int değerinKonumu = değerlerininKonumları.get(değerinAdı);
		switch (yeniDeğer.uzunluğunuEdin()) {
		case 2:
			glUniform2f(
				değerinKonumu,
				(float)yeniDeğer.sayısınıEdin(0),
				(float)yeniDeğer.sayısınıEdin(1));
			break;
		case 3:
			glUniform3f(
				değerinKonumu,
				(float)yeniDeğer.sayısınıEdin(0),
				(float)yeniDeğer.sayısınıEdin(1),
				(float)yeniDeğer.sayısınıEdin(2));
			break;
		case 4:
			glUniform4f(
				değerinKonumu,
				(float)yeniDeğer.sayısınıEdin(0),
				(float)yeniDeğer.sayısınıEdin(1),
				(float)yeniDeğer.sayısınıEdin(2),
				(float)yeniDeğer.sayısınıEdin(3));
			break;
		case 16:
			yeniDeğer
				.uygula(
					girdisi -> Yükleyici.NESNESİ.dizeyTamponu
						.put((float)girdisi));
			glUniformMatrix4fv(
				değerlerininKonumları.get(değerinAdı),
				false,
				Yükleyici.NESNESİ.dizeyTamponu.flip());
			break;
		}
		return this;
	}
	
	/** İki boyutlu bir yöney değeri değiştirir. */
	public Gölgelendirici değeriDeğiştir(String değerinAdı, Yöney2 yeniDeğer) {
		glUniform2f(
			değerlerininKonumları.get(değerinAdı),
			(float)yeniDeğer.birinciBileşeniniEdin(),
			(float)yeniDeğer.ikinciBileşeniniEdin());
		return this;
	}
	
	/** Üç boyutlu bir yöney değeri değiştirir. */
	public Gölgelendirici değeriDeğiştir(String değerinAdı, Yöney3 yeniDeğer) {
		glUniform3f(
			değerlerininKonumları.get(değerinAdı),
			(float)yeniDeğer.birinciBileşeniniEdin(),
			(float)yeniDeğer.ikinciBileşeniniEdin(),
			(float)yeniDeğer.üçüncüBileşeniniEdin());
		return this;
	}
	
	/** Dört boyutlu bir yöney değeri değiştirir. */
	public Gölgelendirici değeriDeğiştir(String değerinAdı, Yöney4 yeniDeğer) {
		glUniform4f(
			değerlerininKonumları.get(değerinAdı),
			(float)yeniDeğer.birinciBileşeniniEdin(),
			(float)yeniDeğer.ikinciBileşeniniEdin(),
			(float)yeniDeğer.üçüncüBileşeniniEdin(),
			(float)yeniDeğer.dördüncüBileşeniniEdin());
		return this;
	}
	
	private void gölgelendiricileriAyarla(
		final int köşeGölgelendiricisi,
		final int benekGölgelendiricisi) {
		glAttachShader(yazılımı, köşeGölgelendiricisi);
		glAttachShader(yazılımı, benekGölgelendiricisi);
		
		yazılımıDerle();
		
		glDetachShader(yazılımı, köşeGölgelendiricisi);
		glDetachShader(yazılımı, benekGölgelendiricisi);
		glDeleteShader(köşeGölgelendiricisi);
		glDeleteShader(benekGölgelendiricisi);
	}
	
	private void yazılımıDerle() {
		glLinkProgram(yazılımı);
		if (glGetProgrami(yazılımı, GL_LINK_STATUS) == 0)
			throw new RuntimeException(
				"Gölgelendirici bağdaştırılamadı! Hata: " +
					glGetProgramInfoLog(yazılımı, 1024));
		
		glValidateProgram(yazılımı);
		if (glGetProgrami(yazılımı, GL_VALIDATE_STATUS) == 0)
			throw new RuntimeException(
				"Gölgelendirici doğrulanamadı! Hata: " +
					glGetProgramInfoLog(yazılımı, 1024));
	}
}
