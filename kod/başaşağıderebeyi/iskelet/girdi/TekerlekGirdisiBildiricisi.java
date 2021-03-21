/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 21:59:37
 */
package başaşağıderebeyi.iskelet.girdi;

import başaşağıderebeyi.iskelet.olaylar.*;

import org.lwjgl.glfw.*;

class TekerlekGirdisiBildiricisi extends GLFWScrollCallback {
	private final TekerlekGirdisiOlayı olayı;
	
	TekerlekGirdisiBildiricisi() {
		olayı = new TekerlekGirdisiOlayı();
	}
	
	@Override
	public void invoke(
		final long pencereİşaretçisi,
		final double yataydakiDevri,
		final double dikeydekiDevri) {
		olayı.devri = dikeydekiDevri;
		olayı.dağıtmayıDene();
	}
}
