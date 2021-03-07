/**
 * başaşağıderebeyi.iskelet.görsel.yazı.DeğişkenYazıGörselleştirici.java
 * 0.6 / 5 Mar 2021 / 21:59:54
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import static başaşağıderebeyi.kütüphane.matematik.MatematikAracı.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.köşedizisi.*;
import başaşağıderebeyi.kütüphane.matematik.*;

/** Kısa aralıklarla değişmesi beklenen ve geçici yazıları çizmek için
 * kullanılan araç. Bu sınıfla sabit yazılar da çizilebilir ama sabit yazıları
 * çizmek için daha verimli yöntemler var. */
public class DeğişkenYazıGörselleştirici {
	private final Gölgelendirici gölgelendiricisi;
	private final Dizey4 izdüşümDizeyi;
	private final SıralıOluşumluKöşeDizisi köşeDizisi;
	private final int sığası;
	private final YazıŞekli şekli;
	private final Yöney4 rengi;
	private final Dönüşüm dönüşümü;
	
	private float ölçüsü;
	private int çizilmişSesSayısı;
	
	/** Verilenler ile tanımlar. */
	public DeğişkenYazıGörselleştirici(
		final Yükleyici yükleyici,
		final float uzayınGenişliği,
		final float uzayınYüksekliği,
		final float uzayınDerinliği,
		final int sığası,
		final YazıŞekli şekli,
		float açısı,
		float saydamlıkEşiği) {
		gölgelendiricisi = new Gölgelendirici(yükleyici, "değişkenYazı");
		gölgelendiricisi.değerinKonumunuBul("izdusumDizeyi");
		gölgelendiricisi.değerinKonumunuBul("saydamlikEsigi");
		
		izdüşümDizeyi = new Dizey4()
			.izdüşümDizeyineÇevir(
				uzayınGenişliği,
				uzayınYüksekliği,
				uzayınDerinliği);
		
		gölgelendiricisi.bağla();
		gölgelendiricisi.değeriDeğiştir("izdusumDizeyi", izdüşümDizeyi);
		gölgelendiricisi.değeriDeğiştir("saydamlikEsigi", saydamlıkEşiği);
		gölgelendiricisi.kopar();
		
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
		çizilmişSesSayısı = 0;
	}
	
	/** Rengini döndürür. */
	public Yöney4 renginiEdin() {
		return rengi;
	}
	
	/** Boyutunu değiştirir. */
	public void boyutunuDeğiştir(final float boyut) {
		ölçüsü = boyut / şekli.enBüyükYükseklik;
	}
	
	/** Verilen dizeleri satır satır yazar. */
	public void yaz(
		final float konumu,
		float çizgisi,
		final String... dizeler) {
		for (final String dize : dizeler)
			çizgisi = yaz(konumu, çizgisi, dize);
	}
	
	private float yaz(float konumu, final float çizgisi, final String dize) {
		for (int i = 0; i < dize.length(); i++)
			konumu = sesEkle(dize.charAt(i), konumu, çizgisi);
		return çizgisi - aşağıYuvarla(şekli.enBüyükYükseklik * ölçüsü * 1.1F);
	}
	
	private float sesEkle(
		final char ses,
		final float konumu,
		final float çizgisi) {
		if (sığası == çizilmişSesSayısı)
			return 0.0F;
		çizilmişSesSayısı++;
		
		final SesŞekli sesŞekli = şekli.sesininŞekliniEdin(ses);
		
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
		
		return konumu + yuvarla(dönüşümü.biçimi.birinciBileşeni * 1.1F);
	}
	
	private void oluşumluKöşeDizisiniOluştur(float açısı) {
		float yarımDikmeliği = dikmeliğiniBul(radyanaÇevir(açısı)) / 2.0F;
		float ileriNokta = 0.5F + yarımDikmeliği;
		float geriNokta = 0.5F - yarımDikmeliği;
		
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
					.put(1.0F)
					.flip());
		
		köşeDizisi
			.sıraTamponuNesnesiYükle(
				memAllocInt(6)
					.put(0)
					.put(1)
					.put(2)
					.put(2)
					.put(1)
					.put(3)
					.flip());
		
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
