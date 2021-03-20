/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.10.1 / 19 Mar 2021 / 09:18:15
 */

/** Baş Aşağı Derebeyi'nin uygulamalarının iskeleti. */
module başaşağıderebeyi.iskelet {
	exports başaşağıderebeyi.iskelet;
	
	exports başaşağıderebeyi.iskelet.girdi;
	
	exports başaşağıderebeyi.iskelet.görsel;
	exports başaşağıderebeyi.iskelet.görsel.köşedizisi;
	exports başaşağıderebeyi.iskelet.görsel.yazı;
	
	exports başaşağıderebeyi.iskelet.olaylar;
	
	requires transitive başaşağıderebeyi.kütüphane;
	
	requires transitive org.lwjgl.natives;
	requires transitive org.lwjgl.glfw.natives;
	requires transitive org.lwjgl.opengl.natives;
	requires transitive org.lwjgl.openal.natives;
	
	requires java.desktop;
}
