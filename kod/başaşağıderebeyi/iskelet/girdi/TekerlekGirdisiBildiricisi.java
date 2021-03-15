/**
 * başaşağıderebeyi.iskelet.girdi.TekerlekGirdisiBildiricisi.java
 * 0.2 / 3 Mar 2021 / 21:59:37
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.girdi;

import org.lwjgl.glfw.*;

class TekerlekGirdisiBildiricisi extends GLFWScrollCallback {
	private final TekerlekGirdisiOlayı olayı;
	
	TekerlekGirdisiBildiricisi(final Girdi içerenGirdi) {
		olayı = new TekerlekGirdisiOlayı(içerenGirdi.bildireceğiİskelet);
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
