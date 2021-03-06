/**
 * başaşağıderebeyi.iskelet.görsel.yazı.SesŞekli.java
 * 0.6 / 5 Mar 2021 / 20:07:03
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import başaşağıderebeyi.kütüphane.matematik.*;

/** Belli bir yazı şeklindeki belli bir sesin şeklini saklayan nesne. */
public class SesŞekli {
	/** Sesin dokudaki sol üst köşesi. */
	public final Yöney2 dokuKonumu;
	/** Sesin dokudaki boyutu. */
	public final Yöney2 boyutu;
	/** Sesin çizginin üstünde kalan kısmının uzunluğu. */
	public final float çizgidenUzaklığı;
	
	/** Verilenlerle tanımlar. */
	SesŞekli(
		final Yöney2 dokuKonumu,
		final Yöney2 boyutu,
		final float çizgidenUzaklığı) {
		this.dokuKonumu = dokuKonumu;
		this.boyutu = boyutu;
		this.çizgidenUzaklığı = çizgidenUzaklığı;
	}
}
