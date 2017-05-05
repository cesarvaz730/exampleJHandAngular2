import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Pass } from './pass.model';
import { PassService } from './pass.service';

@Component({
    selector: 'jhi-pass-detail',
    templateUrl: './pass-detail.component.html'
})
export class PassDetailComponent implements OnInit, OnDestroy {

    pass: Pass;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private passService: PassService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['pass']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPasses();
    }

    load(id) {
        this.passService.find(id).subscribe((pass) => {
            this.pass = pass;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPasses() {
        this.eventSubscriber = this.eventManager.subscribe('passListModification', (response) => this.load(this.pass.id));
    }
}
