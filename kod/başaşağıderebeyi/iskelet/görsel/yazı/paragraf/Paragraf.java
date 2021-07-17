/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.10.6 / 16 Tem 2021 / 18:15:58
 */
package başaşağıderebeyi.iskelet.görsel.yazı.paragraf;

import java.util.*;

/** Bir ya da daha fazla satırın birleşmesinden oluşan dize. */
public class Paragraf {
	/** Paragrafın satırlarını ve her satırdaki sözcüklerin iki boyutlu
	 * listesi. */
	public final List<ParagrafSatırı> satırları;
	
	/** Verilen dizeyi verilen ses genişliğine sığdırılmış bir şekilde tanımlar.
	 * Dayalı olacak şekilde ya da girinti ile de tanımlanabilir. */
	public Paragraf(
		final double genişliği,
		final double girintisi,
		final boolean ilkParagrafınGirintiliOlması,
		final boolean dayalıOlması,
		final String... dizeleri) {
		satırları = new ArrayList<>();
		
		// Satırları oluşturucuyu kullanarak doldur.
		new ParagrafOluşturucu(
			satırları,
			genişliği,
			girintisi,
			ilkParagrafınGirintiliOlması,
			dayalıOlması,
			dizeleri);
	}
}
