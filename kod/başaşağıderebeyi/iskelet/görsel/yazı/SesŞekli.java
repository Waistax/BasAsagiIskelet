/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 5 Mar 2021 / 20:07:03
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

/** Belli bir yazı şeklindeki belli bir sesin şeklini saklayan nesne. */
public class SesŞekli {
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
}
