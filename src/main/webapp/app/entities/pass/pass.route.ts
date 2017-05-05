import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PassComponent } from './pass.component';
import { PassDetailComponent } from './pass-detail.component';
import { PassPopupComponent } from './pass-dialog.component';
import { PassDeletePopupComponent } from './pass-delete-dialog.component';

import { Principal } from '../../shared';

export const passRoute: Routes = [
  {
    path: 'pass',
    component: PassComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'anivipTestApp.pass.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'pass/:id',
    component: PassDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'anivipTestApp.pass.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const passPopupRoute: Routes = [
  {
    path: 'pass-new',
    component: PassPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'anivipTestApp.pass.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'pass/:id/edit',
    component: PassPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'anivipTestApp.pass.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'pass/:id/delete',
    component: PassDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'anivipTestApp.pass.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
