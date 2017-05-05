import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { AnivipTestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PassDetailComponent } from '../../../../../../main/webapp/app/entities/pass/pass-detail.component';
import { PassService } from '../../../../../../main/webapp/app/entities/pass/pass.service';
import { Pass } from '../../../../../../main/webapp/app/entities/pass/pass.model';

describe('Component Tests', () => {

    describe('Pass Management Detail Component', () => {
        let comp: PassDetailComponent;
        let fixture: ComponentFixture<PassDetailComponent>;
        let service: PassService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AnivipTestTestModule],
                declarations: [PassDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PassService,
                    EventManager
                ]
            }).overrideComponent(PassDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PassDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PassService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Pass('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pass).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
