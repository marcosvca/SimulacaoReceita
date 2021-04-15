package sync.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	public static List<String> extrairCaminhoDeArquivoDosArgumentos(String... args) {
		List<String> fileList = new ArrayList<String>();
		for (String arg : args) {
			if (!(arg.startsWith("-"))) {
				fileList.add(arg);
			}
		}
		return fileList;
	}

	/**
	 **
	 * Retorna um objeto de leitura do tipo BufferedReader para leitura do arquivo
	 * conforme caminho recebido como parâmetro
	 *
	 * @param caminhoDoArquivo do arquivo desejado
	 * @return um objeto de leitura do tipo BufferedReader
	 * @throws IOException caso ocorra um erro ao encontrar ou montar o arquivo.
	 */
	public static BufferedReader getFileReader(String caminhoDoArquivo) throws IOException {
		Path path = Paths.get(caminhoDoArquivo);
		BufferedReader reader = Files.newBufferedReader(path);
		return reader;
	}

	/**
	 **
	 * Retorna um objeto para escrita de arquivo do tipo BufferedWriter conforme
	 * caminho recebido como parâmetro
	 *
	 * @param caminhoDoArquivo do arquivo desejado
	 * @return um objeto de escrita do tipo BufferedWriter
	 * @throws IOException caso ocorra um erro ao encontrar ou montar o arquivo.
	 */
	public static BufferedWriter getFileWriter(String caminhoDoArquivo) throws IOException {
		Path path = Paths.get(caminhoDoArquivo);
		BufferedWriter writer = Files.newBufferedWriter(path);
		return writer;
	}

	public static void writeLine(BufferedWriter fileWriter, String content) throws IOException {
		fileWriter.write(content);
		fileWriter.newLine();
	}
}
