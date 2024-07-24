import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { HomepageComponent } from "./components/homepage/homepage.component";
import { UserSignuppageComponent } from "./components/user-signuppage/user-signuppage.component";
import { UserLoginpageComponent } from "./components/user-loginpage/user-loginpage.component";
import { UserAccountProfileComponent } from "./components/user-account/user-account-profile/user-account-profile.component";

const routes: Routes = [
    { path: '', component: HomepageComponent },
    { path: 'user/signup', component: UserSignuppageComponent },
    { path: 'user/login', component: UserLoginpageComponent },
    { path: 'user/account/profile', component: UserAccountProfileComponent },
];

@NgModule({
    imports: [
        RouterModule.forRoot(routes),
    ],
    exports: [RouterModule]
})
export class AppRoutingModule { }