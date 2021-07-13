/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.12.2 / 20 Mar 2021 / 22:59:59
 */
package başaşağıderebeyi.iskelet.görsel.kaynak;

import java.awt.image.*;
import java.nio.file.*;
import java.util.function.*;
import java.util.stream.*;

import javax.imageio.*;

public class ResimBilgisi {
	private static final IntUnaryOperator BENEĞİ_RGB_UZAYINA_ÇEVİRİCİ =
		benekRengi -> benekRengi & 0xFF000000 |
			(benekRengi & 0x000000FF) << 16 |
			(benekRengi & 0x0000FF00) << 0 |
			(benekRengi & 0x00FF0000) >> 16;
	
	/** Resmin yataydaki benek sayısı. */
	public final int genişliği;
	/** Resmin dikeydeki benek sayısı. */
	public final int yüksekliği;
	/** Resmin benekleri. Bu dizide beneklerin her biri bir int tarafından
	 * temsil edilir. Bunun biçimi yüklendikten sonra çevrilmelidir. */
	public final int[] verisi;
	
	/** Boş tanımlar. */
	public ResimBilgisi(final int genişliği, final int yüksekliği) {
		this.genişliği = genişliği;
		this.yüksekliği = yüksekliği;
		verisi = new int[genişliği * yüksekliği];
	}
	
	/** Verilen kaynaktan yükler. Kaynaklardan alınan resimler ekran kartına
	 * yüklenmeden önce çevrilmelidir. */
	public ResimBilgisi(final Path tanımlayıcısı) {
		try {
			final BufferedImage resim = ImageIO.read(tanımlayıcısı.toFile());
			genişliği = resim.getWidth();
			yüksekliği = resim.getHeight();
			verisi = resim
				.getRGB(
					0,
					0,
					genişliği,
					yüksekliği,
					new int[genişliği * yüksekliği],
					0,
					genişliği);
			çevir();
		} catch (final Exception hata) {
			throw new RuntimeException(
				"Resim kaynağı " + tanımlayıcısı + " yüklenemedi!",
				hata);
		}
	}
	
	private ResimBilgisi çevir() {
		IntStream
			.range(0, verisi.length)
			.parallel()
			.forEach(
				benek -> verisi[benek] =
					BENEĞİ_RGB_UZAYINA_ÇEVİRİCİ.applyAsInt(verisi[benek]));
		return this;
	}
	
	/** Resmin verisini yeni oluşturduğu bir bayt dizisine yazar ve döndürür. */
	public byte[] renkBaytlarınıBul() {
		final byte[] renkBaytları = new byte[verisi.length * 4];
		
		IntStream.range(0, verisi.length).parallel().forEach(benek -> {
			final int benekRengi = verisi[benek];
			renkBaytları[benek * 4 + 0] = (byte)(benekRengi >> 16);
			renkBaytları[benek * 4 + 1] = (byte)(benekRengi >> 8);
			renkBaytları[benek * 4 + 2] = (byte)benekRengi;
			renkBaytları[benek * 4 + 3] = (byte)(benekRengi >> 24);
		});
		
		return renkBaytları;
	}
}
