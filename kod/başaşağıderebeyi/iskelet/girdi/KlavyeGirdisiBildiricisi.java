/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 20:42:51
 */
package başaşağıderebeyi.iskelet.girdi;

import static başaşağıderebeyi.iskelet.İskelet.*;
import static org.lwjgl.glfw.GLFW.*;

import başaşağıderebeyi.iskelet.olaylar.*;

import org.lwjgl.glfw.*;

class KlavyeGirdisiBildiricisi extends GLFWKeyCallback {
	KlavyeGirdisiBildiricisi() {
		for (int i = 0; i <= GLFW_KEY_LAST; i++)
			İSKELET.girdisiniEdin().klavyesininTuşunuEkle(i);
	}
	
	@Override
	public void invoke(
		final long pencereİşaretçisi,
		final int tuşKodu,
		final int okumaKodu,
		final int hareket,
		final int kipleri) {
		İSKELET
			.güncellemeOlaylarınınDağıtıcısınıEdin()
			.dağıt(new KlavyeGirdisiOlayı(tuşKodu, hareket != GLFW_RELEASE));
	}
}
