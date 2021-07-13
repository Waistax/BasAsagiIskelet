/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.7 / 9 Mar 2021 / 20:48:15
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import static java.lang.Math.*;
import static org.lwjgl.system.MemoryUtil.*;

import başaşağıderebeyi.kütüphane.matematik.dikdörtgen.*;

import java.nio.*;
import java.util.*;

/** Verilen satırları ekran kartına yükleyen araç. */
public abstract class DurağanYazıOluşturucu {
	/** Yazının şekli. */
	protected final YazıŞekli şekli;
	/** Yüklenecek satırlar. Bu satırlar verilen satırların aynısı olmak zorunda
	 * değil. Oluşturucular verilen satırları istedikleri gibi biçimlendirip bu
	 * listeye eklerler. */
	protected final List<String> satırları;
	
	final FloatBuffer konumları;
	final FloatBuffer dokuKonumları;
	final IntBuffer sırası;
	
	private final double yarıEğimi;
	private final Dikdörtgen ekleneninDikdörtgeni;
	
	int üçgenlerinSayısı;
	
	/** Verilenlerle oluşturucuyu tanımlar. */
	public DurağanYazıOluşturucu(
		final YazıŞekli şekli,
		final double açısı,
		final String... satırlar) {
		this.şekli = şekli;
		
		satırları = new ArrayList<>();
		satırlarınıBul(satırlar);
		
		final int sesSayısı = sesSayısınıBul();
		konumları = memAllocFloat(sesSayısı * 4 * 2);
		dokuKonumları = memAllocFloat(sesSayısı * 4 * 2);
		sırası = memAllocInt(sesSayısı * 6);
		
		yarıEğimi = (float)(tan(toRadians(açısı)) / 2.0);
		ekleneninDikdörtgeni = new Dikdörtgen();
		ekleneninDikdörtgeni.uzunlukları
			.böl(şekli.boyutu, şekli.boyutu.ikinciBileşeniniEdin());
		satırlarıEkle();
	}
	
	/** Verilen satırlardan yazılacak satırları oluşturur ve yerleşik listeye
	 * ekler. */
	protected abstract void satırlarınıBul(String[] satırlar);
	
	/** İlk satırın dikeydeki konumunu döndürür. */
	protected abstract double ilkSatırınDikeyKonumunuBul();
	
	/** Verilen satırın ilk sesinin yatay konumunu döndürür. */
	protected abstract double satırınınYatayKonumu(int satırınSırası);
	
	private int sesSayısınıBul() {
		int sesSayısı = 0;
		for (final String satırı : satırları)
			for (int i = 0; i < satırı.length(); i++)
				if (şekli.sesininŞekliniEdin(satırı.charAt(i)) != null)
					sesSayısı++;
		return sesSayısı;
	}
	
	private void satırlarıEkle() {
		ekleneninDikdörtgeni.ortaNoktası
			.ikinciBileşeniniDeğiştir(ilkSatırınDikeyKonumunuBul());
		for (int i = 0; i < satırları.size(); i++) {
			ekleneninDikdörtgeni.ortaNoktası.birinciBileşeni =
				satırınınYatayKonumu(i);
			satırınıEkle(i);
			ekleneninDikdörtgeni.ortaNoktası.ikinciBileşeni--;
		}
	}
	
	private void satırınıEkle(final int satırınınSırası) {
		final String satırı = satırları.get(satırınınSırası);
		for (int i = 0; i < satırı.length(); i++) {
			final SesŞekli sesŞekli =
				şekli.sesininŞekliniEdin(satırı.charAt(i));
			if (sesŞekli == null)
				continue;
			sesiEkle(sesŞekli);
			ekleneninDikdörtgeni.ortaNoktası.birinciBileşeni +=
				ekleneninDikdörtgeni.uzunlukları.birinciBileşeni;
		}
	}
	
	private void sesiEkle(final SesŞekli sesŞekli) {
		DikdörtgenVerisi
			.bileşenleriniBul(
				DikdörtgenVerisi.ORTA_NOKTASI,
				DikdörtgenVerisi.UZUNLUKLARI,
				ekleneninDikdörtgeni);
		köşeleriEkle();
		dokuKonumlarınıEkle(sesŞekli);
		üçgenleriSırala();
	}
	
	private void köşeleriEkle() {
		konumları
			.put(
				(float)(ekleneninDikdörtgeni.küçükKöşesi.birinciBileşeni -
					yarıEğimi))
			.put((float)ekleneninDikdörtgeni.küçükKöşesi.ikinciBileşeni)
			.put(
				(float)(ekleneninDikdörtgeni.büyükKöşesi.birinciBileşeni -
					yarıEğimi))
			.put((float)ekleneninDikdörtgeni.küçükKöşesi.ikinciBileşeni)
			.put(
				(float)(ekleneninDikdörtgeni.küçükKöşesi.birinciBileşeni +
					yarıEğimi))
			.put((float)ekleneninDikdörtgeni.büyükKöşesi.ikinciBileşeni)
			.put(
				(float)(ekleneninDikdörtgeni.büyükKöşesi.birinciBileşeni +
					yarıEğimi))
			.put((float)ekleneninDikdörtgeni.büyükKöşesi.ikinciBileşeni);
	}
	
	private void dokuKonumlarınıEkle(final SesŞekli sesŞekli) {
		dokuKonumları
			.put((float)sesŞekli.dokuKonumu.birinciBileşeni)
			.put((float)sesŞekli.dokuKonumu.dördüncüBileşeni)
			.put((float)sesŞekli.dokuKonumu.ikinciBileşeni)
			.put((float)sesŞekli.dokuKonumu.dördüncüBileşeni)
			.put((float)sesŞekli.dokuKonumu.birinciBileşeni)
			.put((float)sesŞekli.dokuKonumu.üçüncüBileşeni)
			.put((float)sesŞekli.dokuKonumu.ikinciBileşeni)
			.put((float)sesŞekli.dokuKonumu.üçüncüBileşeni);
	}
	
	private void üçgenleriSırala() {
		final int başlangıçSırası = 4 * (üçgenlerinSayısı++);
		sırası
			.put(başlangıçSırası)
			.put(başlangıçSırası + 1)
			.put(başlangıçSırası + 2)
			.put(başlangıçSırası + 2)
			.put(başlangıçSırası + 1)
			.put(başlangıçSırası + 3);
	}
}
