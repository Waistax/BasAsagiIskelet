/**
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 * 0.2 / 3 Mar 2021 / 21:46:14
 */
package başaşağıderebeyi.iskelet.girdi;

/** Fare tekerleğiyle girdi verilmesinin olayı. Bu girdi olayı tekrar tekrar
 * kullanılır. Böylece her olayda yeni bir nesne oluşturulmamış olur. */
public class TekerlekGirdisiOlayı extends GirdiOlayı {
	/** Fare tekerleğinin döndüğü devir. */
	public double devri;
}
