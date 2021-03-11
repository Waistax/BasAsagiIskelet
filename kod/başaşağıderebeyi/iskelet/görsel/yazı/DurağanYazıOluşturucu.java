/**
 * başaşağıderebeyi.iskelet.görsel.yazı.DurağanYazıOluşturucu.java
 * 0.7 / 9 Mar 2021 / 20:48:15
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.iskelet.görsel.yazı;

import static başaşağıderebeyi.kütüphane.matematik.MatematikAracı.*;

import başaşağıderebeyi.kütüphane.matematik.*;

import java.util.*;

/** Verilen satırları ekran kartına yükleyen araç. */
public abstract class DurağanYazıOluşturucu {
	final List<Float> konumları;
	final List<Float> dokuKonumları;
	final List<Integer> sırası;
	
	private final float yarımDikmeliği;
	private final Dikdörtgen ekleneninDikdörtgeni;
	
	/** Yazının şekli. */
	protected final YazıŞekli şekli;
	/** Yüklenecek satırlar. Bu satırlar verilen satırların aynısı olmak zorunda
	 * değil. Oluşturucular verilen satırları istedikleri gibi biçimlendirip bu
	 * listeye eklerler. */
	protected final List<String> satırları;
	
	/** Verilenlerle oluşturucuyu tanımlar. */
	public DurağanYazıOluşturucu(
		final YazıŞekli şekli,
		final float açısı,
		final String... satırlar) {
		int sesSayısı = 0;
		for (final String element : satırlar)
			sesSayısı += element.length();
		
		konumları = new ArrayList<>(sesSayısı * 4);
		dokuKonumları = new ArrayList<>(sesSayısı * 4);
		sırası = new ArrayList<>(sesSayısı * 6);
		
		this.şekli = şekli;
		yarımDikmeliği = dikmeliğiniBul(radyanaÇevir(açısı)) / 2.0F;
		
		satırları = new ArrayList<>();
		satırlarınıBul(satırlar);
		ekleneninDikdörtgeni = new Dikdörtgen();
		
		satırlarıEkle();
	}
	
	/** Verilen satırlardan yazılacak satırları oluşturur ve yerleşik listeye
	 * ekler. */
	protected abstract void satırlarınıBul(String[] satırlar);
	
	/** İlk satırın çizgisinin dikeydeki konumunu döndürür. */
	protected abstract float satırÇizgisininBaşlangıcınıBul();
	
	/** Verilen satırın ilk sesinin sol kenarının konumunu döndürür. */
	protected abstract float satırınınKonumunuBul(int satırınSırası);
	
	private void satırlarıEkle() {
		float satırÇizgisi = satırÇizgisininBaşlangıcınıBul();
		for (int i = 0; i < satırları.size(); i++) {
			satırınıEkle(i, satırÇizgisi, satırınınKonumunuBul(i));
			satırÇizgisi -= YazıŞekli.ÇİZGİLER_ARASI_BOŞLUĞUN_ORANI;
		}
	}
	
	private void satırınıEkle(
		final int satırınınSırası,
		final float satırınınÇizgisi,
		final float satırınınKonumu) {
		ekleneninDikdörtgeni.küçükKöşesi.birinciBileşeni = satırınınKonumu;
		final String satırı = satırları.get(satırınınSırası);
		for (int i = 0; i < satırı.length(); i++) {
			final SesŞekli sesŞekli =
				şekli.sesininŞekliniEdin(satırı.charAt(i));
			if (i > 0) {
				final SesŞekli öncekiSesŞekli =
					şekli.sesininŞekliniEdin(satırı.charAt(i - 1));
				ekleneninDikdörtgeni.küçükKöşesi.birinciBileşeni +=
					(öncekiSesŞekli.boyutu.birinciBileşeni +
						(sesŞekli.boyutu.birinciBileşeni +
							öncekiSesŞekli.boyutu.birinciBileşeni) *
							YazıŞekli.SESLER_ARASI_BOŞLUĞUN_ORANI) /
						şekli.enBüyükYüksekliği;
			}
			ekleneninDikdörtgeni.küçükKöşesi.ikinciBileşeni = satırınınÇizgisi;
			sesiEkle(sesŞekli);
		}
	}
	
	private void sesiEkle(final SesŞekli sesŞekli) {
		eklenenSeseAyarla(sesŞekli);
		
		final float oranlıYarımDikmeliği =
			yarımDikmeliği * ekleneninDikdörtgeni.uzunlukları.ikinciBileşeni;
		
		for (int i = 0; i < 4; i++) {
			köşeyiEkle(i, oranlıYarımDikmeliği);
			dokuKonumunuEkle(i, sesŞekli);
		}
		
		sonrakiÜçgenleriSırala();
	}
	
	private void eklenenSeseAyarla(final SesŞekli sesŞekli) {
		ekleneninDikdörtgeni.uzunlukları
			.böl(sesŞekli.boyutu, şekli.enBüyükYüksekliği);
		
		ekleneninDikdörtgeni.küçükKöşesi.ikinciBileşeni +=
			(sesŞekli.çizgidenUzaklığı - sesŞekli.boyutu.ikinciBileşeni) /
				şekli.enBüyükYüksekliği;
		
		DikdörtgenVerisi
			.bileşenleriniBul(
				DikdörtgenVerisi.KÜÇÜK_KÖŞESİ,
				DikdörtgenVerisi.UZUNLUKLARI,
				ekleneninDikdörtgeni);
	}
	
	private void köşeyiEkle(
		final int sırası,
		final float oranlıYarımDikmeliği) {
		final boolean a = sırası < 2;
		konumları
			.add(
				(sırası % 2 == 0 ?
					ekleneninDikdörtgeni.küçükKöşesi :
					ekleneninDikdörtgeni.büyükKöşesi).birinciBileşeni +
					(a ? -oranlıYarımDikmeliği : oranlıYarımDikmeliği));
		konumları
			.add(
				(a ?
					ekleneninDikdörtgeni.küçükKöşesi :
					ekleneninDikdörtgeni.büyükKöşesi).ikinciBileşeni);
		konumları.add(0.0F);
		konumları.add(1.0F);
	}
	
	private void dokuKonumunuEkle(final int sırası, final SesŞekli sesŞekli) {
		final Yöney2 dokuKonumu = switch (sırası) {
		case 0 -> sesŞekli.solAltDokuKonumu;
		case 1 -> sesŞekli.sağAltDokuKonumu;
		case 2 -> sesŞekli.solÜstDokuKonumu;
		case 3 -> sesŞekli.sağÜstDokuKonumu;
		default -> throw new IllegalArgumentException(
			"Unexpected value: " + sırası);
		};
		
		dokuKonumları.add(dokuKonumu.birinciBileşeni);
		dokuKonumları.add(dokuKonumu.ikinciBileşeni);
	}
	
	private void sonrakiÜçgenleriSırala() {
		final int başlangıçSırası = konumları.size() / 4 - 4;
		
		sırası.add(başlangıçSırası);
		sırası.add(başlangıçSırası + 1);
		sırası.add(başlangıçSırası + 2);
		
		sırası.add(başlangıçSırası + 2);
		sırası.add(başlangıçSırası + 1);
		sırası.add(başlangıçSırası + 3);
	}
}
