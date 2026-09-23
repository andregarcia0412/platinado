# Platinado

![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?logo=springsecurity&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-migrations-CC0200?logo=flyway&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-7-DC382D?logo=redis&logoColor=white)
![MinIO](https://img.shields.io/badge/MinIO-S3-C72E49?logo=minio&logoColor=white)
![Angular](https://img.shields.io/badge/Angular-22-DD0031?logo=angular&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-6.0-3178C6?logo=typescript&logoColor=white)
![Tailwind CSS](https://img.shields.io/badge/Tailwind%20CSS-4-06B6D4?logo=tailwindcss&logoColor=white)
![Docker](https://img.shields.io/badge/Docker%20Compose-infra-2496ED?logo=docker&logoColor=white)
![OpenAPI](https://img.shields.io/badge/OpenAPI-Swagger%20UI-85EA2D?logo=swagger&logoColor=black)

## Propósito

O Platinado é uma estante virtual de jogos: um sistema onde cada pessoa registra os jogos que jogou, está jogando ou pretende jogar, podendo compartilhar status de conclusão, horas jogadas, datas de início e término, nota e anotações
pessoais.

A ideia é reunir em um lugar só o histórico de jogos que normalmente fica espalhado entre plataformas diferentes, servindo tanto como diário quanto como vitrine da
coleção.

Cada jogo terá também uma nota com base nas notas agregadas dos jogadores. A ideia para evolução do projeto é que seja possível adicionar diversos jogos já existentes ao banco de dados, para que os usuários não precisem se preocupar em adicionar jogos manualmente.

## Funcionalidades atuais

O projeto está em desenvolvimento. Hoje funcionam:

- **Cadastro e login** com token de acesso (15 min) e refresh token (7 dias) de uso
  único, com revogação no logout.
- **Renovação automática de sessão** no frontend: o interceptor detecta o `401`,
  renova o token e repete a requisição.
- **Gestão de conta**: consulta, atualização parcial e remoção de usuário, com campos de bio e avatar.
- **Catálogo de jogos**: criação, listagem, busca por slug, atualização parcial e remoção, com tipos de jogo pré-carregados (jogo principal, DLC, expansão, remake, port, entre outros).
- **Documentação interativa da API** via Swagger UI.
- **Telas de autenticação** (login, cadastro e recuperação de senha) e uma versão inicial da home.


## Padrões GoF Utilizados:
  - **Observer** - Para loggar no console toda vez que um usuário era criado, deletado ou atualizado
  - **Factory** - Para gerênciar a criação de formulários de autenticação (login, cadastro e esqueci minha senha)
  
## Padrões GRASP Utilizados:
  - **Controller** - Para concentrar a entrada das requisições HTTP em um único ponto por módulo, ele faz a validação da entrada e delega o resto para o service.
  - **High Cohesion** - Com separação do backend em módulos e com as responsabilidades de cada classe bem delimitadas
  - **Low Coupling** - Com separação de funcionalidades por camada e utilização de interfaces para serviços, repositórios e providers.

### Em modelagem

O banco já contempla estruturas ainda não expostas pela API: vínculo usuário-jogo com
progresso, gêneros, empresas (desenvolvedora, publicadora, porte e suporte) e status
de conclusão.

## Principais usuários

- **Jogadores que gostam de registrar o que jogam** - o público central: jogadores que querem manter um histórico organizado, com notas e tempo de jogo.
- **Caçadores de platina e completionistas** - acompanham o quanto falta para concluir cada jogo do que possuem.
- **Visitantes** - podem navegar pelo catálogo de jogos sem precisar de conta.

## Arquitetura

```
backend/   Spring Boot - módulos (auth, user, game) em camadas
           controller → service → use case → repository,
           com providers compartilhados de cache (Redis), storage (S3) e JWT

frontend/  Angular standalone - features (auth, home) e shared (componentes, erros)
```

O backend segue uma organização em módulos com interfaces para cada serviço, repositório e provider, um caso de uso por operação e tratamento de erros centralizado.

## Como rodar

**Infraestrutura** (MySQL, Redis e MinIO):

```bash
cd backend
cp .env.example .env   # preencha as variáveis
docker compose up -d
```

**Backend** (as migrations do Flyway rodam na inicialização):

```bash
cd backend
./mvnw spring-boot:run
```

Documentação da API: `http://localhost:8080/swagger-ui.html`

**Frontend:**

```bash
cd frontend
npm install
npm start
```
