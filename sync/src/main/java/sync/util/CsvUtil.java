package sync.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvUtil {
	/**
	 * Cria um mapa para os índices de cada coluna do cabeçalho.
	 * 
	 * @param header    linha do cabeçalho
	 * @param delimiter delimitador dos valores
	 * @return um mapa com os índices de cada coluna do cabeçalho
	 */
	public static Map<String, Integer> mapHeaderIndex(String header, char delimiter) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		if (header != null) {
			header = header.replace("\"", "");
			List<String> items = getRowAsList(header, delimiter);
			Integer columnCounter = 0;
			for (String str : items) {
				map.put(str.trim().toLowerCase(), (Integer) columnCounter++);
			}
		}
		return map;
	}

	/**
	 * Transforma uma String de valores delimitados em uma lista.
	 * 
	 * @param row       String de valores delimitados
	 * @param delimiter delimitador dos valores
	 * @return uma lista dos valores
	 */
	public static List<String> getRowAsList(String row, char delimiter) {
		List<String> list = null;
		if (row != null) {
			row = row.replace("\"", "");
			list = Arrays.asList(row.split("" + delimiter));
		}
		return list;
	}

	/**
	 * Constrói uma string de valores delimitados a partir de uma lista de argumetos
	 * 
	 * @param args
	 * @return
	 */
	public static String buildRowAsString(char delimiter, Object... args) {
		StringBuilder row = new StringBuilder();
		for (Object arg : args) {
			row.append(arg).append(delimiter);
		}
		if (!row.toString().isEmpty()) {
			row.deleteCharAt(row.lastIndexOf("" + delimiter));
		}
		return row.toString();
	}

}
