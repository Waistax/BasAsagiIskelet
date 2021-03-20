/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.1 / 3 Mar 2021 / 08:35:27
 * 
 * BaşAşağıMotor'dan alındı.
 * 0.1 / 28 Ağu 2020 / 23:22:45
 */
package başaşağıderebeyi.iskelet;

import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.olaylar.*;
import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.matematik.ortalama.*;
import başaşağıderebeyi.kütüphane.matematik.sayısal.*;
import başaşağıderebeyi.kütüphane.olay.*;

import java.util.*;

import org.lwjgl.glfw.*;

/** Uygulamayı çalıştıran ve döngü içerisinde güncelleyen genel kodları sağlayan
 * iskelet. */
public class İskelet {
	/** Ana sürümü. */
	public static final int ANA_SÜRÜMÜ = 0;
	/** Ara sürümü. */
	public static final int ARA_SÜRÜMÜ = 10;
	/** Yaması. */
	public static final int YAMASI = 2;
	/** Bütün sürümü. */
	public static final String SÜRÜM =
		ANA_SÜRÜMÜ + "." + ARA_SÜRÜMÜ + "." + YAMASI;
	
	/** İskeletin kullanılacak nesnesi. Herhangi bir zamanda birden fazla
	 * İskelet olması akıl dışı. */
	public static final İskelet NESNESİ = new İskelet();
	
	/** İskeleti çalıştırır. */
	public static void main(final String[] argümanlar) {
		Gösterici
			.sağla(new Gösterici(1000, 800, SÜRÜM, false, 0, 1, new Yöney4()));
		NESNESİ.başla();
	}
	
	/** İskeletin ulaşmaya çalıştığı saniye başına tık oranı. */
	private final float istediğiTıkOranı = 10;
	
	private volatile boolean çalışması;
	
	private double tıklarınınOranı;
	private double karelerininOranı;
	private double güncellenmemişTıkları;
	private long anı;
	
	private Ortalama tıklarınınOranınınOrtalaması;
	private Ortalama karelerininOranınınOrtalaması;
	private Map<String, Süreç> süreçleri;
	
	private ÇiğGirdi çiğGirdisi;
	private OlaySağlayıcısı olaySağlayıcısı;
	
	/** İskeletin dışarıdan tanımlanmasını engeller. */
	private İskelet() {
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
	
	/** Şu anki zamanı GLFW saatinden saniye biriminde döndürür. Bu zamanın
	 * değeri tek başına anlamlı olmayabilir ama daha önceden edinilmiş başka
	 * bir zamanı bu zamandan çıkarak saniye biriminden geçen süre bulunur. */
	public double zamanıEdin() {
		return GLFW.glfwGetTime();
	}
	
	/** Şu anki zamanı JDK saatinden saniye biriminde döndürür. Bu zamanın
	 * değeri tek başına anlamlı olmayabilir ama daha önceden edinilmiş başka
	 * bir zamanı bu zamandan çıkarak saniye biriminden geçen süre bulunur. */
	public float sistemZamanınıEdin() {
		return System.nanoTime() / 1000000000.0F;
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
	public OlayDağıtıcısı güncellemeOlaylarınınDağıtıcısınıEdin() {
		return olaySağlayıcısı.güncellemeOlaylarınınDağıtıcısı;
	}
	
	/** İskeletin çizim olaylarının dağıtıcısını döndürür. Bu dağıtıcı kendi
	 * haline bırakılmalıdır. */
	public OlayDağıtıcısı çizimOlaylarınınDağıtıcısınıEdin() {
		return olaySağlayıcısı.çizimOlaylarınınDağıtıcısı;
	}
	
	/** İskeletin çiğ girdisini döndürür. */
	public ÇiğGirdi girdisiniEdin() {
		return çiğGirdisi;
	}
	
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
	
	public void güncellemeOlayınıDinle(final GüncellemeOlayı olay) {
		if (Gösterici.edin().penceresininKapatılmasınıEdin())
			dur();
		
		çiğGirdisi.güncelle();
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
					
					olaySağlayıcısı.sayaçOlayınıDağıt();
					
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
		oluşturmaSüreci.başla(sistemZamanınıEdin());
		
		tıklarınınOranınınOrtalaması = new Ortalama();
		karelerininOranınınOrtalaması = new Ortalama();
		
		süreçleriniOluştur(oluşturmaSüreci);
		
		çiğGirdisi = new ÇiğGirdi();
		olaySağlayıcısınıOluştur();
		
		UygulamaYükleyicisi.NESNESİ.yükle();
		
		Gösterici.edin().penceresiniOluştur();
		
		olaySağlayıcısı.oluşturmaOlayınıDağıt();
		
		oluşturmaSüreci.dur(sistemZamanınıEdin());
		System.out
			.println(
				"Oluşturma tamamlandı! Geçen süre: " +
					oluşturmaSüreci.toplamınıEdin());
	}
	
	private void olaySağlayıcısınıOluştur() {
		olaySağlayıcısı = new OlaySağlayıcısı();
		
		olaySağlayıcısı.güncellemeOlaylarınınDağıtıcısı
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(
					SayaçOlayı.class,
					this::sayaçOlayınıDinle));
		
		olaySağlayıcısı.güncellemeOlaylarınınDağıtıcısı
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(
					GüncellemeOlayı.class,
					this::güncellemeOlayınıDinle)
						.önceliğiniDeğiştir(Öncelik.ÖNCE));
	}
	
	private void süreçleriniOluştur(final Süreç oluşturmaSüreci) {
		süreçleri = new HashMap<>();
		süreçleri.put("Oluşturma", oluşturmaSüreci);
		süreçleri.put("Tık", new Süreç());
		süreçleri.put("Kare", new Süreç());
	}
	
	private void yokEt() {
		System.out.println("Yok ediliyor...");
		
		olaySağlayıcısı.yokEtmeOlayınıDağıt();
		
		Gösterici.edin().yokEt();
		Yükleyici.NESNESİ.yokEt();
		
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
		
		olaySağlayıcısı.güncellemeOlayınıDağıt();
		anı++;
		
		süreçleri.get("Tık").dur((float)zamanıEdin());
	}
	
	private void çiz() {
		süreçleri.get("Kare").başla((float)zamanıEdin());
		
		olaySağlayıcısı.çizimOlayınıDağıt();
		Gösterici.edin().göster();
		
		süreçleri.get("Kare").dur((float)zamanıEdin());
	}
}
