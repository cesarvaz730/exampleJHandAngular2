import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AnivipTestSharedModule } from '../../shared';
import {
    PassService,
    PassPopupService,
    PassComponent,
    PassDetailComponent,
    PassDialogComponent,
    PassPopupComponent,
    PassDeletePopupComponent,
    PassDeleteDialogComponent,
    passRoute,
    passPopupRoute,
} from './';

const ENTITY_STATES = [
    ...passRoute,
    ...passPopupRoute,
];

@NgModule({
    imports: [
        AnivipTestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PassComponent,
        PassDetailComponent,
        PassDialogComponent,
        PassDeleteDialogComponent,
        PassPopupComponent,
        PassDeletePopupComponent,
    ],
    entryComponents: [
        PassComponent,
        PassDialogComponent,
        PassPopupComponent,
        PassDeleteDialogComponent,
        PassDeletePopupComponent,
    ],
    providers: [
        PassService,
        PassPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AnivipTestPassModule {}
