/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 5 Mar 2021 / 21:34:04
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import java.util.*;

/** Bir yazı şeklinin bilgilerini okuyan araç. */
class ŞekilOkuyucusu {
	/** Yazı şekli dosyasındaki verileri ayıran ses. */
	public static final String AYIRICI_SES = " ";
	
	private final YazıŞekli okuduğuŞekil;
	
	final double dokusununBoyutu;
	final double yüksekliği;
	final double çizgiÜstüYüksekliği;
	final double çizgiAltıYüksekliği;
	final double boşluklarınınBoyutu;
	
	double üstKonumu;
	double solKonumu;
	
	ŞekilOkuyucusu(final YazıŞekli okuduğuŞekil, final List<String> verisi) {
		this.okuduğuŞekil = okuduğuŞekil;
		
		final String[] bilgileri = verisi.get(0).split(AYIRICI_SES);
		dokusununBoyutu = Integer.parseInt(bilgileri[0]);
		yüksekliği = Integer.parseInt(bilgileri[1]);
		çizgiÜstüYüksekliği = Integer.parseInt(bilgileri[2]);
		çizgiAltıYüksekliği = Integer.parseInt(bilgileri[3]);
		boşluklarınınBoyutu = Integer.parseInt(bilgileri[4]);
		
		üstKonumu = boşluklarınınBoyutu;
		solKonumu = boşluklarınınBoyutu;
		verisi.forEach(this::dizesiniOku);
	}
	
	private void dizesiniOku(final String dizesi) {
		if (dizesi.isEmpty()) {
			satırAtla();
			return;
		}
		
		if (dizesi.lastIndexOf(AYIRICI_SES) != 1)
			return;
		
		sesŞekliniOku(dizesi);
	}
	
	private void satırAtla() {
		üstKonumu += yüksekliği + boşluklarınınBoyutu;
		solKonumu = boşluklarınınBoyutu;
	}
	
	private void sesŞekliniOku(final String dizesi) {
		double genişliği =
			Integer.parseInt(dizesi.substring(1 + AYIRICI_SES.length()));
		okuduğuŞekil.seslerininŞekilleri
			.put(
				dizesi.charAt(0),
				new SesŞekli(
					genişliği,
					solKonumu,
					solKonumu += genişliği,
					üstKonumu,
					üstKonumu + yüksekliği,
					dokusununBoyutu));
		solKonumu += boşluklarınınBoyutu;
	}
}
