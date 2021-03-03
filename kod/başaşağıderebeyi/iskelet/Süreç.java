/**
 * başaşağıderebeyi.iskelet.Süreç.java
 * 0.1 / 19 Oca 2021 / 10:44:55
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 
 * BaşAşağıMotor'dan alındı.
 * 0.7 / 29 Ağu 2020 / 19:23:14
 */
package başaşağıderebeyi.iskelet;

/** Bir işlemin bir saniyede her tık başına aldığı ortalama süreyi bulmaya
 * yarayan araç. */
public class Süreç {
	private final İskelet çalıştıranİskelet;
	
	private double enSonBaşlangıcı;
	private double sürmelerininToplamı;
	private int sürmeSayısı;
	
	private double ortalaması;
	private double toplamı;
	
	/** Verilen iskeletin zamanıyla tanımlar. */
	public Süreç(İskelet çalıştıranİskelet) {
		this.çalıştıranİskelet = çalıştıranİskelet;
	}
	
	/** Süreci başlatır. */
	public void başla() {
		enSonBaşlangıcı = çalıştıranİskelet.zamanıEdin();
	}
	
	/** Süreci durdurur. */
	public void dur() {
		sürmelerininToplamı += çalıştıranİskelet.zamanıEdin() - enSonBaşlangıcı;
		sürmeSayısı++;
	}
	
	/** Ortalama ve toplam süreyi bulur ve sayaçları sıfırlar. */
	public void güncelle() {
		ortalaması = sürmelerininToplamı / sürmeSayısı;
		toplamı = sürmelerininToplamı;
		sürmelerininToplamı = 0.0;
		sürmeSayısı = 0;
	}
	
	/** Saniye biriminden ortalama süreyi döndürür. */
	public double ortalamasınıEdin() {
		return ortalaması;
	}
	
	/** Saniye biriminden sürelerinin toplamını döndürür. */
	public double toplamınıEdin() {
		return toplamı;
	}
}
