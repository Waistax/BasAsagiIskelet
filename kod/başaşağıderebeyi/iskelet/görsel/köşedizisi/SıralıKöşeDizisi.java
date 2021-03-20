/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.6 / 6 Mar 2021 / 10:46:40
 */
package başaşağıderebeyi.iskelet.görsel.köşedizisi;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import başaşağıderebeyi.iskelet.görsel.*;

import java.nio.*;

/** Köşeleri bir sıra ile çizen nesne. */
public class SıralıKöşeDizisi extends KöşeDizisi {
	/** Verilenler ile tanımlar. */
	public SıralıKöşeDizisi(final int çizimKipi) {
		super(çizimKipi);
	}
	
	/** Köşelerin çizim sırasını yükler. Verilen tamponu kendiliğinden çevirir
	 * ve yok eder. */
	public void sıraTamponuNesnesiYükle(final IntBuffer yüklenecekVeri) {
		glBindVertexArray(işaretçisi);
		Yükleyici.NESNESİ.sıraTamponuNesnesiYükle(yüklenecekVeri.flip());
		glBindVertexArray(0);
		çizilecekKöşeSayısı = yüklenecekVeri.limit();
	}
	
	@Override
	protected void çizimÇağrısıYap() {
		glDrawElements(çizimKipi, çizilecekKöşeSayısı, GL_UNSIGNED_INT, 0);
	}
}
