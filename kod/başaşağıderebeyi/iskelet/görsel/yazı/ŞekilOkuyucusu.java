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
	final double genişliği;
	final double yüksekliği;
	final double boşluklarınınBoyutu;
	
	double üstKonumu;
	double solKonumu;
	
	ŞekilOkuyucusu(final YazıŞekli okuduğuŞekil, final List<String> verisi) {
		this.okuduğuŞekil = okuduğuŞekil;
		
		final String[] bilgileri = verisi.get(0).split(AYIRICI_SES);
		dokusununBoyutu = Integer.parseInt(bilgileri[0]);
		genişliği = Integer.parseInt(bilgileri[1]);
		yüksekliği = Integer.parseInt(bilgileri[2]);
		boşluklarınınBoyutu = Integer.parseInt(bilgileri[4]);
		
		üstKonumu = boşluklarınınBoyutu;
		solKonumu = boşluklarınınBoyutu;
		
		for (int i = 1; i < verisi.size(); i++)
			dizesiniOku(verisi.get(i));
	}
	
	private void dizesiniOku(final String dizesi) {
		for (int i = 0; i < dizesi.length(); i++)
			sesŞekliniOku(dizesi.charAt(i));
		satırAtla();
	}
	
	private void sesŞekliniOku(final char ses) {
		okuduğuŞekil.seslerininŞekilleri
			.put(
				ses,
				new SesŞekli(
					solKonumu,
					solKonumu += genişliği,
					üstKonumu,
					üstKonumu + yüksekliği,
					dokusununBoyutu));
		solKonumu += boşluklarınınBoyutu;
	}
	
	private void satırAtla() {
		üstKonumu += yüksekliği + boşluklarınınBoyutu;
		solKonumu = boşluklarınınBoyutu;
	}
}
