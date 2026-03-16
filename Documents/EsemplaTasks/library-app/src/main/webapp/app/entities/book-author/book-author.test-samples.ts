import { IBookAuthor, NewBookAuthor } from './book-author.model';

export const sampleWithRequiredData: IBookAuthor = {
  id: 1781,
  bookIsbn: 'hence um haul',
};

export const sampleWithPartialData: IBookAuthor = {
  id: 22832,
  bookIsbn: 'colligate sav',
};

export const sampleWithFullData: IBookAuthor = {
  id: 18826,
  bookIsbn: 'shout sneaky',
};

export const sampleWithNewData: NewBookAuthor = {
  bookIsbn: 'not',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
