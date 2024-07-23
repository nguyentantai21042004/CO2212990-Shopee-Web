import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserAccountProfileComponent } from './user-account-profile/user-account-profile.component';
import { UserRoutingModule } from './user-routing.module';
import { NavbarComponent } from '../navbar/navbar.component';

@NgModule({
    declarations: [
        UserAccountProfileComponent,
    ],
    imports: [
        CommonModule,
        UserRoutingModule
    ]
})
export class UserAccountModule { }
