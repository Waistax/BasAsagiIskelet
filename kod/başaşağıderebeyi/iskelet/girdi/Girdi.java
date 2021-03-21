/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 20:49:04
 * 
 * Waistax Engine'den biraz alındı.
 * 4.0.0 / 7 Ara 2018 / ?
 */
package başaşağıderebeyi.iskelet.girdi;

import static başaşağıderebeyi.kütüphane.matematik.MatematikAracı.*;
import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.olaylar.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** Pencereye yapılan girdileri bildirir. */
public class Girdi {
	private final KlavyeGirdisiBildiricisi klavyeGirdisiBildiricisi;
	private final FareGirdisiBildiricisi fareGirdisiBildiricisi;
	private final İmleçGirdisiBildiricisi imleçGirdisiBildiricisi;
	private final TekerlekGirdisiBildiricisi tekerlekGirdisiBildiricisi;
	
	/** Verilenler ile tanımlar. */
	public Girdi(final long dinlediğiPencere) {
		klavyeGirdisiBildiricisi = new KlavyeGirdisiBildiricisi();
		fareGirdisiBildiricisi = new FareGirdisiBildiricisi();
		imleçGirdisiBildiricisi = new İmleçGirdisiBildiricisi();
		tekerlekGirdisiBildiricisi = new TekerlekGirdisiBildiricisi();
		
		dinleyicileriniİskeleteEkle();
		bildiricileriPencereyeEkle(dinlediğiPencere);
	}
	
	/** Bütün bildiricileri yok eder. */
	public void bildiriceleriniSal() {
		klavyeGirdisiBildiricisi.free();
		fareGirdisiBildiricisi.free();
		imleçGirdisiBildiricisi.free();
		tekerlekGirdisiBildiricisi.free();
	}
	
	private void dinleyicileriniİskeleteEkle() {
		İskelet.NESNESİ
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(
					KlavyeGirdisiOlayı.class,
					olay -> olay.tuşu.basılıOlmasınıBildir(olay.basılıOlması)));
		İskelet.NESNESİ
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(
					FareGirdisiOlayı.class,
					olay -> olay.tuşu.basılıOlmasınıBildir(olay.basılıOlması)));
		İskelet.NESNESİ
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(
					İmleçGirdisiOlayı.class,
					olay -> İskelet.NESNESİ
						.girdisiniEdin()
						.imlecininKonumunuBildir(
							(float)olay.konumununYatayBileşeni,
							(float)olay.konumununDikeyBileşeni)));
		İskelet.NESNESİ
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(
					TekerlekGirdisiOlayı.class,
					olay -> İskelet.NESNESİ
						.girdisiniEdin()
						.tekerleğininDevriniBildir(
							yuvarla((float)olay.devri))));
	}
	
	private void bildiricileriPencereyeEkle(final long dinlediğiPencere) {
		glfwSetKeyCallback(dinlediğiPencere, klavyeGirdisiBildiricisi);
		glfwSetMouseButtonCallback(dinlediğiPencere, fareGirdisiBildiricisi);
		glfwSetCursorPosCallback(dinlediğiPencere, imleçGirdisiBildiricisi);
		glfwSetScrollCallback(dinlediğiPencere, tekerlekGirdisiBildiricisi);
	}
}
