/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 20:42:51
 */
package başaşağıderebeyi.iskelet.girdi;

import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.*;
import başaşağıderebeyi.iskelet.olaylar.*;

import org.lwjgl.glfw.*;

class KlavyeGirdisiBildiricisi extends GLFWKeyCallback {
	private final KlavyeGirdisiOlayı[] olayları =
		new KlavyeGirdisiOlayı[GLFW_KEY_LAST + 1];
	
	KlavyeGirdisiBildiricisi() {
		for (int i = 0; i < olayları.length; i++) {
			İskelet.NESNESİ.girdisiniEdin().klavyesininTuşunuEkle(i);
			olayları[i] = new KlavyeGirdisiOlayı(
				İskelet.NESNESİ.girdisiniEdin().klavyesininTuşunuEdin(i));
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
