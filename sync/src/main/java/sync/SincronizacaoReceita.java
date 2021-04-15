package sync;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sync.service.ReceitaService;
import sync.util.CsvUtil;
import sync.util.FileUtil;

@SpringBootApplication
public class SincronizacaoReceita implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(SincronizacaoReceita.class);

	// TODO: Receber o delimitador como argumento
	private static char delimiter = ';';

	public static void main(String[] args) {
		logger.info("Argumentos: ");
		for (String arg : args) {
			logger.info(arg.toString());
		}
		SpringApplication.run(SincronizacaoReceita.class, args);
	}

	@Bean
	public ReceitaService getReceitaService() {
		return new ReceitaService();
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Processando entrada...");
		List<String> filePaths = FileUtil.extrairCaminhoDeArquivoDosArgumentos(args);
		filePaths.forEach(filePath -> processaArquivo(filePath));
		logger.info("Processo finalizado");
	}

	/**
	 * Processa o arquivo de entrada, efetuando a leitura e o mapeamento dos
	 * valores, que são enviados para atualização de receita. As saídas são
	 * armazenadas em arquivo com final .out.
	 * 
	 * @param filePath do arquivo de entrada
	 */
	private void processaArquivo(String filePath) {
		logger.info("Arquivo de entrada: {}", filePath);
		try {

			BufferedReader fileReader = FileUtil.getFileReader(filePath);
			String inputHeader = fileReader.readLine();
			Map<String, Integer> indexMap = CsvUtil.mapHeaderIndex(inputHeader, delimiter);

			String writeFilePath = filePath.concat(".out");
			BufferedWriter fileWriter = FileUtil.getFileWriter(writeFilePath);
			String outputHeader = this.getOutputHeader(inputHeader);
			logger.info(outputHeader);
			FileUtil.writeLine(fileWriter, outputHeader);

			String row = null;
			while ((row = fileReader.readLine()) != null) {
				List<String> rowValues = CsvUtil.getRowAsList(row, delimiter);

				String agencia = rowValues.get(indexMap.get("agencia"));

				// TODO: Formatar conta conforme máscara
				String conta = rowValues.get(indexMap.get("conta")).replace("-", "");

				// TODO: Formatar e converter número utilizando a região padrão
				Double saldo = Double.parseDouble(rowValues.get(indexMap.get("saldo")).replace(",", "."));

				String status = rowValues.get(indexMap.get("status"));

				boolean resultado = atualizar(agencia, conta, saldo, status);

				String outputRow = CsvUtil.buildRowAsString(delimiter, agencia, rowValues.get(indexMap.get("conta")),
						rowValues.get(indexMap.get("saldo")), status, resultado);
				logger.info(outputRow);
				FileUtil.writeLine(fileWriter, outputRow);
			}
			logger.info("Arquivo de saída: {}", writeFilePath);
			fileWriter.close();
			fileReader.close();
		} catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
			logger.error("Arquivo com formatação inválida.");
		} catch (IOException e) {
			logger.error("Erro de entrada ou saída do sistema");
			e.printStackTrace();
		}
	}

	private boolean atualizar(String agencia, String conta, Double saldo, String status) {
		do {
			try {
				return getReceitaService().atualizarConta(agencia, conta, saldo, status);
			} catch (RuntimeException | InterruptedException e) {
				logger.info("Erro no serviço - Repetindo requisição...");
			}
			// TODO: Adicionar delay entre as repetições (se necessário)
		} while (true);
	}

	private String getOutputHeader(String header) {
		return header + ";" + "resultado";
	}
}
