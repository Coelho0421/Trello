🏗️ Fase 1 — Setup inicial (fazem juntos, ~1h)
Antes de dividir, configurem o projeto base juntos:

Criar projeto no Spring Initializr com as dependências: Spring Web, Spring Data JPA, Spring Security, PostgreSQL Driver, Lombok, Spring Boot DevTools
Criar o banco de dados no PostgreSQL
Configurar o application.properties com a conexão ao banco
Definir a estrutura de pacotes: controller, service, repository, model, dto, security


👤 Você — Autenticação & Usuários
Essa é a base de tudo, e vai desbloquear o trabalho do seu amigo depois.
1. Entidade User

Campos: id, name, email, password, createdAt

2. Spring Security + JWT

Dependência: jjwt
Criar JwtUtil (gerar e validar tokens)
Criar JwtFilter (interceptar requisições)
Configurar SecurityFilterChain (liberar /auth/**, proteger o resto)

3. Endpoints de Auth

POST /auth/register → cadastrar usuário (salvar senha com BCrypt)
POST /auth/login → retornar JWT token

4. Endpoint de perfil

GET /users/me → retornar dados do usuário logado (via token)


📋 Amigo — Boards, Listas & Cards
Enquanto você faz o auth, ele monta o coração do Trello.
1. Entidade Board

Campos: id, title, description, owner (FK → User), createdAt
Endpoints: POST /boards, GET /boards, GET /boards/{id}, PUT /boards/{id}, DELETE /boards/{id}

2. Entidade BoardList (as colunas tipo "A fazer", "Em progresso")

Campos: id, title, position, board (FK → Board)
Endpoints: POST /boards/{boardId}/lists, PUT /lists/{id}, DELETE /lists/{id}

3. Entidade Card (as tarefas dentro das colunas)

Campos: id, title, description, position, dueDate, list (FK → BoardList)
Endpoints: POST /lists/{listId}/cards, PUT /cards/{id}, DELETE /cards/{id}, PATCH /cards/{id}/move (mover entre listas)


🔗 Fase 2 — Integração (fazem juntos)
Depois que cada um terminar sua parte, integram:

Associar Board ao usuário logado (pegar o User do JWT)
Criar entidade BoardMember para convidar outros usuários para um board
Validar permissões: só membros do board podem ver/editar
