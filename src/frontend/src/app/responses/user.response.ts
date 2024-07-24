export interface UserResponse {
    id: number;
    fullname: string;
    phoneNumber: string;
    address: string;
    isActive: boolean;
    date_of_birth: Date;

    token: string;
    facebook_account_id: number;
    google_account_id: number;
}

