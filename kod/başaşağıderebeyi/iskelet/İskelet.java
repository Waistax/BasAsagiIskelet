/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.1 / 3 Mar 2021 / 08:35:27
 * 
 * BaşAşağıMotor'dan alındı.
 * 0.1 / 28 Ağu 2020 / 23:22:45
 */
package başaşağıderebeyi.iskelet;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.matematik.ortalama.*;
import başaşağıderebeyi.kütüphane.olay.*;

import java.util.*;

import org.lwjgl.glfw.*;

/** Uygulamayı çalıştıran ve döngü içerisinde güncelleyen genel kodları sağlayan
 * iskelet. */
public class İskelet {
	/** Ana sürümü. */
	public static final int ANA_SÜRÜMÜ = 0;
	/** Ara sürümü. */
	public static final int ARA_SÜRÜMÜ = 9;
	/** Yaması. */
	public static final int YAMASI = 2;
	/** Bütün sürümü. */
	public static final String SÜRÜM =
		ANA_SÜRÜMÜ + "." + ARA_SÜRÜMÜ + "." + YAMASI;
	
	private static final SayaçOlayı SAYAÇ_OLAYI = new SayaçOlayı();
	
	/** İskeletin ulaşmaya çalıştığı saniye başına tık oranı. */
	public final float istediğiTıkOranı;
	/** İskeletin çalıştırdığı uygulama. */
	public final Uygulama uygulaması;
	/** İskeletin çizimleri göstermek kullandığı araç. */
	public final Gösterici göstericisi;
	/** İskeletin görselleri ve şekilleri yüklemek için kullandığı araç. */
	public final Yükleyici yükleyicisi;
	
	private volatile boolean çalışması;
	
	private double tıklarınınOranı;
	private double karelerininOranı;
	private Ortalama tıklarınınOranınınOrtalaması;
	private Ortalama karelerininOranınınOrtalaması;
	private double güncellenmemişTıkları;
	private long anı;
	
	private AnlıOlayDağıtıcısı olayDağıtıcısı;
	private Map<String, Süreç> süreçleri;
	private ÇiğGirdi çiğGirdisi;
	
	/** Verilenler ile tanımlar. */
	public İskelet(
		final float istediğiTıkOranı,
		final Uygulama uygulaması,
		final Gösterici göstericisi,
		final Yükleyici yükleyicisi) {
		this.istediğiTıkOranı = istediğiTıkOranı;
		this.uygulaması = uygulaması;
		this.göstericisi = göstericisi;
		this.yükleyicisi = yükleyicisi;
	}
	
	/** İstekeleti başlatır. */
	public void başla() {
		assert !çalışması;
		çalışması = true;
		çalış();
	}
	
	/** İskeleti durdurur. */
	public void dur() {
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
	
	/** İskeletin çalıştığı her saniyedeki tık oranının ortalamasını
	 * döndürür. */
	public float tıklarınınOranınınOrtalamasınıEdin() {
		return tıklarınınOranınınOrtalaması.ortalamasınıEdin();
	}
	
	/** İskeletin çalıştığı her saniyedeki kare oranının ortalamasını
	 * döndürür. */
	public float karelerininOranınınOrtalamasınıEdin() {
		return karelerininOranınınOrtalaması.ortalamasınıEdin();
	}
	
	/** Anlık olarak güncellenmeyi bekleyen tıkların sayısını döndürür. Bu sayı
	 * çizim yaparken aradeğerleri hesaplamada kullanılmalıdır. Ayrıca bu sayı
	 * birden büyükse iskelet istenen hızda çalışamıyor demektir. */
	public double güncellenmemişTıklarınıEdin() {
		return güncellenmemişTıkları;
	}
	
	/** Şu anın sırasını döndürür. */
	public long anınıEdin() {
		return anı;
	}
	
	/** İskeletin olay dağıtıcısını döndürür. Bu anlık olarak çalışır;
	 * dağıtacağı olayları biriktirip hepsini güncellemeden önce dağıtır. */
	public OlayDağıtıcısı olayDağıtıcısınıEdin() {
		return olayDağıtıcısı;
	}
	
	/** İskeletin çiğ girdisini döndürür. */
	public ÇiğGirdi girdisiniEdin() {
		return çiğGirdisi;
	}
	
	@Dinleyici
	public void sayaçOlayınıDinle(final SayaçOlayı olay) {
		tıklarınınOranınınOrtalaması.ekle((float)tıklarınınOranı);
		karelerininOranınınOrtalaması.ekle((float)karelerininOranı);
		
		süreçleri.forEach((ad, süreç) -> {
			if (ad != "Oluşturma") {
				System.out.println(ad + " Süreci: " + süreç.ortalamasınıEdin());
				süreç.sıfırla();
			}
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
				final double geçenSüre = zamanıEdin() - öncekiZamanı;
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
		final Süreç oluşturmaSüreci = new Süreç();
		oluşturmaSüreci.başla(System.nanoTime() / 1000000000.0F);
		
		tıklarınınOranınınOrtalaması = new Ortalama();
		karelerininOranınınOrtalaması = new Ortalama();
		
		olayDağıtıcısı = new AnlıOlayDağıtıcısı();
		olayDağıtıcısı.dinleyicileriniEkle(this);
		
		süreçleri = new HashMap<>();
		süreçleri.put("Oluşturma", oluşturmaSüreci);
		süreçleri.put("Tık", new Süreç());
		süreçleri.put("Kare", new Süreç());
		
		çiğGirdisi = new ÇiğGirdi();
		
		göstericisi.penceresiniOluştur(this);
		uygulaması.oluştur();
		
		oluşturmaSüreci.dur(System.nanoTime() / 1000000000.0F);
		System.out
			.println(
				"Oluşturma tamamlandı! Geçen süre: " +
					oluşturmaSüreci.toplamınıEdin());
	}
	
	private void yokEt() {
		System.out.println("Yok ediliyor...");
		
		uygulaması.yokEt();
		göstericisi.yokEt();
		yükleyicisi.yokEt();
		
		System.out
			.println(
				"Ortalama Tık Oranı: " + tıklarınınOranınınOrtalamasınıEdin());
		System.out
			.println(
				"Ortalama Kare Oranı: " +
					karelerininOranınınOrtalamasınıEdin());
	}
	
	private void güncelle() {
		süreçleri.get("Tık").başla((float)zamanıEdin());
		
		if (göstericisi.penceresininKapatılmasınıEdin())
			dur();
		
		olayDağıtıcısı.güncelle();
		çiğGirdisi.güncelle();
		uygulaması.güncelle();
		
		anı++;
		
		süreçleri.get("Tık").dur((float)zamanıEdin());
	}
	
	private void çiz() {
		süreçleri.get("Kare").başla((float)zamanıEdin());
		
		uygulaması.çiz();
		göstericisi.göster();
		
		süreçleri.get("Kare").dur((float)zamanıEdin());
	}
}
