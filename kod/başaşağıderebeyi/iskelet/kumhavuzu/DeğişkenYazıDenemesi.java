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
	private Dönüşüm dönüşüm;
	private Yöney4 renk;
	
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
			1280.0F,
			720.0F,
			20.0F,
			10,
			new YazıŞekli(
				çalıştıranİskelet.yükleyicisi,
				"sabitGenişlikliBüyük"));
		
		çalıştıranİskelet.göstericisi
			.imleciDeğiştir(
				çalıştıranİskelet.göstericisi
					.imleçOluştur(
						çalıştıranİskelet.yükleyicisi.glfwResmiYükle("imleç"),
						0,
						0));
		
		dönüşüm = new Dönüşüm();
		renk = new Yöney4(Yöney4.BİR);
		
		dönüşüm.biçimi.bileşenleriniDeğiştir(80.0F, 130.0F, 0.0F);
	}
	
	@Override
	public void yokEt() {
		
	}
	
	@Override
	public void güncelle() {
		final ÇiğGirdi girdi = çalıştıranİskelet.girdisiniEdin();
		if (girdi.klavyesininTuşunuEdin(GLFW_KEY_ESCAPE).salınmasınıEdin())
			çalıştıranİskelet.dur();
		renk
			.bileşenleriniDeğiştir(
				(float)Math.random(),
				(float)Math.random(),
				(float)Math.random(),
				1.0F);
	}
	
	@Override
	public void çiz() {
		
		dönüşüm.konumu.birinciBileşeni = -200.0F;
		dönüşüm.konumu.ikinciBileşeni = -200.0F;
		dönüşüm.güncelle();
		görselleştirici.sesEkle('A', renk, dönüşüm);
		
		dönüşüm.konumu.birinciBileşeni = -200.0F;
		dönüşüm.konumu.ikinciBileşeni = +200.0F;
		dönüşüm.güncelle();
		görselleştirici.sesEkle('B', renk, dönüşüm);
		
		dönüşüm.konumu.birinciBileşeni = +200.0F;
		dönüşüm.konumu.ikinciBileşeni = +200.0F;
		dönüşüm.güncelle();
		görselleştirici.sesEkle('C', renk, dönüşüm);
		
		dönüşüm.konumu.birinciBileşeni = +204.0F;
		dönüşüm.konumu.ikinciBileşeni = +196.0F;
		dönüşüm.güncelle();
		görselleştirici
			.sesEkle('C', new Yöney4(0.5F, 0.5F, 0.5F, 1.0F), dönüşüm);
		
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
