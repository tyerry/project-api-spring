## CRUD API com Spring Framework e Postgres

------------

> Bem-vindo ao projeto CRUD API RESTful desenvolvido em Java e usando o Spring Framework.

##### Visão Geral
Este projeto consiste em uma API para cadastro de usuários, contendo os campos "nome", "e-mail" e "senha".

------------

##### Tecnologias Utilizadas
###### Spring Framework
O Spring é um framework abrangente para Java, oferecendo suporte a uma variedade de funcionalidades e proporcionando uma estrutura MVC robusta.

###### Spring Security
Com o Spring Security criptografamos as senhas antes de serem armazenadas no banco, garantindo a proteção das informações sensíveis.

###### Docker
Ambiente de desenvolvimento simplificado com Docker. Nosso Dockerfile inclui Maven, facilitando o gerenciamento de dependências, e o docker-compose inicia o banco Postgres e a API com um único comando.

###### Postgres
Armazenamento eficiente com banco de dados Postgres. A utilização do Docker torna o processo de inicialização do banco simples e consistente.

###### Swagger
Facilitamos a interação com a API por meio do Swagger, oferecendo uma interface amigável.

###### Spring Data JPA
Utilizamos o Spring Data JPA para simplificar a camada de persistência, permitindo a interação com o banco de dados de forma mais fácil e intuitiva.

------------

##### Estrutura do Projeto
O padrão MVC (Model-View-Controller) é uma abordagem de design comum em desenvolvimento de software para separar as preocupações e organizar o código de uma maneira mais modular.

###### controller
Contém os controladores responsáveis por receber as requisições HTTP e interagir com os serviços.

###### repository
Armazena as interfaces do Spring Data JPA para interação com o banco de dados, abstraindo a persistência de dados.

###### service
Contém a lógica de negócios, manipulando dados antes de serem salvos ou após serem recuperados do banco.

###### model
Armazena as entidades que representam os objetos de domínio, incluindo Data Transfer Objects (DTO) para uma comunicação eficiente.

------------

##### Como Rodar o Projeto
Para executar este projeto, certifique-se de ter o Docker instalado e o serviço inicializado antes de seguir os passos abaixo:

1. Clone o repositório.
2. Abra o terminal na raiz do projeto.
3. Execute o comando ` docker compose up`.
4. Acesse http://localhost:8080/swagger-ui/index.html para testar a API.
