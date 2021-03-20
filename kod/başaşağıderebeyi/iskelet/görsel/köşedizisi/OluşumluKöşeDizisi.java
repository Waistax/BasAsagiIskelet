/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.4 / 4 Mar 2021 / 19:32:53
 */
package başaşağıderebeyi.iskelet.görsel.köşedizisi;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;

import başaşağıderebeyi.iskelet.görsel.*;

import java.nio.*;

/** Birden fazla nesnenin niteliklerini bir arada tutan köşe dizisi nesnesi. */
public class OluşumluKöşeDizisi extends KöşeDizisi {
	/** Oluşumların verilerini içeren tampon. */
	public final FloatBuffer yazılacakVerisi;
	/** Çizebileceği en fazla oluşum sayısı. */
	public final int sığası;
	
	/** Her bir oluşumun float biriminden boyutu. */
	protected final int oluşumBoyutu;
	/** Çizilecek oluşumların sayısı. */
	protected int oluşumSayısı;
	
	private final int oluşumluKöşeTamponuNesnesi;
	private int niteliklerininBoyutu;
	
	/** Verilen boyutta ve sığada tanımlar. */
	public OluşumluKöşeDizisi(
		final int çizimKipi,
		final int sığası,
		final int oluşumBoyutu) {
		super(çizimKipi);
		yazılacakVerisi = Yükleyici.NESNESİ.tamponYükle(oluşumBoyutu * sığası);
		this.sığası = sığası;
		this.oluşumBoyutu = oluşumBoyutu;
		oluşumluKöşeTamponuNesnesi = Yükleyici.NESNESİ
			.boşKöşeTamponuNesnesiOluştur(yazılacakVerisi.capacity());
	}
	
	/** Oluşum başına değişen ve her kare yenilenebilecek verileri bu köşe
	 * dizisi nesnesine ekler. Verilen tamponu kendiliğinden yok eder. */
	public void oluşumBaşınaDeğişenNitelikEkle(final int boyutu) {
		glBindVertexArray(işaretçisi);
		Yükleyici.NESNESİ
			.oluşumBaşınaDeğişenNitelikEkle(
				işaretçisi,
				oluşumluKöşeTamponuNesnesi,
				niteliklerininSayısı++,
				boyutu,
				oluşumBoyutu,
				niteliklerininBoyutu);
		niteliklerininBoyutu += boyutu;
		glBindVertexArray(0);
	}
	
	/** Tampona yazılmış verileri oluşum başına değişen köşe tamponu nesnesine
	 * yükler. Yerleşik tamponu kendiliğinden çevirir. */
	public void tamponunuGüncelle() {
		Yükleyici.NESNESİ
			.oluşumlarınNitelikleriniGüncelle(
				oluşumluKöşeTamponuNesnesi,
				yazılacakVerisi.flip());
		oluşumSayısı = yazılacakVerisi.limit() / oluşumBoyutu;
		yazılacakVerisi.clear();
	}
	
	@Override
	protected void çizimÇağrısıYap() {
		glDrawArraysInstanced(çizimKipi, 0, çizilecekKöşeSayısı, oluşumSayısı);
	}
}
