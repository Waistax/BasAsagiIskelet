/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 5 Mar 2021 / 20:07:03
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import başaşağıderebeyi.kütüphane.matematik.sayısal.*;

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
	public final float çizgidenUzaklığı;
	
	/** Verilenlerle tanımlar. */
	SesŞekli(
		final Yöney2 dokuKonumu,
		final Yöney2 boyutu,
		final float çizgidenUzaklığı,
		final float dokusununBoyutu) {
		solÜstDokuKonumu = new Yöney2(dokuKonumu).böl(dokusununBoyutu);
		sağAltDokuKonumu = new Yöney2(dokuKonumu).topla(boyutu)
//			.çıkar(Yöney2.BİR)
			.böl(dokusununBoyutu);
		solAltDokuKonumu = new Yöney2(
			solÜstDokuKonumu.birinciBileşeni,
			sağAltDokuKonumu.ikinciBileşeni);
		sağÜstDokuKonumu = new Yöney2(
			sağAltDokuKonumu.birinciBileşeni,
			solÜstDokuKonumu.ikinciBileşeni);
		
		this.boyutu = boyutu;
		this.çizgidenUzaklığı = çizgidenUzaklığı;
	}
}
