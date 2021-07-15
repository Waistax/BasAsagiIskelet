/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.1 / 3 Mar 2021 / 09:06:21
 */
package başaşağıderebeyi.iskelet;

import java.lang.annotation.*;

/** İskeletin çalıştırabildiği bir uygulama. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Uygulama {
	/** Uygulamanın özgün adı. Bu ad bir Java tanımlayıcısı gibi sadece
	 * abecesayısal karakterler ve altçizgi içermelidir. */
	public String adı();
}
