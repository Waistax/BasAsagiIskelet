/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 5 Mar 2021 / 20:06:21
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import static başaşağıderebeyi.iskelet.görsel.yükleyici.Yükleyici.*;

import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

import java.nio.file.*;
import java.util.*;

/** Bir yazının nasıl görselleştirileceğini belirleyen nesne. */
public class YazıŞekli {
	/** Karakterlerinin boyutu. */
	public final Yöney2 boyutu;
	/** Bütün karakterleri içeren doku. */
	public final int dokusu;
	
	final Map<Character, SesŞekli> seslerininŞekilleri;
	
	/** Verilen kaynaktaki dokuyu en iyi küçültme ve büyütme yöntemiyle
	 * yükleyerek verilen bilgiden tanımlar. */
	public YazıŞekli(
		final Path dokusununKaynağı,
		final Path bilgisininKaynağı) {
		this(YÜKLEYİCİ.dokuYükle(dokusununKaynağı), bilgisininKaynağı);
	}
	
	/** Verilen kaynaktaki dokuyu verilen küçültme ve büyütme yöntemiyle
	 * yükleyerek verilen bilgiden tanımlar. */
	public YazıŞekli(
		final Path dokusununKaynağı,
		final int küçültmeYöntemi,
		final int büyütmeYöntemi,
		final Path bilgisininKaynağı) {
		this(
			YÜKLEYİCİ
				.dokuYükle(dokusununKaynağı, küçültmeYöntemi, büyütmeYöntemi),
			bilgisininKaynağı);
	}
	
	/** Verilen doku ve bilgiden tanımlar. */
	public YazıŞekli(final int dokusu, final Path bilgisininKaynağı) {
		seslerininŞekilleri = new HashMap<>();
		this.dokusu = dokusu;
		final ŞekilOkuyucusu şekilOkuyucusu = new ŞekilOkuyucusu(
			this,
			YÜKLEYİCİ.satırlarYükle(bilgisininKaynağı));
		boyutu =
			new Yöney2(şekilOkuyucusu.genişliği, şekilOkuyucusu.yüksekliği);
	}
	
	/** Verilen sesin şeklini döndürür. */
	public SesŞekli sesininŞekliniEdin(final char sesi) {
		return seslerininŞekilleri.get(sesi);
	}
	
	/** Verilen dizenin toplam uzunluğunu bulur. */
	public double uzunluğunuBul(final String dize) {
		return uzunluğunuBul(dize.length());
	}
	
	/** Verilen sayıda sesin toplam uzunluğunu bulur. */
	public double uzunluğunuBul(final double sesSayısı) {
		return sesSayısı * boyutu.birinciBileşeniniEdin();
	}
	
	/** Verilen satırların toplam yüksekliğini döndürür. */
	public double yüksekliğiBul(final String... satırlar) {
		return yüksekliğiBul(satırlar.length);
	}
	
	/** Verilen sayıda satırın toplam yüksekliğini döndürür. */
	public double yüksekliğiBul(final double satırSayısı) {
		return satırSayısı * boyutu.ikinciBileşeniniEdin();
	}
}
