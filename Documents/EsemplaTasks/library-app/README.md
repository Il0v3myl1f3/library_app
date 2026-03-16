# Library App

A library management system built with **JHipster 8.11.0** (Spring Boot + Angular), featuring book catalog management, client tracking, and book borrowing/returning functionality.

## Tech Stack

| Layer      | Technology                        |
| ---------- | --------------------------------- |
| Backend    | Spring Boot 3, Java 17            |
| Frontend   | Angular 19, Bootstrap             |
| Database   | PostgreSQL (dev & prod)            |
| ORM        | JPA / Hibernate + Liquibase       |
| Build      | Maven (backend), npm (frontend)   |
| Auth       | Spring Security + JWT             |

## Domain Model

```
Author (firstName, lastName)
Publisher (name)
Book (isbn, name, publishYear, copies, picture) → Publisher
BookAuthor → Author, Book
Client (firstName, lastName, address, phone)
BorrowedBook (bookIsbn, borrowDate) → Client, Book
```

## User Roles

| Role              | Description                                      |
| ----------------- | ------------------------------------------------ |
| `ROLE_ADMIN`      | Full system access, user management              |
| `ROLE_USER`       | Default authenticated user                       |
| `ROLE_LIBRARIAN`  | Library staff – manages books and borrowing       |

## API Endpoints

### Standard CRUD (all entities)

Each entity (Author, Book, BookAuthor, Client, Publisher, BorrowedBook) exposes standard REST endpoints:

- `GET    /api/{entity}` — list (paginated, filterable)
- `GET    /api/{entity}/{id}` — get by ID
- `POST   /api/{entity}` — create
- `PUT    /api/{entity}/{id}` — update
- `PATCH  /api/{entity}/{id}` — partial update
- `DELETE /api/{entity}/{id}` — delete

### Book Borrowing & Returning

| Method | Endpoint                           | Description                                                                 |
| ------ | ---------------------------------- | --------------------------------------------------------------------------- |
| `POST` | `/api/borrowed-books/borrow`       | Issue a book to a reader. Validates `copies > 0`, decrements copies.        |
| `POST` | `/api/borrowed-books/{id}/return`  | Receive a book back. Increments copies, deletes the BorrowedBook record.    |

#### Borrow request body example

```json
{
  "bookIsbn": "9781234567890",
  "borrowDate": "2026-03-16",
  "client": { "id": 1 },
  "book": { "id": 1 }
}
```

#### Business rules

- **Borrow**: fails with `400 Bad Request` if the book has 0 available copies.
- **Borrow**: auto-fills `bookIsbn` from the selected Book entity.
- **Return**: increments the book's `copies` and removes the BorrowedBook record.

## Development

### Prerequisites

- Java 17+
- Node.js 20+
- PostgreSQL running on `localhost:5432` with database `libraryApp`

### Run the backend

```bash
./mvnw
```

### Run the frontend (hot reload)

```bash
./npmw start
```

The app will be available at [http://localhost:8080](http://localhost:8080).

### Default accounts

| Login   | Password | Roles                    |
| ------- | -------- | ------------------------ |
| `admin` | `admin`  | ROLE_ADMIN, ROLE_USER    |
| `user`  | `user`   | ROLE_USER                |

## Building for Production

```bash
./mvnw -Pprod clean verify
java -jar target/*.jar
```

## Testing

```bash
# Backend tests
./mvnw verify

# Frontend tests
./npmw test
```

## Project Structure

```
src/
├── main/
│   ├── java/md/utm/libraryapp/
│   │   ├── domain/          # JPA entities
│   │   ├── repository/      # Spring Data repositories
│   │   ├── service/         # Business logic (interfaces + impl)
│   │   ├── service/dto/     # Data Transfer Objects
│   │   ├── service/mapper/  # MapStruct mappers
│   │   ├── web/rest/        # REST controllers
│   │   └── security/        # Auth constants & config
│   ├── resources/
│   │   └── config/liquibase/ # Database migrations & seed data
│   └── webapp/app/
│       ├── entities/        # Angular entity modules
│       ├── admin/           # User management UI
│       └── config/          # App constants (roles, etc.)
└── test/                    # Integration & unit tests
```
