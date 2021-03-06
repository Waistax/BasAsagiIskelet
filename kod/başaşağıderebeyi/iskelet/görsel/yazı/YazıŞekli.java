/**
 * başaşağıderebeyi.iskelet.görsel.yazı.YazıŞekli.java
 * 0.6 / 5 Mar 2021 / 20:06:21
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import static org.lwjgl.opengl.GL11.*;

import başaşağıderebeyi.iskelet.görsel.*;

import java.util.*;

/** Bir yazının nasıl görselleştirileceğini belirleyen nesne. */
public class YazıŞekli {
	/** Yazı şeklindeki en büyük yükseklik. Çizilecek yüksekliğin buna oranı
	 * bütün yazının boyutudur. */
	public final float enBüyükYükseklik;
	
	final Map<Character, SesŞekli> seslerininŞekilleri;
	private final int dokusu;
	
	/** Verilen addaki yazı şeklini tanımlar. */
	public YazıŞekli(final Yükleyici yükleyici, final String adı) {
		seslerininŞekilleri = new HashMap<>();
		dokusu = yükleyici.dokuYükle(adı + "YazıŞekli");
		
		enBüyükYükseklik =
			new ŞekilOkuyucusu(this, yükleyici.yazıŞekliBilgisiniYükle(adı))
				.enBüyükYüksekliğiniEdin();
	}
	
	/** Verilen sesin şeklini döndürür. */
	public SesŞekli sesininŞekliniEdin(final char sesi) {
		return seslerininŞekilleri.get(sesi);
	}
	
	/** Dokusunu bağlar. */
	public void bağla() {
		glBindTexture(GL_TEXTURE_2D, dokusu);
	}
	
	/** Dokusunu koparır. */
	public void kopar() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
