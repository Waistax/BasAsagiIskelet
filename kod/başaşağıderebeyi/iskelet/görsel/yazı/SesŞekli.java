/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 5 Mar 2021 / 20:07:03
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import başaşağıderebeyi.iskelet.görsel.köşedizisi.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

import java.nio.*;

/** Belli bir yazı şeklindeki belli bir sesin şeklini saklayan nesne. */
public class SesŞekli {
	/** Ses şeklinin oluşumlu köşe dizisinde kapladığı boyut. */
	public static final int BOYUTU = 2 + 2 + 2 + 2;
	
	/** Verilen oluşumlu köşe dizisinde ses şekli için yer ekler. */
	public static void oluşumluKöşeDizisineEkle(
		final OluşumluKöşeDizisi köşeDizisi) {
		köşeDizisi
			.oluşumBaşınaDeğişenNitelikEkle(2)
			.oluşumBaşınaDeğişenNitelikEkle(2)
			.oluşumBaşınaDeğişenNitelikEkle(2)
			.oluşumBaşınaDeğişenNitelikEkle(2);
	}
	
	/** Sesin dokudaki sol alt köşesi. */
	public final Yöney2 solAltDokuKonumu;
	/** Sesin dokudaki sağ alt köşesi. */
	public final Yöney2 sağAltDokuKonumu;
	/** Sesin dokudaki sol üst köşesi. */
	public final Yöney2 solÜstDokuKonumu;
	/** Sesin dokudaki sağ üst köşesi. */
	public final Yöney2 sağÜstDokuKonumu;
	/** Sesin dokudaki boyutu. */
	public final Yöney2 boyutu;
	/** Sesin çizginin üstünde kalan kısmının uzunluğu. */
	public final double çizgidenUzaklığı;
	
	/** Verilenlerle tanımlar. */
	SesŞekli(
		final Yöney2 dokuKonumu,
		final Yöney2 boyutu,
		final double çizgidenUzaklığı,
		final double dokusununBoyutu) {
		solÜstDokuKonumu = (Yöney2)new Yöney2(dokuKonumu).böl(dokusununBoyutu);
		sağAltDokuKonumu =
			(Yöney2)new Yöney2(dokuKonumu).topla(boyutu).böl(dokusununBoyutu);
		solAltDokuKonumu = new Yöney2()
			.bileşenleriniDeğiştir(
				solÜstDokuKonumu.birinciBileşeniniEdin(),
				sağAltDokuKonumu.ikinciBileşeniniEdin());
		sağÜstDokuKonumu = new Yöney2()
			.bileşenleriniDeğiştir(
				sağAltDokuKonumu.birinciBileşeniniEdin(),
				solÜstDokuKonumu.ikinciBileşeniniEdin());
		this.boyutu = boyutu;
		this.çizgidenUzaklığı = çizgidenUzaklığı;
	}
	
	/** Ses şeklini tampona yükler. */
	public void yükle(final FloatBuffer tampon) {
		tampon
			.put((float)solAltDokuKonumu.birinciBileşeniniEdin())
			.put((float)solAltDokuKonumu.ikinciBileşeniniEdin())
			.put((float)sağAltDokuKonumu.birinciBileşeniniEdin())
			.put((float)sağAltDokuKonumu.ikinciBileşeniniEdin())
			.put((float)solÜstDokuKonumu.birinciBileşeniniEdin())
			.put((float)solÜstDokuKonumu.ikinciBileşeniniEdin())
			.put((float)sağÜstDokuKonumu.birinciBileşeniniEdin())
			.put((float)sağÜstDokuKonumu.ikinciBileşeniniEdin());
	}
}
