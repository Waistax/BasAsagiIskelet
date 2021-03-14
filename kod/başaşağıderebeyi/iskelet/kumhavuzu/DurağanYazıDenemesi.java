/**
 * başaşağıderebeyi.iskelet.kumhavuzu.DurağanYazıDenemesi.java
 * 0.7 / 11 Mar 2021 / 18:36:01
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.kumhavuzu;

import static başaşağıderebeyi.kütüphane.matematik.MatematikAracı.*;
import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.yazı.*;
import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.matematik.*;
import başaşağıderebeyi.kütüphane.olay.*;

import java.util.*;

/** Durağan yazı görselleştiricisini dener. */
public class DurağanYazıDenemesi implements Uygulama {
	public static void main(final String[] args) {
		new DurağanYazıDenemesi();
	}
	
	private final İskelet çalıştıranİskelet;
	
	private List<DurağanYazıGörselleştirici> yazarlar;
	
	private DurağanYazıDenemesi() {
		final Gösterici gösterici = new Gösterici(
			1920,
			1080,
			"Durağan Yazı Denemesi Sürüm: " + İskelet.SÜRÜM,
			true,
			16,
			0,
			new Yöney4());
		çalıştıranİskelet =
			new İskelet(10.0F, this, gösterici, new Yükleyici());
		çalıştıranİskelet.başla();
	}
	
	@Override
	public void oluştur() {
		çalıştıranİskelet.olayDağıtıcısınıEdin().dinleyicileriniEkle(this);
		final Dizey4 izdüşümDizeyi =
			new Dizey4().izdüşümDizeyineÇevir(1280.0F, 720.0F, 20.0F);
		
		yazarlar = new ArrayList<>(1000);
		final Random rastgele = new Random(1L);
		
		for (int i = 0; i < 1000; i++) {
			final DurağanYazıGörselleştirici yazar =
				new DurağanYazıGörselleştirici(
					çalıştıranİskelet.yükleyicisi,
					izdüşümDizeyi,
					0.90F,
					new BelirliYazıOluşturucu(
						new YazıŞekli(
							çalıştıranİskelet.yükleyicisi,
							"sabitGenişlikliBüyük"),
						10.0F,
						"BufferUtils vs MemoryUtil",
						"Tampon hızları karşılaştırması!",
						"Daha fazla ses: 0123456789+-*/"));
			
			yazar
				.konumunuDeğiştir(
					yuvarla((rastgele.nextFloat() - 0.5F) * 1000.0F),
					yuvarla((rastgele.nextFloat() - 0.5F) * 500.0F));
			yazar.boyutunuDeğiştir(yuvarla(rastgele.nextFloat() * 20.0F));
			yazar.dönüşümünüGüncelle();
			yazar.renginiEdin().değiştir(Yöney4.BİR);
			yazar.renginiGüncelle();
			
			yazarlar.add(yazar);
		}
		
		çalıştıranİskelet.göstericisi
			.imleciDeğiştir(
				çalıştıranİskelet.göstericisi
					.imleçOluştur(
						çalıştıranİskelet.yükleyicisi.glfwResmiYükle("imleç"),
						0,
						0));
	}
	
	@Override
	public void yokEt() {
		
	}
	
	@Override
	public void güncelle() {
		final ÇiğGirdi girdi = çalıştıranİskelet.girdisiniEdin();
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_ESCAPE).salınmasınıEdin())
			çalıştıranİskelet.dur();
	}
	
	@Override
	public void çiz() {
		yazarlar.forEach(DurağanYazıGörselleştirici::çiz);
	}
	
	@Dinleyici
	public void sayaçOlayınıDinle(final SayaçOlayı olay) {
		System.out
			.println(
				"Tık Oranı: " +
					çalıştıranİskelet.tıklarınınOranınıEdin() +
					" Kare Oranı: " +
					çalıştıranİskelet.karelerininOranınıEdin());
	}
}
