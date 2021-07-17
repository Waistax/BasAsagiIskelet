/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.10.6 / 16 Tem 2021 / 18:15:58
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import static java.lang.Math.*;

import java.util.*;

/** Bir ya da daha fazla satırın birleşmesinden oluşan dize. */
public class Paragraf {
	/** Paragrafın satırlarını ve her satırdaki sözcüklerin iki boyutlu
	 * listesi. */
	public final List<List<String>> satırları;
	/** Paragrafın her satırının kelimeler arası boşlukları. */
	public final List<Double> boşlukları;
	/** İlk satırın ilk kelimesinin kaç ses sağdan yazılacağı. */
	public final int girintisi;
	
	/** Verilen dizeyi verilen ses genişliğine sığdırılmış bir şekilde tanımlar.
	 * Dayalı olacak şekilde ya da girinti ile de tanımlanabilir. */
	public Paragraf(
		final String dizesi,
		final double genişliği,
		final boolean dayalıOlması,
		final int girintisi) {
		satırları = new ArrayList<>();
		boşlukları = new ArrayList<>();
		this.girintisi = girintisi;
		
		satırlarınıBul(dizesi, (int)floor(genişliği), girintisi);
		
		if (dayalıOlması)
			boşluklarınıBul(genişliği);
		else
			for (int i = 0; i < satırları.size(); i++)
				boşlukları.add(1.0);
	}
	
	private void satırlarınıBul(
		final String dizesi,
		final int genişliği,
		final int girintisi) {
		List<String> satırı = satırEkle();
		int uzunluğu = girintisi - 1;
		
		for (final String sözcüğü : dizesi.split(" ")) {
			int yeniUzunluğu = uzunluğu + 1 + sözcüğü.length();
			if (yeniUzunluğu > genişliği) {
				satırı = satırEkle();
				yeniUzunluğu = sözcüğü.length();
			}
			satırı.add(sözcüğü);
			uzunluğu = yeniUzunluğu;
		}
	}
	
	private List<String> satırEkle() {
		final List<String> satırı = new ArrayList<>();
		satırları.add(satırı);
		return satırı;
	}
	
	private void boşluklarınıBul(final double genişliği) {
		for (int i = 0; i < satırları.size() - 1; i++) {
			final List<String> satırı = satırları.get(i);
			int uzunluğu = 0;
			for (final String sözcüğü : satırı)
				uzunluğu += sözcüğü.length();
			boşlukları.add((genişliği - uzunluğu) / (satırı.size() - 1));
		}
		boşlukları.add(1.0);
	}
}
