import dayjs from 'dayjs/esm';

import { IBorrowedBook, NewBorrowedBook } from './borrowed-book.model';

export const sampleWithRequiredData: IBorrowedBook = {
  id: 27089,
  bookIsbn: 'excluding by',
  borrowDate: dayjs('2026-03-15'),
};

export const sampleWithPartialData: IBorrowedBook = {
  id: 22378,
  bookIsbn: 'knitting',
  borrowDate: dayjs('2026-03-15'),
};

export const sampleWithFullData: IBorrowedBook = {
  id: 29509,
  bookIsbn: 'oof',
  borrowDate: dayjs('2026-03-15'),
};

export const sampleWithNewData: NewBorrowedBook = {
  bookIsbn: 'wombat',
  borrowDate: dayjs('2026-03-15'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
