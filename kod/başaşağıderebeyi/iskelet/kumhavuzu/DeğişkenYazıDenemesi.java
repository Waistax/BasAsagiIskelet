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
	
	private DeğişkenYazıGörselleştirici görselleştirici;
	
	DeğişkenYazıDenemesi() {
		final Gösterici gösterici = new Gösterici(
			640,
			360,
			"Baş Aşağı Derebeyi " + İskelet.SÜRÜM,
			false,
			16,
			1,
			new Yöney4());
		çalıştıranİskelet =
			new İskelet(10.0F, this, gösterici, new Yükleyici());
		çalıştıranİskelet.başla();
	}
	
	@Override
	public void oluştur() {
		çalıştıranİskelet.olayDağıtıcısınıEdin().dinleyicileriniEkle(this);
		
		görselleştirici = new DeğişkenYazıGörselleştirici(
			çalıştıranİskelet.yükleyicisi,
			640.0F,
			360.0F,
			20.0F,
			1000,
			new YazıŞekli(çalıştıranİskelet.yükleyicisi, "kalınEğikVerdana"));
		
		görselleştirici.boyutunuDeğiştir(12.0F);
		görselleştirici.renginiEdin().değiştir(Yöney4.BİR);
		
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
		görselleştirici
			.yaz(
				-300.0F,
				0.0F,
				"BAŞ AŞAĞI DEREBEYİ VURDU",
				"KRAL ÖLDÜ BÜYÜK KURT");
		görselleştirici.çiz();
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
