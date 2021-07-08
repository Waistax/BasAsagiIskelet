/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.3 / 20 Mar 2021 / 11:31:21
 */
package başaşağıderebeyi.iskelet;

import java.util.function.*;

class AnaDöngü {
	double istenenTıkHızı;
	
	private volatile boolean çalışması;
	private int tıkHızı;
	private int kareHızı;
	private double güncellenmemişTıkSayısı;
	private long anı;
	
	private final Runnable oluşturucu;
	private final DoubleSupplier zamanEdinici;
	private final Runnable güncelleyici;
	private final Runnable sayacı;
	private final Runnable çizici;
	private final Runnable yokEdici;
	
	AnaDöngü(
		final Runnable oluşturucu,
		final DoubleSupplier zamanEdinici,
		final Runnable güncelleyici,
		final Runnable sayacı,
		final Runnable çizici,
		final Runnable yokEdici) {
		anı = -1L;
		this.oluşturucu = oluşturucu;
		this.zamanEdinici = zamanEdinici;
		this.güncelleyici = güncelleyici;
		this.sayacı = sayacı;
		this.çizici = çizici;
		this.yokEdici = yokEdici;
	}
	
	void başla() {
		assert !çalışması;
		çalışması = true;
		çalış();
	}
	
	void dur() {
		çalışması = false;
	}
	
	void çalış() {
		try {
			oluşturucu.run();
			
			double öncekiZamanı = zamanEdinici.getAsDouble();
			double saniyeSayacı = 0.0;
			int tıkSayısı = 0;
			int kareSayısı = 0;
			
			while (çalışması) {
				final double geçenSüre =
					zamanEdinici.getAsDouble() - öncekiZamanı;
				öncekiZamanı += geçenSüre;
				güncellenmemişTıkSayısı += geçenSüre * istenenTıkHızı;
				
				while (güncellenmemişTıkSayısı >= 1.0) {
					anı++;
					güncelleyici.run();
					güncellenmemişTıkSayısı--;
					tıkSayısı++;
				}
				
				çizici.run();
				kareSayısı++;
				
				if ((saniyeSayacı += geçenSüre) >= 1.0) {
					tıkHızı = tıkSayısı;
					kareHızı = kareSayısı;
					sayacı.run();
					
					saniyeSayacı--;
					tıkSayısı = 0;
					kareSayısı = 0;
				}
			}
		} catch (final Exception hata) {
			hata.printStackTrace();
		} finally {
			yokEdici.run();
		}
	}
	
	int tıkHızınıEdin() {
		return tıkHızı;
	}
	
	int kareHızınıEdin() {
		return kareHızı;
	}
	
	double güncellenmemişTıkSayısınıEdin() {
		return güncellenmemişTıkSayısı;
	}
	
	long anınıEdin() {
		return anı;
	}
}
