/**
 * başaşağıderebeyi.iskelet.kumhavuzu.DeğişkenYazıDenemesi.java
 * 0.6 / 6 Mar 2021 / 11:52:46
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.kumhavuzu;

import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.görsel.*;
import başaşağıderebeyi.iskelet.görsel.yazı.*;
import başaşağıderebeyi.kütüphane.girdi.*;
import başaşağıderebeyi.kütüphane.matematik.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** Değişken yazı görselleştiricisini dener. */
public class DeğişkenYazıDenemesi implements Uygulama {
	public static void main(final String[] args) {
		new DeğişkenYazıDenemesi();
	}
	
	private final İskelet çalıştıranİskelet;
	
	private DeğişkenYazıGörselleştirici büyükYazar;
	private DeğişkenYazıGörselleştirici küçükYazar;
	
	DeğişkenYazıDenemesi() {
		final Gösterici gösterici = new Gösterici(
			1920,
			1080,
			"Baş Aşağı Derebeyi " + İskelet.SÜRÜM,
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
		
		büyükYazar = new DeğişkenYazıGörselleştirici(
			çalıştıranİskelet.yükleyicisi,
			640.0F,
			360.0F,
			20.0F,
			1000,
			new YazıŞekli(
				çalıştıranİskelet.yükleyicisi,
				"sabitGenişlikliBüyük"),
			30.0F,
			0.90F);
		
		küçükYazar = new DeğişkenYazıGörselleştirici(
			çalıştıranİskelet.yükleyicisi,
			640.0F,
			360.0F,
			20.0F,
			1000,
			new YazıŞekli(
				çalıştıranİskelet.yükleyicisi,
				"sabitGenişlikliKüçük"),
			30.0F,
			0.5F);
		
		büyükYazar.boyutunuDeğiştir(35.0F);
		büyükYazar.renginiEdin().değiştir(Yöney4.BİR);
		
		küçükYazar.boyutunuDeğiştir(35.0F);
		küçükYazar.renginiEdin().değiştir(Yöney4.BİR);
		
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
		büyükYazar
			.yaz(
				-300.0F,
				100.0F,
				"BufferUtils vs MemoryUtil",
				"Tampon hızları karşılaştırması!",
				"Daha fazla ses: 0123456789+-*/");
		büyükYazar.çiz();
		
		küçükYazar
			.yaz(
				-300.0F,
				-50.0F,
				"BufferUtils vs MemoryUtil",
				"Tampon hızları karşılaştırması!",
				"Daha fazla ses: 0123456789+-*/");
		küçükYazar.çiz();
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
