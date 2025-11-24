# ♟️ Chess API

API REST desenvolvida para gerenciamento completo de jogadores, partidas e torneios de xadrez.  
Permite cadastro de jogadores, controle de partidas, atualização de PGN, organização de torneios e aplicação de regras de negócio específicas do domínio enxadrístico.

---

## 📑 Sumário

- [Endpoints](#-endpoints)  
- [Players](#-players)  
- [Matches](#-matches)  
- [Tournaments](#-tournaments)  
- [Regras de Negócio](#-regras-de-negocio)  
- [Observações Técnicas](#-observações-tecnicas)
- [Como executar o projeto localmente](#como-executar-o-projeto-localmente)

---

## 🚀 Endpoints

### 👤 Players

| Método | Rota | Descrição | Corpo/Parâmetros |
|--------|-------|------------|------------------|
| **POST** | `/players` | Criar jogador | `PlayerCreateDto` |
| **GET** | `/players` | Listar jogadores | — |
| **GET** | `/players/{id}` | Consultar jogador | — |
| **PATCH** | `/players/{id}` | Atualizar dados básicos | `PlayerUpdateDto` |
| **PATCH** | `/players/{id}/activate` | Ativar jogador | — |
| **PATCH** | `/players/{id}/deactivate` | Desativar jogador | — |

---

### 🎲 Matches

| Método | Rota | Descrição | Corpo/Parâmetros |
|--------|-------|------------|------------------|
| **POST** | `/matches` | Criar partida (jogadores devem estar ativos e disponíveis) | `MatchCreateDto` |
| **GET** | `/matches` | Listar partidas | — |
| **GET** | `/matches/{id}` | Consultar partida | — |
| **PATCH** | `/matches/{id}/pgn` | Atualizar PGN (se não finalizada) | `MatchPgnDto` |
| **PATCH** | `/matches/{id}/finish` | Finalizar partida (rating não muda se for de torneio) | `MatchFinishDto` |
| **DELETE** | `/matches/{id}` | Cancelar partida não finalizada | — |
| **GET** | `/matches/player/{id}` | Histórico de partidas por jogador | — |

---

### 🏆 Tournaments

| Método | Rota | Descrição | Corpo/Parâmetros |
|--------|-------|------------|------------------|
| **POST** | `/tournaments` | Criar torneio (owner deve estar ativo) | `TournamentCreateDto` |
| **GET** | `/tournaments` | Listar torneios | — |
| **GET** | `/tournaments/{id}` | Consultar torneio | — |
| **PATCH** | `/tournaments/{id}/join` | Inscrever jogador (status: PLANNED) | `playerId` (query) |
| **PATCH** | `/tournaments/{id}/start` | Iniciar torneio (mínimo de 3 inscritos) | — |
| **PATCH** | `/tournaments/{id}/finish` | Finalizar torneio (deve estar ONGOING e winnerId deve ser inscrito) | `TournamentFinishDto` |
| **GET** | `/tournaments/{id}/matches` | Listar partidas vinculadas a um torneio | — |
| **DELETE** | `/tournaments/{id}` | Excluir torneio (somente se sem participantes) | — |

---

## 📌 Regras de Negócio

- Jogadores não podem estar envolvidos simultaneamente em mais de uma partida ativa.  
- Jogadores inativos não podem participar de partidas nem administrar torneios.  
- Partidas de torneio não geram alteração no rating dos jogadores.  
- Partidas de torneio só podem ser criadas em torneios com status **ONGOING**.  
- Torneios só podem ser iniciados com **mínimo de 3 participantes** ativos.  
- Torneios só podem ser finalizados se estiverem **ONGOING** e o vencedor informado for um jogador inscrito.

---

## ⚙️ Observações Técnicas

- IDs utilizam **UUID**.
- Arquitetura recomendada: **Spring Boot + Spring Web + JPA + Validation**.  
- Repositórios com Spring Data JPA.  
- Tratamento de erros via exceções customizadas e `@ControllerAdvice`.  
- Classes separadas em camadas (`controller`, `service`, `repository`, `model`, `dto` etc.).  

---

## Como executar o projeto localmente

Para rodar esta API, é necessário ter Redis em execução. A aplicação depende dele para gerenciar o cache de consultas.

### Pré-requisitos

- Java 17+
- Maven
- Docker Desktop instalado e em execução
- Porta 8080 livre para a API
- Porta 6379 livre para o Redis

### 1. Inicie o Redis usando Docker

Com o Docker aberto, execute no terminal:

```bash
docker run --name redis-cache -p 6379:6379 -d redis
```
Isso cria e inicia um servidor Redis local na porta 6379.

### 2. Clone o repositório

```bash
git clone https://github.com/daviChechetto/backend-projeto-final
```

### 3. Execute a API

```bash
mvn spring-boot:run
```
A API iniciará em: http://localhost:8080 e com o Redis rodando, o sistema ativa automaticamente o cache para os endpoints configurados.

## Para limpar o cache

No terminal digite:
```bash
docker exec -it redis-cache redis-cli
FLUSHALL
exit
```
