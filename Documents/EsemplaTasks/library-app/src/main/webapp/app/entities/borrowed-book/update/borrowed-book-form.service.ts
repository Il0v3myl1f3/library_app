import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IBorrowedBook, NewBorrowedBook } from '../borrowed-book.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBorrowedBook for edit and NewBorrowedBookFormGroupInput for create.
 */
type BorrowedBookFormGroupInput = IBorrowedBook | PartialWithRequiredKeyOf<NewBorrowedBook>;

type BorrowedBookFormDefaults = Pick<NewBorrowedBook, 'id'>;

type BorrowedBookFormGroupContent = {
  id: FormControl<IBorrowedBook['id'] | NewBorrowedBook['id']>;
  bookIsbn: FormControl<IBorrowedBook['bookIsbn']>;
  borrowDate: FormControl<IBorrowedBook['borrowDate']>;
  client: FormControl<IBorrowedBook['client']>;
  book: FormControl<IBorrowedBook['book']>;
};

export type BorrowedBookFormGroup = FormGroup<BorrowedBookFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BorrowedBookFormService {
  createBorrowedBookFormGroup(borrowedBook: BorrowedBookFormGroupInput = { id: null }): BorrowedBookFormGroup {
    const borrowedBookRawValue = {
      ...this.getFormDefaults(),
      ...borrowedBook,
    };
    return new FormGroup<BorrowedBookFormGroupContent>({
      id: new FormControl(
        { value: borrowedBookRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      bookIsbn: new FormControl(borrowedBookRawValue.bookIsbn, {
        validators: [Validators.required, Validators.maxLength(13)],
      }),
      borrowDate: new FormControl(borrowedBookRawValue.borrowDate, {
        validators: [Validators.required],
      }),
      client: new FormControl(borrowedBookRawValue.client, {
        validators: [Validators.required],
      }),
      book: new FormControl(borrowedBookRawValue.book, {
        validators: [Validators.required],
      }),
    });
  }

  getBorrowedBook(form: BorrowedBookFormGroup): IBorrowedBook | NewBorrowedBook {
    return form.getRawValue() as IBorrowedBook | NewBorrowedBook;
  }

  resetForm(form: BorrowedBookFormGroup, borrowedBook: BorrowedBookFormGroupInput): void {
    const borrowedBookRawValue = { ...this.getFormDefaults(), ...borrowedBook };
    form.reset(
      {
        ...borrowedBookRawValue,
        id: { value: borrowedBookRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BorrowedBookFormDefaults {
    return {
      id: null,
    };
  }
}
