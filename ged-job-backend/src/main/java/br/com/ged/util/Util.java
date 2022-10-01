package br.com.ged.util;

public final class Util {

	private Util() {
		throw new IllegalStateException("Utility class");
	}

	public static String[] identificarDocumentos(String texto) {
		String[] text = null;
		String[] textFormat = null;
		try {
			if(texto.contains("__")) {
				text = texto.split("__");
				textFormat = new String[text.length];
				for (int i = 0; i < text.length; i++) {
					if(text[i].toString().contains("_")) {
						textFormat[i] = text[i].replaceAll("_", " ");
					} else {
						textFormat[i] = text[i];
					}
				}
			} else {
				textFormat = new String[1];
				textFormat[0] = texto.replaceAll("_", " ");
			}
			return textFormat;
		} catch (RuntimeException e) {
			return null;
		}
	}

	public static String nomeArquivo(String nomeArquivo, int index) {
		String textFormat = null;
		String[] text = null;
		try {
			if(nomeArquivo.contains("\\")) {
				nomeArquivo = nomeArquivo.replace("\\", "#");
				text = nomeArquivo.split("#");
				textFormat = text[text.length-index].toString();
			}
			return textFormat;
		} catch (RuntimeException e) {
			return null;
		}
	}
}
