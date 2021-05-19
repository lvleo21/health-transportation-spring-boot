<h1 align="center">Health Transportation</h1>

<p align="center" id="descricao">Sistema utilizado para organização e gerenciamento das viagens e seus respectivos processos,  referentes ao programa de <b>Tratamento Fora de Domicílio (TFD)</b>. </p>

<h2> Sumário</h2>

- 🛠 [Ferramentas e Frameworks](#-ferramentas-e-frameworks)

* 💻 [Rodando em sua máquina](#-rodando-em-sua-máquina) 

* 📃 [Documentação](https://github.com/lvleo21/PBD_20-1_Leonardo-Veras/blob/main/Documenta%C3%A7%C3%A3o%20e%20Artefatos/1%C2%AA%20V.A/PBD%20-%20Documenta%C3%A7%C3%A3o.pdf)

* 📊 [Diagrama](https://github.com/lvleo21/PBD_20-1_Leonardo-Veras/blob/main/Documenta%C3%A7%C3%A3o%20e%20Artefatos/1%C2%AA%20V.A/Diagrama.png)

* 💾 [Backup da Base de Dados](#markdown-header-images)

* 📹 [Vídeo de Apresentação](https://ufrpebr-my.sharepoint.com/:f:/g/personal/leonardo_mascena_ufrpe_br/EiK5PiTMicBClDRB5p_jCtsBYjsLDb6EtEzcmTgDSOLHDg?e=m5YvqG)
  
* ⚠️ [Observações](#-observações)


<br>


## 🛠 Ferramentas e Frameworks

<ul>
    <li>Desenvolvido em: <b>Java (versão 11)</b></li>
    <li>Framework: <b>Spring Boot (versão 2.4.5)</b></li>
    <li>Template engine: <b>Thymeleaf</b></li>
    <li>SGBD: <b>PostgreSQL</b></li>
    <li>IDE: <b>IntelliJ IDEA Ultimate Edition</b></li>
    
</ul>  

## 💻 Rodando em sua máquina

0. Clonar ou baixar (.zip) o repositório no [github](https://github.com/lvleo21/PBD_20-1_Leonardo-Veras);
    
    ```
    # Para clonar basta inserir o comando no terminal (linux)
    # Atente para a pasta onde estiver clonado !

    git clone https://github.com/lvleo21/PBD_20-1_Leonardo-Veras.git
    ```

1. Abra o **IntelliJ IDEA Ultimate Edition** (IDE);
2. Clique no botão **Open** (no canto superior direito);
3. Navegue até a pasta onde você clonou ou baixou o repositório;
4. Selectione a pasta **PBD_20-1_Leonardo-Veras** e depois em Ok;

5. Selecione **Trust Project**, a seguir:
  
    ![2](./Documentação%20e%20Artefatos/1ª%20V.A/images/2.png)

6. Em seguida, o projeto será carregado e as dependências do necessárias para o projeto serão baixadas;
   - Aguarde até que não tenha nenhuma barra de loading no canto inferior esquerdo;

7. Agora, abra o arquivo **admin.properties**
     - Se preferir use o comando **ctrl+shift+n** e digite o nome do arquivo que informei.


8. Este arquivo contem dados referêntes ao usuário (username, password, email e name) admin do projeto

    ![3](./Documentação%20e%20Artefatos/1ª%20V.A/images/3.png)

    ![4](./Documentação%20e%20Artefatos/1ª%20V.A/images/4.png)
      - Inseri alguns dados de exemplo.


9. Em seguida, vá para o arquivo **application.properties**, neste arquivo nos iremos inserir o usuário e senha do Postgre, como também, o nome do database;

   - Guarde o nome do database que você escolheu, logo ele será usado.

    ![5](./Documentação%20e%20Artefatos/1ª%20V.A/images/5.png)

    
    - 🔶 Inseri alguns dados como exemplo.
  
    ![6](./Documentação%20e%20Artefatos/1ª%20V.A/images/6.png)


10. Agora vamos abrir o **PgAdmin 4**
11. Selecione um server e em seguida crie um novo database
    
    ![7](./Documentação%20e%20Artefatos/1ª%20V.A/images/7.png)
    - Lembra do nome que vc escolheu para o database la no arquivo **application.properties** ? ele será inserido no campo database (em vermelho).

12. Agora vá para o arquivo **HealthTransportationApplication.java** (main do nosso projeto) e o execute clicando na setinha verde no canto superior direito.
    ![8](./Documentação%20e%20Artefatos/1ª%20V.A/images/8.png)

13. O usuário admin será adicionado na base de dados, como também, algumas roles (permissões/funções) pré estabelecidas.
14. Por fim, vá para url http://localhost:8080/ e logue com o usuário e senha definidos em **admin.properties**

    ![9](./Documentação%20e%20Artefatos/1ª%20V.A/images/9.png)

    - Você deverá ver esta tela.



## ⚠️ Observações


1. Usuários e Senhas caso esteja utilizando o backup da base de dados;
   
   - Lembrar que esta base já contem um usuário admin, logo ele não será criado no InitialAdmin utilizando as informações contidas em admin.properties

    ```
        GESTOR DE SERRA TALHADA
        usuário: gustavo
        senha: Gustavo1

        GESTOR DE AFOGADOS DA INGAZEIRA
        usuário: mequeirosz
        senha: Mequeirosz1


        ADMIN DO SISTEMA
        usuário: lvleo21
        senha: Leo1027210916


        GESTOR DE CAJAZEIRAS
        usuário: bergO
        senha: Berg123


        OPERADOR DE SERRA TALHADA
        usuário: mariaF
        senha: Maria123
    ```  

2. Lembrar que os dados referentes a emails são fictícios, logo se houver a necessidade de enviar o email para resetar a senha, utilize um email válido (na hora de cadastrar os usuários).