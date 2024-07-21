import { CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

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
import { SwiperModule } from 'swiper/angular';

import { UserService } from './services/user.service';
import { HttpClientModule } from '@angular/common/http';
import { TempComponent } from './components/temp/temp.component';
import { CarouselComponent } from './components/homepage/carousel/carousel.component';
import { CategoryComponent } from './components/homepage/category/category.component';
import { FlashSaleComponent } from './components/homepage/flash-sale/flash-sale.component';
import { BannerComponent } from './components/homepage/banner/banner.component';
import { ShopeeMallComponent } from './components/homepage/shopee-mall/shopee-mall.component';
import { SuggestionComponent } from './components/homepage/suggestion/suggestion.component';
import { UserAccountComponent } from './components/user-account/user-account.component';
import { UserAccountProfileComponent } from './components/user-account/user-account-profile/user-account-profile.component';
import { SellerNavbarComponent } from './components/seller-navbar/seller-navbar.component';
import { SellerHomepageComponent } from './components/seller-homepage/seller-homepage.component';
import { CategoryPageComponent } from './components/category-page/category-page.component';

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
    TempComponent,
    CarouselComponent,
    CategoryComponent,
    FlashSaleComponent,
    BannerComponent,
    ShopeeMallComponent,
    SuggestionComponent,
    UserAccountComponent,
    UserAccountProfileComponent,
    SellerNavbarComponent,
    SellerHomepageComponent,
    CategoryPageComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    SwiperModule
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA,
    NO_ERRORS_SCHEMA
  ],
  providers: [
    UserService
  ],
  bootstrap: [CategoryPageComponent]
})
export class AppModule { }
