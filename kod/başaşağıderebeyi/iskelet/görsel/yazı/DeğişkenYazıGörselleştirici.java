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
	
	private final Gölgelendirici gölgelendiricisi;
	private final int sığası;
	private final SıralıOluşumluKöşeDizisi köşeDizisi;
	private final YazıŞekli şekli;
	private final Materyal materyali;
	
	private final Dönüşüm dönüşümü;
	
	private double ölçüsü;
	private int eklenmişSesSayısı;
	
	/** Verilenler ile tanımlar. */
	public DeğişkenYazıGörselleştirici(
		final Gölgelendirici gölgelendiricisi,
		final İzdüşüm izdüşümü,
		final int sığası,
		final YazıŞekli şekli,
		final double açısı) {
		this.gölgelendiricisi = gölgelendiricisi;
		gölgelendiricisiniKur(izdüşümü);
		this.sığası = sığası;
		
		köşeDizisi = new SıralıOluşumluKöşeDizisi(
			GL_TRIANGLES,
			sığası,
			SesŞekli.BOYUTU + Dönüşüm.BOYUTU);
		oluşumluKöşeDizisiniOluştur(açısı);
		this.şekli = şekli;
		materyali =
			new Materyal(şekli.dokusu, new Yöney3(Yöney3.BİR), new Yöney3());
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
	
	/** Verilen dizeleri satır satır yazar. */
	public void yaz(
		final double konumu,
		double çizgisi,
		final String... dizeler) {
		for (final String dize : dizeler) {
			yaz(konumu, çizgisi, dize);
			çizgisi -= şekli.enBüyükYüksekliği *
				ölçüsü *
				YazıŞekli.ÇİZGİLER_ARASI_BOŞLUĞUN_ORANI;
		}
	}
	
	/** Yazdığı yazının rengini döndürür. Bunun sayesinde renk
	 * değiştirilebilir. */
	public Yöney3 renginiEdin() {
		return materyali.rengi;
	}
	
	/** Yazdığı yazının arkaplan rengini döndürür. Bunun sayesinde arkaplan
	 * rengi değiştirilebilir. */
	public Yöney3 tersRenginiEdin() {
		return materyali.tersRengi;
	}
	
	/** Boyutunu değiştirir. */
	public void boyutunuDeğiştir(final double boyut) {
		ölçüsü = boyut / şekli.enBüyükYüksekliği;
	}
	
	private void yaz(double konumu, final double çizgisi, final String dize) {
		SesŞekli öncekiSesŞekli = null;
		for (int i = 0; i < dize.length(); i++) {
			final SesŞekli sesŞekli = şekli.sesininŞekliniEdin(dize.charAt(i));
			if (öncekiSesŞekli != null)
				konumu += (öncekiSesŞekli.boyutu.birinciBileşeniniEdin() +
					(sesŞekli.boyutu.birinciBileşeniniEdin() +
						öncekiSesŞekli.boyutu.birinciBileşeniniEdin()) *
						YazıŞekli.SESLER_ARASI_BOŞLUĞUN_ORANI) *
					ölçüsü;
			öncekiSesŞekli = sesŞekli;
			sesEkle(sesŞekli, konumu, çizgisi);
		}
	}
	
	private void sesEkle(
		final SesŞekli sesŞekli,
		final double konumu,
		final double çizgisi) {
		if (sığası < ++eklenmişSesSayısı)
			return;
		
		dönüşümü.boyutu
			.bileşenleriniDeğiştir(
				sesŞekli.boyutu.birinciBileşeni * ölçüsü,
				sesŞekli.boyutu.ikinciBileşeni * ölçüsü);
		
		dönüşümü.konumu
			.bileşenleriniDeğiştir(
				konumu + dönüşümü.boyutu.birinciBileşeniniEdin() / 2.0,
				çizgisi +
					sesŞekli.çizgidenUzaklığı * ölçüsü -
					dönüşümü.boyutu.ikinciBileşeniniEdin() / 2.0,
				0.0);
		
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
		final float yarımDikmeliği = (float)(sin(toRadians(açısı)) / 2.0);
		final float ileriNokta = 0.5F + yarımDikmeliği;
		final float geriNokta = 0.5F - yarımDikmeliği;
		return new float[] {
			-ileriNokta,
			-0.5F,
			geriNokta,
			-0.5F,
			-geriNokta,
			0.5F,
			ileriNokta,
			0.5F };
	}
}
