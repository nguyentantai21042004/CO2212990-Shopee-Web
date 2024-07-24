import { Role } from "../models/role";

export interface UserDetail {
    createdAt: Date;
    updatedAt: Date;

    id: string;
    email: string;
    fullName: string;
    password: string;
    originalPassword: string;
    phoneNumber: string;

    dateOfBirth: Date;
    addresses: string;
    facebookAccountId: string;
    googleAccountId: string;
    role: Role;
    gender: string;



    enabled: boolean;
    active: boolean;
    username: string;
    accountNonLocked: boolean;
    credentialsNonExpired: boolean;
    accountNonExpired: boolean;
}