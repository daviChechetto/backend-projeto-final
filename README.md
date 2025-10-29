## 🧩 Projeto Final - API REST  
### Tema: ♟️ Xadrez Inteligente (Chess API)  
---

## 👥 Integrantes
- **Brendon Córdova**  
- **Ígor da Silva Antunes**  
- **Davi Chechetto Westphal**

---

## 🧠 Visão Geral do Projeto

O **Chess API** é um sistema backend RESTful projetado para gerenciar partidas, jogadores e rankings de xadrez.  
A API permitirá o registro e acompanhamento de partidas, controle de usuários, histórico de movimentos e cálculo automático de pontuação.

O objetivo é fornecer uma estrutura sólida e escalável que possa ser utilizada por sites, aplicativos ou plataformas que gerenciam torneios e jogos de xadrez.

---

## ⚙️ Principais Funcionalidades da API

| Tipo | Funcionalidade | Descrição |
|------|----------------|------------|
| **Autenticação** | Registro e login de jogadores | Criação de conta, login com JWT e gerenciamento de sessões seguras. |
| **Gerenciamento de Jogadores** | CRUD completo de jogadores | Cadastrar, listar, atualizar e excluir perfis de jogadores. |
| **Partidas** | Registro e acompanhamento de partidas | Criação de partidas, registro de movimentos e definição do vencedor. |
| **Histórico** | Consultar partidas anteriores | Listagem com filtros por jogador, resultado e data. |
| **Ranking** | Cálculo automático de pontuação ELO | Atualização automática do ranking a cada término de partida. |
| **Filtros e Paginação** | GET com filtros dinâmicos e paginação | Filtragem por nome, país, data e pontuação, com suporte a paginação. |
| **Ordenação** | Ordenação por data, ELO ou número de vitórias | Parâmetro `?sortBy=` disponível em listagens. |
| **DTOs e Validação** | Controle e validação de dados de entrada | Garantia de segurança e consistência na criação/edição de registros. |
| **Relatórios (Extra Futuro)** | Relatório de desempenho por jogador | Retorna estatísticas agregadas (vitórias, derrotas, empates). |

---

## 🧩 Modelos (Entidades)

A API possuirá **três entidades principais** com relacionamento entre si:

### 1. **Player (Jogador)**  
Representa um jogador cadastrado no sistema.

| Campo | Tipo | Descrição |
|--------|------|------------|
| `id` | int | Identificador único do jogador |
| `name` | string | Nome completo |
| `email` | string | Email único para login |
| `password` | string | Senha criptografada |
| `elo` | int | Pontuação ELO atual |
| `country` | string | País de origem |
| `createdAt` | datetime | Data de cadastro |

**Relacionamentos:**  
- `Player` (1:N) `Match` → um jogador pode participar de várias partidas.  

---

### 2. **Match (Partida)**  
Representa uma partida de xadrez entre dois jogadores.

| Campo | Tipo | Descrição |
|--------|------|------------|
| `id` | int | Identificador único |
| `playerWhiteId` | int | ID do jogador das peças brancas |
| `playerBlackId` | int | ID do jogador das peças pretas |
| `winner` | int (nullable) | ID do vencedor (ou null em caso de empate) |
| `result` | string | Resultado (`white`, `black`, `draw`) |
| `moves` | text | Registro dos movimentos (PGN simplificado) |
| `createdAt` | datetime | Data de início |
| `updatedAt` | datetime | Última atualização |

**Relacionamentos:**  
- `Match` (N:1) `Player` → cada partida envolve dois jogadores.  

---

### 3. **Tournament (Torneio)**  
Representa torneios de xadrez compostos por várias partidas.

| Campo | Tipo | Descrição |
|--------|------|------------|
| `id` | int | Identificador único do torneio |
| `name` | string | Nome do torneio |
| `location` | string | Local ou plataforma |
| `startDate` | date | Data de início |
| `endDate` | date | Data de término |
| `matches` | array | Lista de partidas associadas |

**Relacionamentos:**  
- `Tournament` (1:N) `Match` → um torneio contém várias partidas.  

---

## 🔁 Relacionamentos entre Entidades
```
Player (1) ───< Match >───(1) Player
                 │
                 ▼
            Tournament (1)
```

---

## 🧭 Estrutura Planejada de Rotas (exemplos)

### 🔐 Autenticação
| Método | Rota | Descrição |
|--------|------|------------|
| POST | `/auth/register` | Cadastrar novo jogador |
| POST | `/auth/login` | Autenticar e gerar token JWT |

---

### 🧍 Jogadores
| Método | Rota | Descrição |
|--------|------|------------|
| GET | `/players` | Listar todos os jogadores (com paginação e filtros) |
| GET | `/players/:id` | Buscar jogador específico |
| POST | `/players` | Criar novo jogador |
| PUT | `/players/:id` | Atualizar informações do jogador |
| DELETE | `/players/:id` | Excluir jogador |

---

### ♟️ Partidas
| Método | Rota | Descrição |
|--------|------|------------|
| GET | `/matches` | Listar partidas com filtros e ordenação |
| GET | `/matches/:id` | Buscar detalhes de uma partida |
| POST | `/matches` | Criar nova partida |
| PUT | `/matches/:id` | Atualizar resultado/movimentos |
| DELETE | `/matches/:id` | Excluir partida (caso necessário) |

---

### 🏆 Torneios
| Método | Rota | Descrição |
|--------|------|------------|
| GET | `/tournaments` | Listar torneios cadastrados |
| GET | `/tournaments/:id` | Buscar detalhes de um torneio |
| POST | `/tournaments` | Criar novo torneio |
| PUT | `/tournaments/:id` | Atualizar informações do torneio |
| DELETE | `/tournaments/:id` | Excluir torneio |

---

## 🧩 Próximos Passos (Entrega 03)
- Implementar relacionamentos entre entidades (Player ↔ Match ↔ Tournament);  
- Adicionar paginação e ordenação nas rotas GET ALL;  
- Criar DTOs para entrada e saída de dados;  
- Adicionar filtros de busca por parâmetros;  

---

## 📚 Observações
Esta entrega documenta **as principais funcionalidades e modelos da API**, conforme solicitado.  
Na próxima etapa, será apresentada a **arquitetura REST detalhada** com rotas, verbos HTTP e códigos de resposta.


### 📅 Entrega 01
> Repositório Git com README.md contendo o **tema** e os **nomes dos integrantes**.  
> Tema escolhido: **Xadrez Inteligente (Chess API)** ✅

### 📅 Entrega 02
> Documentação das Funcionalidades e Modelos  
