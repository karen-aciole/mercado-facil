# Mercado Fácil
 
Um supermercado da cidade de Campina Grande precisa de um sistema que gerencie o estoque e venda de produtos na sua loja. Neste sistema, o administrador deve obter uma visão geral e o controle sobre o funcionamento do supermercado, por exemplo, ele deve poder adicionar novos produtos, acompanhar quantas unidades do produto estão disponíveis, alterar preços, ser notificado sobre eventos críticos, gerenciar as vendas e oferecer alguns serviços personalizados para o cliente.

## User Stories já implementadas

- Eu, como administrador, gostaria de adicionar um novo produto no sistema, informando seu nome, fabricante e preço;
- Eu, como usuário, gostaria de consultar as informações de um produto específico do supermercado;
- Eu, como usuário, gostaria de consultar todos os produtos do catálogo do supermercado.
- Como usuário administrador (responsável) do sistema, desejo cadastrar novos usuários a partir de informações sobre CPF, Nome e Endereço
- Como usuário administrador (responsável) do sistema, desejo atualizar as informações de Endereço ou Telefone de um usuário a partir do seu identificador
- Como usuário administrador (responsável) do sistema, desejo remover um usuário do sistema a partir do seu identificador
- Como usuário administrador (responsável) do sistema, desejo acessar as informações de cadastro um usuário do sistema a partir do seu identificador
- Como usuário administrador (responsável) do sistema, desejo listar as informações de CPF e Nome de todos os usuários cadastrados no sistema.
- Como cliente do sistema, desejo acessar o endpoint do Mercado Fácil para adicionar um produto do catálogo no carrinho de compras, para que seja possível realizar uma compra
- Como cliente do sistema, desejo acessar o endpoint do Mercado Fácil para remover um produto do carrinho de compras, para que seja possível desconsiderar o produto da compra
- Como cliente do sistema, desejo acessar o endpoint do Mercado Fácil para finalizar uma compra (carrinho de compra), para que seja possível efetuar a compra dos produtos do carrinho
- Como cliente do sistema, desejo acessar o endpoint do Mercado Fácil para descartar uma compra (carrinho de compra), para que seja possível desistir da compra dos produtos do carrinho
- Como cliente do sistema, desejo acessar o endpoint do Mercado Fácil para listar minhas compras que foram finalizadas, para que seja possível ver o meu histórico de compras. 
- Como cliente do sistema, desejo acessar o endpoint do Mercado Fácil para ver o descritivo de uma compra, para que seja possível ver detalhes de uma compra realizada.
- Como administrador do sistema Mercado Fácil, quero que o sistema disponibilize formas de pagamento (boleto, paypal e cartão) com acréscimos percentuais no valor da compra, para que as vendas sejam compatíveis com os custos operacionais de cada tipo de operação financeira
- Como cliente do sistema Mercado Fácil, desejo acessar o endpoint do Mercado Fácil para listar as formas de pagamento disponíveis no sistema, para que seja possível realizar o pagamento de uma compra.
- Como cliente do sistema Mercado Fácil, quero poder definir a forma de pagamento (boleto, paypal e cartão) ao realizar uma compra, para que seja possível pagar a compra da forma mais conveniente. 
- Como administrador do sistema Mercado Fácil, quero poder estabelecer perfis de cliente (normal, especial e premium), para possibilitar que descontos diferenciados sejam aplicados nas compras dos clientes. Os descontos associados a cada perfil de cliente são definidos da seguinte forma:  O usuário normal não tem desconto; O usuário especial tem um desconto de 10% para compras com mais de 10 produtos no total; O usuário premium tem um desconto de 10% para compras com mais de 5 produtos no total



## Estrutura básica

- Um projeto: MercadoFacil;
- Um Controller ProdutoController que implementa os endpoints da API Rest relacionados a operações com produtos.
- Dois repositórios são utilizados: ProdutoRepository e LoteRepository, que são responsáveis por manipular as entidades Produto e Lote em um catálogo (Mapa);
- O modelo é composto pelas classes Produto.java e Lote.java que podem ser encontradas no pacote model;
- O pacote exceptions guarda as classes de exceções que podem ser lançadas dentro do sistema;
- Não há implementação de frontend, mas o projeto fornece uma interface de acesso à API via swagger.

## Tecnologias
Código base gerado via [start.sprint.io](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.3.3.RELEASE&packaging=jar&jvmVersion=1.8&groupId=com.example&artifactId=EstoqueFacil&name=EstoqueFacil&description=Projeto%20Estoque%20Facil&packageName=com.example.EstoqueFacil&dependencies=web,actuator,devtools,data-jpa,h2) com as seguintes dependências:  

- Spring Web
- Spring Actuator
- Spring Boot DevTools

## Endereços úteis

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Contato e Dúvidas

- fabio@computacao.ufcg.edu.br
