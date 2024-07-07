import { CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { register } from 'swiper/element/bundle';

import { HomepageComponent } from './components/homepage/homepage.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { BuyerSigupComponent } from './components/buyer-sigup/buyer-sigup.component';
import { LoginNavbarComponent } from './components/user-loginpage/login-navbar/login-navbar.component';
import { UserLoginpageComponent } from './components/user-loginpage/user-loginpage.component';
import { FooterComponent } from './components/footer/footer.component';
import { AppComponent } from './app/app.component';
import { AppRoutingModule } from './app-routing.module';
import { UserSignuppageComponent } from './components/user-signuppage/user-signuppage.component';
import { SignupNavbarComponent } from './components/user-signuppage/signup-navbar/signup-navbar.component';
import { FormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';

import { UserService } from './services/user.service';
import { HttpClientModule } from '@angular/common/http';

register();
@NgModule({
  declarations: [
    HomepageComponent,
    NavbarComponent,
    BuyerSigupComponent,
    LoginNavbarComponent,
    UserLoginpageComponent,
    FooterComponent,
    AppComponent,
    UserSignuppageComponent,
    SignupNavbarComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    MatDialogModule,
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA,
    NO_ERRORS_SCHEMA
  ],
  providers: [
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
