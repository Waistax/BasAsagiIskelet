/**
 * başaşağıderebeyi.iskelet.görsel.yazı.BelirliYazıOluşturucu.java
 * 0.7 / 10 Mar 2021 / 20:21:56
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import java.util.*;

/** Verilen satırları olduğu gibi durağan yazıya dönüştüren araç. */
public class BelirliYazıOluşturucu extends DurağanYazıOluşturucu {
	/** Verilenlerle oluşturucuyu tanımlar. */
	public BelirliYazıOluşturucu(
		final YazıŞekli şekli,
		final float açısı,
		final String... satırlar) {
		super(şekli, açısı, satırlar);
	}
	
	@Override
	protected void satırlarınıBul(final String[] satırlar) {
		Arrays.stream(satırlar).forEachOrdered(satırları::add);
	}
	
	@Override
	protected float satırÇizgisininBaşlangıcınıBul() {
		return 0.0F;
	}
	
	@Override
	protected float satırınınKonumunuBul(final int satırınSırası) {
		return 0.0F;
	}
}
