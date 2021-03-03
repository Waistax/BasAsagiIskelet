/**
 * başaşağıderebeyi.iskelet.girdi.KlavyeGirdisiBildiricisi.java
 * 0.2 / 3 Mar 2021 / 20:42:51
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.girdi;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.*;

class KlavyeGirdisiBildiricisi extends GLFWKeyCallback {
	private final Girdi içerenGirdi;
	private final KlavyeGirdisiOlayı[] olayları =
		new KlavyeGirdisiOlayı[GLFW_KEY_LAST + 1];
	
	KlavyeGirdisiBildiricisi(final Girdi içerenGirdi) {
		this.içerenGirdi = içerenGirdi;
		
		for (int i = 0; i < olayları.length; i++) {
			içerenGirdi.bildireceğiÇiğGirdi.klavyesininTuşunuEkle(i);
			olayları[i] = new KlavyeGirdisiOlayı(
				içerenGirdi.bildireceğiÇiğGirdi.klavyesininTuşunuEdin(i));
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
		olayı.susturulması = false;
		içerenGirdi.bildireceğiOlayDağıtıcısı.dağıt(olayı);
	}
}
