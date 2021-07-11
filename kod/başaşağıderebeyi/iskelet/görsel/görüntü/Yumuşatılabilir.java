/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.1.1 / 11 Tem 2021 / 07:03:39
 */
package başaşağıderebeyi.iskelet.görsel.görüntü;

/** İki farklı kare arasında aradeğeri hesaplanabilen nesne. */
public interface Yumuşatılabilir {
	/** Bu nesneyi baştaki ve sondaki nesnelerin verilen uzaklığa göre
	 * aradeğerlerine değiştirir. Verilen nesnelerin bu nesneden farklı olduğunu
	 * varsayar. Bu nesneyi döndürür. */
	public Yumuşatılabilir aradeğerleriniBul(
		Yumuşatılabilir baştaki,
		Yumuşatılabilir sondaki,
		double uzunluk);
	
	/** Bu nesneyi verilen nesneyle değiştirir. Bu nesneyi döndürür. */
	public Yumuşatılabilir değiştir(Yumuşatılabilir öbürü);
}
