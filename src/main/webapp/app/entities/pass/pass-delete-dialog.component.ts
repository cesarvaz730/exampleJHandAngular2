import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Pass } from './pass.model';
import { PassPopupService } from './pass-popup.service';
import { PassService } from './pass.service';

@Component({
    selector: 'jhi-pass-delete-dialog',
    templateUrl: './pass-delete-dialog.component.html'
})
export class PassDeleteDialogComponent {

    pass: Pass;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private passService: PassService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['pass']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.passService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'passListModification',
                content: 'Deleted an pass'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pass-delete-popup',
    template: ''
})
export class PassDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private passPopupService: PassPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.passPopupService
                .open(PassDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
