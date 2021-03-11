/**
 * başaşağıderebeyi.iskelet.görsel.köşedizisi.KöşeDizisi.java
 * 0.5 / 5 Mar 2021 / 13:36:30
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.görsel.köşedizisi;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import başaşağıderebeyi.iskelet.görsel.*;

import java.nio.*;

/** Köşeler ile ilgili verileri tutan ve onları çizen nesne. */
public class KöşeDizisi {
	/** Yükleneceği yükleyici. */
	protected final Yükleyici yükleyici;
	/** Köşelerin verilerinin nasıl çizileceği. */
	protected final int çizimKipi;
	/** Nesnenin ekran kartındaki konumu. */
	protected final int işaretçisi;
	
	/** Çizilecek köşe sayısı. */
	protected int çizilecekKöşeSayısı;
	/** Nesneye eklenmiş niteliklerin sayısı. Bunun sayesinde yeni eklenecek
	 * niteliklerin sırası bilinir. */
	protected int niteliklerininSayısı;
	
	/** Verilenler ile tanımlar. */
	public KöşeDizisi(final Yükleyici yükleyici, final int çizimKipi) {
		this.yükleyici = yükleyici;
		this.çizimKipi = çizimKipi;
		işaretçisi = yükleyici.köşeDizisiNesnesiYükle();
	}
	
	/** Köşe başına değişen ve bir kere yazıldıktan sonra değişmeyen verileri bu
	 * köşe dizisi nesnesine ekler. Verilen tamponu kendiliğinden çevirir ve yok
	 * eder. */
	public void durağanKöşeTamponuNesnesiEkle(
		final int boyutu,
		final FloatBuffer verisi) {
		glBindVertexArray(işaretçisi);
		yükleyici
			.köşeTamponuNesnesiYükle(
				niteliklerininSayısı++,
				boyutu,
				verisi.flip());
		glBindVertexArray(0);
		
		if (çizilecekKöşeSayısı == 0)
			çizilecekKöşeSayısı = verisi.limit() / boyutu;
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
		glDrawArrays(çizimKipi, 0, çizilecekKöşeSayısı);
	}
}
