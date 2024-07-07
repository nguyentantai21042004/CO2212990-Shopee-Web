import { IsEmail, IsNotEmpty, IsPhoneNumber, IsString } from 'class-validator';

export class LoginDTO {
    @IsPhoneNumber()
    @IsNotEmpty()
    phoneNumber: string;

    @IsEmail()
    email: string;

    @IsString()
    @IsNotEmpty()
    password: string;

    @IsString()
    roleName: string;

    constructor(data: any) {
        this.phoneNumber = data.phoneNumber;
        this.email = data.email;
        this.password = data.password;
        this.roleName = data.roleName;
    }
}