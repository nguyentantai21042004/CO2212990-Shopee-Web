import { IsEmail, IsNotEmpty, IsPhoneNumber, IsString } from 'class-validator';

export class RegisterDTO {
    @IsPhoneNumber()
    @IsNotEmpty()
    phoneNumber: string;

    @IsEmail()
    email: string;

    @IsString()
    @IsNotEmpty()
    password: string;

    @IsString()
    @IsNotEmpty()
    retypePassword: string;

    @IsString()
    @IsNotEmpty()
    roleName: string;

    facebookAccountId: string;

    googleAccountId: string

    constructor(data: any) {
        this.phoneNumber = data.phoneNumber;
        this.email = data.email;
        this.password = data.password;
        this.retypePassword = data.retypePassword;
        this.roleName = data.roleName || "USER";
        this.facebookAccountId = data.facebookAccountId;
        this.googleAccountId = data.googleAccountId;
    }
}