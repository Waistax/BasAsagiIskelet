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
	
	private double ölçüsü;
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
		ölçüsü = 1.0;
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
		final double konumu,
		double çizgisi,
		final double derinliği,
		final String... satırlar) {
		dönüşümü.konumu.üçüncüBileşeniniDeğiştir(derinliği);
		for (final String satır : satırlar) {
			yaz(
				konumu - şekli.uzunluğunuBul(satır) * ölçüsü / 2.0,
				çizgisi,
				derinliği,
				satır);
			çizgisi -= şekli.inilecekYüksekliğiBul() * ölçüsü;
		}
	}
	
	/** Verilen dizelerin ortası konuma gelecek şekilde satır satır yazar. */
	public void ortalıYaz(
		final double konumu,
		double çizgisi,
		final double derinliği,
		final String... satırlar) {
		dönüşümü.konumu.üçüncüBileşeniniDeğiştir(derinliği);
		for (final String satır : satırlar) {
			yaz(
				konumu - şekli.uzunluğunuBul(satır) * ölçüsü / 2.0,
				çizgisi,
				derinliği,
				satır);
			çizgisi -= şekli.inilecekYüksekliğiBul() * ölçüsü;
		}
	}
	
	/** Verilen dizeleri satır satır yazar. */
	public void yaz(
		final double konumu,
		double çizgisi,
		final double derinliği,
		final String... satırlar) {
		dönüşümü.konumu.üçüncüBileşeniniDeğiştir(derinliği);
		for (final String satır : satırlar) {
			yaz(konumu, çizgisi, derinliği, satır);
			çizgisi -= şekli.inilecekYüksekliğiBul() * ölçüsü;
		}
	}
	
	/** Boyutunu değiştirir. Bu boyut yazı şeklinin olabilecek en yüksek
	 * satırının boyutudur. Diğer her boyut buna göre ölçeklenir. */
	public void boyutunuDeğiştir(final double boyut) {
		ölçüsü = boyut / şekli.yüksekliği;
		dönüşümü.boyutu.ikinciBileşeniniDeğiştir(boyut);
	}
	
	private void yaz(
		double konumu,
		final double çizgisi,
		final double derinliği,
		final String satır) {
		SesŞekli öncekiSesŞekli = null;
		dönüşümü.konumu.ikinciBileşeniniDeğiştir(çizgisi);
		for (int i = 0; i < satır.length(); i++) {
			final SesŞekli sesŞekli = şekli.sesininŞekliniEdin(satır.charAt(i));
			if (sesŞekli == null)
				continue;
			if (öncekiSesŞekli != null)
				konumu += şekli.atlanacakUzunluğuBul(öncekiSesŞekli, sesŞekli) *
					ölçüsü;
			öncekiSesŞekli = sesŞekli;
			dönüşümü.konumu.birinciBileşeniniDeğiştir(konumu);
			sesEkle(sesŞekli);
		}
	}
	
	private void sesEkle(final SesŞekli sesŞekli) {
		if (sığası < ++eklenmişSesSayısı)
			return;
		dönüşümü.boyutu.birinciBileşeniniDeğiştir(sesŞekli.genişliği * ölçüsü);
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
		final float çizgiAltıOranı =
			(float)(şekli.çizgiAltıYüksekliği / şekli.yüksekliği);
		final float dikmeliği = (float)sin(toRadians(açısı));
		return new float[] {
			0.0F,
			-çizgiAltıOranı,
			1.0F,
			-çizgiAltıOranı,
			dikmeliği,
			1.0F - çizgiAltıOranı,
			1.0F + dikmeliği,
			1.0F - çizgiAltıOranı };
	}
}
