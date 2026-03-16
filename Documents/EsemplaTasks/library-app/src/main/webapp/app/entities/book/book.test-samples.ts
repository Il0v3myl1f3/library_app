import { IBook, NewBook } from './book.model';

export const sampleWithRequiredData: IBook = {
  id: 3991,
  isbn: 'blue regal',
  name: 'nocturnal wordy',
};

export const sampleWithPartialData: IBook = {
  id: 26838,
  isbn: 'whoever gym u',
  name: 'engender throughout',
};

export const sampleWithFullData: IBook = {
  id: 8637,
  isbn: 'sniff',
  name: 'however quicker',
  publishYear: 'reas',
  copies: 23703,
  picture: 'accelerator fuss',
};

export const sampleWithNewData: NewBook = {
  isbn: 'taxicab nor',
  name: 'agreeable boo',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
