import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { HomepageComponent } from "./components/homepage/homepage.component";
import { UserSignuppageComponent } from "./components/user-signuppage/user-signuppage.component";
import { UserLoginpageComponent } from "./components/user-loginpage/user-loginpage.component";

const routes: Routes = [
    { path: '', component: HomepageComponent },
    { path: 'user/signup', component: UserSignuppageComponent },
    { path: 'user/login', component: UserLoginpageComponent },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }