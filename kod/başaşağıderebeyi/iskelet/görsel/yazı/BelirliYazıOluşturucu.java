/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.7 / 10 Mar 2021 / 20:21:56
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import java.util.*;

/** Verilen satırları olduğu gibi durağan yazıya dönüştüren araç. */
public class BelirliYazıOluşturucu extends DurağanYazıOluşturucu {
	/** Verilenlerle oluşturucuyu tanımlar. */
	public BelirliYazıOluşturucu(
		final YazıŞekli şekli,
		final double açısı,
		final String... satırlar) {
		super(şekli, açısı, satırlar);
	}
	
	@Override
	protected void satırlarınıBul(final String[] satırlar) {
		Arrays.stream(satırlar).forEachOrdered(satırları::add);
	}
	
	@Override
	protected double ilkSatırınDikeyKonumunuBul() {
		return 0.0;
	}
	
	@Override
	protected double satırınınYatayKonumu(final int satırınSırası) {
		return 0.0;
	}
}
