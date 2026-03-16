import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  id: 16289,
  firstName: 'Sadye',
  lastName: 'Macejkovic',
};

export const sampleWithPartialData: IClient = {
  id: 14463,
  firstName: 'Dorothy',
  lastName: 'Huel',
  address: 'next truly harvest',
  phone: '(257) 774-1245 x833',
};

export const sampleWithFullData: IClient = {
  id: 31496,
  firstName: 'Zelda',
  lastName: 'Herman',
  address: 'advocate off granular',
  phone: '415.772.6928 x10583',
};

export const sampleWithNewData: NewClient = {
  firstName: 'Ida',
  lastName: 'Smitham',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
