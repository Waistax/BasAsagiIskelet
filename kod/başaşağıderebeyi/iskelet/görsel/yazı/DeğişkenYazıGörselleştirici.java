/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 5 Mar 2021 / 21:59:54
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import static başaşağıderebeyi.kütüphane.matematik.MatematikAracı.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.köşedizisi.*;
import başaşağıderebeyi.kütüphane.matematik.sayısal.*;

/** Kısa aralıklarla değişmesi beklenen ve geçici yazıları çizmek için
 * kullanılan araç. Bu sınıfla sabit yazılar da çizilebilir ama sabit yazıları
 * çizmek için daha verimli yöntemler var. */
public class DeğişkenYazıGörselleştirici {
	private final Gölgelendirici gölgelendiricisi;
	private final SıralıOluşumluKöşeDizisi köşeDizisi;
	private final int sığası;
	private final YazıŞekli şekli;
	private final Yöney4 rengi;
	private final Dönüşüm dönüşümü;
	
	private float ölçüsü;
	private int eklenmişSesSayısı;
	
	/** Verilenler ile tanımlar. */
	public DeğişkenYazıGörselleştirici(
		final Yükleyici yükleyici,
		final int sığası,
		final YazıŞekli şekli,
		final float açısı,
		final Dizey4 izdüşümDizeyi,
		final float saydamlıkEşiği) {
		gölgelendiricisi = new Gölgelendirici(yükleyici, "değişkenYazı");
		gölgelendiricisiniOluştur(izdüşümDizeyi, saydamlıkEşiği);
		
		köşeDizisi =
			new SıralıOluşumluKöşeDizisi(yükleyici, GL_TRIANGLES, sığası, 28);
		oluşumluKöşeDizisiniOluştur(açısı);
		this.sığası = sığası;
		this.şekli = şekli;
		rengi = new Yöney4();
		dönüşümü = new Dönüşüm();
	}
	
	/** Şu ana kadar yazılmış yazıları çizer. */
	public void çiz() {
		köşeDizisi.tamponunuGüncelle();
		gölgelendiricisi.bağla();
		şekli.bağla();
		köşeDizisi.çiz();
		şekli.kopar();
		gölgelendiricisi.kopar();
		eklenmişSesSayısı = 0;
	}
	
	/** Rengini döndürür. */
	public Yöney4 renginiEdin() {
		return rengi;
	}
	
	/** Boyutunu değiştirir. */
	public void boyutunuDeğiştir(final float boyut) {
		ölçüsü = boyut / şekli.enBüyükYüksekliği;
	}
	
	/** Verilen dizeleri satır satır yazar. */
	public void yaz(
		final float konumu,
		float çizgisi,
		final String... dizeler) {
		for (final String dize : dizeler) {
			yaz(konumu, çizgisi, dize);
			çizgisi -= şekli.enBüyükYüksekliği *
				ölçüsü *
				YazıŞekli.ÇİZGİLER_ARASI_BOŞLUĞUN_ORANI;
		}
	}
	
	private void yaz(float konumu, final float çizgisi, final String dize) {
		for (int i = 0; i < dize.length(); i++) {
			final SesŞekli sesŞekli = şekli.sesininŞekliniEdin(dize.charAt(i));
			if (i > 0) {
				final SesŞekli öncekiSesŞekli =
					şekli.sesininŞekliniEdin(dize.charAt(i - 1));
				konumu += (öncekiSesŞekli.boyutu.birinciBileşeni +
					(sesŞekli.boyutu.birinciBileşeni +
						öncekiSesŞekli.boyutu.birinciBileşeni) *
						YazıŞekli.SESLER_ARASI_BOŞLUĞUN_ORANI) *
					ölçüsü;
			}
			sesEkle(sesŞekli, konumu, çizgisi);
		}
	}
	
	private void sesEkle(
		final SesŞekli sesŞekli,
		final float konumu,
		final float çizgisi) {
		if (sığası < ++eklenmişSesSayısı)
			return;
		
		dönüşümü.biçimi
			.bileşenleriniDeğiştir(
				sesŞekli.boyutu.birinciBileşeni * ölçüsü,
				sesŞekli.boyutu.ikinciBileşeni * ölçüsü,
				0.0F);
		
		dönüşümü.konumu
			.bileşenleriniDeğiştir(
				konumu + dönüşümü.biçimi.birinciBileşeni / 2.0F,
				çizgisi +
					sesŞekli.çizgidenUzaklığı * ölçüsü -
					dönüşümü.biçimi.ikinciBileşeni / 2.0F,
				0.0F);
		
		sesiYükle(sesŞekli);
	}
	
	private void sesiYükle(final SesŞekli sesŞekli) {
		dönüşümü.güncelle();
		
		köşeDizisi.yazılacakVerisi
			.put(sesŞekli.solAltDokuKonumu.birinciBileşeni)
			.put(sesŞekli.solAltDokuKonumu.ikinciBileşeni)
			.put(sesŞekli.sağAltDokuKonumu.birinciBileşeni)
			.put(sesŞekli.sağAltDokuKonumu.ikinciBileşeni)
			.put(sesŞekli.solÜstDokuKonumu.birinciBileşeni)
			.put(sesŞekli.solÜstDokuKonumu.ikinciBileşeni)
			.put(sesŞekli.sağÜstDokuKonumu.birinciBileşeni)
			.put(sesŞekli.sağÜstDokuKonumu.ikinciBileşeni)
			.put(rengi.birinciBileşeni)
			.put(rengi.ikinciBileşeni)
			.put(rengi.üçüncüBileşeni)
			.put(rengi.dördüncüBileşeni)
			.put(dönüşümü.dizeyi.girdileri);
	}
	
	private void gölgelendiricisiniOluştur(
		final Dizey4 izdüşümDizeyi,
		final float saydamlıkEşiği) {
		gölgelendiricisi.değerinKonumunuBul("izdusumDizeyi");
		gölgelendiricisi.değerinKonumunuBul("saydamlikEsigi");
		
		gölgelendiricisi.bağla();
		gölgelendiricisi.değeriDeğiştir("izdusumDizeyi", izdüşümDizeyi);
		gölgelendiricisi.değeriDeğiştir("saydamlikEsigi", saydamlıkEşiği);
		gölgelendiricisi.kopar();
	}
	
	private void oluşumluKöşeDizisiniOluştur(final float açısı) {
		final float yarımDikmeliği = dikmeliğiniBul(radyanaÇevir(açısı)) / 2.0F;
		final float ileriNokta = 0.5F + yarımDikmeliği;
		final float geriNokta = 0.5F - yarımDikmeliği;
		
		köşeDizisi
			.durağanKöşeTamponuNesnesiEkle(
				4,
				memAllocFloat(16)
					.put(-ileriNokta)
					.put(-0.5F)
					.put(0.0F)
					.put(1.0F)
					.put(geriNokta)
					.put(-0.5F)
					.put(0.0F)
					.put(1.0F)
					.put(-geriNokta)
					.put(+0.5F)
					.put(0.0F)
					.put(1.0F)
					.put(ileriNokta)
					.put(+0.5F)
					.put(0.0F)
					.put(1.0F));
		
		köşeDizisi
			.sıraTamponuNesnesiYükle(
				memAllocInt(6).put(0).put(1).put(2).put(2).put(1).put(3));
		
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(2);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(2);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(2);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(2);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
		köşeDizisi.oluşumBaşınaDeğişenNitelikEkle(4);
	}
}
