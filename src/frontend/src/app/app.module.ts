import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HomepageComponent } from './components/homepage/homepage.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { BuyerSigupComponent } from './components/buyer-sigup/buyer-sigup.component';
import { LoginNavbarComponent } from './components/user-loginpage/login-navbar/login-navbar.component';
import { UserLoginpageComponent } from './components/user-loginpage/user-loginpage.component';
import { FooterComponent } from './components/footer/footer.component';

@NgModule({
  declarations: [
    HomepageComponent,
    NavbarComponent,
    BuyerSigupComponent,
    LoginNavbarComponent,
    UserLoginpageComponent,
    FooterComponent,
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [UserLoginpageComponent]
})
export class AppModule { }
