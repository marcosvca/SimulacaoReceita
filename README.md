<h1 align="center">Processo Seletivo Sicredi</h1>
<h2> Etapa - Teste Técnico</h2>
<h2>Candidato: Marcos Vinícius Campos Alves</h2>
<hr>

<h3> Cenário de Negócio </h3>
Todo dia útil por volta das 6 horas da manhã um colaborador da retaguarda do Sicredi recebe e organiza as informações de 
contas para enviar ao Banco Central. Todas agencias e cooperativas enviam arquivos Excel à Retaguarda. Hoje o Sicredi 
já possiu mais de 4 milhões de contas ativas.
Esse usuário da retaguarda exporta manualmente os dados em um arquivo CSV para ser enviada para a Receita Federal, 
antes as 10:00 da manhã na abertura das agências.

Requisito:
Usar o "serviço da receita" (fake) para processamento automático do arquivo.

Funcionalidade:
0. Criar uma aplicação SprintBoot standalone. Exemplo: java -jar SincronizacaoReceita <input-file>
1. Processa um arquivo CSV de entrada com o formato abaixo.
2. Envia a atualização para a Receita através do serviço (SIMULADO pela classe ReceitaService).
3. Retorna um arquivo com o resultado do envio da atualização da Receita. Mesmo formato adicionando o resultado em uma 
nova coluna.


Formato CSV:
<pre>
agencia;conta;saldo;status
0101;12225-6;100,00;A
0101;12226-8;3200,50;A
3202;40011-1;-35,12;I
3202;54001-2;0,00;P
3202;00321-2;34500,00;B
...
</pre>

<h3>Artefatos</h3>
Com objetivo de cobrir todas as funcionalidades descritas previamente, e também as etapas da avaliação técnica, foram produzidos 
códigos fontes em JAVA, armazenados neste repositório, e a partir destes, foi gerado o artefato JAR de nome SincronizacaoReceita.jar localizado na <bold>diretório build</bold>.

A execução segue o padrão:
<pre>java -jar SincronizacaoReceita.jar {input-file}</pre>

Os arquivos de saída são gerados com mesmo nome e caminho dos de entrada com a extensão .out

