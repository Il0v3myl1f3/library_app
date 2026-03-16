import { IAuthor } from 'app/entities/author/author.model';
import { IBook } from 'app/entities/book/book.model';

export interface IBookAuthor {
  id: number;
  bookIsbn?: string | null;
  author?: Pick<IAuthor, 'id' | 'firstName'> | null;
  book?: Pick<IBook, 'id' | 'isbn'> | null;
}

export type NewBookAuthor = Omit<IBookAuthor, 'id'> & { id: null };
