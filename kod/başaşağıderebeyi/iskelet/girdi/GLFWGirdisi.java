/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 20:49:04
 * 
 * Waistax Engine'den biraz alındı.
 * 4.0.0 / 7 Ara 2018 / ?
 */
package başaşağıderebeyi.iskelet.girdi;

import static başaşağıderebeyi.iskelet.İskelet.*;
import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.olaylar.*;
import başaşağıderebeyi.kütüphane.olay.*;

/** Pencereye yapılan girdileri bildirir. */
public class GLFWGirdisi {
	private final KlavyeGirdisiBildiricisi klavyeGirdisiBildiricisi;
	private final FareGirdisiBildiricisi fareGirdisiBildiricisi;
	private final İmleçGirdisiBildiricisi imleçGirdisiBildiricisi;
	private final TekerlekGirdisiBildiricisi tekerlekGirdisiBildiricisi;
	
	/** Verilenler ile tanımlar. */
	public GLFWGirdisi(final long dinlediğiPencere) {
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
		İSKELET
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(
					KlavyeGirdisiOlayı.class,
					olay -> olay.tuşu.basılıOlmasınıBildir(olay.basılması)));
		İSKELET
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(
					FareGirdisiOlayı.class,
					olay -> olay.tuşu.basılıOlmasınıBildir(olay.basılması)));
		İSKELET
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(
					İmleçGirdisiOlayı.class,
					olay -> İSKELET
						.girdisiniEdin()
						.imlecininKonumunuBildir(
							olay.konumununYatayBileşeni,
							olay.konumununDikeyBileşeni)));
		İSKELET
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dinleyiciyiEkle(
				new DinleyiciBilgisi<>(
					TekerlekGirdisiOlayı.class,
					olay -> İSKELET
						.girdisiniEdin()
						.tekerleğininDevriniBildir(
							(int)Math.round(olay.devri))));
	}
	
	private void bildiricileriPencereyeEkle(final long dinlediğiPencere) {
		glfwSetKeyCallback(dinlediğiPencere, klavyeGirdisiBildiricisi);
		glfwSetMouseButtonCallback(dinlediğiPencere, fareGirdisiBildiricisi);
		glfwSetCursorPosCallback(dinlediğiPencere, imleçGirdisiBildiricisi);
		glfwSetScrollCallback(dinlediğiPencere, tekerlekGirdisiBildiricisi);
	}
}
