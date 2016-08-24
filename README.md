# RedeDoar-Android

Rede Doar consiste em um projeto social para criação de um aplicativo Open Source que possibilita os usuários contribuírem com a sociedade compartilhando por meio de doação ou buscando doações de roupas, sobras de alimentos ou móveis. 

## Requisitos

O projeto é estruturado utilizando Android Studio, versão 2.0+ e funciona sobre as plataformas Android com SDK 15+.

## Instalação

Simples, basta clonar o código fonte e abrir a aplicação no Android Studio. Caso não como configurar o SDK Android, visite a página oficial da aplicação (será bem simples).

## Estrutura do Projeto

O projeto está estruturado por pacotes Java, onde temos:

1. **`managers`** : pacotes com esturturas básicas a serem inseridas, até o momento a aplicação base e uma activity padrão a ser utilizada no decorrer da aplicação

2. **`models`**: POJOS e objetos responsáveis pela modelagem estrutural dos elementos básicos de CRUD e/ou retorno de APIs Restful

3. **`network`**: estrutura de comunicação com a web

4.  **`utils`**: classes utilizadas com frequência na aplicação, p.e: SharedPreferences

5. **`views`**: estrutura padrão de activities, fragments, adapters, widgets e notificadores de push

## LICENSE
MIT © [MIT](LICENSE)