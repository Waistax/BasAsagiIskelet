/**
 * başaşağıderebeyi.iskelet.görsel.KöşeDizisi.java
 * 0.5 / 5 Mar 2021 / 13:36:30
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.görsel;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.*;

/** Köşeler ile ilgili verileri tutan ve onları çizen nesne. */
public class KöşeDizisi {
	/** Yükleneceği yükleyici. */
	protected final Yükleyici yükleyici;
	/** Köşelerin verilerinin nasıl çizileceği. */
	protected final int çizimKipi;
	/** Toplam köşe sayısı. */
	protected final int köşeSayısı;
	/** Nesnenin ekran kartındaki konumu. */
	protected final int işaretçisi;
	
	/** Nesneye eklenmiş niteliklerin sayısı. Bunun sayesinde yeni eklenecek
	 * niteliklerin sırası bilinir. */
	protected int niteliklerininSayısı;
	
	/** Verilenler ile tanımlar. */
	public KöşeDizisi(
		final Yükleyici yükleyici,
		final int çizimKipi,
		final int köşeSayısı) {
		this.yükleyici = yükleyici;
		this.çizimKipi = çizimKipi;
		this.köşeSayısı = köşeSayısı;
		işaretçisi = yükleyici.köşeDizisiNesnesiYükle();
	}
	
	/** Köşe başına değişen ve bir kere yazıldıktan sonra değişmeyen verileri bu
	 * köşe dizisi nesnesine ekler. */
	public void durağanKöşeTamponuNesnesiEkle(
		final int boyutu,
		final FloatBuffer verisi) {
		glBindVertexArray(işaretçisi);
		yükleyici
			.köşeTamponuNesnesiYükle(niteliklerininSayısı++, boyutu, verisi);
		glBindVertexArray(0);
	}
	
	/** Köşe dizisini çizer. */
	public void çiz() {
		glBindVertexArray(işaretçisi);
		for (int i = 0; i < niteliklerininSayısı; i++)
			glEnableVertexAttribArray(i);
		çizimÇağrısıYap();
		for (int i = 0; i < niteliklerininSayısı; i++)
			glDisableVertexAttribArray(i);
		glBindVertexArray(0);
	}
	
	/** Ekran kartına bu köşe dizisini çizmek için çağrı gönderir. */
	protected void çizimÇağrısıYap() {
		glDrawArrays(çizimKipi, 0, köşeSayısı);
	}
}
