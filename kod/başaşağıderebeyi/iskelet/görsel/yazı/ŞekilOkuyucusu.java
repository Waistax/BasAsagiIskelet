/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 5 Mar 2021 / 21:34:04
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import başaşağıderebeyi.kütüphane.matematik.doğrusalcebir.*;

import java.util.*;

/** Bir yazı şeklinin bilgilerini okuyan araç. */
class ŞekilOkuyucusu {
	/** Yazı şekli dosyasındaki verileri ayıran ses. */
	public static final String AYIRICI_SES = "\\$";
	
	private final YazıŞekli okuduğuŞekil;
	private final double dokusununBoyutu;
	private final Yöney2 dokuKonumu;
	
	private double satırınÇizgisi;
	private double boşluklarınınBoyutu;
	private double enBüyükYüksekliği;
	private double satırdakiEnBüyükYüksekliği;
	
	ŞekilOkuyucusu(final YazıŞekli okuduğuŞekil, final List<String> satırları) {
		this.okuduğuŞekil = okuduğuŞekil;
		
		final String[] bilgileri = satırları.get(0).split(AYIRICI_SES);
		dokusununBoyutu = Integer.parseInt(bilgileri[2]);
		dokuKonumu = new Yöney2();
		
		satırları.forEach(this::satırıOku);
	}
	
	double enBüyükYüksekliğiniEdin() {
		return enBüyükYüksekliği;
	}
	
	private void satırıOku(final String satır) {
		final String[] verileri = satır.split(AYIRICI_SES);
		
		if (verileri.length != 4)
			satırDeğiştir(verileri);
		else {
			dokuKonumu
				.birinciBileşeniniDeğiştir(
					dokuKonumu.birinciBileşeniniEdin() + boşluklarınınBoyutu);
			sesŞekliniOku(verileri);
		}
	}
	
	private void satırDeğiştir(final String[] verileri) {
		satırınÇizgisi = Integer.parseInt(verileri[0]);
		boşluklarınınBoyutu = Integer.parseInt(verileri[1]);
		dokuKonumu.birinciBileşeniniDeğiştir(0.0);
		dokuKonumu
			.ikinciBileşeniniDeğiştir(
				dokuKonumu.ikinciBileşeniniEdin() +
					satırdakiEnBüyükYüksekliği +
					boşluklarınınBoyutu);
		
		if (enBüyükYüksekliği < satırdakiEnBüyükYüksekliği)
			enBüyükYüksekliği = satırdakiEnBüyükYüksekliği;
		
		satırdakiEnBüyükYüksekliği = 0.0;
	}
	
	private SesŞekli sesŞekliniOku(final String[] verileri) {
		final double dikeyKaçıklığı = Integer.parseInt(verileri[3]);
		final SesŞekli sesŞekli = new SesŞekli(
			new Yöney2()
				.bileşenleriniDeğiştir(
					dokuKonumu.birinciBileşeniniEdin(),
					dokuKonumu.ikinciBileşeniniEdin() + dikeyKaçıklığı),
			new Yöney2()
				.bileşenleriniDeğiştir(
					Integer.parseInt(verileri[1]),
					Integer.parseInt(verileri[2])),
			satırınÇizgisi - dikeyKaçıklığı,
			dokusununBoyutu);
		
		sesŞekliniİşle(
			sesŞekli,
			sesŞekli.boyutu.ikinciBileşeniniEdin() + dikeyKaçıklığı);
		
		okuduğuŞekil.seslerininŞekilleri.put(verileri[0].charAt(0), sesŞekli);
		return sesŞekli;
	}
	
	private void sesŞekliniİşle(
		final SesŞekli sesŞekli,
		final double yüksekliği) {
		dokuKonumu
			.birinciBileşeniniDeğiştir(
				dokuKonumu.birinciBileşeniniEdin() +
					sesŞekli.boyutu.birinciBileşeniniEdin());
		if (yüksekliği > satırdakiEnBüyükYüksekliği)
			satırdakiEnBüyükYüksekliği = yüksekliği;
	}
}
