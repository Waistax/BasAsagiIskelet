/**
 * başaşağıderebeyi.iskelet.İskelet.java
 * 0.1 / 3 Mar 2021 / 08:35:27
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 
 * BaşAşağıMotor'dan alındı.
 * 0.1 / 28 Ağu 2020 / 23:22:45
 */
package başaşağıderebeyi.iskelet;

import başaşağıderebeyi.kütüphane.olay.*;

import java.util.*;

import org.lwjgl.glfw.*;

/** Uygulamayı çalıştıran ve döngü içerisinde güncelleyen genel kodları sağlayan
 * iskelet. */
public class İskelet {
	/** İskeletin sürümü. */
	public static final String SÜRÜM = "0.1";
	
	private static final SayaçOlayı SAYAÇ_OLAYI = new SayaçOlayı();
	
	/** İskeletin ulaşmaya çalıştığı saniye başına tık oranı. */
	public final float istediğiTıkOranı;
	/** İskeletin çalıştırdığı uygulama. */
	public final Uygulama uygulaması;
	
	private volatile boolean çalışması;
	
	private double tıklarınınOranı;
	private double karelerininOranı;
	private double güncellenmemişTıkları;
	
	private AnlıOlayDağıtıcısı olayDağıtıcısı;
	private Map<String, Süreç> süreçleri;
	
	/** Verilenler ile tanımlar. */
	public İskelet(final float istediğiTıkOranı, Uygulama uygulaması) {
		this.istediğiTıkOranı = istediğiTıkOranı;
		this.uygulaması = uygulaması;
	}
	
	/** İstekeleti başlatır. */
	public void başla() {
		assert !çalışması;
		çalışması = true;
		çalış();
	}
	
	/** İskeleti durdurur. */
	public void dur() {
		assert çalışması;
		çalışması = false;
	}
	
	/** Şu anki zamanı saniye biriminde döndürür. Bu zamanın değeri tek başına
	 * anlamlı olmayabilir ama daha önceden edinilmiş başka bir zamanı bu
	 * zamandan çıkarak saniye biriminden geçen süre bulunur. */
	public double zamanıEdin() {
		return GLFW.glfwGetTime();
	}
	
	/** Saniye başına tık oranını döndürür. Tık oranı güncelleme sayısıdır. */
	public double tıklarınınOranınıEdin() {
		return tıklarınınOranı;
	}
	
	/** Saniye başına kare oranını döndürür. Kare oranı çizme sayısıdır. */
	public double karelerininOranınıEdin() {
		return karelerininOranı;
	}
	
	/** Anlık olarak güncellenmeyi bekleyen tıkların sayısını döndürür. Bu sayı
	 * çizim yaparken aradeğerleri hesaplamada kullanılmalıdır. Ayrıca bu sayı
	 * birden büyükse iskelet istenen hızda çalışamıyor demektir. */
	public double güncellenmemişTıklarınıEdin() {
		return güncellenmemişTıkları;
	}
	
	/** İskeletin olay dağıtıcısını döndürür. Bu anlık olarak çalışır;
	 * dağıtacağı olayları biriktirip hepsini güncellemeden önce dağıtır. */
	public OlayDağıtıcısı olayDağıtıcısınıEdin() {
		return olayDağıtıcısı;
	}
	
	@Dinleyici
	public void sayaçOlayınıDinle(SayaçOlayı olay) {
		süreçleri.forEach((ad, süreç) -> {
			süreç.güncelle();
			System.out.println(ad + " Süreci: " + süreç.ortalamasınıEdin());
		});
	}
	
	private void çalış() {
		try {
			oluştur();
			
			double öncekiZamanı = zamanıEdin();
			double saniyelikGüncellemeSayacı = 0.0;
			int tıklarınınSayısı = 0;
			int karelerininSayısı = 0;
			
			while (çalışması) {
				double geçenSüre = zamanıEdin() - öncekiZamanı;
				öncekiZamanı += geçenSüre;
				güncellenmemişTıkları += geçenSüre * istediğiTıkOranı;
				
				while (güncellenmemişTıkları >= 1.0) {
					güncelle();
					güncellenmemişTıkları--;
					tıklarınınSayısı++;
				}
				
				çiz();
				karelerininSayısı++;
				
				if ((saniyelikGüncellemeSayacı += geçenSüre) >= 1.0) {
					tıklarınınOranı =
						tıklarınınSayısı / saniyelikGüncellemeSayacı;
					karelerininOranı =
						karelerininSayısı / saniyelikGüncellemeSayacı;
					
					olayDağıtıcısı.dağıt(SAYAÇ_OLAYI);
					
					saniyelikGüncellemeSayacı = 0.0;
					tıklarınınSayısı = 0;
					karelerininSayısı = 0;
				}
			}
		} catch (final Exception hata) {
			hata.printStackTrace();
		} finally {
			yokEt();
		}
	}
	
	private void oluştur() {
		Süreç oluşturmaSüreci = new Süreç(this);
		oluşturmaSüreci.başla();
		
		olayDağıtıcısı = new AnlıOlayDağıtıcısı();
		olayDağıtıcısı.dinleyicileriniEkle(this);
		
		süreçleri = new HashMap<>();
		süreçleri.put("Tık", new Süreç(this));
		süreçleri.put("Kare", new Süreç(this));
		
		uygulaması.oluştur();
		
		oluşturmaSüreci.dur();
		oluşturmaSüreci.güncelle();
		System.out
			.println(
				"Oluşturma tamamlandı! Geçen süre: " +
					oluşturmaSüreci.toplamınıEdin());
	}
	
	private void yokEt() {
		uygulaması.yokEt();
	}
	
	private void güncelle() {
		süreçleri.get("Tık").başla();
		
		olayDağıtıcısı.güncelle();
		uygulaması.güncelle();
		
		süreçleri.get("Tık").dur();
	}
	
	private void çiz() {
		süreçleri.get("Kare").başla();
		
		uygulaması.çiz();
		
		süreçleri.get("Kare").dur();
	}
}
