/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 5 Mar 2021 / 21:59:54
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.görüntü.*;
import başaşağıderebeyi.iskelet.görsel.köşedizisi.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

/** Kısa aralıklarla değişmesi beklenen ve geçici yazıları çizmek için
 * kullanılan araç. Bu sınıfla sabit yazılar da çizilebilir ama sabit yazıları
 * çizmek için daha verimli yöntemler var. */
public class DeğişkenYazıGörselleştirici {
	private static final int[] KÖŞE_SIRASI = { 0, 1, 2, 2, 1, 3 };
	
	/** Yazdıklarının şekli. */
	public final YazıŞekli şekli;
	/** Yazdıklarının materyali. Bu materyalin dokusu yazı şeklinin dokusu ile
	 * aynı olmalıdır. Bu nesne ilk tanımlandığında materyalin dokusunu yazı
	 * şeklinin dokusu ile değiştirir. */
	public final Materyal materyali;
	
	private final Gölgelendirici gölgelendiricisi;
	private final int sığası;
	private final SıralıOluşumluKöşeDizisi köşeDizisi;
	private final Dönüşüm dönüşümü;
	
	private int eklenmişSesSayısı;
	
	/** Verilenler ile tanımlar. Beyaz renkte ve zemin rengi olmayan bir
	 * materyal oluşturur. */
	public DeğişkenYazıGörselleştirici(
		final YazıŞekli şekli,
		final Gölgelendirici gölgelendiricisi,
		final İzdüşüm izdüşümü,
		final int sığası,
		final double açısı) {
		this(
			şekli,
			new Materyal(0, new Yöney4(Yöney4.BİR), new Yöney4()),
			gölgelendiricisi,
			izdüşümü,
			sığası,
			açısı);
	}
	
	/** Verilenler ile tanımlar. Materyalin dokusunu kendiliğinden yazı şeklinin
	 * dokusuna değiştirir. */
	public DeğişkenYazıGörselleştirici(
		final YazıŞekli şekli,
		final Materyal materyali,
		final Gölgelendirici gölgelendiricisi,
		final İzdüşüm izdüşümü,
		final int sığası,
		final double açısı) {
		this.şekli = şekli;
		this.materyali = materyali;
		materyali.dokusu = şekli.dokusu;
		this.gölgelendiricisi = gölgelendiricisi;
		gölgelendiricisiniKur(izdüşümü);
		this.sığası = sığası;
		köşeDizisi = new SıralıOluşumluKöşeDizisi(
			GL_TRIANGLES,
			sığası,
			SesŞekli.BOYUTU + Dönüşüm.BOYUTU);
		oluşumluKöşeDizisiniOluştur(açısı);
		dönüşümü = new Dönüşüm();
		boyutunuDeğiştir(şekli.boyutu.ikinciBileşeniniEdin());
	}
	
	/** Şu ana kadar yazılmış yazıları çizer. */
	public void çiz(final Bakış bakış) {
		gölgelendiricisi.bağla();
		bakış.yükle(gölgelendiricisi);
		materyali.yükle(gölgelendiricisi);
		köşeDizisi.tamponunuGüncelle().çiz();
		gölgelendiricisi.kopar();
		eklenmişSesSayısı = 0;
	}
	
	/** Verilen dizelerin ortası konuma gelecek şekilde satır satır yazar. */
	public void tamOrtayaYaz(
		final double yatayKonumu,
		final double dikeyKonumu,
		final double derinliği,
		final String... satırlar) {
		ortalıYaz(
			yatayKonumu,
			dikeyKonumu + yüksekliğiBul(satırlar) / 2.0,
			derinliği,
			satırlar);
	}
	
	/** Verilen dizelerin ortası konuma gelecek şekilde satır satır yazar. */
	public void ortalıYaz(
		final double yatayKonumu,
		final double dikeyKonumu,
		final double derinliği,
		final String... satırlar) {
		konumunuDeğiştir(dikeyKonumu, derinliği);
		for (final String satır : satırlar) {
			yaz(yatayKonumu - uzunluğunuBul(satır) / 2.0, satır);
		}
	}
	
	/** Verilen dizeleri satır satır yazar. */
	public void yaz(
		final double yatayKonumu,
		double dikeyKonumu,
		final double derinliği,
		final String... satırlar) {
		konumunuDeğiştir(dikeyKonumu, derinliği);
		for (final String satır : satırlar) {
			yaz(yatayKonumu, satır);
		}
	}
	
	/** Boyutunu değiştirir. Bu boyut yazı şeklinin yüksekliğidir. Diğer her
	 * boyut buna göre ölçeklenir. */
	public void boyutunuDeğiştir(final double boyut) {
		dönüşümü.boyutu
			.bileşenleriniDeğiştir(
				şekli.boyutu.birinciBileşeniniEdin() *
					boyut /
					şekli.boyutu.ikinciBileşeniniEdin(),
				boyut);
	}
	
	/** Verilen dizenin toplam uzunluğunu bulur. */
	public double uzunluğunuBul(final String dize) {
		return uzunluğunuBul(dize.length());
	}
	
	/** Verilen sayıda sesin toplam uzunluğunu bulur. */
	public double uzunluğunuBul(final int sesSayısı) {
		return sesSayısı * dönüşümü.boyutu.birinciBileşeniniEdin();
	}
	
	/** Verilen satırların toplam yüksekliğini döndürür. */
	public double yüksekliğiBul(final String... satırlar) {
		return yüksekliğiBul(satırlar.length);
	}
	
	/** Verilen sayıda satırın toplam yüksekliğini döndürür. */
	public double yüksekliğiBul(final int satırSayısı) {
		return satırSayısı * dönüşümü.boyutu.ikinciBileşeniniEdin();
	}
	
	private void konumunuDeğiştir(
		final double dikeyKonumu,
		final double derinliği) {
		dönüşümü.konumu.ikinciBileşeniniDeğiştir(dikeyKonumu);
		dönüşümü.konumu.üçüncüBileşeniniDeğiştir(derinliği);
	}
	
	private void yaz(final double yatayKonumu, final String satır) {
		dönüşümü.konumu.birinciBileşeni = yatayKonumu;
		yaz(satır);
		dönüşümü.konumu.ikinciBileşeni -=
			dönüşümü.boyutu.ikinciBileşeniniEdin();
	}
	
	private void yaz(final String satır) {
		for (int i = 0; i < satır.length(); i++) {
			final SesŞekli sesŞekli = şekli.sesininŞekliniEdin(satır.charAt(i));
			if (sesŞekli == null)
				continue;
			sesEkle(sesŞekli);
			dönüşümü.konumu.birinciBileşeni += dönüşümü.boyutu.birinciBileşeni;
		}
	}
	
	private void sesEkle(final SesŞekli sesŞekli) {
		if (sığası < ++eklenmişSesSayısı)
			return;
		sesŞekli.yükle(köşeDizisi.yazılacakVerisi);
		dönüşümü.yükle(köşeDizisi.yazılacakVerisi);
	}
	
	private void gölgelendiricisiniKur(final İzdüşüm izdüşümü) {
		gölgelendiricisi.bağla();
		İzdüşüm.değerlerininKonumlarınıBul(gölgelendiricisi);
		Bakış.değerlerininKonumlarınıBul(gölgelendiricisi);
		Materyal.değerlerininKonumlarınıBul(gölgelendiricisi);
		izdüşümü.yükle(gölgelendiricisi);
		gölgelendiricisi.kopar();
	}
	
	private void oluşumluKöşeDizisiniOluştur(final double açısı) {
		final float[] köşeKonumları = köşeKonumlarınıBul(açısı);
		köşeDizisi
			.sıraTamponuNesnesiYükle(
				memAllocInt(KÖŞE_SIRASI.length).put(KÖŞE_SIRASI))
			.durağanKöşeTamponuNesnesiEkle(
				köşeKonumları.length / 4,
				memAllocFloat(köşeKonumları.length).put(köşeKonumları));
		SesŞekli.oluşumluKöşeDizisineEkle(köşeDizisi);
		Dönüşüm.oluşumluKöşeDizisineEkle(köşeDizisi);
	}
	
	private float[] köşeKonumlarınıBul(final double açısı) {
		final float yarıEğimi = (float)(tan(toRadians(açısı)) / 2.0);
		final float ileriNoktası = 0.5F + yarıEğimi;
		final float geriNoktası = 0.5F - yarıEğimi;
		return new float[] {
			-ileriNoktası,
			0.0F,
			geriNoktası,
			0.0F,
			-geriNoktası,
			1.0F,
			ileriNoktası,
			1.0F };
	}
}
