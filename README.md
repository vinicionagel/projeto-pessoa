

<h3>Teste:</h3>
```
A demanda
Deverá ser criada uma aplicação de cadastro de pessoas:
1) Back-end
   A aplicação, a ser desenvolvida em Java, deverá expor uma API de cadastro, alteração, remoção e consulta de pessoas com as seguintes informações:
   Nome - obrigatório
   Sexo
   E-mail - não obrigatório, deve ser validado caso preenchido
   Data de Nascimento - obrigatório, deve ser validada
   Naturalidade
   Nacionalidade
   CPF - obrigatório, deve ser validado (formato e não pode haver dois cadastros com mesmo cpf)
   Obs: a data de cadastro e atualização dos dados devem ser armazenados.
2) Front-end
   A aplicação deverá ser acessível via navegador e possuir uma tela com formulário. Não há restrição em relação à tecnologia para o desenvolvimento do frontend.
3) Segurança
   O acesso à aplicação só poderá ser realizado por um usuário pré-existente via autenticação basic.
4) Instalação
   A aplicação deverá estar disponível em uma imagem docker a partir do docker-hub e não deve exigir configurações/parâmetros. Ou seja, ao rodar a imagem, deve levantar a aplicação e funcionar.
5) Código fonte
   A aplicação deverá possuir um endpoint /source acessível sem autenticação via HTTP GET que deverá retornar o link do projeto no github com o código fonte do projeto desenvolvido.
   Importante 1: O projeto deverá ter testes unitários da aplicação.
   Importante 2: O projeto deverá utilizar as versões mais recentes das tecnologias/frameworks selecionados.
```
<h3>Para executar o projeto no terminal, digite o seguinte comando:<h3>

```shell script
mvn spring-boot:run 
```

<h3>Para executar a suíte de testes desenvolvida:<h3>

```shell script
mvn clean test
```

Após executar o comando acima, basta apenas abrir o seguinte endereço e visualizar a execução do projeto:

```
http://localhost:8080/api/v1/pessoas
```

<h3>Para subir a aplicação com docker:<h3>

```
docker-compose up
```

<h3> Documentação da API: </h3>

http://localhost:8080/swagger-ui.html#/


