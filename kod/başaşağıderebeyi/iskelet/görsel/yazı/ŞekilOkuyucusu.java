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
	private final YazıŞekli okuduğuŞekil;
	private final float dokusununBoyutu;
	private final float boşluklarınınBoyutu;
	private final Yöney2 dokuKonumu;
	
	private float enBüyükYüksekliği;
	private float satırdakiEnBüyükYüksekliği;
	
	ŞekilOkuyucusu(final YazıŞekli okuduğuŞekil, final List<String> satırları) {
		this.okuduğuŞekil = okuduğuŞekil;
		
		final String[] bilgileri = satırları.get(0).split(";");
		dokusununBoyutu = Integer.parseInt(bilgileri[0]);
		boşluklarınınBoyutu = Integer.parseInt(bilgileri[1]);
		dokuKonumu = new Yöney2();
		
		satırları.forEach(this::satırıOku);
	}
	
	float enBüyükYüksekliğiniEdin() {
		return enBüyükYüksekliği;
	}
	
	private void satırıOku(final String satır) {
		final String[] verileri = satır.split(";");
		
		if (verileri.length != 4)
			satırDeğiştir();
		else {
			dokuKonumu.birinciBileşeni += boşluklarınınBoyutu;
			sesŞekliniİşle(sesŞekliniOku(verileri));
		}
	}
	
	private void satırDeğiştir() {
		dokuKonumu.birinciBileşeni = 0.0F;
		dokuKonumu.ikinciBileşeni +=
			satırdakiEnBüyükYüksekliği + boşluklarınınBoyutu;
		
		if (enBüyükYüksekliği < satırdakiEnBüyükYüksekliği)
			enBüyükYüksekliği = satırdakiEnBüyükYüksekliği;
		
		satırdakiEnBüyükYüksekliği = 0.0F;
	}
	
	private SesŞekli sesŞekliniOku(final String[] verileri) {
		final SesŞekli sesŞekli = new SesŞekli(
			dokuKonumu,
			new Yöney2(
				Integer.parseInt(verileri[1]),
				Integer.parseInt(verileri[2])),
			Integer.parseInt(verileri[3]),
			dokusununBoyutu);
		
		okuduğuŞekil.seslerininŞekilleri.put(verileri[0].charAt(0), sesŞekli);
		return sesŞekli;
	}
	
	private void sesŞekliniİşle(final SesŞekli sesŞekli) {
		dokuKonumu.birinciBileşeni += sesŞekli.boyutu.birinciBileşeni;
		
		if (sesŞekli.boyutu.ikinciBileşeni > satırdakiEnBüyükYüksekliği)
			satırdakiEnBüyükYüksekliği = sesŞekli.boyutu.ikinciBileşeni;
	}
}
