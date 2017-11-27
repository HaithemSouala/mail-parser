package fr.gfi.poc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class Parser {

	private static final String TABLE_LIGNES_DE_DISTRIBUTIONS = "Lignes de distributions";

	private static final String TABLE_DATA = "summary";

	private static final String TAG_TABLE = "table";

	private static final String PATH_MAIL = "src/main/resources/mail.txt";

	private static final List<String> ELEMENTS_LINKS_APPROBATION = Arrays.asList("Approuver", "Rejeter");

	private static final List<String> ELEMENTS_TABLE_HEADER = Arrays.asList("Envoyé", "Attendu", "ID", "A",
			"Nom du fournisseur", "Numéro de facture", "Date de la facture", "Total facture",
			"Description de la facture", "Devise de la facture");

	private static final List<String> ELEMENTS_TABLE_DETAILS = Arrays.asList("Cost", "ASIASSI", "N° Ligne", "Compte");

	/**
	 * @throws IOException
	 */
	public static void parser() throws IOException {

		String html = readFile(PATH_MAIL, StandardCharsets.ISO_8859_1);

		String safeHtml = Jsoup.clean(html, Whitelist.relaxed());
		Document _doc = Jsoup.parse(safeHtml);

		final Elements _table_actions = _doc.select(TAG_TABLE);
		final Elements _table_lignes = _doc.getElementsByAttributeValue(TABLE_DATA, TABLE_LIGNES_DE_DISTRIBUTIONS);

		ELEMENTS_TABLE_HEADER.parallelStream().forEach(el -> {
			Element _td = _table_actions.first().getElementsMatchingOwnText(el).first();
			System.out.println(String.format("%s: %s", el, _td.parent().parent().lastElementSibling().text()));
		});

		ELEMENTS_TABLE_DETAILS.parallelStream().forEach(el -> {
			Element _tdx = _table_lignes.first().getElementsMatchingOwnText(el).first().parent().parent().parent();
			Element _tbody = _tdx.parent().parent();
			int index = _tdx.elementSiblingIndex();
			Element e = _tbody.child(2).getElementsByIndexEquals(index).first();
			System.out.println(String.format("%s: %s", el, e.text()));
		});

		ELEMENTS_LINKS_APPROBATION.parallelStream().forEach(el -> {
			Element _link = _doc.getElementsMatchingOwnText(el).first().parent().parent();
			System.out.println(String.format("%s: %s", el, _link.select("a").attr("href")));
		});

	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}
