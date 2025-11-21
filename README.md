# ♟️ Chess API

API REST desenvolvida para gerenciamento completo de jogadores, partidas e torneios de xadrez.  
Permite cadastro de jogadores, controle de partidas, atualização de PGN, organização de torneios e aplicação de regras de negócio específicas do domínio enxadrístico.

---

## 📑 Sumário

- [Endpoints](#-endpoints)  
  - [Players](#-players)  
  - [Matches](#-matches)  
  - [Tournaments](#-tournaments)  
- [Regras de Negócio](#-regras-de-negócio)  
- [Observações Técnicas](#-observações-técnicas)  

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
