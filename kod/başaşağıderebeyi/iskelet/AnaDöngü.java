/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.3 / 20 Mar 2021 / 11:31:21
 */
package başaşağıderebeyi.iskelet;

import java.util.function.*;

class AnaDöngü {
	double istenenTıkOranı;
	
	private volatile boolean çalışması;
	private int tıklarınınOranı;
	private int karelerininOranı;
	private double güncellenmemişTıkları;
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
			int tıklarınınSayısı = 0;
			int karelerininSayısı = 0;
			
			while (çalışması) {
				final double geçenSüre =
					zamanEdinici.getAsDouble() - öncekiZamanı;
				öncekiZamanı += geçenSüre;
				güncellenmemişTıkları += geçenSüre * istenenTıkOranı;
				
				while (güncellenmemişTıkları >= 1.0) {
					anı++;
					güncelleyici.run();
					güncellenmemişTıkları--;
					tıklarınınSayısı++;
				}
				
				çizici.run();
				karelerininSayısı++;
				
				if ((saniyeSayacı += geçenSüre) >= 1.0) {
					tıklarınınOranı = tıklarınınSayısı;
					karelerininOranı = karelerininSayısı;
					sayacı.run();
					
					saniyeSayacı--;
					tıklarınınSayısı = 0;
					karelerininSayısı = 0;
				}
			}
		} catch (final Exception hata) {
			hata.printStackTrace();
		} finally {
			yokEdici.run();
		}
	}
	
	int tıklarınınOranınıEdin() {
		return tıklarınınOranı;
	}
	
	int karelerininOranınıEdin() {
		return karelerininOranı;
	}
	
	double güncellenmemişTıklarınıEdin() {
		return güncellenmemişTıkları;
	}
	
	long anınıEdin() {
		return anı;
	}
}
