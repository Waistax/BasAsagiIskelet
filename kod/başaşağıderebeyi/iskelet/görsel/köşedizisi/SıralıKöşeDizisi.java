/**
 * başaşağıderebeyi.iskelet.görsel.köşedizisi.SıralıKöşeDizisi.java
 * 0.6 / 6 Mar 2021 / 10:46:40
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.görsel.köşedizisi;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import başaşağıderebeyi.iskelet.görsel.*;

import java.nio.*;

/** Köşeleri bir sıra ile çizen nesne. */
public class SıralıKöşeDizisi extends KöşeDizisi {
	/** Verilenler ile tanımlar. */
	public SıralıKöşeDizisi(final Yükleyici yükleyici, final int çizimKipi) {
		super(yükleyici, çizimKipi);
	}
	
	/** Köşelerin çizim sırasını yükler. Verilen tamponu kendiliğinden yok
	 * eder. */
	public void sıraTamponuNesnesiYükle(final IntBuffer yüklenecekVeri) {
		glBindVertexArray(işaretçisi);
		yükleyici.sıraTamponuNesnesiYükle(yüklenecekVeri);
		glBindVertexArray(0);
		çizilecekKöşeSayısı = yüklenecekVeri.limit();
	}
	
	@Override
	protected void çizimÇağrısıYap() {
		glDrawElements(çizimKipi, çizilecekKöşeSayısı, GL_UNSIGNED_INT, 0);
	}
}
