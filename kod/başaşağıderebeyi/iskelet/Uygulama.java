/**
 * başaşağıderebeyi.iskelet.Uygulama.java
 * 0.1 / 3 Mar 2021 / 09:06:21
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 
 * BaşAşağıMotor'dan alındı.
 * 0.2 / 28 Ağu 2020 / 23:57:20
 */
package başaşağıderebeyi.iskelet;

/** İskeletin çalıştırabildiği bir uygulama. */
public interface Uygulama {
	/** Uygulamanın gerekli araçlarını oluşturur. */
	void oluştur();
	
	/** Uygulamanın kapatılması gereken araçlarını yok eder. */
	void yokEt();
	
	/** Uygulamanın anlık durumunu günceller. */
	void güncelle();
	
	/** Uygulamayı çizer. */
	void çiz();
}
