import { IPublisher } from 'app/entities/publisher/publisher.model';

export interface IBook {
  id: number;
  isbn?: string | null;
  name?: string | null;
  publishYear?: string | null;
  copies?: number | null;
  picture?: string | null;
  publisher?: Pick<IPublisher, 'id' | 'name'> | null;
}

export type NewBook = Omit<IBook, 'id'> & { id: null };
