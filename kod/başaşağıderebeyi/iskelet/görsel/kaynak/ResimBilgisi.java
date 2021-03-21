/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.12.2 / 20 Mar 2021 / 22:59:59
 */
package başaşağıderebeyi.iskelet.görsel.kaynak;

import static java.lang.Math.*;

import java.awt.image.*;
import java.net.*;
import java.nio.file.*;
import java.util.function.*;
import java.util.stream.*;

import javax.imageio.*;

import org.hsluv.*;

public class ResimBilgisi {
	/** Resmin beneğini RGB renk uzayına çeviren işleç. */
	public static final IntUnaryOperator BENEĞİ_RGB_UZAYINA_ÇEVİRİCİ =
		benekRengi -> benekRengi & 0xFF000000 |
			(benekRengi & 0x000000FF) << 16 |
			(benekRengi & 0x0000FF00) << 0 |
			(benekRengi & 0x00FF0000) >> 16;
	
	/** Resmin beneğini HSLUV renk uzayına çeviren işleç. */
	public static final IntUnaryOperator BENEĞİ_HSLUV_UZAYINA_ÇEVİRİCİ =
		benekRengi -> {
			final int solukluğu = (benekRengi & 0xFF000000) >> 24;
			
			final double[] HSLuv = HUSLColorConverter
				.rgbToHsluv(
					new double[] {
						((benekRengi & 0x00FF0000) >> 16) / 255.0,
						((benekRengi & 0x0000FF00) >> 8) / 255.0,
						((benekRengi & 0x000000FF) >> 0) / 255.0 });
			
			return solukluğu << 24 |
				(int)round(HSLuv[2] / 100.0 * 255.0) << 16 |
				(int)round(HSLuv[1] / 100.0 * 255.0) << 8 |
				(int)round(HSLuv[0] / 360.0 * 255.0);
		};
	
	public final int genişliği;
	public final int yüksekliği;
	public final int[] verisi;
	
	/** Boş tanımlar. */
	public ResimBilgisi(final int genişliği, final int yüksekliği) {
		this.genişliği = genişliği;
		this.yüksekliği = yüksekliği;
		verisi = new int[genişliği * yüksekliği];
	}
	
	/** Verilen kaynaktan yükler. Kaynaklardan alınan resimler ekran kartına
	 * yüklenmeden önce çevrilmelidir. */
	public ResimBilgisi(final URI tanımlayıcısı) {
		try {
			final BufferedImage resim =
				ImageIO.read(Path.of(tanımlayıcısı).toFile());
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
		} catch (final Exception hata) {
			throw new RuntimeException(
				"Resim kaynağı " + tanımlayıcısı + " yüklenemedi!",
				hata);
		}
	}
	
	public ResimBilgisi rgbOlarak() {
		return çevir(BENEĞİ_RGB_UZAYINA_ÇEVİRİCİ);
	}
	
	public ResimBilgisi hsluvOlarak() {
		return çevir(BENEĞİ_HSLUV_UZAYINA_ÇEVİRİCİ);
	}
	
	public ResimBilgisi çevir(final IntUnaryOperator çevirici) {
		IntStream
			.range(0, verisi.length)
			.parallel()
			.forEach(
				benek -> verisi[benek] = çevirici.applyAsInt(verisi[benek]));
		return this;
	}
	
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
