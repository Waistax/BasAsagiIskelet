/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.10.13 / 17 Tem 2021 / 11:03:07
 */
package başaşağıderebeyi.iskelet.görsel.yazı.paragraf;

import java.util.*;

/** Paragrafın bir satırının bilgilerini içeren nesne. */
public class ParagrafSatırı {
	/** Sırasıyla satırı oluşturan sözcükler. "Sözcük" içinde boşluk olmayan her
	 * sıralı ses topluluğuna denir. */
	public final List<String> sözcükleri;
	
	private double girintisininUzunluğu;
	private double boşluğununUzunluğu;
	
	ParagrafSatırı(final List<String> sözcükleri) {
		this.sözcükleri = new ArrayList<>(sözcükleri);
	}
	
	/** Satırdaki ilk sözcüğün ne kadar içeriden yazılacağını belirten uzunluğu
	 * döndürür. Genelde ilk paragraf girintisizken takip eden paragraflar
	 * girinti ile yazılır. Bu uzunluk ses genişliği birimindendir. */
	public double girintisininUzunluğunuEdin() {
		return girintisininUzunluğu;
	}
	
	/** Sözcüklerin arasına koyulacak boşluğun uzunluğu döndürür. Eğer paragraf
	 * dayalı değilse bir ses kadardır. Eğer paragraf dayalıysa, satırdaki son
	 * sözcüğün son harfinin sağ kenarı belirli bir konuma gelecek şekilde
	 * hesaplanır. Bu uzunluk ses genişliği birimindendir. */
	public double boşluğununUzunluğunuEdin() {
		return boşluğununUzunluğu;
	}
	
	void uzunluklarınıBul(
		final double girintisininUzunluğu,
		final double paragrafınGenişliği,
		final boolean dayalıOlması) {
		girintisininUzunluğunuDeğiştir(girintisininUzunluğu);
		boşluğununUzunluğunuBul(paragrafınGenişliği, dayalıOlması);
	}
	
	private void girintisininUzunluğunuDeğiştir(
		final double girintisininUzunluğu) {
		this.girintisininUzunluğu = girintisininUzunluğu;
	}
	
	private void boşluğununUzunluğunuBul(
		final double paragrafınGenişliği,
		final boolean dayalıOlması) {
		if (!dayalıOlması) {
			boşluğununUzunluğu = 1.0;
			return;
		}
		double toplamSözcükUzunluğu = girintisininUzunluğu;
		for (final String sözcüğü : sözcükleri)
			toplamSözcükUzunluğu += sözcüğü.length();
		boşluğununUzunluğu = (paragrafınGenişliği - toplamSözcükUzunluğu) /
			(sözcükleri.size() - 1);
	}
}
