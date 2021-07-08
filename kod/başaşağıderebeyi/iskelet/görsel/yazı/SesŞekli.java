/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 5 Mar 2021 / 20:07:03
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import başaşağıderebeyi.kütüphane.matematik.*;
import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

/** Belli bir yazı şeklindeki belli bir sesin şeklini saklayan nesne. */
public class SesŞekli {
	/** Sesin dokudaki sol alt köşesi. */
	public final Yöney solAltDokuKonumu;
	/** Sesin dokudaki sağ alt köşesi. */
	public final Yöney sağAltDokuKonumu;
	/** Sesin dokudaki sol üst köşesi. */
	public final Yöney solÜstDokuKonumu;
	/** Sesin dokudaki sağ üst köşesi. */
	public final Yöney sağÜstDokuKonumu;
	/** Sesin dokudaki boyutu. */
	public final Yöney boyutu;
	/** Sesin çizginin üstünde kalan kısmının uzunluğu. */
	public final double çizgidenUzaklığı;
	
	/** Verilenlerle tanımlar. */
	SesŞekli(
		final Yöney dokuKonumu,
		final Yöney boyutu,
		final double çizgidenUzaklığı,
		final double dokusununBoyutu) {
		solÜstDokuKonumu = new Yöney(dokuKonumu);
		solÜstDokuKonumu
			.bul(solÜstDokuKonumu, dokusununBoyutu, MatematikAracı::böl);
		sağAltDokuKonumu = new Yöney(dokuKonumu);
		sağAltDokuKonumu
			.bul(sağAltDokuKonumu, boyutu, MatematikAracı::topla)
			.bul(sağAltDokuKonumu, dokusununBoyutu, MatematikAracı::böl);
		solAltDokuKonumu = new Yöney(2)
			.bileşenleriniDeğiştir(
				solÜstDokuKonumu.sayısınıEdin(0),
				sağAltDokuKonumu.sayısınıEdin(1));
		sağÜstDokuKonumu = new Yöney(2)
			.bileşenleriniDeğiştir(
				sağAltDokuKonumu.sayısınıEdin(0),
				solÜstDokuKonumu.sayısınıEdin(1));
		this.boyutu = boyutu;
		this.çizgidenUzaklığı = çizgidenUzaklığı;
	}
}
