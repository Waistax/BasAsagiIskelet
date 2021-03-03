/**
 * başaşağıderebeyi.iskelet.girdi.TekerlekGirdisiBildiricisi.java
 * 0.2 / 3 Mar 2021 / 21:59:37
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.girdi;

import org.lwjgl.glfw.*;

class TekerlekGirdisiBildiricisi extends GLFWScrollCallback {
	private final Girdi içerenGirdi;
	private final TekerlekGirdisiOlayı olayı;
	
	TekerlekGirdisiBildiricisi(final Girdi içerenGirdi) {
		this.içerenGirdi = içerenGirdi;
		olayı = new TekerlekGirdisiOlayı();
	}
	
	@Override
	public void invoke(
		final long pencereİşaretçisi,
		final double yataydakiDevri,
		final double dikeydekiDevri) {
		olayı.devri = dikeydekiDevri;
		olayı.susturulması = false;
		içerenGirdi.bildireceğiOlayDağıtıcısı.dağıt(olayı);
	}
}
