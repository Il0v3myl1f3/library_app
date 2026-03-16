import dayjs from 'dayjs/esm';
import { IClient } from 'app/entities/client/client.model';
import { IBook } from 'app/entities/book/book.model';

export interface IBorrowedBook {
  id: number;
  bookIsbn?: string | null;
  borrowDate?: dayjs.Dayjs | null;
  client?: Pick<IClient, 'id' | 'firstName'> | null;
  book?: Pick<IBook, 'id' | 'isbn'> | null;
}

export type NewBorrowedBook = Omit<IBorrowedBook, 'id'> & { id: null };
