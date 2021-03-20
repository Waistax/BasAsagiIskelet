/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.12.2 / 20 Mar 2021 / 22:25:56
 */
package başaşağıderebeyi.iskelet.görsel.kaynak;

import static org.lwjgl.system.MemoryUtil.*;

import java.nio.*;
import java.util.*;

import org.lwjgl.system.*;

class TamponYükleyicisi {
	private final List<Buffer> tamponlar;
	
	TamponYükleyicisi() {
		tamponlar = new ArrayList<>();
	}
	
	void yokEt() {
		tamponlar.forEach(MemoryUtil::memFree);
		tamponlar.clear();
	}
	
	FloatBuffer yükle(final int uzunluğu) {
		final FloatBuffer tampon = memAllocFloat(uzunluğu);
		tamponlar.add(tampon);
		return tampon;
	}
}
