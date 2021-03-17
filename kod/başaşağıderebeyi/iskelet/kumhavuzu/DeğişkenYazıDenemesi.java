/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 6 Mar 2021 / 11:52:46
 */
package başaşağıderebeyi.iskelet.kumhavuzu;

import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.yazı.*;
import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.matematik.sayısal.*;
import başaşağıderebeyi.kütüphane.olay.*;

import java.util.*;

/** Değişken yazı görselleştiricisini dener. */
public class DeğişkenYazıDenemesi implements Uygulama {
	public static void main(final String[] args) {
		new DeğişkenYazıDenemesi();
	}
	
	private final İskelet çalıştıranİskelet;
	
	private DeğişkenYazıGörselleştirici yazar;
	private float[] konumları;
	private float[] çizgileri;
	private float[] boyutları;
	
	private DeğişkenYazıDenemesi() {
		final Gösterici gösterici = new Gösterici(
			1920,
			1080,
			"Değişken Yazı Denemesi Sürüm: " + İskelet.SÜRÜM,
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
		
		konumları = new float[1000];
		çizgileri = new float[konumları.length];
		boyutları = new float[konumları.length];
		final Random rastgele = new Random(1L);
		
		yazar = new DeğişkenYazıGörselleştirici(
			çalıştıranİskelet.yükleyicisi,
			86 * konumları.length,
			new YazıŞekli(
				çalıştıranİskelet.yükleyicisi,
				"sabitGenişlikliBüyük"),
			10.0F,
			izdüşümDizeyi,
			0.90F);
		
		yazar.renginiEdin().değiştir(Yöney4.BİR);
		
		for (int i = 0; i < konumları.length; i++) {
			konumları[i] = (rastgele.nextFloat() - 0.5F) * 1000.0F;
			çizgileri[i] = (rastgele.nextFloat() - 0.5F) * 500.0F;
			boyutları[i] = rastgele.nextFloat() * 20.0F;
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
		for (int i = 0; i < konumları.length; i++) {
			yazar.boyutunuDeğiştir(boyutları[i]);
			yazar
				.yaz(
					konumları[i],
					çizgileri[i],
					"BufferUtils vs MemoryUtil",
					"Tampon hızları karşılaştırması!",
					"Daha fazla ses: 0123456789+-*/");
		}
		
		yazar.çiz();
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
