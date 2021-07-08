/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 5 Mar 2021 / 20:06:21
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import static org.lwjgl.opengl.GL11.*;

import başaşağıderebeyi.iskelet.görsel.kaynak.*;

import java.net.*;
import java.util.*;

/** Bir yazının nasıl görselleştirileceğini belirleyen nesne. */
public class YazıŞekli {
	/** Satır çizgileri arasında bırakılacak boşluğun en büyük yüksekliğe
	 * oranı. */
	public static final double ÇİZGİLER_ARASI_BOŞLUĞUN_ORANI = 1.1;
	/** Herhangi bitişik iki sesin arasında bırakılacak boşluğun bu iki sesin
	 * toplam genişliğine oranı. */
	public static final double SESLER_ARASI_BOŞLUĞUN_ORANI = 0.0;
	
	/** Yazı şeklindeki en büyük yükseklik. Çizilecek yüksekliğin buna oranı
	 * bütün yazının boyutudur. */
	public final double enBüyükYüksekliği;
	
	final Map<Character, SesŞekli> seslerininŞekilleri;
	private final int dokusu;
	
	/** Verilen doku ve bilgiden tanımlar. */
	public YazıŞekli(final URI dokusununKaynağı, final URI bilgisininKaynağı) {
		seslerininŞekilleri = new HashMap<>();
		dokusu = Yükleyici.NESNESİ.dokuYükle(dokusununKaynağı);
		
		enBüyükYüksekliği = new ŞekilOkuyucusu(
			this,
			Yükleyici.NESNESİ.satırlarınıYükle(bilgisininKaynağı))
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
