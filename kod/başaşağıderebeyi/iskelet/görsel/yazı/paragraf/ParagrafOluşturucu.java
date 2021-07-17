/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 2.10.13 / 17 Tem 2021 / 10:42:43
 */
package başaşağıderebeyi.iskelet.görsel.yazı.paragraf;

import java.util.*;

/* Paragraflar nesneleri oluşturmaya yarayan araç. */
class ParagrafOluşturucu {
	private final List<ParagrafSatırı> satırları;
	private final double genişliği;
	private final double girintisi;
	private final boolean ilkParagrafınGirintiliOlması;
	private final boolean dayalıOlması;
	
	/* Verilen satırları doldurur. */
	ParagrafOluşturucu(
		final List<ParagrafSatırı> satırları,
		final double genişliği,
		final double girintisi,
		final boolean ilkParagrafınGirintiliOlması,
		final boolean dayalıOlması,
		final String... dizeleri) {
		this.satırları = satırları;
		this.genişliği = genişliği;
		this.girintisi = girintisi;
		this.ilkParagrafınGirintiliOlması = ilkParagrafınGirintiliOlması;
		this.dayalıOlması = dayalıOlması;
		
		// Bütün dizeler için dön. Her dizeyi farklı bir paragraf olarak satır
		// satır dizelgeye ekler.
		for (final String dizesi : dizeleri)
			dizesiniİşle(dizesi);
	}
	
	/* Verilen dizeyi paragrafa dönüştürür. */
	private void dizesiniİşle(final String dizesi) {
		// Paragrafın ilk satırının sırasını al ve bu satırın girintisni bul.
		final int ilkSatırınınSırası = satırları.size();
		final double girintisininUzunluğu =
			ilkSatırınınSırası == 0 && !ilkParagrafınGirintiliOlması ?
				0.0 :
				girintisi;
		
		// Paragraftaki "sözcük"leri bul.
		final String[] paragrafınSözcükleri = dizesi.split(" ");
		
		/* Oluşturacak satırların sözcüklerini tutacak tampon dizelge. Paragraf
		 * satırı nesnesi tanımlanırken verilen dizelgeyi kopyalıyor; Bu nedenle
		 * aynı tampon temizlenip tekrar kullanılabilir. */
		final List<String> satırınSözcükleri = new ArrayList<>();
		
		// Aşağıda yeni uzunluğu hesaplarken boşluk için bir ekleniyor ama ilk
		// sözcükten önce boşluk yok. Bu nedenle burada satırın uzunluğunu
		// girintiden bir eksik olarak tanımla.
		double satırınUzunluğu = girintisininUzunluğu - 1.0;
		
		// Paragraftaki bütün sözcükler için dön.
		for (final String sözcüğü : paragrafınSözcükleri) {
			// Sözcük boşsa atla. Böylece birden fazla boşluk koyulduysa bunları
			// yok sayar.
			if (sözcüğü.isEmpty())
				continue;
			
			// Bir boşluk ve bu sözcüğü ekledikten sonraki uzunluğu bul.
			double yeniUzunluğu = satırınUzunluğu + 1.0 + sözcüğü.length();
			
			// Eğer bu kelimeyi ekledikten sonra satırın uzunluğu paragrafın
			// genişliğini aşıyorsa eklenen sözcükler ile satırı bitir ve yeni
			// bir satıra geç.
			if (yeniUzunluğu > genişliği) {
				satırıOluştur(
					ilkSatırınınSırası,
					girintisininUzunluğu,
					dayalıOlması,
					satırınSözcükleri);
				satırınSözcükleri.clear();
				
				// Bir sonraki satırın yeni uzunluğunu bul. Bu satırda sadece bu
				// sözcük olacak.
				yeniUzunluğu = sözcüğü.length();
			}
			
			// Sözcüğü baştaki satıra ya da sınırı geçmişse yeni satıra ekle.
			satırınSözcükleri.add(sözcüğü);
			satırınUzunluğu = yeniUzunluğu;
		}
		
		// Son kalan sözcüklerle dayalı olmayan bir satır oluştur.
		satırıOluştur(
			ilkSatırınınSırası,
			girintisininUzunluğu,
			false,
			satırınSözcükleri);
	}
	
	/* Verilen sözcüklerle bir paragraf satırını nesnesi oluşturur ve bunu
	 * paragrafa ekler. */
	private void satırıOluştur(
		final int ilkSatırınınsırası,
		final double girintisininUzunluğu,
		final boolean dayalıOlması,
		final List<String> satırınSözcükleri) {
		// Paragraf satırı nesnesini tanımla ve eğer paragrafın ilk satırıysa
		// girintisinin uzunluğunu ver.
		final ParagrafSatırı satırı = new ParagrafSatırı(satırınSözcükleri);
		satırı
			.uzunluklarınıBul(
				satırları.size() == ilkSatırınınsırası ?
					girintisininUzunluğu :
					0.0,
				genişliği,
				dayalıOlması);
		
		// Paragrafı ekle.
		satırları.add(satırı);
	}
}
