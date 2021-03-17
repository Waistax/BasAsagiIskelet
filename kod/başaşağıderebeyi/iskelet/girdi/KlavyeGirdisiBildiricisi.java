/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 20:42:51
 */
package başaşağıderebeyi.iskelet.girdi;

import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.*;

import org.lwjgl.glfw.*;

class KlavyeGirdisiBildiricisi extends GLFWKeyCallback {
	private final KlavyeGirdisiOlayı[] olayları =
		new KlavyeGirdisiOlayı[GLFW_KEY_LAST + 1];
	
	KlavyeGirdisiBildiricisi(final İskelet bildireceğiİskelet) {
		for (int i = 0; i < olayları.length; i++) {
			bildireceğiİskelet.girdisiniEdin().klavyesininTuşunuEkle(i);
			olayları[i] = new KlavyeGirdisiOlayı(
				bildireceğiİskelet,
				bildireceğiİskelet.girdisiniEdin().klavyesininTuşunuEdin(i));
		}
	}
	
	@Override
	public void invoke(
		final long pencereİşaretçisi,
		final int tuşKodu,
		final int okumaKodu,
		final int hareket,
		final int kipleri) {
		final KlavyeGirdisiOlayı olayı = olayları[tuşKodu];
		olayı.basılıOlması = hareket != GLFW_RELEASE;
		olayı.dağıtmayıDene();
	}
}
