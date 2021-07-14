/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.6.3 / 14 Tem 2021 / 19:43:32
 */
package başaşağıderebeyi.iskelet;

import java.lang.module.*;
import java.nio.file.*;
import java.util.stream.*;

/** İskeleti çalıştıran ve kütüphaneleri yükleyen sınıf. */
public class Başlatıcı {
	private static final String KÜTÜPHANE_KLASÖRÜ = "kütüphaneler";
	
	/** İskeleti başlatır. */
	public static void main(final String... argümanlar) {
		try {
			final Class<?> iskeletSınıfı = kütüphaneleriYükle()
				.findLoader("başaşağıderebeyi.iskelet")
				.loadClass("başaşağıderebeyi.iskelet.İskelet");
			iskeletSınıfı
				.getMethod("başlat")
				.invoke(iskeletSınıfı.getField("NESNESİ").get(null));
		} catch (final Exception hata) {
			hata.printStackTrace();
		}
	}
	
	private static ModuleLayer kütüphaneleriYükle() {
		final ModuleLayer üstModülKatmanı = ModuleLayer.boot();
		final ModuleFinder modülBulucu =
			ModuleFinder.of(Path.of(KÜTÜPHANE_KLASÖRÜ), Path.of("iskelet.jar"));
		return üstModülKatmanı
			.defineModulesWithOneLoader(
				üstModülKatmanı
					.configuration()
					.resolve(
						modülBulucu,
						ModuleFinder.of(),
						modülBulucu
							.findAll()
							.stream()
							.map(ModuleReference::descriptor)
							.map(ModuleDescriptor::name)
							.collect(Collectors.toSet())),
				ClassLoader.getSystemClassLoader());
	}
}
