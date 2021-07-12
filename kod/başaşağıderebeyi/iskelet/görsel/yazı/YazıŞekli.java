/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 5 Mar 2021 / 20:06:21
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import başaşağıderebeyi.iskelet.görsel.kaynak.*;

import java.net.*;
import java.util.*;

/** Bir yazının nasıl görselleştirileceğini belirleyen nesne. */
public class YazıŞekli {
	/** Satır çizgileri arasında bırakılacak boşluğun satır yüksekliğine
	 * oranı. */
	public static final double ÇİZGİLER_ARASI_BOŞLUĞUN_ORANI = 1.1;
	/** Herhangi bitişik iki sesin arasında bırakılacak boşluğun bu iki sesin
	 * toplam genişliğine oranı. */
	public static final double SESLER_ARASI_BOŞLUĞUN_ORANI = 0.5;
	
	/** Yazı şeklindeki bütün karakterlerden çizginin en çok üstüne çıkan ile
	 * çizginin en çok altına inenin dikey sınırları arasında kalan uzaklık.
	 * Aşağıdaki çizgi üstü ve altı yüksekliklerin toplamı. Bu yazı şekliyle
	 * yazılabilecek en büyük satırın yüksekliği, boşluk olmadan. Bu yükseklik
	 * yazıların boyutunu ölçeklemede kullanılır. */
	public final double yüksekliği;
	/** Yazı şeklindeki bütün karakterlerden çizginin en çok üstüne çıkanın
	 * çizginin üstünde kalan yüksekliğidir. */
	public final double çizgiÜstüYüksekliği;
	/** Yazı şeklindeki bütün karakterlerden çizginin en çok altına inenin
	 * çizginin altında kalan yüksekliğidir. */
	public final double çizgiAltıYüksekliği;
	/** Bütün karakterleri içeren doku. */
	public final int dokusu;
	
	final Map<Character, SesŞekli> seslerininŞekilleri;
	
	/** Verilen kaynaktaki dokuyu en iyi küçültme ve büyütme yöntemiyle
	 * yükleyerek verilen bilgiden tanımlar. */
	public YazıŞekli(final URI dokusununKaynağı, final URI bilgisininKaynağı) {
		this(Yükleyici.NESNESİ.dokuYükle(dokusununKaynağı), bilgisininKaynağı);
	}
	
	/** Verilen kaynaktaki dokuyu verilen küçültme ve büyütme yöntemiyle
	 * yükleyerek verilen bilgiden tanımlar. */
	public YazıŞekli(
		final URI dokusununKaynağı,
		final int küçültmeYöntemi,
		final int büyütmeYöntemi,
		final URI bilgisininKaynağı) {
		this(
			Yükleyici.NESNESİ
				.dokuYükle(dokusununKaynağı, küçültmeYöntemi, büyütmeYöntemi),
			bilgisininKaynağı);
	}
	
	private YazıŞekli(final int dokusu, final URI bilgisininKaynağı) {
		seslerininŞekilleri = new HashMap<>();
		this.dokusu = dokusu;
		ŞekilOkuyucusu şekilOkuyucusu = new ŞekilOkuyucusu(
			this,
			Yükleyici.NESNESİ.satırlarınıYükle(bilgisininKaynağı));
		yüksekliği = şekilOkuyucusu.yüksekliği;
		çizgiÜstüYüksekliği = şekilOkuyucusu.çizgiÜstüYüksekliği;
		çizgiAltıYüksekliği = şekilOkuyucusu.çizgiAltıYüksekliği;
	}
	
	/** Verilen sesin şeklini döndürür. */
	public SesŞekli sesininŞekliniEdin(final char sesi) {
		return seslerininŞekilleri.get(sesi);
	}
	
	/** Verilen dizenin toplam uzunluğunu bulur. */
	public double uzunluğunuBul(String dize) {
		double sonuç = 0.0;
		
		SesŞekli öncekiSesŞekli = null;
		for (int i = 0; i < dize.length(); i++) {
			final SesŞekli sesŞekli = sesininŞekliniEdin(dize.charAt(i));
			if (sesŞekli == null)
				continue;
			if (öncekiSesŞekli != null)
				sonuç += atlanacakUzunluğuBul(öncekiSesŞekli, sesŞekli);
			öncekiSesŞekli = sesŞekli;
		}
		
		if (öncekiSesŞekli != null)
			sonuç += öncekiSesŞekli.genişliği;
		
		return sonuç;
	}
	
	/** Verilen iki sesin ardaşık olarak yazılmasında önceki sesin yataydaki
	 * konumundan sonrakine ne kadar mesafe atlanması gerektiğini döndürür. */
	public double atlanacakUzunluğuBul(final char öncekiSes, final char ses) {
		return atlanacakUzunluğuBul(
			sesininŞekliniEdin(ses),
			sesininŞekliniEdin(öncekiSes));
	}
	
	/** Verilen iki sesin ardaşık olarak yazılmasında önceki sesin yataydaki
	 * konumundan sonrakine ne kadar mesafe atlanması gerektiğini döndürür. */
	public double atlanacakUzunluğuBul(
		final SesŞekli öncekiSesŞekli,
		final SesŞekli sesŞekli) {
		return öncekiSesŞekli.genişliği +
			(sesŞekli.genişliği + öncekiSesŞekli.genişliği) *
				SESLER_ARASI_BOŞLUĞUN_ORANI;
	}
	
	/** Verilen satırların toplam yüksekliğini döndürür. */
	public double yüksekliğiBul(String... satırlar) {
		return yüksekliğiBul(satırlar.length);
	}
	
	/** Verilen sayıda satırın toplam yüksekliğini döndürür. */
	public double yüksekliğiBul(int satırSayısı) {
		return (satırSayısı - 1) * inilecekYüksekliğiBul() + yüksekliği;
	}
	
	/** Ardaşık iki çizginin arasındaki yüksekliği döndürür. */
	public double inilecekYüksekliğiBul() {
		return yüksekliği * ÇİZGİLER_ARASI_BOŞLUĞUN_ORANI;
	}
}
