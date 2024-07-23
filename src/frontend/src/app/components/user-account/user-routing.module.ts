import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { UserAccountProfileComponent } from "./user-account-profile/user-account-profile.component";

const userRoutes: Routes = [
    { path: '', component: UserAccountProfileComponent }, // Route for user account profile
];

@NgModule({
    imports: [RouterModule.forChild(userRoutes)],
    exports: [RouterModule]
})
export class UserRoutingModule { }
