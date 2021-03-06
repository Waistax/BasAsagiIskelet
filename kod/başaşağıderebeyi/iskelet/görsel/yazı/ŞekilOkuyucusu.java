/**
 * başaşağıderebeyi.iskelet.görsel.yazı.ŞekilOkuyucusu.java
 * 0.6 / 5 Mar 2021 / 21:34:04
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import başaşağıderebeyi.kütüphane.matematik.*;

import java.util.*;

/** Bir yazı şeklinin bilgilerini okuyan araç. */
class ŞekilOkuyucusu {
	/** Yazı şekli dosyasındaki verileri ayıran ses. */
	public static final String AYIRICI_SES = "\\$";
	
	private final YazıŞekli okuduğuŞekil;
	private final float dokusununBoyutu;
	private final Yöney2 dokuKonumu;
	
	private float satırınÇizgisi;
	private float boşluklarınınBoyutu;
	private float enBüyükYüksekliği;
	private float satırdakiEnBüyükYüksekliği;
	
	ŞekilOkuyucusu(final YazıŞekli okuduğuŞekil, final List<String> satırları) {
		this.okuduğuŞekil = okuduğuŞekil;
		
		final String[] bilgileri = satırları.get(0).split(AYIRICI_SES);
		dokusununBoyutu = Integer.parseInt(bilgileri[2]);
		dokuKonumu = new Yöney2();
		
		satırları.forEach(this::satırıOku);
	}
	
	float enBüyükYüksekliğiniEdin() {
		return enBüyükYüksekliği;
	}
	
	private void satırıOku(final String satır) {
		final String[] verileri = satır.split(AYIRICI_SES);
		
		if (verileri.length != 4)
			satırDeğiştir(verileri);
		else {
			dokuKonumu.birinciBileşeni += boşluklarınınBoyutu;
			sesŞekliniOku(verileri);
		}
	}
	
	private void satırDeğiştir(final String[] verileri) {
		satırınÇizgisi = Integer.parseInt(verileri[0]);
		boşluklarınınBoyutu = Integer.parseInt(verileri[1]);
		dokuKonumu.birinciBileşeni = 0.0F;
		dokuKonumu.ikinciBileşeni +=
			satırdakiEnBüyükYüksekliği + boşluklarınınBoyutu;
		
		if (enBüyükYüksekliği < satırdakiEnBüyükYüksekliği)
			enBüyükYüksekliği = satırdakiEnBüyükYüksekliği;
		
		satırdakiEnBüyükYüksekliği = 0.0F;
	}
	
	private SesŞekli sesŞekliniOku(final String[] verileri) {
		float dikeyKaçıklığı = Integer.parseInt(verileri[3]);
		final SesŞekli sesŞekli = new SesŞekli(
			new Yöney2()
				.bileşenleriniDeğiştir(
					dokuKonumu.birinciBileşeni,
					dokuKonumu.ikinciBileşeni + dikeyKaçıklığı),
			new Yöney2(
				Integer.parseInt(verileri[1]),
				Integer.parseInt(verileri[2])),
			satırınÇizgisi - dikeyKaçıklığı,
			dokusununBoyutu);
		
		sesŞekliniİşle(
			sesŞekli,
			sesŞekli.boyutu.ikinciBileşeni + dikeyKaçıklığı);
		
		okuduğuŞekil.seslerininŞekilleri.put(verileri[0].charAt(0), sesŞekli);
		return sesŞekli;
	}
	
	private void sesŞekliniİşle(final SesŞekli sesŞekli, float yüksekliği) {
		dokuKonumu.birinciBileşeni += sesŞekli.boyutu.birinciBileşeni;
		if (yüksekliği > satırdakiEnBüyükYüksekliği)
			satırdakiEnBüyükYüksekliği = yüksekliği;
	}
}
